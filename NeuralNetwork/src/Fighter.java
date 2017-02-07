
public class Fighter {

	private double[] nothing;
	private double[] fight;
	private double[] hide;
	private double[] collectAmmo;
	private double[] getHealing;

	private final int angriff = 0;
	private final int verstecken = 1;
	private final int sammeln = 2;
	private final int heilung = 3;

	public Fighter() {

		nothing = new double[] { 0, 0, 0, 0 };

		fight = new double[] { 1, 0, 0, 0 };
		hide = new double[] { 0, 1, 0, 0 };
		collectAmmo = new double[] { 0, 0, 1, 0 };
		getHealing = new double[] { 0, 0, 0, 1 };
	}

	public String showReaction(double[] outputVector) {

		if (equal(outputVector, nothing)) {
			return "Nichtstun";
		} else if (equal(outputVector, fight)) {
			return "Angriff";
		} else if (equal(outputVector, hide)) {
			return "Verstecken";
		} else if (equal(outputVector, collectAmmo)) {
			return "Munition sammeln";
		} else if (equal(outputVector, getHealing)) {
			return "Heilung von VerbŁndeten erhalten";
		} else {
			return "Untentschlossen";
		}

	}

	private boolean equal(double[] a, double[] b) {
		if (a.length != b.length) {
			return false;
		}
		for (int i = 0; i < a.length; i++) {
			if (Math.abs(a[i] - b[i]) > 0.1) { // toleranzbereich weil exakt 1 /
												// 0 nie erreicht
				return false;
			}
		}
		return true;
	}

	public int[] calculateExpectedOutput(int health, int ammo, int nrEnemies, int nrFriends) {
		int[] y = new int[] { 0, 0, 0, 0 };

		if (health < 3 && nrFriends > 0) {
			y[heilung] = 1; // heilung durch verbuendeten
		} else if (health < 3 && nrFriends == 0) {
			y[verstecken] = 1; // verstecken, keine heilung moeglich
		} else if (ammo < 3 && !ueberzahl(nrEnemies, nrFriends)) {
			y[sammeln] = 1; // sammle munition
		} else if (ammo < 3 && ueberzahl(nrEnemies, nrFriends)) {
			y[verstecken] = 1; // sammle keine munition, weil zu viele feinde in
								// der naehe
		} else if (!ueberzahl(nrEnemies, nrFriends)) {
			y[angriff] = 1; // yeah, we can fight!
		} else {
			y[verstecken] = 1; // zu viele. Nope
		}

		return y;
	}

	private boolean ueberzahl(int nrEnemies, int nrFriends) {
		return nrEnemies - nrFriends >= 3;
	}

}
