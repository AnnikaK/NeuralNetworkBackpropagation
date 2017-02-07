import java.util.ArrayList;

import javafx.application.Platform;

public class LearningThread extends Thread {

	private Network net;
	private Window window;
	private Fighter fighter;

	public LearningThread(Network net, Window window, Fighter fighter) {
		this.net = net;
		this.window = window;
		this.fighter = fighter;
		this.setDaemon(true);
		this.start();
	}

	@Override
	public void run() {

		Platform.runLater(() -> window.setStatus("Generiere Trainingsset"));
		ArrayList<Pattern> trainingSet = fighter.generateTrainingSamples();

		Platform.runLater(() -> window.setStatus("Trainiere"));

		net.backpropagation_learning(trainingSet, window);

		Platform.runLater(() -> window.setStatus("trainiert"));
		Platform.runLater(() -> window.enableUserInterface());

	}

}
