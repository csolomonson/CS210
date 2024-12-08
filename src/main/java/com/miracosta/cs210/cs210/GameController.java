package com.miracosta.cs210.cs210;


import com.miracosta.cs210.cs210.game.GameBoard;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class GameController {

    final double X_OFFSET = 77;
    final double Y_OFFSET = 64;
    final double SQUARE_WIDTH = 67;
    final double SQUARE_HEIGHT = 67;
    final double PIECE_SIZE = 50;
    public AnchorPane anchorPane;
    ImageManager images;

    public GameController() {
        images = ImageManager.getInstance();

    }
    public void initialize() {
        GameBoard board = new GameBoard();
        for (int i = 0; i < 64; i++) {
            ImageView blackBishop = new ImageView(images.blackBishop);
            renderPiece(blackBishop, i % 8, i / 8);
        }
    }

    public void handleUndoMove() {
        clearBoard();
    }

    public void handleRestartGame() {
        images = ImageManager.getInstance();
    }

    public void clearBoard() {
        anchorPane.getChildren().removeIf(node -> node.getClass() == ImageView.class);
    }

    private void renderPiece(ImageView pieceImage, int c, int r) {
        pieceImage.setFitHeight(PIECE_SIZE);
        pieceImage.setFitWidth(PIECE_SIZE);
        pieceImage.setLayoutX(X_OFFSET + SQUARE_WIDTH / 2 - PIECE_SIZE / 2 + c * SQUARE_WIDTH);
        pieceImage.setLayoutY(Y_OFFSET + SQUARE_HEIGHT / 2 - PIECE_SIZE / 2 + r * SQUARE_HEIGHT);
        anchorPane.getChildren().add(pieceImage);
    }

    public void handleSurrendering() {
    }

    public void handleExitToMenu() {
        SceneManager assets = SceneManager.getInstance();
        assets.stage.setScene(assets.mainMenu);
    }
}
