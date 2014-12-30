package tcpClient;
import util.*;

import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;



public class Client {
	static SocketManager sm;
    public static void main(String[] args) throws Exception {
     
        try {
            //Se declara un buffer de lectura del dato escrito por el usuario por teclado
            //es necesario pq no es un buffer propio de los sockets
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            
            int estado = 0;
            Usuario user = new Usuario();
            while (estado!=4) {
            	
            	switch(estado){
            	case 0: // comprobar user y creo el socket
            		ventanaLoggin ventanaloggin = new ventanaLoggin();
            		//Se crea el socket, pasando el nombre del servidor y el puerto de conexi�n
            		String ip = ventanaloggin.txtFIP.getText();
             		String numeros[] = ip.split(",");
            		sm = new SocketManager(numeros[0]+"."+numeros[1]+"."+numeros[2]+"."+numeros[3],2345);
            		while (ventanaloggin.boton==0){
            			System.out.println("");// espero a que rellene los datos y pulse boton loggin
            		}    
            		if(ventanaloggin.boton==1){ // pulso boton loggearme
            			
            			ventanaloggin.boton=0;
	            		user.setNick(ventanaloggin.txtFUser.getText()); 
	                  	user.setContrasena(Integer.parseInt(ventanaloggin.txtFPasword.getText()));
	            		
	            		sm.Escribir(user.getNick()+'\n'); // mando nick al server
	            		String resp=sm.Leer();
	            		if (resp.contains("200OK.Bienvenido")){ // lo que me responde es todo ok
	            			ventanaloggin.dispose();
	            			estado=1;
	            		}else if (resp.equals("400ERR.Falta el nombre de usuario")){ // si me responde que esta vacio 
	            			JOptionPane.showMessageDialog(ventanaloggin,"Campo User vacio");
	            			ventanaloggin.dispose();
	            			estado=0;
	            		}else if (resp.equals("401ERR.Usuario desconocido")){ // si me responde que esta mal introducido/no existe 
	            			JOptionPane.showMessageDialog(ventanaloggin,"El User que has introducido no es correcto");
	            			ventanaloggin.dispose();
	            			estado=0;
	            		}
	            		
            		}else if(ventanaloggin.boton==2){// pulso boton salir
            			ventanaloggin.boton=0;
            			ventanaloggin.dispose();// salgo de la app
            			sm.Escribir("adios"+'\n'); // mando al server que quiere salir
            			estado= 4;
            			
            		}else if (ventanaloggin.boton==3){// pulso para altas bajas
            			ventanaloggin.dispose();
            			VentanaAltasBajas vent = new VentanaAltasBajas();
            			
            			while (ventanaloggin.boton==0){
                			// espero a que rellene los datos y pulse boton loggin
                		}
            			if (vent.boton==1){// Alta
            				sm.Escribir("alta"+'\n');
            				String nick = vent.textNick.toString();
            				String pass = vent.textPass.toString();
            				
            			}else if (vent.boton==2){// Salir
            				estado=4;
	            			sm.Escribir("adios"+'\n'); // mando al server que quiere salir
	            			
            			}else if (vent.boton==3){ //Baja
            				sm.Escribir("baja"+'\n');
            				String nick = vent.textNick.toString();
            				String pass = vent.textPass.toString();
            			}
            		}
            		
            		
            	break;
            	case 1:// comprobar pass
            		
            		String a = Integer.toString(user.getContrasena());
            		sm.Escribir(a+'\n');  // mando la pass al server
            		String resp=sm.Leer();
            		if (resp.equals("201 OK Bienvenido al sistema")){ 
            			estado=2;
            		}else if (resp.equals("402 ERR Falta la clave")){ // falta la pass
            			estado=0;
            			System.out.println("no entra por falta de pass ");
            		}else  { //  pass erronea
            			estado=0;
            			System.out.println("no entra por pass incorrecta");
            		}
                break;
                
                
                
            	case 2:// accion
               		sm.Escribir("sacalista"+'\n'); // manda al server un comando para que me mande la lista
            		 // recibe la lista en string
            		ArrayList<String> listae = cargarLista(sm);
            		System.out.println("lista "+listae);
            		ventanaAccion vent = new ventanaAccion();
            		vent.cargarTabla(listae);
            		
            		
            		while (vent.boton!=6){
            			while (vent.boton==0){
            			//estoy en la vent sin mas
            				Thread.sleep(1000);
            			}
            			if (vent.boton==1){//Activar
            				vent.boton=0;
            				int confirmado = JOptionPane.showConfirmDialog(vent,"�confirmar?");
            				if (JOptionPane.OK_OPTION == confirmado){
            					sm.Escribir("activar"+'\n');
  								String id = vent.id; //id del sensor que tengo que activar  
  								System.out.println("Cliente id"+id);
           						sm.Escribir(id+'\n');
           						System.out.println("Cargo la lista?");
           						String stringLista = sm.Leer();
           						System.out.println("Leyendo lista aqui "+stringLista);
           						ArrayList<String> df = new ArrayList<String>();
           						String Sensor[] = stringLista.split(",");// separo sensores
           						for(int i=0;i<Sensor.length;i++){
           							df.add(Sensor[i]);
           						}
           						System.out.println("los datos de la lista"+df);
           						vent.cargarTabla(df);
           					   }
            				
            							
            			}else if(vent.boton==2){// desactivar
            				vent.boton=0;
            				int confirmado = JOptionPane.showConfirmDialog(vent, "�Confirmar?");
        					if (JOptionPane.OK_OPTION == confirmado){
        						sm.Escribir("desactivar"+'\n');
        						String id = vent.id; //id del sensor que tengo que activar  
           						sm.Escribir(id+'\n');
           						System.out.println("Cargo la lista?");
           						String stringLista = sm.Leer();
           						System.out.println("Leyendo lista aqui "+stringLista);
           						ArrayList<String> df = new ArrayList<String>();
           						String Sensor[] = stringLista.split(",");// separo sensores
           						for(int i=0;i<Sensor.length;i++){
           							df.add(Sensor[i]);
           						}
           						System.out.println("los datos de la lista"+df);
           						vent.cargarTabla(df);
           					   }
        					
	               		}else if(vent.boton==3){//bActuar
	               			vent.boton=0;
	               			System.out.println("bot3 clicado");
	            			sm.Escribir("actuar"+'\n');
	            	    	sm.Escribir(vent.id+'\n'); // paso el id que voy a cambiar la accion
	            	    	System.out.println("Cargo la lista?");
       						String stringLista = sm.Leer();
       						System.out.println(stringLista);
       						ArrayList<String> df = new ArrayList<String>();
       						String Sensor[] = stringLista.split(",");// separo sensores
       						for(int i=0;i<Sensor.length;i++){
       							df.add(Sensor[i]);
       						}
       						System.out.println("los datos de la lista"+df);
       						vent.cargarTabla(df);
       					   
	            	    }else if(vent.boton==4){//bBuscar
	            	    	vent.boton=0;
	            	    	System.out.println("bot4 clicado");
	            	    	sm.Escribir("buscar"+'\n');
	            	    	sm.Escribir(vent.palabra.toString()+'\n');
	            	    	String recibido=sm.Leer();
	            	    	ArrayList<String> df = new ArrayList<String>();
	            	    	String Sensor[] = recibido.split(",");// separo sensores
	            	    	for(int i=0;i<Sensor.length;i++){
	            	    		df.add(Sensor[i]);
	            	    	}
	            	    	vent.cargarTabla(df);
	            	    	
	            	    }else if(vent.boton==5){// imagen
	            	    	System.out.println("bot5 clicado");
	            	    	vent.boton=0;
	            	    	sm.Escribir("imagen"+'\n');
	            	    	sm.Escribir(vent.id+'\n');
	            	    	String foto = sm.Leer();
	            	    //	BufferedImage bufferedImage = ImageIO.read();// tengo que pasar aqui una imagen formada por string foto
	 			          //  ImageIO.write(bufferedImage,"png", new FileOutputStream("fotos recibidas/image.png"));       	    	
	            	    }else if(vent.boton==6){// salir
	            			estado=4;
	        
	            		}
            	}

            	case 4:// salir
            		sm.Escribir("adios"+'\n'); // mando al server que quiere salir
            		System.exit(0); 
                break;
            	}        
            }
            	
            System.out.println("Fin de la pr�ctica");
            sm.CerrarSocket();
        } catch (Exception e) {
			System.err.println("main: " + e);
			e.printStackTrace();
        }

    }

	private static ArrayList<String> cargarLista(SocketManager sm) throws IOException {
		// TODO Auto-generated method stub
		String stringLista = sm.Leer();
		ArrayList<String> df = new ArrayList<String>();
		String Sensor[] = stringLista.split(",");// separo sensores
		for(int i=0;i<Sensor.length;i++){
			df.add(Sensor[i]);
		}
		System.out.println("los datos de la lista"+df);
		return df;
	   }
	
}