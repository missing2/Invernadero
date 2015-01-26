package std.webServer;
import java.io.IOException;
import java.net.* ;

import util.*;

public final class WebServer extends Thread
{
	public static int actuales= 0;
	public static int maximas = 2;
	int port = 2345; 
	ServerSocket wellcomeSocket;
	
	public void run ()
	{
		
		try {
			wellcomeSocket = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (true)
		{
			
			SocketManager sockManager = null;
			try {
				sockManager = new SocketManager(wellcomeSocket.accept());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (actuales<maximas) {// control de numero de nuevos clientes
				
				Request request = null;
				try {
					request = new Request(sockManager);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				Thread thre = new Thread(request); 
	
				thre.start();
				actuales++;
			}else {
				try {
					wellcomeSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("servidor completo");
			}
		  }
		}
   }