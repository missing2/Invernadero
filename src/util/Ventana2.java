

 package util;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JRadioButton;

import java.awt.CardLayout;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.JSeparator;

import java.awt.Font;
import java.awt.Component;

import javax.swing.Box;

import java.awt.Dimension;

public class Ventana2 extends JFrame implements ActionListener, ComponentListener{
	public JTextField txtPalabraABuscar;
	public int opcion = 0; // controlador de que opcion de checks se va a escojer
	public int termina = 0; // si esta a 0 es que sigue metiendo datos/ 1 le a dado a buscar/ 2 le a dado a salir
	
	public Ventana2() {
		
		this.setTitle("M&V");
		getContentPane().setBackground(Color.BLACK);
		getContentPane().setLayout(null);
		
		ButtonGroup grupo = new ButtonGroup();	
		
		JRadioButton rdbtnPlaca = new JRadioButton("Placa");
		rdbtnPlaca.setFont(new Font("Tahoma", Font.BOLD, 14));
		rdbtnPlaca.setForeground(Color.CYAN);
		rdbtnPlaca.setBackground(Color.BLACK);
		rdbtnPlaca.setBounds(22, 48, 100, 30);
		grupo.add(rdbtnPlaca);
		getContentPane().add(rdbtnPlaca);
		
		JRadioButton rdbtnVariable = new JRadioButton("Variable");
		rdbtnVariable.setFont(new Font("Tahoma", Font.BOLD, 14));
		rdbtnVariable.setBackground(Color.BLACK);
		rdbtnVariable.setForeground(Color.CYAN);
		rdbtnVariable.setBounds(22, 88, 100, 30);
		grupo.add(rdbtnVariable);
		getContentPane().add(rdbtnVariable);
		
		JRadioButton rdbtnSensor = new JRadioButton("Sensor");
		rdbtnSensor.setFont(new Font("Tahoma", Font.BOLD, 14));
		rdbtnSensor.setBackground(Color.BLACK);
		rdbtnSensor.setForeground(Color.CYAN);
		rdbtnSensor.setBounds(22, 128, 100, 30);
		grupo.add(rdbtnSensor);
		getContentPane().add(rdbtnSensor);
		
		JRadioButton rdbtnFuncion = new JRadioButton("Funci\u00F3n");
		rdbtnFuncion.setFont(new Font("Tahoma", Font.BOLD, 14));
		rdbtnFuncion.setForeground(Color.CYAN);
		rdbtnFuncion.setBackground(Color.BLACK);
		rdbtnFuncion.setBounds(22, 168, 100, 30);
		grupo.add(rdbtnFuncion);
		getContentPane().add(rdbtnFuncion);
		
		JRadioButton rdbtnUltimaAccion = new JRadioButton("Ultima acci\u00F3n");
		rdbtnUltimaAccion.setFont(new Font("Tahoma", Font.BOLD, 14));
		rdbtnUltimaAccion.setForeground(Color.CYAN);
		rdbtnUltimaAccion.setBackground(Color.BLACK);
		rdbtnUltimaAccion.setBounds(22, 208, 139, 30);
		grupo.add(rdbtnUltimaAccion);
		getContentPane().add(rdbtnUltimaAccion);
		
		JButton btnBuscar = new JButton("buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				termina = 1;
			}
		});
		btnBuscar.setBackground(Color.LIGHT_GRAY);
		btnBuscar.setForeground(Color.BLACK);
		btnBuscar.setBounds(322, 123, 109, 44);
		getContentPane().add(btnBuscar);
		
		JButton btnSalir = new JButton("salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				termina = 2;
			}
		});
		btnSalir.setBounds(332, 180, 89, 23);
		getContentPane().add(btnSalir);
		
		txtPalabraABuscar = new JTextField();
		txtPalabraABuscar.setForeground(Color.BLACK);
		txtPalabraABuscar.setBackground(Color.LIGHT_GRAY);
		txtPalabraABuscar.setText("palabra a buscar...");
		txtPalabraABuscar.setBounds(269, 55, 211, 20);
		getContentPane().add(txtPalabraABuscar);
		txtPalabraABuscar.setColumns(10);
		
		while(1==1){ // buble infinito que controlara el check elegido
			if (rdbtnPlaca.isSelected()){   
				opcion = 1;
			}else if(rdbtnVariable.isSelected()){
				opcion = 2;
			}else if(rdbtnSensor.isSelected()){
				opcion = 3;
			}else if(rdbtnFuncion.isSelected()){
				opcion = 4;
			}else if (rdbtnUltimaAccion.isSelected()){
				opcion = 5;
			}
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
	
}
}
