package chess.pieces;

import chess.Board;

public class Knight extends Piece {
    public Knight(int rowCoord, int colCoord) {
        super(rowCoord, colCoord);
    }
    public Knight(int rowCoord, int colCoord, boolean isBlack){
        super(rowCoord, colCoord, isBlack);
    }

    @Override
    public boolean canMove(Board board, int newRow, int newCol) {
        int currentRow = this.getRowCoord();
        int currentCol = this.getColCoord();
        Piece newPiece = board.getPiece(newRow, newCol);
        if(newPiece != null && (newPiece.isBlack() == this.isBlack())) {
            return false;
        }
        if(((Math.abs(newRow - currentRow) == 2) && (Math.abs(newCol - currentCol) == 1)||
                (Math.abs(newCol - currentCol) == 2 && Math.abs(newRow-currentRow) == 1))) {
            return true;
        }
        return false;
    }
}
