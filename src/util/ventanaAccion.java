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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ventanaAccion extends JFrame implements ActionListener, ComponentListener{
 
 private JTable table;
 JPanel panelSuperior;
 JPanel panelInferior;
 JPanel panelCentral;
 JPanel panelBotonera;
 JPanel panelListas;
 
 JScrollPane scrollLista; 
 JList listaVariables;
 
 JButton bActivar;
 JButton bImagenPlaca;
 JButton bActuar;
 JButton bSalir;
 JButton bBuscar;
 JButton bListar;
 
 JTextField palabraBuscar;

 public ventanaAccion(){
 
 panelSuperior = new JPanel();
 panelInferior = new JPanel();
 panelCentral = new JPanel();
 panelBotonera = new JPanel();
 panelListas = new JPanel();
 
 panelBotonera.setLayout(new BorderLayout());
 bActivar = new JButton ("On");
 bImagenPlaca = new JButton ("Imagen Placa"); 
 bActuar = new JButton("Actuar");
 panelBotonera.add(bActivar,"Center");
 panelBotonera.add(bImagenPlaca,"Center");
 panelBotonera.add(bActuar,"Center");
 
 panelInferior.setLayout(new FlowLayout());
 panelInferior.add(palabraBuscar);
 panelInferior.add(bBuscar);
 panelInferior.add(bListar);
 panelInferior.add(bSalir);
 
 
 this.getContentPane().setLayout(new BorderLayout());
 this.getContentPane().add(panelSuperior,"North");
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