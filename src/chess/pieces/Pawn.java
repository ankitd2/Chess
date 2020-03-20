package chess.pieces;

import chess.Board;

public class Pawn extends Piece{

    private boolean isFirstMove;

    /**
     * Checks to see if the move for the current Pawn piece would be the first move for the piece. The first move for
     * any pawn would allow it to move 2 spaces instead of 1.
     * @return true if it would be the first move;
     *         false if the pawn has already moved
     */
    public boolean getIsFirstMove() {
        return isFirstMove;
    }

    /**
     * Sets the isFirstMove value of Pawn to the given firstMove value
     * @param firstMove true if it would be the first move for the pawn;
     *                  false if the pawn has already moved
     */
    public void setFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
    }

    /**
     * Constructor for a Black Pawn piece at a given row and column, sets isFirstMove to true
     * @param rowCoord
     * @param colCoord
     */
    public Pawn(int rowCoord, int colCoord) {
        super(rowCoord, colCoord);
        isFirstMove = true;
    }

    /**
     * Constructor for a Pawn piece at a given row and col, with a given color isBlack and with isFirstMove being true
     * @param rowCoord
     * @param colCoord
     * @param isBlack
     */
    public Pawn(int rowCoord, int colCoord, boolean isBlack){
        super(rowCoord, colCoord, isBlack);
        isFirstMove = true;
    }

    /**
     * Checks if the move to the newRow, newCol on the board is a valid move for the current Pawn piece.
     * @param board current board
     * @param newRow new row coordinate to be moved to
     * @param newCol new column coordinate to be moved to
     * @return
     */
    @Override
    public boolean canMove(Board board, int newRow, int newCol) {
        int currentRow = this.getRowCoord();
        int currentCol = this.getColCoord();
        Piece newPiece = board.getPiece(newRow, newCol);
        if(newPiece != null && (newPiece.isBlack() == this.isBlack())) {
            return false;
        }
        if (Math.abs(newRow - currentRow) > 2){
            return false;
        }
        if (currentCol == newCol){
            if (Math.abs(newRow - currentRow) == 2){
                if (this.getIsFirstMove()){
                    this.setFirstMove(false);
                    return true;
                } else {
                    return false;
                }
            } else {
                if(newPiece != null){
                    return false;
                }
                return true;
            }
        } else if (Math.abs(newRow - currentRow) == 1){
            if(Math.abs(newCol - currentCol) != 1){
                return false;
            } else {
                if((newPiece != null) && (newPiece.isBlack() != this.isBlack())) {
                    if(this.getIsFirstMove()){
                        this.setFirstMove(false);
                    }
                    return true;
                }
            }
        }
            return false;
    }
}
