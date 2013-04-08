/**
 * @author Matthew Hausman 2012
 * 
 *         This class is the GUI for the damage table look-up function of
 *         Referee. The GUI uses tabs to distinguish between damage types, combo
 *         boxes for attack zone selection and usage of the Minor and Major
 *         Accuracy perks. It also uses combo boxes for the gender of the
 *         opponent and the damage level of the wound. On the click of a button
 *         it selects and displays the appropriate wound.
 * 
 *         Multiple possible wounds may be displayed with Minor or Major
 *         Accuracy selected.
 * 
 *         Currently only works for blunt attacks to zones I-IX against male
 *         opponents. The other damage tables haven't been added yet.
 * 
 */

package referee;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;

class DamageFinder extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L; // parent appeasement
	// Options for the combo boxes
	private static final String[] BLUDGEONING_STRINGS = { "", "I", "II", "III",
	    "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", "XIV" };
	private static final String[] CUTTING_STRINGS = { "", "I", "II", "III", "IV",
	    "V", "VI", "VII" };
	private static final String[] PUNCTURING_STRINGS = { "", "VIII", "IX", "X",
	    "XI", "XII", "XIII", "XIV" };
	private static final String[] ACCURACY_STRINGS = { "", "Minor", "Major" };
	private static final String[] DAMAGE_STRINGS = { "", "1", "2", "3", "4", 
		"5" };
	private static final String[] OPPONENT_STRINGS = { "Male", "Female" };
	
	private static final int ACCURACY_NULL = 0;
	private static final int ACCURACY_MINOR = 1;
	private static final int ACCURACY_MAJOR = 2;
	private static final int MALE = 0;
	private static final int FEMALE = 1;
	private static final int BLUDGEONING_TAB = 0;
	private static final int CUTTING_TAB = 1;
	private static final int PUNCTURING_TAB = 2;
	
	private JTabbedPane targetTabs;
	private JComboBox damageLevelInput, bludgeoningZone, cuttingZone,
	    puncturingZone, bAccuracy, cAccuracy, pAccuracy, opponent;
	private JTextArea woundOutput;
	private JScrollPane woundPane;
	private Dice diceBag;
	private DamageTables damageModel;
	
	public DamageFinder(Dice d) {
		this.diceBag = d;
		this.damageModel = new DamageTables();
		this.setBorder(new TitledBorder("Wound Finder"));
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		// This tabbed pane is where the user selects damage type
		this.targetTabs = new JTabbedPane();
		
		// 1. Bludgeoning Damage
		// Create the objects
		JPanel bludgeoningInput = new JPanel();
		bludgeoningInput.setLayout(new BoxLayout(bludgeoningInput,
		    BoxLayout.PAGE_AXIS));
		JLabel bludgeonLabel = new JLabel("Bludgeoning");
		JLabel zoneLabelB = new JLabel("Zone:");
		bludgeoningZone = new JComboBox(BLUDGEONING_STRINGS);
		JLabel accuracyLabelB = new JLabel("Accuracy:");
		bAccuracy = new JComboBox(ACCURACY_STRINGS);
		
		// Arrange the objects
		bludgeonLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		zoneLabelB.setAlignmentX(Component.LEFT_ALIGNMENT);
		accuracyLabelB.setAlignmentX(Component.LEFT_ALIGNMENT);
		bludgeoningZone.setAlignmentX(Component.LEFT_ALIGNMENT);
		bAccuracy.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		// Place the objects
		bludgeoningInput.add(bludgeonLabel);
		bludgeoningInput.add(zoneLabelB);
		bludgeoningInput.add(bludgeoningZone);
		bludgeoningInput.add(accuracyLabelB);
		bludgeoningInput.add(bAccuracy);
		
		// 2. Cutting Damage
		// Create the objects
		JPanel cuttingInput = new JPanel();
		cuttingInput.setLayout(new BoxLayout(cuttingInput, BoxLayout.PAGE_AXIS));
		JLabel cuttingLabel = new JLabel("Cutting");
		JLabel zoneLabelC = new JLabel("Zone:");
		cuttingZone = new JComboBox(CUTTING_STRINGS);
		JLabel accuracyLabelC = new JLabel("Accuracy:");
		cAccuracy = new JComboBox(ACCURACY_STRINGS);
		
		// Arrange the objects
		cuttingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		zoneLabelC.setAlignmentX(Component.LEFT_ALIGNMENT);
		accuracyLabelC.setAlignmentX(Component.LEFT_ALIGNMENT);
		cuttingZone.setAlignmentX(Component.LEFT_ALIGNMENT);
		cAccuracy.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		// Place the objects
		cuttingInput.add(cuttingLabel);
		cuttingInput.add(zoneLabelC);
		cuttingInput.add(cuttingZone);
		cuttingInput.add(accuracyLabelC);
		cuttingInput.add(cAccuracy);
		
		// 3. Puncturing Damage
		// Create the objects
		JPanel puncturingInput = new JPanel();
		puncturingInput.setLayout(new BoxLayout(puncturingInput,
		    BoxLayout.PAGE_AXIS));
		JLabel punctureLabel = new JLabel("Puncturing");
		JLabel zoneLabelP = new JLabel("Zone:");
		puncturingZone = new JComboBox(PUNCTURING_STRINGS);
		JLabel accuracyLabelP = new JLabel("Accuracy:");
		pAccuracy = new JComboBox(ACCURACY_STRINGS);
		
		// Arrange the objects
		punctureLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		zoneLabelP.setAlignmentX(Component.LEFT_ALIGNMENT);
		accuracyLabelP.setAlignmentX(Component.LEFT_ALIGNMENT);
		puncturingZone.setAlignmentX(Component.LEFT_ALIGNMENT);
		pAccuracy.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		// Place the objects
		puncturingInput.add(punctureLabel);
		puncturingInput.add(zoneLabelP);
		puncturingInput.add(puncturingZone);
		puncturingInput.add(accuracyLabelP);
		puncturingInput.add(pAccuracy);
		
		// 4. Generic Damage
		// Create the objects
		JPanel genericInput = new JPanel();
		genericInput.setLayout(new BoxLayout(genericInput, BoxLayout.PAGE_AXIS));
		JLabel genericLabel = new JLabel("Generic");
		JLabel genericBox = new JLabel("Damage that doesn't fit");
		JLabel genericBox2 = new JLabel("elsewhere.");
		JComponent blankSpace = (JComponent) Box.createRigidArea(new Dimension(150,
		    50));
		
		// Arrange the objects
		genericLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		genericBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		genericBox2.setAlignmentX(Component.LEFT_ALIGNMENT);
		blankSpace.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		// Place the objects
		genericInput.add(genericLabel);
		genericInput.add(genericBox);
		genericInput.add(genericBox2);
		genericInput.add(blankSpace);
		
		// Assemble the tabs
		targetTabs.addTab("Bld", bludgeoningInput);
		targetTabs.addTab("Cut", cuttingInput);
		targetTabs.addTab("Pct", puncturingInput);
		targetTabs.addTab("Gnc", genericInput);
		
		// Input damage level
		JLabel damageLabel = new JLabel("Damage");
		damageLevelInput = new JComboBox(DAMAGE_STRINGS);
		
		// Button to return wound(s)
		JButton woundButton = new JButton("Hit");
		woundButton.addActionListener(this);
		
		woundOutput = new JTextArea(10, 30);
		woundOutput.setEditable(false);
		woundPane = new JScrollPane(woundOutput);
		woundPane.setMinimumSize(new Dimension(392, 182));
		
		JLabel opponentLabel = new JLabel("Opponent");
		opponent = new JComboBox(OPPONENT_STRINGS);
		
		/*
		 * Here we build the window. This gets weird. If I pick this up again to
		 * finish it, I might try it with separate constraints for each object.
		 * 
		 * As it is, I just redefine the constraints as necessary before using them
		 * to add to the JPanel.
		 */
		constraints.gridheight = 4;
		add(targetTabs, constraints);
		
		constraints.gridheight = 1;
		constraints.gridx = 1;
		add(opponentLabel, constraints);
		
		constraints.gridx = 2;
		add(opponent, constraints);
		
		constraints.gridy = 1;
		constraints.gridx = 1;
		add(damageLabel, constraints);
		
		constraints.gridx = 2;
		add(damageLevelInput, constraints);
		
		constraints.gridy = 2;
		add(woundButton, constraints);
		
		constraints.gridy = 0;
		constraints.gridx = 3;
		constraints.gridheight = 4;
		add(woundPane, constraints);
	}
	
	/*
	 * This method is called when the button is clicked.
	 * 
	 * First we check which tab is active. That gives the damage type. Then we
	 * check which accuracy option is selected. Minor and Major Accuracy have
	 * helper methods to display appropriate wounds. Next we get a deep copy of
	 * the wound entry in the appropriate damage table. Lastly, we display the
	 * wound with a border to distinguish it from earlier input.
	 * 
	 * Future implementation idea: Assign a number to each printed wound using an
	 * int to keep count.
	 */
	public void actionPerformed(ActionEvent arg0) {
		if (targetTabs.getSelectedIndex() == BLUDGEONING_TAB){
			if (bAccuracy.getSelectedIndex() == ACCURACY_NULL){
				Wound newWound = new Wound(damageModel.getBluntWound(
				    opponent.getSelectedIndex(), bludgeoningZone.getSelectedIndex(),
				    diceBag.rollD6(), damageLevelInput.getSelectedIndex()));
				woundOutput.append("=======================\n" + newWound);
			}else if (bAccuracy.getSelectedIndex() == ACCURACY_MINOR)
				minorAccuracyB(diceBag.rollD6());
			else
				majorAccuracyB();
			
		}else if (targetTabs.getSelectedIndex() == CUTTING_TAB){
			if (cAccuracy.getSelectedIndex() == ACCURACY_NULL){
				Wound newWound = new Wound(damageModel.getCutWound(
				    opponent.getSelectedIndex(), cuttingZone.getSelectedIndex(),
				    diceBag.rollD6(), damageLevelInput.getSelectedIndex()));
				woundOutput.append("\n=======================\n" + newWound);
			}else if (cAccuracy.getSelectedIndex() == ACCURACY_MINOR)
				minorAccuracyC(diceBag.rollD6());
			else
				majorAccuracyC();
			
		}else if (targetTabs.getSelectedIndex() == PUNCTURING_TAB){
			if (pAccuracy.getSelectedIndex() == ACCURACY_NULL){
				Wound newWound = new Wound(damageModel.getPunctWound(
				    opponent.getSelectedIndex(), puncturingZone.getSelectedIndex(),
				    diceBag.rollD6(), damageLevelInput.getSelectedIndex()));
				woundOutput.append("\n=======================\n" + newWound);
			}else if (pAccuracy.getSelectedIndex() == ACCURACY_MINOR){
				minorAccuracyP(diceBag.rollD6());
			}else{
				majorAccuracyP();
			}
			
		}else{
			Wound newWound = new Wound(damageModel.getGnrcWound(damageLevelInput
			    .getSelectedIndex() + 1));
			woundOutput.append("\n=======================\n" + newWound);
		}
	}
	
	/*
	 * Helper methods for determining and displaying available wound choices for
	 * characters with the Minor Accuracy perk. Players may choose any available
	 * wounds from +/- 1 of the location roll.
	 * 
	 * TODO: Roll these three methods into one method
	 */
	private void minorAccuracyP(int x) {
		Wound newWoundOne = new Wound(damageModel.getPunctWound(
		    opponent.getSelectedIndex(), puncturingZone.getSelectedIndex(),
		    (x - 1) % 6, damageLevelInput.getSelectedIndex()));
		Wound newWoundTwo = new Wound(damageModel.getPunctWound(
		    opponent.getSelectedIndex(), puncturingZone.getSelectedIndex(), x,
		    damageLevelInput.getSelectedIndex()));
		Wound newWoundThree = new Wound(damageModel.getPunctWound(
		    opponent.getSelectedIndex(), puncturingZone.getSelectedIndex(),
		    (x + 1) % 6, damageLevelInput.getSelectedIndex()));
		woundOutput.append("=======================\n" + newWoundOne);
		if (!newWoundOne.equals(newWoundTwo)){
			woundOutput.append("-----------------------\n" + newWoundTwo);
		}
		if (!newWoundTwo.equals(newWoundThree)){
			woundOutput.append("-----------------------\n" + newWoundThree);
		}
	}
	
	private void minorAccuracyC(int x) {
		Wound newWoundOne = new Wound(damageModel.getCutWound(
		    opponent.getSelectedIndex(), cuttingZone.getSelectedIndex(),
		    (x - 1) % 6, damageLevelInput.getSelectedIndex()));
		Wound newWoundTwo = new Wound(damageModel.getCutWound(
		    opponent.getSelectedIndex(), cuttingZone.getSelectedIndex(), x,
		    damageLevelInput.getSelectedIndex()));
		Wound newWoundThree = new Wound(damageModel.getCutWound(
		    opponent.getSelectedIndex(), cuttingZone.getSelectedIndex(),
		    (x + 1) % 6, damageLevelInput.getSelectedIndex()));
		woundOutput.append("=======================\n" + newWoundOne);
		if (!newWoundOne.equals(newWoundTwo)){
			woundOutput.append("-----------------------\n" + newWoundTwo);
		}
		if (!newWoundTwo.equals(newWoundThree)){
			woundOutput.append("-----------------------\n" + newWoundThree);
		}
	}
	
	private void minorAccuracyB(int x) {
		Wound newWoundOne = new Wound(damageModel.getBluntWound(
		    opponent.getSelectedIndex(), bludgeoningZone.getSelectedIndex(),
		    (x - 1) % 6, damageLevelInput.getSelectedIndex()));
		Wound newWoundTwo = new Wound(damageModel.getBluntWound(
		    opponent.getSelectedIndex(), bludgeoningZone.getSelectedIndex(), x,
		    damageLevelInput.getSelectedIndex()));
		Wound newWoundThree = new Wound(damageModel.getBluntWound(
		    opponent.getSelectedIndex(), bludgeoningZone.getSelectedIndex(),
		    (x + 1) % 6, damageLevelInput.getSelectedIndex()));
		woundOutput.append("=======================\n" + newWoundOne);
		if (!newWoundOne.equals(newWoundTwo)){
			woundOutput.append("-----------------------\n" + newWoundTwo);
		}
		if (!newWoundTwo.equals(newWoundThree)){
			woundOutput.append("-----------------------\n" + newWoundThree);
		}
	}
	
	/*
	 * Helper methods to display wound choices for characters with the Major
	 * Accuracy perk. This allows the player to choose any location in the attack
	 * zone for the wound. Since a zone my have up to 6 locations, these methods
	 * check all 6 and print only unique entries.
	 * 
	 * TODO: arrange into one method, use Set to remove duplicates?
	 */
	private void majorAccuracyB() {
		Wound newWoundOne = new Wound(damageModel.getBluntWound(
		    opponent.getSelectedIndex(), bludgeoningZone.getSelectedIndex(), 1,
		    damageLevelInput.getSelectedIndex()));
		Wound newWoundTwo = new Wound(damageModel.getBluntWound(
		    opponent.getSelectedIndex(), bludgeoningZone.getSelectedIndex(), 2,
		    damageLevelInput.getSelectedIndex()));
		Wound newWoundThree = new Wound(damageModel.getBluntWound(
		    opponent.getSelectedIndex(), bludgeoningZone.getSelectedIndex(), 3,
		    damageLevelInput.getSelectedIndex()));
		Wound newWoundFour = new Wound(damageModel.getBluntWound(
		    opponent.getSelectedIndex(), bludgeoningZone.getSelectedIndex(), 4,
		    damageLevelInput.getSelectedIndex()));
		Wound newWoundFive = new Wound(damageModel.getBluntWound(
		    opponent.getSelectedIndex(), bludgeoningZone.getSelectedIndex(), 5,
		    damageLevelInput.getSelectedIndex()));
		Wound newWoundSix = new Wound(damageModel.getBluntWound(
		    opponent.getSelectedIndex(), bludgeoningZone.getSelectedIndex(), 6,
		    damageLevelInput.getSelectedIndex()));
		woundOutput.append("=======================\n" + newWoundOne);
		if (!newWoundOne.equals(newWoundTwo)){
			woundOutput.append("-----------------------\n" + newWoundTwo);
		}
		if (!newWoundTwo.equals(newWoundThree)){
			woundOutput.append("-----------------------\n" + newWoundThree);
		}
		if (!newWoundThree.equals(newWoundFour)){
			woundOutput.append("-----------------------\n" + newWoundFour);
		}
		if (!newWoundFour.equals(newWoundFive)){
			woundOutput.append("-----------------------\n" + newWoundFive);
		}
		if (!newWoundFive.equals(newWoundSix)){
			woundOutput.append("-----------------------\n" + newWoundSix);
		}
	}
	
	private void majorAccuracyC() {
		Wound newWoundOne = new Wound(damageModel.getCutWound(
		    opponent.getSelectedIndex(), cuttingZone.getSelectedIndex(), 1,
		    damageLevelInput.getSelectedIndex()));
		Wound newWoundTwo = new Wound(damageModel.getCutWound(
		    opponent.getSelectedIndex(), cuttingZone.getSelectedIndex(), 2,
		    damageLevelInput.getSelectedIndex()));
		Wound newWoundThree = new Wound(damageModel.getCutWound(
		    opponent.getSelectedIndex(), cuttingZone.getSelectedIndex(), 3,
		    damageLevelInput.getSelectedIndex()));
		Wound newWoundFour = new Wound(damageModel.getCutWound(
		    opponent.getSelectedIndex(), cuttingZone.getSelectedIndex(), 4,
		    damageLevelInput.getSelectedIndex()));
		Wound newWoundFive = new Wound(damageModel.getCutWound(
		    opponent.getSelectedIndex(), cuttingZone.getSelectedIndex(), 5,
		    damageLevelInput.getSelectedIndex()));
		Wound newWoundSix = new Wound(damageModel.getCutWound(
		    opponent.getSelectedIndex(), cuttingZone.getSelectedIndex(), 6,
		    damageLevelInput.getSelectedIndex()));
		woundOutput.append("=======================\n" + newWoundOne);
		if (!newWoundOne.equals(newWoundTwo)){
			woundOutput.append("-----------------------\n" + newWoundTwo);
		}
		if (!newWoundTwo.equals(newWoundThree)){
			woundOutput.append("-----------------------\n" + newWoundThree);
		}
		if (!newWoundThree.equals(newWoundFour)){
			woundOutput.append("-----------------------\n" + newWoundFour);
		}
		if (!newWoundFour.equals(newWoundFive)){
			woundOutput.append("-----------------------\n" + newWoundFive);
		}
		if (!newWoundFive.equals(newWoundSix)){
			woundOutput.append("-----------------------\n" + newWoundSix);
		}
	}
	
	private void majorAccuracyP() {
		Wound newWoundOne = new Wound(damageModel.getPunctWound(
		    opponent.getSelectedIndex(), puncturingZone.getSelectedIndex(), 1,
		    damageLevelInput.getSelectedIndex()));
		Wound newWoundTwo = new Wound(damageModel.getPunctWound(
		    opponent.getSelectedIndex(), puncturingZone.getSelectedIndex(), 2,
		    damageLevelInput.getSelectedIndex()));
		Wound newWoundThree = new Wound(damageModel.getPunctWound(
		    opponent.getSelectedIndex(), puncturingZone.getSelectedIndex(), 3,
		    damageLevelInput.getSelectedIndex()));
		Wound newWoundFour = new Wound(damageModel.getPunctWound(
		    opponent.getSelectedIndex(), puncturingZone.getSelectedIndex(), 4,
		    damageLevelInput.getSelectedIndex()));
		Wound newWoundFive = new Wound(damageModel.getPunctWound(
		    opponent.getSelectedIndex(), puncturingZone.getSelectedIndex(), 5,
		    damageLevelInput.getSelectedIndex()));
		Wound newWoundSix = new Wound(damageModel.getPunctWound(
		    opponent.getSelectedIndex(), puncturingZone.getSelectedIndex(), 6,
		    damageLevelInput.getSelectedIndex()));
		woundOutput.append("=======================\n" + newWoundOne);
		if (!newWoundOne.equals(newWoundTwo)){
			woundOutput.append("-----------------------\n" + newWoundTwo);
		}
		if (!newWoundTwo.equals(newWoundThree)){
			woundOutput.append("-----------------------\n" + newWoundThree);
		}
		if (!newWoundThree.equals(newWoundFour)){
			woundOutput.append("-----------------------\n" + newWoundFour);
		}
		if (!newWoundFour.equals(newWoundFive)){
			woundOutput.append("-----------------------\n" + newWoundFive);
		}
		if (!newWoundFive.equals(newWoundSix)){
			woundOutput.append("-----------------------\n" + newWoundSix);
		}
	}
	
}
