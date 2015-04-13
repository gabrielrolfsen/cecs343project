package views;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import controllers.ExploreControl;
import models.ExploreModel;
import utils.Constants;

public class ExploreView extends JFrame
						 implements ActionListener{
	private ExploreModel exploreModel = null;
	private ExploreControl exploreControl = null;
	
	private final int MAX_BUTTONS = 18;
	private JButton[] tileButtonArray;
	private JButton buttonPass;
	
	public ExploreView(ExploreModel curExploreModel, ExploreControl curExploreControl) {
		final int MAX_ROWS = 3;
		final int MAX_COLUMNS = 6;
		final int MIN_LEFT = 5;
		final int MIN_TOP = 5;
		final int BUTTON_SPACE = 5;
		final int VIEW_WIDTH = 725;
		final int VIEW_HEIGHT = 425;
		final int PASS_WIDTH = 100;
		final int PASS_HEIGHT = 50;

		
		exploreModel = curExploreModel;
		exploreControl = curExploreControl;
		
		this.setLayout(null);
		this.setSize(VIEW_WIDTH, VIEW_HEIGHT);
		this.setVisible(false);
		
		
		tileButtonArray = new JButton[MAX_BUTTONS];
		int i, j;
		int k = 0;
		int curLeft = MIN_LEFT;
		int curTop = MIN_TOP;
		for (i = 0; i < MAX_ROWS; i++) {
			
			for (j = 0; j < MAX_COLUMNS; j++) {
				tileButtonArray[k] = new JButton();
				tileButtonArray[k].setActionCommand(Integer.toString(k));
				tileButtonArray[k].addActionListener(this);
				this.add(tileButtonArray[k]);
				tileButtonArray[k].setBounds(curLeft, curTop, Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
				tileButtonArray[k].setVisible(true);
				curLeft = curLeft + BUTTON_SPACE + Constants.TILE_WIDTH;
				k++;
			}
			
			curLeft = MIN_LEFT;
			curTop = curTop + BUTTON_SPACE + Constants.TILE_HEIGHT;
		}
		
		
		buttonPass = new JButton("Pass");
		buttonPass.setActionCommand(Integer.toString(-1));
		buttonPass.addActionListener(this);
		this.add(buttonPass);
		curLeft = (VIEW_WIDTH / 2) - (PASS_WIDTH / 2) - 7;
		buttonPass.setBounds(curLeft, curTop, PASS_WIDTH, PASS_HEIGHT);
		buttonPass.setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		exploreControl.playerChose(Integer.parseInt(e.getActionCommand()));
	}
	
	@Override
	public void paint(final Graphics g) {
		super.paint(g);
		
		int i;
		for (i = 0; i < MAX_BUTTONS; i++) {
			if (exploreModel.tileArray[i] == null) {
				tileButtonArray[i].setVisible(false);
			} else {
				tileButtonArray[i].setVisible(true);
				tileButtonArray[i].setIcon(exploreModel.tileArray[i].getIcon());
			}
		}
	}
}
