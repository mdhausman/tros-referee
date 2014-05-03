/**
 * @author Maura Hausman 2012
 * 
 *         This class stores the combat damage tables for the game. Different
 *         types of damage applied with differing force to different locations
 *         on the body produce different wounds, and the system in TRoS makes
 *         those distinctions.
 * 
 *         The damage tables are unwieldy for humans, but can be easily encoded
 *         for a computer. They're stored here in four-dimensional arrays. The
 *         dimensions are respectively: gender, attack zone (where the strike
 *         was aimed), location (where it actually hit), and damage (severity of
 *         the blow).
 */

//TODO: Finish bluntWounds, cutWounds, punctWounds
package referee;

public class DamageTables {
	private Wound[][][][] bluntWounds, cutWounds, punctWounds;
	private Wound[] gnrcWounds;
	
	public DamageTables() {
		this.bluntWounds = new Wound[2][14][6][5];
		this.cutWounds = new Wound[2][7][6][5];
		this.punctWounds = new Wound[2][7][6][5];
		this.gnrcWounds = new Wound[5];
		this.setTable();
	}
	
	/*
	 * The following four methods are simple getters that the DamageFinder can to
	 * reference the damage tables.
	 */
	public Wound getCutWound(int gender, int zone, int loc, int dmg) {
		return new Wound(this.cutWounds[gender][zone - 1][loc - 1][dmg - 1]);
	}
	
	public Wound getBluntWound(int gender, int zone, int loc, int dmg) {
		try{
			if (zone == 3 && loc == 6) // check for reroll
				return rerollBluntZone3(gender, dmg);
			return new Wound(this.bluntWounds[gender][zone - 1][loc - 1][dmg - 1]);
		}catch (IndexOutOfBoundsException e){
			return new Wound(0, 0, 0, "Invalid input.");
		}
	}
	
	public Wound getPunctWound(int gender, int zone, int loc, int dmg) {
		return new Wound(this.punctWounds[gender][zone - 8][loc - 1][dmg - 1]);
	}
	
	public Wound getGnrcWound(int dmg) {
		return new Wound(this.gnrcWounds[dmg - 1]);
	}
	
	/* Helper methods calling helper methods...
	 * This method loads the damage tables 
	 */
	private void setTable() {
		this.setBluntTable();
		this.setCutTable();
		this.setPunctTable();
		this.setGnrcTable();
	}
	
	
	/* Helper method to load the Bludgeoning damage table. Unfinished. Need to
	 * complete through zone 14, then do female tables. Fair Warning: This is
	 * messy. Considering storing to file for final version. It would make future
	 * development much easier.
	 */
	private void setBluntTable() {
		// Zone 1, area 1
		this.bluntWounds[0][0][0][0] = new Wound(0, 4, 4, "Surface wound.", "Foot",
		    true);
		this.bluntWounds[0][0][0][1] = new Wound(0, 3, 5,
		    "Some bruised flesh and bone.", "Foot");
		this.bluntWounds[0][0][0][2] = new Wound(0, 4, 6,
		    "Hit bone, may be broken (KD+3).", "Foot");
		this.bluntWounds[0][0][0][3] = new Wound(1, 5, 6, "Broken foot (KD+1).",
		    "Foot");
		this.bluntWounds[0][0][0][4] = new Wound(2, 8, 9,
		    "Foot totally mashed. Instant knock down.", "Foot");
		
		// Zone 1 area 2-4
		this.bluntWounds[0][0][1][0] = new Wound(0, 4, 5,
		    "Ow... ow ow ow dammit ow ow ow ow!", "Shin and lower leg");
		this.bluntWounds[0][0][1][1] = new Wound(0, 5, 6, "Bone chipped (KD+2)",
		    "Shin and lower leg");
		this.bluntWounds[0][0][1][2] = new Wound(0, 6, 7, "Bone chipped (KD)",
		    "Shin and lower leg");
		this.bluntWounds[0][0][1][3] = new Wound(2, 8, 9, "Broken (KD-3)",
		    "Shin and lower leg");
		this.bluntWounds[0][0][1][4] = new Wound(5, 10, 12,
		    "Compound fracture. Instant knockdown.", "Shin and lower leg");
		System.arraycopy(this.bluntWounds[0][0][1], 0, this.bluntWounds[0][0][2],
		    0, 5);
		System.arraycopy(this.bluntWounds[0][0][1], 0, this.bluntWounds[0][0][3],
		    0, 5);
		
		// Zone 1, area 5-6
		this.bluntWounds[0][0][4][0] = new Wound(0, 5, 4, "Glancing blow.",
		    "Knee and nearby areas", true);
		this.bluntWounds[0][0][4][1] = new Wound(0, 4, 5,
		    "Solid blow; funny-bone effect.", "Knee and nearby areas");
		this.bluntWounds[0][0][4][2] = new Wound(2, 8, 8,
		    "Torn ligament or similar wound (KD)", "Knee and nearby areas");
		this.bluntWounds[0][0][4][3] = new Wound(6, 10, 10,
		    "Shattered knee (KD-5)", "Knee and nearby areas");
		this.bluntWounds[0][0][4][4] = new Wound(8, 15, 12,
		    "Compound fracture. Instant knockdown.", "Knee and nearby areas");
		System.arraycopy(this.bluntWounds[0][0][4], 0, this.bluntWounds[0][0][5],
		    0, 5);
		
		// Zone 2, area 1-2
		this.bluntWounds[0][1][0][0] = new Wound(0, 5, 4, "Glancing blow.",
		    "Knee and nearby areas", true);
		this.bluntWounds[0][1][0][1] = new Wound(0, 4, 5,
		    "Solid blow; funny-bone effect.", "Knee and nearby areas");
		this.bluntWounds[0][1][0][2] = new Wound(2, 8, 8,
		    "Torn ligament or similar wound (KD)", "Knee and nearby areas");
		this.bluntWounds[0][1][0][3] = new Wound(6, 10, 10,
		    "Shattered knee (KD-5)", "Knee and nearby areas");
		this.bluntWounds[0][1][0][4] = new Wound(8, 15, 12,
		    "Compound fracture. Instant knockdown.", "Knee and nearby areas");
		System.arraycopy(this.bluntWounds[0][1][0], 0, this.bluntWounds[0][1][1],
		    0, 5);
		
		// Zone 2, areas 3-5
		this.bluntWounds[0][1][2][0] = new Wound(0, 4, 4, "Glancing blow.",
		    "Thigh", true);
		this.bluntWounds[0][1][2][1] = new Wound(0, 5, 4,
		    "Serious 'Charlie Horse' (KD+2).", "Thigh");
		this.bluntWounds[0][1][2][2] = new Wound(0, 7, 7,
		    "Bone is bruised, maybe broken (KD).", "Thigh");
		this.bluntWounds[0][1][2][3] = new Wound(3, 8, 9,
		    "Femur is broken, muscle is pulverized (KD-4).", "Thigh");
		this.bluntWounds[0][1][2][4] = new Wound(7, 10, 12,
		    "Compound fracture. Instant knockdown.", "Thigh");
		System.arraycopy(this.bluntWounds[0][1][2], 0, this.bluntWounds[0][1][3],
		    0, 5);
		System.arraycopy(this.bluntWounds[0][1][2], 0, this.bluntWounds[0][1][4],
		    0, 5);
		
		// Zone 2, area 6
		this.bluntWounds[0][1][5][0] = new Wound(0, 3, 4, "Thump.", "Hip");
		this.bluntWounds[0][1][5][1] = new Wound(0, 5, 6,
		    "Nearly dislocates leg, bone is bruied.", "Hip");
		this.bluntWounds[0][1][5][2] = new Wound(2, 8, 10,
		    "Leg dislocated, hip cracked; instant knockdown", "Hip");
		this.bluntWounds[0][1][5][3] = new Wound(10, 10, 12,
		    "Hip mangled badly, broken bone fragments cause bleeding", "Hip");
		this.bluntWounds[0][1][5][4] = new Wound(20, 100, 13,
		    "Pelvis destroyed, with massive bleeding.", "Hip");
		
		// Zone 3, area 1
		System.arraycopy(this.bluntWounds[0][1][5], 0, this.bluntWounds[0][2][0],
		    0, 5);
		
		// Zone 3, area 2
		this.bluntWounds[0][2][1][0] = new Wound(0, 3, 5,
		    "Glancing blow; will leave a nasty bruise.", "Upper abs");
		this.bluntWounds[0][2][1][1] = new Wound(0, 7, 6,
		    "Slightly winded, may lose consciousness (KO+3)", "Upper abs");
		this.bluntWounds[0][2][1][2] = new Wound(3, 10, 8,
		    "Badly winded, may vomit and/or lose consciousness (KO+1)", "Upper abs");
		this.bluntWounds[0][2][1][3] = new Wound(8, 10, 12,
		    "More serious internal damage and bleeding (KO)", "Upper abs");
		this.bluntWounds[0][2][1][4] = new Wound(15, 100, 15,
		    "Internal damage is real nasty. (KO-3)", "Upper abs");
		
		// Zone 3, area 3
		this.bluntWounds[0][2][2][0] = new Wound(0, 3, 5,
		    "Glancing blow; will leave a nasty bruise.", "Lower abs");
		this.bluntWounds[0][2][2][1] = new Wound(0, 7, 6,
		    "Slightly winded, may lose consciousness (KO+3)", "Lower abs");
		this.bluntWounds[0][2][2][2] = new Wound(3, 10, 8,
		    "Badly winded, may vomit and/or lose consciousness (KO+1)", "Lower abs");
		this.bluntWounds[0][2][2][3] = new Wound(8, 10, 12,
		    "More serious internal damage and bleeding (KO)", "Lower abs");
		this.bluntWounds[0][2][2][4] = new Wound(15, 100, 15,
		    "Internal damage is real nasty. (KO-3)", "Lower abs");
		
		// Zone 3, area 4-5
		this.bluntWounds[0][2][3][0] = new Wound(0, 5, 4,
		    "Glancing blow; will leave a nasty bruise.", "Ribcage", true);
		this.bluntWounds[0][2][3][1] = new Wound(0, 4, 5,
		    "Solid blow, ribs and muscle will be bruised", "Ribcage");
		this.bluntWounds[0][2][3][2] = new Wound(1, 8, 6,
		    "Winded, maybe with a broken rib. (KO+2)", "Ribcage");
		this.bluntWounds[0][2][3][3] = new Wound(3, 10, 9,
		    "Cracked ribs and serious winding. (KO)", "Ribcage");
		this.bluntWounds[0][2][3][4] = new Wound(9, 99, 15,
		    "Broken ribs and some internal damage. (KO-3)", "Ribcage");
		System.arraycopy(this.bluntWounds[0][2][3], 0, this.bluntWounds[0][2][4],
		    0, 5);
		
		// Zone 3, area 6 requires reroll in Zone 7, area 1-3
		// Accounted for in getBluntWound
		
		// Zone 4 area 1-2
		this.bluntWounds[0][3][0][0] = new Wound(0, 5, 4, "Thump.",
		    "Upper arm and shoulder", true);
		this.bluntWounds[0][3][0][1] = new Wound(0, 5, 5, "Charlie horse.",
		    "Upper arm and shoulder");
		this.bluntWounds[0][3][0][2] = new Wound(1, 7, 8,
		    "Broken humerus (hairline fracture), may drop item in that hand.",
		    "Upper arm and shoulder");
		this.bluntWounds[0][3][0][3] = new Wound(5, 10, 9,
		    "Broken bones, including collar bone or worse.",
		    "Upper arm and shoulder");
		this.bluntWounds[0][3][0][4] = new Wound(10, 13, 12,
		    "Entire shoulder caves in. Lots of blood and bone fragments.",
		    "Upper arm and shoulder");
		System.arraycopy(this.bluntWounds[0][3][0], 0, this.bluntWounds[0][3][1],
		    0, 5);
		
		// Zone 4 area 3
		this.bluntWounds[0][3][2][0] = new Wound(0, 5, 4,
		    "Glancing blow—will leave a nasty bruise.", "Upper body", true);
		this.bluntWounds[0][3][2][1] = new Wound(0, 4, 5,
		    "Solid blow, ribs and muscle will be bruised.", "Upper body");
		this.bluntWounds[0][3][2][2] = new Wound(1, 8, 6,
		    "Winded, maybe with a broken rib. May lose consciousness (+2)",
		    "Upper body");
		this.bluntWounds[0][3][2][3] = new Wound(3, 10, 9,
		    "Cracked ribs and serious winding. May lose consciousness.",
		    "Upper body");
		this.bluntWounds[0][3][2][4] = new Wound(9, 99, 15,
		    "Broken ribs (perhaps several) and some internal damange and "
		        + "bleeding. May lose consciousness (-3)", "Upper body");
		
		// Zone 4, area 4
		this.bluntWounds[0][3][3][0] = new Wound(0, 4, 5,
		    "Glancing blow, crick in neck remains.", "Neck");
		this.bluntWounds[0][3][3][1] = new Wound(1, 7, 9,
		    "Damage to throat and air tracts.", "Neck");
		this.bluntWounds[0][3][3][2] = new Wound(3, 10, 12,
		    "Crushed larynx. May lose consciousness or suffocate.", "Neck");
		this.bluntWounds[0][3][3][3] = new Wound(3, 99, 15,
		    "Cracked vertebrae and other throat problems.", "Neck");
		this.bluntWounds[0][3][3][4] = new Wound(0, 0, 0, "Neck instantly broken.",
		    "Neck");
		
		// Zone 4, area 5
		this.bluntWounds[0][3][4][0] = new Wound(0, 5, 0,
		    "Gonna leave a shiner... May lose consciousness (+3)",
		    "Lower head/Face", true);
		this.bluntWounds[0][3][4][1] = new Wound(1, 8, 6,
		    "Broken nose or lost teeth (or both). May lose consciousness (+1)",
		    "Lower head/Face");
		this.bluntWounds[0][3][4][2] = new Wound(4, 10, 0, "Bones near eye are "
		    + "smashed; eye is considered lost. Lose 1/2 CP. (KO at -1)",
		    "Lower head/Face");
		this.bluntWounds[0][3][4][3] = new Wound(6, 12, 9,
		    "Jaw has been shattered, with a concussion. May lose consciousness "
		        + "(-3)", "Lower head/Face");
		this.bluntWounds[0][3][4][4] = new Wound(0, 0, 0,
		    "Death. Destruction of cerebellum. Really messy.", "Lower head/Face");
		
		// Zone 4, area 6
		this.bluntWounds[0][3][5][0] = new Wound(0, 8, 5,
		    "Glancing blow,  May lose consciousness (+3)", "Upper Head", true);
		this.bluntWounds[0][3][5][1] = new Wound(3, 8, 8,
		    "Concussion. May lose consciousness.", "Upper Head");
		this.bluntWounds[0][3][5][2] = new Wound(4, 10, 12,
		    "Cracked skull. May lose consciousness (-3)", "Upper Head");
		this.bluntWounds[0][3][5][3] = new Wound(6, 99, 99,
		    "Skull is shattered. Character is unconscious and may not recover "
		        + "(or will have brain damage if he does)", "Upper Head");
		this.bluntWounds[0][3][5][4] = new Wound(0, 0, 0,
		    "Real, real messy. Instant death.", "Upper Head");
		
		// Zone 5, area 1-2
		this.bluntWounds[0][4][0][0] = new Wound(0, 5, 4, "Thump.", "Shoulders",
		    true);
		this.bluntWounds[0][4][0][1] = new Wound(0, 5, 5, "Charlie horse.",
		    "Shoulders");
		this.bluntWounds[0][4][0][2] = new Wound(1, 7, 8,
		    "Broken humerus (hairline). May drop item in hand.", "Shoulders");
		this.bluntWounds[0][4][0][3] = new Wound(5, 10, 9,
		    "Broken bones, including collar bone or worse.", "Shoulders");
		this.bluntWounds[0][4][0][4] = new Wound(10, 13, 12,
		    "Entire shoulder caves in. Lots of blood and bone fragments.",
		    "Shoulders");
		System.arraycopy(this.bluntWounds[0][4][0], 0, this.bluntWounds[0][4][1],
		    0, 5);
		
		// Zone 5, area 3
		System.arraycopy(this.bluntWounds[0][3][4], 0, this.bluntWounds[0][4][2],
		    0, 5);
		
		// Zone 5, area 4-6
		System.arraycopy(this.bluntWounds[0][3][5], 0, this.bluntWounds[0][4][3],
		    0, 5);
		System.arraycopy(this.bluntWounds[0][3][5], 0, this.bluntWounds[0][4][4],
		    0, 5);
		System.arraycopy(this.bluntWounds[0][3][5], 0, this.bluntWounds[0][4][5],
		    0, 5);
		
		// Zone 6, area 1-3
		this.bluntWounds[0][5][0][0] = new Wound(0, 4, 4, "Glancing blow.",
		    "Inner Thigh", true);
		this.bluntWounds[0][5][0][1] = new Wound(0, 5, 4,
		    "Serious Charlie horse (KD+2)", "Inner Thigh");
		this.bluntWounds[0][5][0][2] = new Wound(0, 7, 7,
		    "Bone is bruised, maybe broken (KD)", "Inner Thigh");
		this.bluntWounds[0][5][0][3] = new Wound(3, 8, 9,
		    "Femur is broken, muscle is pulverized (KD-4)", "Inner Thigh");
		this.bluntWounds[0][5][0][4] = new Wound(7, 10, 12,
		    "Compound fracture. Instant knockdown.", "Inner Thigh");
		System.arraycopy(this.bluntWounds[0][5][0], 0, this.bluntWounds[0][5][1],
		    0, 5);
		System.arraycopy(this.bluntWounds[0][5][0], 0, this.bluntWounds[0][5][2],
		    0, 5);
		
		// Zone 6, area 4
		// This is a gendered area
		this.bluntWounds[0][5][3][0] = new Wound(0, 7, 9,
		    "Yup... Sorry. Pain drops by 10 after 1d6-1 min.", "Groin");
		this.bluntWounds[0][5][3][1] = new Wound(0, 9, 10,
		    "Yup... Sorry. Pain drops by 10 after 1d6-1 min. (KO)", "Groin");
		this.bluntWounds[0][5][3][2] = new Wound(3, 11, 15,
		    "Surface organs destroyed. May lose consciousness (KO-2)", "Groin");
		this.bluntWounds[0][5][3][3] = new Wound(18, 99, 99,
		    "Instant loss of consciousness. The damage is real, real bad.", "Groin");
		this.bluntWounds[0][5][3][4] = new Wound(20, 99, 99,
		    "Weapon destroys pelvis. Death is imminent.", "Groin");
		
		// Zone 6, area 5
		this.bluntWounds[0][5][4][0] = new Wound(0, 3, 5,
		    "Glancing blow—will leave a nasty bruise.", "Abdomen");
		this.bluntWounds[0][5][4][1] = new Wound(0, 7, 6,
		    "Slightly wounded, may lose consciousness (KO+3)", "Abdomen");
		this.bluntWounds[0][5][4][2] = new Wound(3, 10, 8,
		    "Badly wounded, may vomit and/or lose consciousness.", "Abdomen");
		this.bluntWounds[0][5][4][3] = new Wound(8, 10, 12,
		    "More serious internal damage and bleeding. (KO)", "Abdomen");
		this.bluntWounds[0][5][4][4] = new Wound(15, 99, 15,
		    "Internal damage is real nasty. May lose consciousness (KO-3)",
		    "Abdomen");
		
		// Zone 6, area 6
		System.arraycopy(this.bluntWounds[0][3][4], 0, this.bluntWounds[0][5][5],
		    0, 5);
		
		// Zone 7, area 1
		this.bluntWounds[0][6][0][0] = new Wound(0, 5, 4,
		    "Surface graze. May drop held item.", "Hand", true);
		this.bluntWounds[0][6][0][1] = new Wound(0, 3, 4,
		    "Bruised bone. May frop held item (-3).", "Hand");
		this.bluntWounds[0][6][0][2] = new Wound(0, 5, 5,
		    "Smashed fingers. Drop held items.", "Hand");
		this.bluntWounds[0][6][0][3] = new Wound(0, 7, 7,
		    "Hand broken. Drop held items.", "Hand");
		this.bluntWounds[0][6][0][4] = new Wound(2, 8, 10,
		    "Hand is mashed badly. Unusable.", "Hand");
		
		// Zone 7, area 2-3
		this.bluntWounds[0][6][1][0] = new Wound(0, 4, 4, "Glancing thump.",
		    "Forearm", true);
		this.bluntWounds[0][6][1][1] = new Wound(2, 5, 6,
		    "Bone chipped. May drop held item.", "Forearm");
		this.bluntWounds[0][6][1][2] = new Wound(3, 5, 6,
		    "Bone chipped. Drop held item.", "Forearm");
		this.bluntWounds[0][6][1][3] = new Wound(6, 7, 8,
		    "Arm broken. Lots of blood.", "Forearm");
		this.bluntWounds[0][6][1][4] = new Wound(12, 10, 10,
		    "Arm destroyed, perhaps cut off.", "Forearm");
		
		System.arraycopy(this.bluntWounds[0][6][1], 0, this.bluntWounds[0][6][2],
		    0, 5);
		
		// Zone 7, area 4
		this.bluntWounds[0][6][3][0] = new Wound(0, 5, 4, "Glancing blow.",
		    "Elbow", true);
		this.bluntWounds[0][6][3][1] = new Wound(0, 4, 5,
		    "Solid blow; funny-bone effect. May drop held item.", "Elbow");
		this.bluntWounds[0][6][3][2] = new Wound(2, 6, 6,
		    "Torn ligament or similar wound. Drop held item.", "Elbow");
		this.bluntWounds[0][6][3][3] = new Wound(5, 8, 9, "Elbow shattered.",
		    "Elbow");
		this.bluntWounds[0][6][3][4] = new Wound(12, 20, 10,
		    "Arm torn off at the elbow.", "Elbow");
		
		// Zone 7, area 5-6
		System.arraycopy(this.bluntWounds[0][3][0], 0, this.bluntWounds[0][6][4],
		    0, 5);
		System.arraycopy(this.bluntWounds[0][3][0], 0, this.bluntWounds[0][6][5],
		    0, 5);
		
		// Zone 8, area 1-5
		// Copy Zone I, locations 1-5
		System.arraycopy(this.bluntWounds[0][0], 0, this.bluntWounds[0][7], 0, 5);
		
		// Zone 8, area 6
		this.bluntWounds[0][7][5][0] = new Wound(0, 0, 0,
		    "Passed between legs. Sorry, you missed.");
		this.bluntWounds[0][7][5][1] = this.bluntWounds[0][7][5][0];
		this.bluntWounds[0][7][5][2] = this.bluntWounds[0][7][5][0];
		this.bluntWounds[0][7][5][3] = this.bluntWounds[0][7][5][0];
		this.bluntWounds[0][7][5][4] = this.bluntWounds[0][7][5][0];
		
		// Zone 9, area 1
		System.arraycopy(this.bluntWounds[0][7][4], 0, this.bluntWounds[0][8][0],
		    0, 5);
		
		// Zone 9, area 2-5
		System.arraycopy(this.bluntWounds[0][1][2], 0, this.bluntWounds[0][8][1],
		    0, 5);
		System.arraycopy(this.bluntWounds[0][1][2], 0, this.bluntWounds[0][8][2],
		    0, 5);
		System.arraycopy(this.bluntWounds[0][1][2], 0, this.bluntWounds[0][8][3],
		    0, 5);
		System.arraycopy(this.bluntWounds[0][1][2], 0, this.bluntWounds[0][8][4],
		    0, 5);
		
		// Zone 9, area 6
		System.arraycopy(this.bluntWounds[0][1][5], 0, this.bluntWounds[0][8][5],
		    0, 5);
		
		// Zone 10, area 1-2
		System.arraycopy(this.bluntWounds[0][2][2], 0, this.bluntWounds[0][9][0],
		    0, 5);
		System.arraycopy(this.bluntWounds[0][2][2], 0, this.bluntWounds[0][9][1],
		    0, 5);
		
		// Zone 10, area 3
		
	}
	
	/*
	 * These two methods set the Cutting and Puncturing Damage Tables much like
	 * the previous one sets them. Or would, if I implemented them.
	 */
	private void setCutTable() {
		
	}
	
	private void setPunctTable() {
		
	}
	
	// This method sets the Generic Damage Table.
	private void setGnrcTable() {
		this.gnrcWounds[0] = new Wound(0, 2, 5);
		this.gnrcWounds[1] = new Wound(0, 5, 8);
		this.gnrcWounds[2] = new Wound(0, 8, 12);
		this.gnrcWounds[3] = new Wound(0, 11, 16);
		this.gnrcWounds[4] = new Wound(0, 100, 20);
		// That was easy.
	}
	
	// Helper method to handle a reroll in Blunt Damage Zone III
	private Wound rerollBluntZone3(int gender, int damage) {
		Dice roller = new Dice();
		int loc = roller.nextInt(3) + 1;
		Wound blunt = getBluntWound(gender, 7, loc, damage);
		return blunt;
	}
}
