package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
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
	private boolean start;
	
	/**
	 * Construtor da classe GameBoard
	 */
	public GameView(int height, int width,GameController controller) {
		this.width = width;
		this.height = height;
		this.controller = controller;
		cells = new ArrayList<Cell>();
		controller.setRule(dropDownList());
		GraphicUserInterface();
	}
	
	public List<Cell> getCells() {
		return cells;
	}
	
	public void setCells(List<Cell> cells) {
		this.cells = cells;
	}
	
	public void update() {
		grid.repaint();
	}
	

	public void GraphicUserInterface() {

		javax.swing.JFrame jframe = new javax.swing.JFrame("Game of Life");
		jframe.setBackground(Color.black);
		jframe.setSize(600, 600);
		jframe.setResizable(false);
		jframe.setVisible(true);
		jframe.setLocationRelativeTo(null);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.controls = createButtonsMenu(jframe);
		grid = new GridView(height, width, controller);
		grid.setPreferredSize(new Dimension(jframe.getSize().width,jframe.getSize().height - 50));
		grid.setLayout(new GridLayout(height, width));
		setCells(grid.getCells());
		jframe.add(controls,  BorderLayout.SOUTH);
		jframe.add(grid,  BorderLayout.NORTH);
		jframe.pack();
		System.out.println(jframe.getSize().height);

	}
	
	public int dropDownList(){
		int rule = 0;
		String[] choices = { "", "Conway", "Maze", "DayNight", "WalledCities", "Gnarl",
				"Square", "Dots", "Experimental" };
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
	
	
	private javax.swing.JPanel createButtonsMenu(javax.swing.JFrame window) {
		javax.swing.JButton startButton = new javax.swing.JButton("Start");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				start= !start;
				if(start){
					startButton.setText("Pause");
					controller.start();
				}
				else{
					startButton.setText("Start");
					controller.stop();
				}
			}
		});
		
		javax.swing.JButton undoButton = new javax.swing.JButton("Undo");
		undoButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a){
				controller.undo();
			}
		});
		
		javax.swing.JButton haltButton = new javax.swing.JButton("Halt");
		haltButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				controller.halt();
			}
		});
	
		javax.swing.JPanel controls = new javax.swing.JPanel();
		controls.setBackground(Color.black);
		controls.setPreferredSize(new Dimension(600, 50));
		controls.add(startButton);
		controls.add(undoButton);
		controls.add(haltButton);
		
		return controls;
	}
	

}


