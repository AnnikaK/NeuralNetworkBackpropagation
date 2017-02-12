import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class WriteFileThread extends Thread {

	private Network net;

	public WriteFileThread(Network net) {
		this.net = net;
		setDaemon(true);
		start();
	}

	@Override
	public void run() {

		File txt = new File("networkWeights.txt");


		ArrayList<StringBuffer> weights = net.getWeights();

		try {
			
			
			if(txt.exists())
			{
				FileWriter writer = new FileWriter("networkWeights.txt", true); //append

				writer.write("---------------------------------------------------------------------");
				writer.write(System.getProperty("line.separator"));
				for (int i = 0; i < weights.size(); i++) {
					writer.write("GEWICHTE F�R SCHICHT " + i + ":");
					writer.write(System.getProperty("line.separator"));
					writer.write(System.getProperty("line.separator"));

					writer.write(weights.get(i).toString());
					writer.write(System.getProperty("line.separator"));
				}
				writer.write("---------------------------------------------------------------------");
				writer.write(System.getProperty("line.separator"));
				writer.write(System.getProperty("line.separator"));
				writer.flush();
				writer.close();
			}
			else {
				FileWriter writer = new FileWriter("networkWeights.txt"); //create new

				writer.write("-------------------------------");
				writer.write(System.getProperty("line.separator"));
				for (int i = 0; i < weights.size(); i++) {
					writer.write("GEWICHTE F�R SCHICHT " + i + ":");
					writer.write(System.getProperty("line.separator"));
					writer.write(System.getProperty("line.separator"));

					writer.write(weights.get(i).toString());
					writer.write(System.getProperty("line.separator"));
				}
				writer.write("-------------------------------");
				writer.write(System.getProperty("line.separator"));
				writer.write(System.getProperty("line.separator"));
				writer.flush();
				writer.close();
				
			}
			
			

			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
