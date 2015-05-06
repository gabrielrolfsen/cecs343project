/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Apr 10, 2015
 */
package controllers;

import java.util.ArrayList;
import java.util.Random;

import models.BattleCard;
import models.BuildingTile;
import models.Card;
import models.Player;
import models.ResourcesBank;
import models.Unit;
import utils.Constants;
import utils.Types.BuildingTileType;
import utils.Types.CardType;
import utils.Types.CultureType;
import utils.Types.ResourceCubeType;
import utils.Types.UnitType;
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
	 * Execute Trade Card Routine.
	 * 
	 * @param price
	 * @param i
	 */
	private void playTradeCard(final int price, final int i, final CardType type, final boolean allowGodPower) {

		// If player has a market, he doesn't have to pay resources
		if (!mPlayers[i].hasMarket() && (type == CardType.TRADE || type == CardType.UNKNOWN)) {
			if (i == 0) {
				mainFrame.showPaymentDialog(resourceBank.getResourceCounter(),
						mPlayers[i].getResourceCounter(), price);
				// Update the Player's board with the new resource counter
				mainFrame.updatePlayerResources(mPlayers[i].getResourceCounter());
			} else {
				// Choose the first available resource as payment
				for (int ii = 0; ii < 4; ii++) {
					if (mPlayers[i].getResourceCounter()[ii] > 1) {
						mPlayers[i].decrementResource(ResourceCubeType.getType(ii), 2);
						break;
					}
				}
			}
		}
		if (i == Constants.HUMAN_PLAYER) {
			boolean allowVictoryCubes = false;

			// If player has greatTemple and has at least 8 favor cubes, let him
			// trade them for victory cubes
			if (mPlayers[i].hasGreatTemple() && mPlayers[i].getResourceCounter()[0] > 7) {
				allowVictoryCubes = true;
			}

			// Open the trade dialog
			final int[] result = mainFrame.openTradeDialog(resourceBank.getResourceCounter(),
					mPlayers[i].getResourceCounter(), allowVictoryCubes, type);
			// Update Resources for the player
			mPlayers[i].decrementResources(result);
			// Update Resources for the bank
			resourceBank.replenishResources(result);
		} else {
			final Random r = new Random();
			// Takes a Random Number to use as Resource to Trade
			final int resNum = r.nextInt(4);
			final int qty = mPlayers[i].getResourceCounter()[resNum];
			mPlayers[i].decrementResource(ResourceCubeType.getType(resNum), qty);
			resourceBank.replenishResource(ResourceCubeType.getType(resNum), qty);
		}
	}

	/**
	 * Execute Explore Card Routine.
	 * 
	 * @param qty
	 * @param i
	 */
	private void playExploreCard(final int qty, final int i, final CardType type, final boolean allowGodPower) {
		final TerrainPickerController terrainController = TerrainPickerController.getInstance();
		terrainController.exploreCardRoutine(mPlayers, i, type);
	}

	/**
	 * Execute Recruit Card Routine.
	 * 
	 * @param qty
	 * @param i
	 */
	private void playRecruitCard(final int qty, final int i, final CardType type, final boolean allowGodPower) {
		final ArrayList<BattleCard> availableUnits = new ArrayList<BattleCard>();

		// Select the units that are from the player's culture
		// TODO: Check the age (?)
		for (final BattleCard card : resourceBank.getBattleCardsDeck()) {

			if (card.getUnit().getType().getCulture() == mPlayers[i].getBoard().getType()) {
				availableUnits.add(card);
			}
		}
		final ArrayList<Unit> selectedUnits;

		if (i == Constants.HUMAN_PLAYER) {
			// Pops up the Dialog and get selected units
			selectedUnits = mainFrame
					.openRecruitDialog(mPlayers[i].getResourceCounter(), qty, availableUnits);
		} else {
			selectedUnits = new ArrayList<Unit>();

			for (final BattleCard b : availableUnits) {
				for (int ii = 0; ii < 4; ii++) {
					// If player cannot afford the unit, go to the other one
					if (b.getCost()[ii] > mPlayers[i].getResourceCounter()[ii]) {
						break;
					}
				}
				// Otherwise, add to player's selected Units
				System.out.println("** Player " + i + " recruited Unit: " + b.getName());
				selectedUnits.add(b.getUnit());
				// Decrement Player Resources
				mPlayers[i].decrementResources(b.getCost());
				// If player has selected the maximum qty, break the loop
				if (selectedUnits.size() == qty) {
					break;
				}
			}
		}

		// If player hasn't given up playing the card
		if (selectedUnits != null) {
			// If the card has a special power and it is activated
			if (allowGodPower == true) {
				if (type == CardType.BAST) {
					switch (mPlayers[i].getAge()) {
					case CLASSICAL:
						selectedUnits.add(new Unit(UnitType.PRIEST));
						break;
					case HEROIC:
						selectedUnits.add(new Unit(UnitType.PHARAOH));
						break;
					case MYTHIC:
						selectedUnits.add(new Unit(UnitType.SON_OF_OSIRIS));
						break;
					default:
						break;
					}
				} else if (type == CardType.APOLLO) {
					selectedUnits.add(new Unit(UnitType.TOXOTES));
					selectedUnits.add(new Unit(UnitType.TOXOTES));
				} else if (type == CardType.HEL) {
					final ArrayList<Unit> mortalNorseUnits = UnitType.getUnits(CultureType.NORSE,
							UnitType.MORTAL);
					// Add the two chosen units to player's board
					selectedUnits.addAll(mainFrame.showNorseRecruitSpecialDialog(mortalNorseUnits));
				}
			}

			mPlayers[i].addUnits(selectedUnits);

			// Update the board if it's the human player playing
			if (i == 0) {
				mainFrame.updatePlayerArmy(selectedUnits);
			}

		}

	}

	/**
	 * Execute Build Card Routine.
	 * 
	 * @param i
	 * @param type
	 */
	private void playBuildCard(final int i, final CardType type, final boolean allowGodPower) {
		final BuildController c = new BuildController();
		c.play(mPlayers[i]);

		// If card is the Greek Power, adds a House to player's board.
		if (type == CardType.HERA && allowGodPower == true) {
			final boolean result = mPlayers[i].getBoard().addBuildingTile(
					new BuildingTile(BuildingTileType.HOUSE));
			System.out.println("DEBUG>> House added to board: " + result);
		}

		// MainFrameView.getInstance().setDisplayedBoard(mPlayers[i]);
	}

	/**
	 * Execute Gather Card Routine.
	 * 
	 * @param price
	 * @param i
	 * @param type
	 */
	private void playGatherCard(final int i, final CardType type, final boolean allowGodPower) {
		final GatherControl c = new GatherControl(resourceBank);
		c.play(mPlayers, type, allowGodPower);

		// If player payed for the god power
		if (allowGodPower) {
			if (type == CardType.FREYJA) {
				// Norse God Power increments 5 gold when the card is played
				mPlayers[i].incrementResource(ResourceCubeType.GOLD, 5);
			} else if (type == CardType.POSEIDON) {
				// Greek God Power increments 5 food when the card is played
				mPlayers[i].incrementResource(ResourceCubeType.FOOD, 5);
			}
		}
	}

	/**
	 * Execute NextAge Card Routine.
	 * 
	 * @param price
	 * @param i
	 * @param type
	 */
	private void playNextAgeCard(final int i, final CardType type, final boolean allowGodPower) {
		final NextAgeController c = new NextAgeController(resourceBank);
		c.play(mPlayers[i], type);
	}

	/**
	 * Method that handles both Random Action Cards and Permanent Action Cards.
	 * Random Action Cards differ form Permanent Action Cards on CardType, so
	 * each method handles each CardType differently.
	 * 
	 * @param card
	 *            The card to be played.
	 * @param playNum
	 *            Number of the player that played the card.
	 */
	public void play(final Card card, final int playNum) {
		boolean allowGodPower = false;

		// If the card has a godPower, ask the player if he wants to use it
		if (card.getGodName().length() > 1 && playNum == Constants.HUMAN_PLAYER) {
			// Card.getCost return the cost in favor cubes
			if (mPlayers[playNum].getResourceCounter()[ResourceCubeType.FAVOR.getValue()] >= card.getCost()) {
				// If player has enough resources asks if he wants to use the
				// power
				allowGodPower = mainFrame.showPaymentGodPowerDialog(card.getGodName(), card.getCost(),
						mPlayers[playNum].getResourceCounter());
				mainFrame.updatePlayerResources(mPlayers[0].getResourceCounter());
			} else {
				// Otherwise, show a message showing he doesn't have enough
				// resources
				mainFrame.showNotEnoughResourcesDialog();
			}
		} else if (mPlayers[playNum].getResourceCounter()[ResourceCubeType.FAVOR.getValue()] >= card
				.getCost()) {
			// Subtract the card cost from player's resource
			mPlayers[playNum].decrementResource(ResourceCubeType.FAVOR, card.getCost());
			allowGodPower = true;
		}

		switch (card.getType()) {
		case ATTACK:
			break;
		case NJORD:
		case HERA:
		case NENPHTHYS:
		case BUILD:
			playBuildCard(playNum, card.getType(), allowGodPower);
			break;
		case BALDR:
		case ARTHEMIS:
		case PTAH:
		case EXPLORE:
			playExploreCard(card.getNum(), playNum, card.getType(), allowGodPower);
			break;
		case FREYJA:
		case POSEIDON:
		case RA:
		case GATHER:
			playGatherCard(playNum, card.getType(), allowGodPower);
			break;
		case ODIN:
		case HEPHAESTUS:
		case HATHOR:
		case NEXTAGE:
			playNextAgeCard(playNum, card.getType(), allowGodPower);
			break;
		case HEL:
		case APOLLO:
		case BAST:
		case RECRUIT:
			playRecruitCard(card.getNum(), playNum, card.getType(), allowGodPower);
			break;
		case FORSETI:
		case HERMES:
		case UNKNOWN:
		case TRADE:
			playTradeCard(card.getNum(), playNum, card.getType(), allowGodPower);
		default:
			break;
		}
		// Update Human Player Resources
		mainFrame.updatePlayerResources(mPlayers[0].getResourceCounter());
	}

	/**
	 * Points the variable to players that are playing. Probably can be
	 * refactored.
	 * 
	 * @param players
	 */
	public void setPlayers(final Player[] players) {
		this.mPlayers = players;
	}

	public synchronized static CardController getInstance() {
		return mInstance;
	}

}
