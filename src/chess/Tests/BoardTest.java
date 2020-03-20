package chess.Tests;

import chess.*;
import chess.pieces.*;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    @org.junit.Test
    public void getRows() {
        Board testBoard = new Board(12,12, false);
        Assert.assertEquals(testBoard.getRows(), 12);

        Board defaultBoard = new Board();
        Assert.assertEquals(defaultBoard.getRows(), 8);
    }

    @org.junit.Test
    public void getCols() {
        Board testBoard = new Board(12,12, false);
        Assert.assertEquals(testBoard.getCols(), 12);

        Board defaultBoard = new Board();
        Assert.assertEquals(defaultBoard.getCols(), 8);
    }

    @org.junit.Test
    public void getPiece() {
        Board testBoard = new Board(8, 8, false);
        assertTrue(testBoard.getPiece(0,0) instanceof Rook);
        assertTrue(testBoard.getPiece(7, 3) instanceof Queen);
        assertFalse(testBoard.getPiece(8, 8) instanceof King);
        assertNull(testBoard.getPiece(12, 12));
        assertNull(testBoard.getPiece(-1, 4));
        assertNotNull(testBoard.getPiece(0,0));
    }

    @Test
    public void getPieces() {
        Board testBoard = new Board();
        Piece[][] testPieces = testBoard.getPieces();
        assertNotNull(testPieces);
        assertNotNull(testPieces[0][0]);
        assertNull(testPieces[4][4]);
    }

    @org.junit.Test
    public void isEmpty() {
        Board testBoard = new Board();
        assertTrue(testBoard.isEmpty(4,4));
        assertFalse(testBoard.isEmpty(1,testBoard.getCols() - 1));
    }

    @org.junit.Test
    public void movePieceIllegalMoves() {
        Board testBoard = new Board(8, 8, false);
        assertFalse(testBoard.movePiece(1, 0, 2, 0, false));
        assertFalse(testBoard.movePiece(1,0, 2, 1, true));
        assertFalse(testBoard.movePiece(0,0,0,0,true ));
        assertFalse(testBoard.movePiece(3, 4, 4, 4, true));
        assertFalse(testBoard.movePiece(0, 0, -1, -1,true ));
        assertFalse(testBoard.movePiece(5, 5, -9, 8,true ));
        assertFalse(testBoard.movePiece(0,0, 1,0,true ));
        assertFalse(testBoard.movePiece(0,0, 1,1, true));
        assertFalse(testBoard.movePiece(0,1, 2, 3, true));
        assertTrue(testBoard.movePiece(1, 0, 3, 0, true));
        assertFalse(testBoard.movePiece(3, 0, 5, 0, true));
        assertTrue(testBoard.getPiece(3, 0) instanceof Pawn);
        Pawn testPawn = new Pawn(0, 0, true);
        assertFalse(testBoard.setPiece(10, -9, testPawn));
    }
    @org.junit.Test
    public void movePieceLegalMoves() {
        Board testBoard = new Board(8, 8, false);
        assertTrue(testBoard.movePiece(0,1, 2, 2, true));
        assertTrue(testBoard.getPiece(2,2) instanceof Knight);
        assertNull(testBoard.getPiece(0,1));
        assertTrue(testBoard.movePiece(1, 0, 3, 0,true ));
        assertTrue(testBoard.getPiece(3,0) instanceof Pawn);

        assertTrue(testBoard.movePiece(3, 0, 4, 0, true));
        assertTrue(testBoard.getPiece(4,0) instanceof Pawn);
    }


    @Test
    public void removePiece() {
        Board testBoard = new Board();
        testBoard.removePiece(1, 0);
        assertNull(testBoard.getPiece(1,0));
    }

    @Test
    public void clearBoard() {
        Board testBoard = new Board();
        testBoard.clearBoard();
        assertNull(testBoard.getPiece(0,0));
    }

    @Test
    public void setPiece() {
        Board testBoard = new Board();
        testBoard.clearBoard();
        assertNull(testBoard.getPiece(0,0));
        Piece queenTest = new Queen(0,0);
        assertTrue(testBoard.setPiece(0, 3, queenTest));
        assertTrue(testBoard.getPiece(0, 3) instanceof Queen);
        assertEquals(queenTest.getRowCoord(), 0);
        assertEquals(queenTest.getColCoord(), 3);
    }

    @Test
    public void testCustomBoard() {
        Board customBoard = new Board(true);
        assertTrue(customBoard.getPiece(6, 0) instanceof Tiger);
        assertFalse(customBoard.getPiece(6, 0).isBlack());
        assertTrue(customBoard.getPiece(1, 7) instanceof Orbit);
        assertTrue(customBoard.getPiece(1, 7).isBlack());
    }

    @Test
    public void testKingCheck() {
        Board testBoard = new Board(true);
        assertFalse(testBoard.getCheck(true));
        assertFalse(testBoard.getCheck(false));
        assertFalse(testBoard.getCheckmate(true));
        assertFalse(testBoard.getCheckmate(false));
//        assertTrue(testBoard.setCheck(true));
        assertTrue(testBoard.movePiece(6, 2, 5, 2, false));
        assertTrue(testBoard.movePiece(1, 4, 2, 4, true));
        assertTrue(testBoard.movePiece(7, 3, 4, 0, false));
        testBoard.clearBoard();
        King blackKing = new King(0, 0, true);
        King whiteKing = new King(0, 0, false);
        testBoard.setPiece(0, 0, blackKing);
        testBoard.setPiece(2, 1, whiteKing);
        assertFalse(testBoard.movePiece(0, 0, 1, 1, true));
        testBoard.setPiece(1, 1, blackKing);
        testBoard.setCheck(true);
        assertTrue(testBoard.movePiece(1, 1, 2, 1, true));
    }

    @Test
    public void testCheckmate() {
        Board testBoard = new Board(true);
        testBoard.clearBoard();
        testBoard.removePiece(1, 4);
        Queen testQueen = new Queen(3, 3, false);
        testBoard.setPiece(3, 3, testQueen);
        assertTrue(testBoard.movePiece(3, 3, 2, 4, false));

    }
}