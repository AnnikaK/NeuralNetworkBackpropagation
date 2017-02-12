import javafx.application.Platform;

public class CheckThread extends Thread {

	private Fighter fighter;
	private Window window;

	public CheckThread(Fighter fighter, Window window) {
		this.fighter = fighter;
		this.window = window;
		setDaemon(true);
		start();
	}

	@Override
	public void run() {

		int falses = fighter.checkForCorrectness();
		
		int total = fighter.getNrOfSamples();
		
		double percent =  100 - (((double) falses/total)*100);
		
		Platform.runLater(()-> window.setCheckDisable(true));
		 
		
		Platform.runLater(()->window.setErfolgsrate(Math.round(percent) + "%"));

	}

}
