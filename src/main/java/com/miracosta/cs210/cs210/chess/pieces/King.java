package com.miracosta.cs210.cs210.chess.pieces;

import com.miracosta.cs210.cs210.ImageManager;
import javafx.scene.image.Image;

/**
 * King piece that can move 1 space in each direction
 * TODO Castling
 * TODO Can't walk into check
 */
public class King extends SlidingPiece{
    public King(PieceColor pieceColor) {
        super(pieceColor);
        setKingProperties();
    }

    @Override
    public String toString() {
        if (getColor() == PieceColor.WHITE) return "♔";
        return "♚";
    }

    @Override
    public Image getImage() {
        ImageManager imageManager = ImageManager.getInstance();
        if (getColor() == PieceColor.WHITE) return imageManager.whiteKing;
        return imageManager.blackKing;
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
