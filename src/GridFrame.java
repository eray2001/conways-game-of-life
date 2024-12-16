import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GridFrame extends JFrame implements KeyListener {

    @Override
    public synchronized void addKeyListener(KeyListener l) {
        super.addKeyListener(l);
    }
    GridPanel gridPanel = new GridPanel();

    public GridFrame() throws HeadlessException {

        super("game of life");


        gridPanel.addState(1,Color.white);
        setContentPane(gridPanel);
        this.addKeyListener(this);

        gridPanel.setGridListener(new GridPanel.GridListener() {
            @Override
            public void gridReady() {
            }

            @Override
            public void click(int gridX, int gridY, int button) {
                gridPanel.setCell(1, gridY, gridX);
                gridPanel.update();
            }});

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 842);
        setResizable(false);

        setVisible(true);
    } 


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {

            gridPanel.changeStatesAlgo();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


}