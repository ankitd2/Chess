package chess.pieces;

import chess.Board;

public class Rook extends Piece {
    /**
     * Constructor for a Rook piece at a given row and column
     * @param rowCoord
     * @param colCoord
     */
    public Rook(int rowCoord, int colCoord) {
        super(rowCoord, colCoord);
    }

    /**
     * Constructor for a Rook at a given row and column with a given color isBlack
     * @param rowCoord
     * @param colCoord
     * @param isBlack
     */
    public Rook(int rowCoord, int colCoord, boolean isBlack) {
        super(rowCoord, colCoord, isBlack);
    }

    /**
     * Checks if the move to the newRow, newCol on the board is a valid move for the current Rook piece.
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
        if (newPiece != null && (newPiece.isBlack() == this.isBlack())) {
            return false;
        }
        if (checkRookMove(board, newRow, newCol, currentRow, currentCol)) {
            return true;
        }
        return false;
    }
}
