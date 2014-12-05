package util;

public class Sensor {
	
    public String id_placa;
	public String id_sensor;
	public String def;
	public String ultima_accion;
	public String estado;
	public String on_off;
	public String funcion_principal;
	
	
	public Sensor(String id_placa, String id_sensor, String def,
			String ultima_accion, String estado, String on_off, String funcion_principal) {
		super();
		this.id_placa = id_placa;
		this.id_sensor = id_sensor;
		this.def = def;
		this.ultima_accion = ultima_accion;
		this.estado = estado;
		this.on_off = on_off;
		this.funcion_principal = funcion_principal;
	}
	
	public String getFuncion_principal() {
		return funcion_principal;
	}

	public void setFuncion_principal(String funcion_principal) {
		this.funcion_principal = funcion_principal;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getOn_off() {
		return on_off;
	}

	public void setOn_off(String on_off) {
		this.on_off = on_off;
	}

	public String getId_sensor() {
		return id_sensor;
	}
	public void setId_sensor(String id_sensor) {
		this.id_sensor = id_sensor;
	}
	public String getId_placa() {
		return id_placa;
	}
	public void setId_placa(String id_placa) {
		this.id_placa = id_placa;
	}
	public String getDef() {
		return def;
	}
	public void setDef(String def) {
		this.def = def;
	}
	public String getUltima_accion() {
		return ultima_accion;
	}
	public void setUltima_accion(String ultima_accion) {
		this.ultima_accion = ultima_accion;
	}
	
	
	
}
