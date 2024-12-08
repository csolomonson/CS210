package com.miracosta.cs210.cs210;

import javafx.scene.image.Image;

import java.util.Objects;

public class ImageManager {
    public Image whiteQueen;
    public Image whiteKing;
    public Image whiteRook;
    public Image whiteBishop;
    public Image whiteKnight;
    public Image whitePawn;
    public Image blackQueen;
    public Image blackKing;
    public Image blackRook;
    public Image blackKnight;
    public Image blackBishop;
    public Image blackPawn;

    public Image bomb;
    public Image flag;
    public Image explosion;

    private static ImageManager instance;
    private ImageManager() {
        loadImages();
    }
    public static ImageManager getInstance() {
        if (instance == null) instance = new ImageManager();
        return instance;
    }
    private void loadImages() {
        blackBishop = loadImage("black_bishop.png");
        blackKing = loadImage("black_king.png");
        blackKnight = loadImage("black_knight.png");
        blackPawn = loadImage("black_pawn.png");
        blackQueen = loadImage("black_queen.png");
        blackRook = loadImage("black_rook.png");

        whiteBishop = loadImage("white_bishop.png");
        whiteKing = loadImage("white_king.png");
        whiteKnight = loadImage("white_knight.png");
        whitePawn = loadImage("white_pawn.png");
        whiteQueen = loadImage("white_queen.png");
        whiteRook = loadImage("white_rook.png");

        bomb = loadImage("bomb.png");
        flag = loadImage("flag.png");
        explosion = loadImage("bang.png");
    }
    private Image loadImage(String name) {
        return new Image(Objects.requireNonNull(getClass().getResource(name)).toExternalForm());
    }
}
