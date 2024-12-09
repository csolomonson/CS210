package com.miracosta.cs210.cs210.game;

import com.miracosta.cs210.cs210.chess.pieces.ChessPiece;
import com.miracosta.cs210.cs210.chess.pieces.Pawn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {

    @Test
    void cloneTest() {
        GameBoard board = new GameBoard();
        GameBoard clone = board.clone();

        assertEquals(board, clone);
        assertNotSame(board, clone);
    }

    @Test
    void pieceCloneTest() {
        GameBoard board = new GameBoard();
        ChessPiece pawn = board.getChessBoard().getPiece(1,0);
        ChessPiece pawnClone = pawn.clone();

        assertInstanceOf(Pawn.class, pawn);
        assertInstanceOf(Pawn.class, pawnClone);
        assertEquals(pawn, pawnClone);
        assertNotSame(pawn, pawnClone);
    }

}