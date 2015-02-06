package std.webServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import util.DataBaseControler;

import javax.swing.JPanel;
import javax.swing.BoxLayout;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.io.IOException;
                                                     
public class VentanaControl extends JFrame implements ActionListener, ComponentListener{
	
	private JTable table;
	private JButton btnEchar;
	JButton btnActualizar;
	public String nick;
	public static LinkedList<Request> listaRequest = new LinkedList<Request>(); // lista que contendra los requets de los clientes
	public static LinkedList<String> listanombres = new LinkedList<String>(); // lista que contendra el orden de insercion de los clientes
	 public DataBaseControler base = DataBaseControler.getInstance();
	
	public VentanaControl() throws ClassNotFoundException {
		
		 WebServer web = new WebServer();
	     web.start();
	     
	    this.setVisible(true);
		table = new JTable();
		getContentPane().add(table, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnEchar = new JButton("Echar");
		panel.add(btnEchar);
		btnEchar.addActionListener(this);
		
	    btnActualizar = new JButton("Actualizar");
		panel.add(btnActualizar);
		btnActualizar.addActionListener(this);
		
		this.setSize(309,314);
		
		try { // cargo la tabla de inicio de los clientes
		
			this.cargarTabla();
			
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
		
		int index = table.getSelectedRow();
		nick = (String) table.getValueAt(index, 0);
		try {
			base.conectar();
			base.echarUsuario(nick);
			base.desconectar();
			int pos = 0;
		
			for (int i=0; i<listanombres.size(); i++) {				
			 if (nick.equals(this.listanombres.get(i)))
					 pos=i;
			}
		
			Request aeliminar = this.listaRequest.get(pos);
			JOptionPane.showMessageDialog(this, "expulsado"+nick);
			aeliminar.sockManager.CerrarSocket();
			this.listanombres.remove(pos);
			this.listaRequest.remove(pos);
			
			
		} catch (SQLException | IOException | ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
    }else if(e.getSource().equals(btnActualizar)){
    	try {
			this.cargarTabla();
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
		
}
public void cargarTabla() throws SQLException, ClassNotFoundException{

	base.conectar();
	DefaultTableModel a = base.sacarUsuarios();
	base.desconectar();
	
	table.setModel(a);
	this.repaint();

}
public static void main(String[]args) throws ClassNotFoundException {
	
	VentanaControl ventana = new VentanaControl();
	
}



}
