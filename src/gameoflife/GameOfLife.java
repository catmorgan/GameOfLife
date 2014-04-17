package gameoflife;
import acm.program.*;
import acm.graphics.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


public class GameOfLife extends GraphicsProgram {
    
  private static final int WORLD_SIZE = 50;
  private static final int CELL_SIZE = 16;
  private static final int CELL_PADDING = 2;
  private static final int SPARSITY = 5;
  private static final int OFFSET = 50;
  private static final int DELAY = 64;
  private static final Color COLOR0 = Color.GRAY;
  private static final Color COLOR1 = Color.MAGENTA;
  private static final Color COLOR2 = Color.GREEN;
  private static final Color COLOR3 = Color.BLUE;
  private static final Color COLOR4 = Color.YELLOW;  
  private static GRect background;
  private static GOval[][] world;
  private static boolean[][] alive;
  private static boolean running = false;
  private static boolean exit = false;

    public void run() {
        addMouseListeners();
        setUpButtons();
        setUpBackground();
        setUpWorld();
        while (!exit) {
            if (running) nextGeneration();
            pause(DELAY);            
        }
        System.exit(0);
    }  
    
    protected void setUpButtons() {
        add(new JButton("Randomize"),NORTH);
        add(new JButton("Clear"),NORTH);
        add(new JButton("Next"),NORTH);
        add(new JButton("Start"),NORTH);
        add(new JButton("Stop"),NORTH);
        add(new JButton("Exit"),NORTH);
        addActionListeners();        
    }
    
    protected void setUpBackground() {
        int backgroundDimension = CELL_SIZE*WORLD_SIZE;
        background = new GRect(OFFSET,OFFSET,
                               backgroundDimension,backgroundDimension);
        background.setColor(Color.BLACK);
        background.setFilled(true);
        add(background);           
    }
    
    protected void setUpWorld() {   
        world = new GOval [WORLD_SIZE] [WORLD_SIZE];
        alive = new boolean [WORLD_SIZE][WORLD_SIZE];
        for (int r = 0; r<WORLD_SIZE; r++)
            for (int c = 0; c<WORLD_SIZE; c++) {
                world [r][c] = newCell(r,c);
                alive [r][c] = false;
            }
    }
    
    protected GOval newCell(int i, int j) {
        GOval cell = new GOval(OFFSET+i*CELL_SIZE+CELL_PADDING,
                OFFSET+j*CELL_SIZE+CELL_PADDING,
                CELL_SIZE-2*CELL_PADDING,
                CELL_SIZE-2*CELL_PADDING);
        cell.setColor(Color.BLACK);
        cell.setFilled(true);
        add(cell);
        return cell;
    }    
    
    protected void randomize() {
        for (int r=0; r<WORLD_SIZE; r++) {
            for (int c=0; c<WORLD_SIZE; c++) {
               if (Math.random() <= 1/(1.0*SPARSITY)) {
                alive [r][c] =  true; 
               }
               if (Math.random() >= 1/(1.0*SPARSITY)) {
                alive [r][c] = false;
               }
            }
        }
      updateColors();  
    }
    
    protected void nextGeneration()  {
        updateLife();
        updateColors();
    }
    
    protected void updateLife() {
        boolean[][] temp = new boolean[WORLD_SIZE][WORLD_SIZE];
        for (int r = 0; r < WORLD_SIZE; r++) {
            for (int c = 0; c < WORLD_SIZE; c++) {
                int nN = numNeighbors(r, c);
                if (alive[r][c] == false) {
                    if (nN == 3) {
                        temp[r][c] = true; //birth
                    } else {
                        temp[r][c] = false;
                    }
                } else {
                    if (nN == 2 || nN == 3) {
                        temp[r][c] = true; //life
                    } else {
                        temp[r][c] = false; //death
                    }
                }
            }
        }
        alive = temp;
    }
    
    protected int numNeighbors(int i, int j) {
        int accu = 0;
        for (int dr=-1; dr<=1; dr++) {
            for (int dc=-1; dc<=1; dc++) {
                if ((dc!=0) || (dr!=0)) {
                    int newR = ((i+WORLD_SIZE+dr) % WORLD_SIZE);
                    int newC = ((j+WORLD_SIZE+dc) % WORLD_SIZE);
                    if (alive [newR][newC]) {
                     accu++;   
                    }
                }
            }
        }
        return accu;        
    }    

    
    protected void updateColors() {
        for (int r=0; r<WORLD_SIZE; r++) {
            for (int c=0; c<WORLD_SIZE; c++) {
               if (alive [r][c] == false) {
                   world [r][c].setColor(Color.BLACK);
               }
               else if (numNeighbors (r,c) == 0) {
                   world [r][c].setColor(COLOR0);
               }
               else if (numNeighbors (r,c) == 1) {
                   world [r][c].setColor(COLOR1);
               }
               else if (numNeighbors (r,c) == 2) {
                   world [r][c].setColor(COLOR2);
               }
               else if (numNeighbors (r,c) == 3) {
                   world [r][c].setColor(COLOR3);
               }
               else if (numNeighbors (r,c) >= 4) {
                   world [r][c].setColor(COLOR4);
               } 
            }
        } 
    }   
    
    protected void clear() {
         for (int r=0; r<WORLD_SIZE; r++) {
            for (int c=0; c<WORLD_SIZE; c++) {
                alive [r][c]=false;
            }
        }
         updateColors();
    }

    public void mousePressed(MouseEvent e) {
      int x = e.getX();
      int y = e.getY();
      int i = (x-OFFSET)/CELL_SIZE;
      int j = (y-OFFSET)/CELL_SIZE;
      alive[i][j] = !alive[i][j];
      updateColors();
   }
    
    public void actionPerformed(ActionEvent e) {
      String cmd = e.getActionCommand();
      if (cmd=="Randomize") randomize();
      if (cmd=="Clear") clear();
      else if (cmd=="Next") nextGeneration();
      else if (cmd=="Start") running = true;
      else if (cmd=="Stop") running = false;
      else if (cmd=="Exit") exit = true;
   }

    public static void main(String[] args) {
        new GameOfLife().start();
    }
    
}
