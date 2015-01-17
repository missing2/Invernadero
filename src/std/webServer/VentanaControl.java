package std.webServer;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.BorderLayout;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
                                                     // NECESITARA UN THREAD O ALGO, PERO QUE SE PUEDA COMUNICAR CON DATABASE Y CLIENTE
public class VentanaControl extends JFrame implements ActionListener, ComponentListener{
	
	private JTable table;
	private JButton btnEchar;
	public int boton = 0;
	public String nick;
	public VentanaControl() {
		
		table = new JTable();
		getContentPane().add(table, BorderLayout.CENTER);
		
		btnEchar = new JButton("Echar");
		getContentPane().add(btnEchar, BorderLayout.SOUTH);
		btnEchar.addActionListener(this);
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
	repaint();
	

}




}