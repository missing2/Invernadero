package util;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class FondoMenuPrincipal extends JPanel  {
	/**
	 * 
	 */
	String url;
	private static final long serialVersionUID = 4330242536776306893L;
	public FondoMenuPrincipal(String url) {
		this.url=url;
		this.setSize(800,600);
    }
	  public void paintComponent (Graphics g){
	    	Dimension tam = getSize();
			ImageIcon imagenFondoPrincipal = new ImageIcon(getClass().getResource(url));
			g.drawImage(imagenFondoPrincipal.getImage(),0,0,tam.width, tam.height, null);
			paintComponents(g);
			setOpaque(false);
			super.paintComponents(g);
	    }

}
