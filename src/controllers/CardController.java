/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Apr 10, 2015
 */
package controllers;

import java.util.ArrayList;
import java.util.Arrays;

import models.BattleCard;
import models.Card;
import models.Player;
import models.ResourceBank;
import utils.Types.ResourceCubeType;
import views.MainFrameView;

/**
 * @author grolfsen
 *
 */
public class CardController {

	private final static CardController mInstance = new CardController();
	private final MainFrameView mainFrame = MainFrameView.getInstance();
	private final ResourceBank resourceBank = ResourceBank.getInstance();

	private void playTradeCard(final Player player, final int price) {

		// TODO: Check if player has a market
		if (1 != 2) {
			// TODO: Let the player choose the resource used to pay
			player.updateResource(ResourceCubeType.GOLD, -price);
		}

		// TODO: Check if player owns a "Great Temple"

		final int[] result = mainFrame.openTradeDialog(
				resourceBank.getResourceCounter(), player.getResourceCounter());
		player.updateResources(result);
		mainFrame.updatePlayerResources(player.getResourceCounter());
		resourceBank.updateResources(result);
	}

	private void playRecruitCard(final Player player, final int qty) {
		final ArrayList<BattleCard> availableUnits = new ArrayList<BattleCard>();

		switch (player.getBoard().getType()) {
		case NORSE:
			break;
		case EGYPTIAN:
			final int[] cost = new int[] { 0, 2, 0, 4 };
			availableUnits.addAll(Arrays.asList(new BattleCard("Anubite",
					"N/A", 6, cost),
					new BattleCard("Anubite 2", "N/A", 6, cost),
					new BattleCard("Anubite 3", "N/A", 6, cost),
					new BattleCard("Anubite 4", "N/A", 6, cost)));
			break;
		case GREEK:
			break;
		}

		final ArrayList<String> selectedUnits = mainFrame.openRecruitDialog(
				player.getResourceCounter(), qty, availableUnits);

		// TODO: add the units to the player board

	}

	public void play(final Player player, final Card card) {
		switch (card.getType()) {
		case TRADE:
			playTradeCard(player, card.getCost());
			break;
		case RECRUIT:
			playRecruitCard(player, card.getNum());
			break;
		default:
			break;
		}
	}

	public static CardController getInstance() {
		return mInstance;
	}

}
