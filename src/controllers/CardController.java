/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Apr 10, 2015
 */
package controllers;

import models.Player;
import models.ResourceBank;
import utils.Types.ResourceCubeType;
import views.MainFrameView;

/**
 * @author grolfsen
 *
 */
public class CardController {

	private final MainFrameView mainFrame = MainFrameView.getInstance();
	private final ResourceBank resourceBank = ResourceBank.getInstance();

	public void playTradeCard(final Player player, final int price) {

		// TODO: Check if player has a market
		if (1 != 2) {
			// TODO: Let the player choose the resource used to pay
			player.updateResource(ResourceCubeType.GOLD, -price);
		}

		// TODO: Check if player owns a "Great Temple"

		final int[] result = mainFrame.openTradeDialog(
				resourceBank.getResourceCounter(), player.getResourceCounter());
		player.updateResources(result);
		resourceBank.updateResources(result);
	}

}
