/**
 * @author Maura Hausman 2012
 * 
 *         A class to represent wounds in The Riddle of Steel. Wounds have
 *         three numeric attributes: Bloodloss, Pain, and Shock. They may have
 *         additional effects listed in their descriptions. For now, these are
 *         left for players to keep track of. Future functionality is planned 
 *         to handle Knockdown (KD) and Knockout (KO).
 * 
 *         In addition, wounds have a location. This is important in gameplay,
 *         but for now is left for players to track.
 */

package referee;

class Wound {
	private int blood, pain, shock;
	private String location, description;
	
	// This tracks whether or not WP can mitigate the Shock from this wound.
	private boolean shockWP;
	
	public Wound(int a, int b, int c) {
		this.blood = a;
		this.shock = b;
		this.pain = c;
		this.description = "";
		this.location = "";
	}
	
	public Wound(int a, int b, int c, String d) {
		this.blood = a;
		this.shock = b;
		this.pain = c;
		this.description = d;
		this.location = "";
	}
	
	public Wound(int a, int b, int c, String d, String e) {
		this.blood = a;
		this.shock = b;
		this.pain = c;
		this.description = d;
		this.location = e;
	}
	
	public Wound(int a, int b, int c, String d, String e, boolean f) {
		this.blood = a;
		this.shock = b;
		this.pain = c;
		this.description = d;
		this.location = e;
		this.shockWP = f;
	}
	
	public Wound(Wound that) {
		this.blood = that.blood;
		this.pain = that.pain;
		this.shock = that.shock;
		this.description = that.description;
		this.location = that.location;
		this.shockWP = that.shockWP;
	}
	
	public String toString() {
		if (shockWP){
			return "BL: " + blood + " Pain: " + pain + "-WP" + " Shock: " + shock
			    + "-WP\n" + location + "\n" + description + "\n";
		}
		return "BL: " + blood + " Pain: " + pain + "-WP" + " Shock: " + shock
		    + "\n" + location + "\n" + description + "\n";
	}
	
	public boolean equals(Wound that) {
		return (this.blood == that.blood && this.pain == that.pain
		    && this.shock == that.shock && this.location == that.location
		    && this.description == that.description && 
		    this.shockWP == that.shockWP);
	}
}
