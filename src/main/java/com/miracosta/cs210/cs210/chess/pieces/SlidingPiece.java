package com.miracosta.cs210.cs210.chess.pieces;

import com.miracosta.cs210.cs210.chess.board.ChessTile;

public abstract class SlidingPiece extends ChessPiece {
    public SlidingPiece() {
        this(Color.WHITE);
    }
    public SlidingPiece(Color color) {
        super(color);
    }
    @Override
    boolean move(ChessTile moveTo) {
        return false;
    }
}
