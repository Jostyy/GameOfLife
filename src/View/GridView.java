package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Controller.GameController;
import Model.Cell;



public class GridView extends javax.swing.JPanel {

    private int col;
    private int row;
    private List<Cell> cells;
    private Point selectedCell;
    private GameController controller;

    public GridView(final int row, final int col, GameController controller) {
    	this.row = row;
    	this.col = col;
    	this.controller = controller;
        cells = new ArrayList<Cell>(col * row);
        
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {

                 	  int width = getWidth();
                      int height = getHeight();
                      int i = col * evt.getX() / width;
                      int j = row * evt.getY() / height;
                      
                      selectedCell = new Point(i, j);
                      clickCell(selectedCell);
                      repaint();
                 
            }
        });
        
        addMouseListener(new java.awt.event.MouseAdapter() {
        	
            @Override
            public void mouseReleased(MouseEvent evt) {}
            @Override
            public void mousePressed(MouseEvent evt){
            	 int width = getWidth();
                 int height = getHeight();
                 int i = col * evt.getX() / width;
                 int j = row * evt.getY() / height;

                 selectedCell = new Point(i, j);
                 clickCell(selectedCell);
                 repaint();
            }

			@Override
            public void mouseExited(MouseEvent evt) {}
            @Override
            public void mouseEntered(MouseEvent evt) {}
            @Override
            public void mouseClicked(MouseEvent evt) {}
        });
    } 
    
   public void mousePressed(MouseEvent evt){
	   int width = getWidth();
       int height = getHeight();
       int i = col * evt.getX() / width;
       int j = row * evt.getY() / height;

       selectedCell = new Point(i, j);
       clickCell(selectedCell);
       repaint();
    	
    }
    
    
    private void clickCell(Point selectedCell) {
		
		int i = (int) selectedCell.getY();
		int j = (int) selectedCell.getX();
		
		controller.clickedCell(i, j);
	}


    @Override
    public void invalidate() {
        cells.clear();
        selectedCell = null;
        super.invalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        int width = getWidth();
        int height = getHeight();
        int cellDim = width / col;
        int x = (width - (col * cellDim)) / 2;
        int y = (height - (row * cellDim)) / 2;

        if (cells.isEmpty()) {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    Cell cell = new Cell(
                            x + (j * cellDim),
                            y + (i * cellDim),
                            cellDim,
                            cellDim);
                    cells.add(cell);
                }
            }
        }

        for (Cell cell : cells) {
        	if(cell.isAlive()) {
                g2d.setColor(Color.black);
                g2d.fill(cell);
        	}
        }        

        g2d.setColor(Color.red);
        for (Rectangle cell : cells) {
            g2d.draw(cell);
        }

        g2d.dispose();
    }
    
    public List<Cell> getCells() {
    	return cells;
    }
    public void setCells(List<Cell> cells){
    	this.cells = cells;
    }
 

}