
/**
 * JPanel with the Graphics object doing the output. what is drawn is determined by the array. Important Note: all Y vales were determined experimentally 
 * to match up the picture in the scoreCard class
 * 
 * @author (Jonathan) 
 * @version (May 20)
 */
import java.awt.*;
import javax.swing.*;
public class ScorePaper extends JPanel
{  
    //declaration of Class wide variables for use by all methods
    int numPlayers;
    //Graphics2D g;
    int[][] scores;
    String[] names;
    Font myFont = new Font("Comic Sans", Font.PLAIN, 18);
    int horizontalPush = 35;
    // constructor
    ScorePaper(int numPlayers, int[][] passedScores, String[] passedNames)
    {
        // sets up the panel
        setPreferredSize(new Dimension(81*6, 633));
        setBackground(Color.WHITE);
        // copying the data passed through the constructor
        this.numPlayers = numPlayers;
        scores = passedScores;
        names = passedNames;
    }
    // defining what must be drawn
    public void paintComponent(Graphics page)
    {
        super.paintComponent(page);
        //page.setFont(myFont);
        //g = (Graphics2D) page.create();
        //creates the dividing lines  so that the players are clearly divided
        for(int i = 0; i <  7 ; i++)
        {
            page.drawLine(i*81 , 0, i*81, 633);
        }
        page.drawLine(6*81 - 2, 0, 6*81 -2, 633); 
        // draws the names of each player
        for( int i = 0; i < numPlayers; i++)
        {
            drawName(names[i], i, page);
        }
        
        // draws the scores for each player in 
        for(int player = 0; player < numPlayers;player++)
        {
            drawAces(scores[player][0], player, page);
            drawTwos(scores[player][1], player, page);
            drawThrees(scores[player][2], player, page);
            drawFours(scores[player][3], player, page);
            drawFives(scores[player][4], player, page);
            drawSixes(scores[player][5], player, page);
            upperTotalScore(scores[player][6], player, page);
            drawBonus(scores[player][7],player, page);
            upperTotal(scores[player][8], player, page);
            draw3Kind(scores[player][9], player, page);
            draw4Kind(scores[player][10], player, page);
            drawFullHouse(scores[player][11], player, page);
            drawSmStraight(scores[player][12], player, page);
            drawLgStraight(scores[player][13], player, page);
            drawYahtzee(scores[player][14], player, page);            
            drawExtraYahtzee(scores[player][16], player, page);
            drawExtraYahtzeeScore(scores[player][17], player, page);
            drawChance(scores[player][15], player, page);
            drawLowerTotal(scores[player][18], player, page);
            drawGrandTotal(scores[player][20], player, page);
        }
        drawHorizontalLines(6, page );
    }
    // draws the horizontal lines with the pre determines Y values 
    public void drawHorizontalLines(int x, Graphics g )
    { 
        int y = 0;
        g.drawLine(0, y, x * 81, y); 
        y = 28;
        g.drawLine(0, y, x * 81, y); 
        y = 57;
        g.drawLine(0, y, x * 81, y); 
        y = 84;
        g.drawLine(0, y, x * 81, y); 
        y = 109;
        g.drawLine(0, y, x * 81, y); 
        y = 135;
        g.drawLine(0, y, x * 81, y); 
        y = 160;
        g.drawLine(0, y, x * 81, y); 
        y = 186;
        g.drawLine(0, y, x * 81, y); 
         y = 213;
        g.drawLine(0, y, x * 81, y); 
         y = 241;
        g.drawLine(0, y, x * 81, y); 
         y = 569;
        g.drawLine(0, y, x * 81, y); 
         y = 293;
        g.drawLine(0, y, x * 81, y); 
         y = 370;
        g.drawLine(0, y, x * 81, y); 
         y = 344;
        g.drawLine(0, y, x * 81, y); 
         y = 317;
        g.drawLine(0, y, x * 81, y); 
         y = 396;
        g.drawLine(0, y, x * 81, y); 
         y = 423;
        g.drawLine(0, y, x * 81, y); 
         y = 457;
        g.drawLine(0, y, x * 81, y); 
         y = 483;
        g.drawLine(0, y, x * 81, y); 
         y = 509;
        g.drawLine(0, y, x * 81, y); 
         y = 535;
        g.drawLine(0, y, x * 81, y); 
         y = 603;
        g.drawLine(0, y, x * 81, y);
         y = 631;
        g.drawLine(0, y, x * 81, y);
         y = 267;
        g.drawLine(0, y, x * 81, y);
        g.setColor(Color.WHITE);
        g.fillRect(0, 269,6*81, 292-269);
    }
        
    // returns the amount of push the drawString must be moved to the lest so all the text appears in the middle of the area given
    private int getWidthModifier(String str, Graphics g)
    {
       int temp = (81 -(g.getFontMetrics().stringWidth(str)))/2;
       if(temp > -1)
            return temp;
       else
            return 0;
    }
    // draws the name of player
    public void drawName(String name, int playerNum, Graphics g)
    {
        //a rectable of white is drawn each time to "erase" before the new information id drawn
        // this was so the the text doesn't build and look bad ( for aesthetics)
        g.setColor(Color.WHITE);
        g.fillRect(playerNum * 81 + 5, 2, 81 - 6, 26);
        g.setColor(Color.BLACK);
        // draws the information in ther correct location
        g.drawString(name , playerNum * 81 + getWidthModifier(name, g), 2+26 - 9);
        repaint();
    }
    //**************************************************************************************************************************************************
    // draws the point value in the corrent spot for the corresponding point value                                                                    //
    // all other non commented method are the same except for the Y values                                                                            //    
    //**************************************************************************************************************************************************
    public void drawAces(int score, int playerNum, Graphics g)
    {
        //a rectable of white is drawn each time to "erase" before the new information id drawn
        // this was so the the text doesn't build and look bad ( for aesthetics)
        g.setColor(Color.WHITE);
        g.fillRect(playerNum * 81 + 5 , 28, 81 - 6, 24);
        // set the color back to black 
        g.setColor(Color.BLACK);
        // draw the information
        g.drawString(getString(score) ,playerNum * 81 + getWidthModifier(getString(score), g), 54- 7);
    }
    public void drawTwos(int score, int playerNum, Graphics g)
    {
        //a rectable of white is drawn each time to "erase" before the new information id drawn
        // this was so the the text doesn't build and look bad ( for aesthetics)
        g.setColor(Color.WHITE);
        g.fillRect(playerNum * 81 + 5, 57, 81 - 6, 26);
        g.setColor(Color.BLACK);
        g.drawString(getString(score), playerNum * 81 + getWidthModifier(getString(score), g) , 57 + 24 - 7 );
    }
    public void drawThrees(int score, int playerNum, Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(playerNum * 81 + 5, 84, 81 - 6, 25);
        g.setColor(Color.BLACK);
        g.drawString(getString(score) , playerNum * 81  + getWidthModifier(getString(score), g), 84+25 - 7);
    }
    public void drawFours(int score, int playerNum, Graphics g)
    {  
        //a rectable of white is drawn each time to "erase" before the new information id drawn
        // this was so the the text doesn't build and look bad ( for aesthetics)
        g.setColor(Color.WHITE);
        g.fillRect(playerNum * 81 + 5, 109, 81 - 6, 24);
        g.setColor(Color.BLACK);
        g.drawString(getString(score) ,  playerNum * 81 + getWidthModifier(getString(score), g),  108 + 24- 7);
    }
    public void drawFives(int score, int playerNum, Graphics g)
    {   
        //a rectable of white is drawn each time to "erase" before the new information id drawn
        // this was so the the text doesn't build and look bad ( for aesthetics)
        g.setColor(Color.WHITE);
        g.fillRect(playerNum * 81 + 5, 135, 81 - 6, 25);
        g.setColor(Color.BLACK);
        g.drawString(getString(score) ,playerNum* 81 + getWidthModifier(getString(score), g), 134+ 25- 7);
    }
    public void drawSixes(int score, int playerNum, Graphics g)
    {   
        //a rectable of white is drawn each time to "erase" before the new information id drawn
        // this was so the the text doesn't build and look bad ( for aesthetics)
        g.setColor(Color.WHITE);
        g.fillRect(playerNum * 81 + 5, 160, 81 - 6, 24);
         g.setColor(Color.BLACK);
        g.drawString(getString(score) , playerNum* 81 + getWidthModifier(getString(score), g), 160 + 24- 7);
    }
    public void upperTotalScore(int score, int playerNum, Graphics g)
    {
        //a rectable of white is drawn each time to "erase" before the new information id drawn
        // this was so the the text doesn't build and look bad ( for aesthetics)
        g.setColor(Color.WHITE);
        g.fillRect(playerNum * 81 + 5, 186, 81 - 6, 26);
         g.setColor(Color.BLACK);
        g.drawString(getString(score), playerNum* 81 + getWidthModifier(getString(score), g),  186 + 26 - 7);
    }
    public void drawBonus(int score, int playerNum, Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(playerNum * 81 + 5, 213, 81 - 6, 26);
        
        g.setColor(Color.BLACK);
        g.drawString(getString(score), playerNum * 81 + getWidthModifier(getString(score), g), 213 + 26- 7);
    }
    public void upperTotal(int score, int playerNum, Graphics g)
    {
        // this method has double because there are 2 sports this information goes
        g.setColor(Color.WHITE);
        g.fillRect(playerNum * 81 + 5, 241, 81 - 6, 26);
         g.setColor(Color.BLACK);
        g.drawString(getString(score),playerNum* 81 + getWidthModifier(getString(score), g), 241 + 26- 7 );
        
        g.setColor(Color.WHITE);
        g.fillRect(playerNum * 81 + 5, 569, 81 - 6, 26);
        g.setColor(Color.BLACK);
        g.drawString(getString(score), playerNum* 81 + getWidthModifier(getString(score), g), 569 + 26- 7 );
    }
    public void draw3Kind(int score, int playerNum, Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(playerNum * 81 + 5, 293, 81 - 6, 26);
         g.setColor(Color.BLACK);
        g.drawString(getString(score), playerNum* 81 + getWidthModifier(getString(score), g), 294 + 20 - 3);
    }
    public void draw4Kind(int score, int playerNum, Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(playerNum * 81 + 5, 319, 81 - 6, 24);
         g.setColor(Color.BLACK);
        g.drawString(getString(score), playerNum * 81 + getWidthModifier(getString(score), g), 319 + 20 - 3);
    }
    public void drawFullHouse(int score, int playerNum, Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(playerNum * 81 + 5, 344, 81 - 6, 26);
         g.setColor(Color.BLACK);
        g.drawString(getString(score),playerNum* 81 + getWidthModifier(getString(score), g), 344 + 26 - 7);
    }
    public void drawSmStraight(int score, int playerNum, Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(playerNum * 81 + 5, 370, 81 - 6, 25);
         g.setColor(Color.BLACK);
        g.drawString(getString(score), playerNum* 81 + getWidthModifier(getString(score), g), 370 + 25- 7);
    }
    public void drawLgStraight(int score, int playerNum, Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(playerNum * 81 + 5, 396, 81 - 6, 26);
         g.setColor(Color.BLACK);
        g.drawString(getString(score), playerNum* 81 + getWidthModifier(getString(score), g), 396 + 26- 7 );
    }
    public void drawYahtzee(int score, int playerNum, Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(playerNum * 81 + 5, 423, 81 - 6, 26);
         g.setColor(Color.BLACK);
        g.drawString(getString(score), playerNum* 81 + getWidthModifier(getString(score), g), 423 + 26- 7);
    }
    public void drawChance(int score, int playerNum, Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(playerNum * 81 + 5, 457, 81 - 6, 26);
         g.setColor(Color.BLACK);
        g.drawString(getString(score),playerNum* 81 + getWidthModifier(getString(score), g), 457 + 26- 7 );
        
    }
    public void drawExtraYahtzee(int score, int playerNum, Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(playerNum * 81 + 5, 483, 81 - 6, 26);
         g.setColor(Color.BLACK);
        g.drawString(getString(score), playerNum* 81 + getWidthModifier(getString(score), g), 483 +26 - 7);
    }
    public void drawExtraYahtzeeScore(int score, int playerNum, Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(playerNum * 81 + 5, 509, 81 - 6, 26);
         g.setColor(Color.BLACK);
        g.drawString(getString(score),playerNum* 81 + getWidthModifier(getString(score), g), 509 + 26- 7);
    }
    public void drawLowerTotal ( int score, int playerNum, Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(playerNum * 81 + 5, 535, 81 - 6, 26);
        g.setColor(Color.BLACK);
        g.drawString(getString(score), playerNum* 81 + getWidthModifier(getString(score), g), 535 +26 - 7);
    }
    public void drawGrandTotal ( int score, int playerNum, Graphics g)
    {   
        g.setColor(Color.WHITE);
        g.fillRect(playerNum * 81 + 5, 603, 81 - 6, 26);
        g.setColor(Color.BLACK);
        g.drawString(getString(score), playerNum * 81 + getWidthModifier(getString(score), g), 603 + 30- 7 );
    }
    public String getString(int score)
    {
        String tempString = Integer.toString(score);
        return tempString;
    }
    // recieves the new array of scores and repaints the new array
    public void myUpdate(int[][] passedScores)
    {
        // passes the array by reference
        scores = passedScores;
        // calls repaint;
        repaint();
    }
}
