package chess.Tests;

import chess.*;
import chess.pieces.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {


    @org.junit.jupiter.api.Test
    void getRowCoord() {
        Piece testRook = new Rook(0,0);
        Assertions.assertEquals(testRook.getRowCoord(), 0);

    }

    @org.junit.jupiter.api.Test
    void getColCoord() {
        Piece testPawn = new Rook(1,0);
        Assertions.assertEquals(testPawn.getRowCoord(), 1);

    }

    @org.junit.jupiter.api.Test
    void isBlack() {
        Piece testRook = new Rook(0,0);
        assertTrue(testRook.isBlack());
        Piece whiteQueen = new Queen(0, 4, false);
        assertFalse(whiteQueen.isBlack());
    }

    @org.junit.jupiter.api.Test
    void canMove() {
        Board testBoard = new Board();
        assertFalse(testBoard.movePiece(0, 0, 1, 0, true));
    }

    @org.junit.jupiter.api.Test
    void checkRookMove() {
        Board testBoard = new Board();
        Piece testRook = testBoard.getPiece(0, 0);
        assertFalse(testRook.checkRookMove(testBoard, 0, 5, testRook.getRowCoord(), testRook.getColCoord()));
        assertFalse(testRook.checkRookMove(testBoard, 2, 0,  testRook.getRowCoord(), testRook.getColCoord()));
        assertFalse(testRook.checkRookMove(testBoard, 5, 4,  testRook.getRowCoord(), testRook.getColCoord()));
        assertFalse(testRook.checkRookMove(testBoard, 5, 0,  testRook.getRowCoord(), testRook.getColCoord()));
        testBoard.removePiece(1,0);
        assertTrue(testRook.checkRookMove(testBoard, 5, 0,  testRook.getRowCoord(), testRook.getColCoord()));
        testBoard.clearBoard();
        testBoard.setPiece(3, 5, testRook);
        Piece testRook2 = new Rook(6, 5);
        testBoard.setPiece(6, 5, testRook2);
        assertFalse(testRook2.checkRookMove(testBoard,0, 0, testRook2.getRowCoord(), testRook2.getColCoord()));
        assertFalse(testRook2.checkRookMove(testBoard, 7, 7, testRook2.getRowCoord(), testRook2.getColCoord()));
        assertTrue(testRook2.checkRookMove(testBoard,3, 5, testRook2.getRowCoord(), testRook2.getColCoord()));
        assertTrue(testRook2.checkRookMove(testBoard,6, 7, testRook2.getRowCoord(), testRook2.getColCoord()));
        assertTrue(testRook2.checkRookMove(testBoard,6, 2, testRook2.getRowCoord(), testRook2.getColCoord()));
        assertTrue(testRook2.checkRookMove(testBoard,6, 7, testRook2.getRowCoord(), testRook2.getColCoord()));
        assertTrue(testRook2.canMove(testBoard, 8, 5));
        assertFalse(testRook2.canMove(testBoard, 0, 5));
        assertTrue(testRook2.canMove(testBoard, 6, 2));
        assertTrue(testRook2.canMove(testBoard, 6, 7));
        assertFalse(testRook2.canMove(testBoard, 2, 2));
        testBoard.clearBoard();
        testBoard.setPiece(3, 5, testRook);
        testBoard.setPiece(3, 4, testRook2);
        assertFalse(testRook.canMove(testBoard, 3, 0));
    }

    @org.junit.jupiter.api.Test
    void checkBishopMove() {
        Board testBoard = new Board();
        assertFalse(testBoard.movePiece(0, 2, 1, 3, true));
        assertFalse(testBoard.movePiece(0, 2, 2, 4, true));
        testBoard.movePiece(1, 3, 2, 3, true);
        assertTrue(testBoard.movePiece(0, 2, 2, 4, true));

        testBoard.clearBoard();
        Piece bishopTest = new Bishop(0 ,0);
        testBoard.setPiece(2, 4, bishopTest);
        assertTrue(bishopTest.checkBishopMove(testBoard, 3, 5, bishopTest.getRowCoord(), bishopTest.getColCoord()));
        assertTrue(bishopTest.checkBishopMove(testBoard, 0, 2, bishopTest.getRowCoord(), bishopTest.getColCoord()));
        assertFalse(bishopTest.checkBishopMove(testBoard, 0, 0, bishopTest.getRowCoord(), bishopTest.getColCoord()));
        assertFalse(bishopTest.checkBishopMove(testBoard, 0, 7, bishopTest.getRowCoord(), bishopTest.getColCoord()));
        assertFalse(bishopTest.checkBishopMove(testBoard, 1, 7, bishopTest.getRowCoord(), bishopTest.getColCoord()));

        assertFalse(bishopTest.checkBishopMove(testBoard, 4, 7, bishopTest.getRowCoord(), bishopTest.getColCoord()));
        assertTrue(bishopTest.checkBishopMove(testBoard, 4, 2, bishopTest.getRowCoord(), bishopTest.getColCoord()));
        assertFalse(bishopTest.checkBishopMove(testBoard, 2, 5, bishopTest.getRowCoord(), bishopTest.getColCoord()));
        assertFalse(bishopTest.checkBishopMove(testBoard, 3, 4, bishopTest.getRowCoord(), bishopTest.getColCoord()));
        testBoard.setPiece(2, 4, bishopTest);
        Piece bishopTest2 = new Bishop(0, 0);
        testBoard.setPiece(4, 2, bishopTest2);
        assertFalse(testBoard.movePiece(2, 4, 4, 2, true));
        assertFalse(testBoard.movePiece(2, 4, 5, 1, true));
    }

    @Test
    void checkQueensMoves() {
        Board testBoard = new Board();
        assertFalse(testBoard.movePiece(0, 3, 2, 5, true));
        assertFalse(testBoard.movePiece(0, 3, 1, 3, true));
        testBoard.clearBoard();
        Queen queenTest = new Queen(0, 0);
        testBoard.setPiece(0, 3, queenTest);
        assertTrue(queenTest.canMove(testBoard, 3, 3));
        assertTrue(queenTest.canMove(testBoard, 0, 0));
        assertTrue(queenTest.canMove(testBoard, 0, 7));
        testBoard.clearBoard();
        testBoard.setPiece(4,4, queenTest);
        assertTrue(queenTest.canMove(testBoard, 2, 2));
        assertTrue(queenTest.canMove(testBoard, 2, 4));
        assertTrue(queenTest.canMove(testBoard, 4, 5));
        assertTrue(queenTest.canMove(testBoard, 4, 0));
        assertFalse(queenTest.canMove(testBoard, 5, 2));
        assertFalse(queenTest.canMove(testBoard, 3, 7));
        assertFalse(queenTest.canMove(testBoard, 0, 3));
        assertFalse(queenTest.canMove(testBoard, 5, 7));
    }
    @Test
    void checkKingMoves() {
        Piece testKing = new King(6,2);
        assertEquals(testKing.getRowCoord(), 6);
        assertEquals(testKing.getColCoord(), 2);
        Board testBoard = new Board();
        assertFalse(testBoard.movePiece(0, 4, 1, 4, true));
        testBoard.removePiece(1, 4);
        assertFalse(testBoard.movePiece(0, 4, 2, 4, true));
        assertTrue(testBoard.movePiece(0, 4, 1, 4, true));
    }
    @org.junit.jupiter.api.Test
    void checkPawnMoves() {
        Piece testPawn = new Pawn(5, 4);
        assertEquals(testPawn.getColCoord(), 4);
        assertEquals(testPawn.getRowCoord(), 5);
        Board testBoard = new Board();
        assertFalse(testBoard.movePiece(1, 0, 1, 1, true));
        assertFalse(testBoard.movePiece(1, 0, 2, 0, false));
        assertFalse(testBoard.movePiece(1, 0, 4, 0, true));
        assertFalse(testBoard.movePiece(1, 0, 2, 2, true));
        Piece testPawn2 = new Pawn(0, 0, false);
        testBoard.setPiece(2, 1, testPawn2);
        assertTrue(testBoard.movePiece(1, 0, 2, 1, true));
    }
    @Test
    void checkKnightMoves(){
        Piece testKnight = new Knight(3, 6);
        assertEquals(testKnight.getRowCoord(), 3);
        assertEquals(testKnight.getColCoord(), 6);
    }

    @Test
    void checkOrbitConstructors() {
        Orbit testOrb = new Orbit(4, 3);
        assertEquals(testOrb.getRadius(), 3);
        assertEquals(testOrb.getRowCoord(), 4);
        assertEquals(testOrb.getColCoord(), 3);
        Orbit testOrbRad = new Orbit(0, 0, 10);
        assertTrue(testOrbRad.isBlack());
        assertEquals(testOrbRad.getRadius(), 10);
        Orbit testWhiteRad = new Orbit(0, 0, false, 4);
        assertFalse(testWhiteRad.isBlack());
        assertEquals(testWhiteRad.getRadius(), 4);
        Orbit testWhiteOrb = new Orbit(4, 3, false);
        assertFalse(testWhiteOrb.isBlack());
        assertEquals(testWhiteOrb.getRadius(), 3);
    }

    @Test
    void checkOrbitMoves() {
        Board testBoard = new Board(true);
        assertTrue(testBoard.getPiece(1, 7) instanceof Orbit);
        assertFalse(testBoard.movePiece(1, 7, 1, 1, true));
        assertFalse(testBoard.movePiece(1, 7, 3, 3, true));
        assertFalse(testBoard.movePiece(1, 7, 3, 0, true));
        assertTrue(testBoard.movePiece(1, 7, 3, 7, true));


    }

    @Test
    void checkTigerConstructors() {
        Tiger testTiger = new Tiger(4, 3);
        assertEquals(testTiger.getRowCoord(), 4);
        assertEquals(testTiger.getColCoord(), 3);


    }

    @Test
    void checkTigerMoves() {
        Board testBoard = new Board(true);
        assertTrue(testBoard.getPiece(1, 0) instanceof Tiger);
        assertFalse(testBoard.movePiece(1, 0, 1, 1, true));
        assertFalse(testBoard.movePiece(1, 0, 3, 3, true));
        assertTrue(testBoard.movePiece(1, 0, 3, 0, true));
        assertTrue(testBoard.movePiece(3, 0, 5, 0, true));
        assertTrue(testBoard.movePiece(5, 0, 5, 2, true));
        assertTrue(testBoard.movePiece(5, 2, 6, 2, true));
    }

    @Test
    void checkToString() {
        Pawn testWhitePawn = new Pawn(0, 4, false);
        assertEquals(testWhitePawn.toString(), "White Pawn");
        Tiger testBlackTiger = new Tiger(0, 0, true);
        assertEquals(testBlackTiger.toString(), "Black Tiger");
    }
}