package com.miracosta.cs210.cs210.chess.board;

import com.miracosta.cs210.cs210.chess.pieces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessBoardTest {
    ChessBoard board;
    @BeforeEach
    void setup() {
        board = new ChessBoard();
    }

    /**
     * Check that the equals methods of each chess piece are working
     * Equals method only compares type and color, not position or board or anything.
     * @author Cole Solomonson
     */
    @Test
    void pieceEqualsTest() {
        Rook r1 = new Rook(PieceColor.WHITE);
        Rook r2 = new Rook(PieceColor.WHITE);
        Rook r3 = new Rook(PieceColor.BLACK);

        Knight k1 = new Knight(PieceColor.WHITE);
        Knight k2 = new Knight(PieceColor.BLACK);

        assertEquals(r1, r2);
        assertNotEquals(r1, r3);
        assertNotEquals(r1, k1);
        assertNotEquals(k1, k2);
        assertNotEquals(k2, r3);
        assertNotEquals(r3, k2);
    }

    /**
     * The ChessBoard constructor places the pieces in the standard configuration.
     * This test makes sure everything is in the right place following the constructor call
     * @author Cole Solomonson
     */
    @Test
    void boardSetup() {
        //black pieces
        assertEquals(board.getPiece(0, 0), new Rook(PieceColor.BLACK));
        assertEquals(board.getPiece(0, 1), new Knight(PieceColor.BLACK));
        assertEquals(board.getPiece(0, 2), new Bishop(PieceColor.BLACK));
        assertEquals(board.getPiece(0, 3), new Queen(PieceColor.BLACK));
        assertEquals(board.getPiece(0, 4), new King(PieceColor.BLACK));
        assertEquals(board.getPiece(0, 5), new Bishop(PieceColor.BLACK));
        assertEquals(board.getPiece(0, 6), new Knight(PieceColor.BLACK));
        assertEquals(board.getPiece(0, 7), new Rook(PieceColor.BLACK));

        //white pieces
        assertEquals(board.getPiece(7, 0), new Rook(PieceColor.WHITE));
        assertEquals(board.getPiece(7, 1), new Knight(PieceColor.WHITE));
        assertEquals(board.getPiece(7, 2), new Bishop(PieceColor.WHITE));
        assertEquals(board.getPiece(7, 3), new Queen(PieceColor.WHITE));
        assertEquals(board.getPiece(7, 4), new King(PieceColor.WHITE));
        assertEquals(board.getPiece(7, 5), new Bishop(PieceColor.WHITE));
        assertEquals(board.getPiece(7, 6), new Knight(PieceColor.WHITE));
        assertEquals(board.getPiece(7, 7), new Rook(PieceColor.WHITE));

        //pawns
        for (int i = 0; i < 8; i++) {
            assertEquals(board.getPiece(1, i), new Pawn(PieceColor.BLACK));
            assertEquals(board.getPiece(6, i), new Pawn(PieceColor.WHITE));
        }

        //empty tiles
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                assertNull(board.getPiece(i,j));
            }
        }

    }

    /**
     * Make sure the coordinates of each ChessTile in the ChessBoard are correct
     * @author Cole Solomonson
     */
    @Test
    void tileTester() {
        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                assertEquals(board.getTile(i,j).getRow(), i);
                assertEquals(board.getTile(i,j).getColumn(), j);
            }
        }
    }

    /**
     * Not really a test, I just made a beautiful toString that I wanted to print out.
     * I visibly inspect if it is correct.
     * @author Cole Solomonson
     */
    @Test
    void testPrint() {
        System.out.println(board.toString());
    }

    /**
     * Hypothetical move clones the board, makes a move on the board, and returns the clone
     * Make sure all the references are correct and stuff, and the clone actually moves
     * @author Cole Solomonson
     */
    @Test
    void testHypotheticalMove() {
        ChessBoard hypothetical = board.hypotheticalMove(6, 4, 4, 4);
        assertNotEquals(board, hypothetical);
        board.move(6,4,4,4);
        assertEquals(board, hypothetical);
    }

    /**
     * Make sure the getCheck functionality is working in hypothetical clones.
     * This test exists because it previously was not working.
     * @author Cole Solomonson
     */
    @Test
    void testHypotheticalChecks() {
        assertTrue(board.move(7,1,5,2));
        assertTrue(board.move(1,4,3,4));
        assertTrue(board.move(5,2,7,1));
        assertTrue(board.move(0,3,4,7));

        ChessBoard hypothetical = board.hypotheticalMove(6,5,5,5);
        hypothetical.getPiece(4,7).getLegalMoves();
        System.out.println(hypothetical);
        assertNull(hypothetical.getPiece(6,5));
        assertEquals(new Pawn(), hypothetical.getPiece(5,5));

        assertTrue(hypothetical.getCheck(PieceColor.WHITE));

    }

    /**
     * Make sure the getCheck functionality works normally (when not in hypothetical clones)
     * @author Cole Solomonson
     */
    @Test
    void testChecks() {
        assertTrue(board.move(7,1,5,2));
        assertTrue(board.move(1,4,3,4));
        assertTrue(board.move(6,5,5,5));
        assertTrue(board.move(0,3,4,7));

        System.out.println(board);

        assertTrue(board.getCheck(PieceColor.WHITE));

        ChessBoard block = board.hypotheticalMove(6,6,5,6);
        System.out.println("BLOCK:");
        System.out.println(block);
        assertNull(block.getPiece(6,6));
        assertEquals(new Pawn(), block.getPiece(5,6));
        assertFalse(block.getCheck(PieceColor.WHITE));



        ChessBoard ignore = board.hypotheticalMove(7,1,5,2);
        System.out.println("IGNORE");
        System.out.println(ignore);
        assertNull(ignore.getPiece(7,1));
        assertEquals(new Knight(), ignore.getPiece(5,2));
        assertTrue(ignore.getCheck(PieceColor.WHITE));
    }
}