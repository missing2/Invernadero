package tcpClient;
import util.*;

import java.net.*;
import java.io.*;



public class TCPClient {
    public static void main(String[] args) throws Exception {
    	
        String sentence=""; //Variable dnd se almacena la frase introducida por el usuario
        
        String respuesta=""; //Variable donde se recibe la frase capitalizada
        try {
            //Se crea el socket, pasando el nombre del servidor y el puerto de conexión
            SocketManager sm = new SocketManager("127.0.0.1", 6789);  
            //Se inicializan los streams de lectura y escritura del socket

            //Se declara un buffer de lectura del dato escrito por el usuario por teclado
            //es necesario pq no es un buffer propio de los sockets
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            
            int estado = 0;
            Usuario user = new Usuario();
            while (estado!=4) {
            	
            	switch(estado){
            	case 0: // comprobar user
            		ventanaLoggin ventanaloggin = new ventanaLoggin();
            		while (ventanaloggin.boton==0){
            			// espero a que haga loggin
            		}
            		if(ventanaloggin.boton==1){ // me loggeo
            			
	            		user.setNick(ventanaloggin.txtFUser.getText()); 
	                  	user.setContrasena(Integer.parseInt(ventanaloggin.txtFPasword.getText()));
	            		
	            		sm.Escribir(user.getNick()+'\n'); // mando nick al server
	       
	            		if (sm.Leer().contains("200 OK. bienvenido")){ // lo que me responde....
	            			ventanaloggin.dispose();
	            			estado=1;
	            		}else{
	            			ventanaloggin.dispose();
	            			estado=0;
	            		}
            		}else if(ventanaloggin.boton==2){
            			ventanaloggin.dispose();// salgo de la app
            			sm.Escribir("adios"+'\n');
            			estado= 4;
            		}
            	break;
            	case 1:// comprobar pass
            		String a = Integer.toString(user.getContrasena());
            		sm.Escribir(a+'\n'); 
            		if (sm.Leer().contains("201 OK.")){ 
            			estado=2;
            			System.out.println("entra");
            		}else{
            			estado=0;
            			System.out.println("no entra");
            		}
                break;
            	case 2:
            		
            		
            		
                break;
            	case 3:
                break;
            	case 4:// salir
            		System.exit(0); 
                break;
            	}
                             
            }
            	
            System.out.println("Fin de la práctica");
            sm.CerrarSocket();
        } catch (Exception e) {
			System.err.println("main: " + e);
			e.printStackTrace();
        }

    }
    }