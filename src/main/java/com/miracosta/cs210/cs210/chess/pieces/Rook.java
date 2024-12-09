package com.miracosta.cs210.cs210.chess.pieces;

import javafx.scene.image.Image;

/**
 * Rook piece that can move laterally and longitudinally
 * TODO castling
 */
public class Rook extends SlidingPiece{
    /**
     * Create a Rook of a given Color
     * @param color Color of the Rook
     */
    public Rook(Color color) {
        super(color);
        setRookProperties();
    }

    @Override
    public String toString() {
        if (getColor() == Color.WHITE) return "♖";
        return "♜";
    }

    @Override
    public Image getImage() {
        if (getColor() == Color.WHITE) return imageManager.whiteRook;
        return imageManager.blackRook;
    }

    /**
     * Create a WHITE Rook
     */
    public Rook() {
        super();
        setRookProperties();
    }

    /**
     * Set SlidingPiece properties (directions and range of motion)
     */
    private void setRookProperties() {
        lateral = true;
        longitudinal = true;
        diagonal = false;
        range = 8;
    }
}
