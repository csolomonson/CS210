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

    @Test
    void pieceEqualsTest() {
        Rook r1 = new Rook(Color.WHITE);
        Rook r2 = new Rook(Color.WHITE);
        Rook r3 = new Rook(Color.BLACK);

        Knight k1 = new Knight(Color.WHITE);
        Knight k2 = new Knight(Color.BLACK);

        assertEquals(r1, r2);
        assertNotEquals(r1, r3);
        assertNotEquals(r1, k1);
        assertNotEquals(k1, k2);
        assertNotEquals(k2, r3);
        assertNotEquals(r3, k2);
    }

    @Test
    void boardSetup() {
        //black pieces
        assertEquals(board.getPiece(0, 0), new Rook(Color.BLACK));
        assertEquals(board.getPiece(0, 1), new Knight(Color.BLACK));
        assertEquals(board.getPiece(0, 2), new Bishop(Color.BLACK));
        assertEquals(board.getPiece(0, 3), new Queen(Color.BLACK));
        assertEquals(board.getPiece(0, 4), new King(Color.BLACK));
        assertEquals(board.getPiece(0, 5), new Bishop(Color.BLACK));
        assertEquals(board.getPiece(0, 6), new Knight(Color.BLACK));
        assertEquals(board.getPiece(0, 7), new Rook(Color.BLACK));

        //white pieces
        assertEquals(board.getPiece(7, 0), new Rook(Color.WHITE));
        assertEquals(board.getPiece(7, 1), new Knight(Color.WHITE));
        assertEquals(board.getPiece(7, 2), new Bishop(Color.WHITE));
        assertEquals(board.getPiece(7, 3), new Queen(Color.WHITE));
        assertEquals(board.getPiece(7, 4), new King(Color.WHITE));
        assertEquals(board.getPiece(7, 5), new Bishop(Color.WHITE));
        assertEquals(board.getPiece(7, 6), new Knight(Color.WHITE));
        assertEquals(board.getPiece(7, 7), new Rook(Color.WHITE));

        //pawns
        for (int i = 0; i < 8; i++) {
            assertEquals(board.getPiece(1, i), new Pawn(Color.BLACK));
            assertEquals(board.getPiece(6, i), new Pawn(Color.WHITE));
        }

        //empty tiles
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                assertNull(board.getPiece(i,j));
            }
        }

    }

    @Test
    void tileTester() {
        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                assertEquals(board.getTile(i,j).getRow(), i);
                assertEquals(board.getTile(i,j).getColumn(), j);
            }
        }
    }

    @Test
    void testPrint() {
        System.out.println(board.toString());
    }
}