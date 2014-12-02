package util;

import java.awt.Image;

public class Placa {
public String id_placa;
public String estado_placa;
public String imagen;

public Placa(String id_placa, String estado_placa, String imagen) {
	super();
	this.id_placa = id_placa;
	this.estado_placa = estado_placa;
	this.imagen = imagen;
}
public String getId_placa() {
	return id_placa;
}
public void setId_placa(String id_placa) {
	this.id_placa = id_placa;
}
public String getEstado_placa() {
	return estado_placa;
}
public void setEstado_placa(String estado_placa) {
	this.estado_placa = estado_placa;
}
public String getImagen() {
	return imagen;
}
public void setImagen(String imagen) {
	this.imagen = imagen;
}
}
