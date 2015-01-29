package std.webServer;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;

import util.*;

final class Request implements Runnable {
	
  final static String CRLF = "\r\n";
  SocketManager sockManager;
  public DataBaseControler base = DataBaseControler.getInstance();
  
  ArrayList<Usuario> listaClientesConectados = new ArrayList<>();
  
  // Constructor
  public Request(SocketManager sockMan) throws Exception {
    sockManager = sockMan;
  }

  
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
	  int estado=0;
	  Usuario user = new Usuario();
	  Usuario c = new Usuario("Desconocido", "on");
	  listaClientesConectados.add(c);
	
	  
  while (true){
   
		switch (estado) {
	
		case 0://(USER)
			
			String  requestLine  = sockManager.Leer();// lee el user que le has pasado por socket desde el cliente
			
			if (!requestLine.equals("adios")) {
				
				if (requestLine.equals("alta")){// estoy en la ventana altas bajas
					base.conectar();
					String nick= sockManager.Leer();
					String pass= sockManager.Leer();
			    	String respuesta=base.alta(nick,pass);
					base.desconectar();
			
					sockManager.Escribir(respuesta+'\n');
				}
				else if (requestLine.equals("baja")){
					base.conectar();
					String nick= sockManager.Leer();
					String pass= sockManager.Leer();
					String respuesta=base.baja(nick,pass);
					base.desconectar();
					
					sockManager.Escribir(respuesta+'\n');
						
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
							
							int pos = listaClientesConectados.indexOf(c);
							Usuario aux = listaClientesConectados.get(pos);
							listaClientesConectados.remove(pos);
							aux.setNick(a[1]);
							listaClientesConectados.add(pos, aux);
							c = aux;
							//aqui actualizo
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
					base.conectar();
					base.conectarUsuario(user.getNick());
					base.desconectar();
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
			 while (true) { // queda en bucle para poder seguir haciendo cosas
				 requestLine  = sockManager.Leer();// lee el comando (boton) que e has pasado por socket desde el cliente
					if (requestLine.equals("activar")){
						String id = sockManager.Leer(); // id del sensor que voy a activar
						 base.conectar();
						 base.encenderSensor(id);
						 base.desconectar();
						 base.conectar();
						 String listaActualizada= base.sacarlista();
						 base.desconectar();
						 sockManager.Escribir(listaActualizada+'\n'); //mando lista actualizada al cliente
						
					}else if (requestLine.equals("desactivar")){
						String id = sockManager.Leer(); // id del sensor que voy a desactivar
						 base.conectar();
						 System.out.println("Request id:"+id);
						 base.apagarSensor(id);
						 base.desconectar();
						 base.conectar();
						 String listaActualizada= base.sacarlista();
						 base.desconectar();
						 sockManager.Escribir(listaActualizada+'\n');
						
					}else if (requestLine.contains("buscar")){
							requestLine = sockManager.Leer(); // recivo que se sensor quiere buscar en la bd
							 base.conectar();
							 String lista=base.buscarSensor(requestLine);
							 base.desconectar();
							sockManager.Escribir(lista+'\n');
						
					}else if (requestLine.contains("imagen")){
						// ni idea de pasar de la bd a aqui una imagen...
						System.out.println("imagen");
						requestLine = sockManager.Leer(); // recivo que se sensor quiere buscar en la bd
						 base.conectar();
					     String url = base.foto(requestLine);
						 base.desconectar();
						 sockManager.Escribir(url+'\n');
					
					}else if (requestLine.contains("actuar")){
						String id = sockManager.Leer(); // recivo el id que quiero cambiar de accion
						String parametro = sockManager.Leer();
						base.conectar();
						if (!parametro.equals("")){
						String respuesta = base.cambiarEstado(id,parametro);
						String lista = base.sacarlista();
						base.desconectar();
						sockManager.Escribir(respuesta+'\n');
						sockManager.Escribir(lista+'\n');
						}else{
							sockManager.Escribir("409 ERR Faltan datos."+'\n');
							base.conectar();
							String lista = base.sacarlista();
							base.desconectar();
							sockManager.Escribir(lista+'\n');
						}
					}else if (requestLine.equals("activarplaca")){
							String id = sockManager.Leer(); // id de la placa que voy a desactivar
							 base.conectar();
							 base.encenderPlaca(id);
							 base.desconectar();
							 base.conectar();
							 String listaActualizada= base.sacarlista();
							 base.desconectar();
							 sockManager.Escribir(listaActualizada+'\n');
							 
					}else if (requestLine.equals("desactivarplaca")){
						 String id = sockManager.Leer();  // id de la placa que voy a desactivar
						 base.conectar();
						 System.out.println("Request id:"+id);
						 base.apagarPlaca(id);
						 base.desconectar();
						 base.conectar();
						 String listaActualizada= base.sacarlista();
						 base.desconectar();
						 sockManager.Escribir(listaActualizada+'\n');
					
					}else if (requestLine.contains("salir")){
						estado=4;
						System.out.println("me piden que adios");
					}
			 }
		
		case 4: //salir 
			String nick=sockManager.Leer();
			if (nick.equals("nadie")) {
				System.out.println("nadie");
			}
			else {
				base.conectar();
				base.echarUsuario(nick);
				System.out.println("hasta luegooo");
				base.desconectar();
				VentanaControl.listaRequest.remove(this);
			}
			listaClientesConectados.remove(listaClientesConectados.indexOf(c));
			//aqui actualizo
			sockManager.CerrarStreams();
		    sockManager.CerrarSocket();
		  
		    
		break;
			
		default :
			sockManager.Escribir("Error, comando invalido."+'\n'); //?? igual hay que quitarlo
		break;
		} 
	}

  
  }

 

  private String sacarListado() throws SQLException,IOException,ClassNotFoundException
  {
	  base.conectar();
	  String lista = base.sacarlista();
	  base.desconectar();
	  return lista;
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
