package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JList;

import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JRadioButton;

public class ventanaAccion extends JFrame implements ActionListener, ComponentListener{

	
	public JTable table;
	JScrollPane scroll;

	JPanel panelInferior;
	JPanel panelCentral;
	JPanel panelBotonera;

	JScrollPane scrollLista; 
	JList listaVariables;

	public int boton = 0;
	public JButton bActivar,bDesactivar;
	JButton bImagenPlaca;
	JButton bActuar;
	JButton bSalir;
	JButton bBuscar;

	public JRadioButton rdbPlaca;
	public JRadioButton rdbSensor;


	public JTextField palabra;
	JLabel lLab;

	public ArrayList<String> lista = new ArrayList<String>() ;

	public String  id;
	public String txt;

	public ventanaAccion(){
		
		panelInferior = new JPanel();
		panelCentral = new JPanel();
		panelBotonera = new JPanel();

		palabra = new JTextField(10);

		bActivar = new JButton ("ON");
		bDesactivar = new JButton ("OFF");
		bImagenPlaca = new JButton ("Imagen Placa"); 
		bActuar = new JButton("Actuar");
		bBuscar = new JButton("Buscar");
		bSalir = new JButton("Salir");

		panelBotonera.setLayout(new FlowLayout());
		panelBotonera.add(bActivar);
		panelBotonera.add(bDesactivar);
		panelBotonera.add(bImagenPlaca);
		panelBotonera.add(bActuar);


		panelInferior.setLayout(new FlowLayout());
		panelInferior.add(palabra);

		rdbPlaca = new JRadioButton("Placa");
		panelInferior.add(rdbPlaca);

		rdbSensor = new JRadioButton("Sensor");
		panelInferior.add(rdbSensor);
		panelInferior.add(bBuscar);
		panelInferior.add(bSalir);


		table = new JTable();
		scroll = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panelCentral.add(scroll);
		scroll.setVisible(true);


		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(panelBotonera,"North");
		this.getContentPane().add(panelCentral,"Center");
		this.getContentPane().add(panelInferior,"South");

		this.setSize(600,320);
		this.setResizable(false);
		this.setVisible(true);

		bActivar.addActionListener(this);
		bDesactivar.addActionListener(this);
		bActuar.addActionListener(this);
		bBuscar.addActionListener(this);
		bImagenPlaca.addActionListener(this);
		bSalir.addActionListener(this);
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );


		
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

		if(e.getSource().equals(bActivar)){
			boton = 1;
			int index = table.getSelectedRow();
			id = (String) table.getValueAt(index, 1);
			
		}else if(e.getSource().equals(bDesactivar)){
			boton = 2;
			int index = table.getSelectedRow();
			id = (String) table.getValueAt(index, 1);
			
		}else if(e.getSource().equals(bActuar)){
			boton = 3;
			int index = table.getSelectedRow();
			id = (String) table.getValueAt(index, 2);
			txt = palabra.getText();

		}else if(e.getSource().equals(bBuscar)){
			boton = 4;
		}else if(e.getSource().equals(bImagenPlaca)){
			boton = 5;
			int index = table.getSelectedRow();
			id = (String) table.getValueAt(index, 1);
			
		}else if(e.getSource().equals(bSalir)){
			boton = 6;
			System.exit(0);
		}


	}


	public void cargarTabla(ArrayList<String> listae){
		
		if(listae.isEmpty()){
			System.out.println(listae);
			System.out.println("null y tal");
		}

		DefaultTableModel modelo = new DefaultTableModel();
		String[] columTitulo =	{"Placa","Id sensor", "Sensor","Función","Estado","Ultima acción"};
		modelo.setColumnIdentifiers(columTitulo);
		String[] Sensor = new String[listae.size()];
		Sensor = listae.toArray(Sensor);
		String stringAtributos="";
		

		for(int i=0;i<Sensor.length;i++){
			stringAtributos= Sensor[i]; // separo los atributos de cada sensor
			String Atributo[]=stringAtributos.split(";");// separo atributos en el array
			Object[] o ={Atributo[0],Atributo[1],Atributo[2],Atributo[3],Atributo[4],Atributo[5]};
			modelo.addRow(o);
		}
		
		modelo.fireTableDataChanged();
		table.setModel(modelo);
		repaint();
		

	}
}
