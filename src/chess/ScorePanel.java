package chess;

import javax.swing.*;
import java.awt.*;

/**
 * JPanel class used to implenent Score and Player tracking
 */
public class ScorePanel extends JPanel {
    private int whiteScore = 0;
    private int blackScore = 0;
    private JLabel playerLabel;
    private JLabel scoreLabel;
    public boolean isBlackTurn;

    /**
     * Constructor to create a ScorePanel with the first turn going to isBlackTurn. This panel will display the
     * players, the player's turn, and both player's scores
     * @param isBlackTurn
     */
    public ScorePanel(boolean isBlackTurn){
        this.isBlackTurn = isBlackTurn;
        if(isBlackTurn){
            playerLabel = new JLabel("Black Turn    ");
        } else {
            playerLabel = new JLabel("White Turn    ");
        }
        playerLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        scoreLabel = new JLabel();
        updateScore();
        scoreLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        this.setLayout(new FlowLayout());
        add(playerLabel);
        add(scoreLabel);
        this.setVisible(true);

    }

    /**
     * returns the Black player's score
     * @return int score of Black player
     */
    public int getBlackScore(){
        return blackScore;
    }

    /**
     * returns the White player's score
     * @return int score of White player
     */
    public int getWhiteScore(){
        return whiteScore;
    }

    /**
     * Adds a win for the Black player
     */
    public void addBlackWin(){
        blackScore += 1;
        updateScore();
        isBlackTurn = false;
    }

    /**
     * Adds a win for the White player
     */
    public void addWhiteWin(){
        whiteScore += 1;
        updateScore();
        isBlackTurn = false;
    }

    /**
     * updates the ScorePanel to display the latest scores
     */
    public void updateScore(){
        scoreLabel.setText("    Black: " + getBlackScore() + " | White: " + getWhiteScore());
    }

    /**
     * updates the ScorePanel to display which player's turn it is. Also sets the current isBlackTurn to the parameter
     * @param isBlackTurn the current player's turn
     */
    public void updateTurn(boolean isBlackTurn){
        this.isBlackTurn = isBlackTurn;
        if(isBlackTurn){
            playerLabel.setText("Black Turn    ");
        } else {
            playerLabel.setText("White Turn    ");
        }
    }

    /**
     * Changes the player's turns and displays updated turn
     */
    public void changePlayers(){
        this.isBlackTurn = !isBlackTurn;
        updateTurn(this.isBlackTurn);
    }

    /**
     * resets the ScorePanel turn display, but keeps the accumulated score
     */
    public void reset() {
        updateTurn(this.isBlackTurn);

    }
}
