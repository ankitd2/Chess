package chess;

import chess.pieces.*;

/**
 * Board Class to set up and maintain Chessboard, game states, current layout of pieces, and evaluate game logic
 */
public class Board {

    private int rows;
    private int cols;
    private Piece[][] pieces;
    private boolean isWhiteCheck;
    private boolean isBlackCheck;
    private boolean isWhiteCheckmate;
    private boolean isBlackCheckmate;
    private int[] whitePosition;
    private int[] blackPosition;

    /**
     * Default constructor for a Board with 8 rows and 8 columns and Pieces board initialized to default chessboard
     */
    public Board(){
        rows = 8;
        cols = 8;
        pieces = new Piece[rows][cols];
        isWhiteCheck = false;
        isBlackCheck = false;
        isWhiteCheckmate = false;
        isBlackCheckmate = false;
        this.whitePosition = new int[2];
        this.blackPosition = new int[2];
        for(int rowCount = 0; rowCount < rows; rowCount++){
            for(int colCount = 0; colCount < cols; colCount++){
                pieces[rowCount][colCount] = null;
            }
        }
        initializeBoard(rows, cols, false);
    }

    /**
     * Constructs a Board with the option to create a custom board with custom Chess pieces
     * @param isCustom true if Custom Board desired
     *                 false if Standard Board desired
     */
    public Board(boolean isCustom){
        rows = 8;
        cols = 8;
        pieces = new Piece[rows][cols];
        isWhiteCheck = false;
        isBlackCheck = false;
        isWhiteCheckmate = false;
        isBlackCheckmate = false;
        this.whitePosition = new int[2];
        this.blackPosition = new int[2];
        for(int rowCount = 0; rowCount < rows; rowCount++){
            for(int colCount = 0; colCount < cols; colCount++){
                pieces[rowCount][colCount] = null;
            }
        }
        initializeBoard(rows, cols, isCustom);
    }
    /**
     * Constructs a board with numRows rows and numColumns columns and chessboard initialized
     * @param numRows number of rows
     * @param numColumns number of columns
     * @param isCustom true if Custom Board desired
     *                 false if Standard Board desired
     */
    public Board(int numRows, int numColumns, boolean isCustom){
        rows = numRows;
        cols = numColumns;
        pieces = new Piece[rows][cols];
        isWhiteCheck = false;
        isBlackCheck = false;
        isWhiteCheckmate = false;
        isBlackCheckmate = false;
        this.whitePosition = new int[2];
        this.blackPosition = new int[2];
        initializeBoard(numRows, numColumns, isCustom);
    }

    /**
     * Getter for the Chessboard array stored in a two-dimensional array
     * @return Piece[][] pieces array
     */
    public Piece[][] getPieces() {
        return pieces;
    }

    /**
     *  Sets up board with Pawns, Rooks, Knights, Bishops, Kings, and Queens in their default initial positions according
     *  to rules of Chess. If a custom Board is created, then two pawn pieces will be substituted for one Tiger and one
     *  Orbit for both players.
     * @param rows number of rows
     * @param cols number of columns
     * @param isCustom if custom pieces must be added
     */
    private void initializeBoard(int rows, int cols, boolean isCustom){
        //initialize Pawns
        for (int rowInitializer = 0; rowInitializer < rows; rowInitializer++){
            for (int colInitializer = 0; colInitializer < cols; colInitializer++){
                pieces[rowInitializer][colInitializer] = null;
            }
        }
        for(int colCounter = 0; colCounter < cols; colCounter++){
            pieces[1][colCounter] = new Pawn(1, colCounter, true);
            pieces[rows-2][colCounter] = new Pawn(rows - 2, colCounter, false);
        }
        pieces[0][0] = new Rook(0,0, true);
        pieces[0][cols - 1] = new Rook(0, cols - 1, true);
        pieces[rows - 1][0] = new Rook(rows - 1,0, false);
        pieces[rows - 1][cols - 1] = new Rook(rows - 1, cols - 1, false);

        pieces[0][1] = new Knight(0, 1, true);
        pieces[0][cols - 2] = new Knight(0, cols - 2, true);
        pieces[rows - 1][1] = new Knight(rows - 1, 1,  false);
        pieces[rows - 1][cols - 2] = new Knight(rows - 1, cols - 2, false);

        pieces[0][2] = new Bishop(0, 2,  true);
        pieces[0][cols - 3] = new Bishop(0, cols - 3, true);
        pieces[rows - 1][2] = new Bishop(rows - 1, 2,  false);
        pieces[rows - 1][cols - 3] = new Bishop(rows - 1, cols - 3, false);

        pieces[0][3] = new Queen(0, 3,  true);
        pieces[rows - 1][3] = new Queen(rows - 1, 3,  false);

        pieces[0][4] = new King(0, 4, true);
        pieces[rows - 1][4] = new King(rows - 1, 4, false);
        this.whitePosition[0] = rows - 1;
        this.whitePosition[1] = 4;
        this.blackPosition[0] = 0;
        this.blackPosition[1] = 4;



        if(isCustom){
            pieces[1][0] = new Tiger(1, 0, true);
            pieces[rows - 2][0] = new Tiger(rows - 2 , 0, false);

            pieces[1][cols - 1] = new Orbit(1, cols - 1, true);
            pieces[rows - 2][cols - 1] = new Orbit(rows - 2, cols - 1, false);
        }

    }


    /**
     *
     * @return Number of rows in the board
     */
    public int getRows() {
        return rows;
    }

    /**
     *
     * @return Number of columns in the board
     */
    public int getCols() {
        return cols;
    }

    /**
     * Checks to see if the given spot on the board is empty or occupied
     * @param rowCoord  Coordinate of row on the board to be checked
     * @param colCoord  Coordinate of column on the board to be checked
     * @return True if the spot on the board is empty
     */
    public boolean isEmpty(int rowCoord, int colCoord){
        return pieces[rowCoord][colCoord] == null;
    }

    /**
     * Removes a piece from the given location on the board
     * @param rowCoord row coordinate of piece
     * @param colCoord column coordinate of piece
     */
    public void removePiece(int rowCoord, int colCoord){
        this.pieces[rowCoord][colCoord] = null;
    }

    /**
     * Sets a given location on the board to be the given Piece
     * @param rowCoord row coordinate of piece
     * @param colCoord column coordinate of piece
     * @param piece new piece that is to be set
     * @return true if the piece was set on the board;
     *         false otherwise;
     */
    public boolean setPiece(int rowCoord, int colCoord, Piece piece){
        if(rowCoord < 0 || colCoord < 0 || rowCoord >= rows || colCoord >= cols) {
            return false;
        }
        if(piece == null){
            pieces[rowCoord][colCoord] = null;
            return true;
        }
        pieces[rowCoord][colCoord] = piece;
        piece.setRowCoord(rowCoord);
        piece.setColCoord(colCoord);
        return true;
    }

    /**
     * Wipes and clears the board with all the pieces on the board set to null
     */
    public void clearBoard(){
        for (int rowIter = 0; rowIter < rows; rowIter++) {
            for (int colIter = 0; colIter < cols; colIter++) {
                pieces[rowIter][colIter] = null;
            }

        }
    }
    /**
     * Returns the piece on the board that is located at the given coordinates. If there is no piece there, returns null
     * @param rowCoord row coordinate on board
     * @param colCoord column coordinate on board
     * @return Piece the piece that is on the board at the coordinate;
     *         null if invalid coordinates or no piece on the board
     */
    public Piece getPiece(int rowCoord, int colCoord){
        if(rowCoord < 0 || colCoord < 0 || rowCoord >= rows || colCoord >= cols) {
            return null;
        }
        return pieces[rowCoord][colCoord];
    }

    /**
     * Checks to see if there is a piece at the given rowCoord, colCoord and if there is checks to see if that piece can
     * be moved to the desired newRol, newCol coordinates. If the move is legal and valid, then the piece will be moved
     * and if an opposing players piece can be captured at the end location, it is captured.
     * @param rowCoord Starting  row coordinate
     * @param colCoord Starting column coordinate
     * @param newRow Ending row coordinate
     * @param newCol Ending column coordinate
     * @param isBlack
     * @return true if the piece can be moved to new location;
     *         false if invalid or illegal move
     */
    public boolean movePiece(int rowCoord, int colCoord, int newRow, int newCol, boolean isBlack){

        if(newRow < 0 || newCol < 0 || newRow >= rows || newCol >= cols){
            return false;
        }
        if(rowCoord == newRow && colCoord == newCol){
            return false;
        }
        Piece currentPiece = this.getPiece(rowCoord, colCoord);
        if (currentPiece == null || currentPiece.isBlack() != isBlack){
            return false;
        }
        if (currentPiece.canMove(this, newRow, newCol)){
            Piece oldPiece = this.getPiece(newRow, newCol);
            setPiece(newRow, newCol, currentPiece);
            setPiece(rowCoord, colCoord, null);
            boolean isChecked = getCheck(isBlack);
            boolean isOwnCheck = canCheckOwnKing(isBlack);
            if(isOwnCheck){
                setPiece(rowCoord, colCoord, currentPiece);
                setPiece(newRow, newCol, oldPiece);
                getKing(isBlack);
                return false;
            }
            if(isChecked && !isOwnCheck){
                setCheck(isBlack);
            }
            boolean isEnemyCheck = canCheckEnemyKing(currentPiece, isBlack);
            int[] enemyCoord = getKing(!isBlack);
            Piece enemyKing = getPiece(enemyCoord[0], enemyCoord[1]);
            if(isEnemyCheck){
                for (int rowMove = -1; rowMove < 2; rowMove++){
                    for (int colMove = -1; colMove < 2; colMove++) {
                        if(!enemyKing.canMove(this, enemyCoord[0] + rowMove, enemyCoord[1] + colMove)){
                            setCheckmate(!isBlack);
                        }
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sets whether a Player is in checkmate
     * @param isBlack color of player in checkmate
     */
    public void setCheckmate(boolean isBlack) {
        if(isBlack == true){
            isBlackCheckmate = true;
            isBlackCheck = true;
        } else {
            isWhiteCheckmate = true;
            isWhiteCheck = true;
        }
    }

    private boolean isWhiteCheck(){
        return this.isWhiteCheck;
    }

    private boolean isBlackCheck(){
        return this.isBlackCheck;
    }

    /**
     * Gets whether the isBlack player is under checkmate
     * @param isBlack color of player to look up checkmate
     * @return true if king under checkmate
     */
    public boolean getCheckmate(boolean isBlack){
        if(isBlack == true){
            return isBlackCheckmate;
        } else {
            return isWhiteCheckmate;
        }
    }

    /**
     * Gets the check status for the isBlack player
     * @param isBlack color of player to return check status
     * @return true if king is checked, false otherwise
     */
    public boolean getCheck(boolean isBlack) {
        if(isBlack){
            return isBlackCheck();
        } else {
            return isWhiteCheck();
        }
    }

    /**
     * sets the color isBlack to be under check if it previously was not, and sets the check status to false if the king
     * was previously under check
     * @param isBlack
     */
    public void setCheck(boolean isBlack){
        if(isBlack){
            isBlackCheck = !isBlackCheck;
        } else {
            isWhiteCheck = !isWhiteCheck;
        }
    }

    /**
     * This functions serves 2 functions: updates the positions of the two kings on the board,
     *  and gets the positions of the king for the specified color
     * @param isBlack color of desired King piece
     * @return int[] the coordinates of the King Piece in the format {rowCoord, colCoord}
     */
    public int[] getKing(boolean isBlack) {
        for (int rowIter = 0; rowIter < getRows(); rowIter++) {
            for (int colIter = 0; colIter < getCols(); colIter++) {
                Piece currentPiece = getPiece(rowIter, colIter);
                if(currentPiece instanceof King){
                    if(currentPiece.isBlack() == true){
                        blackPosition[0] = currentPiece.getRowCoord();
                        blackPosition[1] = currentPiece.getColCoord();
                    } else {
                        whitePosition[0] = currentPiece.getRowCoord();
                        whitePosition[1] = currentPiece.getColCoord();
                    }
                }
            }

        }
        if(isBlack){
            return blackPosition;
        } else {
            return whitePosition;
        }
    }

    /**
     * Checks to see if a given move will cause the player's own king to be exposed to check, or the king continues
     * to be in check position after attempted move
     * @param isBlack color of the Player moving
     * @return true if the move would cause your own King to be under check
     *         false if move is safe
     */
    public boolean canCheckOwnKing(boolean isBlack){
        int[] kingPosition = getKing(isBlack);
        for(int rowIteration = 0; rowIteration < getRows(); rowIteration++){
            for(int colIteration = 0; colIteration < getCols(); colIteration++){
                Piece currentPiece = this.getPiece(rowIteration, colIteration);
                if(currentPiece != null && currentPiece.canMove(this, kingPosition[0], kingPosition[1])){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks whether the input piece will be able to place the opponent King under check after the attempted move
     * @param piece Piece on the board that is to be moved
     * @param isBlack color of the Player moving
     * @return true if the enemy King will be under check after the move
     */
    public boolean canCheckEnemyKing(Piece piece, boolean isBlack){
        int[] enemyKing = getKing(!isBlack);
        if(piece.canMove(this, enemyKing[0], enemyKing[1])){
            setCheck(!isBlack);
            return true;
        }
        return false;
    }

}
