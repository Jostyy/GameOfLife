package Model;

import java.util.ArrayList;
import java.util.List;
import java.security.InvalidParameterException;

import View.Statistics;

/**
 * Representa um ambiente (environment) do jogo GameOfLife.
 * 
 * Essa implementacao eh nao inifinita, ou seja, nem todas as celulas possuem
 * oito celulas vizinhas. Por exemplo, a celula de coordenada (0,0) possui
 * apenas tres celulas vizinhas, (0,1), (1,0) e (1,1).
 * 
 * Um ambiente eh representado como um array bidimensional de celulas, com
 * altura (height) e comprimento (width).
 * 
 * @author rbonifacio
 */
public abstract class GameEngine {
	private int height;
	private int width;
	private List<Cell> listCells;
	private Statistics statistics;
	private Memento memento;

	/**
	 * Construtor da classe Environment.
	 * 
	 * @param height
	 *            dimensao vertical do ambiente
	 * @param width
	 *            dimentsao horizontal do ambiente
	 */
	public GameEngine(int height, int width, Statistics statistics, List<Cell> listCells) {
		this.height = height;
		this.width = width;
		this.listCells = listCells;
		this.statistics = statistics;
		memento = new Memento();
	}
	
	/**
	 * Calcula uma nova geracao do ambiente. Essa implementacao utiliza o
	 * algoritmo do Conway, ou seja:
	 * 
	 * a) uma celula morta com exatamente tres celulas vizinhas vivas se torna
	 * uma celula viva.
	 * 
	 * b) uma celula viva com duas ou tres celulas vizinhas vivas permanece
	 * viva.
	 * 
	 * c) em todos os outros casos a celula morre ou continua morta.
	 */
	public void nextGeneration() {
		List<Cell> mustRevive = new ArrayList<Cell>();
		List<Cell> mustKill = new ArrayList<Cell>();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (shouldRevive(i, j)) {
					mustRevive.add(getListCellsItem(i, j));
				} 
				else if ((!shouldKeepAlive(i, j)) && getListCellsItem(i, j).isAlive()) {
					mustKill.add(getListCellsItem(i, j));
				}
			}
		}
		
		
		for (Cell cell : mustRevive) {
			cell.revive();
			statistics.recordRevive();
		}
		
		for (Cell cell : mustKill) {
			cell.kill();
			statistics.recordKill();
		}
		
		memento.storeCells(listCells);
		
	}
	
	
	/**
	 * verifica se existe alguma celula viva
	 * 
	 * @return Verdadeiro se tiver alguma celula viva e falso caso contrario
	 */
	public boolean isAnyCellAlive() {
		for (Cell cell : listCells) {
	    	if(cell.isAlive()) {
	    		return true;
	    	}
	    }
		return false;
	}
	
	/**
	 * Verifica se uma celula na posicao (i, j) estah viva.
	 * 
	 * @param i Posicao vertical da celula
	 * @param j Posicao horizontal da celula
	 * @return Verdadeiro caso a celula de posicao (i,j) esteja viva.
	 * 
	 * @throws InvalidParameterException caso a posicao (i,j) nao seja valida. 
	 */
	public boolean isCellAlive(int i, int j) throws InvalidParameterException {
		if(validPosition(i, j)) {
			return getListCellsItem(i, j).isAlive();
		}
		else {
			throw new InvalidParameterException("Invalid position (" + i + ", " + j + ")" );
		}
	}
	
	/**
	 * Retorna o numero de celulas vivas no ambiente. 
	 * Esse metodo eh particularmente util para o calculo de 
	 * estatisticas e para melhorar a testabilidade.
	 * 
	 * @return  numero de celulas vivas.
	 */
	public int numberOfAliveCells() {
		int aliveCells = 0;
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				if(isCellAlive(i,j)) {
					aliveCells++;
				}
			}
		}
		return aliveCells;
	}
	
	/**
	 * Verifica se uma posicao (a, b) referencia uma celula valida no tabuleiro. 
	 *  
	 * @return  Verdadeiro caso a posicao seja valida e falso caso contrario.
	 */
	public boolean validPosition(int a, int b) {
		return a >= 0 && a < height && b >= 0 && b < width;
	}
	
	public void undo(){
		listCells = memento.restoreCells();
	}

	public void halt() {
		statistics.display();
		System.exit(0);
	}
	
	/**
	 * Apos clicar em uma determinada celula no grid esse metodo fara com que ela se
	 * torne viva caso esteja morta ou morra caso esteja viva. 
	 * 
	 * @param i posicao vertical da celula clicada
	 * @param j posicao horizontal da celula clicada
	 * 
	 * 
	 */
	public void clickedCell(int i, int j) {
		Cell cell = getListCellsItem(i, j);
		
		if(cell.isAlive()) {
			killCell(i, j);
		} else {
			makeCellAlive(i, j);
		}
	}
	
	/**
	 * Torna a celula de posicao (i, j) viva
	 * 
	 * @param i posicao vertical da celula
	 * @param j posicao horizontal da celula
	 * 
	 * @throws InvalidParameterException caso a posicao (i, j) nao seja valida.
	 */
	private void makeCellAlive(int i, int j) throws InvalidParameterException {
		if(validPosition(i, j)) {
			getListCellsItem(i, j).revive();
			statistics.recordRevive();
		}
		else {
			new InvalidParameterException("Invalid position (" + i + ", " + j + ")" );
		}
	}
	
	/**
	 * Torna a celula de posicao (i, j) morta
	 * 
	 * @param i posicao vertical da celula
	 * @param j posicao horizontal da celula
	 * 
	 * @throws InvalidParameterException caso a posicao (i, j) nao seja valida.
	 */
	private void killCell(int i, int j) throws InvalidParameterException {
		if(validPosition(i, j)) {
			getListCellsItem(i, j).kill();
			statistics.recordKill();
		}
		else {
			new InvalidParameterException("Invalid position (" + i + ", " + j + ")" );
		}
	}
	
	protected Cell getListCellsItem(int i, int j) {
		 return this.listCells.get(i * this.width + j);
	}
	
	
	protected abstract boolean shouldKeepAlive(int i, int j);
	
	protected abstract boolean shouldRevive(int i, int j);
	
	protected int numberOfNeighborhoodAliveCells(int i, int j) {
		int alive = 0;
		for (int a = i - 1; a <= i + 1; a++) {
			for (int b = j - 1; b <= j + 1; b++) {
				List<Integer> infiniteWorld = infiniteWorld(a, b);
				int a1 = infiniteWorld.get(0);
				int b1 = infiniteWorld.get(1);
				
				if (validPosition(a1, b1)  && (!(a1==i && b1 == j)) && getListCellsItem(a1, b1).isAlive()) {
					alive++;
				}
			}
		}
		return alive;
	}
	
	private List<Integer> infiniteWorld(int i, int j) {
	
		if(i == -1) {
			i = width - 1;
		} 
		else if(i == width) {
			i = 0;
		}
		if(j == -1) {
			j = height - 1;
		}
		else if(j == height) {
			j = 0;
		}
		
		List<Integer> returnIJ = new ArrayList<Integer>();
		returnIJ.add(i);
		returnIJ.add(j);
		
		return returnIJ;
	}
	


}
