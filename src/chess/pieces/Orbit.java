package chess.pieces;

import chess.Board;

/**
 * Orbit Class is a custom chess piece class. It allows movements from the piece to any location on the board
 * within the mathematical radius of the piece. The default allowed radius is set to 3 tiles, but can be changed.
 * The piece can jump to any square within its radius.
 */
public class Orbit extends Piece {



    private int radius;

    /**
     * Default constructor for an Orbit piece at a row and column. Sets the default radius to 3 tiles and
     * default color is black.
     *
     * @param rowCoord row number
     * @param colCoord column number
     */
    public Orbit(int rowCoord, int colCoord) {
        super(rowCoord, colCoord);
        radius = 3;
    }

    /**
     * Constructs an Orbit piece at the given row and column, with a given radius. The color is set to black
     * @param rowCoord row number
     * @param colCoord column number
     * @param radius allowed radius
     */
    public Orbit(int rowCoord, int colCoord, int radius){
        super(rowCoord, colCoord);
        this.radius = radius;
    }
    /**
     * Constructor for a piece at a given row and column, and the given color of the piece. A true value for isBlack
     * represents a black piece and a false value represents a white piece. Radius is set to 3
     *
     * @param rowCoord row number
     * @param colCoord column number
     * @param isBlack color of Piece
     */
    public Orbit(int rowCoord, int colCoord, boolean isBlack) {
        super(rowCoord, colCoord, isBlack);
        radius = 3;

    }

    /**
     * Constructor for an Orbit piece at a given row , column, the color isBlack, and the given radius.
     * @param rowCoord
     * @param colCoord
     * @param isBlack
     * @param radius
     */
    public Orbit(int rowCoord, int colCoord, boolean isBlack, int radius) {
        super(rowCoord, colCoord, isBlack);
        this.radius = radius;

    }

    /**
     * gets the radius of the Orbit piece
     * @return int radius
     */
    public int getRadius() {
        return radius;
    }

    /**
     * abstract canMove method defined by the Piece Class is overridden with the logic for the Orbit piece.
     * Orbits can move to any square that is within the radius for the piece. The distance from the current space
     * to the new space is computed mathematically using the standard distance formula.
     * taking the
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
        if(Math.sqrt(Math.pow(Math.abs(newRow - currentRow), 2) + Math.pow(Math.abs(newCol-currentCol), 2)) <= this.getRadius()){
            return true;
        }
        return false;
    }
}
