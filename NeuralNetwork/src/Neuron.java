import java.util.ArrayList;

public class Neuron {

	private double activation;
	private double input;
	private double error;
	private int nrInLayer;

	private ArrayList<Connection> inputConnections;
	private ArrayList<Connection> outputConnections;

	public Neuron(int nr) {
		this.input = 0;
		this.activation = 0;
		this.error = 0;
		this.nrInLayer = nr;
	}

	public void setActivation(double a) {
		activation = a;

	}

	public double getActivation() {
		return activation;
	}

	public void inputFunction() {
		double in = 0;
		for (Connection c : inputConnections) {
			if (c.isBias()) {
				in += (-1 * c.getWeight());
			} else {
				in += c.getLeftNeuron().getActivation() * c.getWeight();
			}
		}
		input = in;

	}

	public double activationFunction() {
		activation = 1 / (1 + Math.exp(-input));
		return activation;
	}

	public void setError(double e) {
		error = e;

	}

	public double activationFunctionDerivate() {

		return activation * (1 - activation);
	}

	public double getError() {
		return error;
	}

	public void addBiasConnection(Connection bias) {
		inputConnections.add(bias);
	}

	public ArrayList<Connection> getOutputConnections() {
		return outputConnections;
	}

	public void setOutputConnections(ArrayList<Connection> o) {
		outputConnections = o;
	}

	public ArrayList<Connection> getInputConnections() {
		return inputConnections;
	}
	
	public StringBuffer InputConnectionsToStringBuffer(){
		StringBuffer str = new StringBuffer();
		for(int i = 0; i < inputConnections.size(); i++)
		{
			str.append("Gewicht Nr." + i + ": " + inputConnections.get(i).getWeight());
			str.append(System.getProperty("line.separator"));
		}
		return str;
	}

	public void setInputConnections(ArrayList<Connection> i) {
		this.inputConnections = i;
	}

	public int getNrInLayer() {
		return nrInLayer;
	}

	public void setNrInLayer(int nrInLayer) {
		this.nrInLayer = nrInLayer;
	}

}
