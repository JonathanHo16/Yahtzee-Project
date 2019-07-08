
/**
 * THis is the scorecard that keeps track of the score for the game and deal can idenify and save the winners
 * 
 * @author (Jonathan) 
 * @version (May 20)
 */
//imports the necessary libraries
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
public class Scorecard extends JPanel
{
    // declaration of items needed
    int[][] scores;
    String[] names;
    ScorePaper scorePaper;
    int numPlayers;
    //JLabel spacer = new JLabel(" Spacer ");
    public Scorecard(int numPlayers)
    {
        // instanciation the 2D array of the scores
        scores = new int[numPlayers][21];
        // initialization the the array of player names
        names = new String[numPlayers];
        setSize(new Dimension(194 + 81 *6, 618));
        //setLayout(new BorderLayout());
        //spacer.setForeground(new Color(0, 152, 0));
        //spacer.setBackground(new Color(0, 152, 0));
        //add(spacer, BorderLayout.NORTH);
        this.numPlayers = numPlayers;
        
        //setBackground(new Color(0, 152, 0));
        // adds the image to the scoring rules to this JPanel

        ImageIcon sheet  = new ImageIcon(new File("src/How to score yahtzee.JPG").getAbsolutePath());
        JLabel sheetContainer = new JLabel(sheet);
        add(sheetContainer,BorderLayout.WEST);
        // creates the graphics object scorepaper by passing the array of names and a scores and the numbr of players
        scorePaper = new ScorePaper(numPlayers, scores, names);
        // adds the scorepaper to this JPanel
        add(scorePaper);
    }//sets the name of the player 
    public void setName(int player, String name)
    {
        names[player] = name;
        //scorePaper.drawName(name, player);
        revalidate();
    }
    public String getName( int player)
    {
        return names[player];
    }
    // sets the value of the score of the player im the 2d array based on the actual parameters
    public void scoreAces(int score, int player)
    {
        //System.out.println("scoreAces called");
        scores[player][0] = score;
        //scorePaper.drawAces(scores[player][0], player);
        updateUpper(player);
    }
    // updates the score value in the approate spot in the 2D array based on the player number
    public void scoreTwos(int score, int player)
    {
        scores[player][1] = score;
        //scorePaper.drawAces(scores[player][1], player);
        updateUpper(player);
    }
    // sets the value of the score of the player im the 2d array based on the actual parameters
    public void scoreThrees(int score, int player)
    {
        scores[player][2] = score;
        //scorePaper.drawAces(scores[player][2], player);
        updateUpper(player);
    }
    // sets the value of the score of the player im the 2d array based on the actual parameters
    public void scoreFours(int score, int player)
    {
        scores[player][3] = score;
        //scorePaper.drawAces(scores[player][3], player);
        updateUpper(player);
    }
    // sets the value of the score of the player im the 2d array based on the actual parameters
    public void scoreFives(int score, int player)
    {
        scores[player][4] = score;
        //scorePaper.drawAces(scores[player][4], player);
        updateUpper(player);
    }
    // sets the value of the score of the player im the 2d array based on the actual parameters
    public void scoreSixes(int score, int player)
    {
        scores[player][5] = score;
        //scorePaper.drawAces(scores[player][5], player);
        updateUpper(player);
    }
    // updates the uppertotal before the bonu is factored in
    private void updateUpper(int player)
    {
        int tempScore = 0;
        for( int i = 0; i < 6; i++)
            tempScore = tempScore + scores[player][i];
        scores[player][6] = tempScore;
        //scorePaper.upperTotalScore(scores[player][6], player);
        // checks if the Bonus was achieved
        if (scores[player][6] > 62)
        {
            scores[player][7] = 35;
            //scorePaper.drawBonus(player);
        }
        // evaluates the uppertotal after the bonus
        scores[player][8] = scores[player][7] + scores[player][6];
        scores[player][19] = scores[player][8];
        //scorePaper.upperTotal(scores[player][], player);
        // calls the update grade total to pass the new indormation to the graphics panel to be displayed
        updateGrandTotal(player);
        
    }
    // updates the score value in the approate spot in the 2D array based on the player number
    public void score3Kind(int score, int player)
    {
        scores[player][9] = score;
        //scorePaper.draw3Kind(scores[player][9], player);
        updateLower(player);
        
    }
    // sets the value of the score of the player im the 2d array based on the actual parameters
    public void score4Kind(int score, int player)
    {
        scores[player][10] = score;
        //scorePaper.draw4Kind(scores[player][10], player);
        updateLower(player);
    }
    // sets the value of the score of the player im the 2d array based on the actual parameters
    public void scoreFullHouse( int player)
    {
        scores[player][11] = 25;
        //scorePaper.drawFullHouse(scores[player][11], player);
        updateLower(player);
    }
    // sets the value of the score of the player im the 2d array based on the actual parameters
    public void scoreSmStraight( int player)
    {
        scores[player][12] = 30;
        //scorePaper.drawSmStraight(scores[player][12], player);
        updateLower(player);
    }
   // sets the value of the score of the player im the 2d array based on the actual parameters
    public void scoreLgStraight( int player)
    {
        scores[player][13] = 40;
        //scorePaper.drawLgStraight(scores[player][13], player);
        updateLower(player);
    }
    // adds the points of a yahtzee score
    public void scoreYahtzee( int player)
    {
        // if a yahtzee was not alreay score
        if(scores[player][14] != 50)
        {
            scores[player][14] = 50;
            //scorePaper.drawYahtzee(scores[player][14], player);
        }
        else
        // if a yahzee was already scored it adds a point to the number of exra yahtzee scores
        {
            scores[player][16] += 1;
            //scorePaper.drawExtraYahtzee(scores[player][16], player);
            scoreMultiYahtzee(player);
        }
        
        updateLower(player);
    }
    public void scoreChance (int score, int player)
    {
        scores[player][15] = score;
        //scorePaper.drawChance(scores[player][15], player);
        updateLower(player);
    }
    // evaluates the number of points awared for extra yahtzees based on how many have been scored
    private void scoreMultiYahtzee( int player)
    {
        scores[player][17] = scores[player][16] * 100;
        //scorePaper.drawExtraYahtzeeScore(scores[player][17] , player);
        updateLower(player);
    }
    // updates the lower half of the scorecard
    private void updateLower(int player)
    {
        int tempScore = 0;
        for( int i = 9; i <= 17; i++)
            tempScore = tempScore + scores[player][i];
        tempScore = tempScore - scores[player][16];
        scores[player][18] = tempScore;
        //scorePaper.drawLowerTotal(scores[player][19], player);
        updateGrandTotal(player);
    }
    // updates the last spot in the array based on the upper and lower halves
    private void updateGrandTotal(int player)
    {
        scores[player][20] = scores[player][19] + scores[player][18];
        //System.out.println(scores[player][18]);
        //scorePaper.drawGrandTotal(scores[player][20], player);
        // passes the updated array to the graphics object to update it
        scorePaper.myUpdate(scores);
       
    }
    //this method prints the rules using a JOptionpane
    public void printRules()
    {
        //http://stackoverflow.com/questions/4716503/reading-a-plain-text-file-in-java
        
        StringBuilder output = new StringBuilder();
        String line = "";
        String rules;
        BufferedReader scan;    
        try{
            String path = new File("src/abc123.txt").getAbsolutePath();
            scan = new BufferedReader(new FileReader (path));
            do
            {
                line = scan.readLine();
                output.append(line);
                output.append(("\n"));
             }
            while(line != null);
            
        }
        catch(java.io.FileNotFoundException e )
        {
            System.out.println("File not found");
        }
        catch(java.io.IOException e)
        {
            System.out.println("File error");
          }
        //manually trimming the string to remove the extra characters  
        rules = (output.toString()).substring(2, (output.toString()).length() - 5);
        JOptionPane.showMessageDialog(null, rules);
    }
    // this method saves the winner of the game t a text file
    public void saveWinner()
    {
        
        int winnerScore = 0;
        int winnerCount = 0;
        int index = 0;
        String output;// = new String(BytedString, "UTF-8");
        
       
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        http://stackoverflow.com/questions/2942857/how-to-convert-current-date-into-string-in-java
        // determining the winning score and the amount of winners
        for ( int i = 0; i < numPlayers; i++)
        {
            if(scores[i][20] > winnerScore)
            {
                winnerCount = 1;
                winnerScore = scores[i][20];
            }
            else if (scores[i][20] == winnerScore)
                winnerCount++;
        }
        String[] winners = new String[winnerCount];
        int winnerIndex = 0;
        for ( int i = 0; i < numPlayers; i++)
        {
            if(scores[i][20] == winnerScore)
            { 
                winners[winnerIndex] = names[i];
                winnerIndex++;
            }
                
        }
        
        File outputFile = new File("winnersList.txt");
        // thanks keith for showing me how to do this ( just the FileIO bit
        try {
            if (!outputFile.exists()) {
                outputFile.createNewFile();
                Files.write(Paths.get(outputFile.getAbsolutePath()),"Winners:\n".getBytes(), StandardOpenOption.APPEND);
            }
        } 
        catch (java.io.IOException e) 
        {}
        // prints the winner(s) t0 the file
        try
        {
            for(int i = 0; i < winnerCount; i++)
            {
                output = "\n" + winners[i] + " won with " + winnerScore + " points on " + date + System.getProperty("line.separator"); 
                Files.write(Paths.get(outputFile.getAbsolutePath()),output.getBytes(), StandardOpenOption.APPEND);
            }
        }
        catch ( java.io.IOException e)
        {}
        
    }
    // this method prompts the winner(s) to the users 
    public void outputWinners()
    {
        int winnerScore = 0;
        int winnerCount = 0;
        int index = 0;
        // get the number of winners and the winning score
       for ( int i = 0; i < numPlayers; i++)
        {
            if(scores[i][20] > winnerScore)
            {
                winnerCount = 1;
                winnerScore = scores[i][20];
            }
            else if (scores[i][20] == winnerScore)
                winnerCount++;
        }
        String output = "Congratulations to the winner";
        // adding the plural
        if (winnerCount >= 2)
            output = output + "s";
        //printing the winners;    
        for ( int i = 0; i < numPlayers; i++)
        {
            if(scores[i][20] == winnerScore)
            { 
                output = output + ", " + names[i];
                
            }
                
        }
        JOptionPane.showMessageDialog(null, output);
    }
}
