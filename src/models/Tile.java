package models;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import utils.*;


public class Tile extends JPanel{

	private static final int BOARD_HEIGHT = 600;
	private static final int BOARD_WIDTH = 800;
	protected JLabel lblTitle;
	protected TileEnum myType;
	
	private Dimension myDimension;
	private Point myPoint;
	
	public Tile() {
		super();

		int intHeight = BOARD_HEIGHT / 2 / 4;
		int intWidth = BOARD_WIDTH / 2 / 4;
		setSize(intWidth, intHeight);
		setLocation(0, 0);
		setVisible(false);
		setLayout(null);
		
		lblTitle = new JLabel();
		add(lblTitle);
		lblTitle.setLocation(0, 0);
		lblTitle.setSize(intWidth, intHeight / 2);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setVisible(true);
	}
	
	public void setType(TileEnum newType) {
		myType = newType;
	}
	
	public TileEnum getType() {
		return myType;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension d = getSize();
		Point p = getLocation();
		
		g.drawLine(0,  0, 0, d.height - 1);
		g.drawLine(0, d.height - 1, d.width - 1, d.height - 1);
		g.drawLine(d.width - 1, d.height - 1, d.width - 1, 0);
		g.drawLine(d.width - 1, 0, 0, 0);
		
		setSize(d);
		setLocation(p);
	} 

	//public ImageIcon getImageIcon() {
	public Icon getIcon() {
		Dimension d = getSize();
		
		BufferedImage bi = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bi.createGraphics();
		print(g);
		ImageIcon ii = new ImageIcon(bi);
		return ii;
	}
	
	public void setSize(int width, int height) {
		super.setSize(width, height);
		myDimension = super.getSize();
	}
	
	public void setLocation(int left, int top) {
		super.setLocation(left, top);
		myPoint = super.getLocation();
	}
	
	public void Refresh() {
		setLocation(myPoint);
		setSize(myDimension);
	}
}
