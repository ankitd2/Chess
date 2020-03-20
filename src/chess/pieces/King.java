package chess.pieces;

import chess.Board;

public class King extends Piece {
    /**
     * King piece constructor at a given row and column
     * @param rowCoord
     * @param colCoord
     */
    public King(int rowCoord, int colCoord) {
        super(rowCoord, colCoord);
    }

    /**
     * Constructor for a King piece at a row and column with a color
     * @param rowCoord
     * @param colCoord
     * @param isBlack true if black piece;
     *                 false otherwise
     */
    public King(int rowCoord, int colCoord, boolean isBlack){
        super(rowCoord, colCoord, isBlack);
    }

    /**
     * Checks if the move to the newRow, newCol on the board is a valid move for the current King piece.
     * @param board current board
     * @param newRow new row coordinate to be moved to
     * @param newCol new column coordinate to be moved to
     * @return true if it is a valid move;
     *         false otherwise
     */
    @Override
    public boolean canMove(Board board, int newRow, int newCol) {
        int currentRow = this.getRowCoord();
        int currentCol = this.getColCoord();
        Piece newPiece = board.getPiece(newRow, newCol);
        if(newPiece != null && (newPiece.isBlack() == this.isBlack())) {
            return false;
        }

        if((Math.abs(currentCol - newCol) <= 1) &&  (Math.abs(currentRow-newRow) <= 1)){
            return true;
        }
        return false;
    }
}
