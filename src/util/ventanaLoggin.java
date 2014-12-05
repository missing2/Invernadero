package util;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;

public class ventanaLoggin extends JFrame implements ActionListener, ComponentListener{
	private static final long serialVersionUID = -2914651660379733387L;
	ventanaAccion vA;
	private final JLabel lblUser = new JLabel("USER:");
	public JTextField txtFUser;
	public JTextField txtFPasword;
	public int boton = 0;
	
public ventanaLoggin(){
	getContentPane().setBackground(Color.BLACK);	
	getContentPane().setLayout(null);
	lblUser.setFont(new Font("Tahoma", Font.BOLD, 19));
	lblUser.setForeground(Color.CYAN);
	lblUser.setBackground(Color.BLACK);
	lblUser.setBounds(26, 68, 76, 31);
	getContentPane().add(lblUser);
	
	txtFUser = new JTextField();
	txtFUser.setBackground(Color.LIGHT_GRAY);
	txtFUser.setBounds(97, 77, 171, 20);
	getContentPane().add(txtFUser);
	txtFUser.setColumns(10);
	
	JLabel lblPasword = new JLabel("PASWORD:");
	lblPasword.setBackground(Color.BLACK);
	lblPasword.setFont(new Font("Tahoma", Font.BOLD, 20));
	lblPasword.setForeground(Color.CYAN);
	lblPasword.setBounds(26, 160, 128, 31);
	getContentPane().add(lblPasword);
	
	txtFPasword = new JTextField();
	txtFPasword.setBackground(Color.LIGHT_GRAY);
	txtFPasword.setBounds(156, 169, 171, 20);
	getContentPane().add(txtFPasword);
	txtFPasword.setColumns(10);
	
	JButton btnLoggin = new JButton("Loggin");
	btnLoggin.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			boton=1;
			vA = new ventanaAccion() ;
		}
	});
	btnLoggin.setBounds(395, 94, 108, 43);
	getContentPane().add(btnLoggin);
	
	JButton btnSalir = new JButton("Salir");
	btnSalir.setBounds(412, 148, 76, 31);
	getContentPane().add(btnSalir);
	btnSalir.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			boton=2;
		}
	});
		this.setTitle("M&V");
		this.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width/2-800/2,Toolkit.getDefaultToolkit().getScreenSize().height/2-600/2, 800, 600);
		this.setSize(546,273);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	
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
	}
}
