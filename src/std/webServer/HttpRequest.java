package std.webServer;

import java.io.*;
import java.net.*;
import java.util.*;

import util.*;

final class HttpRequest implements Runnable {

  final static String CRLF = "\r\n";
  SocketManager sockManager;

  // Constructor
  public HttpRequest(SocketManager sockMan) throws Exception {
    sockManager = sockMan;
  }

  // Implement the run() method of the Runnable interface.
  public void run() {
    try {
      processRequest();
    }
    catch (Exception e) {
      System.out.println(e);
    }
  }

  private void processRequest() throws Exception {
	  
	 // lee la palabra que e has pasado por socket desde el cliente
	 String  requestLine  = sockManager.Leer();
    System.out.println("RequestLine: " + requestLine);
    int estado=0;
	String comando="";
	Data_base_controler base = new Data_base_controler();
	Usuario user = new Usuario();
  while (estado!=4){
   
		switch (estado) {
	
		case 0:
			if (comando.equals("User")) {
				sockManager.Escribir("Introduzca el nombre de usuario:");
				requestLine  = sockManager.Leer();
				if (requestLine!=null){
					sockManager.Escribir("400 ERR.Falta el nombre de usuario");
					estado=0;
				}else if (!base.consultaUsuario(requestLine)){
					sockManager.Escribir("401ERR.Usuario desconocido");
					estado=0;
				}else if (base.consultaUsuario(requestLine)){
					sockManager.Escribir("200 OK. bienvenido"+"requestLine");
					user.setNick(requestLine);
					estado = 2;
			    }     
	     	}else if (comando.equals("adios")){
	     		estado = 4;
	    	}else{
	    		estado=0;
	    	}
			
		break;

		case 1:
			if (comando.equals("Pass")) {
				
				sockManager.Escribir("Introduzca su pasword :");
				requestLine  = sockManager.Leer();
				int pass = Integer.parseInt(requestLine);
				
				if (requestLine==null){
					sockManager.Escribir("402 ERR.Falta la clave ");
					estado = 0;
				}else{
					if (base.ConsultarPasword(user.getNick(), pass)){
							sockManager.Escribir("201 OK.Bienvenido al sistema");
							estado=2;
							user.setContrasena(pass);
					}else if (!base.ConsultarPasword(user.getNick(), pass)){
							sockManager.Escribir("401 ERR.La clave es incorrecta");
							estado=1;
					}
				}
	    	}else if (comando.equals("adios")){
	    		estado=4;
	    	}
		break;

		case 2:
			if (comando.equals("On")) {
	 		}else if(comando.equals("Off")) {
	 		}else if(comando.equals("Accion")) {
			}else if(comando.equals("Listado")) {
			}else if(comando.equals("Buscar")) {
			}else if(comando.equals("Obtener_foto")) {
			}else if(comando.equals("Salir")) {
			}else {
				sockManager.Escribir("Error, comando invalido."); //?? igual hay que quitarlo
			}
		break;

		case 3:
		    if (comando.equals("Confirmar_accion")) {
			}else if(comando.equals("Rechazar_accion")) { 
			}
		break;

		case 4:            // va a hacer conflicto con mi while (!4)
			System.out.println("salir");
		break;
			
		default :
			sockManager.Escribir("Error, comando invalido."); //?? igual hay que quitarlo
		break;
		} 
	}

   
 
  
    
     // Close streams and socket.
      sockManager.CerrarStreams();
    sockManager.CerrarSocket();
    
  }

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
