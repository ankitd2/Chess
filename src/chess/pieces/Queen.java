package chess.pieces;

import chess.Board;

public class Queen extends Piece {
    /**
     * Queen piece constructor at a given row and column
     * @param rowCoord
     * @param colCoord
     */
    public Queen(int rowCoord, int colCoord) {
        super(rowCoord, colCoord);
    }

    /**
     * Constructor for a Queen piece constructor at a given row, column and color
     * @param rowCoord
     * @param colCoord
     * @param isBlack
     */
    public Queen(int rowCoord, int colCoord, boolean isBlack){
        super(rowCoord, colCoord, isBlack);
    }

    /**
     * Checks if the move to the newRow, newCol on the board is a valid move for the current Queen piece.
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
        if(checkBishopMove(board, newRow, newCol, currentRow, currentCol) || checkRookMove(board, newRow, newCol, currentRow, currentCol)){
            return true;
        }
        return false;
    }
}
