
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

// This class contains all the parts of the GUI interface
class GameGUI extends JPanel implements Runnable{
    //instance variables
    private GraphicsPanel gameGraphics;
    private Thread runner;
    
    //constructor
    public GameGUI() {

        //Create graphics panel
        gameGraphics = new GraphicsPanel();
        
        //Set the layout and add the components
        this.setLayout(new BorderLayout());
        this.add(gameGraphics, BorderLayout.CENTER);
        //Start the thread
        if (runner == null)
        {
            runner = new Thread(this);
            runner.start();
        }
    }
    
    public void run()
    {
        while(true)
        {
            try {runner.sleep(1);}
            catch (Exception e) { }
            gameGraphics.repaint();
        }
    }
}