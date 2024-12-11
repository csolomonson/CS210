package com.miracosta.cs210.cs210.chess.pieces;

import com.miracosta.cs210.cs210.ImageManager;
import javafx.scene.image.Image;

/**
 * Rook piece that can move laterally and longitudinally
 * TODO castling
 */
public class Rook extends SlidingPiece{
    /**
     * Create a Rook of a given Color
     * @param pieceColor Color of the Rook
     */
    public Rook(PieceColor pieceColor) {
        super(pieceColor);
        setRookProperties();
    }

    @Override
    public String toString() {
        if (getColor() == PieceColor.WHITE) return "♖";
        return "♜";
    }

    @Override
    public Image getImage() {
        ImageManager imageManager = ImageManager.getInstance();
        if (getColor() == PieceColor.WHITE) return imageManager.whiteRook;
        return imageManager.blackRook;
    }

    @Override
    public double getValue() {
        return 5;
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
