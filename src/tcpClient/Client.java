package tcpClient;
import util.*;

import java.net.*;
import java.io.*;

import javax.swing.JOptionPane;



public class Client {
    public static void main(String[] args) throws Exception {
    	
        String sentence=""; //Sensor dnd se almacena la frase introducida por el usuario
        
        
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
            			// espero a que rellene los datos y pulse boton loggin
            		}
            		if(ventanaloggin.boton==1){ // pulso boton loggearme
            			
	            		user.setNick(ventanaloggin.txtFUser.getText()); 
	                  	user.setContrasena(Integer.parseInt(ventanaloggin.txtFPasword.getText()));
	            		
	            		sm.Escribir(user.getNick()+'\n'); // mando nick al server
	       
	            		if (sm.Leer().contains("200 OK. bienvenido")){ // lo que me responde es todo ok
	            			ventanaloggin.dispose();
	            			estado=1;
	            		}else if (sm.Leer().contains("400 ERR")){ // si me responde que esta vacio 
	            			JOptionPane.showMessageDialog(ventanaloggin,"Campo User vacio");
	            			ventanaloggin.dispose();
	            			estado=0;
	            		}else{ // si me responde que esta mal introducido/no existe 
	            			JOptionPane.showMessageDialog(ventanaloggin,"El User que has introducido no es correcto");
	            			ventanaloggin.dispose();
	            			estado=0;
	            		}
            		}else if(ventanaloggin.boton==2){// pulso boton salir
            			ventanaloggin.dispose();// salgo de la app
            			sm.Escribir("adios"+'\n'); // mando al server que quiere salir
            			estado= 4;
            		}
            	break;
            	case 1:// comprobar pass
            		String a = Integer.toString(user.getContrasena());
            		sm.Escribir(a+'\n');  // mando la pass al server
            		if (sm.Leer().contains("201 OK.")){ 
            			estado=2;
            			System.out.println("entra");
            		}else if (sm.Leer().contains("402 ERR.")){ // falta la pass
            			estado=0;
            			System.out.println("no entra por falta de pass ");
            		}else  { //  pass erronea
            			estado=0;
            			System.out.println("no entra por pass incorrecta");
            		}
                break;
            	case 2:// accion
            		
            		
            		
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