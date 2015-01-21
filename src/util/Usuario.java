package util;

public class Usuario {


private String nick;
private int contrasena;
private String estado;

public Usuario(String nick, int contrasena) {
	
	this.nick = nick;
	this.contrasena = contrasena;
}
public Usuario(String nick, String estado){
	
	this.nick = nick;
	this.estado = estado;
	
}

public Usuario() {
	
}

public String getNick() {
	return nick;
}

public void setNick(String nick) {
	this.nick = nick;
}

public int getContrasena() {
	return contrasena;
}

public void setContrasena(int contrasena) {
	this.contrasena = contrasena;
}
public String getEstado() {
	return estado;
}
public void setEstado(String estado) {
	this.estado = estado;
}

}
