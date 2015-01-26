package std.webServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JList;

import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import util.DataBaseControler;
                                                     // NECESITARA UN THREAD O ALGO, PERO QUE SE PUEDA COMUNICAR CON DATABASE Y CLIENTE
public class VentanaControl extends JFrame implements ActionListener, ComponentListener{
	
	private JTable table;
	private JButton btnEchar;
	public int boton = 0;
	public String nick;
	public static LinkedList<Request> listaRequest = new LinkedList<Request>(); // lista que contendra los requets de los clientes
	 public DataBaseControler base = DataBaseControler.getInstance();
	
	public VentanaControl() {
		 WebServer web = new WebServer();
	     web.start();
	     
	     this.setVisible(true);
		table = new JTable();
		getContentPane().add(table, BorderLayout.CENTER);
		
		btnEchar = new JButton("Echar");
		getContentPane().add(btnEchar, BorderLayout.SOUTH);
		btnEchar.addActionListener(this);
		this.setSize(309,314);
		
		try { // cargo la tabla de inicio de los clientes
			
			DefaultTableModel a = base.sacarUsuarios();
			this.cargarTabla(a);
			
		} catch (SQLException e) {
			System.out.println("fallo bd");
		}
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
	if(e.getSource().equals(btnEchar)){ 
		boton=1;
		int index = table.getSelectedRow();
		nick = (String) table.getValueAt(index, 1);
    }
	
}

public void cargarTabla(DefaultTableModel tabla){
	table.setModel(tabla);
	this.repaint();

}
public static void main(String[]args) {
	
	VentanaControl ventana = new VentanaControl();
	
}



}