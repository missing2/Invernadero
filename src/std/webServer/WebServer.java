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
		  }
		}
   }