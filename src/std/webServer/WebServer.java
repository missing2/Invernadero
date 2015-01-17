package std.webServer;
import java.net.* ;


import util.*;

public final class WebServer
{
	public static void main(String argv[]) throws Exception
	{
		
		int port = 2345; 

		ServerSocket wellcomeSocket = new ServerSocket(port);

		while (true)
		{
			
			SocketManager sockManager = new SocketManager(wellcomeSocket.accept());

			Request request = new Request(sockManager);

			Thread thre = new Thread(request); 

			thre.start();
		  }
		}
   }