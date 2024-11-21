package com.miracosta.cs210.cs210.chess.pieces;

public class King extends SlidingPiece{
    public King(Color color) {
        super(color);
        setKingProperties();
    }
    public King() {
        super();
        setKingProperties();
    }

    private void setKingProperties() {
        diagonal = true;
        lateral = true;
        longitudinal = true;
        range = 1;
    }
}
