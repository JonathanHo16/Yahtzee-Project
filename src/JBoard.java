
/**
 * Main Class of the game, it contains ll of the logic for the game to run. Specifically, the game flow, amd the order of the operations for the game. 
 * Graphically, it contains all of the other JPanels that are used for the GUI. 
 * 
 * @author (Jonathan) 
 * @version (May 20)
 */
//imports the necessary libraries
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.*;
import java.util.*;
public class JBoard extends JPanel 
{
    // declaration of GUI items used for graphics output
    JPanel keeperPanel, playPanel, buttonPanel, promptPanel, scoreContainer, keeperContainer;
    JButton rollButton, done;
    JLabel roundCounter, playerIndicator;
    int xDimension;
    // define the universal background color    
    Color backGround = new Color(0, 152, 0);
    //necessary variables to run the game made class wide so they can be accessed from all methods
    Die[] dice = new Die[5];
    int[] dieValues = new int[5];
    int numOfPlayers;
    Scorecard scoreCard;
    String[] playerNames;
    int numOfRolls = 0;
    int playerTurn= 0;
    int roundNum = 1;
    String choice;
    //Defining the basic border for all other borders to be built upon
    
    Border border = BorderFactory.createMatteBorder(3, 3, 3, 3, new Color( 253, 200, 88));
    public JBoard()
    {
        getNumOfPlayers();
        // initialization of the variables for the grpahical output of the main panel
        xDimension = 6 *81 + 193 + 75 + 10 + 150;  
        setPreferredSize(new Dimension (xDimension , 833 + 25 - (175-60)+ 5 + 32 + 5));
        setLayout(new BorderLayout());
        setBackground(backGround);
        
        // initiialization of the keeper panel
        keeperPanel = new JPanel();
        keeperPanel.setBackground(new Color(8, 99, 58));
        keeperPanel.setPreferredSize(new Dimension(100, 1000));
        // giving the keeper panel a custom border dervied from the main border style
        keeperPanel.setBorder(BorderFactory.createTitledBorder(border,"Keepers", TitledBorder.CENTER, TitledBorder.TOP, new Font("Book Antiqua" , Font.ITALIC, 14), new Color( 253, 200, 88)));
        add(keeperPanel, BorderLayout.WEST);
        // intialization of the play panel
        playPanel = new JPanel();
        playPanel.setPreferredSize(new Dimension ( 500, 500));
        // give it a custom border derived from the main broder style
        playPanel.setBorder(BorderFactory.createTitledBorder(border ,"Rolling", TitledBorder.CENTER, TitledBorder.TOP, new Font("Book Antiqua" , Font.ITALIC, 14), new Color( 253, 200, 88)));
        playPanel.setBackground(new Color(0, 152,0 ));
        add(playPanel, BorderLayout.CENTER);
        // this section is the control panel at the bottom of the screen
        {
            // instanciation of buttons used in the panel
            done = new JButton();
            done.setText("Done");
            done.setPreferredSize(new Dimension((xDimension - 10)/2, 50));
            done.addActionListener(new buttonListener());
            
            rollButton = new JButton();
            rollButton.setText("Roll the Dice");
            rollButton.setPreferredSize(new Dimension((xDimension - 10)/2, 50));
            
            rollButton.addActionListener(new buttonListener());
             // container for the 2 buttons 
            buttonPanel = new JPanel();
            buttonPanel.setPreferredSize(new Dimension ( 1000, 60 + 20));
            
            buttonPanel.setBackground(new Color(81, 24, 24));
            buttonPanel.add(rollButton);
            buttonPanel.add(done);
            add(buttonPanel, BorderLayout.SOUTH);
        }
        
        
        // instanciation 5 dice in a array and giving them an action listener and adding them to the play panel
        for( int i = 0; i < 5; i++)
        {
            dice[i] = new Die();
            playPanel.add(dice[i]);
            dice[i].addMouseListener(new myMouseListener());
        }
     
        //instanciation of scorecard
        scoreCard = new Scorecard(numOfPlayers);
        // get the player names
        for( int i = 1; i <= numOfPlayers; i++)
        {
            String tempName;
            do
                tempName = JOptionPane.showInputDialog(" Please enter the name of Player " + i );
            while(tempName == null );
            playerNames[i-1] = tempName;
            // giving the scorecard the name to be printed on screen
            scoreCard.setName(i-1, tempName);
        }
        //this section is the output n the North portionof the panel
        {
            // instanciation of the container panel
            promptPanel = new JPanel();
            promptPanel.setPreferredSize( new Dimension(xDimension, 25));
            promptPanel.setLayout(new BorderLayout());
            promptPanel.setBackground(backGround);
            // instanciation of the JLabel outing the round number
            roundCounter = new JLabel("It is round " + roundNum + " of 13");
            roundCounter.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
            roundCounter.setForeground(Color.WHITE);
            roundCounter.setSize(new Dimension( (xDimension - 10)/ 2, 11));
            promptPanel.add(roundCounter, BorderLayout.WEST);
            // instanciation of the JLabel outpping whose turn it is
            playerIndicator = new JLabel ("It is now " + playerNames[playerTurn] + "'s turn.");
            playerIndicator.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
            playerIndicator.setForeground(Color.WHITE);
            playerIndicator.setSize(new Dimension( (xDimension - 10)/ 2, 11));
            playerIndicator.setAlignmentX(Component.RIGHT_ALIGNMENT);
            promptPanel.add(playerIndicator, BorderLayout.EAST);
        }
        add(promptPanel, BorderLayout.NORTH);
        // instaciation of the container JPanel for the scorecard done for sylistic purposses
        scoreContainer = new JPanel();
        scoreContainer.setBackground(backGround);
        scoreContainer.add(scoreCard);
        scoreContainer.setBorder(BorderFactory.createTitledBorder(border,"Score", TitledBorder.CENTER, TitledBorder.TOP, new Font("Book Antiqua" , Font.ITALIC, 14), new Color( 253, 200, 88)));
        
        add(scoreContainer, BorderLayout.EAST);
        scoreCard.printRules();
    }
    // rolls the dice ans saves their values to an interger array
    private void rolltheDice()
    {
        for(int i = 0; i < 5; i++)
        {
            if(dice[i].isRolling())
            {
                dice[i].roll();
                dieValues[i] = dice[i].getFaceValue();
            }
        }
        
    }
    // gets the number of players
    private void getNumOfPlayers()
    {   
        String tempNumOfPlayers;
        
        boolean flag = false;
        do
        {
            // resettingthe flag variable (ironically called flag ) to false
            flag = false;
            // get input from the user
            tempNumOfPlayers = JOptionPane.showInputDialog("Please enter the number of players(max of 6)");
            try
            {
                numOfPlayers = Integer.parseInt(tempNumOfPlayers);
            }
            // catches the excpetion if a user does not give valid input
            catch( NumberFormatException e)
            {
                flag = true;
                JOptionPane.showMessageDialog(null,"That input was invalid please try again");
            }
            // ensures the number of players is less than the max of 6
            if(numOfPlayers > 6 || numOfPlayers < 1)
                flag = true;
        } 
        while(tempNumOfPlayers == null || flag );
        
                
        numOfPlayers = Integer.parseInt(tempNumOfPlayers);
        // instanciaties array of names to the correct size
        playerNames = new String[numOfPlayers];
    
    }
    
    // situation checker
    // checks if at least one 1, 2 , 3 ... were rolled
    private boolean checkTheSame(int key)
    {
        int counter = 0;
        for(int i = 0; i < 5; i++)
        {
            if(dice[i].getFaceValue() == key)
                counter++;
        }
        // if atleat 1 was rolled
        if (counter > 0)
            return true;
        else
            return false;
    }
    // returns the point value for the amount of 1's , 2's ... rolled
    private int scoreTHESAME(int key)
    {
         int counter = 0;
        for(int i = 0; i < 5; i++)
        {
            if(dieValues[i] == key)
                counter++;
        }
        return counter * key;
    }
    // checks if 3 of a kind was scored
    private boolean check3OfAKind()
    {
        // creates a temporary array stroing the number of 1's , 2's , 3's rolled 
        int[] temp = {0, 0, 0, 0, 0, 0};
        for( int i = 0; i < 5; i++)
        {
            switch (dieValues[i])
            {
                case 1:
                    temp[0]+= 1;
                    break;
                case 2:
                    temp[1]+= 1;
                    break;
                case 3:
                    temp[2]+= 1;
                    break;
                case 4:
                   temp[3]+= 1;
                    break;
                case 5:
                    temp[4]+= 1;
                    break;
                case 6:
                    temp[5]+= 1;
                    break;
            }
        }
        // sorts the array from lowest to highest
        Arrays.sort(temp);
        //is the last spot of highest amout of one value is greater than 3 
        if(temp[5] >= 3)
            return true;
        else
            return false;
    }
    // same as above method except for 4
    private boolean check4OfAKind()
    {
       
        // creates a temporary array stroing the number of 1's , 2's , 3's rolled 
        int[] temp = {0, 0, 0, 0, 0, 0};
        for( int i = 0; i < 5; i++)
        {
            switch (dieValues[i])
            {
                case 1:
                    temp[0]+= 1;
                    break;
                case 2:
                    temp[1]+= 1;
                    break;
                case 3:
                    temp[2]+= 1;
                    break;
                case 4:
                   temp[3]+= 1;
                    break;
                case 5:
                    temp[4]+= 1;
                    break;
                case 6:
                    temp[5]+= 1;
                    break;
            }
        }
        // put them in an array
        
        
        Arrays.sort(temp);
        
        if(temp[5] >= 4)
            return true;
        else
            return false;
    }
    // gets the point value for the 3 or 4 or a kind
    private int scoreOfAKind( int key)
    {
        int[] temp = {0, 0, 0, 0, 0, 0};
        for( int i = 0; i < 5; i++)
        {
            switch (dieValues[i])
            {
                case 1:
                    temp[0]+= 1;
                    break;
                case 2:
                    temp[1]+= 1;
                    break;
                case 3:
                    temp[2]+= 1;
                    break;
                case 4:
                   temp[3]+= 1;
                    break;
                case 5:
                    temp[4]+= 1;
                    break;
                case 6:
                    temp[5]+= 1;
                    break;
            }
        }
        // put them in an array
        int scoreFactor = 0;
        if(temp[5] >= key)
            scoreFactor = 6;
        else if(temp[4] >= key)
             scoreFactor = 5;
         else if(temp[3] >= key)
            scoreFactor = 4;
         else if(temp[2] >= key)
            scoreFactor = 3;
         else if(temp[1] >= key)
            scoreFactor = 2; 
         else if(temp[1] >= key)
            scoreFactor = 1;
         //sort the array from lowest to higest   
        Arrays.sort(temp);
        // multiply the multiple(key (3 or 4)) by te score factor
        return key * scoreFactor;
    }
    // checks for straights of length  4
    private boolean checkSmallStraight()
    {
         // creates a temporary array stroing the number of 1's , 2's , 3's rolled 
        int[] temp = {0, 0, 0, 0, 0, 0};
        for( int i = 0; i < 5; i++)
        {
            switch (dieValues[i])
            {
                case 1:
                    temp[0]+= 1;
                    break;
                case 2:
                    temp[1]+= 1;
                    break;
                case 3:
                    temp[2]+= 1;
                    break;
                case 4:
                   temp[3]+= 1;
                    break;
                case 5:
                    temp[4]+= 1;
                    break;
                case 6:
                    temp[5]+= 1;
                    break;
            }
        }
        int flagCounter;
        boolean found = false;
        // a straight must start with 1,2,3, 
        // forloop that checks for a straight starting on 1,2,3,
        for (int initial = 0; initial <= 2; initial++)
        {
            flagCounter = 0;
            //increments the spot in the array starting from the intial spot in the array
            for( int increment = 0; increment < 4; increment++)
            {
                if(temp[initial + increment] > 0)
                    flagCounter++;
            }
            // if 3 consecutive numbers were found then return true
            if ( flagCounter == 4)
                found = true;
        }
        return found;
    }
    // same as above method but for straights of length 5
    private boolean checkLargeStraight()
    {
        int[] temp = {0, 0, 0, 0, 0, 0};
        for( int i = 0; i < 5; i++)
        {
            switch (dieValues[i])
            {
                case 1:
                    temp[0]+= 1;
                    break;
                case 2:
                    temp[1]+= 1;
                    break;
                case 3:
                    temp[2]+= 1;
                    break;
                case 4:
                   temp[3]+= 1;
                    break;
                case 5:
                    temp[4]+= 1;
                    break;
                case 6:
                    temp[5]+= 1;
                    break;
            }
        }
        int flagCounter;
        boolean found = false;
        for (int initial = 0; initial <= 1; initial++)
        {
            flagCounter = 0;
            for( int increment = 0; increment < 5; increment++)
            {
                if(temp[initial + increment] > 0)
                    flagCounter++;
            }
            if (flagCounter == 5)
                found = true;
        }
        return found;
    }
    private boolean checkFullHouse()
    {
         // creates a temporary array stroing the number of 1's , 2's , 3's rolled 
        int[] temp = {0, 0, 0, 0, 0, 0};
        for( int i = 0; i < 5; i++)
        {
            switch (dieValues[i])
            {
                case 1:
                    temp[0]+= 1;
                    break;
                case 2:
                    temp[1]+= 1;
                    break;
                case 3:
                    temp[2]+= 1;
                    break;
                case 4:
                   temp[3]+= 1;
                    break;
                case 5:
                    temp[4]+= 1;
                    break;
                case 6:
                    temp[5]+= 1;
                    break;
            }
        }
        Arrays.sort(temp);
        // if the greatest is 3 and the second greatest is 2 since there are only 5 dice
        if(temp[5] == 3 && temp[4] == 2)
            return true;
        else
            return false;
    }
    //checks for a yahtzee
    private boolean checkYahtzee()
    {
         // creates a temporary array stroing the number of 1's , 2's , 3's rolled 
        int[] temp = {0, 0, 0, 0, 0, 0};
        for( int i = 0; i < 5; i++)
        {
            switch (dieValues[i])
            {
                case 1:
                    temp[0]+= 1;
                    break;
                case 2:
                    temp[1]+= 1;
                    break;
                case 3:
                    temp[2]+= 1;
                    break;
                case 4:
                   temp[3]+= 1;
                    break;
                case 5:
                    temp[4]+= 1;
                    break;
                case 6:
                    temp[5]+= 1;
                    break;
            }
        }
        Arrays.sort(temp);
        // all the dice must be thge same therefore the last spot of the array must be 5
        if(temp[5] == 5)
            return true;
        else
            return false;
    }
    // returns an object array for use in a JOptionPane that contains an string representation of all the possible ways to score with the current start of the dice
    public Object[] getOptions()
    {
        // the lengths of the array is unknow so an arraylist is used
        ArrayList<String> optionsList = new ArrayList(); 
        // checks for if 1's, 2's, 3's ... are rolled
        for(int i = 1; i <=6; i++)
        {
            // adds if the options is true to the list of options
            if (checkTheSame(i))
                optionsList.add("Score " +i+ "'s");
        }
        // checks for all different types of score and adds them toi the list if they are scoreable
        if( check3OfAKind())
            optionsList.add("Score 3 of a kind");
        if( check4OfAKind())
            optionsList.add("Score 4 of a kind");
        if(checkFullHouse())
            optionsList.add ("Score a Full House");
        if(checkSmallStraight())
            optionsList.add ("Score a Small Straight");
        if(checkLargeStraight())
            optionsList.add ("Score a Large Straight");
            
        optionsList.add("Score Chance");
        
        if(checkYahtzee())
            optionsList.add("Score a Yahtzee");
        // creates an formal array that is correct length based on howmany options are scorable
        String[] options = new String[optionsList.size()];
        // translates the arraylist to the formal array
        for(int i = 0; i < optionsList.size(); i++)
        {
            options[i] = optionsList.get(i);
        }
        
        return options;
    }
    //adds the score of the selected option to the scoreCard
    private void appendChoice(String choice, int player)
    {
        // variable that represents the value of points for some cases
        int temp;
        switch (choice)
        {
            case "Score 1's":
                scoreCard.scoreAces(scoreTHESAME(1), player);
                break;
            case "Score 2's":
                scoreCard.scoreTwos(scoreTHESAME(2), player);
                break;
            case "Score 3's":
                scoreCard.scoreThrees(scoreTHESAME(3), player);
                break;
            case "Score 4's":
                scoreCard.scoreFours(scoreTHESAME(4), player);
                break;
            case "Score 5's":
                scoreCard.scoreFives(scoreTHESAME(5), player);
                break;
            case "Score 6's":
                scoreCard.scoreSixes(scoreTHESAME(6), player);
                break;
            case "Score 3 of a kind":
               temp = 0;
               // sum of all dice
                for(int i = 0; i < 5; i++)
                {
                    temp = temp + dice[i].getFaceValue();
                }
                scoreCard.score3Kind(temp, player);
                break;
            case "Score 4 of a kind":
                temp = 0;
                //sum of all dice
                for(int i = 0; i < 5; i++)
                {
                    temp = temp + dice[i].getFaceValue();
                }
                scoreCard.score4Kind(temp, player);
                break;
            case "Score a Full House":
            // have no value attached since there is a defnied value of points attached
                scoreCard.scoreFullHouse(player);
                break;
            case "Score a Small Straight":
                scoreCard.scoreSmStraight(player);
                break;
            case "Score a Large Straight":
                scoreCard.scoreLgStraight(player);
                break;
            case "Score Chance":
                temp = 0;
                // sum of all dice
                for(int i = 0; i < 5; i++)
                {
                    temp = temp + dice[i].getFaceValue();
                }
                scoreCard.scoreChance(temp, player);
                break;
            case "Score a Yahtzee":
                scoreCard.scoreYahtzee(player);
                break;
            }
        // updates the scorecontainer so the GUI gets updated
        scoreContainer.revalidate();
        }
    private void resetDice()
    {
        for( int i = 0; i < 5; i++)
        {
            if (!dice[i].isRolling())
            {
                // adds the dice to the rolling panel and unlocks it
                dice[i].unlockDie();
                keeperPanel.remove(dice[i]);
                playPanel.add(dice[i]);
            }
            
        }
        revalidate();
        repaint();
    }
    // resets the game 
     private void newGame()
    {
        int again;
        // reeset all the variables
        roundNum = 1;
        
        scoreContainer.remove(scoreCard);
        
        do
                    again = JOptionPane.showConfirmDialog(null, "Are you playing again with the same players?","Players",  JOptionPane.YES_NO_OPTION);
        while (again == JOptionPane.CLOSED_OPTION);
        if( again == JOptionPane.NO_OPTION)
        {
            getNumOfPlayers();
            scoreCard = new Scorecard(numOfPlayers);
            for( int i = 1; i <= numOfPlayers; i++)
            {
                String tempName;
                do
                    tempName = JOptionPane.showInputDialog(" Please enter the name of Player " + i);
                while(tempName == null);
                playerNames[i-1] = tempName;
                scoreCard.setName(i-1, tempName);
            }
        }
        else
        {
            
            scoreCard = new Scorecard(numOfPlayers);
            for( int i = 1; i <= numOfPlayers; i++)
            {
               scoreCard.setName(i-1, playerNames[i-1]); 
            }
        }
        
        playerTurn = 0;
        // gets the new set of players
        
        xDimension = 6 *81 + 193 + 75 + 10 + 150;  
        setPreferredSize(new Dimension (xDimension , 833 + 25 - (175-60)+ 5 + 32 + 5));
        scoreContainer.add(scoreCard);
        playerIndicator.setText("It is now " + playerNames[playerTurn] + "'s turn.");
        roundCounter.setText("It is round " + roundNum + " of 13");
    }
    
   
    private class myMouseListener implements MouseListener
    {
        public void mouseClicked(MouseEvent e)
        {
            boolean found = false;
            int index = 0;
            //searches for which dice was clicked
            while (!found)
            {
                if (dice[index] == (Die)e.getSource())
                //http://stackoverflow.com/questions/9635126/which-jlabel-has-been-clicked
                {
                    found = true; 
                }
                else
                    index += 1;
                
                if (index >=5)
                    return;
            }
            // if the dice was rolling
            // adds the dice to the keeper panel and lockes the dice so it does not roll
            if(dice[index].isRolling() && numOfRolls != 0)
            {
                dice[index].lockDie();
                playPanel.remove(dice[index]);
                keeperPanel.add(dice[index]);
            }
            // if the dice was not rolling set its state to rolling
            else if (!dice[index].isRolling())
            {
                // adds the dice to the rolling panel and unlocks it
                dice[index].unlockDie();
                keeperPanel.remove(dice[index]);
                playPanel.add(dice[index]);
            }
            if(numOfRolls == 0)
            {
                JOptionPane.showMessageDialog(null, "You must roll all of the dice on your first roll");
            }
            // updates the panel
            revalidate();
            repaint();
        }
        // unused methods
        public void mouseEntered(MouseEvent e){}
        public void mouseExited(MouseEvent e){}
        public void mousePressed(MouseEvent e){}
        public void mouseReleased(MouseEvent e){}
    }
    private class buttonListener implements ActionListener
    {

        public void actionPerformed( ActionEvent e)
        {
            // the roll button was pressed it rolls the dice and increments the number of rolls
            if(rollButton == e.getSource())
            {
                rolltheDice();
                revalidate();
                repaint();
                numOfRolls++;
                //System.out.println("DEBUG: the value of numOfRolls is: " + numOfRolls);
               
            }
            else if (done == e.getSource())
            // signifies that the user is done rolling the dice and is ready score
            {
                //System.out.println("DEBUG: the value of numOfRolls is: " + numOfRolls);
                // enusres the user has rolled atleast once
                if(numOfRolls != 0)
                {
                    numOfRolls = 3;
                    
                }
                else
                    JOptionPane.showMessageDialog(null, "You must roll the dice first");
            }
            if(numOfRolls == 3)
            {
                // evalutats the possible ways to score and askes the user to choose a way of scoring
              do
                    choice = (String) JOptionPane.showInputDialog(null, "what would you like to score?", "Scoring",JOptionPane.QUESTION_MESSAGE, null, getOptions(), null );
                // https://www.mkyong.com/swing/java-swing-joptionpane-showinputdialog-example/ this is where I figured out thow to acieve my JOption Panel
              while(choice == null);
               //increment player
               // the the scoreCard to increae the score based on the choice
               appendChoice(choice, playerTurn);
                playerTurn += 1;
                //reset the dice so thay all roll on the first roll
                resetDice();
                // if everyone has taken their turn this round
               if(playerTurn == numOfPlayers)
                {
                    // go back to the first player
                    // increate the round number
                    playerTurn = 0;
                    roundNum += 1;
                    
                    roundCounter.setText("It is round " + roundNum + " of 13");
                    //System.out.println("DEBUG: the value of numOfRolls is: " + numOfRolls);
                    
                }
                //promts the user to pass the device to the correct player
                if( roundNum != 14)
                 JOptionPane.showMessageDialog(null, "It is now " + scoreCard.getName(playerTurn) + "'s turn please pass the device to them");
                 // reset the number of rolls
                 numOfRolls = 0;
                 // update the GUI output
                 playerIndicator.setText("It is now " + playerNames[playerTurn] + "'s turn.");
            }
            // end of game scenario
            if(roundNum == 14)
            { 
                //System.out.println("Game Over");
                int again;
                scoreCard.saveWinner();
                scoreCard.outputWinners();
                // asking if the user wants to play again
                do
                    again = JOptionPane.showConfirmDialog(null, "Play Again", "Would you like to play again?", JOptionPane.YES_NO_OPTION);
                while (again == JOptionPane.CLOSED_OPTION);
                // sets up a new game
                if( again == JOptionPane.YES_OPTION)
                    newGame();
                else if (again == JOptionPane.NO_OPTION)
                // exits the system
                {
                    JOptionPane.showMessageDialog(null, "Good Bye!");
                    System.exit(0);
                }
            }
            
        }
         
    }
}
