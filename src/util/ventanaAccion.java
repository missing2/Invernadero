package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JList;

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
 
 private JTable table;
 JScrollPane scroll;

 JPanel panelInferior;
 JPanel panelCentral;
 JPanel panelBotonera;
 
 JScrollPane scrollLista; 
 JList listaVariables;
 
 public int boton = 0;
 public JButton bActivar;
 JButton bImagenPlaca;
 JButton bActuar;
 JButton bSalir;
 JButton bBuscar;
 
 public JRadioButton rdbPlaca;
 public JRadioButton rdbSensor;

 
 public JTextField palabra;
 JLabel lLab;
 
 public String lista = new String ("");
 
 public String  id;
 public String txt;
 
 public ventanaAccion(){
 
 panelInferior = new JPanel();
 panelCentral = new JPanel();
 panelBotonera = new JPanel();
 
 palabra = new JTextField(10);
 
 bActivar = new JButton ("On");
 bImagenPlaca = new JButton ("Imagen Placa"); 
 bActuar = new JButton("Actuar");
 bBuscar = new JButton("Buscar");
 bSalir = new JButton("Salir");
 
 panelBotonera.setLayout(new FlowLayout());
 panelBotonera.add(bActivar);
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
 
 String[] columTitulo =	{"Placa", "Sensor","Estado","Función","Ultima acción"};
 Object [][]data = new Object[15][5];	
 table = new JTable(data, columTitulo);
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
 bActuar.addActionListener(this);
 bBuscar.addActionListener(this);
 bImagenPlaca.addActionListener(this);
 bSalir.addActionListener(this);
 setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
 
 table.setModel(cargarTabla(lista));

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
		 // necesito saber la variable o algo de la fila seleccionada
		 int index = table.getSelectedRow();
		 id = (String) table.getValueAt(index, 1);
	 }else if(e.getSource().equals(bActuar)){
		 boton = 2;
		// necesito saber la variable o algo de la fila seleccionada
		 int index = table.getSelectedRow();
		 id = (String) table.getValueAt(index, 2);
		 txt = palabra.getText();
		 
	 }else if(e.getSource().equals(bBuscar)){
		 boton = 3;
		// necesito botones para saber en que columna buscar o que variable
	 }else if(e.getSource().equals(bImagenPlaca)){
		 boton = 4;
		 int index = table.getSelectedRow();
		 id = (String) table.getValueAt(index, 1);
//		String cads[]={imagenes de las placas};
//		 int ra= id; //aqui habria que seleccionarla dependiendo de la placa que sea
//		 URL url=this.getClass().getResource(cads[ra]);
//		 try {
//			 Image img=ImageIO.read(url); //leemos la imagen
//			 lLab.setIcon(new ImageIcon(img)); //la asignamos al JLabel de Java
//		 } catch (IOException e1) {
//			 e1.printStackTrace();
//		 }
//		 
//	 }else if(e.getSource().equals(bListar)){
//		 boton = 5;
//		 // creo que ya esta hecho por defecto en la tabla principal
	 }else if(e.getSource().equals(bSalir)){
		 boton = 6;
		 System.exit(0);
	 }

 
 }
 

 public static void main(String[] args)
 {
//	ventanaAccion v= new ventanaAccion();
//	v.setVisible(true);
 }

 public DefaultTableModel cargarTabla(String a){
	 
	 if(a.isEmpty()){
		 System.out.println(a);
		 System.out.println("null y tal");
	 }
	 
	 System.out.println("en el inicio de la ventana"+a);
		ArrayList<String> df = new ArrayList<String>();
		System.out.println("Array lista en el inicio: "+df);
		 
		String Sensor[] = a.split(",");// separo sensores
		
		for(int i=0;i<Sensor.length;i++){
			String stringAtributos= Sensor[i]; // separo los atributos de cada sensor
		System.out.println("lista separada ,"+Sensor[i]);
			String Atributo[]=stringAtributos.split("-");// separo atributos en el array
		System.out.println("sensor "+ Atributo[0]+", "+Atributo[1]+", "+Atributo[2]+", "+Atributo[3]+", "+Atributo[4]);
			df.add(Atributo[0]+Atributo[1]+Atributo[2]+Atributo[3]+Atributo[4]); // añado los atributos al arraylist
		}
		System.out.println(df);
		
	
	 	 int fila=0;
	 	 DefaultTableModel modelo = (DefaultTableModel) table.getModel();
	 	 System.out.println("dentro de la ventana creando la tabla"+df.get(0));
	 	 Object[] o ={df.get(0),df.get(1),df.get(2),df.get(3),df.get(4),df.get(5)};
	 	 modelo.addRow(o);
	 	 return modelo;
	
	 
 }
}