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



public class GridView extends JPanel{

    private int columnCount;
    private int rowCount;
    private List<Cell> cells;
    private Point selectedCell;
    private GameController controller;

    public GridView(final int rowCount, final int columnCount, GameController controller) {
    	this.rowCount = rowCount;
    	this.columnCount = columnCount;
    	this.controller = controller;
    	
        cells = new ArrayList<Cell>(columnCount * rowCount);
        
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {

                 	  int width = getWidth();
                      int height = getHeight();

                      int column = columnCount * evt.getX() / width;
                      int row = rowCount * evt.getY() / height;

                      selectedCell = new Point(column, row);
                      
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

                 int column = columnCount * evt.getX() / width;
                 int row = rowCount * evt.getY() / height;

                 selectedCell = new Point(column, row);
                 
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

       int column = columnCount * evt.getX() / width;
       int row = rowCount * evt.getY() / height;

       selectedCell = new Point(column, row);
       
       clickCell(selectedCell);
       
       repaint();
    	
    }
    
    
    private void clickCell(Point selectedCell) {
		int column = (int) selectedCell.getX();
		int row = (int) selectedCell.getY();
		
		controller.clickedCell(row, column);
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
        int cellDim = width / columnCount;
        int x = (width - (columnCount * cellDim)) / 2;
        int y = (height - (rowCount * cellDim)) / 2;

        if (cells.isEmpty()) {
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < columnCount; j++) {
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