import java.util.ArrayList;

public class Network {
	private ArrayList<ArrayList<Neuron>> layers;
	private int outputLayerSize;
	private int inputLayerSize;
	private int nrOfLayers;

	private double[] errors;
	private double minError;
	private double networkError;
	private double learningRate;

	private long minIterations;
	private long nrOfIterations;

	public Network(int[] structure) {

		// default values
		minError = 0;
		learningRate = 0.1;
		minIterations = 5000;
		networkError = Double.MAX_VALUE;

		nrOfIterations = 0;
		nrOfLayers = structure.length;
		errors = new double[structure[structure.length - 1]];

		layers = new ArrayList<>();

		setupNetwork(nrOfLayers, structure);

	}

	public Network(int[] structure, double learningRate, double minError, long minIterations) {
		this.minError = minError;
		this.learningRate = learningRate;
		this.minIterations = minIterations;
		networkError = Double.MAX_VALUE;

		nrOfIterations = 0;
		nrOfLayers = structure.length;
		errors = new double[structure[structure.length - 1]];

		layers = new ArrayList<>();

		setupNetwork(nrOfLayers, structure);

	}

	private void setupNetwork(int nr, int[] struct) {

		for (int i = 0; i < nr; i++) {
			ArrayList<Neuron> layer = new ArrayList<>();
			for (int n = 0; n < struct[i]; n++) {
				layer.add(new Neuron(n + 1));
			}
			layers.add(layer);
		}

		inputLayerSize = struct[0];
		outputLayerSize = struct[struct.length - 1];

		createConnections();

	}

	private void createConnections() {

		for (int i = 0; i < nrOfLayers; i++) {

			ArrayList<Neuron> layer = layers.get(i);

			if (i != 0) {
				Neuron bias = new Neuron(0);
				for (Neuron r : layer) {
					ArrayList<Connection> inConn = new ArrayList<>();
					r.setInputConnections(inConn);
					r.addBiasConnection(new Connection(bias, r, true));

					ArrayList<Neuron> layerBelow = layers.get(i - 1);
					for (Neuron l : layerBelow) {
						inConn.add(new Connection(l, r, false));
					}

				}
			}

		}

	}

	public void run(ArrayList<Pattern> patterns) {

		for (Pattern p : patterns) {
			int[] net_input = p.getX();

			printInput(net_input);

			// init input layer
			ArrayList<Neuron> inputLayer = getInputLayer();
			for (int i = 0; i < net_input.length; i++) {
				Neuron n = inputLayer.get(i);
				n.setActivation(net_input[i]);
			}

			// run forwards
			for (int l = 1; l < nrOfLayers; l++) {
				ArrayList<Neuron> current_layer = getLayer(l);
				for (Neuron n : current_layer) {
					n.inputFunction();
					n.activationFunction();
				}
			}
			printResult();

		}

	}

	public void run(Pattern p) {

		int[] net_input = p.getX();

		printInput(net_input);

		// init input layer
		ArrayList<Neuron> inputLayer = getInputLayer();
		for (int i = 0; i < net_input.length; i++) {
			Neuron n = inputLayer.get(i);
			n.setActivation(net_input[i]);
		}

		// run forwards
		for (int l = 1; l < nrOfLayers; l++) {
			ArrayList<Neuron> current_layer = getLayer(l);
			for (Neuron n : current_layer) {
				n.inputFunction();
				n.activationFunction();
			}
		}
		printResult();

	}

	public void backpropagation_learning(ArrayList<Pattern> examples) {

		while (!stop_criteria()) {
			nrOfIterations++;

			for (Pattern pattern : examples) {
				int[] target = pattern.getY();
				if (target.length != outputLayerSize) {
					throw new IllegalArgumentException("Pattern does not match network output!");
				}
				int[] net_input = pattern.getX();
				if (net_input.length != inputLayerSize) {
					throw new IllegalArgumentException("Pattern does not match network input!");
				}

				printInput(net_input);

				// init input layer
				ArrayList<Neuron> inputLayer = getInputLayer();
				for (int i = 0; i < net_input.length; i++) {
					Neuron n = inputLayer.get(i);
					n.setActivation(net_input[i]);
				}

				// run forwards
				for (int l = 1; l < nrOfLayers; l++) {
					ArrayList<Neuron> current_layer = getLayer(l);
					for (Neuron n : current_layer) {
						n.inputFunction();
						n.activationFunction();
					}
				}

				// error calculation in output layer
				ArrayList<Neuron> outputLayer = getOutputLayer();
				for (int i = 0; i < target.length; i++) {
					Neuron n = outputLayer.get(i);
					double error = n.activationFunctionDerivate() * (target[i] - n.getActivation());

					n.setError(error);
					errors[i] = error;

					// update weights
					for (Connection con : n.getInputConnections()) {
						Neuron left = con.getLeftNeuron();
						double delta = learningRate * left.getActivation() * n.getError();
						con.setWeight(con.getWeight() + delta);
					}

				}

				// error calculation for hidden layers
				for (int l = nrOfLayers - 2; l > 0; l--) {
					ArrayList<Neuron> layer = getLayer(l);
					for (Neuron n : layer) {
						ArrayList<Neuron> layerAbove = getLayer(l + 1);
						double total = 0;
						for (Neuron k : layerAbove) {
							ArrayList<Connection> conns = k.getInputConnections();
							Connection con = conns.get(n.getNrInLayer());

							total += k.getError() * con.getWeight();

						}

						total *= n.activationFunctionDerivate();
						n.setError(total);

						// update weigths
						ArrayList<Connection> inConn = n.getInputConnections();
						for (Connection con : inConn) {
							Neuron left = con.getLeftNeuron();
							double delta = learningRate * left.getActivation() * n.getError();
							con.setWeight(con.getWeight() + delta);
						}

					}

				}

				printResult();
			}
			printError();

		}

	}

	private ArrayList<Neuron> getOutputLayer() {
		return layers.get(nrOfLayers - 1);
	}

	private ArrayList<Neuron> getLayer(int l) {
		return layers.get(l);
	}

	private ArrayList<Neuron> getInputLayer() {
		return layers.get(0);
	}

	private boolean stop_criteria() {
		if (nrOfIterations < minIterations) {
			networkErrorTolerable();
			return false;
		} else {
			return networkErrorTolerable();
		}

	}

	// squared error
	private boolean networkErrorTolerable() {
		double total = 0;
		for (int i = 0; i < errors.length; i++) {
			total += Math.pow(errors[i], 2);
		}
		networkError = 0.5 * total;
		return networkError < minError;

	}

	public void setMinInterations(long min) {
		this.minIterations = min;
	}

	public void setLearningRate(double alpha) {
		if (alpha > 0 && alpha < 1) {
			learningRate = alpha;
		}
	}

	public void setMinError(double minErr) {
		minError = minErr;
	}

	private void printResult() {

		System.out.println("Ausgabe:");
		ArrayList<Neuron> outputLayer = getOutputLayer();
		for (Neuron n : outputLayer) {
			System.out.println(n.getActivation());
		}
		System.out.println();

	}

	private void printError() {
		System.out.println("NetworkError: " + networkError);
		System.out.println();
	}

	private void printInput(int[] input) {
		System.out.println("Eingabe: ");
		for (int i = 0; i < input.length; i++) {
			System.out.println(input[i]);
		}
		System.out.println();
	}

}
