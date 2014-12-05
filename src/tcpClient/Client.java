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
            		sm.Escribir("sacalista"+'\n'); // manda al server un comando para que me mande la lista
            		String stringLista = sm.Leer(); // recibe la lista en string
            		
            		JList lista = cargarLista(stringLista);
            		ventanaAccion vent = new ventanaAccion();
            		
            		
            		while (vent.boton==0){
            			//estoy en la vent sin mas
            		}if (vent.boton==1){//Activar
            			sm.Escribir("activar"+'\n');
            			
            			
            		}else if(vent.boton==2){//bActuar
            			sm.Escribir("actuar"+'\n');
            		
            	    }else if(vent.boton==3){//bBuscar
            	    	sm.Escribir("buscar"+'\n');
        		
            	    }else if(vent.boton==4){// imagen
            	    	sm.Escribir("imagen"+'\n');
            	    }else if(vent.boton==5){//listar 
            	    	sm.Escribir("listar"+'\n');
            	    }else if(vent.boton==6){// salir
            			estado=4;
            			sm.Escribir("adios"+'\n'); // mando al server que quiere salir
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

	private static JList cargarLista(String stringLista) {
		// TODO Auto-generated method stub
		DefaultListModel df = new DefaultListModel();
		
		//Placa P =  new Placa();
		
		String sensoresYPlacas[]  = stringLista.split("/");// separo placas de sensores
		String stringSensores = sensoresYPlacas[0].toString(); 
		String Sensor[] = stringSensores.split(";");// separo sensores
		int conta=0;
		while(Sensor[conta].isEmpty()!=false){
		String stringAtributos= Sensor[conta].toString();
		String Atributo[]=stringAtributos.split(",");// separo atributos
		Sensor a = new Sensor(Atributo[0],Atributo[1],Atributo[2],Atributo[3],Atributo[4],Atributo[5],Atributo[6]);
		df.addElement(a);
		}
		//----------PLACAS-----------------
		String stringPlacas = sensoresYPlacas[1].toString();
		String Placa[] = stringPlacas.split(";");// separo placas
	    conta=0;
		while(Placa[conta].isEmpty()!=false){
		String stringAtributos= Placa[conta].toString();
		String Atributo[]=stringAtributos.split(",");// separo atributos
		Placa a = new Placa(Atributo[0],Atributo[1],Atributo[2]);
		df.addElement(a);
		}
		JList lista= new JList(df);
			return lista;
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
}