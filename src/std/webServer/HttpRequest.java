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
    String requestLine = sockManager.Leer();
    System.out.println("RequestLine: " + requestLine);

    // Display the request line.
    //System.out.println();
    //System.out.println(requestLine);

    // Extract the filename from the request line.
   //  StringTokenizer tokens = new StringTokenizer(requestLine);
   //  tokens.nextToken(); // skip over the method, which should be "GET"
    //System.out.println("Next Token: "+tokens.nextToken());
    
    // cambia la palabra metida a mayusculas y la envia a traves del socket
    sockManager.Escribir(requestLine.toUpperCase()); 
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
