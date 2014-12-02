package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JList;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ventanaAccion extends JFrame implements ActionListener, ComponentListener{
	private JTable table;

	public ventanaAccion(ArrayList lista){
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelTabla = new JPanel();
		getContentPane().add(panelTabla, BorderLayout.CENTER);
		
		table = new JTable();
		table.setModel(this.cambiarTabla(lista));
		panelTabla.add(table);
		
		JPanel panelBotonera = new JPanel();
		getContentPane().add(panelBotonera, BorderLayout.EAST);
		panelBotonera.setLayout(new BorderLayout(0, 0));
		
		JButton btnNewButton = new JButton("New button");
		panelBotonera.add(btnNewButton, BorderLayout.CENTER);
		
		JButton btnNewButton_1 = new JButton("New button");
		panelBotonera.add(btnNewButton_1, BorderLayout.SOUTH);
		
		JButton btnNewButton_2 = new JButton("New button");
		panelBotonera.add(btnNewButton_2, BorderLayout.NORTH);
		
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
	public DefaultListModel cambiarTabla(ArrayList lista) {
		DefaultListModel a = new DefaultListModel();
		
		for (int i = 0; i<=lista.size();i++ ){
			a.add(i, lista.get(i));
		}	
		
		return a;
	}
}
