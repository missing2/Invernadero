package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Data_base_controler {
	private static Data_base_controler instance = null;
	private Connection conn;
			
	public static Data_base_controler getInstance() {
		if (instance == null)
			instance = new Data_base_controler();
		return instance;
	}

	//Método para cargar el driver JDBC y conectar la BD
	public void conectar() throws ClassNotFoundException, SQLException{
		
		//Cargo el driver JDBC
		Class.forName("org.sqlite.JDBC");
		
		//Creo la conexion con la BD
		conn = DriverManager.getConnection("jdbc:sqlite:Data_base/ProyectoRedesBds.sqlite"); // esta bien??
	}
	
	//Método para cerrar la conexion de la BD
	public void desconectar() throws SQLException {
	conn.close();
	}

	
	
     /**
     * Método para CONSULTAR un EMPLEADO por Nombre de empleado (De la lista)
     * @param nombre
     * @return boolean
     * @throws SQLException
     */
    public boolean consultaUsuario(String nombre) throws SQLException {
    
    	boolean existe=false;
    	
		Statement st = conn.createStatement(); 
		
		ResultSet rs2 = st.executeQuery("Select * from User Where User = "+nombre+"");                                                                                                                                                     // estado civil es estado, no string
	 	if (rs2!=null)
	 		existe=true;
	 		
		rs2.close();
		return existe;
	}
     
	 		/**
	 		 * Método para CONSULTAR la contraseña
	 		 * @param nombre
	 		 * @param pass
	 		 * @return boolean
	 		 * @throws SQLException
	 		 */
	 		public boolean ConsultarPasword(String nombre,int pass)throws SQLException{
	 				 			
	 			boolean existe=false;
	 	    	
	 			Statement st = conn.createStatement(); 
	 			
	 			ResultSet rs2 = st.executeQuery("Select * from User Where User = "+nombre+"and Where contrasena='"+pass+"'");                                                                                                                                                     // estado civil es estado, no string
	 		 	if (rs2!=null)
	 		 		existe=true;
	 		 		
	 			rs2.close();
	 			return existe;
	 		  }

			public boolean ConsultarIdVariable(int id) throws SQLException {
				boolean existe = false;
				Statement st = conn.createStatement();
				ResultSet rs2 = st.executeQuery("Select * from variable Where id_variable = '"+id+"'");
				return existe;
			}

			public String consultarEstadoVariable(int id) throws SQLException {
				String estado;
				Statement st = conn.createStatement();
				ResultSet rs2 = st.executeQuery("Select estado_variable from variable Where id_variable = '"+id+"'");
				estado=rs2.toString();
				return estado;
			}
}
	 	
	 	
	 	