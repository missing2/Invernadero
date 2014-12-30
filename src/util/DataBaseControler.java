package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

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
	
	public void  alta(String nick,String pass) throws SQLException{
		Statement st = conn.createStatement();
		ResultSet rs2 = st.executeQuery("insert into User values('"+nick+"','"+pass+"';");
		
	}
	public void  baja(String nick,String pass) throws SQLException{
		Statement st = conn.createStatement();
		ResultSet rs2 = st.executeQuery("delete from User where nick ='"+nick+"' and where contrasena ='"+pass+"';");
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
		
    	if (!Integer.toString(pass).isEmpty()){// si la clave no esta vacia...
    		
    		Statement st = conn.createStatement(); 
			ResultSet rs2 = st.executeQuery("SELECT * from User WHERE User = '"+nombre+"';");  
			int contra = rs2.getInt("contrasena");
		 	if (contra==pass){
		 		existe="201 OK Bienvenido al sistema";
		 	}else		 	
		 		existe="401 ERR La clave es incorrecta";
		 		rs2.close();
	    }else
			existe="402 ERR Falta la clave";
		
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
		System.out.println(fin);
		return fin;
		
		
	}

	
	public String buscarSensor(String requestLine) throws SQLException {
		String temp="";
		String fin="";
		
		 // adecuo las consultas especiales que me pasan, a consultas adaptadas a sql
		if (requestLine.contains("*")){
			requestLine.replace('*','%');
		} 
		else if(requestLine.contains("?")){
			requestLine.replace('?','_');
			requestLine=requestLine+"__";
		} 
		
 		Statement stat = conn.createStatement();
 		
 		ResultSet rs2 = stat.executeQuery("Select * from Sensor where id_sensor like '"+requestLine+"';");
 		while (rs2.next()) {
 			 Sensor a = new Sensor(rs2.getString("id_placa"),rs2.getString("id_sensor"),rs2.getString("def_variable"),
 					 rs2.getString("Ultima_accion"),rs2.getString("estado"),rs2.getString("func_principal"));
 			 
 			 temp = (a.getId_placa()+";"+a.getDef()+";"+a.getFuncion_principal()+";"+a.getEstado()+";"+a.getUltima_accion());
 			 
 			if (!fin.contains(temp)){ // si no esta insertado ya por la anterior consulta
 				fin=fin+temp+",";
 	 			}
 	 		}
 		stat.close();
 		stat = conn.createStatement();
 		rs2.close();
 		rs2 = stat.executeQuery("Select * from Sensor where id_Placa like '"+requestLine+"';");
 		while (rs2.next()) {
 			 Sensor a = new Sensor(rs2.getString("id_placa"),rs2.getString("id_sensor"),rs2.getString("def_variable"),
 					 rs2.getString("Ultima_accion"),rs2.getString("estado"),rs2.getString("func_principal"));
 			 
 			 temp = (a.getId_placa()+";"+a.getDef()+";"+a.getFuncion_principal()+";"+a.getEstado()+";"+a.getUltima_accion());
 			 
 			if (!fin.contains(temp)){ // si no esta insertado ya por la anterior consulta
 				fin=fin+temp+",";
 	 			}
 		}
 		stat.close();
 		stat = conn.createStatement();
 		rs2.close();
 		rs2 = stat.executeQuery("Select * from Sensor where def_variable like '"+requestLine+"';");
 		while (rs2.next()) {
 			 Sensor a = new Sensor(rs2.getString("id_placa"),rs2.getString("id_sensor"),rs2.getString("def_variable"),
 					 rs2.getString("Ultima_accion"),rs2.getString("estado"),rs2.getString("func_principal"));
 			 
 			 temp = (a.getId_placa()+";"+a.getDef()+";"+a.getFuncion_principal()+";"+a.getEstado()+";"+a.getUltima_accion());
 			 
 			if (!fin.contains(temp)){ // si no esta insertado ya por la anterior consulta
 				fin=fin+temp+",";
 	 			}
 		}
 		rs2.close();
 		rs2 = stat.executeQuery("Select * from Sensor where Ultima_accion like '"+requestLine+"';");
 		
 		while (rs2.next()) {
 			 Sensor a = new Sensor(rs2.getString("id_placa"),rs2.getString("id_sensor"),rs2.getString("def_variable"),
 					 rs2.getString("Ultima_accion"),rs2.getString("estado"),rs2.getString("func_principal"));
 			 
 			 temp = (a.getId_placa()+";"+a.getDef()+";"+a.getFuncion_principal()+";"+a.getEstado()+";"+a.getUltima_accion());
 			 
 			if (!fin.contains(temp)){ // si no esta insertado ya por la anterior consulta
 				fin=fin+temp+",";
 	 			}
 		}
 		rs2.close();
 		rs2 = stat.executeQuery("Select * from Sensor where estado like '"+requestLine+"';");
 		while (rs2.next()) {
 			 Sensor a = new Sensor(rs2.getString("id_placa"),rs2.getString("id_sensor"),rs2.getString("def_variable"),
 					 rs2.getString("Ultima_accion"),rs2.getString("estado"),rs2.getString("func_principal"));
 			 
 			 temp = (a.getId_placa()+";"+a.getDef()+";"+a.getFuncion_principal()+";"+a.getEstado()+";"+a.getUltima_accion());
 			 
 			if (!fin.contains(temp)){ // si no esta insertado ya por la anterior consulta
 				fin=fin+temp+",";
 	 			}
 		}
 		rs2.close();
 		rs2 = stat.executeQuery("Select * from Sensor where func_principal like '"+requestLine+"';");
 		while (rs2.next()) {
 			 Sensor a = new Sensor(rs2.getString("id_placa"),rs2.getString("id_sensor"),rs2.getString("def_variable"),
 					 rs2.getString("Ultima_accion"),rs2.getString("estado"),rs2.getString("func_principal"));
 			 
 			 temp = (a.getId_placa()+";"+a.getDef()+";"+a.getFuncion_principal()+";"+a.getEstado()+";"+a.getUltima_accion());
 			 
 			if (!fin.contains(temp)){ // si no esta insertado ya por la anterior consulta
 				fin=fin+temp+",";
 	 			}
 		}
 		
 		fin=fin+"/";
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
	

	

	public void cambiarEstado(String id) throws SQLException { // depurarlo y mirar consultas...
		// TODO Auto-generated method stub
		
		Statement st = conn.createStatement();
		ResultSet rs2 = st.executeQuery ( "Select * from Sensor WHERE id_sensor='"+id+"';");// Saco la info del sensor que quieren que cambie
		Sensor a = new Sensor(rs2.getString("id_placa"),rs2.getString("id_sensor"),rs2.getString("def_variable"),
				rs2.getString("func_principal"),rs2.getString("estado"),rs2.getString("ultima_accion"));
		 
		if ( a.getFuncion_principal().equals("Regulación climatización")){
			
			if(a.getUltima_accion().equals("bajar calefaccion"))
				st.executeUpdate("UPDATE Sensor SET ultima_accion ='"+"subir calefaccion"+"' WHERE id_sensor='"+id+"';");
			else if(a.getUltima_accion().equals("subir calefaccion"))
				st.executeUpdate("UPDATE Sensor SET ultima_accion ='"+"bajar calefaccion"+"' WHERE id_sensor='"+id+"';");
			else if(a.getUltima_accion().equals("encender calefaccion"))
				st.executeUpdate("UPDATE Sensor SET ultima_accion ='"+"apagar calefaccion"+"' WHERE id_sensor='"+id+"';");
			else if(a.getUltima_accion().equals("apagar calefaccion"))
				st.executeUpdate("UPDATE Sensor SET ultima_accion ='"+"encender calefaccion"+"' WHERE id_sensor='"+id+"';");
		
		}else if(a.getFuncion_principal().equals("Regulación de Luminosidad")){
			
			if(a.getUltima_accion().equals("encender luz"))
				st.executeQuery ("UPDATE Sensor SET ultima_accion ='"+"apagar luz"+"' WHERE id_sensor='"+id+"';");
			else if(a.getUltima_accion().equals("apagar luz"))
				st.executeQuery ("UPDATE Sensor SET ultima_accion ='"+"encender luz"+"' WHERE id_sensor='"+id+"';");
			else if(a.getUltima_accion().equals("subir intensidad"))
				st.executeUpdate("UPDATE Sensor SET ultima_accion ='"+"bajar intensidad"+"' WHERE id_sensor='"+id+"';");
			else
				st.executeUpdate("UPDATE Sensor SET ultima_accion ='"+"subir intensidad"+"' WHERE id_sensor='"+id+"';");
			
		}else if(a.getFuncion_principal().equals("Sistema de riego")){
			
			if(a.getUltima_accion().equals("Activar sistema de riego"))
				st.executeUpdate("UPDATE Sensor SET ultima_accion ='"+"Desactivar sistema de riego"+"' WHERE id_sensor='"+id+"';");
			else if(a.getUltima_accion().equals("Desactivar sistema de riego"))
				st.executeUpdate("UPDATE Sensor SET ultima_accion ='"+"Activar sistema de riego"+"' WHERE id_sensor='"+id+"';");
			else if(a.getUltima_accion().equals("Aumentar presion de riego"))
				st.executeUpdate("UPDATE Sensor SET ultima_accion ='"+"Bajar presion de riego"+"' WHERE id_sensor='"+id+"';");
			else if(a.getUltima_accion().equals("Bajar presion de riego"))
				st.executeUpdate("UPDATE Sensor SET ultima_accion ='"+"Aumentar presion de riego"+"' WHERE id_sensor='"+id+"';");
			
		}
		
		rs2.close();			
	 	st.close();
				
	}
	public String foto(String id) throws SQLException {
		
		Statement st = conn.createStatement();
		ResultSet rs2 = st.executeQuery("SELECT P.foto from Placa P, Sensor S  where P.id_placa=S.id_placa and S.id_sensor='"+id+"';");
		rs2.close();
		st.close();
		String foto = rs2.getString("foto");
		return foto;
		
	}
	public static void main(String[] args){
		
	}
}
	 	
	 	
	 	