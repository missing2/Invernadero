package util;

public class Usuario {
private String nick;
private int contrasena;

public Usuario(String nick, int contrasena) {
	
	this.nick = nick;
	this.contrasena = contrasena;
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
}
