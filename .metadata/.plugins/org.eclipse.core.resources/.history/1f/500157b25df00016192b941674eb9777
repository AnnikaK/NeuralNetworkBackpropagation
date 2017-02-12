import java.util.ArrayList;

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
	private Network net;
	private ArrayList<Pattern> totalSet;
	private ArrayList<Pattern> trainingSet;

	public Fighter(Network net) {

		nothing = new double[] { 0, 0, 0, 0 };

		fight = new double[] { 1, 0, 0, 0 };
		hide = new double[] { 0, 1, 0, 0 };
		collectAmmo = new double[] { 0, 0, 1, 0 };
		getHealing = new double[] { 0, 0, 0, 1 };

		this.net = net;
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
			return "Heilung von Verbündetem erhalten";
		} else {
			return "Untentschlossen";
		}

	}

	private boolean equal(double[] a, double[] b) {
		if (a.length != b.length) {
			return false;
		}
		for (int i = 0; i < a.length; i++) {
			if (Math.abs(a[i] - b[i]) > 0.01) { // toleranzbereich weil exakt 1
												// bzw.
												// 0 nie erreicht
				return false;
			}
		}
		return true;
	}

	public ArrayList<Pattern> generateTrainingSamples() {
		totalSet = new ArrayList<>();
		trainingSet = new ArrayList<>();

		int counter = 1;

		for (int a = 0; a < 11; a++) {
			for (int b = 0; b < 11; b++) {
				for (int c = 0; c < 11; c++) {
					for (int d = 0; d < 11; d++) {

						ArrayList<String> strings = new ArrayList<>();
						strings.add(Integer.toBinaryString(a));
						strings.add(Integer.toBinaryString(b));
						strings.add(Integer.toBinaryString(c));
						strings.add(Integer.toBinaryString(d));

						int[] x = createInputVector(strings);
						int[] y = calculateExpectedOutput(a, b, c, d);

						Pattern sample = new Pattern(x, y);
						totalSet.add(sample);
						if (counter % 1000 != 0) {

							trainingSet.add(sample); // don't use all for
														// training
						}

						counter++;

					}

				}
			}
		}
		;
		return trainingSet;
	}

	public int checkForCorrectness() {
		int nrOfFalses = 0;
		for (Pattern p : totalSet) {
			double[] output = net.run(p);
			if (!equal(output, p.getY())) {
				nrOfFalses++;
			}
		}
		return nrOfFalses;
	}

	private boolean equal(double[] a, int[] b) {
		if (a.length != b.length) {
			return false;
		}
		for (int i = 0; i < a.length; i++) {
			if (Math.abs(a[i] - b[i]) > 0.01) { // toleranzbereich weil exakt 1
												// bzw.
												// 0 nie erreicht
				return false;
			}
		}
		return true;
	}

	public int[] createInputVector(ArrayList<String> binaryStrings) {
		int[] inputVector = new int[net.getInputLayerSize()];
		int offset = 0;
		int elements = net.getInputLayerSize() / binaryStrings.size();
		for (int i = 0; i < binaryStrings.size(); i++) {
			adjustString(inputVector, offset, binaryStrings.get(i));
			offset += elements;
		}
		return inputVector;
	}

	private void adjustString(int[] x, int offset, String str) {

		int index = offset + (4 - str.length());
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '1') {
				x[index] = 1;
				index++;
			} else {
				x[index] = 0;
				index++;
			}

		}

	}

	public int[] calculateExpectedOutput(int health, int ammo, int nrEnemies, int nrFriends) {
		int[] y = new int[] { 0, 0, 0, 0 };

		if (health == 0) {

		} else if (health < 3 && nrFriends > 0) {
			y[heilung] = 1;
		} else if (health < 3 || ueberzahl(nrEnemies, nrFriends)) {
			y[verstecken] = 1;
		} else if (ammo == 0) {
			y[sammeln] = 1;
		} else if (nrEnemies != 0) {
			y[angriff] = 1;
		}

		return y;
	}

	private boolean ueberzahl(int nrEnemies, int nrFriends) {
		return nrEnemies - nrFriends > 3;
	}

	public int getNrOfSamples() {
		return totalSet.size();
	}

}
