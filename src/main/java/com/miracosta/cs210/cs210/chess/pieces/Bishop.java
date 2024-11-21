package com.miracosta.cs210.cs210.chess.pieces;

public class Bishop extends SlidingPiece{
    public Bishop(Color color) {
        super(color);
        setBishopProperties();
    }
    public Bishop() {
        super();
        setBishopProperties();
    }

    private void setBishopProperties() {
        diagonal = true;
        lateral = false;
        longitudinal = false;
        range = 8;
    }
}
