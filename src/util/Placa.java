package util;

import java.awt.Image;

public class Placa {
	public String id_placa;
	public String estado_placa;
	public Image imagen;

	public Placa(String id_placa, String estado_placa) {
		super();
		this.id_placa = id_placa;
		this.estado_placa = estado_placa;

	}

	public Placa(String id, Image imagen) {
		// TODO Auto-generated constructor stub
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

	public Image getImagen() {
		return imagen;
	}

	public void setImagen(Image imagen) {
		this.imagen = imagen;
	}

	@Override
	public String toString() {
		return id_placa + "," + estado_placa + "," + imagen;
	}

}
