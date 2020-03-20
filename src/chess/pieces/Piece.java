package chess.pieces;

import chess.Board;

/**
 * Abstract class that represents each Chess piece that is present on the Board. Extended by Pawn, Rook, Knight, Bishop,
 * Queen, and King. The logic for the individual pieces and movement is implemented within each child class.
 */
public abstract class Piece {
    private int rowCoord;
    private int colCoord;
    private boolean isBlack;

    /**
     * Sets the row coordinate of the piece
     * @param rowCoord new row coordinate
     */
    public void setRowCoord(int rowCoord) {
        this.rowCoord = rowCoord;
    }

    /**
     * Sets the column coordinate of the piece
     * @param colCoord new column coordinate
     */
    public void setColCoord(int colCoord) {
        this.colCoord = colCoord;
    }

    /**
     * Gets the row coordinate of the piece
     * @return int row coordinate
     */
    public int getRowCoord() {
        return rowCoord;
    }

    /**
     * Gets the column coordinate of the piece
     * @return int column coordinate
     */
    public int getColCoord() {
        return colCoord;
    }

    /**
     * Gets the color of the piece
     * @return true if the piece is black;
     *         white if the piece is white
     */
    public boolean isBlack() {
        return isBlack;
    }

    /**
     * Default constructor for a piece at a row and column. Sets the default color to black
     * @param rowCoord
     * @param colCoord
     */
    public Piece(int rowCoord, int colCoord) {
        this.rowCoord = rowCoord;
        this.colCoord = colCoord;
        isBlack = true;
    }

    /**
     * Constructor for a piece at a given row and column, and the given color of the piece. A true value for isBlack
     * represents a black piece and a false value represents a white piece.
     * @param rowCoord
     * @param colCoord
     * @param isBlack
     */
    public Piece(int rowCoord, int colCoord, boolean isBlack) {
        this.rowCoord = rowCoord;
        this.colCoord = colCoord;
        this.isBlack = isBlack;
    }

    /**
     * Abstract method implemented by the inherited classes to determine if a given piece can move to a new location on
     * the board. The new location may have a piece from the opposing team that can be captured, or be an empty space.
     * Pieces may jump over intermediate spaces like the Knight, or have to travel through the path to the new location
     * for the other pieces and the path must be empty.
     * @param board current board
     * @param newRow new row coordinate to be moved to
     * @param newCol new column coordinate to be moved to
     * @return true if the piece can move be moved to the new location;
     *         false otherwise
     */
    public abstract boolean canMove(Board board, int newRow, int newCol);

    /**
     * Checks to see if the move from the current location to the new location is a Rook move. This means
     * moving either vertically and horizontally with no pieces in the way.
     *
     * @param board
     * @param newRow
     * @param newCol
     * @param currentRow
     * @param currentCol
     * @return true if it is a valid move for a rook;
     *         false otherwise;
     */
    public boolean checkRookMove(Board board, int newRow, int newCol, int currentRow, int currentCol) {
        if (currentRow != newRow && currentCol != newCol) {
            return false;
        }
        if(currentRow != newRow){
            if(currentRow > newRow){
                for (int rowUpper = currentRow - 1; rowUpper > newRow; rowUpper--) {
                    if(board.getPiece(rowUpper, currentCol) != null){
                        return false;
                    }
                }
            } else {
                for (int rowLower = currentRow + 1 ; rowLower < newRow; rowLower++) {
                    if(board.getPiece(rowLower, currentCol) != null){
                        return false;
                    }
                }
            }
        }
        if(currentCol != newCol){
            if(currentCol < newCol){
                for (int colRight = currentCol + 1; colRight < newCol; colRight++) {
                    if(board.getPiece(currentRow, colRight) != null){
                        return false;
                    }
                }
            } else {
                for (int colLeft = currentCol - 1; colLeft > newCol ; colLeft--) {
                    if(board.getPiece(currentRow, colLeft) != null){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Checks to see if the move from the current location to the new location is a valid move for a Bishop. It will
     * check to see if the new location is on the same diagonal path and no pieces in the way.
     * @param board
     * @param newRow
     * @param newCol
     * @param currentRow
     * @param currentCol
     * @return true if it is a valid move for a Bishop;
     *         false otherwise
     */
    public boolean checkBishopMove(Board board, int newRow, int newCol, int currentRow, int currentCol) {
        if(Math.abs(currentCol - newCol) != Math.abs(currentRow - newRow)){
            return false;
        }
        if(currentRow < newRow && currentCol < newCol){
            for (int rowLowerRight = currentRow + 1; rowLowerRight < newRow; rowLowerRight++) {
                if(board.getPiece(rowLowerRight, (++currentCol)) != null){
                    return false;
                }
            }
        } else if(currentRow < newRow && currentCol > newCol){
            for (int rowLowerLeft = currentRow + 1; rowLowerLeft < newRow; rowLowerLeft++) {
                if(board.getPiece(rowLowerLeft, (--currentCol)) != null){
                    return false;
                }
            }
        } else if (currentRow > newRow && currentCol < newCol){
            for (int rowUpperRight = currentRow - 1; rowUpperRight > newRow; rowUpperRight--) {
                if(board.getPiece(rowUpperRight, (++currentCol)) != null){
                    return false;
                }
            }
        } else {
            for (int rowUpperLeft = currentRow - 1; rowUpperLeft > newRow; rowUpperLeft--) {
                if(board.getPiece(rowUpperLeft, (--currentCol)) != null){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Prints out the Color and Piece type of a Piece object
     * @return String in the form of [Color] [Piece]
     */
    @Override
    public String toString() {

        String str = "";
        if(this.isBlack){
            str += "Black ";
        } else {
            str += "White ";
        }
        str += this.getClass().getSimpleName();

        return str;
    }
}
