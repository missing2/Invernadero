package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;

public class VentanaAltasBajas extends JFrame implements ActionListener, ComponentListener {
	public JTextField textNick;
	public JTextField textPass;
	public int boton;
	JButton btnCrear;
	JButton btnDarDeBaja;
	JButton btnSalir;
	
	public VentanaAltasBajas() {
		this.setSize(480, 280);
		getContentPane().setBackground(Color.BLACK);
		getContentPane().setLayout(null);
		
		textNick = new JTextField();
		textNick.setBounds(66, 61, 105, 20);
		getContentPane().add(textNick);
		textNick.setColumns(10);
		
		JLabel lblNick = new JLabel("nick:");
		lblNick.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNick.setForeground(Color.CYAN);
		lblNick.setBounds(10, 62, 46, 14);
		getContentPane().add(lblNick);
		
		textPass = new JTextField();
		textPass.setBounds(101, 135, 105, 20);
		getContentPane().add(textPass);
		textPass.setColumns(10);
		
		JLabel lblPassword = new JLabel("password:");
		lblPassword.setForeground(Color.CYAN);
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPassword.setBounds(10, 136, 91, 14);
		getContentPane().add(lblPassword);
		
	    btnCrear = new JButton("crear");
		btnCrear.setBounds(326, 87, 89, 23);
		getContentPane().add(btnCrear);
		btnCrear.addActionListener(this);
		
		btnDarDeBaja = new JButton("dar de baja");
		btnDarDeBaja.setBounds(326, 121, 89, 23);
		getContentPane().add(btnDarDeBaja);
		btnDarDeBaja.addActionListener(this);
		
		btnSalir = new JButton("salir");
		btnSalir.setBounds(326, 53, 89, 23);
		getContentPane().add(btnSalir);
		btnSalir.addActionListener(this);
	}

	
	
	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource().equals(btnCrear)){
			boton=1;
		}else if(e.getSource().equals(btnSalir)){
			boton=2;
		}else if(e.getSource().equals(btnDarDeBaja)){
			boton=3;
		}
	}
}
