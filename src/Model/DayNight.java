package Model;

import java.util.List;

import View.Statistics;

public class DayNight extends GameEngine{

	public DayNight(int height, int width, Statistics statistics,
			List<Cell> listCells) {
		super(height, width, statistics, listCells);
	}
	
	public boolean shouldKeepAlive(int i, int j){
		int aliveNeighbors = numberOfNeighborhoodAliveCells(i, j);
		return (getListCellsItem(i, j).isAlive())
				&& (aliveNeighbors == 3 || aliveNeighbors == 4 || aliveNeighbors == 6 || 
				aliveNeighbors == 7 || aliveNeighbors == 8);
	}
	
	public boolean shouldRevive(int i, int j) {
		int aliveNeighbors = numberOfNeighborhoodAliveCells(i, j);
		return (!getListCellsItem(i, j).isAlive())
				&& (aliveNeighbors == 3 || aliveNeighbors == 6 || aliveNeighbors == 7 ||
				aliveNeighbors == 8);
	}
	
}
