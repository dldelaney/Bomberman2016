

// GameMain
// Create base window and add GUI panel to it
import javax.swing.JFrame;

class GameMain {
    public static void main(String[] args) {
        JFrame window = new JFrame("Don't Look At This");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().add(new GameGUI());
        window.pack();  // finalize layout
        window.setVisible(true);  // make window visible
        window.setResizable(false);
    }
}