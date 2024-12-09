package com.miracosta.cs210.cs210.chess.pieces;

import javafx.scene.image.Image;

/**
 * Bishop piece that can move diagonally
 */
public class Bishop extends SlidingPiece{
    /**
     * Create a Bishop of a given Color
     * @param color Color of the Bishop
     */
    public Bishop(Color color) {
        super(color);
        setBishopProperties();
    }

    @Override
    public String toString() {
        if (getColor() == Color.WHITE) return "♗";
        return "♝";
    }

    @Override
    public Image getImage() {
        if (getColor() == Color.WHITE) return imageManager.whiteBishop;
        return imageManager.blackBishop;
    }

    /**
     * Create a WHITE Bishop
     */
    public Bishop() {
        super();
        setBishopProperties();
    }

    /**
     * Set SlidingPiece properties (directions and range of motion)
     */
    private void setBishopProperties() {
        diagonal = true;
        lateral = false;
        longitudinal = false;
        range = 8;
    }
}
