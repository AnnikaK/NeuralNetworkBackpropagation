
public class Connection {

	private double weight;
	private Neuron left;
	private Neuron right;
	private boolean isBias;

	public Connection(Neuron l, Neuron r, boolean isBias) {
		this.left = l;
		this.right = r;

		// init weight randomly
		if (Math.random() > 0.5) {
			weight = Math.random() % 0.5;
		} else {
			weight = Math.random() % 0.5;
		}

		this.isBias = isBias;
	}

	public Neuron getRightNeuron() {
		return right;
	}

	public Neuron getLeftNeuron() {
		return left;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double w) {
		weight = w;

	}

	public boolean isBias() {
		return isBias;
	}

}
