
//********************************************************************
// Die.java Author: Jonathan (inspired from the text book more methods
//
// Represents one die (singular of dice) with faces showing values
// between 1 and 6.
//********************************************************************
import javax.swing.*;
import java.io.File;

public class Die extends JLabel 
{
    private final int MAX = 6; // maximum face value
    private int faceValue; // current value showing on the die
    private boolean rolling = true;
    
    ImageIcon[] icons = new ImageIcon[6];

    
    //-----------------------------------------------------------------
    // Constructor: Sets the initial face value.
    //-----------------------------------------------------------------
    public Die()
    {
        
        faceValue = 1;
        // initializing the array of Icons
        String[] filePaths = new String[6];
        filePaths[0] = new File("src/diceFaces/diceFace1.png").getAbsolutePath();
        filePaths[1] = new File("src/diceFaces/diceFace2.png").getAbsolutePath();
        filePaths[2] = new File("src/diceFaces/diceFace3.png").getAbsolutePath();
        filePaths[3] = new File("src/diceFaces/diceFace4.png").getAbsolutePath();
        filePaths[4] = new File("src/diceFaces/diceFace5.png").getAbsolutePath();
        filePaths[5] = new File("src/diceFaces/diceFace6.png").getAbsolutePath();

        icons[0] = new ImageIcon(filePaths[0]);
        icons[1] = new ImageIcon(filePaths[1]);
        icons[2] = new ImageIcon(filePaths[2]);
        icons[3] = new ImageIcon(filePaths[3]);
        icons[4] = new ImageIcon(filePaths[4]);
        icons[5] = new ImageIcon(filePaths[5]);
        this.setIcon(icons[0]);
        System.out.println("Die Created");
        
    }
    //-----------------------------------------------------------------
    // Rolls the die and returns the result.
    //-----------------------------------------------------------------
    public int roll()
        {
        faceValue = (int)(Math.random() * MAX) + 1;
        mySetIcon(faceValue);
        return faceValue;
    }
    //-----------------------------------------------------------------
    // Face value mutator.
    //-----------------------------------------------------------------
    public void setFaceValue (int value)
    {
        faceValue = value;
    }
    //-----------------------------------------------------------------
    // Face value accessor.
    //-----------------------------------------------------------------
    public int getFaceValue()
    {
        return faceValue;
    }
    //-----------------------------------------------------------------
    // Returns a string representation of this die.
    //-----------------------------------------------------------------
    public String toString()
    {
        String result = Integer.toString(faceValue);
        return result;
    }
    
    public void lockDie()
    {
        rolling = false;
    }
    
    public void unlockDie()
    {
        rolling = true;
    }
    
    public boolean isRolling()
    {
        return rolling;
    }
   
    public void mySetIcon(int value)
    {
        setIcon(icons[faceValue-1]);
    }
}