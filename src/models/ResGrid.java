package models;

import javax.swing.JFrame;
import javax.swing.JPanel;

import utils.*;

public class ResGrid extends TileGrid {

	public ResGrid(JPanel jfrm, TileGridEnum newType) {
		super(jfrm, newType);
	}
	
	public void setCulture(CulEnum newCul) {
		switch(newCul) {
		case GREEK:
			initGreek();
			break;
		case EGYPTIAN:
			initEgyptian();
			break;
		default:
			initNorse();
		}
	}
	
	private void initGreek() {
		TileArray[0].setType(TileEnum.FERTILE);
		TileArray[1].setType(TileEnum.FERTILE);
		TileArray[2].setType(TileEnum.FOREST);
		TileArray[3].setType(TileEnum.SWAMP);
		TileArray[4].setType(TileEnum.HILL);
		TileArray[5].setType(TileEnum.MOUNTAIN);
		TileArray[6].setType(TileEnum.FERTILE);
		TileArray[7].setType(TileEnum.FOREST);
		TileArray[8].setType(TileEnum.HILL);
		TileArray[9].setType(TileEnum.HILL);
		TileArray[10].setType(TileEnum.HILL);
		TileArray[11].setType(TileEnum.HILL);
		TileArray[12].setType(TileEnum.DESERT);
		TileArray[13].setType(TileEnum.HILL);
		TileArray[14].setType(TileEnum.HILL);
		TileArray[15].setType(TileEnum.HILL);
	}
	
	private void initEgyptian() {
		TileArray[0].setType(TileEnum.DESERT);
		TileArray[1].setType(TileEnum.DESERT);
		TileArray[2].setType(TileEnum.SWAMP);
		TileArray[3].setType(TileEnum.SWAMP);
		TileArray[4].setType(TileEnum.FOREST);
		TileArray[5].setType(TileEnum.DESERT);
		TileArray[6].setType(TileEnum.FERTILE);
		TileArray[7].setType(TileEnum.FERTILE);
		TileArray[8].setType(TileEnum.DESERT);
		TileArray[9].setType(TileEnum.DESERT);
		TileArray[10].setType(TileEnum.FERTILE);
		TileArray[11].setType(TileEnum.FERTILE);
		TileArray[12].setType(TileEnum.DESERT);
		TileArray[13].setType(TileEnum.HILL);
		TileArray[14].setType(TileEnum.FERTILE);
		TileArray[15].setType(TileEnum.HILL);
	}
	
	private void initNorse() {
		TileArray[0].setType(TileEnum.FERTILE);
		TileArray[1].setType(TileEnum.MOUNTAIN);
		TileArray[2].setType(TileEnum.MOUNTAIN);
		TileArray[3].setType(TileEnum.MOUNTAIN);
		TileArray[4].setType(TileEnum.FERTILE);
		TileArray[5].setType(TileEnum.FOREST);
		TileArray[6].setType(TileEnum.HILL);
		TileArray[7].setType(TileEnum.MOUNTAIN);
		TileArray[8].setType(TileEnum.HILL);
		TileArray[9].setType(TileEnum.SWAMP);
		TileArray[10].setType(TileEnum.FOREST);
		TileArray[11].setType(TileEnum.HILL);
		TileArray[12].setType(TileEnum.DESERT);
		TileArray[13].setType(TileEnum.FOREST);
		TileArray[14].setType(TileEnum.FOREST);
		TileArray[15].setType(TileEnum.FERTILE);
	}
}
