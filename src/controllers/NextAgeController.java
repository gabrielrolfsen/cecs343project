package controllers;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import utils.Types.AgeType;
import models.Player;
import models.ResourcesBank;

public class NextAgeController {
	private final int CLASSICAL_COST = 4;
	private final int HEROIC_COST = 5;
	private final int MYTHIC_COST = 6;
	
	private ResourcesBank bank;
	
	public NextAgeController(ResourcesBank newBank) {
		bank = newBank;
	}
	
	public void play(Player curPlayer) {
		int[] payment = new int[5];
		int[] playerResources;
		int temp;
		
		// Determine payment
		switch (curPlayer.currentAge) {
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
		payment[4] = 0;  // Victory points
		
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
			bank.incrementResources(payment);
		
			// Change Player's Age
			if (curPlayer.currentAge == AgeType.ARCHAIC) {
			
				curPlayer.currentAge = AgeType.CLASSICAL;
			
			} else if (curPlayer.currentAge == AgeType.CLASSICAL) {
			
				curPlayer.currentAge = AgeType.HEROIC;
			
			} else if (curPlayer.currentAge == AgeType.HEROIC) {
			
				curPlayer.currentAge = AgeType.MYTHIC;
			
			}
		
			// Display achievement dialog
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "You have advanced to the " + curPlayer.currentAge.getString() + " age.");
		
		}
		catch (IllegalStateException ex) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "You do not have enough resources to advance to next age.");
		}
	}
	
}
