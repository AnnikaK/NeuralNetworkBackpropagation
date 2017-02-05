
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Window extends Application {

	private Network net;
	private ArrayList<Pattern> examples;
	private Label statusContent;
	private Label errorLabel;
	private Label networkError;

	private Button train;
	private Button run;

	private ComboBox<Integer> b1;
	private ComboBox<Integer> b2;
	private ComboBox<Integer> b3;
	private ComboBox<Integer> b4;
	private Label react;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		setupNetwork();

		Pane root = new Pane();
		createWindow(root);
		Scene scene = new Scene(root, 600, 400);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Neuronales Netzwerk");
		primaryStage.show();

		// net.backpropagation_learning(examples);

	}

	// ---------------------------User Interface

	private void createWindow(Pane root) {

		// labels on top
		Label status = new Label("Status: ");
		status.setFont(new Font(16));
		status.setLayoutX(20);
		status.setLayoutY(10);
		statusContent = new Label("untrainiert");
		statusContent.setFont(new Font(16));
		statusContent.setLayoutX(70);
		statusContent.setLayoutY(10);

		root.getChildren().add(status);
		root.getChildren().add(statusContent);

		Label err = new Label("Netzwerkfehler: ");
		err.setFont(new Font(16));
		networkError = new Label("noch keine Daten vorhanden.");
		networkError.setFont(new Font(16));
		err.setLayoutX(240);
		err.setLayoutY(10);
		networkError.setLayoutX(360);
		networkError.setLayoutY(10);

		root.getChildren().add(err);
		root.getChildren().add(networkError);

		train = new Button("Netzwerk trainieren");
		train.setFont(new Font(16));
		train.setLayoutX(20);
		train.setLayoutY(60);
		train.setOnAction((e) -> net.backpropagation_learning(examples));
		root.getChildren().add(train);

		createComboBoxes(root);

		run = new Button("Netzwerkdurchlauf starten!");
		run.setFont(new Font(16));
		// run.setDisable(true);
		run.setOnAction((e) -> computeInput());
		run.setLayoutX(20);
		run.setLayoutY(320);
		root.getChildren().add(run);

		Label reaction = new Label("Aktion: ");
		reaction.setFont(new Font(16));
		reaction.setLayoutX(360);
		reaction.setLayoutY(140);
		react = new Label("Nichtstun");
		react.setFont(new Font(16));
		react.setLayoutX(360);
		react.setLayoutY(180);
		root.getChildren().addAll(reaction, react);

	}

	private void createComboBoxes(Pane root) {
		ArrayList<Integer> numbers = new ArrayList<>();
		for (int i = 0; i <= 10; i++) {
			numbers.add(i);
		}

		Label l1 = new Label("Gesundheit: ");
		l1.setFont(new Font(16));
		b1 = new ComboBox<>();
		b1.setPromptText("0");
		b1.setValue(0);
		b1.getItems().addAll(numbers);
		l1.setLayoutX(20);
		l1.setLayoutY(140);
		b1.setLayoutX(180);
		b1.setLayoutY(140);
		// b1.setDisable(true);

		Label l2 = new Label("Munition: ");
		l2.setFont(new Font(16));
		b2 = new ComboBox<>();
		b2.setPromptText("0");
		b2.setValue(0);
		b2.getItems().addAll(numbers);
		l2.setLayoutX(20);
		l2.setLayoutY(180);
		b2.setLayoutX(180);
		b2.setLayoutY(180);
		// b2.setDisable(true);

		Label l3 = new Label("Anzahl Gegner: ");
		l3.setFont(new Font(16));
		b3 = new ComboBox<>();
		b3.setPromptText("0");
		b3.setValue(0);
		b3.getItems().addAll(numbers);
		l3.setLayoutX(20);
		l3.setLayoutY(220);
		b3.setLayoutX(180);
		b3.setLayoutY(220);
		// b3.setDisable(true);

		Label l4 = new Label("Anzahl VerbŁndete: ");
		l4.setFont(new Font(16));
		b4 = new ComboBox<>();
		b4.setPromptText("0");
		b4.setValue(0);
		b4.getItems().addAll(numbers);
		l4.setLayoutX(20);
		l4.setLayoutY(260);
		b4.setLayoutX(180);
		b4.setLayoutY(260);
		// b4.setDisable(true);

		root.getChildren().addAll(l1, l2, l3, l4);
		root.getChildren().addAll(b1, b2, b3, b4);

	}

	// -------------------------------------------Network

	private void setupNetwork() {
		examples = createPatterns();
		int[] structure = { 16, 6, 4 };
		net = new Network(structure, 0.3, 0.0001, 7000);

	}

	private void computeInput() {

		String v1 = Integer.toBinaryString(b1.getValue());
		String v2 = Integer.toBinaryString(b2.getValue());
		String v3 = Integer.toBinaryString(b3.getValue());
		String v4 = Integer.toBinaryString(b4.getValue());

		int[] x = new int[16];
		int offset = 0;
		adjustString(x, offset, v1);
		offset += 4;
		adjustString(x, offset, v2);
		offset += 4;
		adjustString(x, offset, v3);
		offset += 4;
		adjustString(x, offset, v4);

		Pattern input = new Pattern(x, null);
		net.run(input);

	}

	private void adjustString(int[] x, int offset, String v1) {

		int index = offset + (4 - v1.length());
		for (int i = 0; i < v1.length(); i++) {
			if (v1.charAt(i) == '1') {
				x[index] = 1;
				index++;
			} else {
				x[index] = 0;
				index++;
			}

		}

	}

	private ArrayList<Pattern> createPatterns() {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

}
