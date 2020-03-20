package chess;

import chess.pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class BoardScreen extends JPanel {
    private int rows;
    private int columns;
    private JButton[][] squares;
    private ScorePanel scorePanel;
    private Board board;
    private final String IMAGE_PATH = "src/chess/PieceImages/";
    public Map<String, ImageIcon> pieceIconMap = new HashMap<>();
    public boolean isBlackTurn;
    public Map<String, int[]> clickLog;
    public Stack<int[]> moveLog;
    public Stack<Piece> capturedPieces;
    public Piece capturedPiece;



    /**
     * The dark squares of the board are set to a dark green color
     */
    public Color BLACK = new Color(111, 143, 114);

    /**
     * the light squares of the board are set to a light green color
     */
    public Color WHITE = new Color(173, 189, 143);


    /**
     * Initializes and sets up the Chessboard JPanel for the GUI
     * @param panel ScorePanel the panel tracking turns and scores for the game
     * @param isCustom true for Custom Board
     * @param isBlackTurn true if Black turn first, false if White turn first
     */
    public BoardScreen(ScorePanel panel, boolean isCustom, boolean isBlackTurn){
        rows = 8;
        columns = 8;
        board = new Board(rows, columns, isCustom);
        squares = new JButton[rows][columns];
        clickLog = new HashMap<>();
        moveLog = new Stack<>();
        this.isBlackTurn = isBlackTurn;
        scorePanel = panel;
        capturedPiece = null;
        capturedPieces = new Stack<>();
        importPieces();

        for (int rowNum = 0; rowNum < rows; rowNum++) {
            for (int colNum = 0; colNum < columns; colNum++) {
                if ((colNum + rowNum)% 2 == 0) {
                    JButton btn = new JButton();
                    paintButton(BLACK, btn);
                    add(btn);
                    squares[rowNum][colNum] = btn;

                } else {
                    JButton btn = new JButton();
                    paintButton(WHITE, btn);
                    add(btn);
                    squares[rowNum][colNum] = btn;
                }
            }
        }
        setLayout(new GridLayout(8, 8));
        setVisible(true);
        initializeBoard();

    }

    /**
     * This is used to setup the display for the squares of the chess board. The JButton[][] is set with alternating
     * colors of dark green and light green and the background color of the button is set to display the square tile.
     * @param color the desired color of the button
     * @param button JButton which will have color changed
     */
    private void paintButton(Color color, JButton button) {
        button.setBackground(color);
        button.setBorderPainted(false);
        button.setOpaque(true);
    }

    /**
     * Method to setup the image pieces, display the pieces on the board, and setup action listeners to each square of
     * the board
     */
    public void initializeBoard() {
        showBoard();
        addBoardListeners();
    }

    /**
     * Adds action listeners to each square of the board to keep track of user input and what tile is being pressed
     * and determine what piece is at the position
     */
    public void addBoardListeners() {
        for (int rowCoord = 0; rowCoord < this.rows; rowCoord++) {
            for (int colCoord = 0; colCoord < this.columns; colCoord++) {
                int currentRowCoord = rowCoord;
                int currentColCoord = colCoord;
                this.squares[rowCoord][colCoord].addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        int[] clickPosition = {currentRowCoord, currentColCoord};
                        if(clickLog.get("start") != null){
                            int[] startPosition = clickLog.get("start");
                            if((currentRowCoord == startPosition[0] && currentColCoord == startPosition[1])){
                                paintButton(getColor(clickPosition), squares[currentRowCoord][currentColCoord]);
                                JOptionPane.showMessageDialog(null, "Error: Please click on the square" +
                                                " you would like to move to");
                                clickLog.clear();
                                return;
                            }
                            Color originalColor = getColor(startPosition);
                            paintButton(originalColor, squares[startPosition[0]][startPosition[1]]);
                            movePiece(startPosition[0], startPosition[1], currentRowCoord, currentColCoord);
                            moveLog.push(startPosition);
                            moveLog.push(clickPosition);
                            changePlayer();
                            clickLog.clear();
                        } else {
                            Color originalColor = getColor(clickPosition);
                            paintButton(Color.YELLOW, squares[currentRowCoord][currentColCoord]);
                            if(board.getPiece(currentRowCoord, currentColCoord) == null
                                    || board.getPiece(currentRowCoord, currentColCoord).isBlack() != isBlackTurn){
                                paintButton(originalColor, squares[currentRowCoord][currentColCoord]);
                                JOptionPane.showMessageDialog(null, "Error: Please click on the piece you would like" +
                                        " to move");
                                return;
                            }
                            clickLog.put("start", clickPosition);
                        }
                    }
                });
            }
        }
    }

    /**
     * Returns the custom Color either black or white that a square on the chess board is
     * @param startPosition int[] representing the row and column of the square
     * @return Color returns the shade of the square at the startPosition
     */
    public Color getColor(int[] startPosition) {
        boolean isDark = ((startPosition[0] + startPosition[1]) % 2) == 0;
        Color originalColor = WHITE;
        if(isDark){
            originalColor = BLACK;
        }
        return originalColor;
    }

    /**
     * Changes the player's turn and updates the panel to reflect the proper information
     */
    private void changePlayer() {
        isBlackTurn = !isBlackTurn;
        scorePanel.changePlayers();
    }

    /**
     * Main logic for moving a piece from one square to the other. JButtons from the squares are set up to receive
     * input locations, so actionListeners parse input and once a valid instruction is inputted, calls movePiece to
     * determine if it is a legal Chess move. This function will use the standard Board and Chess logic to determine
     * if the attempted move is allowed. Also determines what will happen to the board after the move it is successful.
     * If the game will end due to a checkmate, then a new game is started and scores updated. If the game loop continues
     * but an illegal move is attempted, then the appropriate error dialog will be displayed and there will be no changes
     * to the board
     * @param startRow starting row
     * @param startCol starting column
     * @param endRow ending row
     * @param endCol ending column
     */
    private void movePiece(int startRow, int startCol, int endRow, int endCol) {
        Piece currentPiece = board.getPiece(endRow, endCol);
        boolean canPieceMove = board.movePiece(startRow, startCol, endRow, endCol, isBlackTurn);
        if(canPieceMove == false){
            JOptionPane.showMessageDialog(null, "Error: This move is not possible, try again");
            changePlayer();
            return;
        }

        if(board.getCheck(true) || board.getCheck(false)){
            if(board.getCheckmate(true) || board.getCheckmate(false)){
                if(board.getCheckmate(true)){
                    JOptionPane.showMessageDialog(null, "Black is under Checkmate, White wins");
                    scorePanel.addWhiteWin();
                    startNewGame(false, true);
                } else {
                    JOptionPane.showMessageDialog(null, "White is under Checkmate, Black wins");
                    scorePanel.addBlackWin();
                    startNewGame(false, false);
                }
            } else {
                if (board.getCheck(true)) {
                    JOptionPane.showMessageDialog(null, "Black is under Check!");
                } else {
                    JOptionPane.showMessageDialog(null, "White is under Check!");
                }
            }
        }

        capturedPiece = currentPiece;
        capturedPieces.push(currentPiece);
        showBoard();
    }

    /**
     * Sets up the Map of ImageIcons which represent the pictures of the pieces that are to be displayed on the screen.
     * The images are imported as new ImageIcons and put into the map where the key is name of the piece ex "white_rook"
     * and the value is the ImageIcon. The JButtons have their icons set to these images if a piece is present at that
     * square
     */
    public void importPieces(){
        pieceIconMap.put("white_rook", new ImageIcon(IMAGE_PATH + "white_rook.png"));
        pieceIconMap.put("black_rook", new ImageIcon(IMAGE_PATH + "black_rook.png"));
        pieceIconMap.put("white_pawn", new ImageIcon(IMAGE_PATH + "white_pawn.png"));
        pieceIconMap.put("black_pawn", new ImageIcon(IMAGE_PATH + "black_pawn.png"));
        pieceIconMap.put("white_bishop", new ImageIcon(IMAGE_PATH + "white_bishop.png"));
        pieceIconMap.put("black_bishop", new ImageIcon(IMAGE_PATH + "black_bishop.png"));
        pieceIconMap.put("white_knight", new ImageIcon(IMAGE_PATH + "white_knight.png"));
        pieceIconMap.put("black_knight", new ImageIcon(IMAGE_PATH + "black_knight.png"));
        pieceIconMap.put("white_king", new ImageIcon(IMAGE_PATH + "white_king.png"));
        pieceIconMap.put("black_king", new ImageIcon(IMAGE_PATH + "black_king.png"));
        pieceIconMap.put("white_queen", new ImageIcon(IMAGE_PATH + "white_queen.png"));
        pieceIconMap.put("black_queen", new ImageIcon(IMAGE_PATH + "black_queen.png"));
        pieceIconMap.put("white_orbit", new ImageIcon(IMAGE_PATH + "white_orbit.png"));
        pieceIconMap.put("black_orbit", new ImageIcon(IMAGE_PATH + "black_orbit.png"));
        pieceIconMap.put("white_tiger", new ImageIcon(IMAGE_PATH + "white_tiger.png"));
        pieceIconMap.put("black_tiger", new ImageIcon(IMAGE_PATH + "black_tiger.png"));

    }

    /**
     * Displays the pieces on the board. This method will update the chess board with the visuals of the pieces that it
     * receives from the pieceIconMap, and if a piece is at a particular square, the JButton at that square is updated
     * to show the icon with that piece.
     */
    public void showBoard() {
        for (int rowCoord = 0; rowCoord < rows; rowCoord++) {
            for (int colCoord = 0; colCoord < columns; colCoord++) {
                Piece currentPiece = board.getPiece(rowCoord, colCoord);
                if(currentPiece instanceof Pawn){
                    if(currentPiece.isBlack()){
                        squares[rowCoord][colCoord].setIcon(pieceIconMap.get("black_pawn"));
                    } else {
                        squares[rowCoord][colCoord].setIcon(pieceIconMap.get("white_pawn"));
                    }
                } else if(currentPiece instanceof Rook){
                    if(currentPiece.isBlack()){
                        squares[rowCoord][colCoord].setIcon(pieceIconMap.get("black_rook"));
                    } else {
                        squares[rowCoord][colCoord].setIcon(pieceIconMap.get("white_rook"));
                    }
                } else if(currentPiece instanceof Knight){
                    if(currentPiece.isBlack()){
                        squares[rowCoord][colCoord].setIcon(pieceIconMap.get("black_knight"));
                    } else {
                        squares[rowCoord][colCoord].setIcon(pieceIconMap.get("white_knight"));
                    }
                } else if(currentPiece instanceof Bishop){
                    if(currentPiece.isBlack()){
                        squares[rowCoord][colCoord].setIcon(pieceIconMap.get("black_bishop"));
                    } else {
                        squares[rowCoord][colCoord].setIcon(pieceIconMap.get("white_bishop"));
                    }
                } else if(currentPiece instanceof King){
                    if(currentPiece.isBlack()){
                        squares[rowCoord][colCoord].setIcon(pieceIconMap.get("black_king"));
                    } else {
                        squares[rowCoord][colCoord].setIcon(pieceIconMap.get("white_king"));
                    }
                } else if(currentPiece instanceof Queen){
                    if(currentPiece.isBlack()){
                        squares[rowCoord][colCoord].setIcon(pieceIconMap.get("black_queen"));
                    } else {
                        squares[rowCoord][colCoord].setIcon(pieceIconMap.get("white_queen"));
                    }
                } else if(currentPiece instanceof Orbit){
                    if(currentPiece.isBlack()){
                        squares[rowCoord][colCoord].setIcon(pieceIconMap.get("black_orbit"));
                    } else {
                        squares[rowCoord][colCoord].setIcon(pieceIconMap.get("white_orbit"));
                    }
                } else if(currentPiece instanceof Tiger){
                    if(currentPiece.isBlack()){
                        squares[rowCoord][colCoord].setIcon(pieceIconMap.get("black_tiger"));
                    } else {
                        squares[rowCoord][colCoord].setIcon(pieceIconMap.get("white_tiger"));
                    }
                }
                else {
                    squares[rowCoord][colCoord].setIcon(null);
                }
            }
        }
    }

    /**
     * Starts a new game either standard or custom, and sets the starting player to isBlackTurn
     * @param isCustomGame true if Custom game, false if Standard game
     * @param isBlackTurn first player's turn
     */
    public void startNewGame(boolean isCustomGame, boolean isBlackTurn) {
        board = new Board(isCustomGame);
        this.isBlackTurn = isBlackTurn;
        scorePanel.updateTurn(this.isBlackTurn);
        showBoard();

    }

    /**
     * Used to undo the last recorded move. This method can be used multiple times consecutively to undo the last moves
     * for the Board, alternatives between sides and resets the turn to the original player if the undo button is called
     * after a player has used their turn. Called by the undo button, will replace the pieces' original positions and
     * display any captured pieces and restore board to the state it was in before the move.
     */
    public void undoMove() {
        if(moveLog.isEmpty()){
            JOptionPane.showMessageDialog(null, "Error: There is nothing to undo");
            return;
        }
        int[] newPosition = moveLog.pop();
        int[] startPosition = moveLog.pop();
        changePlayer();
        Piece currentPiece = board.getPiece(newPosition[0], newPosition[1]);
        board.setPiece(startPosition[0], startPosition[1], currentPiece);
        board.setPiece(newPosition[0], newPosition[1], null);
        if(currentPiece instanceof Pawn && (startPosition[0] == 1 || startPosition[0] == board.getRows() - 2)){
            ((Pawn)currentPiece).setFirstMove(true);
        }

        Piece lastCaptured = capturedPieces.pop();
        board.setPiece(newPosition[0], newPosition[1], lastCaptured);

        if(lastCaptured == null){
            squares[newPosition[0]][newPosition[1]].setIcon(null);
        }

        showBoard();
    }
}
