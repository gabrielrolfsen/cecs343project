/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Apr 11, 2015
 */
package views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import models.BattleCard;
import models.Unit;
import utils.EnabledJComboBoxRenderer;

/**
 * @author grolfsen
 *
 */
public class RecruitDialog extends JDialog implements ListSelectionListener {

	ArrayList<BattleCard> availableUnits = new ArrayList<BattleCard>();
	private int unitsSelected = 0;
	private final int maxUnitsSelection;
	int[] mPlayerResources;

	private final DefaultListModel listModel;
	private final JComboBox unitsAvailable;
	private final JList list;
	private final JButton removeButton = new JButton("Remove");
	private final JButton confirmButton = new JButton("Recruit Units");
	private final JButton cancelButton = new JButton("Cancel");
	private final JButton addButton = new JButton("Add");

	final DefaultListSelectionModel comboModel = new DefaultListSelectionModel();

	public RecruitDialog(final JFrame parentFrame, final int[] playerResources, final int qty,
			final ArrayList<BattleCard> availableUnits) {
		super(parentFrame, "Add up to " + qty + " unit" + (qty > 1 ? "s" : "") + " to recruit.", true);
		this.availableUnits = availableUnits;
		this.mPlayerResources = playerResources;
		this.maxUnitsSelection = qty;

		setResizable(false);

		listModel = new DefaultListModel();
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.addListSelectionListener(this);
		list.setVisibleRowCount(5);
		final JScrollPane listScrollPane = new JScrollPane(list);

		addButton.setActionCommand("Add");
		addButton.addActionListener(new AddListener());
		addButton.setEnabled(true);

		final String[] unitNameList = new String[availableUnits.size()];
		for (int i = 0; i < availableUnits.size(); i++) {
			unitNameList[i] = availableUnits.get(i).getName();
		}

		unitsAvailable = new JComboBox(unitNameList);

		comboModel.setSelectionInterval(0, unitNameList.length);
		unitsAvailable.setRenderer(new EnabledJComboBoxRenderer(comboModel));

		final ControlBtnsListener ctrlBtnListener = new ControlBtnsListener();
		confirmButton.addActionListener(ctrlBtnListener);
		cancelButton.addActionListener(ctrlBtnListener);

		removeButton.addActionListener(new RemoveListener());

		final JPanel buttonPane = new JPanel();

		final JPanel controlButtonsPane = new JPanel();
		controlButtonsPane.setLayout(new GridLayout(1, 4));
		controlButtonsPane.add(confirmButton);
		controlButtonsPane.add(cancelButton);

		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		removeButton.setEnabled(false);
		buttonPane.add(removeButton);
		buttonPane.add(Box.createHorizontalStrut(5));
		buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
		buttonPane.add(Box.createHorizontalStrut(5));
		buttonPane.add(unitsAvailable);
		buttonPane.add(addButton);

		buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		add(listScrollPane, BorderLayout.PAGE_START);
		add(buttonPane, BorderLayout.CENTER);
		add(controlButtonsPane, BorderLayout.PAGE_END);
		pack();
		setLocationRelativeTo(parentFrame);

	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<Unit> getSelectedUnits() {
		final ArrayList<Unit> selectedUnits = new ArrayList<Unit>();
		for (int i = 0; i < listModel.getSize(); i++) {
			final String unitName = listModel.getElementAt(i).toString();
			selectedUnits.add(getUnit(unitName));
		}

		return selectedUnits;
	}

	@Override
	public void valueChanged(final ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {

			if (list.getSelectedIndex() == -1) {
				// No selection, disable fire button.
				removeButton.setEnabled(false);

			} else {
				// Selection, enable the fire button.
				removeButton.setEnabled(true);
			}
		}
	}

	private class ControlBtnsListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			// If it is the cancel button, clear the list first
			if (((JButton) e.getSource()).getText().equals("Cancel")) {
				listModel.clear();
			}
			dispose();
		}
	}

	private class RemoveListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			// This method can be called only if
			// there's a valid selection
			// so go ahead and remove whatever's selected.
			int index = list.getSelectedIndex();

			// Get Unit Cost
			final int[] unitCost = getUnitCost(list.getSelectedValue().toString());

			// Increase Player Resources
			for (int i = 0; i < 4; i++) {
				mPlayerResources[i] += unitCost[i];
			}

			listModel.remove(index);

			// If it was on maximum selection, enable the add button again
			if (unitsSelected == maxUnitsSelection) {
				addButton.setEnabled(true);
			}

			// Decrease qty of Units Selected
			unitsSelected--;

			final int size = listModel.getSize();

			if (size == 0) { // Nobody's left, disable firing.
				removeButton.setEnabled(false);

			} else { // Select an index.
				if (index == listModel.getSize()) {
					// removed item in last position
					index--;
				}

				list.setSelectedIndex(index);
				list.ensureIndexIsVisible(index);
			}
		}
	}

	private class AddListener implements ActionListener {

		// Required by ActionListener.
		@Override
		public void actionPerformed(final ActionEvent e) {
			final String unitName = unitsAvailable.getSelectedItem().toString();

			// Verify if player can afford the Unit
			final int[] unitCost = getUnitCost(unitName);
			for (int i = 0; i < 4; i++) {
				System.out
				.println("Unit Cost: " + unitCost[i] + " - Player Resource: " + mPlayerResources[i]);
				if (unitCost[i] > mPlayerResources[i]) {

					// Player cannot afford it
					return;
				}
			}
			// Decrease Player Resources
			for (int i = 0; i < 4; i++) {
				mPlayerResources[i] -= unitCost[i];
			}
			// Increase Units Selected counter
			unitsSelected++;

			// If reached maximum, disable add button
			if (unitsSelected == maxUnitsSelection) {
				addButton.setEnabled(false);
			}

			int index = list.getSelectedIndex(); // get selected index
			if (index == -1) { // no selection, so insert at beginning
				index = 0;
			} else { // add after the selected item
				index++;
			}

			// Add element to the list
			listModel.addElement(unitsAvailable.getSelectedItem().toString());

			// Select the new item and make it visible.
			list.setSelectedIndex(index);
			list.ensureIndexIsVisible(index);
		}
	}

	private int[] getUnitCost(final String s) {
		int[] cost = null;
		for (final BattleCard card : availableUnits) {
			if (card.getName().equals(s)) {
				cost = card.getCost();
				break;
			}
		}
		return cost;
	}

	private Unit getUnit(final String s) {
		Unit unit = null;
		for (final BattleCard card : availableUnits) {
			if (card.getName().equals(s)) {
				unit = card.getUnit();
				break;
			}
		}
		return unit;
	}

}
