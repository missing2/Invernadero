package tcpClient;
import util.*;

import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.List;
import java.io.*;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;



public class Client {
    public static void main(String[] args) throws Exception {
    	
        String sentence=""; //Sensor dnd se almacena la frase introducida por el usuario
        
        
        try {
            //Se crea el socket, pasando el nombre del servidor y el puerto de conexión
            SocketManager sm = new SocketManager("127.0.0.1", 2345);  
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
            			System.out.println("");
            			// espero a que rellene los datos y pulse boton loggin
            		}
            		if(ventanaloggin.boton==1){ // pulso boton loggearme
	            		user.setNick(ventanaloggin.txtFUser.getText()); 
	                  	user.setContrasena(Integer.parseInt(ventanaloggin.txtFPasword.getText()));
	            		
	            		sm.Escribir(user.getNick()+'\n'); // mando nick al server
	       
	            		if (sm.Leer().contains("200 OK")){ // lo que me responde es todo ok
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
 		
            		if (sm.Leer().contains("201 OK.")){ 
            			estado=2;
            			
            		}else if (sm.Leer().contains("402 ERR.")){ // falta la pass
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
            		ventanaAccion vent = new ventanaAccion(listae);
            	                 		
            		while (vent.boton!=6){
            			while (vent.boton==0){
            			//estoy en la vent sin mas
            			}
            			if (vent.boton==1){//Activar/ desactivar
            				vent.boton=0;
            				int confirmado = JOptionPane.showConfirmDialog(vent,"¿confirmar?");

            				if (JOptionPane.OK_OPTION == confirmado){
            					sm.Escribir("activar"+'\n');
  								String id = vent.id; //id del sensor que tengo que activar  
           						sm.Escribir(id+'\n');
            					listae = cargarLista(sm);
        		                		
            					vent.cargarTabla(listae);
            				}
            							
            			}else if(vent.boton==2){
            				int confirmado = JOptionPane.showConfirmDialog(vent, "¿Confirmar?");
        					if (JOptionPane.OK_OPTION == confirmado){
        						sm.Escribir("desactivar"+'\n');
        						String id = vent.id; //id del sensor que tengo que activar 
        						sm.Escribir(id+'\n');
        						listae = cargarLista(sm);
        						vent.cargarTabla(listae);
        					}
	               		}else if(vent.boton==3){//bActuar
	               			vent.boton=0;
	               			System.out.println("bot3 clicado");
	            			sm.Escribir("actuar"+'\n');
	            	    	sm.Escribir(vent.id+'\n'); // paso el id que voy a cambiar la accion
	            	    	cargarLista(sm);
	            	    	
	            	    }else if(vent.boton==4){//bBuscar
	            	    	vent.boton=0;
	            	    	System.out.println("bot4 clicado");
	            	    	sm.Escribir("buscar"+'\n');
	           
	               	    	if (vent.rdbPlaca.isSelected()){ // busco por placa
	            	    		sm.Escribir(vent.palabra.toString()+'\n');
	            	    		sm.Escribir("placa"+'\n');
	            	    	    String recibido=sm.Leer();
	            	    	    System.out.println("resibida busqueda"+recibido);
	            	    	    
	            	    	    ArrayList<String> df = new ArrayList<String>();
	            	    		String Placa[] = recibido.split(",");// separo sensores
	            	    		for(int i=0;i<Placa.length;i++){
	            	    			df.add(Placa[i]);
	            	    		}
	            	    		 vent.cargarTabla(df);
	            	    	}else { // busco por sensor
	            	    		sm.Escribir("sensor"+'\n');
	            	    		sm.Escribir(vent.palabra.toString()+'\n');
	            	    		String recibido=sm.Leer();
	            	    		ArrayList<String> df = new ArrayList<String>();
	            	    		String Sensor[] = recibido.split(",");// separo sensores
	            	    		for(int i=0;i<Sensor.length;i++){
	            	    			df.add(Sensor[i]);
	            	    		}
	            	    		 vent.cargarTabla(df);
	            	    	}
	            	    	
	            	    	
	            	    }else if(vent.boton==5){// imagen
	            	    	System.out.println("bot5 clicado");
	            	    	vent.boton=0;
	            	    	sm.Escribir("imagen"+'\n');
	            	    	
	            	 	            	    	
	            	    }else if(vent.boton==6){// salir
	            			estado=4;
	            			sm.Escribir("adios"+'\n'); // mando al server que quiere salir
	            		}
            	}
            		
                break;
            	case 3:
                break;
            	case 4:// salir
            		System.exit(0); 
            		sm.Escribir("adios"+'\n'); // mando al server que quiere salir
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
		
		
		
	

	


//private boolean enActivado = false;
//
//// Método privado utilizado para activar o desactivar los componentes de acuerdo al modo de activación
//private void setModoActivado( boolean on ) {
//	enActivado = on;
//	bActuar.setEnabled( !on );
//	bImagenPlaca.setEnabled( !on );
//	bBuscar.setEnabled( !on );
//	bListar.setEnabled( !on );
//	listaVariables.setEnabled( !on );
//	//nick.setEnabled( on );
//	if (on)
//		bActivar.setText( "OFF" );
//	else
//		bActivar.setText( "ON" );
//}
////dentro del actioner boton 1
//if(!enActivado){
//	 setModoActivado(true);
//	 //aqui cambiar el valor de la tabla a off
//	 int index = table.getSelectedRow();
//	 String  idv = (String) table.getValueAt(index, 1);
//	 Data_base_controler prueba = Data_base_controler.getInstance();
//	 try {
//		prueba.conectar();
//		prueba.apagarVariable(idv);
//		prueba.desconectar();
//			
//	} catch (ClassNotFoundException | SQLException e1) {
//		// TODO Auto-generated catch block
//		System.out.println("Algun error con la conexion BD");
//		e1.printStackTrace();
//	}
//}else{
//	 //aqui a on
//	 int index = table.getSelectedRow();
//	 String  idv = (String) table.getValueAt(index, 1);
//	 Data_base_controler prueba = Data_base_controler.getInstance();
//		
//		try {
//			prueba.conectar();
//			prueba.encenderVariable(idv);
//			prueba.desconectar();
//			
//		} catch (ClassNotFoundException | SQLException e1) {
//			// TODO Auto-generated catch block
//			System.out.println("Algun error con la conexion BD");
//			e1.printStackTrace();
//		}
