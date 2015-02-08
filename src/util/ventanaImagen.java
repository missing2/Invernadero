package util;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ventanaImagen extends JFrame {
	public ventanaImagen(Placa p) {
		setBounds(100, 100, new ImageIcon(p.getImagen()).getIconWidth(), new ImageIcon(p.getImagen()).getIconHeight()+70);
		JLabel label = new JLabel(new ImageIcon(p.getImagen()));
		label.setSize(300, 300);
		this.getContentPane().add(label, BorderLayout.CENTER);
	}

}
