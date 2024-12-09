package com.miracosta.cs210.cs210.chess.pieces;

import com.miracosta.cs210.cs210.chess.board.ChessBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.miracosta.cs210.cs210.chess.pieces.Color.BLACK;
import static org.junit.jupiter.api.Assertions.*;

class EnPassantPieceTest {
    ChessBoard board;

    @BeforeEach
    void setup() {
        board = new ChessBoard();
        board.setPrintMoves(true);
    }

    @Test
    void enPassantableTest() {

        assertTrue(board.move(1,0,3,0));
        EnPassantPiece piece = (EnPassantPiece) board.getPiece(3,0);
        assertTrue(piece.getEnPassantStatus());
    }

    @Test
    void pawnDoubleMoveTest() {
        board.clearBoard();
        board.setPiece(new Pawn(), 6, 0);
        board.setPiece(new Pawn(BLACK), 1, 0);
        board.update();
        assertEquals(2, board.getPiece(6,0).legalMoves.size());
        assertTrue(board.getPiece(6,0).isLegalMove(5,0));
        assertTrue(board.getPiece(6,0).isLegalMove(4,0));
        assertEquals(2, board.getPiece(1,0).legalMoves.size());
        assertTrue(board.getPiece(1,0).isLegalMove(2,0));
        assertTrue(board.getPiece(1,0).isLegalMove(3,0));
    }

    @Test
    void EnPassantCaptureTest() {
        board.setPiece(new Pawn(BLACK), 4,1);
        board.update();
        //double move the white pawn
        assertTrue(board.move(6,0,4,0));
        //en passant with the black pawn
        assertTrue(board.move(4,1,5,0));
        //make sure the pieces are in the right place now
        assertNull(board.getPiece(4,0));
        assertEquals(new Pawn(BLACK), board.getPiece(5,0));
    }

    @Test
    void checkEnPassantOnlyWorksOnDoubleMoves() {
        board.setPiece(new Pawn(BLACK), 4,1);
        board.update();
        //single move the white pawn
        assertTrue(board.move(6,0,5,0));
        //random black knight move
        assertTrue(board.move(0,1,2,0));
        //another pawn single move
        assertTrue(board.move(5,0,4,0));
        //random knight move back
        assertTrue(board.move(2,0,0,1));
        //attempt en passant with the black pawn
        assertFalse(board.move(4,1,5,0));
    }

    @Test
    void checkEnPassantOnlyWorksImmediately() {
        board.setPiece(new Pawn(BLACK), 4,1);
        board.update();
        //double move the white pawn
        assertTrue(board.move(6,0,4,0));
        //random black knight move (miss chance to en passant)
        assertTrue(board.move(0,1,2,0));
        //random white knight move
        assertTrue(board.move(7,6,5,7));
        //try to en passant now with the black pawn
        assertFalse(board.move(4,1,5,0));
    }

    @Test void checkWeDontDeletePiecesThatWerentSupposedToBeDeleted() {
        board.setPiece(new Pawn(BLACK), 4,1);
        board.update();
        //double move the white pawn
        assertTrue(board.move(6,0,4,0));
        //don't capture with black pawn, but do move it
        assertTrue(board.move(4,1,5,1));
        //make sure the piece didn't get captured
        assertEquals(new Pawn(), board.getPiece(4,0));
    }






}