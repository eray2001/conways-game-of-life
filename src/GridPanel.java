import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GridPanel extends JPanel {
    private GridListener gridListener;
    private static final int GRID_SIZE = 7;
    private static final int MARGIN = 1;
    private Map<Integer,BufferedImage> statesMap = new HashMap<>();
    private Integer[][] states;
    private Integer[][] numNeighbors;

    public Integer[][] getStates() {
        return states;
    }

    public GridPanel() {
        setBackground(Color.LIGHT_GRAY);
        addState(0,Color.BLACK);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                int gridX = (e.getX() - MARGIN)/(GRID_SIZE + MARGIN);
                int gridY = (e.getY() - MARGIN)/(GRID_SIZE + MARGIN);

                if(gridListener != null) {
                    gridListener.click(gridX, gridY, e.getButton());
                }
                super.mousePressed(e);
            }
        });
    }

public Integer getCell(int x, int y) {
        if(x<0||x>99||y<0||y>99) {
            return null;
        }
        return states[y][x];
}

    public void setCell(int state, int x, int y) {
        if(x<0||x>99||y<0||y>99)
            return;
        states[y][x] = state;
    }

    public interface GridListener {
        void gridReady();
        void click(int gridX,int gridY, int button);
    }

    public void setGridListener(GridListener gridListener) {
        this.gridListener = gridListener;
    }

    public void update(){
        repaint();

    }



    @Override
    public void paint(Graphics g) {
        super.paint(g);
        initCells(800, 800);
        initNeighbor();
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);

        for(int i = MARGIN , n = 0; i < 800; i+=GRID_SIZE + MARGIN , n++) {
            for(int j = MARGIN, m = 0; j < 800; j+=GRID_SIZE + MARGIN , m++) {
                Integer state = states[n][m];
                BufferedImage stateImage = statesMap.get(state);
                g2.drawImage(stateImage,i,j,null);}}
    }

    private void initCells(int gridWidth, int gridHeight) {
        if(states != null) {
            return;
        }
        states = new Integer[gridWidth][gridHeight];
        Arrays.stream(states).forEach(row -> Arrays.fill(row, 0));

        if(gridListener != null) {
            gridListener.gridReady();
        }
    }

    private void initNeighbor(){
        numNeighbors = new Integer[states.length][states.length];
    }

    public void addState(Integer state, Color background) {
      BufferedImage image = new BufferedImage(GRID_SIZE - 1, GRID_SIZE - 1,
              BufferedImage.TYPE_INT_RGB);

      Graphics2D g2 = image.createGraphics();
      g2.setColor(background);
      g2.fillRect(0, 0, GRID_SIZE-1, GRID_SIZE-1);

      g2.dispose();
      statesMap.put(state, image);
    }

    public void changeStatesAlgo() {

            for(int i = 0;i<states.length;i++) {
                for(int j = 0; j<states[i].length;j++) {
                    checkNeighbors(i, j);
                }}
            for(int i = 0;i<states.length;i++) {
                for(int j = 0;j<states[i].length;j++) {
                    if(states[i][j]==0 && numNeighbors[i][j]==3) states[i][j]=1;
                    if(states[i][j]==1) {
                        if(numNeighbors[i][j]>3) states[i][j]=0;
                        if(numNeighbors[i][j]<2) states[i][j]=0;
                    }

                }
            }
        update();
    }

    public void checkNeighbors(int i,int j) {
        int numNeighbor = 0;

        try {if(states[i-1][j-1]==1) numNeighbor++;}
        catch(Exception e) {}
        try{ if(states[i][j-1]==1) numNeighbor++;}
        catch(Exception e) {}
        try {if(states[i+1][j-1]==1) numNeighbor++;}
        catch(Exception e) {}
        try{if(states[i-1][j]==1) numNeighbor++;}
        catch(Exception e) {}
        try{if(states[i+1][j]==1) numNeighbor++;}
        catch(Exception e) {}
        try{if(states[i-1][j+1]==1) numNeighbor++;}
        catch(Exception e) {}
        try{if(states[i][j+1]==1) numNeighbor++;}
        catch(Exception e) {}
        try{if(states[i+1][j+1]==1) numNeighbor++;}
        catch(Exception e) {}

        numNeighbors[i][j] = numNeighbor;
    }

}
