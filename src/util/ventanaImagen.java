package util;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class ventanaImagen extends JFrame {
	public ventanaImagen(String url) {
		FondoMenuPrincipal imagen = new FondoMenuPrincipal(url);
		getContentPane().add(imagen, BorderLayout.CENTER);
	}

}
