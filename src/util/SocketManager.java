package util;
import java.net.*;
import java.io.*;


public class SocketManager {
    private Socket mySocket;

    private DataOutputStream bufferEscritura;
    private BufferedReader bufferLectura;

    public SocketManager(Socket sock) throws IOException {
        this.mySocket = sock;
        InicializaStreams();
       
    }

    /**
     * @param address InetAddress
     * @param port int numero de puerto
     * @throws IOException
     */
    public SocketManager(InetAddress address, int port) throws IOException {
        mySocket = new Socket(address, port);
        InicializaStreams();
        
    }

    /**
     * @param host String nombre del servidor al que se conecta
     * @param port int puerto de conexion
     * @throws IOException
     */
    public SocketManager(String host, int port) throws IOException {
        mySocket = new Socket(host, port);
        InicializaStreams();
       
    }

    /**
     * Inicialización de los bufferes de lectura y escritura del socket
     * @throws IOException
     */
    public void InicializaStreams() throws IOException {
        bufferEscritura = new DataOutputStream(mySocket.getOutputStream());
        bufferLectura = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
        System.out.println("canales creados");
    }

    public void CerrarStreams() throws IOException {
        bufferEscritura.close();
        bufferLectura.close();
        System.out.println("canales cerrados");
    }

    public void CerrarSocket() throws IOException {
        mySocket.close();
    }

    /**
     * @return String
     * @throws IOException
     * Metodos de entrada salida del los sockets
     */
    public String Leer() throws IOException {
        return (bufferLectura.readLine());
    }

    public void Escribir(String contenido) throws IOException {
        bufferEscritura.writeBytes(contenido);
    }

    public void Escribir(byte[] buffer, int bytes) throws IOException {
        bufferEscritura.write(buffer, 0, bytes);
    }
    public void EscribirBytes(byte[] buffer, int bytes) throws IOException{
    	
    	if (bytes< 0)
			throw new IllegalArgumentException("Negative length not allowed");
		if (0 >= buffer.length)
			throw new IndexOutOfBoundsException("Out of bounds: " + 0);
    	
    	bufferEscritura = new DataOutputStream(mySocket.getOutputStream());
    	bufferEscritura.writeInt(bytes);
    	if(bytes > 0)
    		bufferEscritura.write(buffer, 0, bytes);
    	
    }
    public byte[] LeerBytes() throws IOException{
    	
		DataInputStream dis = new DataInputStream(mySocket.getInputStream());
	

		int len = dis.readInt();
		byte[] data = new byte[len];
		System.out.println("len "+len);
		if (len > 0) {
			System.out.println("entro en el if");
			dis.readFully(data);
			System.out.println("data "+ data);
		}
		System.out.println(data);
		return data;
    	
    }
}
