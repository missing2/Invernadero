package util;

public class Variable {

	int id;
	String def;
	String estado;
	
	
	public Variable(int id, String def, String estado) {
		this.id = id;
		this.def = def;
		this.estado = estado;
	}
	
	public Variable() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	
}
