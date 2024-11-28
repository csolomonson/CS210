package com.miracosta.cs210.cs210.chess.pieces;

/**
 * King piece that can move 1 space in each direction
 * TODO Castling
 * TODO Can't walk into check
 */
public class King extends SlidingPiece{
    public King(Color color) {
        super(color);
        setKingProperties();
    }

    @Override
    public String toString() {
        if (getColor() == Color.WHITE) return "♔";
        return "♚";
    }

    public King() {
        super();
        setKingProperties();
    }

    /**
     * Set SlidingPiece properties (directions and range of motion)
     */
    private void setKingProperties() {
        diagonal = true;
        lateral = true;
        longitudinal = true;
        range = 1;
    }
}
