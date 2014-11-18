package std.webServer;
import java.io.* ;
import java.net.* ;
import java.util.* ;

import util.*;

public final class WebServer
{
	public static void main(String argv[]) throws Exception
	{
		// Set the port number.
		int port = 6789; //(new Integer(argv[0])).intValue();

		ServerSocket wellcomeSocket = new ServerSocket(port);

		while (true)
		{
			//Socket conn = sock.accept();

			SocketManager sockManager = new SocketManager(wellcomeSocket.accept());

			HttpRequest request = new HttpRequest(sockManager);

			Thread thre = new Thread(request);

			thre.start();

//			int estado;
//			String comando="";
//
//			switch (estado) {
//			
//			case 0:
//				    if (comando.equals("User")) {
//					}else {
//					System.out.println("Error, inserte el usuario.");
//					}
//			break;
//
//			case 1:
//				    if (comando.equals("Pass")) {
//					}else {
//						System.out.println("Error, inserte la contraseña.");
//					}
//			break;
//
//			case 2:
//		  			if (comando.equals("On")) {
//			   		}else if(comando.equals("Off")) {
//				    }else if(comando.equals("Accion")) {
//					}else if(comando.equals("Listado")) {
//					}else if(comando.equals("Buscar")) {
//					}else if(comando.equals("Obtener_foto")) {
//					}else if(comando.equals("Salir")) {
//					}else {
//						System.out.println("Error, comando invalido.");
//					}
//			 break;
//
//			case 3:
//				    if (comando.equals("Confirmar_accion")) {
//					}else if(comando.equals("Rechazar_accion")) { 
//					}
//			break;
//
//			case 4:
//				    System.out.println("salir");
//			break;
//				
//			default :
//				System.out.println("Error, comando invalido");
//			break;
//			} 
		}
	}
}
