package old;

import java.awt.Dimension;
import java.awt.Color;

import javax.swing.JLabel;

import utils.*;
import views.ResourceCube;

public class TileRes extends TileAndy{
	private JLabel lblRes1, lblRes2;
	
	public TileRes() {
		super();
		
		final int ICON_SIZE = 30;
		Dimension d = getSize();
		
		lblRes1 = new JLabel();
		add(lblRes1);
		lblRes1.setSize(ICON_SIZE, ICON_SIZE);
		lblRes1.setLocation(15, d.height / 2);
		
		lblRes2 = new JLabel();
		add(lblRes2);
		lblRes2.setSize(ICON_SIZE, ICON_SIZE);
		lblRes2.setLocation(d.width - ICON_SIZE - 15, d.height / 2);
	}
	
	public void setType(TileEnum tileType) {
		super.setType(tileType);
		
		lblTitle.setForeground(Color.BLACK);
		switch(tileType){
		case FERTILE:
			setBackground(Color.WHITE);
			lblTitle.setText("Fertile");
			break;
		case FOREST:
			setBackground(Color.GREEN);
			lblTitle.setText("Forest");
			break;
		case HILL:
			setBackground(Color.CYAN);
			lblTitle.setText("Hill");
			break;
		case MOUNTAIN:
			setBackground(Color.BLUE);
			lblTitle.setText("Mountain");
			lblTitle.setForeground(Color.WHITE);
			break;
		case DESERT:
			setBackground(Color.YELLOW);
			lblTitle.setText("Desert");
			break;
		default:
			setBackground(Color.BLACK);
			lblTitle.setText("Swamp");
			lblTitle.setForeground(Color.WHITE);
		}
	}
	
	public void setRes(ResEnum resType, boolean blnTwoRes) {
		lblRes1.setIcon(new ResourceCube(resType));
		lblRes1.setVisible(true);
		
		lblRes2.setIcon(new ResourceCube(resType));
		if (blnTwoRes) {
			lblRes2.setVisible(true);
		} else {
			lblRes2.setVisible(false);
		}
	}
}
