package chess.pieces;

import chess.Board;

public class Bishop extends Piece {
    public Bishop(int rowCoord, int colCoord) {
        super(rowCoord, colCoord);
    }
    public Bishop(int rowCoord, int colCoord, boolean isBlack){
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
        if (checkBishopMove(board, newRow, newCol, currentRow, currentCol)) {
            return true;
        }
        return false;
    }

//    public boolean checkBishopMove(Board board, int newRow, int newCol, int currentRow, int currentCol) {
//        if(Math.abs(currentCol - newCol) != Math.abs(currentRow - newRow)){
//            return true;
//        }
//        if(currentRow < newRow && currentCol < newCol){
//            for (int rowLowerRight = currentRow; rowLowerRight < newRow; rowLowerRight++) {
//                if(board.getPiece(rowLowerRight, (rowLowerRight + 1)) != null){
//                    return true;
//                }
//            }
//        } else if(currentRow < newRow && currentCol > newCol){
//            for (int rowLowerLeft = currentRow; rowLowerLeft < newRow; rowLowerLeft++) {
//                if(board.getPiece(rowLowerLeft, (rowLowerLeft - 1)) != null){
//                    return true;
//                }
//            }
//        } else if (currentRow > newRow && currentCol < newCol){
//            for (int rowUpperRight = currentRow; rowUpperRight > newRow; rowUpperRight--) {
//                if(board.getPiece(rowUpperRight, (rowUpperRight + 1)) != null){
//                    return true;
//                }
//            }
//        } else {
//            for (int rowUpperLeft = currentRow; rowUpperLeft > newRow; rowUpperLeft--) {
//                if(board.getPiece(rowUpperLeft, (rowUpperLeft - 1)) != null){
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
}
