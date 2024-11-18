package com.miracosta.cs210.cs210.chess.pieces;

import com.miracosta.cs210.cs210.chess.board.ChessTile;

public class Pawn extends ChessPiece{
    public Pawn(Color color) {
        super(color);
    }
    @Override
    boolean move(ChessTile moveTo) {
        return false;
    }
}
