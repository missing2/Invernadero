package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

public class DataBaseControler {
	private static DataBaseControler instance = null;
	private Connection conn;
			
	public static DataBaseControler getInstance() {
		if (instance == null)
			instance = new DataBaseControler();
		return instance;
	}

	//Método para cargar el driver JDBC y conectar la BD
	public void conectar() throws ClassNotFoundException, SQLException{
		
		//Cargo el driver JDBC
		Class.forName("org.sqlite.JDBC");
		
		//Creo la conexion con la BD
		conn = DriverManager.getConnection("jdbc:sqlite:res/ProyectoRedesBds.s3db");
		System.out.println("connection done!");	 
	}
	
	//Método para cerrar la conexion de la BD
	public void desconectar() throws SQLException {
	conn.close();
	}
	
	public DataBaseControler()
	 {
		try {
		 conectar();
		} catch (ClassNotFoundException e) {
		 e.printStackTrace();
	 	} catch (SQLException e) {
		 e.printStackTrace();
	 	}
	 }
	
	//___________________Altas/bajas/loggin_____________________________//
	
	public String alta(String nick,String pass) throws SQLException{
		String respuesta;
		Statement st = conn.createStatement();
		
		if (!nick.equals("")&& !pass.equals("")){
			st.execute("Insert into User values('"+nick+"','"+pass+"','"+"offline"+"');");
			respuesta = "211.OK Usuario insertado correctamente";
		}else{
			respuesta = "411.ERR Faltan datos";
		}
		
		st.close();
		return respuesta;
	}
	public String  baja(String nick,String pass) throws SQLException{
		String respuesta;
		Statement st = conn.createStatement();
		if (!nick.equals("")&& !pass.equals("")){
			st.execute("Delete from User where User ='"+nick+"' and contrasena ='"+pass+"';");
			respuesta = "210.OK Usuario eliminado correctamente";
			
		}else{
			respuesta = "410.ERR Faltan datos";
		}
		st.close();
		return respuesta;
	}

    public String consultaUsuario(String nombre) throws SQLException {
    	
    	String respuesta="401ERR.Usuario desconocido";
    	
    	if (nombre.isEmpty()){
    		respuesta= "400ERR.Falta el nombre de usuario";
    	}else{
    		Statement st = conn.createStatement(); 
    		ResultSet rs2 = st.executeQuery("Select * from User;"); 
    		
    		while (rs2.next()){
	    		if (rs2.getString("User").equals(nombre)){ 
	    			respuesta = "200OK.Bienvenido:"+nombre;	    		
	    			rs2.close();
	    		}
    		}
    	}
    	return respuesta;
    }
	 		
	public String ConsultarPasword(String nombre,int pass)throws SQLException{
			
		String existe="";
		Statement st = conn.createStatement();
		
    	if (!Integer.toString(pass).isEmpty()){// si la clave no esta vacia...
    		 
			ResultSet rs2 = st.executeQuery("SELECT * from User WHERE User = '"+nombre+"';");  
			int contra = rs2.getInt("contrasena");
		 	if (contra==pass){
		 		existe="201 OK Bienvenido al sistema";
		 	}else		 	
		 		existe="401 ERR La clave es incorrecta";
		 		rs2.close();
	    }else
			existe="402 ERR Falta la clave";
    	
    	st.close();
    	return existe;
	  }
	
//___________________________________VENTANA ACCION________________________________________//
	/**
     * metodo que retorna un string con todos los sensores de la bd
     * Sensores separados por ,
     * atributos separados por ;
     */
	public String sacarlista() throws SQLException {
													
		Statement st = conn.createStatement();
		
		String temp;
		String fin="";
		
		ResultSet rs2 =  st.executeQuery("Select * from Sensor;");
		int conta =1;
		while (rs2.next()) {
			Sensor a = new Sensor(rs2.getString("id_placa"),rs2.getString("id_sensor"),rs2.getString("def_variable"),
					rs2.getString("func_principal"),rs2.getString("estado"),rs2.getString("ultima_accion"));
			
			temp = ("ELEM:"+""+conta+";"+a.getId_placa()+";"+a.getId_sensor()+";"+a.getDef()+";"+a.getFuncion_principal()+";"+a.getEstado()+";"+a.getUltima_accion());
			conta++;
			fin=fin+temp+",";
			
		}
		rs2.close();
		st.close();
		return fin;
		
		
	}

	
	public String buscarSensor(String requestLine) throws SQLException {
		String temp="";
		String fin="";
		
		String params[] = requestLine.split(",");// separo los dos parametros que me pasan desde el client
		String parametro=params[1] ;
		String colum=params[0] ;
		
		 // adecuo las consultas especiales que me pasan, a consultas adaptadas a sql
		if (parametro.contains("*")){
			parametro.replace('*','%');
		} 
		else if(parametro.contains("?")){
			parametro.replace('?','_');
			parametro=parametro+"__";// añado 2 mas parq que sumen 3x_
		} 
		else {
			// consulta normal sin caracteres raros
		}
		
 		Statement stat = conn.createStatement();
 		
 		ResultSet rs2 = stat.executeQuery("Select * from Sensor where "+colum+" like '"+parametro+"';"); // columna en la que quiero buscar y parametro que quiero buscar
 		int conta =1; 
 		
		while (rs2.next()) {
			Sensor a = new Sensor(rs2.getString("id_placa"),rs2.getString("id_sensor"),rs2.getString("def_variable"),
					rs2.getString("func_principal"),rs2.getString("estado"),rs2.getString("ultima_accion"));
			
			temp = ("ELEM:"+""+conta+";"+a.getId_placa()+";"+a.getId_sensor()+";"+a.getDef()+";"+a.getFuncion_principal()+";"+a.getEstado()+";"+a.getUltima_accion());
			conta++;
			fin=fin+temp+",";
			
 			if (!fin.contains(temp)){ // si no esta insertado ya por la anterior consulta
 				fin=fin+temp+",";
 	 			}
 	 		}
 	
 		stat.close();
 		
 		rs2.close();

		return fin;
	}
	
	

	public void encenderSensor(String ids) throws SQLException {
		
		Statement st = conn.createStatement();
		System.out.println("me pasan el id sensor para encenderlo: "+ids);
		String sql = "UPDATE Sensor SET estado ='"+"on"+"' WHERE id_sensor='"+ids+"'";
		st.executeUpdate(sql);
		System.out.println("Hago el update");
 		st.close();
 		
	}
	public void apagarSensor(String ids) throws SQLException {
		
		Statement st = conn.createStatement();
		System.out.println("me pasan el id sensor para apagarlo: "+ids);
		String sql = "UPDATE Sensor SET estado ='"+"off"+"' WHERE id_sensor='"+ids+"'";
		st.executeUpdate(sql);
		System.out.println("Hago el update");
		st.close();
	}
	
	public void encenderPlaca(String id) throws SQLException {
		// TODO Auto-generated method stub
		Statement st = conn.createStatement();
		System.out.println("me pasan el id placa para apagarlo: "+id);
		String sql = "UPDATE Placa SET estado_placa ='"+"on"+"' WHERE id_placa='"+id+"'";
		st.executeUpdate(sql);
		System.out.println("Hago el update");
		st.close();
	}

	public void apagarPlaca(String id) throws SQLException {
		// TODO Auto-generated method stub
		Statement st = conn.createStatement();
		System.out.println("me pasan el id placa para apagarlo: "+id);
		String sql = "UPDATE Placa SET estado_placa ='"+"off"+"' WHERE id_placa='"+id+"'";
		st.executeUpdate(sql);
		System.out.println("Hago el update");
		st.close();
	}


	public String cambiarEstado(String id, String parametro) throws SQLException { // depurarlo y mirar consultas...
		// TODO Auto-generated method stub
		
		Statement st = conn.createStatement();
		Statement st2 = conn.createStatement();
		System.out.println("id del sensor que va a actuar: "+id);
		String respuesta="";
		ResultSet rs2 = st.executeQuery ( "Select * from Sensor WHERE id_sensor ='"+id+"';");// Saco la info del sensor que quieren que cambie
		ResultSet rs = st2.executeQuery("Select * from Placa  WHERE id_placa = '"+rs2.getString("id_placa")+"';");
		System.out.println("------"+rs2.getString("id_placa"));
		Sensor a = new Sensor(rs2.getString("id_placa"),rs2.getString("id_sensor"),rs2.getString("def_variable"),
				rs2.getString("ultima_accion"),rs2.getString("estado"),rs2.getString("func_principal"));
		
		Placa p = new Placa(rs.getString("id_placa"), rs.getString("estado_placa"), rs.getString("foto"));
  		 
//		st.executeUpdate("UPDATE Sensor SET ultima_accion ='"+parametro+"' WHERE id_sensor='"+id+"';");
		if(a.getEstado().equals("on") && p.getEstado_placa().equals("on")){

			if ( a.getFuncion_principal().equals("Regulación climatización")){
				
				if(a.getUltima_accion().equals("bajar calefaccion") && parametro.equals("subir")){
					st.executeUpdate("UPDATE Sensor SET ultima_accion ='"+"subir calefaccion"+"' WHERE id_sensor='"+id+"';");
					respuesta = "206 OK Acción sobre el sensor confirmada";
				}else if(a.getUltima_accion().equals("subir calefaccion") && parametro.equals("bajar")){
					st.executeUpdate("UPDATE Sensor SET ultima_accion ='"+"bajar calefaccion"+"' WHERE id_sensor='"+id+"';");
					respuesta = "206 OK Acción sobre el sensor confirmada";
				}else if(a.getUltima_accion().equals("encender calefaccion") && parametro.equals("apagar")){
					st.executeUpdate("UPDATE Sensor SET ultima_accion ='"+"apagar calefaccion"+"' WHERE id_sensor='"+id+"';");
					respuesta = "206 OK Acción sobre el sensor confirmada";
				}else if(a.getUltima_accion().equals("apagar calefaccion") && parametro.equals("encender")){
					st.executeUpdate("UPDATE Sensor SET ultima_accion ='"+"encender calefaccion"+"' WHERE id_sensor='"+id+"';");
					respuesta = "206 OK Acción sobre el sensor confirmada";
				}else
					respuesta = "407 ERROR accion ya ejecutada";
				
			}else if(a.getFuncion_principal().equals("Regulación de Luminosidad")){
				
				if(a.getUltima_accion().equals("encender luz")&& parametro.equals("apagar")){
					st.executeUpdate("UPDATE Sensor SET ultima_accion ='"+"apagar luz"+"' WHERE id_sensor='"+id+"';");
					respuesta = "206 OK Acción sobre el sensor confirmada";
				}else if(a.getUltima_accion().equals("apagar luz")&& parametro.equals("encender")){
					st.executeUpdate("UPDATE Sensor SET ultima_accion ='"+"encender luz"+"' WHERE id_sensor='"+id+"';");
					respuesta = "206 OK Acción sobre el sensor confirmada";
				}else if(a.getUltima_accion().equals("subir intensidad")&& parametro.equals("bajar")){
					st.executeUpdate("UPDATE Sensor SET ultima_accion ='"+"bajar intensidad"+"' WHERE id_sensor='"+id+"';");
					respuesta = "206 OK Acción sobre el sensor confirmada";
				}else if(a.getUltima_accion().equals("bajar intensidad") && parametro.equals("subir")){
					st.executeUpdate("UPDATE Sensor SET ultima_accion ='"+"subir intensidad"+"' WHERE id_sensor='"+id+"';");
					respuesta = "206 OK Acción sobre el sensor confirmada";
				}else
					respuesta = "407 ERROR accion ya ejecutada";
				
			}else if(a.getFuncion_principal().equals("Sistema de riego")){
				
				if(a.getUltima_accion().equals("Activar sistema de riego")&& parametro.equals("apagar")){
					st.executeUpdate("UPDATE Sensor SET ultima_accion ='"+"Desactivar sistema de riego"+"' WHERE id_sensor='"+id+"';");
					respuesta = "206 OK Acción sobre el sensor confirmada";
				}else if(a.getUltima_accion().equals("Desactivar sistema de riego")&& parametro.equals("encender")){
					st.executeUpdate("UPDATE Sensor SET ultima_accion ='"+"Activar sistema de riego"+"' WHERE id_sensor='"+id+"';");
					respuesta = "206 OK Acción sobre el sensor confirmada";
				}else if(a.getUltima_accion().equals("Aumentar presion de riego")&& parametro.equals("bajar")){
					st.executeUpdate("UPDATE Sensor SET ultima_accion ='"+"Bajar presion de riego"+"' WHERE id_sensor='"+id+"';");
					respuesta = "206 OK Acción sobre el sensor confirmada";
				}else if(a.getUltima_accion().equals("Bajar presion de riego")&& parametro.equals("subir")){
					st.executeUpdate("UPDATE Sensor SET ultima_accion ='"+"Aumentar presion de riego"+"' WHERE id_sensor='"+id+"';");
					respuesta = "206 OK Acción sobre el sensor confirmada";
				}else
					respuesta = "407 ERROR accion ya ejecutada";
			}
		}else{
			respuesta = "408 ERROR id_variable en estado OFF.";
		}
		
		rs2.close();
		rs.close();
	 	st.close();
	 	st2.close();
	 	return respuesta;
	}
	public String foto(String id) throws SQLException {
		
		Statement st = conn.createStatement();
		ResultSet rs2 = st.executeQuery("SELECT P.foto from Placa P, Sensor S  where P.id_placa=S.id_placa and S.id_sensor='"+id+"';");
		String foto = rs2.getString("foto");// falla
		foto = foto+(".jpg");
		rs2.close();
		st.close();
		System.out.println("foto:"+foto);
		return foto;
		
	}
	
	//___________________________________ventana controlador_______________________
	public DefaultTableModel echarUsuario(String nick) throws SQLException {
		
		Statement st = conn.createStatement();
		st.executeUpdate("UPDATE User SET estado ='"+"offline"+"' WHERE User='"+nick+"';");
		
		DefaultTableModel tabla = this.sacarUsuarios();
		st.close();
		return tabla;
	}
	
	public DefaultTableModel conectarUsuario(String nick) throws SQLException {
		
		Statement st = conn.createStatement();
		st.executeUpdate("UPDATE User SET estado ='"+"online"+"' WHERE User='"+nick+"';");
		
		DefaultTableModel tabla = this.sacarUsuarios();
		st.close();
		return tabla;
	}
	
	public DefaultTableModel sacarUsuarios() throws SQLException {
		
		Statement st = conn.createStatement();
		ResultSet rs2 = st.executeQuery("SELECT * from User;");
		
		
		DefaultTableModel modelo = new DefaultTableModel();
		String[] columTitulo = { " Nick  ", " Estado " };
		modelo.setColumnIdentifiers(columTitulo);

		while (rs2.next()) {
			Usuario u = new Usuario(rs2.getString("User"),
					rs2.getString("estado"));
			Object[] o = { u.getNick(), u.getEstado() };			
			modelo.addRow(o);
		}

		modelo.fireTableDataChanged();
		rs2.close();
		st.close();
		return modelo;
	}
	
	public static void main(String argv[]) throws Exception
	{
		DataBaseControler db = new DataBaseControler();
		String requestLine = "id_placa,p1";
		db.buscarSensor(requestLine);
		
	}
		 

}
	 
	
	 	
