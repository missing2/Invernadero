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
            			//Thread.sleep(1000);
            			System.out.println("while");
            			// espero a que rellene los datos y pulse boton loggin
            		}
            		if(ventanaloggin.boton==1){ // pulso boton loggearme
            			System.out.println("log");
	            		user.setNick(ventanaloggin.txtFUser.getText()); 
	                  	user.setContrasena(Integer.parseInt(ventanaloggin.txtFPasword.getText()));
	            		
	            		sm.Escribir(user.getNick()+'\n'); // mando nick al server
	       
	            		if (sm.Leer().contains("200 OK. bienvenido")){ // lo que me responde es todo ok
	            			System.out.println("user comprobado, correcto");
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
            		System.out.println("case 1");
            		String a = Integer.toString(user.getContrasena());
            		sm.Escribir(a+'\n');  // mando la pass al server
            		System.out.println("escribir");
            		Thread.sleep(1000);
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
            		System.out.println("case 2");
            		sm.Escribir("sacalista"+'\n'); // manda al server un comando para que me mande la lista
            		String stringLista = sm.Leer(); // recibe la lista en string
            		ArrayList<String> listae = cargarLista(stringLista);
            		System.out.println("Lista.."+listae);
            		ventanaAccion vent = new ventanaAccion();
            	    vent.lista = stringLista; // -----------------AQUI PETAAAAAAA, NO SABE COPIAR UN LIST A OTRO
            		
            	while (vent.boton!=6){
            		while (vent.boton==0){
            			//estoy en la vent sin mas
	                }
            			if (vent.boton==1){//Activar/ desactivar
            				
            			
            				if (vent.bActivar.getText().equals("on")){ // el boton esta en on y quiero activar
	            			sm.Escribir("activar"+'\n');
	            			String id = vent.id; //id del sensor que tengo que activar 
	            			sm.Escribir("id"+'\n');
            				}else { // quiero desactivar 
            					sm.Escribir("desactivar"+'\n');
    	            			String id = vent.id; //id del sensor que tengo que activar 
    	            			sm.Escribir("id"+'\n');
    	            			
            				}	
	               		}else if(vent.boton==2){//bActuar
	            			sm.Escribir("actuar"+'\n');
	            	    	sm.Escribir(vent.id+'\n'); // paso el id que voy a cambiar la accion
	            	    }else if(vent.boton==3){//bBuscar
	            	    	sm.Escribir("buscar"+'\n');
	            	    	
	               	    	if (vent.rdbPlaca.isSelected()){ // busco por placa
	            	    		sm.Escribir(vent.palabra.toString()+'\n');
	            	    		sm.Escribir("placa"+'\n');
	            	    	    String recibido=sm.Leer();
	            	    	  //  vent.cargarTabla( cargarLista(recibido)); // muestro en la tabla los resultados
	            	    	    
	            	    	}else { // busco por sensor
	            	    		sm.Escribir("sensor"+'\n');
	            	    		sm.Escribir(vent.palabra.toString()+'\n');
	            	    		String recibido=sm.Leer();
	            	    		cargarLista(recibido);
	            	    		//vent.cargarTabla( cargarLista(recibido)); // muestro en la tabla los resultados
	            	    	}
	            	    	
	            	    	
	            	    }else if(vent.boton==4){// imagen
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

	private static ArrayList<String> cargarLista(String stringLista) {
		// TODO Auto-generated method stub
		System.out.println(stringLista);
		ArrayList<String> df = new ArrayList<String>();
		System.out.println("Array lista en el inicio: "+df);
		 
		String Sensor[] = stringLista.split(",");// separo sensores
		System.out.println("lista entera sensores "+stringLista);
		
		for(int i=0;i<Sensor.length;i++){
			String stringAtributos= Sensor[i]; // separo los atributos de cada sensor
		System.out.println("lista separada ,"+Sensor[i]);
			String Atributo[]=stringAtributos.split("-");// separo atributos en el array
		System.out.println("sensor "+ Atributo[0]+", "+Atributo[1]+", "+Atributo[2]+", "+Atributo[3]+", "+Atributo[4]);
			df.add(Atributo[0]+Atributo[1]+Atributo[2]+Atributo[3]+Atributo[4]); // añado los atributos al arraylist
			
		}
		System.out.println(df);
		
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
