package chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Display extends JPanel {
    private JPanel rootPanel;
    private BoardScreen boardScr;
    private boolean isBlackTurn;

    public Display() {

        isBlackTurn = false;
        rootPanel = new JPanel();
        rootPanel.setLayout(new BorderLayout());
        this.add(rootPanel);

        ScorePanel scorePanel = new ScorePanel(isBlackTurn);
        rootPanel.add(scorePanel, BorderLayout.PAGE_START);

        JPanel commandPanel = new JPanel();
        commandPanel.setLayout(new FlowLayout());
        rootPanel.add(commandPanel, BorderLayout.PAGE_END);

        // creates a start button with a green text color
        JButton startBtn = new JButton("Start");
        startBtn.setFont(new Font("Calibri", Font.PLAIN, 16));
        startBtn.setForeground(new Color(0, 204, 0));
        startBtn.setPreferredSize(new Dimension(100,35));
        commandPanel.add(startBtn);

//        creates a reset button with a red text color
        JButton restartBtn = new JButton("Restart");
        restartBtn.setFont(new Font("Calibri", Font.PLAIN, 16));
        restartBtn.setPreferredSize(new Dimension(100,35));
        commandPanel.add(restartBtn);

        JButton quitBtn = new JButton("Quit");
        quitBtn.setFont(new Font("Calibri", Font.PLAIN, 16));
        quitBtn.setForeground(new Color(255, 0, 0));
        quitBtn.setPreferredSize(new Dimension(100,35));
        commandPanel.add(quitBtn);

        JButton undoBtn = new JButton("Undo");
        undoBtn.setFont(new Font("Calibri", Font.PLAIN, 16));
        undoBtn.setForeground(new Color(255, 166, 0));
        undoBtn.setPreferredSize(new Dimension(100,35));
        commandPanel.add(undoBtn);

        JButton helpBtn = new JButton("Help");
        helpBtn.setFont(new Font("Calibri", Font.PLAIN, 16));
        helpBtn.setForeground(new Color(0, 130, 255));
        helpBtn.setPreferredSize(new Dimension(100,35));
        commandPanel.add(helpBtn);

        boardScr = new BoardScreen(scorePanel, false, isBlackTurn);
        rootPanel.add(boardScr, BorderLayout.CENTER);

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Object[] options = {"Standard Game", "Custom Game", "Cancel"};
                int gameType = JOptionPane.showOptionDialog(null, "What type of game would you like to play?", "Start Game",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
                if(gameType == JOptionPane.YES_OPTION){
                    boardScr.startNewGame(false, false);
                    isBlackTurn = false;
                    scorePanel.reset();
                } else if (gameType == JOptionPane.NO_OPTION){
                    boardScr.startNewGame(true, false);
                    isBlackTurn = false;
                    scorePanel.reset();
                }
            }
        });
        quitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Object[] options = {"White", "Black", "Cancel"};
                int quitOption = JOptionPane.showOptionDialog(null, "Which Player would like to quit?", "Quit Game",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
                if(quitOption == JOptionPane.YES_OPTION){
                    int quitConfirm = JOptionPane.showConfirmDialog(null, "Are You Sure White wants to quit?");
                    if (quitConfirm == JOptionPane.YES_OPTION){
                        scorePanel.addBlackWin();
                        boardScr.startNewGame(false, false);
                        isBlackTurn = false;
                    }
                } else if (quitOption == JOptionPane.NO_OPTION){
                    int quitConfirm = JOptionPane.showConfirmDialog(null, "Are You Sure Black wants to quit?");
                    if (quitConfirm == JOptionPane.YES_OPTION){
                        scorePanel.addWhiteWin();
                        boardScr.startNewGame(false, true);
                        isBlackTurn = true;
                    }
                }
            }
        });
        undoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int undoOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to undo the last move?");
                if(undoOption == JOptionPane.YES_OPTION){
                    boardScr.undoMove();
                }
            }
        });
        restartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int restartOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to restart the game?");
                if(restartOption == JOptionPane.YES_OPTION){
                    boardScr.startNewGame(false, false);
                    isBlackTurn = false;
                }
            }
        });
        helpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(null ,"Click on the square of the piece you" +
                        " would like to move, then the square you would like to move it to.");
            }
        });

    }

    private Container getRootPanel() {
        return rootPanel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chess Display");
        Display disp = new Display();
        frame.setContentPane(disp.getRootPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}