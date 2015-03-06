package old;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import utils.*;


public class TileBuild extends TileAndy {

	private JLabel lblDesc;
	
	public TileBuild() {
		super();
		
		setBackground(Color.LIGHT_GRAY);

		lblDesc = new JLabel();
		add(lblDesc);
		Dimension myDimension = getSize();
		lblDesc.setLocation(0, myDimension.height / 2);
		lblDesc.setSize(myDimension.width, myDimension.height / 2);
		lblDesc.setHorizontalAlignment(SwingConstants.CENTER);
		lblDesc.setFont(new Font("Serif", Font.PLAIN, 9));
		lblDesc.setVisible(true);

		setType(TileEnum.NONE);
	}
	
	public void setType(TileEnum newType) {
		super.setType(newType);
		
		switch(newType) {
		case HOUSE:
			lblTitle.setText("House");
			lblDesc.setText("Villager");
			break;
		case WALL:
			lblTitle.setText("Wall");
			lblDesc.setText("Defend City");
			break;
		case TOWER:
			lblTitle.setText("Tower");
			lblDesc.setText("<html><center>Defend Production</center></html>");
			break;
		case STOREHOUSE:
			lblTitle.setText("Storehouse");
			lblDesc.setText("<html><center>Store Resource Cubes</center></html>");
			break;
		case MARKET:
			lblTitle.setText("Market");
			lblDesc.setText("Free Trade");
			break;
		case ARMORY:
			lblTitle.setText("Armory");
			lblDesc.setText("+1 Unit in Battle");
			break;
		case QUARRY:
			lblTitle.setText("Quarry");
			lblDesc.setText("<html><center>-1 Cost per Building</center></html>");
			break;
		case MONUMENT:
			lblTitle.setText("Monument");
			lblDesc.setText("+2 Favor / Gather");
			break;
		case GRANARY:
			lblTitle.setText("Granary");
			lblDesc.setText("+2 Food / Gather");
			break;
		case MINT:
			lblTitle.setText("Gold Mint");
			lblDesc.setText("+2 Gold / Gather");
			break;
		case WORKSHOP_WOOD:
			lblTitle.setText("<html><center>Wood Workshop</center><html>");
			lblDesc.setText("+2 Wood / Gather");
			break;
		case WORKSHOP_SIEGE:
			lblTitle.setText("<html><center>Siege Engine Workshop</center><html>");
			lblDesc.setText("<html><center>Negates Walls and Towers +1 Building Destroyed</center><html>");
			break;
		case TEMPLE:
			lblTitle.setText("Great Temple");
			lblDesc.setText("<html><center>Trade 8 favor for 1 Victory Point</center></html>");
			break;
		case WONDER:
			lblTitle.setText("The Wonder");
			lblDesc.setText("End Game");
			break;
		default:
			lblTitle.setText("None");
			lblDesc.setText("None");
		}
	}
}
