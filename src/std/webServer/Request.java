package std.webServer;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import util.*;

final class Request implements Runnable {

	final static String CRLF = "\r\n";
	SocketManager sockManager;
	public DataBaseControler base = DataBaseControler.getInstance();

	// Constructor
	public Request(SocketManager sockMan) throws Exception {
		sockManager = sockMan;
	}

	public void run() {
		try {
			processRequest();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void processRequest() throws Exception {
		int estado = 0;
		Usuario user = new Usuario();
		boolean sigo = true;

		while (sigo) {

			switch (estado) {

			case 0:// (USER)

				String requestLine = sockManager.Leer();// lee el user que le
														// has pasado por socket
														// desde el cliente

				if (!requestLine.equals("salir")) {

					if (requestLine.equals("alta")) {// estoy en la ventana
														// altas bajas
						base.conectar();
						String nick = sockManager.Leer();
						String pass = sockManager.Leer();
						String respuesta = base.alta(nick, pass);
						base.desconectar();

						sockManager.Escribir(respuesta + '\n');
					} else if (requestLine.equals("baja")) {
						base.conectar();
						String nick = sockManager.Leer();
						String pass = sockManager.Leer();
						String respuesta = base.baja(nick, pass);
						base.desconectar();

						sockManager.Escribir(respuesta + '\n');

					} else {// requestLine!=null---> contiene un nombre de
							// usuario
						base.conectar();
						String respuesta = base.consultaUsuario(requestLine);
						base.desconectar();
						if (respuesta.equals("401ERR.Usuario desconocido")) {
							estado = 0;
						} else if (respuesta.contains("200OK.Bienvenido")) {
							String[] a = respuesta.split(":");
							user.setNick(a[1]); // me dan el nick separado por :
												// por lo que lo saco con un
												// split
												// ("200OK.Bienvenido:"+nombre;)
							estado = 1;

						} else {// respuesta.contains("400ERR.Falta el nombre de usuario")
							estado = 0;
						}
						sockManager.Escribir(respuesta + '\n');
					}

				} else { // request = salir
					estado = 4;
				}

				break;

			case 1:// (PASS)

				requestLine = sockManager.Leer();// lee el pasword que le has
													// pasado por socket desde
													// el cliente

				if (!requestLine.equals("salir")) {

					int pass = Integer.parseInt(requestLine);
					base.conectar();
					String respuesta = base.ConsultarPasword(user.getNick(),
							pass);
					base.desconectar();
					if (respuesta.equals("402 ERR Falta la clave")) {
						estado = 0;
					} else if (respuesta
							.equals("401 ERR La clave es incorrecta")) {
						System.out.println("no entra por pass incorrecta");
						estado = 0;
					} else if (respuesta.equals("201 OK Bienvenido al sistema")) {
						base.conectar();
						base.conectarUsuario(user.getNick());
						base.desconectar();
						VentanaControl.listanombres.add(user.getNick());// inserto
																		// el
																		// nombre
																		// del
																		// usuario
																		// para
																		// luego
																		// buscar
																		// su
																		// request
						estado = 2;
					}

					sockManager.Escribir(respuesta + '\n');

				} else if (requestLine.equals("salir")) {
					estado = 4;
				}

				break;

			case 2: // ACTION
				requestLine = sockManager.Leer();// mandar la lista de la tabla
													// principal
				if (requestLine.contains("sacalista")) {
					String lista = sacarListado();
					sockManager.Escribir(lista + '\n'); // mando la lista al
														// cliente para que
														// inicie la ventana

				}
				boolean sigo2 = true;
				while (sigo) { // queda en bucle para poder seguir haciendo
								// cosas
					requestLine = sockManager.Leer();// lee el comando (boton)
														// que e has pasado por
														// socket desde el
														// cliente
					if (requestLine.equals("activar")) {
						String id = sockManager.Leer(); // id del sensor que voy
														// a activar
						base.conectar();
						String resp = base.encenderSensor(id);
						base.desconectar();
						base.conectar();
						String listaActualizada = base.sacarlista();
						base.desconectar();
						sockManager.Escribir(resp + '\n');
						sockManager.Escribir(listaActualizada + '\n'); // mando
																		// lista
																		// actualizada
																		// al
																		// cliente

					} else if (requestLine.equals("desactivar")) {
						String id = sockManager.Leer(); // id del sensor que voy
														// a desactivar
						base.conectar();
						String resp = base.apagarSensor(id);
						base.desconectar();
						base.conectar();
						String listaActualizada = base.sacarlista();
						base.desconectar();
						sockManager.Escribir(resp + '\n');
						sockManager.Escribir(listaActualizada + '\n');

					} else if (requestLine.contains("buscar")) {
						requestLine = sockManager.Leer(); // recivo que se
															// sensor quiere
															// buscar en la bd
						base.conectar();
						String lista = base.buscarSensor(requestLine);
						base.desconectar();
						sockManager.Escribir(lista + '\n');

					} else if (requestLine.contains("imagen")) {
						// ni idea de pasar de la bd a aqui una imagen...
						// ni idea de pasar de la bd a aqui una imagen...
						System.out.println("imagen");
						requestLine = sockManager.Leer(); // recivo que se
															// sensor quiere
															// buscar en la bd
						base.conectar();
						byte[] bfoto = base.foto(requestLine);
						base.desconectar();
						enviarFoto(bfoto);

					} else if (requestLine.contains("actuar")) {
						String id = sockManager.Leer(); // recivo el id que
														// quiero cambiar de
														// accion
						String parametro = sockManager.Leer();
						base.conectar();
						if (!parametro.equals("")) {
							String respuesta = base
									.cambiarEstado(id, parametro);
							String lista = base.sacarlista();
							base.desconectar();
							sockManager.Escribir(respuesta + '\n');
							sockManager.Escribir(lista + '\n');
						} else {
							sockManager
									.Escribir("409 ERR Faltan datos." + '\n');
							base.conectar();
							String lista = base.sacarlista();
							base.desconectar();
							sockManager.Escribir(lista + '\n');
						}
					} else if (requestLine.equals("activarplaca")) {
						String id = sockManager.Leer(); // id de la placa que
														// voy a desactivar
						base.conectar();
						String respuesta = base.encenderPlaca(id);
						base.desconectar();
						base.conectar();
						String listaActualizada = base.sacarlista();
						base.desconectar();
						sockManager.Escribir(respuesta + '\n');
						sockManager.Escribir(listaActualizada + '\n');

					} else if (requestLine.equals("desactivarplaca")) {
						String id = sockManager.Leer(); // id de la placa que
														// voy a desactivar
						base.conectar();
						String respuesta = base.apagarPlaca(id);
						base.desconectar();
						base.conectar();
						String listaActualizada = base.sacarlista();
						base.desconectar();
						sockManager.Escribir(respuesta + '\n');
						sockManager.Escribir(listaActualizada + '\n');

					} else if (requestLine.equals("salir")) {
						estado = 4;
						sigo = false;
					}
				}

			case 4: // salir

				String nick = sockManager.Leer();
				if (nick.equals("nadie")) {
				} else {
					base.conectar();
					base.echarUsuario(nick);
					base.desconectar();
					this.salir(user.getNick());
				}

				sockManager.CerrarStreams();
				sockManager.CerrarSocket();

				break;

			default:
				sockManager.Escribir("Error, comando invalido." + '\n'); // ??
																			// igual
																			// hay
																			// que
																			// quitarlo
				break;
			}
		}

	}

	private void salir(String nick) {
		// TODO Auto-generated method stub

		if (nick == "nadie") {

		} else {

			int pos = 0;
			for (int i = 0; i < VentanaControl.listanombres.size(); i++) {
				if (nick.equals(VentanaControl.listanombres.get(i)))
					pos = i;
			}
			VentanaControl.listaRequest.remove(pos);
			VentanaControl.listanombres.remove(pos);
		}
	}

	private String sacarListado() throws SQLException, IOException,
			ClassNotFoundException {
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
		while ((bytes = fis.read(buffer)) != -1) {
			sockManager.Escribir(buffer, bytes);
		}
	}

	private void enviarFoto(byte[] bfoto) {
		try {
			
			int bytes = bfoto.length;

			if (bytes > 0)
				sockManager.Escribir(bfoto, bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
