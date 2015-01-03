package std.webServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.BorderLayout;

import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class VentanaControl extends JFrame implements ActionListener, ComponentListener{
	private JTable table;
	private JButton btnEchar;
	public VentanaControl(DefaultTableModel tabla) {
		
		btnEchar = new JButton("Echar");
		getContentPane().add(btnEchar, BorderLayout.SOUTH);
		btnEchar.addActionListener(this);
		
		table = new JTable(tabla);
		getContentPane().add(table, BorderLayout.CENTER);
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
		// pasar al request que haga la consulta
    }

}





}