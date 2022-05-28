/*=====================================================*/
/* Project Title: Legends Of Valor                     */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Authors:                                    */
/*    - Jack Giunta                                    */
/*    - Victoria-Rose Burke                            */
/*    - Victor Vicente                                 */
/*=====================================================*/

package Items;

public class LegendsWeapon extends LegendsItem {
	
	// Theoretical limits to Weapons
	public static final int ABSOLUTE_MAX_DAMAGE = 1000;
	public static final int ABSOLUTE_MAX_DURABILITY = 500;

	private int damage;

	private boolean dualHanded;

	private int currentDurability;
	private int maxDurability;

	/* =================== */
	/* Constructor Methods */
	/* =================== */
	
	public LegendsWeapon(int ID, String name, int cost, int minLevel, int damage, boolean dualHanded,
			int maxDurability) {
		super(ID, name, cost, minLevel);
		this.setDamage(damage);
		this.setDualHanded(dualHanded);
		this.setMaxDurability(maxDurability);
		this.regenDurability(1);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */
	
	public int getCurrentDurability() {
		return currentDurability;
	}

	public void regenDurability(double regenDurabilityPercentage) {
		int toRegen = (int) Math.round(regenDurabilityPercentage * this.getMaxDurability());

		this.currentDurability = Math.min(this.getMaxDurability(), this.getCurrentDurability() + toRegen);
	}

	public boolean loseDurability(int toLose) {
		this.currentDurability = Math.max(0, this.getCurrentDurability() - toLose);

		return this.currentDurability == 0;
	}

	public int getDamage() {
		return this.damage;
	}

	public boolean getDualHanded() {
		return this.dualHanded;
	}

	public int getMaxDurability() {
		return this.maxDurability;
	}

	private void setDamage(int damage) {
		if (damage > LegendsWeapon.ABSOLUTE_MAX_DAMAGE) {
			System.out.println("Damage value exceeds the maximum allowed value!");
			return;
		}
		this.damage = damage;
	}

	private void setDualHanded(boolean dualHanded) {
		this.dualHanded = dualHanded;
	}

	private void setMaxDurability(int maxDurability) {
		if (maxDurability > LegendsWeapon.ABSOLUTE_MAX_DURABILITY) {
			System.out.println("Max durability value exceeds the maximum allowed value!");
			return;
		}
		this.maxDurability = maxDurability;
	}
	
	/* =========== */
	/* Aux Methods */
	/* =========== */

	@Override
	public String toString() {
		if (this.getDualHanded()) {
			return "   "+this.getName() + " is a 2-handed weapon that deals " + this.getDamage()
					+ " in damage and has durability " + this.getCurrentDurability() + "/" + this.getMaxDurability();
		} else {
			return this.getName() + " is a 1-handed weapon that deals " + this.getDamage()
					+ " in damage and has durability " + this.getCurrentDurability() + "/" + this.getMaxDurability();
		}
	}
}
