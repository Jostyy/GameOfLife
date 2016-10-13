import Controller.GameController;
import Model.RuleConway;
import Model.RuleDayNight;
import Model.RuleGnarl;
import Model.GameEngine;
import Model.RuleMaze;
import Model.RuleWalledCities;
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
			engine = new RuleConway(HEIGHT, WIDTH, statistics, board.getCells());	
			break;
		case 2 :
			engine = new RuleMaze(HEIGHT, WIDTH, statistics, board.getCells());
			break;
		case 3 :
			engine = new RuleDayNight(HEIGHT, WIDTH, statistics, board.getCells());
			break;
		case 4 : 
			engine = new RuleWalledCities(HEIGHT, WIDTH, statistics, board.getCells());
			break;
		case 5 :
			engine = new RuleGnarl(HEIGHT, WIDTH, statistics, board.getCells());
			break;
		}	
		controller.setBoard(board);
		controller.setEngine(engine);
		controller.setStatistics(statistics);
		
	}
}
