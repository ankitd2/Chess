package chess.pieces;

import chess.Board;

/**
 * The Tiger class is for the Tiger custom chess piece. Tiger pieces can  jump to any space that is 2 spaces away
 * horizontally or vertically. They can also move to any adjacent tile if there is an enemy piece there to capture it.
 * If there is no piece at the chosen adjacent tile, then the Tiger cannot move there.
 */
public class Tiger extends Piece {

    /**
     * Default constructor for a Tiger at a row and column. Sets the default color to black
     *
     * @param rowCoord
     * @param colCoord
     */
    public Tiger(int rowCoord, int colCoord) {
        super(rowCoord, colCoord);
    }

    /**
     * Constructor for a Tiger at a given row and column, and the given color of the piece. A true value for isBlack
     * represents a black piece and a false value represents a white piece.
     *
     * @param rowCoord
     * @param colCoord
     * @param isBlack
     */
    public Tiger(int rowCoord, int colCoord, boolean isBlack) {
        super(rowCoord, colCoord, isBlack);
    }

    /**
     * Abstract method implemented by the Tiger class with the logic for Tiger pieces.
     * Tiger pieces can move to spaces that are a distance of 2 away from the current square in the horizontal
     * or vertical directions. The tiger can also move to an adjacent square (including adjacent diagonal tile) if
     * there is an enemy piece there to be captured.
     *
     * @param board  current board
     * @param newRow new row coordinate to be moved to
     * @param newCol new column coordinate to be moved to
     * @return true if the piece can move be moved to the new location;
     * false otherwise
     */
    @Override
    public boolean canMove(Board board, int newRow, int newCol) {
        int currentRow = this.getRowCoord();
        int currentCol = this.getColCoord();
        Piece newPiece = board.getPiece(newRow, newCol);
        if(newPiece != null && (newPiece.isBlack() == this.isBlack())) {
            return false;
        }
        if((Math.abs(currentCol - newCol) == 2 && Math.abs(currentRow - newRow) == 0)
                || ((Math.abs(currentCol - newCol) == 0 && Math.abs(currentRow - newRow) == 2))){
            return true;
        }
        if((Math.abs(currentCol - newCol) <= 1) && (Math.abs(currentRow - newRow) <= 1)){
            if(newPiece != null){
                return true;
            }
        }
        return false;
    }
}
