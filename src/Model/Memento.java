package Model;

import java.util.LinkedList;
import java.util.List;

import Model.Cell;

public class Memento {

	LinkedList<List<Cell>> cellsList;
	private int n = 10;
	
	public Memento(){
		cellsList = new LinkedList<List<Cell>>();
	}
	public void storeCells(List<Cell> cells){
		if(cellsList.size() > n){
			cellsList.removeFirst();
		}
		cellsList.add(cells);
	}
	public List<Cell> restoreCells(){
		return cellsList.removeFirst();
	}
	/*
	public void storeCell(List<Cell> cellsList){
		this.cellsList = cellsList;
	}
	
	public List<Cell> restoreCell(){
		return this.cellsList;
	}
	*/
}

