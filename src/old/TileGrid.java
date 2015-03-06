package old;

import java.awt.Insets;

import javax.swing.JPanel;

import utils.Constants;

public class TileGrid {

	public TileAndy[] TileArray;

	public TileGrid(final JPanel jfrm, final TileGridEnum newType) {
		TileArray = new TileAndy[16];

		final Insets insFrame = jfrm.getInsets();

		int i, j, k;
		int intTop = Constants.GRID_TOP - insFrame.top;
		int intLeft;
		int intLeftTemp;
		final int intHeightInc = (Constants.BOARD_HEIGHT - insFrame.top) / 2 / 4 + 2;
		final int intWidthInc = (Constants.BOARD_WIDTH - insFrame.left * 2) / 2 / 4;

		if (newType == TileGridEnum.BUILDING) {
			intLeft = Constants.GRID_LEFT - insFrame.left;
		} else {
			intLeft = 0;
		}

		intLeftTemp = intLeft;

		k = 0;
		for (i = 0; i < 4; i++) {

			for (j = 0; j < 4; j++) {

				if (newType == TileGridEnum.BUILDING) {
					TileArray[k] = new TileBuild();
				} else {
					TileArray[k] = new TileRes();
				}

				jfrm.add(TileArray[k]);
				TileArray[k].setSize(intWidthInc, intHeightInc);
				TileArray[k].setLocation(intLeftTemp, intTop);

				intLeftTemp += intWidthInc;
				k++;
			}

			intTop += intHeightInc;
			intLeftTemp = intLeft;
		}
	}

	public void Refresh() {
		int i;
		for (i = 0; i < 16; i++) {
			TileArray[i].Refresh();
		}
	}
}
