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
      System.out.println(e);
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
					if (base.ConsultarPasword(user.getNick(), pass)){
							sockManager.Escribir("201 OK.Bienvenido al sistema"+user.getNick()+'\n');
							estado=2;
							System.out.println("contraseña bien");
							user.setContrasena(pass);
					}else if (!base.ConsultarPasword(user.getNick(), pass)){
							sockManager.Escribir("401 ERR.La clave es incorrecta"+'\n');
							System.out.println("clave erronea");
							estado=1;
					}
				}
	    	}else if (requestLine.equals("adios")){
	    		estado=4;
	    	}
		break;

		case 2:  // ACTION
			sendBytes();
			 requestLine  = sockManager.Leer();// lee el comando que e has pasado por socket desde el cliente
			 if (!requestLine.equals("adios")) {
					
				 if (requestLine.equals ("aaaaaa")){
				sockManager.Escribir("Insertar id Placa"+'\n');
				requestLine  = sockManager.Leer();
				String idp = requestLine.toString();
				
				sockManager.Escribir("Insertar definicion de la variable"+'\n');
				requestLine  = sockManager.Leer();
				String defv = requestLine.toString();
				
				if (base.ConsultarIdVariable(idp,defv)){
					if (base.consultarEstadoVariable(idp,defv).equals("off")){
						sockManager.Escribir("203 OK.Control de variable activo"+'\n');
						base.encenderVariable(idp,defv);
					}else if (base.consultarEstadoVariable(idp,defv).equals("on")) {
						sockManager.Escribir("404ERR."+defv+" en estado ON"+'\n');
					}
				}
				}else{
					sockManager.Escribir("405 ERR."+1+" no existe"+'\n');
				}
				
	 		}else if(comando.equals("Off")) {
	 			sockManager.Escribir("Insertar id Placa"+'\n');
				requestLine  = sockManager.Leer();
				String idp = requestLine.toString();
				sockManager.Escribir("Insertar id variable"+'\n');
				requestLine  = sockManager.Leer();
				String defv = requestLine.toString();
				
				if (base.ConsultarIdVariable(idp,defv)){
					if (base.consultarEstadoVariable(idp,defv).equals("on")){
						sockManager.Escribir("203 OK.Control de variable activo"+'\n');
						base.apagarVariable(idp,defv);
					}else if (base.consultarEstadoVariable(idp,defv).equals("off")) {
						sockManager.Escribir("404ERR."+defv+" en estado OFF"+'\n');
					}
					
				}else{
					sockManager.Escribir("405 ERR."+defv+" no existe"+'\n');
				}
	 		}else if(comando.equals("Accion")) {
	 			//---------------------------
			}else if(comando.equals("Listado")) {
				sacarListado();       //se puede quedar en bucle??
				estado=2;
			}else if(comando.equals("Buscar")) {
				sockManager.Escribir("Insertar lo que deseas buscar"+'\n');
				requestLine  = sockManager.Leer();
				String a = requestLine.toString();
				sacarBusqueda(a);       //se puede quedar en bucle??
				estado=2;
			}else if(comando.equals("Obtener_foto")) {
				//--------------------------
			}else if(comando.equals("Salir")) {
				estado=4;
			}else {
				sockManager.Escribir("Error, comando invalido."+'\n'); //?? igual hay que quitarlo
			}
		break;

		case 3:
		    if (comando.equals("Confirmar_accion")) {
		    	//--------------------------------
			}else if(comando.equals("Rechazar_accion")) { 
				sockManager.Escribir("207 OK Acción cancelada"+'\n');
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

  private void sacarListado() throws SQLException, IOException {
	  List< Sensor> lista =base. sacarlista();
	  
	int a= lista.size();
	for (int i=0; i<a;a++){
		Sensor v = lista.get(i);
		
		sockManager.Escribir("ELEM:"+i+1+":"+v.getDef()+" ; "+v.getFuncion()+" ; "+v.getEstado()+" ; "+v.getUltima_accion()+'\n');
				
	}
	sockManager.Escribir("      "+'\n');
	sockManager.Escribir("202 FINLISTA"+'\n');
	
}
  
  private void sacarBusqueda(String palabra) throws SQLException, IOException {
	  List< Sensor> lista =base. sacarBusqueda(palabra);
	  
	int a= lista.size();
	for (int i=0; i<a;a++){
		Sensor v = lista.get(i);
		
		sockManager.Escribir("ELEM:"+i+1+":"+v.getDef()+" ; "+v.getFuncion()+" ; "+v.getEstado()+" ; "+v.getUltima_accion()+'\n');
				
	}
	sockManager.Escribir("      ");
	sockManager.Escribir("202 FINLISTA"+'\n');
	
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
