package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;

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
	
	public Data_base_controler()
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
				ResultSet rs2 = st.executeQuery("Select * from variable Where def_variable = "+idv+" and Where id_placa = "+idp+"");
				return existe;
			}

			public String consultarEstadoVariable(String idp,String idv) throws SQLException {
				String estado;
				Statement st = conn.createStatement();
				ResultSet rs2 = st.executeQuery("Select estado_variable from variable Where def_variable = "+idv+" and Where id_placa="+idp+"");
				estado=rs2.toString();
				return estado;
			}

			public List< String> sacarlista() throws SQLException {// saca una lista en la ventana de accion con todos los sensores y placas
				
				List<String> lista = new ArrayList<String>();
				Statement st = conn.createStatement();
				
				ResultSet rs2 =  st.executeQuery("Select * from Sensor;");
				while (rs2.next()) {
					Sensor a = new Sensor(rs2.getString("id_sensor"),rs2.getString("id_placa"),
							rs2.getString("def_variable"),rs2.getString("ultima_accion"),rs2.getString("estado"),rs2.getString("on_off"),rs2.getString("func_principal"));
					
					String temp = (""+a.getId_sensor()+","+a.id_placa+","+a.def+","+a.getUltima_accion()+","+a.estado+","+a.on_off+","+a.funcion_principal+";");
					lista.add(temp);
					lista.add("/");
					
				}
				
				rs2 =  st.executeQuery("Select * from Placa;");
				while (rs2.next()) {
					Placa a = new Placa(rs2.getString("id_placa"),rs2.getString("estado_placa"),rs2.getString("foto"));
					String temp =(""+a.id_placa+","+a.getEstado_placa()+","+a.imagen+";");
					lista.add(temp);
				}
				
				return lista;
				
			}

			public List<Sensor> sacarBusqueda(String nombre, String donde ) throws SQLException { 

				List< Sensor> lista = new ArrayList<Sensor>();
				Statement st = conn.createStatement();
				
					ResultSet rs2 =  st.executeQuery("Select * from Placa Where ---------"); // esta bien????
					
					while (rs2.next()){
						Sensor a = new Sensor(rs2.getString("id_sensor"),rs2.getString("id_placa"),
								rs2.getString("def"),rs2.getString("ultima_accion"),rs2.getString("estado"),rs2.getString("on_off"),rs2.getString("func_principal"));
						lista.add(a);
					}
				
				return lista;
				
			}
			//Necesitamos cosas de placa para hacer busquedas?? si es asi habria que hacer algo asi
			public List<Placa> buscarPlaca(String nombrePlaca) throws SQLException {
				
				List<Placa> lista = new ArrayList<Placa>();
	 	 		Statement stat = conn.createStatement();
	 	 		ResultSet rs = stat.executeQuery("Select * from Placa where id_placa = "+nombrePlaca+";");
	 	 		Placa placa = null;
	 	 		
	 	 		while (rs.next()) {
	 	 			placa = new Placa(rs.getString("id_placa"), rs.getString("estado_placa"), rs.getString("imagen"));
	 	 			lista.add(placa);
	 	 		}
	 	 		
	 	 		rs.close();
	 	 		stat.close();
	 	 		
	 			return lista;
	 		}
			public List<Sensor> buscarSensor(String requestLine) throws SQLException {
				List<Sensor> lista = new ArrayList<Sensor>();
	 	 		Statement stat = conn.createStatement();
	 	 		ResultSet rs = stat.executeQuery("Select * from Sensor where id_sensor = "+requestLine+";");
	 	 		Sensor sensor = null;
	 	 		
	 	 		while (rs.next()) {
	 	 		    sensor = new Sensor(requestLine, requestLine, requestLine, requestLine, requestLine, requestLine, requestLine);
	 	 			lista.add(sensor);
	 	 		}
	 	 		
	 	 		rs.close();
	 	 		stat.close();
	 	 		
	 			return lista;
	 		}

			public void encenderSensor(String idv) throws SQLException {
				
				Statement st = conn.createStatement();
				String sql = "UPDATE Sensor SET"+" on_off ='ON' WHERE  id_sensor='" +idv+"'";
				System.out.println(sql);
	 	 		st.execute(sql);
	 	 		st.close();
			}
			public void apagarSensor(String idv) throws SQLException {
				
				Statement st = conn.createStatement();
				String sql = "UPDATE SENSOR SET"+" on_off ='OFF' WHERE  id_sensor='" +idv+"'";
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
				Data_base_controler dbc = getInstance();
				try {
					System.out.println(dbc.consultaUsuario("vero"));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
	 	
	 	
	 	