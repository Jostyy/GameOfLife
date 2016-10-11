package Controller;



import java.util.Timer;
import java.util.TimerTask;

import Model.GameEngine;
import View.GameView;
import View.Statistics;

public class GameController {

	private GameEngine engine;
	private GameView board;
	private Statistics statistics;
	private Timer task;
	private boolean TaskActive = false; 
	private int rule;
	
	public GameEngine getEngine() {
		return engine;
	}
	
	public void setEngine(GameEngine engine) {
		this.engine = engine;
	}
	
	public GameView getBoard() {
		return board;
	}
	
	public void setBoard(GameView board) {
		this.board = board;
	}
	
	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}
	
	public void setRule(int rule){
		this.rule = rule;
	}
	
	public int getRule(){
		return rule;
	}
	
	public void start() {
		if(TaskActive == false){
			TaskActive = true;
			task = new Timer();
			task.scheduleAtFixedRate(new TimerTask() {
			    @Override
			    public void run() {
			    	engine.nextGeneration();
			    	board.update();
			    	if(engine.isAnyCellAlive() == false) {
			    		task.cancel();
			    	}
			    }
			}, 0, 100);
		}
	}
	public void halt() {
		task.cancel();
		engine.halt();
	}
	
	public void clickedCell(int i, int j) {
		engine.clickedCell(i, j);
		board.update();
	}
	
	
	public void nextGeneration() {
		engine.nextGeneration();
		board.update();
		
	}
	
}
