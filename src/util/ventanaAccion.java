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
 
 public JList lista;
 
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
 
 String[] columTitulo =	{"Placa", "Sensor","Estado","Funci�n","Ultima acci�n"};
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

 public TableModel cargarTabla(JList a){
	 
	// cambiamos de nuestro jlist al array que usaremos para cargar la tabla
	  ArrayList<Object> array= new ArrayList();
	 for (int i=0;i<a.getModel().getSize();i++){
		array.add(a.getModel().getElementAt(i)) ;
	 }
	 	 
     
		 int fila=0;
	TableModel modelo = table.getModel();
	for (Object o :array) // cargo la tabla con la jlist devuelta por la bd
	{
		if(o instanceof Sensor){
			modelo.setValueAt(((Sensor) o).getId_sensor(), fila, 1);
			modelo.setValueAt(((Sensor) o).getUltima_accion(), fila, 4);
			modelo.setValueAt(((Sensor) o).getFuncion_principal(), fila, 3); 
		}else if(o instanceof Placa){
			modelo.setValueAt(((Placa) o).getId_placa(), fila, 0);
			modelo.setValueAt(((Placa) o).getEstado_placa(), fila, 2);
			
		}
		fila++;
	}// refresco de los datos de las filas restantes vacias
	while(fila<modelo.getRowCount()){ 
		modelo.setValueAt("", fila, 0);
		modelo.setValueAt("", fila, 1);
		modelo.setValueAt("", fila, 2);
		modelo.setValueAt("", fila, 3);
		modelo.setValueAt("", fila, 4);
		modelo.setValueAt("", fila, 5);
		modelo.setValueAt("", fila, 6);
		modelo.setValueAt("", fila, 7);
		modelo.setValueAt("", fila, 8);
		fila++;
	}
 return modelo;
	}
}