package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JList;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ventanaAccion extends JFrame implements ActionListener, ComponentListener{
 
 private JTable table;
 JScrollPane scroll;

 JPanel panelInferior;
 JPanel panelCentral;
 JPanel panelBotonera;
 JPanel panelListas;
 
 JScrollPane scrollLista; 
 JList listaVariables;
 
 int boton = 0;
 JButton bActivar;
 JButton bImagenPlaca;
 JButton bActuar;
 JButton bSalir;
 JButton bBuscar;
 JButton bListar;
 
 JTextField palabraBuscar;

 public ventanaAccion(){
 
 panelInferior = new JPanel();
 panelCentral = new JPanel();
 panelBotonera = new JPanel();
 panelListas = new JPanel();
 
 palabraBuscar = new JTextField(10);
 
 bActivar = new JButton ("On");
 bImagenPlaca = new JButton ("Imagen Placa"); 
 bActuar = new JButton("Actuar");
 bBuscar = new JButton("Buscar");
 bListar = new JButton("Listado");
 bSalir = new JButton("Salir");
 
 panelBotonera.setLayout(new FlowLayout());
 panelBotonera.add(bActivar);
 panelBotonera.add(bImagenPlaca);
 panelBotonera.add(bActuar);

 
 panelInferior.setLayout(new FlowLayout());
 panelInferior.add(palabraBuscar);
 panelInferior.add(bBuscar);
 panelInferior.add(bListar);
 panelInferior.add(bSalir);
 
 panelCentral.add(panelListas, "Center");
 
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
 bListar.addActionListener(this);
 bSalir.addActionListener(this);
 setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );

 }
 private boolean enActivado = false;

	// Método privado utilizado para activar o desactivar los componentes de acuerdo al modo de activación
	private void setModoActivado( boolean on ) {
		enActivado = on;
		bActuar.setEnabled( !on );
		bImagenPlaca.setEnabled( !on );
		bBuscar.setEnabled( !on );
		bListar.setEnabled( !on );
		listaVariables.setEnabled( !on );
		//nick.setEnabled( on );
		if (on)
			bActivar.setText( "OFF" );
		else
			bActivar.setText( "ON" );
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
	 boton = 1;
	 if(e.getSource().equals(bActivar)){
		 if(!enActivado){
			 setModoActivado(true);
			 //aqui cambiar el valor de la tabla a off
		 }else{
			 //aqui a on
		 }
		 
	 }else if(e.getSource().equals(bActuar)){
		 boton = 2;
	 }else if(e.getSource().equals(bBuscar)){
		 boton = 3;
		 
	 }else if(e.getSource().equals(bImagenPlaca)){
		 boton = 4;
		 
	 }else if(e.getSource().equals(bListar)){
		 boton = 5;
		 
	 }else if(e.getSource().equals(bSalir)){
		 boton = 6;
		 System.exit(0);
	 }

 
 }
 public DefaultListModel cambiarTabla(ArrayList lista) {
 DefaultListModel a = new DefaultListModel();
 
 for (int i = 0; i<=lista.size();i++ ){
 a.add(i, lista.get(i));
 } 
 
 return a;
 }
 public static void main(String[] args)
 {
	ventanaAccion v= new ventanaAccion();
	v.setVisible(true);
 }

}

/*// listener de la lista  lo que hace es crear la tabla de la derecha
public void cargarTabla(){

String nombre= (String) lnombres.getSelectedValue();	aqui en vez de la lista de nombres seria el campo de la tabla
	
ConexBD prueba = ConexBD.getInstance();

try {
	prueba.conectar();
	 ArrayList = prueba.datosObras(nombre); prueba.metodo
	prueba.desconectar();
	TableModel modelo = table.getModel();
	int fila=0;
	
	for (Placa p:ArrayList || Sensor s:ArrayList) // cargo la tabla con la jlist devuelta por la bd
	{
		modelo.setValueAt(p.get..., fila, 0);
		modelo.setValueAt(p.get..., fila, 1);
		modelo.setValueAt(p.get..., fila, 2);
		modelo.setValueAt(o.getLugarRealizacion(), fila, 3);
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
	
} catch (ClassNotFoundException | SQLException e) {
	// TODO Auto-generated catch block
	System.out.println("Algun error con la conexion BD");
	e.printStackTrace();
}

}
*/