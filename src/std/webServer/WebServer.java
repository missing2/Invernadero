package std.webServer;
import java.net.* ;


import util.*;

public final class WebServer
{
	public static int actuales= 0;
	public static int maximas = 2;
	public static void main(String argv[]) throws Exception
	{
		
		int port = 2345; 

		ServerSocket wellcomeSocket = new ServerSocket(port);

		while (true)
		{
			
			SocketManager sockManager = new SocketManager(wellcomeSocket.accept());
			if (actuales<maximas) {// control de numero de nuevos clientes
				
				Request request = new Request(sockManager);
	
				Thread thre = new Thread(request); 
	
				thre.start();
				actuales++;
			}else {
				wellcomeSocket.close();
				System.out.println("servidor completo");
			}
		  }
		}
   }