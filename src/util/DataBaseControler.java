package util;

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
		//Statement state = conn.createStatement();
		//String sql = "CREATE TABLE TEST (test int)";
		//state.executeUpdate(sql);

		//el problema es que estas usando una bd s3db y has puesto sqlite
		 
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
	
	
  
    public boolean consultaUsuario(String nombre) throws SQLException {
    	System.out.println("checking user");
    
    	boolean existe=false;
    	
		Statement st = conn.createStatement(); 
		
		ResultSet rs2 = st.executeQuery("Select * from User Where User = \""+nombre+"\";");                                                                                                                                                     // estado civil es estado, no string
	 	if (rs2!=null)
	 		existe=true;
	 		
		rs2.close();
		return existe;
	}
     
	 		
	 		public boolean ConsultarPasword(String nombre,int pass)throws SQLException{
	 			
	 			System.out.println("checking password");
	 				 			
	 			boolean existe=false;
	 	    	
	 			Statement st = conn.createStatement(); 
	 			
	 			ResultSet rs2 = st.executeQuery("SELECT * from User WHERE User = \""+nombre+"\" AND contrasena = '"+pass+"';");                                                                                                                                                     // estado civil es estado, no string
	 		 	if (rs2!=null)
	 		 		existe=true;
	 		 		
	 			rs2.close();
	 			return existe;
	 		  }

			public boolean ConsultarIdVariable(String idp,String idv) throws SQLException {
				boolean existe = false;
				Statement st = conn.createStatement();
				ResultSet rs2 = st.executeQuery("Select * from variable Where def_variable = "+idv+"and Where id_placa="+idp+"");
				return existe;
			}

			public String consultarEstadoVariable(String idp,String idv) throws SQLException {
				String estado;
				Statement st = conn.createStatement();
				ResultSet rs2 = st.executeQuery("Select estado_variable from variable Where def_variable = "+idv+" and Where id_placa="+idp+"");
				estado=rs2.toString();
				return estado;
			}

			public String sacarlista() throws SQLException {// saca una lista en la ventana de accion con todos los sensores y placas
				
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
	 	 			temp = (placa.getId_placa()+"-"+placa.getEstado_placa()+"-"+placa.getImagen());
	 	 			lista=lista+temp+",";
	 	 		}
	 	 		 stat = conn.createStatement();
	 	 		 rs = stat.executeQuery("Select * from Placa where estado_placa = "+nombre+";");
	 	 		 
	 	 		while (rs.next()) {
	 	 			placa = new Placa(rs.getString("id_placa"), rs.getString("estado_placa"), rs.getString("imagen"));
	 	 			temp = (placa.getId_placa()+"-"+placa.getEstado_placa()+"-"+placa.getImagen());
	 	 			if (!lista.contains(temp)){ // si no esta insertado ya por la anterior consulta
	 	 			lista=lista+temp+",";
	 	 			}
	 	 		}
	 	 		lista=lista+"/";		
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
	 	 			 
	 	 			 temp = (a.getId_placa()+"-"+a.getDef()+"-"+a.getFuncion_principal()+"-"+a.getEstado()+"-"+a.getUltima_accion());
	 	 			 
	 	 			if (!fin.contains(temp)){ // si no esta insertado ya por la anterior consulta
	 	 				fin=fin+temp+",";
		 	 			}
		 	 		}
	 	 	
	 	 		stat = conn.createStatement();
	 	 		rs2 = stat.executeQuery("Select * from Sensor where id_Placa = "+requestLine+";");
	 	 		while (rs2.next()) {
	 	 			 Sensor a = new Sensor(rs2.getString("id_placa"),rs2.getString("id_sensor"),rs2.getString("def_variable"),
	 	 					 rs2.getString("Ultima_accion"),rs2.getString("estado"),rs2.getString("func_principal"));
	 	 			 
	 	 			 temp = (a.getId_placa()+"-"+a.getDef()+"-"+a.getFuncion_principal()+"-"+a.getEstado()+"-"+a.getUltima_accion());
	 	 			 
	 	 			if (!fin.contains(temp)){ // si no esta insertado ya por la anterior consulta
	 	 				fin=fin+temp+",";
		 	 			}
	 	 		}
	 	 		stat = conn.createStatement();
	 	 		rs2 = stat.executeQuery("Select * from Sensor where def_variable = "+requestLine+";");
	 	 		while (rs2.next()) {
	 	 			 Sensor a = new Sensor(rs2.getString("id_placa"),rs2.getString("id_sensor"),rs2.getString("def_variable"),
	 	 					 rs2.getString("Ultima_accion"),rs2.getString("estado"),rs2.getString("func_principal"));
	 	 			 
	 	 			 temp = (a.getId_placa()+"-"+a.getDef()+"-"+a.getFuncion_principal()+"-"+a.getEstado()+"-"+a.getUltima_accion());
	 	 			 
	 	 			if (!fin.contains(temp)){ // si no esta insertado ya por la anterior consulta
	 	 				fin=fin+temp+",";
		 	 			}
	 	 		}
	 	 		rs2 = stat.executeQuery("Select * from Sensor where Ultima_accion = "+requestLine+";");
	 	 		while (rs2.next()) {
	 	 			 Sensor a = new Sensor(rs2.getString("id_placa"),rs2.getString("id_sensor"),rs2.getString("def_variable"),
	 	 					 rs2.getString("Ultima_accion"),rs2.getString("estado"),rs2.getString("func_principal"));
	 	 			 
	 	 			 temp = (a.getId_placa()+"-"+a.getDef()+"-"+a.getFuncion_principal()+"-"+a.getEstado()+"-"+a.getUltima_accion());
	 	 			 
	 	 			if (!fin.contains(temp)){ // si no esta insertado ya por la anterior consulta
	 	 				fin=fin+temp+",";
		 	 			}
	 	 		}
	 	 		
	 	 		rs2 = stat.executeQuery("Select * from Sensor where estado = "+requestLine+";");
	 	 		while (rs2.next()) {
	 	 			 Sensor a = new Sensor(rs2.getString("id_placa"),rs2.getString("id_sensor"),rs2.getString("def_variable"),
	 	 					 rs2.getString("Ultima_accion"),rs2.getString("estado"),rs2.getString("func_principal"));
	 	 			 
	 	 			 temp = (a.getId_placa()+"-"+a.getDef()+"-"+a.getFuncion_principal()+"-"+a.getEstado()+"-"+a.getUltima_accion());
	 	 			 
	 	 			if (!fin.contains(temp)){ // si no esta insertado ya por la anterior consulta
	 	 				fin=fin+temp+",";
		 	 			}
	 	 		}
	 	 		rs2 = stat.executeQuery("Select * from Sensor where func_principal = "+requestLine+";");
	 	 		while (rs2.next()) {
	 	 			 Sensor a = new Sensor(rs2.getString("id_placa"),rs2.getString("id_sensor"),rs2.getString("def_variable"),
	 	 					 rs2.getString("Ultima_accion"),rs2.getString("estado"),rs2.getString("func_principal"));
	 	 			 
	 	 			 temp = (a.getId_placa()+"-"+a.getDef()+"-"+a.getFuncion_principal()+"-"+a.getEstado()+"-"+a.getUltima_accion());
	 	 			 
	 	 			if (!fin.contains(temp)){ // si no esta insertado ya por la anterior consulta
	 	 				fin=fin+temp+",";
		 	 			}
	 	 		}
	 	 		
	 	 		fin=fin+"/";		
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
				System.out.println(sql);
	 	 		st.execute(sql);
	 	 		st.close();
			}
			
			public void  alta(String nick,String pass) throws SQLException{
				Statement st = conn.createStatement();
				String sql = "insert into User values("+nick+","+pass+");";
				
			}
			public void  baja(String nick,String pass) throws SQLException{
				Statement st = conn.createStatement();
				String sql = "delete from User where User ="+nick+" and where contrasena ="+pass+";";
			}

			public static void main(String[] args){
				DataBaseControler dbc = getInstance();
				try {
					System.out.println(dbc.ConsultarPasword("vero", 654321));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					System.out.println(dbc.sacarlista());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			public void cambiarEstado(String id) throws SQLException {
				// TODO Auto-generated method stub
				//no que que tendria que hacer este metodo, ya que solo puedo cambiar en la bd la ultima accion
				// lo demas seria mecanico del propio sensor...
				Statement st = conn.createStatement();
				String sql = "Select * from Sensor where id_sensor ="+st+";";
				
			}
}
	 	
	 	
	 	