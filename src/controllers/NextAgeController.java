package controllers;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import models.Player;
import models.ResourcesBank;
import utils.Types.CardType;

public class NextAgeController {
	private final int CLASSICAL_COST = 4;
	private final int HEROIC_COST = 5;
	private final int MYTHIC_COST = 6;

	private final ResourcesBank bank;

	public NextAgeController(final ResourcesBank newBank) {
		bank = newBank;
	}

	public void play(final Player curPlayer, final CardType type) {
		final int[] payment = new int[5];
		int[] playerResources;
		int temp;

		// Determine payment
		switch (curPlayer.getAge()) {
		case ARCHAIC:
			temp = CLASSICAL_COST;
			break;
		case CLASSICAL:
			temp = HEROIC_COST;
			break;
		case HEROIC:
			temp = MYTHIC_COST;
			break;
		default:
			temp = 0;
		}

		int i;
		for (i = 0; i < 4; i++) {
			payment[i] = temp;
		}
		payment[4] = 0; // Victory points

		try {

			// Check for sufficient resources
			playerResources = curPlayer.getResourceCounter();
			for (i = 0; i < 4; i++) {
				if (playerResources[i] < payment[i]) {
					throw new IllegalStateException();
				}
			}

			// Remove corresponding resource cubes from player
			curPlayer.decrementResources(payment);

			// Transfer resource cube payment to bank
			bank.replenishResources(payment);

			// Advance Player's Age
			curPlayer.advanceAge();

			// Display achievement dialog
			final JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "You have advanced to the " + curPlayer.getAge().getName()
					+ " age.");

		} catch (final IllegalStateException ex) {
			final JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "You do not have enough resources to advance to next age.");
		}
	}

}
