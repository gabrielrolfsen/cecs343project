package views;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

import utils.Constants;

public class TileView extends JPanel {

	private Dimension mDimension;
	private Point mPoint;

	public TileView() {
		setSize(Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
		setLocation(0, 0);

		setLayout(null);

		this.setVisible(true);
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		final Dimension d = getSize();
		final Point p = getLocation();

		g.drawLine(0, 0, 0, d.height - 1);
		g.drawLine(0, d.height - 1, d.width - 1, d.height - 1);
		g.drawLine(d.width - 1, d.height - 1, d.width - 1, 0);
		g.drawLine(d.width - 1, 0, 0, 0);

		setSize(d);
		setLocation(p);
	}

	@Override
	public void setSize(final int width, final int height) {
		super.setSize(width, height);
		mDimension = super.getSize();
	}

	@Override
	public void setLocation(final int left, final int top) {
		super.setLocation(left, top);
		mPoint = super.getLocation();
	}

	/**
	 * Make the tile visible and set right Location.
	 */
	public void refresh() {
		setLocation(mPoint);
		setSize(mDimension);
	}
}
