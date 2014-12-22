package util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;

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
    	
    	String respuesta="";
    	if (nombre.isEmpty()){
    		respuesta= "400ERR.Falta el nombre de usuario";
    	}
    	else{
    		Statement st = conn.createStatement(); 
    		
    		ResultSet rs2 = st.executeQuery("Select * from User Where nick = '"+nombre+"';"); 
    		

    		if (rs2.getString("nick").contains("")){ // falla aqui, no se como comprobar si devuelve algo vacio
    			respuesta = "200OK.Bienvenido:"+nombre;
    		}else{
    			respuesta = "401ERR.Usuario desconocido";
    		}
    		rs2.close();
        }
		return respuesta;
	}
     
	 		
	public String ConsultarPasword(String nombre,int pass)throws SQLException{
			
		String existe="";
		
    	if (pass!=0){
    		
    		Statement st = conn.createStatement(); 
			ResultSet rs2 = st.executeQuery("SELECT * from User WHERE nick = '"+nombre+"' AND contrasena = '"+pass+"';");  // falla aqui, no se como comprobar si devuelve algo vacio                                                                                                                                           // estado civil es estado, no string
		 	if (!rs2.equals(null))
		 		existe="201 OK Bienvenido al sistema";
		 	else
		 		existe="401 ERR La clave es incorrecta";
			rs2.close();
	    }else
			existe="402 ERR Falta la clave";
		
    	return existe;
    		
    	
    	
	  }
	

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
		while (rs2.next()) {
			Sensor a = new Sensor(rs2.getString("id_placa"),rs2.getString("id_sensor"),rs2.getString("def_variable"),
					rs2.getString("func_principal"),rs2.getString("estado"),rs2.getString("ultima_accion"));
			
			temp = (a.getId_placa()+";"+a.getId_sensor()+";"+a.getDef()+";"+a.getFuncion_principal()+";"+a.getEstado()+";"+a.getUltima_accion());
			
			fin=fin+temp+",";
			
		}
		rs2.close();
		return fin;
		
	}


	public String buscarPlaca(String nombre) throws SQLException {
		
		String lista ="";
		String temp="";
 		Statement stat = conn.createStatement();
 		ResultSet rs = stat.executeQuery("Select * from Placa where id_placa = "+nombre+";");
 		Placa placa = null;
 		
 		while (rs.next()) {
 			placa = new Placa(rs.getString("id_placa"), rs.getString("estado_placa"), rs.getString("imagen"));
 			temp = (placa.getId_placa()+";"+placa.getEstado_placa()+";"+placa.getImagen());
 			lista=lista+temp+",";
 		}
 		 stat = conn.createStatement();
 		 rs = stat.executeQuery("Select * from Placa where estado_placa = "+nombre+";");
 		 
 		while (rs.next()) {
 			placa = new Placa(rs.getString("id_placa"), rs.getString("estado_placa"), rs.getString("imagen"));
 			temp = (placa.getId_placa()+";"+placa.getEstado_placa()+";"+placa.getImagen());
 			if (!lista.contains(temp)){ // si no esta insertado ya por la anterior consulta
 			lista=lista+temp+",";
 			}
 		}
 		lista=lista+"/";
 		rs.close();
		return lista;
	}
	// metodo buscar por sensor
	public String buscarSensor(String requestLine) throws SQLException {
		String temp="";
		String fin="";
		
 		Statement stat = conn.createStatement();
 		
 		ResultSet rs2 = stat.executeQuery("Select * from Sensor where id_sensor = "+requestLine+";");
 		while (rs2.next()) {
 			 Sensor a = new Sensor(rs2.getString("id_placa"),rs2.getString("id_sensor"),rs2.getString("def_variable"),
 					 rs2.getString("Ultima_accion"),rs2.getString("estado"),rs2.getString("func_principal"));
 			 
 			 temp = (a.getId_placa()+";"+a.getDef()+";"+a.getFuncion_principal()+";"+a.getEstado()+";"+a.getUltima_accion());
 			 
 			if (!fin.contains(temp)){ // si no esta insertado ya por la anterior consulta
 				fin=fin+temp+",";
 	 			}
 	 		}
 	
 		stat = conn.createStatement();
 		rs2.close();
 		rs2 = stat.executeQuery("Select * from Sensor where id_Placa = "+requestLine+";");
 		while (rs2.next()) {
 			 Sensor a = new Sensor(rs2.getString("id_placa"),rs2.getString("id_sensor"),rs2.getString("def_variable"),
 					 rs2.getString("Ultima_accion"),rs2.getString("estado"),rs2.getString("func_principal"));
 			 
 			 temp = (a.getId_placa()+";"+a.getDef()+";"+a.getFuncion_principal()+";"+a.getEstado()+";"+a.getUltima_accion());
 			 
 			if (!fin.contains(temp)){ // si no esta insertado ya por la anterior consulta
 				fin=fin+temp+",";
 	 			}
 		}
 		stat = conn.createStatement();
 		rs2.close();
 		rs2 = stat.executeQuery("Select * from Sensor where def_variable = '"+requestLine+"';");
 		while (rs2.next()) {
 			 Sensor a = new Sensor(rs2.getString("id_placa"),rs2.getString("id_sensor"),rs2.getString("def_variable"),
 					 rs2.getString("Ultima_accion"),rs2.getString("estado"),rs2.getString("func_principal"));
 			 
 			 temp = (a.getId_placa()+";"+a.getDef()+";"+a.getFuncion_principal()+";"+a.getEstado()+";"+a.getUltima_accion());
 			 
 			if (!fin.contains(temp)){ // si no esta insertado ya por la anterior consulta
 				fin=fin+temp+",";
 	 			}
 		}
 		rs2.close();
 		rs2 = stat.executeQuery("Select * from Sensor where Ultima_accion = '"+requestLine+"';");
 		
 		while (rs2.next()) {
 			 Sensor a = new Sensor(rs2.getString("id_placa"),rs2.getString("id_sensor"),rs2.getString("def_variable"),
 					 rs2.getString("Ultima_accion"),rs2.getString("estado"),rs2.getString("func_principal"));
 			 
 			 temp = (a.getId_placa()+";"+a.getDef()+";"+a.getFuncion_principal()+";"+a.getEstado()+";"+a.getUltima_accion());
 			 
 			if (!fin.contains(temp)){ // si no esta insertado ya por la anterior consulta
 				fin=fin+temp+",";
 	 			}
 		}
 		rs2.close();
 		rs2 = stat.executeQuery("Select * from Sensor where estado = '"+requestLine+"';");
 		while (rs2.next()) {
 			 Sensor a = new Sensor(rs2.getString("id_placa"),rs2.getString("id_sensor"),rs2.getString("def_variable"),
 					 rs2.getString("Ultima_accion"),rs2.getString("estado"),rs2.getString("func_principal"));
 			 
 			 temp = (a.getId_placa()+";"+a.getDef()+";"+a.getFuncion_principal()+";"+a.getEstado()+";"+a.getUltima_accion());
 			 
 			if (!fin.contains(temp)){ // si no esta insertado ya por la anterior consulta
 				fin=fin+temp+",";
 	 			}
 		}
 		rs2.close();
 		rs2 = stat.executeQuery("Select * from Sensor where func_principal = '"+requestLine+"';");
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
		System.out.println("me pasan el id sensor: "+ids);
		String sql ="UPDATE Sensor SET estado ='on' WHERE id_sensor='"+ids+"';";
		System.out.println(sql);
 		st.execute(sql);
 		st.close();
	}
	public void apagarSensor(String ids) throws SQLException {
		
		Statement st = conn.createStatement();
		System.out.println("me pasan el id sensor: "+ids);
		String sql ="UPDATE Sensor SET estado ='off' WHERE id_sensor='"+ids+"';";
 		st.execute(sql);
 		st.close();
	}
	

	

	public void cambiarEstado(String id) throws SQLException {
		// TODO Auto-generated method stub
		
		Statement st = conn.createStatement();
		ResultSet rs2 = st.executeQuery ( "Select * from Sensor WHERE id_sensor='"+id+"';");// Saco la info del sensor que quieren que cambie
		if ( !rs2.getString("Ultima_accion").equals(rs2.getString("func_principal"))){
			String accion= rs2.getString("func_principal");
			rs2 = st.executeQuery ("UPDATE Sensor SET UltimaAccion ='"+accion+"' WHERE id_sensor='"+id+"';");
		}else {
			if (id.equals("s1"))
				rs2 = st.executeQuery ("UPDATE Sensor SET UltimaAccion ='subir calefaccion' WHERE id_sensor='"+id+"';");
			else if (id.equals("s2"))
				rs2 = st.executeQuery ("UPDATE Sensor SET UltimaAccion ='apagar calefaccion' WHERE id_sensor='"+id+"';");
			else if (id.equals("s3"))
				rs2 = st.executeQuery ("UPDATE Sensor SET UltimaAccion ='bajar intensidad luz' WHERE id_sensor='"+id+"';");
			else if (id.equals("s4"))
				rs2 = st.executeQuery ("UPDATE Sensor SET UltimaAccion ='apagar luz' WHERE id_sensor='"+id+"';");
			else if (id.equals("s5"))
				rs2 = st.executeQuery ("UPDATE Sensor SET UltimaAccion ='bajar aire acondicionado' WHERE id_sensor='"+id+"';");
			else if (id.equals("s6"))
				rs2 = st.executeQuery ("UPDATE Sensor SET UltimaAccion ='apagar aire acondicionado' WHERE id_sensor='"+id+"';");
				}
				
				
				
	 	 		st.close();
				
			}
			public static void main(String[] args){
				
			}
}
	 	
	 	
	 	