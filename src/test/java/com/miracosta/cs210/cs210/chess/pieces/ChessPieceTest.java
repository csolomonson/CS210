package com.miracosta.cs210.cs210.chess.pieces;

import com.miracosta.cs210.cs210.chess.board.ChessBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessPieceTest {
    ChessBoard board;
    @BeforeEach
    void setup() {
        board = new ChessBoard();
        board.clearBoard();

        board.setPiece(new Rook(PieceColor.BLACK), 0, 4);
        board.setPiece(new King(PieceColor.BLACK), 0, 5);
        board.setPiece(new Knight(PieceColor.BLACK), 0, 6);

        board.setPiece(new Queen(PieceColor.BLACK), 2, 0);
        board.setPiece(new Knight(PieceColor.WHITE), 2, 5);
        board.setPiece(new Pawn(PieceColor.BLACK), 2, 6);
        board.setPiece(new Rook(PieceColor.BLACK), 2, 7);

        board.setPiece(new Pawn(PieceColor.BLACK),3, 0);
        board.setPiece(new Pawn(PieceColor.WHITE),3, 2);
        board.setPiece(new Pawn(PieceColor.BLACK),3, 5);
        board.setPiece(new Bishop(PieceColor.WHITE),3, 7);

        board.setPiece(new Pawn(PieceColor.WHITE), 4, 7);

        board.setPiece(new Rook(), 5, 1);
        board.setPiece(new Rook(), 5, 3);
        board.setPiece(new Pawn(), 5, 5);
        board.setPiece(new Pawn(), 5, 6);

        board.setPiece(new Knight(), 7, 1);
        board.setPiece(new King(), 7, 2);

        board.update();
    }

    @Test
    void rookMoves() {
        ChessPiece rook1 = board.getPiece(5, 1);
        ChessPiece rook2 = board.getPiece(5, 3);
        ChessPiece rook3 = board.getPiece(0, 4);
        ChessPiece rook4 = board.getPiece(2, 7);

        assertNotNull(rook1);
        assertNotNull(rook2);
        assertNotNull(rook3);
        assertNotNull(rook4);

        assertEquals(8, rook1.getLegalMoves().size());
        assertTrue(rook1.isLegalMove(5,0));
        assertTrue(rook1.isLegalMove(5,2));
        assertTrue(rook1.isLegalMove(6,1));
        assertTrue(rook1.isLegalMove(4,1));
        assertTrue(rook1.isLegalMove(3,1));
        assertTrue(rook1.isLegalMove(2,1));
        assertTrue(rook1.isLegalMove(1,1));
        assertTrue(rook1.isLegalMove(0,1));

        assertEquals(9, rook2.getLegalMoves().size());
        assertEquals(11, rook3.getLegalMoves().size());
        assertEquals(3, rook4.getLegalMoves().size());
    }

    @Test
    void bishopMoves() {
        ChessPiece bishop = board.getPiece(3, 7);

        assertNotNull(bishop);
        assertEquals(2, bishop.getLegalMoves().size());
        assertTrue(bishop.isLegalMove(2,6));
        assertTrue(bishop.isLegalMove(4,6));
    }

    @Test
    void knightMoves() {
        Knight knight1 = (Knight) board.getPiece(0, 6);
        Knight knight2 = (Knight) board.getPiece(2,5);
        Knight knight3 = (Knight) board.getPiece(7, 1);

        assertNotNull(knight1);
        assertNotNull(knight2);
        assertNotNull(knight3);

        assertEquals(2, knight1.getLegalMoves().size());
        assertEquals(7, knight2.getLegalMoves().size());
        assertEquals(3, knight3.getLegalMoves().size());

        assertTrue(knight1.isLegalMove(1,4));
        assertTrue(knight1.isLegalMove(2,5));
    }

    @Test
    void queenTest() {
        Queen queen1 = (Queen) board.getPiece(2,0);
        assertNotNull(queen1);

        assertEquals(12, queen1.getLegalMoves().size());

        assertTrue(queen1.isLegalMove(0,0));
        assertTrue(queen1.isLegalMove(0,2));
        assertTrue(queen1.isLegalMove(2,5));
        assertTrue(queen1.isLegalMove(5,3));
    }

    @Test
    void kingTest() {
        King king1 = (King) board.getPiece(0, 5);
        King king2 = (King) board.getPiece(7, 2);

        assertNotNull(king1);
        assertNotNull(king2);

        assertEquals(3, king1.getLegalMoves().size());
        assertEquals(4, king2.getLegalMoves().size());
    }

    @Test
    void pawnTest() {
        Pawn pawn1 = (Pawn) board.getPiece(2,6);
        Pawn pawn2 = (Pawn) board.getPiece(3,0);
        Pawn pawn3 = (Pawn) board.getPiece(3,2);
        Pawn pawn4 = (Pawn) board.getPiece(4,7);

        assertNotNull(pawn1);
        assertNotNull(pawn2);
        assertNotNull(pawn3);
        assertNotNull(pawn4);

        assertEquals(2, pawn1.getLegalMoves().size());
        assertEquals(1, pawn2.getLegalMoves().size());
        assertEquals(1, pawn3.getLegalMoves().size());
        assertEquals(0, pawn4.getLegalMoves().size());
    }

    @Test
    void moveTest() {
        ChessPiece piece1 = board.getPiece(2,0);
        ChessPiece piece2 = board.getPiece(5,1);

        assertTrue(piece1.move(board.getTile(5,3)));
        assertNull(board.getPiece(2,0));
        assertEquals(new Queen(PieceColor.BLACK), board.getPiece(5,3));
        assertTrue(piece1.isLegalMove(5,1));
        assertTrue(piece2.isLegalMove(5,3));

    }



}