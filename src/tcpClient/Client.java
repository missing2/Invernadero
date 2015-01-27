package tcpClient;
import util.*;

import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.Image;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
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
         
           
                     
           
            while (true) {	
            	switch(estado){
            	case 0: // comprobar user y creo el socket
            		ventanaLoggin ventanaloggin = new ventanaLoggin();
            		 String seleccion = JOptionPane.showInputDialog(ventanaloggin,"Introduce host del servidor (127.0.0.1)",JOptionPane.QUESTION_MESSAGE); 
            		//Se crea el socket, pasando el host del server manualmente
               		sm = new SocketManager(seleccion,2345);
            		
            		while (ventanaloggin.boton==0){
            			Thread.sleep(1000);
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
            			user.setNick("nadie");
            			ventanaloggin.dispose();
            			estado= 4;
            			
            		}else if (ventanaloggin.boton==3){// pulso para altas bajas
            			ventanaloggin.dispose();
            			VentanaAltasBajas vent = new VentanaAltasBajas();
            			vent.setVisible(true);
            			
            			while (vent.boton==0){
                			// espero a que rellene los datos y pulse boton loggin
                		}
            			if (vent.boton==1){// Alta
            				sm.Escribir("alta"+'\n');
            				String nick = vent.textNick.getText();
            				sm.Escribir(nick+'\n');
            				String pass = vent.textPass.getText();
            				sm.Escribir(pass+'\n');
            				String resp = sm.Leer();
            				if (resp.equals("211.OK Usuario insertado correctamente")){
            					JOptionPane.showMessageDialog(ventanaloggin,"Usuario insertado correctamente");
            					vent.dispose();
            					estado=0;
            				}else{
            					JOptionPane.showMessageDialog(ventanaloggin,"Usuario no insertado por algun motivo");
            					vent.dispose();
            					estado=0;
            				}
            			}else if (vent.boton==2){// Salir
	            			vent.dispose();
	            			user.setNick("nadie");
	            			ventanaloggin.boton=0;
	            			ventanaloggin.dispose();
	            			estado= 4;
	            			
            			}else if (vent.boton==3){ //Baja
            				sm.Escribir("baja"+'\n');
            				String nick = vent.textNick.getText();
            				sm.Escribir(nick+'\n');
            				String pass = vent.textPass.getText();
            				sm.Escribir(pass+'\n');
            				String resp = sm.Leer();
            				if (resp.equals("210.OK Usuario eliminado correctamente")){
            					JOptionPane.showMessageDialog(ventanaloggin,"Usuario eliminado correctamente");
            					vent.dispose();
            					estado=0;
            				}else{
            					JOptionPane.showMessageDialog(ventanaloggin,"Usuario no eliminado por algun motivo");
            					vent.dispose();
            					estado=0;
            					
            				}
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
            		ventanaAccion vent = new ventanaAccion();
            		vent.cargarTabla(listae);
            		
            		
            		while (true){
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
           						vent.cargarTabla(df);
           					   }
            				
            							
            			}else if(vent.boton==2){// desactivar
            				vent.boton=0;
            				int confirmado = JOptionPane.showConfirmDialog(vent, "�Confirmar?");
        					if (JOptionPane.OK_OPTION == confirmado){
        						sm.Escribir("desactivar"+'\n');
        						String id = vent.id; //id del sensor que tengo que activar  
           						sm.Escribir(id+'\n');
           						String stringLista = sm.Leer();
           						ArrayList<String> df = new ArrayList<String>();
           						String Sensor[] = stringLista.split(",");// separo sensores
           						for(int i=0;i<Sensor.length;i++){
           							df.add(Sensor[i]);
           						}
           						vent.cargarTabla(df);
           					   }
        					
	               		}else if(vent.boton==3){//bActuar
	               			vent.boton=0;
	            			sm.Escribir("actuar"+'\n');
	            	    	sm.Escribir(vent.id+'\n'); // paso el id que voy a cambiar la accion 
	               	    	sm.Escribir(vent.txt+'\n');
	            	    	String respuesta = sm.Leer();
       						String stringLista = sm.Leer();
       						if(respuesta.equals("409 ERR Faltan datos."))
       							JOptionPane.showMessageDialog(vent, "falta parametro");
       						else if (respuesta.equals("407 ERROR accion ya ejecutada"))
           							JOptionPane.showMessageDialog(vent, "accion repetida");
       						else if (respuesta.equals("206 OK Acci�n sobre el sensor confirmada")){
	       						ArrayList<String> df = new ArrayList<String>();
	       						String Sensor[] = stringLista.split(",");// separo sensores
	       						for(int i=0;i<Sensor.length;i++){
	       							df.add(Sensor[i]);
	       						}
	       						vent.cargarTabla(df);
       						}
       						
	            	    }else if(vent.boton==4){//bBuscar
	            	    	vent.boton=0;
	            	    	sm.Escribir("buscar"+'\n');
	            	    	sm.Escribir(vent.palabra.getText()+'\n');
	            	    	String recibido=sm.Leer();
	            	    	ArrayList<String> df = new ArrayList<String>();
	            	    	String Sensor[] = recibido.split(",");// separo sensores
	            	    	for(int i=0;i<Sensor.length;i++){
	            	    		df.add(Sensor[i]);
	            	    	}
	            	    	vent.cargarTabla(df);
	            	    	
	            	    	
	            	    	
	            	    }else if(vent.boton==5){// imagen
	            	    	vent.boton=0;
	            	    	System.out.println("bot5 clicado");
	            	    	vent.boton=0;
	            	    	sm.Escribir("imagen"+'\n');
	            	    	sm.Escribir(vent.id+'\n');
	            	    	
	            	    }else if(vent.boton==6){// salir
	            	    	vent.boton=0;
	            	    	System.out.println("quiero salirrrr");
	            			estado=4;
	        
	            		}else if(vent.boton==7){//bdesactivar placa
	               			vent.boton=0;
	               			int confirmado = JOptionPane.showConfirmDialog(vent, "�Confirmar?");
        					if (JOptionPane.OK_OPTION == confirmado){
		               			sm.Escribir("desactivarplaca"+'\n');
		               			sm.Escribir(vent.id+'\n');
           						String stringLista = sm.Leer();
           						ArrayList<String> df = new ArrayList<String>();
           						String Sensor[] = stringLista.split(",");// separo sensores
           						for(int i=0;i<Sensor.length;i++){
           							df.add(Sensor[i]);
           						}
           						vent.cargarTabla(df);
        					}
	            		}else if(vent.boton==8){//bActivarplaca
	               			vent.boton=0;
	               			int confirmado = JOptionPane.showConfirmDialog(vent, "�Confirmar?");
        					if (JOptionPane.OK_OPTION == confirmado){
		               			sm.Escribir("activarplaca"+'\n');
		               			sm.Escribir(vent.id+'\n');
		               			System.out.println("Cargo la lista?");
           						String stringLista = sm.Leer();
           						ArrayList<String> df = new ArrayList<String>();
           						String Sensor[] = stringLista.split(",");// separo sensores
           						for(int i=0;i<Sensor.length;i++){
           							df.add(Sensor[i]);
           						}
           						vent.cargarTabla(df);
           					   }
        					}
	            		}
            	

            	case 4:// salir
            		sm.Escribir("adios"+'\n'); // mando al server que quiere salir
            		sm.Escribir(user.getNick()+'\n'); //mando el nombre del user que soy para que me desconecte en la bd
            		System.out.println("mando adios");
                break;
            	}        
            }
                   
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
		return df;
	   }
	
}