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
		conn = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos/GALAFERR.sqlite"); // cambiar
	}
	
	//Método para cerrar la conexion de la BD
	public void desconectar() throws SQLException {
	conn.close();
	}

	
	
	
	
	
	
	
	
	//------------------------- METODOS DE EMPLEADO ---------------------------------------
	
    /**
     * Método para INSERTAR un Usuario
     * @param a -> Objeto de tipo Usuario
     * @throws SQLException
     */
    public void insertarUsuario(Usuario a) throws SQLException {
				
	Statement st = conn.createStatement();
					
	st.execute("INSERT INTO User VALUES ('"+a.getDni()+"','"+a.getNombre()+"','"+a.getObservaciones()+"')");
							   
	System.out.println("-------------------");					
	System.out.println("USUARIO INSERTADO");
	System.out.println("-------------------");
					
	st.close();
	}
	
	
	
     /**
     * Método para CONSULTAR un EMPLEADO por Nombre de empleado (De la lista)
     * @param nombre -> Atributo de tipo String de Empleado
     * @return emp -> Objeto de tipo Empleado
     * @throws SQLException
     */
    public  Usuario consultaUsuario(String nombre) throws SQLException {
    	
    	Usuario emp = new Usuario();
	
		Statement st = conn.createStatement(); 
		
		ResultSet rs2 = st.executeQuery("Select * from User Where Nombre = '"+nombre+"';");                                                                                                                                                     // estado civil es estado, no string
	 	emp = new Usuario(rs2.getString("DNI"),rs2.getString("Nombre"),rs2.getString("Apellido1"),rs2.getString("Apellido2"),rs2.getString("NSS"),rs2.getString("Dir"),rs2.getString("CP"),rs2.getString("E_civil"),rs2.getInt("N_hijos"),rs2.getString("F_nac"),rs2.getString("F_alta"),rs2.getString("F_rev_med"),arrayNumTel,rs2.getString("observaciones"),rs2.getString("cargo"),rs2.getString("puesto"),rs2.getString("N_cuenta"));
	 				 
		rs2.close();
		return emp;
	}
     
     

	 		/**
	 		 * Método para CONSULTAR una lista de EMPLEADOS
	 		 * @return Empleado -> Lista de objetos de tipo Empleado
	 		 * @throws SQLException
	 		 */
	 		public List<Empleado> listaEmpleados()throws SQLException{
	 			final List<Empleado> Empleado = new ArrayList<Empleado>();
	 			
	 			Statement stat = conn.createStatement();
	 			ResultSet rs = stat.executeQuery("select * from EMPLEADO");
	 			
	 			while (rs.next()) {
	 				
	 				String []telf = new String[3];
	 				telf[0]=rs.getString("Telf1");
	 				telf[1]=rs.getString("Telf2");
	 				telf[2]=rs.getString("Telf3");
	 				
	 				final Empleado emp = new Empleado(rs.getString("DNI"), rs.getString("Nombre"),rs.getString("Apellido1"),rs.getString("Apellido2"),rs.getString("NSS"),rs.getString("Dir"),rs.getString("CP"),
	 						rs.getString("E_civil"),rs.getInt("N_hijos"),rs.getString("F_nac"),rs.getString("F_alta"),rs.getString("F_rev_med"),
	 						telf,rs.getString("Observaciones"),rs.getString("Cargo"),rs.getString("Puesto"),rs.getString("N_cuenta"));
	 	 	 		Empleado.add(emp);
	 			}
	 			
	 			rs.close();
	 			stat.close();
	 			return Empleado;
	 			
	 		}
	 		
	 		

	 	/**
	 	 * Método para BORRAR un EMPLEADO através de su DNI
	 	 * @param p -> Objeto de tipo Empleado de la BD
	 	 * @throws SQLException
	 	 */
	 	public void borrarEmpleado(Empleado p) throws SQLException{
	 	 		Statement stat = conn.createStatement();
	 	 	
	 	 		stat.executeUpdate("DELETE FROM EMPLEADO WHERE DNI='"+p.getDni()+"';");
	 	 		
	 	 		System.out.println("------------------");					
	 			System.out.println("EMPLEADO ELIMINADO");
	 			System.out.println("------------------");
	 			
	 	 		stat.close();
	 	 	}
	 	
	 	
	 	
	 	/**
	 	 * Método para CONSULTAR un EMPLEADO por Nombre de empleado
	 	 * @param nombreEmpleado
	 	 * @return empleado -> Objeto de tipo Empleado
	 	 * @throws SQLException
	 	 */
	 	public Empleado buscarEmpleado(String nombreEmpleado) throws SQLException {
	 		Statement stat = conn.createStatement();
	 		ResultSet rs = stat.executeQuery("select * from EMPLEADO where Nombre='"+nombreEmpleado+"'");
	 		Empleado empleado = null;
	 		while (rs.next()) {
	 			

 				String []telf = new String[3];
 				telf[0]=rs.getString("Telf1");
 				telf[1]=rs.getString("Telf2");
 				telf[2]=rs.getString("Telf3");
	 			
	 			empleado = new Empleado(rs.getString("DNI"), rs.getString("Nombre"),rs.getString("Apellido1"),rs.getString("Apellido2"),rs.getString("NSS"),rs.getString("Dir"),rs.getString("CP"),
 						rs.getString("E_civil"),rs.getInt("N_hijos"),rs.getString("F_nac"),rs.getString("F_alta"),rs.getString("F_rev_med"),
 						telf,rs.getString("Observaciones"),rs.getString("Cargo"),rs.getString("Puesto"),rs.getString("N_cuenta"));
	 			//Preguntar a elena
	 			//empleado = new Empleado(rs.getString("DNI"), rs.getString("Nombre"), rs.getString("Apellido1"), rs.getString("Apellido2"),rs.getString("NSS"),rs.getString("Dir"), rs.getString("CP"), rs.getString("E_civil"), rs.getInt("N_hijos"), rs.getDate("F_Nac"), rs.getDate("F_alta"), rs.getDate("F_rev_med"),rs.getArray("Telf1"), rs.getString("Observaciones"), rs.getString("Cargo"), rs.getString("Puesto"), rs.getString("N_cuenta"));
	 		}
	 		
	 		rs.close();
	 		stat.close();
	 		
			return empleado;
		}
	 	
	 	/**
	 	 * Método para MODIFICAR un EMPLEADO en la BD
	 	 * @param p -> Objeto de tipo Empleado
	 	 * @throws SQLException
	 	 */
	 	public void guardarEmpleado(Empleado p)throws SQLException{
	 		Statement stat = conn.createStatement();
	 		
	 		String sql ="UPDATE EMPLEADO SET DNI='"+p.getDni()+"',Nombre='"+p.getNombre()+"',Apellido1='"+p.getPrimerApellido()+"',Apellido2='"+p.getSegundoApellido()+"',NSS='"+p.getNSS()+
	 		"',Dir='"+p.getDireccion()+"',CP='"+p.getCodPostal()+"',E_civil='"+p.getEstadoCivil()+"',N_hijos="+p.getNumHijos()+"',F_nac='"+p.getFechaNacimiento()+"',F_alta='"+p.getFechaAltaEmpresa()+
	 		"',F_rev_med='"+p.getFechaUltimaRevision()+	"',F_baja='"+p.getFechaBaja()+"',Telf1="+p.getNumerosTelefono()[0]+"',Telf2="+p.getNumerosTelefono()[1]+"',Telf3="+p.getNumerosTelefono()[2]+
	 		"',Observaciones='"+p.getObservaciones()+"',Cargo='"+p.getCargo()+"',Puesto='"+p.getPuestoDeTrabajo()+"',N_cuenta='"+p.getNumeroDeCuenta()+	"' WHERE DNI='"+p.getDni()+"'";
	 		
	 		System.out.println(sql);

	 		
	 		
	 		System.out.println("-------------------");					
			System.out.println("EMPLEADO MODIFICADO");
			System.out.println("-------------------");
	 		stat.close();
	 	}
	 		
	 	
	
	 	
	 	//------------------------------- METODOS DE PEDIDOS ---------------------------------
	 	
	 	/**
	 	 * Método para CONSULTAR los PEDIDOS (Lista de Pedidos)
	 	 * @return pedidos -> Objeto de tipo Pedido
	 	 * @throws SQLException
	 	 */
	 	public List<Pedido> listaPedidos()throws SQLException{
	 		
	 		final List<Pedido> pedidos = new ArrayList<Pedido>();
	 		
	 		Statement stat = conn.createStatement();
	 		ResultSet rs = stat.executeQuery("select * from PEDIDO");
	 			
	 		System.out.println("Lista Pedidos");
	 		System.out.println("-------------");
	 		
	 		while (rs.next()) {
	 			final Pedido ped = new Pedido(rs.getString("Cod_ped"), rs.getString("Denom_ped"));
	 			pedidos.add(ped);
	 		}
	 		
	 		rs.close();	
	 		stat.close();
	 			
			return pedidos;			
	 	}
	 		
	 	
	 	/**
	 	 * Método para CONSULTAR un PEDIDO por Denominación del pedido (Nombre)
	 	 * @param nombrePedido -> Variable de tipo String
	 	 * @return pedido -> Objeto de tipo Pedido
	 	 * @throws SQLException
	 	 */
	 	public Pedido buscarPedido(String nombrePedido) throws SQLException {
	 		Statement stat = conn.createStatement();
	 		
	 		ResultSet rs = stat.executeQuery("select * from PEDIDO where Denom_ped='"+nombrePedido+"'");
	 		Pedido pedido = null;
	 		
	 		while (rs.next()) {
	 			pedido = new Pedido(rs.getString("Cod_ped"), rs.getString("Denom_ped"), rs.getString("CIF"));
	 		}
	 		
	 		rs.close();
	 		stat.close();
	 		
			return pedido;
		}
	 	
	 	
	 	
	 	/**
	 	 * Método para INSERTAR un PEDIDO
	 	 * @param p -> Objeto de tipo Pedido
	 	 * @throws SQLException
	 	 */
	 	public void CrearPedido(Pedido p) throws SQLException{
	 		
	 		Statement stat = conn.createStatement();
			
	 		stat.execute("INSERT INTO PEDIDO VALUES ('"+p.getCODPedido()+"','"+p.getDENOMPedido()+"','"+p.getCIFCliente()+"')");
	 		
	 		System.out.println("----------------");					
			System.out.println("PEDIDO INSERTADO");
			System.out.println("----------------");
			
			stat.close();
	 	}
	 	
	 	
	 	
	 	/**
	 	 * Método para MODIFICAR un PEDIDO en la BD
	 	 * @param p -> Objeto de tipo Pedido
	 	 * @throws SQLException
	 	 */
	 	public void guardarPedido(Pedido p)throws SQLException{
	 		Statement stat = conn.createStatement();
	 		String sql ="UPDATE PEDIDO SET "+"Cod_ped='"+p.getCODPedido()+"',Denom_ped='"+p.getDENOMPedido()+"',CIF='"+p.getCIFCliente()+"' WHERE Cod_ped='"+p.getCODPedido()+"'";
	 		
	 		System.out.println(sql);
	 		stat.execute(sql);
	 		
	 		System.out.println("------------------");					
			System.out.println("PEDIDO MODIFICADO");
			System.out.println("------------------");
	 		
	 		stat.close();
	 	}
	 	
	 	
	 	
	 	/**
	 	 * Método para BORRAR un PEDIDO através de su Denominación
	 	 * @param p -> Objeto de tipo Pedido
	 	 * @throws SQLException
	 	 */
	 	public void borrarPedido(String p, ArrayList[]a) throws SQLException{
	 		
	 		Statement stat = conn.createStatement();
	 		stat.executeUpdate("DELETE FROM Pedido WHERE Denom_ped='"+p+"'");
	 		
	 		System.out.println("----------------");					
 		System.out.println("PEDIDO ELIMINADO");
 		System.out.println("----------------");
 			
 		
 		
	 		stat.close();
	 	}

}
	 	
	 	
	 	
	 	