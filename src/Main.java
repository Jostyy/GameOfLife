import Controller.GameController;
import Model.Conway;
import Model.DayNight;
import Model.GameEngine;
import Model.Maze;
import Model.WalledCities;
import View.GameView;
import View.Statistics;

public class Main {

	private static int HEIGHT = 200;
	private static int WIDTH = 200;
	private static GameEngine engine;
	
	public static void main(String args[]) {
		GameController controller = new GameController();
		
		GameView board = new GameView(HEIGHT, WIDTH, controller);
		
		Statistics statistics = new Statistics();
		switch(controller.getRule()){
		case 1 :
			engine = new Conway(HEIGHT, WIDTH, statistics, board.getCells());	
			break;
		case 2 :
			engine = new Maze(HEIGHT, WIDTH, statistics, board.getCells());	
		case 3 :
			engine = new DayNight(HEIGHT, WIDTH, statistics, board.getCells());
		case 4 : 
			engine = new WalledCities(HEIGHT, WIDTH, statistics, board.getCells());
		}	
		controller.setBoard(board);
		controller.setEngine(engine);
		controller.setStatistics(statistics);
		
	}
}
