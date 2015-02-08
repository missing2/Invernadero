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
import javax.swing.BoxLayout;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class ventanaAccion extends JFrame implements ActionListener, ComponentListener{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4079650453536016725L;
	
	public JTable table;
	JScrollPane scroll;

	JPanel panelInferior;
	JPanel panelCentral;
	JPanel panelBotonera;
	JPanel panelIzquierda;

	JScrollPane scrollLista; 
	JList listaVariables;

	public int boton = 0;
	public JButton bActivar,bDesactivar;
	JButton bImagenPlaca;
	JButton bActuar;
	JButton bSalir;
	JButton bBuscar;
	JButton btnActivarPlaca;
	JButton btnDesactivarPlaca;


	public JTextField palabra;
	JLabel lLab;

	public ArrayList<String> lista = new ArrayList<String>() ;

	public String  id;
	public String txt;
	public String lineaSelec;
	private JRadioButton rbApagar;
	private JRadioButton rbEncender;
	private JRadioButton rbBajar;
	private JRadioButton rbSubir;
	

	public ventanaAccion(){
		
		panelInferior = new JPanel();
		panelCentral = new JPanel();
		panelBotonera = new JPanel();
		panelIzquierda = new JPanel();

		palabra = new JTextField(10);
		palabra.setText("id_placa,p1");

		bActivar = new JButton ("Activar Sensor");
		bDesactivar = new JButton ("Desactivar Sensor");
		bImagenPlaca = new JButton ("Imagen Placa"); 
		bActuar = new JButton("Actuar");
		bBuscar = new JButton("Buscar");
		bSalir = new JButton("Salir");
		btnActivarPlaca = new JButton("Activar Placa");
		panelBotonera.add(btnActivarPlaca);
		
		btnDesactivarPlaca = new JButton("Desactivar placa");
		panelBotonera.add(btnDesactivarPlaca);
		

		panelBotonera.setLayout(new FlowLayout());
		panelBotonera.add(bActivar);
		panelBotonera.add(bDesactivar);
		panelBotonera.add(bImagenPlaca);
		panelBotonera.add(bActuar);


		panelInferior.setLayout(new FlowLayout());
		panelInferior.add(palabra);

		panelInferior.add(bBuscar);
		panelInferior.add(bSalir);
		panelCentral.setLayout(null);
		//panelCentral.add(table);


		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(panelBotonera,"North");
		
		
		
		
				table = new JTable();
				scroll = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				scroll.setBounds(10, 10, 690, 300);
				panelCentral.add(scroll);
		this.getContentPane().add(panelInferior,"South");
		this.getContentPane().add(panelIzquierda,"West");
		
		rbApagar = new JRadioButton("Apagar");
		
		rbEncender = new JRadioButton("Encender");
		
		rbBajar = new JRadioButton("Bajar");
		panelIzquierda.setLayout(new BoxLayout(panelIzquierda,BoxLayout.Y_AXIS));
		panelIzquierda.add(rbApagar);
		panelIzquierda.add(rbEncender);
		panelIzquierda.add(rbBajar);
		
		rbSubir = new JRadioButton("Subir");
		panelIzquierda.add(rbSubir);
		

		this.setSize(800,600);
		this.setResizable(false);
		this.setVisible(true);

		bActivar.addActionListener(this);
		bDesactivar.addActionListener(this);
		bActuar.addActionListener(this);
		bBuscar.addActionListener(this);
		bImagenPlaca.addActionListener(this);
		bSalir.addActionListener(this);
		btnDesactivarPlaca.addActionListener(this);
		btnActivarPlaca.addActionListener(this);
		
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		this.getContentPane().add(panelCentral,"Center");
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
			id = (String) table.getValueAt(index, 2);
			
		}else if(e.getSource().equals(bDesactivar)){
			boton = 2;
			int index = table.getSelectedRow();
			System.out.println(index);
			id = (String) table.getValueAt(index, 2);
			
		}else if(e.getSource().equals(bActuar)){
			boton = 3;
			int index = table.getSelectedRow();
			id = (String) table.getValueAt(index, 2);
			if (rbApagar.isSelected())
				txt = "apagar";
			else if (rbEncender.isSelected())
				txt = "encender";
			else if (rbBajar.isSelected())
				txt = "bajar";
			else if (rbSubir.isSelected())
				txt = "subir";

		}else if(e.getSource().equals(bBuscar)){
			boton = 4;
		}else if(e.getSource().equals(bImagenPlaca)){ // mirar si la columna es la correcta
			boton = 5;
			int index = table.getSelectedRow();
			id = (String) table.getValueAt(index, 1);
			Sensor s = new Sensor((String)table.getValueAt(index, 1), (String)table.getValueAt(index, 2), (String) table.getValueAt(index,3), (String) table.getValueAt(index,4), (String) table.getValueAt(index,5), (String) table.getValueAt(index,6));
			lineaSelec = s.toString();
		}else if(e.getSource().equals(bSalir)){
			boton = 6;
			
		}else if(e.getSource().equals(btnDesactivarPlaca)){
			boton = 7;
			int index = table.getSelectedRow();
			id = (String) table.getValueAt(index, 1);
			
		}else if(e.getSource().equals(btnActivarPlaca)){
			boton = 8;
			int index = table.getSelectedRow();
			id = (String) table.getValueAt(index, 1);
		}

	}


	public void cargarTabla(ArrayList<String> listae){
		
		if(listae.isEmpty()){
			System.out.println("vacia");
		}
		try{
		DefaultTableModel modelo = new DefaultTableModel();
		String[] columTitulo =	{" ","  Placa  ","  Id sensor  ", "  Sensor  ","  Ultima acción  ","  Estado  ","  Función  "};
		modelo.setColumnIdentifiers(columTitulo);
		String[] Sensor = new String[listae.size()];
		Sensor = listae.toArray(Sensor);
		String stringAtributos="";
		

		for(int i=0;i<Sensor.length;i++){
			stringAtributos= Sensor[i]; // separo los atributos de cada sensor
			String Atributo[]=stringAtributos.split(";");// separo atributos en el array
			Object[] o ={Atributo[0],Atributo[1],Atributo[2],Atributo[3],Atributo[4],Atributo[5],Atributo[6]};
			modelo.addRow(o);
		}
		
		modelo.fireTableDataChanged();
		table.setModel(modelo);
		repaint();
		}catch (Exception e2) {
			
		}

	}
}
