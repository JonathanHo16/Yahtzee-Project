/**
 * adds the panel containing the Game board to a frame so it is visible
 * 
 * @author (Jonathan Ho) 
 * @version (4/13/17)
 */
import javax.swing.*;
import java.awt.*;
public class Driver 
{
    public static void main(String [] args) {
        JFrame frame = new JFrame("YAHTZEE!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.add(new JBoard());
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}