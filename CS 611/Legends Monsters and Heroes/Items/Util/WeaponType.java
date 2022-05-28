/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Items.Util;

public class WeaponType {

	// Absolute limits to a weapons damage and maximum durability
	public static final int ABSOLUTE_MAX_DAMAGE = 1000;
	public static final int ABSOLUTE_MAX_DURABILITY = 500;

	private int damage;

	private boolean dualHanded;

	private int maxDurability;
	
	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public WeaponType(int damage, boolean dualHanded, int maxDurability) {
		this.setDamage(damage);
		this.setDualHanded(dualHanded);
		this.setMaxDurability(maxDurability);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

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
		if (damage > WeaponType.ABSOLUTE_MAX_DAMAGE) {
			System.out.println("Damage value exceeds the maximum allowed value!");
			return;
		}
		this.damage = damage;
	}

	private void setDualHanded(boolean dualHanded) {
		this.dualHanded = dualHanded;
	}

	private void setMaxDurability(int maxDurability) {
		if (maxDurability > WeaponType.ABSOLUTE_MAX_DURABILITY) {
			System.out.println("Max durability value exceeds the maximum allowed value!");
			return;
		}
		this.maxDurability = maxDurability;
	}

}
