package std.webServer;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.Buffer;
import java.sql.Array;
import java.sql.SQLException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import util.*;

final class Request implements Runnable {
	

	
  final static String CRLF = "\r\n";
  SocketManager sockManager;
  public DataBaseControler base = DataBaseControler.getInstance();
  
  // Constructor
  public Request(SocketManager sockMan) throws Exception {
    sockManager = sockMan;
  }

  // Implement the run() method of the Runnable interface.
  public void run() {
    try {
    	System.out.println("run");
      processRequest();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void processRequest() throws Exception {
	  
	System.out.println("processRequest");
	
    int estado=0;
	Usuario user = new Usuario();
	
  while (estado!=4){
   
		switch (estado) {
	
		case 0://(USER)
		
			String  requestLine  = sockManager.Leer();// lee el user que le has pasado por socket desde el cliente
			
			if (! requestLine.equals("208Ok.adios")) {
				
				if (requestLine.equals("alta")){// estoy en la ventana altas bajas
					base.conectar();
					String nick= sockManager.Leer();
					String pass= sockManager.Leer();
					base.alta(nick,pass);
					base.desconectar();
				}
				else if (requestLine.equals("baja")){
					base.conectar();
					String nick= sockManager.Leer();
					String pass= sockManager.Leer();
					base.baja(nick,pass);
					base.desconectar();
						
				}else {//requestLine!=null---> contiene un nombre de usuario
					 base.conectar();
					 String respuesta = base.consultaUsuario(requestLine);
					 base.desconectar();
						if (respuesta.equals("401ERR.Usuario desconocido")){
							estado=0;
						}else if (respuesta.contains("200OK.Bienvenido")){
							String[] a= respuesta.split(":");
							user.setNick(a[1]); // me dan el nick separado por : por lo que lo saco con un split ("200OK.Bienvenido:"+nombre;)
							estado = 1;
						}else{//respuesta.contains("400ERR.Falta el nombre de usuario")
							estado = 0;
						}
					sockManager.Escribir(respuesta+'\n');					
					}
				  
	     	}else { // request = adios
	     		estado = 4;
	    	}
			
		break;

		case 1://(PASS)
			
			 requestLine  = sockManager.Leer();// lee el pasword que le has pasado por socket desde el cliente
		
			if (!requestLine.equals("adios")) {
				
				int pass = Integer.parseInt(requestLine);
				base.conectar();
				String respuesta = base.ConsultarPasword(user.getNick(), pass);
				base.desconectar();
				if (respuesta.equals("402 ERR Falta la clave")){ 
					estado = 0;
				}else if (respuesta.equals("401 ERR La clave es incorrecta")){
					System.out.println("no entra por pass incorrecta");
					estado = 0;
				}else if (respuesta.equals("201 OK Bienvenido al sistema")){
					estado = 2;
				}
				
				sockManager.Escribir(respuesta+'\n');
				
	    	}else if (requestLine.equals("adios")){
	    		estado=4;
	    	}
			
		break;

		case 2:  // ACTION
			 requestLine  = sockManager.Leer();// mandar la lista de la tabla principal
			 if (requestLine.contains("sacalista")){
				 String lista = sacarListado();
				 sockManager.Escribir(lista+'\n'); // mando la lista al cliente para que inicie la ventana
				
			 }
			
			 requestLine  = sockManager.Leer();// lee el comando (boton) que e has pasado por socket desde el cliente
			 
			 while (!requestLine.equals("adios")) { // queda en bucle para poder seguir haciendo cosas
				 System.out.println("esperando ");
				 
					if (requestLine.equals("activar")){
						String id = sockManager.Leer(); // id del sensor que voy a activar
						 base.conectar();
						 base.encenderSensor(id);
						 base.desconectar();
						 Thread.sleep(100);
						 base.conectar();
						 String listaActualizada= base.sacarlista();
						 base.desconectar();
						 sockManager.Escribir(listaActualizada+'\n'); //mando lista actualizada al cliente
						
					}else if (requestLine.equals("desactivar")){
						System.out.println("entroo");
						String id = sockManager.Leer(); // id del sensor que voy a desactivar
						 base.conectar();
						 base.apagarSensor(id);
						 base.desconectar();
						 Thread.sleep(100);
						 base.conectar();
						 String listaActualizada= base.sacarlista();
						 base.desconectar();
						 sockManager.Escribir(listaActualizada+'\n');
						
					}else if (requestLine.contains("buscar")){
						System.out.println("buscando");
							requestLine = sockManager.Leer(); // recivo que se sensor quiere buscar en la bd
							 base.conectar();
							 String lista=base.buscarSensor(requestLine);
							 base.desconectar();
							sockManager.Escribir(lista+'\n');
						
					}else if (requestLine.contains("imagen")){
						// ni idea de pasar de la bd a aqui una imagen...
						
						BufferedImage img = ImageIO.read(new File("src/Icons/placa.gif"));
					
						// sockManager.Escribir(img,);
					
					}else if (requestLine.contains("actuar")){
						requestLine = sockManager.Leer(); // recivo el id que quiero camn¡biar de accion
						base.conectar();
						base.cambiarEstado(requestLine);
						base.desconectar();
					}
			 }
			
		break;

		

		case 4:  // va a hacer conflicto con mi while (!4)
			sockManager.Escribir("208 OK.Adios."+'\n');
		break;
			
		default :
			sockManager.Escribir("Error, comando invalido."+'\n'); //?? igual hay que quitarlo
		break;
		} 
	}

     // Close streams and socket.
      sockManager.CerrarStreams();
    sockManager.CerrarSocket();
    
  }

 

/*
  private String sacarListado() throws SQLException, IOException, ClassNotFoundException { // cambia de lista a string
	base.conectar();
	String string = "";
	List<String> lista =base. sacarlista();
	string = lista. toString(); // cambio de lista a String
	string.substring(1, string.length()-2);
	 base.desconectar();
	 return string;
	   
	}
*/
  private String sacarListado() throws SQLException,IOException,ClassNotFoundException
  {
	  base.conectar();
	  String lista = base.sacarlista();
	  base.desconectar();
	  return lista;
  }


	   

//  
//  private void sacarBusqueda(String palabra) throws SQLException, IOException {
//	  List< Sensor> lista =base. sacarBusqueda(palabra);
//	  
//	int a= lista.size();
//	for (int i=0; i<a;a++){
//		Sensor v = lista.get(i);
//		
//		sockManager.Escribir("ELEM:"+i+1+":"+v.getDef()+" ; "+v.getFuncion()+" ; "+v.getEstado()+" ; "+v.getUltima_accion()+'\n');
//				
//	}
//	sockManager.Escribir("      ");
//	sockManager.Escribir("202 FINLISTA"+'\n');
//	
//}

private void sendBytes(FileInputStream fis) throws Exception {
    // Construct a 1K buffer to hold bytes on their way to the socket.
    byte[] buffer = new byte[1024];
    int bytes = 0;

    // Copy requested file into the socket's output stream.
    while ( (bytes = fis.read(buffer)) != -1) {
      sockManager.Escribir(buffer, bytes);
    }
  }

  private static String contentType(String fileName) {
       
    if (fileName.endsWith(".ram") || fileName.endsWith(".ra")) {
      return "audio/x-pn-realaudio";
    }
    return "application/octet-stream";
  }
  public static void main(String argv[]) throws Exception
	{
	  System.out.println("main");
	  ServerSocket s = new ServerSocket(2345);
	  SocketManager sockManager =  new SocketManager(s.accept());
	  System.out.println("socket manager creado");
	  Request r = new Request(sockManager);
	  System.out.println("ejecutando run");
	  r.run();
	  System.out.println();
	}
}
