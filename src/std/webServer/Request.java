package std.webServer;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.*;

import util.*;

final class Request implements Runnable {
	

	
  final static String CRLF = "\r\n";
  SocketManager sockManager;
  public Data_base_controler base = new Data_base_controler();
  
  // Constructor
  public Request(SocketManager sockMan) throws Exception {
    sockManager = sockMan;
  }

  // Implement the run() method of the Runnable interface.
  public void run() {
    try {
      processRequest();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void processRequest() throws Exception {
	  
	
	String comando ="";
    int estado=0;
	Usuario user = new Usuario();
	
  while (estado!=4){
   
		switch (estado) {
	
		case 0://(USER)
			String  requestLine  = sockManager.Leer();// lee el user que le has pasado por socket desde el cliente
			
			if (! requestLine.equals("adios")) {
				
				if (requestLine.equals("")){// si esta vacia
					sockManager.Escribir("400 ERR.Falta el nombre de usuario"+'\n');
					System.out.println("falta usuario");
					estado=0;
					
				}else{//requestLine!=null)
					base.conectar();
					 if (!base.consultaUsuario(requestLine)){
						sockManager.Escribir("401ERR.Usuario desconocido"+'\n');
						System.out.println("user desconocido");
						estado=0;
					}else {//(base.consultaUsuario(requestLine))
						sockManager.Escribir("200 OK. bienvenido"+"requestLine"+'\n');
						System.out.println("user correncto");
						user.setNick(requestLine);
						estado = 1;					
					}
				}   
	     	}else { // request = adios
	     		estado = 4;
	    	}
			
		break;

		case 1://(PASS)
			 requestLine  = sockManager.Leer();// lee el pasword que le has pasado por socket desde el cliente
			 
			if (!requestLine.equals("adios")) {
				
				requestLine  = sockManager.Leer();//pasword :
				int pass = Integer.parseInt(requestLine);
				
				if (requestLine.equals("")){ // si esta vacia la pasword...
					sockManager.Escribir("402 ERR.Falta la clave "+'\n');
					estado = 0;
					System.out.println("falta clave");
				}else{// !requestLine.equals(""))
					base.conectar();
					if (base.ConsultarPasword(user.getNick(), pass)){
							sockManager.Escribir("201 OK.Bienvenido al sistema"+user.getNick()+'\n');
							estado=2;
							System.out.println("contrase�a bien");
							
							user.setContrasena(pass);
					}else if (!base.ConsultarPasword(user.getNick(), pass)){
							sockManager.Escribir("401 ERR.La clave es incorrecta"+'\n');
							System.out.println("clave erronea");
							estado=1;
					}
					base.desconectar();
				}
	    	}else if (requestLine.equals("adios")){
	    		estado=4;
	    	}
		break;

		case 2:  // ACTION
			 requestLine  = sockManager.Leer();// mandar la lista de la tabla principal
			 if (requestLine.contains("sacalista")){
				 String lista = sacarListado ();
				 sockManager.Escribir(lista+'\n'); // mando la lista al cliente
			 }
			
			 requestLine  = sockManager.Leer();// lee el comando que e has pasado por socket desde el cliente
			 
			 while (!requestLine.equals("adios")) { // queda en bucle para poder seguir haciendo cosas
					if (requestLine.contains("activar")){
						
					}else if (requestLine.contains("buscar")){
						
					}if (requestLine.contains("imagen")){
						
					}if (requestLine.contains("listar")){
						
					}
			 }
		break;

		case 3:
		    if (comando.equals("Confirmar_accion")) {
		    	//--------------------------------
			}else if(comando.equals("Rechazar_accion")) { 
				sockManager.Escribir("207 OK Acci�n cancelada"+'\n');
				estado=2;
			}
		break;

		case 4:            // va a hacer conflicto con mi while (!4)
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

 

private String sacarListado() throws SQLException, IOException, ClassNotFoundException { // cambia de lista a string
	base.conectar();
	String string = "";
	 List<String> lista =base. sacarlista();
	   string = lista. toString(); // cambio de lista a String
	   base.desconectar();
	   return string;
	   
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
}
