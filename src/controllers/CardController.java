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

	private Player[] mPlayers;

	/**
	 * 
	 * @param price
	 * @param i
	 */
	private void playTradeCard(final int price, final int i) {

		// If player has a market, he doesn't have to pay resources
		if (!mPlayers[i].hasMarket()) {
			mainFrame.showPaymentDialog(resourceBank.getResourceCounter(),
					mPlayers[i].getResourceCounter(), price);
			mainFrame.updatePlayerResources(mPlayers[i].getResourceCounter());
		}

		boolean allowVictoryCubes = false;

		// If player has greatTemple and has at least 8 favor cubes, let him
		// trade them for victory cubes
		if (mPlayers[i].hasGreatTemple()
				&& mPlayers[i].getResourceCounter()[0] > 7) {
			allowVictoryCubes = true;
		}

		final int[] result = mainFrame.openTradeDialog(
				resourceBank.getResourceCounter(),
				mPlayers[i].getResourceCounter(), allowVictoryCubes);
		mPlayers[i].decrementResources(result);
		resourceBank.decrementResources(result);
	}

	/**
	 * 
	 * @param qty
	 * @param i
	 */
	private void playExploreCard(final int qty, final int i) {
		final TerrainPickerController terrainController = TerrainPickerController
				.getInstance();
		terrainController.exploreCardRoutine(mPlayers, i);
	}

	/**
	 * 
	 * @param qty
	 * @param i
	 */
	private void playRecruitCard(final int qty, final int i) {
		final ArrayList<BattleCard> availableUnits = new ArrayList<BattleCard>();

		// Select the units that are from the player's culture
		// TODO: Check the age (?)
		for (final BattleCard card : resourceBank.getBattleCardsDeck()) {

			if (card.getUnit().getType().getCulture() == mPlayers[i].getBoard()
					.getType()) {
				availableUnits.add(card);
			}
		}
		System.out.println(mPlayers[i].getResourceCounter()[0]);
		// Pops up the Dialog and get selected units
		final ArrayList<Unit> selectedUnits = mainFrame.openRecruitDialog(
				mPlayers[i].getResourceCounter(), qty, availableUnits);

		if (selectedUnits != null) {
			mPlayers[i].addUnits(selectedUnits);
			// TODO: implement "add" method this one delete all units
			mainFrame.updatePlayerArmy(selectedUnits);
		}

	}

	private void playBuildCard(final int i) {
		final BuildController c = new BuildController();
		c.play(mPlayers[i]);
	}

	private void playGatherCard(final int price, final int i) {
		final GatherControl c = new GatherControl(resourceBank);
		c.play(mPlayers[0], mPlayers[1], mPlayers[2]);
	}

	private void playNextAgeCard(final int price, final int i) {
		final NextAgeController c = new NextAgeController(resourceBank);
		c.play(mPlayers[i]);
	}

	public void setPlayers(final Player[] players) {
		this.mPlayers = players;
	}

	public void play(final Card card, final int playNum) {

		switch (card.getType()) {
		case ATTACK:
			break;
		case BUILD:
			playBuildCard(playNum);
			break;
		case EXPLORE:
			playExploreCard(card.getNum(), playNum);
			break;
		case GATHER:
			playGatherCard(card.getNum(), playNum);
			break;
		case NEXTAGE:
			playNextAgeCard(card.getNum(), playNum);
			break;
		case RECRUIT:
			playRecruitCard(card.getNum(), playNum);
			break;
		case TRADE:
			playTradeCard(card.getCost(), playNum);
			break;
		default:
			break;
		}
		// Update Player Resources
		// TODO: Don't need to do with AI
		mainFrame.updatePlayerResources(mPlayers[playNum].getResourceCounter());
	}

	public synchronized static CardController getInstance() {
		return mInstance;
	}

}
