/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Apr 10, 2015
 */
package controllers;

import java.util.ArrayList;

import models.BattleCard;
import models.Card;
import models.Player;
import models.ResourceBank;
import models.Unit;
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
		player.decrementResources(result);
		resourceBank.decrementResources(result);
	}

	private void playRecruitCard(final Player player, final int qty) {
		final ArrayList<BattleCard> availableUnits = new ArrayList<BattleCard>();

		// Select the units that are from the player's culture
		// TODO: Check the age (?)
		for (final BattleCard card : resourceBank.getBattleCardsDeck()) {
			if (card.getUnit().getType().getCulture() == player.getBoard()
					.getType()) {
				availableUnits.add(card);
			}
		}
		System.out.println(player.getResourceCounter()[0]);
		// Pops up the Dialog and get selected units
		final ArrayList<Unit> selectedUnits = mainFrame.openRecruitDialog(
				player.getResourceCounter(), qty, availableUnits);

		if (selectedUnits != null) {
			player.addUnits(selectedUnits);
			// TODO: implement "add" method this one delete all units
			mainFrame.updatePlayerArmy(selectedUnits);
		}

	}

	public void play(final Player player, final Card card) {
		switch (card.getType()) {
		case ATTACK:
			break;
		case TRADE:
			playTradeCard(player, card.getCost());
			break;
		case RECRUIT:
			playRecruitCard(player, card.getNum());
			break;
		default:
			break;
		}
		mainFrame.updatePlayerResources(player.getResourceCounter());
	}

	public static CardController getInstance() {
		return mInstance;
	}

}
