import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Window extends Application {

	private Network net;
	private ArrayList<Pattern> examples;
	private Label statusContent;
	private Label errorLabel;
	private Label networkError;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		setupNetwork();

		Pane root = new Pane();
		createWindow(root);
		Scene scene = new Scene(root, 600, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Neuronales Netzwerk");
		primaryStage.show();

		// net.backpropagation_learning(examples);

	}

	// ---------------------------User Interface

	private void createWindow(Pane root) {

		Label status = new Label("Status: ");
		status.setLayoutX(10);
		status.setLayoutY(10);
		statusContent = new Label("untrainiert");
		statusContent.setLayoutX(60);
		statusContent.setLayoutY(10);

		root.getChildren().add(status);
		root.getChildren().add(statusContent);

		Label err = new Label("Netzwerkfehler: ");
		networkError = new Label("noch keine Daten vorhanden.");
		err.setLayoutX(200);
		err.setLayoutY(10);
		networkError.setLayoutX(300);
		networkError.setLayoutY(10);

		root.getChildren().add(err);
		root.getChildren().add(networkError);

	}

	// -------------------------------------------Network

	private void setupNetwork() {
		examples = createPatterns();
		int[] structure = { 16, 6, 4 };
		net = new Network(structure, 0.3, 0.0001, 7000);

	}

	private static ArrayList<Pattern> createPatterns() {
		// TODO Auto-generated method stub
		return null;
	}

}
