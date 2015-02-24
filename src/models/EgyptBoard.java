import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class EgyptBoard extends JPanel {

	private Image AOMBoard;

	public EgyptBoard() {
		initBoard();

	}

	private void initBoard() {
		loadImage();

		int w = AOMBoard.getWidth(this);
		int h = AOMBoard.getHeight(this);

		// int w = 800;
		// int h = 600;

		setPreferredSize(new Dimension(w, h));
	}

	private void loadImage() {
		ImageIcon ii = new ImageIcon("bin/EgyptBoard9x7.png");
		AOMBoard = ii.getImage();

	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(AOMBoard, 0, 0, null);
	}

}
