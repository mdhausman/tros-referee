/**
 * @author Maura Hausman 2012
 * 
 *         Challenges solved by rolling dice are called Tests in TRoS. This
 *         class defines the GUI for the test rolling function. It calls for
 *         the number of dice (d10s) to be rolled, the target number (TN) for 
 *         a success, and will at the press of a button calculate it out.
 */

package referee;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.*;
import javax.swing.border.TitledBorder;

class TestRoller extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L; // parent appeasement
	
	private JFormattedTextField diceInput, tnInput;
	private JButton rollButton;
	private JTextArea diceOutput;
	private Dice diceBag;
	private NumberFormat format;
	
	public TestRoller(Dice d) {
		diceBag = d;
		format = NumberFormat.getIntegerInstance();
		setBorder(new TitledBorder("Dice Roller"));
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		
		// Dice and TN input
		JLabel diceLabel = new JLabel("Dice:");
		JLabel tnLabel = new JLabel("TN:");
		diceInput = new JFormattedTextField(format);
		diceInput.setColumns(2);
		this.tnInput = new JFormattedTextField(format);
		tnInput.setColumns(2);
		
		// Output window
		this.diceOutput = new JTextArea(5, 40);
		diceOutput.setEditable(false);
		JScrollPane textPane = new JScrollPane(diceOutput);
		
		// Button to roll the dice
		rollButton = new JButton("Roll");
		rollButton.addActionListener(this);
		
		/* Here we build the window. This gets weird. If I pick this up again to
		 * finish it, I might try it with separate constraints for each object.
		 * 
		 * As it is, I just redefine the constraints as necessary before using them
		 * to add to the JPanel.
		 */
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		add(diceLabel, constraints);
		
		constraints.gridx = 1;
		add(diceInput, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.anchor = GridBagConstraints.FIRST_LINE_END;
		add(tnLabel, constraints);
		
		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		add(tnInput, constraints);
		
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.insets = new Insets(0, 3, 0, 3);
		constraints.anchor = GridBagConstraints.CENTER;
		add(rollButton, constraints);
		
		constraints.gridx = 3;
		constraints.gridy = 0;
		constraints.gridheight = 2;
		add(textPane, constraints);
	}
	
	/* This method is triggered by clicking the button. 
	 * 
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			diceInput.commitEdit();
			try {
				tnInput.commitEdit();
				int n = ((Number) diceInput.getValue()).intValue();
				if (n > 0) {
					int tn = ((Number) tnInput.getValue()).intValue();
					diceOutput.append(diceBag.roll(n, tn));
				} else {
					diceOutput.append("I can't roll negative dice.\n");
				}
			} catch (ParseException e1) {
				diceOutput.append("Please provide a target number.\n");
			}
		} catch (ParseException e1) {
			diceOutput.append("How many dice are you rolling?\n");
		}
		
	}
	
}
