package com.miracosta.cs210.cs210.game;

import com.miracosta.cs210.cs210.chess.pieces.ChessPiece;
import com.miracosta.cs210.cs210.chess.pieces.Pawn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {

    /**
     * Check that we're on the up-and-up with the clone and equals methods
     * @author Cole Solomonson
     */
    @Test
    void cloneTest() {
        GameBoard board = new GameBoard();
        GameBoard clone = board.clone();

        assertEquals(board, clone);
        assertNotSame(board, clone);
    }

    /**
     * Make sure my understanding of cloning subclasses is correct
     * @author Cole Solomonson
     */
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

    /**
     * Make sure that hypothetical GameBoard moves actually move as intended
     * @author Cole Solomonson
     */
    @Test
    void hypotheticalTest() {
        GameBoard board = new GameBoard(0);
        //assertTrue(board.move(6,0,4,0));
        GameBoard hypothetical = board.hypotheticalMove(6,0,4,0);

        System.out.println(hypothetical.getChessBoard());
        System.out.println(board.getChessBoard());
        assertNotEquals(board, hypothetical);
        assertNull(hypothetical.getChessBoard().getPiece(6,0));
        assertEquals(new Pawn(), hypothetical.getChessBoard().getPiece(4,0));

    }

}