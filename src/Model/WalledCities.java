package Model;

import java.util.List;

import View.Statistics;

public class WalledCities extends GameEngine{

	public WalledCities(int height, int width, Statistics statistics,
			List<Cell> listCells) {
		super(height, width, statistics, listCells);
	}
	
	public boolean shouldKeepAlive(int i, int j){
		int aliveNeighbors = numberOfNeighborhoodAliveCells(i, j);
		return (getListCellsItem(i, j).isAlive())
				&& (aliveNeighbors == 2 || aliveNeighbors == 3 || 
				aliveNeighbors == 4 || aliveNeighbors == 5);
	}
	
	public boolean shouldRevive(int i, int j) {
		int aliveNeighbors = numberOfNeighborhoodAliveCells(i, j);
		return (!getListCellsItem(i, j).isAlive())
				&& (aliveNeighbors == 4 || aliveNeighbors == 5 || aliveNeighbors == 6 ||
						aliveNeighbors == 7 || aliveNeighbors == 8);
	}
	
}
