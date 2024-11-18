package com.miracosta.cs210.cs210.chess.pieces;

import com.miracosta.cs210.cs210.chess.board.ChessTile;

public class Knight extends ChessPiece{
    public Knight(Color color) {
        super(color);
    }
    @Override
    boolean move(ChessTile moveTo) {
        return false;
    }
}
