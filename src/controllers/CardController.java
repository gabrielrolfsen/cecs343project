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
import models.ResourcesBank;
import models.Unit;
import views.MainFrameView;

/**
 * @author grolfsen
 *
 */
public class CardController {

	private final static CardController mInstance = new CardController();
	private final MainFrameView mainFrame = MainFrameView.getInstance();
	private final ResourcesBank resourceBank = ResourcesBank.getInstance();

	private void playTradeCard(final Player player, final int price) {

		// If player has a market, he doesn't have to pay resources
		if (!player.hasMarket()) {
			mainFrame.showPaymentDialog(resourceBank.getResourceCounter(),
					player.getResourceCounter(), price);
			mainFrame.updatePlayerResources(player.getResourceCounter());
		}

		boolean allowVictoryCubes = false;

		// If player has greatTemple and has at least 8 favor cubes, let him
		// trade them for victory cubes
		if (player.hasGreatTemple() && player.getResourceCounter()[0] > 7) {
			allowVictoryCubes = true;
		}

		final int[] result = mainFrame.openTradeDialog(
				resourceBank.getResourceCounter(), player.getResourceCounter(),
				allowVictoryCubes);
		player.decrementResources(result);
		resourceBank.decrementResources(result);
	}

	private void playExploreCard(final Player[] players, final int qty) {
		final TerrainPickerController terrainController = TerrainPickerController
				.getInstance();
		terrainController.exploreCardRoutine(players);

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

	private void playBuildCard(final Player player) {
		final BuildController c = new BuildController();
		c.play(player);
	}

	private void playGatherCard(final Player player, final int price) {

	}

	private void playNextAgeCard(final Player player, final int price) {

	}

	public void play(final Player[] players, final Card card) {
		switch (card.getType()) {
		case ATTACK:
			break;
		case BUILD:
			playBuildCard(players[0]);
			break;
		case EXPLORE:
			playExploreCard(players, card.getNum());
			break;
		case GATHER:
			break;
		case NEXTAGE:
			break;
		case RECRUIT:
			playRecruitCard(players[0], card.getNum());
			break;
		case TRADE:
			playTradeCard(players[0], card.getCost());
			break;
		default:
			break;
		}
		mainFrame.updatePlayerResources(players[0].getResourceCounter());
	}

	public static CardController getInstance() {
		return mInstance;
	}

}
