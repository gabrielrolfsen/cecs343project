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
import models.BuildingTile;
import models.Card;
import models.Player;
import models.ResourcesBank;
import models.Unit;
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
		if (!mPlayers[i].hasMarket() || (type == CardType.FORSETI && allowGodPower == true)) {
			mainFrame.showPaymentDialog(resourceBank.getResourceCounter(), mPlayers[i].getResourceCounter(),
					price);
			mainFrame.updatePlayerResources(mPlayers[i].getResourceCounter());
		}

		boolean allowVictoryCubes = false;

		// If player has greatTemple and has at least 8 favor cubes, let him
		// trade them for victory cubes
		if (mPlayers[i].hasGreatTemple() && mPlayers[i].getResourceCounter()[0] > 7) {
			allowVictoryCubes = true;
		}

		final int[] result = mainFrame.openTradeDialog(resourceBank.getResourceCounter(),
				mPlayers[i].getResourceCounter(), allowVictoryCubes, type);
		mPlayers[i].decrementResources(result);
		resourceBank.decrementResources(result);
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
		System.out.println("# of units to recruit: " + qty);
		// Pops up the Dialog and get selected units
		final ArrayList<Unit> selectedUnits = mainFrame.openRecruitDialog(mPlayers[i].getResourceCounter(),
				qty, availableUnits);

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
			// TODO: implement "add" method this one delete all units
			mainFrame.updatePlayerArmy(selectedUnits);
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
		
		MainFrameView.getInstance().setDisplayedBoard(mPlayers[i]);
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
		c.play(mPlayers[0], mPlayers[1], mPlayers[2], type);
		// Norse God Power increments 5 gold when the card is played
		if (type == CardType.FREYJA) {
			mPlayers[i].incrementResource(ResourceCubeType.GOLD, 5);
		} else if (type == CardType.POSEIDON) {
			mPlayers[i].incrementResource(ResourceCubeType.FOOD, 5);
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
	 * Points the variable to players that are playing. Probably can be
	 * refactored.
	 * 
	 * @param players
	 */
	public void setPlayers(final Player[] players) {
		this.mPlayers = players;
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
		if (card.getGodName().length() > 1) {
			allowGodPower = mainFrame.showPaymentGodPowerDialog(card.getGodName(), card.getCost(),
					mPlayers[playNum].getResourceCounter());
			mainFrame.updatePlayerResources(mPlayers[0].getResourceCounter());
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

	public synchronized static CardController getInstance() {
		return mInstance;
	}

}
