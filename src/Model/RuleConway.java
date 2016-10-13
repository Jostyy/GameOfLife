package Model;

import java.util.List;

import View.Statistics;


public class RuleConway extends GameEngine{

	public RuleConway(int height, int width, Statistics statistics,
			List<Cell> listCells) {
		super(height, width, statistics, listCells);
	}
	
	public boolean shouldKeepAlive(int i, int j){
		int aliveNeighbors = numberOfNeighborhoodAliveCells(i, j);
		
		return (getListCellsItem(i, j).isAlive()) && (aliveNeighbors == 2 || aliveNeighbors == 3);
	}
	
	public boolean shouldRevive(int i, int j) {
		int aliveNeighbors = numberOfNeighborhoodAliveCells(i, j);
		return (!getListCellsItem(i, j).isAlive()) && (aliveNeighbors == 3);
	}
	
}