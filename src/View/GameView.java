package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Controller.GameController;
import Model.Cell;

/**
 * Atua como um componente de apresentacao (view), exibindo o estado atual do
 * game com uma implementacao baseada em caracteres ASCII.
 * 
 * @author rbonifacio
 */
public class GameView {

	private int width;
	private int height;
	
	private GameController controller;
	private JPanel controls;
	private GridView grid;
	private List<Cell> cells;
	
	/**
	 * Construtor da classe GameBoard
	 */
	public GameView(int height, int width,GameController controller) {
		this.width = width;
		this.height = height;
		this.controller = controller;
		cells = new ArrayList<Cell>();
		controller.setRule(dropDownList());
		initGUI();
	}
	
	public List<Cell> getCells() {
		return cells;
	}
	
	public void update() {
		grid.repaint();
	}
	
	private void setCells(List<Cell> cells) {
		this.cells = cells;
	}

	public void initGUI() {

		JFrame window = new JFrame("Game of Life");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBackground(Color.black);
		window.setSize(500, 500);
		window.setVisible(true);
		window.setLocationRelativeTo(null);
		this.controls = createButtonsMenu(window);
		this.grid = createGrid(window, controls);
		window.add(controls,  BorderLayout.SOUTH);
		window.add(grid,  BorderLayout.NORTH);
		window.pack();

	}
	
	public int dropDownList(){
		int rule = 0;
		String[] choices = { "", "Conway", "Maze", "DayNight", "WalledCities" };
		String choice = (String) JOptionPane.showInputDialog(null, "Choose the Rules",
		        "Rules of GameOfLife", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
		if((choice != null)&&(choice.length()>0)){
			for(int i = 0; i < choices.length; i++){
				if(choices[i].equals(choice)){
					return i;
				}
			}
		}
		else{
			System.exit(0);
		}
		return rule;
		
	}
	
	
	private JPanel createButtonsMenu(JFrame window) {
		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				controller.start();

			}
		});
		
		JButton nextGeneration = new JButton("Next");
		nextGeneration.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				controller.nextGeneration();
			}
		});
		
		JButton haltButton = new JButton("Halt");
		haltButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				controller.halt();
			}
		});
		
		JPanel controls = new JPanel();
		controls.setBackground(Color.black);
		controls.setPreferredSize(new Dimension(window.getSize().width, 50));
		controls.add(startButton);
		controls.add(nextGeneration);
		controls.add(haltButton);
		
		return controls;
	}
	
	private GridView createGrid(JFrame window, JPanel controls) {
		
		GridView grid = new GridView(height, width, controller);
		grid.setPreferredSize(new Dimension(window.getSize().width, 
											window.getSize().height - 40));
		grid.setLayout(new GridLayout(height, width));
		setCells(grid.getCells());
		return grid;
		
	}

}


