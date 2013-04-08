/**
 * @author Matthew Hausman 2012
 * 
 *         This class represents the two types of dice one uses in TRoS: the d6
 *         and the d10. Of the two, d10 is most common.
 */

package referee;

import java.util.Random;

class Dice extends Random {
	private static final long serialVersionUID = 1L; // parent appeasement
	
	public Dice() {
		super();
	}
	
	// This method returns a random number between 1 and 6.
	public int rollD6() {
		return this.nextInt(6) + 1;
	}
	
	/*
	 * This method rolls a d10. If the result is a 10, the die is rerolled and 
	 * the results are added together. This rerolling continues until the die 
	 * comes out as something other than a 10.
	 * 
	 * To simulate this, I've used recursion.
	 */
	public int roll() {
		int result = this.nextInt(10) + 1;
		return (result == 10) ? result + roll() : result;
	}
	
	/* This method rolls nd10 dice, tests each result to target number tn, and
	 * returns the TestResult as a String to be displayed in TestRoller.
	 */
	public String roll(int n, int tn) {
		TestResult result = new TestResult();
		for (int i = 0; i < n; i++) {
			int d = this.roll();
			result.add(d, (d >= tn));
		}
		return result.toString();
	}
	
	/* Helper class to store die results in a useful String format.
	 */
	private class TestResult {
		private int successes;
		private String dice;
		
		public TestResult() {
			this.successes = 0;
			this.dice = "";
		}
		
		public void add(int result, boolean success) {
			if (success) {
				dice += "[" + result + "] ";
				successes++;
			} else
				dice += result + " ";
		}
		
		public String toString() {
			return "Successes: " + successes + "\n" + dice + "\n";
		}
	}
	
}
