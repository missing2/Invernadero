package util;

public class Variable {

	String id;
	String def;
	String estado;
	String id_placa;
	String funcion;
	String ultima_accion;
	
	
	
	public Variable(String id, String def, String estado, String id_placa,
			String funcion, String ultima_accion) {
	
		this.id = id;
		this.def = def;
		this.estado = estado;
		this.id_placa = id_placa;
		this.funcion = funcion;
		this.ultima_accion = ultima_accion;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDef() {
		return def;
	}
	public void setDef(String def) {
		this.def = def;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getId_placa() {
		return id_placa;
	}
	public void setId_placa(String id_placa) {
		this.id_placa = id_placa;
	}
	public String getFuncion() {
		return funcion;
	}
	public void setFuncion(String funcion) {
		this.funcion = funcion;
	}
	public String getUltima_accion() {
		return ultima_accion;
	}
	public void setUltima_accion(String ultima_accion) {
		this.ultima_accion = ultima_accion;
	}
	
}
