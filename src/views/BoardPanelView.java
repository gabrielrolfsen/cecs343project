package views;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JPanel;

import models.Board;

public class BoardPanelView extends JPanel {
	private final int MAX_DIMENSION = 2;
	private JPanel[][] panelHolder;
	
	private TilePanelView productionGrid;
	private TilePanelView buildingGrid;
	private Image BackgroundImage;
	
	public BoardPanelView() {
		super();
		setLayout(new GridLayout(MAX_DIMENSION, MAX_DIMENSION));
		
		productionGrid = new TilePanelView(TilePanelView.TileType.PRODUCTION);
		buildingGrid = new TilePanelView(TilePanelView.TileType.BUILDING);
		
		panelHolder = new JPanel[MAX_DIMENSION][MAX_DIMENSION];
		int i, j;
		for (i = 0; i < MAX_DIMENSION; i++) {
			for (j = 0; j < MAX_DIMENSION; j++) {
				add(panelHolder[i][j]);
			}
		}
		panelHolder[1][0] = productionGrid;
		panelHolder[1][1] = buildingGrid;
	}
	
	public void setBoard(final Board newBoard) {
		productionGrid.setBoard(newBoard);
		buildingGrid.setBoard(newBoard);
		BackgroundImage = newBoard.getIcon().getImage();
		
		this.repaint();
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(BackgroundImage, 0, 0, null);

		productionGrid.repaint();
		buildingGrid.repaint();
	}
}
