package com.miracosta.cs210.cs210.chess.pieces;

public class Queen extends SlidingPiece{
    public Queen() {
        super();
        setQueenProperties();
    }
    public Queen(Color color) {
        super(color);
        setQueenProperties();
    }

    private void setQueenProperties() {
        lateral = true;
        longitudinal = true;
        diagonal = true;
        range = 8;
    }

}
