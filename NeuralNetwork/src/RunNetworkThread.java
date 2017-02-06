import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Platform;
import javafx.scene.control.ComboBox;

public class RunNetworkThread extends Thread {

	private Network net;
	private Window window;
	private Fighter fighter;

	public RunNetworkThread(Network net, Window window, Fighter fighter) {
		this.net = net;
		this.window = window;
		this.fighter = fighter;
		this.setDaemon(true);
		this.start();
	}

	@Override
	public void run() {

		ArrayList<ComboBox<Integer>> comboBoxes = window.getComboboxes();
		ArrayList<String> binaryStrings = new ArrayList<>();
		for (int i = 0; i < comboBoxes.size(); i++) {
			binaryStrings.add(Integer.toBinaryString(comboBoxes.get(i).getValue()));
		}

		int[] inputVector = new int[net.getInputLayerSize()];
		int offset = 0;
		int elements = net.getInputLayerSize() / binaryStrings.size();
		for (int i = 0; i < binaryStrings.size(); i++) {
			adjustString(inputVector, offset, binaryStrings.get(i));
			offset += elements;
		}

		Pattern inputPattern = new Pattern(inputVector, null); // no target
																// vector
		double[] outputVector = net.run(inputPattern);
		String reaction = fighter.showReaction(outputVector);
		Platform.runLater(() -> window.react(reaction));

		Platform.runLater(() -> window.enableRunningButton());
		// System.out.println(Arrays.toString(inputPattern.getX()));

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

}
