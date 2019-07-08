import javax.swing.JFrame;
public class Main {

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("YAHTZEE!");
        frame.setDefaultCloseOperation(3);
        frame.add(new JBoard());
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
