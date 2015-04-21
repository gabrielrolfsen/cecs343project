package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

import controllers.BuildController;
import utils.Types.BuildingTileType;

public class BuildView extends JDialog
					   implements ActionListener{
	private BuildController curBuildControl;
	
	private final int MAX_BUTTONS = 14;
	private JRadioButton[] btnBuilding;
	private ButtonGroup bgBuilding;
	private JButton btnPurchase;
	
	public BuildView(JFrame parentFrame, BuildController newBuildControl) {
		super(parentFrame, "Select Building", true);
		
		curBuildControl = newBuildControl;
		
		btnBuilding = new JRadioButton[MAX_BUTTONS];
		bgBuilding = new ButtonGroup();
		
		int i;
		for(i = 0; i < MAX_BUTTONS; i++) {
			btnBuilding[i] = new JRadioButton(BuildingTileType.getStringForInt(i));
			btnBuilding[i].addActionListener(this);
			this.add(btnBuilding[i]);
			bgBuilding.add(btnBuilding[i]);
		}
		
		btnPurchase = new JButton("Purchase");
		btnPurchase.setActionCommand(Integer.toString(-1));
		btnPurchase.addActionListener(this);
		this.add(btnPurchase);
	}
	
	public void actionPerformed(ActionEvent e) {
		int i;
		for (i = 0; i < MAX_BUTTONS; i++) {
			if(btnBuilding[i].isSelected()) {
				break;
			}
		}
		curBuildControl.playerChose(i);
	}
}
