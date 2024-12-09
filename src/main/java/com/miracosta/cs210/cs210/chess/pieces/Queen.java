package com.miracosta.cs210.cs210.chess.pieces;

import com.miracosta.cs210.cs210.ImageManager;
import javafx.scene.image.Image;

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
     * @param pieceColor Color of the Queen
     */
    public Queen(PieceColor pieceColor) {
        super(pieceColor);
        setQueenProperties();
    }

    @Override
    public String toString() {
        if (getColor() == PieceColor.WHITE) return "♕";
        return "♛";
    }

    @Override
    public Image getImage() {
        ImageManager imageManager = ImageManager.getInstance();
        if (getColor() == PieceColor.WHITE) return imageManager.whiteQueen;
        return imageManager.blackQueen;
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
