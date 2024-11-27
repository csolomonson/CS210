package com.miracosta.cs210.cs210.chess.pieces;

public class Rook extends SlidingPiece{
    public Rook(Color color) {
        super(color);
        setRookProperties();
    }
    public Rook() {
        super();
        setRookProperties();
    }

    private void setRookProperties() {
        lateral = true;
        longitudinal = true;
        diagonal = false;
        range = 8;
    }
}
