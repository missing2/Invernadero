package util;

public class Sensor {

	public	String id_sensor;
	public String id_placa;
	public String def;
	public String ultima_accion;
	
	public Sensor(String id_sensor, String id_placa, String def,
			String ultima_accion) {
		super();
		this.id_sensor = id_sensor;
		this.id_placa = id_placa;
		this.def = def;
		this.ultima_accion = ultima_accion;
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
