package views;

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

import utils.Constants;
import utils.Types.ResourceTileType;
import utils.Types.ResourceType;

public class TileView extends JPanel {

	protected JLabel lblTitle;
	private ResourceTileType myType;

	private Dimension myDimension;
	private Point myPoint;

	public TileView() {
		setSize(Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
		setLocation(0, 0);
		// TODO: Why?!
		setVisible(false);
		setLayout(null);

		lblTitle = new JLabel();
		add(lblTitle);
		lblTitle.setLocation(0, 0);
		lblTitle.setSize(Constants.TILE_WIDTH, Constants.TILE_HEIGHT / 2);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setVisible(true);
	}

	public void setType(final ResourceTileType newType,
			final ResourceType resource) {
		this.myType = newType;
	}

	public ResourceTileType getType() {
		return this.myType;
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

	public Icon getIcon() {
		final Dimension d = getSize();

		final BufferedImage bi = new BufferedImage(d.width, d.height,
				BufferedImage.TYPE_INT_RGB);
		final Graphics2D g = bi.createGraphics();
		print(g);
		final ImageIcon ii = new ImageIcon(bi);
		return ii;
	}

	@Override
	public void setSize(final int width, final int height) {
		super.setSize(width, height);
		myDimension = super.getSize();
	}

	@Override
	public void setLocation(final int left, final int top) {
		super.setLocation(left, top);
		myPoint = super.getLocation();
	}

	// TODO: Why is it needed??
	/**
	 * Make the tile visible and set right Location.
	 */
	public void refresh() {
		this.setVisible(true);
		setLocation(myPoint);
		setSize(myDimension);
	}
}
