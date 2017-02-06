
public class Fighter {

	private double[] nothing;
	private double[] fight;
	private double[] hide;
	private double[] collectAmmo;
	private double[] getHealing;


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

}
