package com.miracosta.cs210.cs210.chess.pieces;

import com.miracosta.cs210.cs210.ImageManager;
import javafx.scene.image.Image;

/**
 * Bishop piece that can move diagonally
 */
public class Bishop extends SlidingPiece{
    /**
     * Create a Bishop of a given Color
     * @param pieceColor Color of the Bishop
     */
    public Bishop(PieceColor pieceColor) {
        super(pieceColor);
        setBishopProperties();
    }

    @Override
    public String toString() {
        if (getColor() == PieceColor.WHITE) return "♗";
        return "♝";
    }

    @Override
    public Image getImage() {
        ImageManager imageManager = ImageManager.getInstance();
        if (getColor() == PieceColor.WHITE) return imageManager.whiteBishop;
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
