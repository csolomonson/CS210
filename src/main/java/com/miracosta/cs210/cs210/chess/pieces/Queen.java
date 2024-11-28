package com.miracosta.cs210.cs210.chess.pieces;

/**
 * Queen piece that can move laterally, longitudinally, and diagonally
 */
public class Queen extends SlidingPiece{
    /**
     * Create a WHITE Queen
     */
    public Queen() {
        super();
        setQueenProperties();
    }

    /**
     * Create a Queen of a given Color
     * @param color Color of the Queen
     */
    public Queen(Color color) {
        super(color);
        setQueenProperties();
    }

    @Override
    public String toString() {
        if (getColor() == Color.WHITE) return "♕";
        return "♛";
    }

    /**
     * Set SlidingPiece properties (directions and range of motion)
     */
    private void setQueenProperties() {
        lateral = true;
        longitudinal = true;
        diagonal = true;
        range = 8;
    }

}
