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
		conn = DriverManager.getConnection("jdbc:sqlite:Data_base/ProyectoRedesBds.sqlite"); // esta bien??
		//el problema es que estas usando una bd s3db y has puesto sqlite
	}
	
	//Método para cerrar la conexion de la BD
	public void desconectar() throws SQLException {
	conn.close();
	}

	
	
  
    public boolean consultaUsuario(String nombre) throws SQLException {
    
    	boolean existe=false;
    	
		Statement st = conn.createStatement(); 
		
		ResultSet rs2 = st.executeQuery("Select * from User Where User = "+nombre+"");                                                                                                                                                     // estado civil es estado, no string
	 	if (rs2!=null)
	 		existe=true;
	 		
		rs2.close();
		return existe;
	}
     
	 		
	 		public boolean ConsultarPasword(String nombre,int pass)throws SQLException{
	 				 			
	 			boolean existe=false;
	 	    	
	 			Statement st = conn.createStatement(); 
	 			
	 			ResultSet rs2 = st.executeQuery("Select * from User Where User = "+nombre+"and Where contrasena='"+pass+"'");                                                                                                                                                     // estado civil es estado, no string
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

			public List< Object> sacarlista() throws SQLException {// saca una lista en la ventana de accion con todos los sensores y sus atributos
				
				List< Object> lista = new ArrayList<Object>();
				Statement st = conn.createStatement();
				
				ResultSet rs2 =  st.executeQuery("Select * from Sensor");
				while (rs2.next()) {
					Sensor a = new Sensor(rs2.getString("id_sensor"),rs2.getString("id_placa"),
							rs2.getString("def"),rs2.getString("ultima_accion"),rs2.getString("estado"),rs2.getString("on_off"));
					lista.add(a);
				}
				
				rs2 =  st.executeQuery("Select * from Placa");
				while (rs2.next()) {
					Placa a = new Placa(rs2.getString("id_placa"),rs2.getString("estado_placa"),rs2.getString("imagen"));
					lista.add(a);
				}
				
				return lista;
				
			}

			public List<Sensor> sacarBusqueda(String nombre, String donde ) throws SQLException { 

				List< Sensor> lista = new ArrayList<Sensor>();
				Statement st = conn.createStatement();
				
					ResultSet rs2 =  st.executeQuery("Select * from "+donde+" Where def_variable = "+nombre+""); // esta bien????
					
					while (rs2.next()){
						Sensor a = new Sensor(rs2.getString("id_sensor"),rs2.getString("id_placa"),
								rs2.getString("def"),rs2.getString("ultima_accion"),rs2.getString("estado"),rs2.getString("on_off"));
						lista.add(a);
					}
				
				return lista;
				
			}
			//Necesitamos cosas de placa para hacer busquedas?? si es asi habria que hacer algo asi
			public List<Placa> buscarPlaca(String nombrePlaca) throws SQLException {
				
				List<Placa> lista = new ArrayList<Placa>();
	 	 		Statement stat = conn.createStatement();
	 	 		ResultSet rs = stat.executeQuery("Select P.,P. from PLACA P,SENSOR S where ='"+nombrePlaca+"'");
	 	 		Placa placa = null;
	 	 		
	 	 		while (rs.next()) {
	 	 			//placa = new Placa(rs.getString("Cod_obra"), rs.getString("Concepto"), rs.getDouble("Num_horas"), rs.getString("Lugar"), rs.getString("Estado"), rs.getString("F_inicio"), rs.getString("F_fin"), rs.getString("Cod_ped"),rs.getDouble("Presupuesto"));
	 	 			lista.add(placa);
	 	 		}
	 	 		
	 	 		rs.close();
	 	 		stat.close();
	 	 		
	 			return lista;
	 		}

			public void encenderVariable(String idv) throws SQLException {
				
				Statement st = conn.createStatement();
				String sql = "UPDATE SENSOR SET"+" on_off ='ON' WHERE  id_sensor='" +idv+"'";
				System.out.println(sql);
	 	 		st.execute(sql);
	 	 		st.close();
			}
			public void apagarVariable(String idv) throws SQLException {
				
				Statement st = conn.createStatement();
				String sql = "UPDATE SENSOR SET"+" on_off ='OFF' WHERE  id_sensor='" +idv+"'";
				System.out.println(sql);
	 	 		st.execute(sql);
	 	 		st.close();
			}
}
	 	
	 	
	 	