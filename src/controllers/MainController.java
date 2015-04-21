/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Feb 23, 2015
 */
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import models.Player;
import models.ResourcesBank;
import utils.Constants;
import utils.Types.BoardType;
import views.MainFrameView;
import views.VictoryCardsDialog;

/**
 * Main Controller class, that control all the game flow
 * 
 * @author grolfsen
 *
 */
public class MainController {
	private static MainController mInstance = new MainController();
	private final MainFrameView mainFrame = MainFrameView.getInstance();
	private final CardController cardController = CardController.getInstance();

	/*
	 * Counter for each type of terrain inside the randomTiles array, each index
	 * represent a terrainType and contains the number of tiles of this type in
	 * the random array
	 */
	int[] terrainTypeCounter = new int[6];

	// Array containing current players 0 is always the user
	public Player[] players = new Player[Constants.MAX_PLAYERS];

	private MainController() {

		// Initialize Players
		for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
			players[i] = new Player();
		}
		players[0].human = true;
		
		// Pops a Dialog so user can pick a culture
		userCulturePick();

		// Show the player board in the main frame
		mainFrame.setDisplayedBoard(players[0]);

		final TerrainPickerController terrainController = TerrainPickerController
				.getInstance();

		// Do the terrain picking routine
		terrainController.terrainPickRoutine(players);

		// TODO: Improve this dialog
		mainFrame.showGameReadyDialog();

		mainFrame.setVisible(true);

		final TurnController turnController = new TurnController();

		turnController.playTurn(players);

		// Add "Place Victory Cubes" Button functionality
		mainFrame.addVictoryCardButtonActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {

				final int victoryCubesOnCards[] = ResourcesBank.getInstance()
						.getVictoryCubesOnCards();

				final VictoryCardsDialog b = new VictoryCardsDialog(mainFrame,
						players[0].getResourceCounter(), victoryCubesOnCards);
				b.setVisible(true);
			}
		});

		// Add "Switch Board" Button functionality
		mainFrame.addSwitchBoardsButtonActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				Player playerToDisplay = null;

				final String[] boardNames = new String[] {
						players[0].getBoard().getTypeName(),
						players[1].getBoard().getTypeName(),
						players[2].getBoard().getTypeName() };

				final JFrame frame = new JFrame("Board Selection");
				final String boardType = (String) JOptionPane.showInputDialog(
						frame, "Please choose a board:", "Board Selection",
						JOptionPane.QUESTION_MESSAGE, null, boardNames,
						boardNames[0]);

				if (boardType != null) {
					if (boardType.equalsIgnoreCase(players[0].getBoard()
							.getTypeName())) {
						playerToDisplay = players[0];
					} else if (boardType.equalsIgnoreCase(players[1].getBoard()
							.getTypeName())) {
						playerToDisplay = players[1];
					} else if (boardType.equalsIgnoreCase(players[2].getBoard()
							.getTypeName())) {
						playerToDisplay = players[2];
					}

					// If the option was one different from the already
					// displayed, change it
					if (playerToDisplay.getBoard().getType() != mainFrame
							.getDisplayedBoardType()) {
						mainFrame.setDisplayedBoard(playerToDisplay);
					}
				}
			}
		});
		
		// Test Gather
		//GatherControl gc = new GatherControl(ResourcesBank.getInstance());
		//gc.gather(players[0]);
		//mainFrame.setDisplayedBoard(players[0]);
		
	}

	/**
	 * Method that calls the mainFrame to pops the Dialog for culture pick, then
	 * initialize the boards.
	 */
	private void userCulturePick() {
		// Pops the DialogView on the mainFrame so user can pick a culture
		final String input = mainFrame.culturePickDialog();
		System.out.println(input);
		// Gets the input and initialize the specific board for user + AI's
		if (input.equalsIgnoreCase("Greek")) {
			initBoard(BoardType.GREEK);
		} else if (input.equalsIgnoreCase("Norse")) {
			initBoard(BoardType.NORSE);
		} else if (input.equalsIgnoreCase("Egyptian")) {
			initBoard(BoardType.EGYPTIAN);
		}

	}

	/**
	 * Initialize all the Boards depending on what culture the user has chosen.
	 * Then call the mainFrame to display the userBoard first.
	 * 
	 * @param type
	 *            BoardType, the BoardType that the user has chosen.
	 * @param title
	 *            String, the title to be used in the JFrame
	 */
	private void initBoard(final BoardType type) {
		/*
		 * ------------- If Player = Greek, Ai0 = Egyptian, Ai1 = Norse.
		 * ------------- If Player = Egyptian, Ai0 = Norse, Ai1 = Greek.
		 * ------------- If Player = Norse, Ai0 = Greek, Ai1 = Egyptian.
		 */
		players[0].setBoard(type);
		players[1].setBoard(type == BoardType.GREEK ? BoardType.EGYPTIAN
				: (type == BoardType.EGYPTIAN ? BoardType.NORSE
						: BoardType.GREEK));
		players[2].setBoard(type == BoardType.GREEK ? BoardType.NORSE
				: (type == BoardType.EGYPTIAN ? BoardType.GREEK
						: BoardType.EGYPTIAN));
	}

	public Player[] getPlayers() {
		return this.players;
	}

	public static MainController getInstance() {
		return mInstance;
	}

}
