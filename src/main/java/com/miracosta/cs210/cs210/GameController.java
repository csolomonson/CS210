package com.miracosta.cs210.cs210;


import com.miracosta.cs210.cs210.chess.pieces.ChessPiece;
import com.miracosta.cs210.cs210.game.GameBoard;
import com.miracosta.cs210.cs210.game.GameTile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

public class GameController {

    final double X_OFFSET = 77;
    final double Y_OFFSET = 64;
    final double SQUARE_WIDTH = 67;
    final double SQUARE_HEIGHT = 67;
    final double PIECE_SIZE = 50;
    public AnchorPane anchorPane;
    ImageManager images;
    GameBoard board;
    GameTile selection;

    public GameController() {
        images = ImageManager.getInstance();

    }
    public void initialize() {
        board = new GameBoard();
        updateBoard();
    }

    public void handleUndoMove() {
        clearBoard();
        removeHighlights();
    }

    public void handleRestartGame() {
        board = new GameBoard();
        removeHighlights();
        updateBoard();
    }

    public void drawBoard() {
        for (ChessPiece piece : board.getChessPieces()) {
            renderPiece(piece.getImage(), piece.getPosition().getRow(), piece.getPosition().getColumn());
        }
    }

    public void clearBoard() {
        anchorPane.getChildren().removeIf(node -> node.getClass() == ImageView.class);
    }

    public void updateBoard() {
        clearBoard();
        drawBoard();
    }

    private void renderPiece(Image piece, int r, int c) {
        ImageView pieceImage = new ImageView(piece);
        pieceImage.setFitHeight(PIECE_SIZE);
        pieceImage.setFitWidth(PIECE_SIZE);
        pieceImage.setLayoutX(X_OFFSET + SQUARE_WIDTH / 2 - PIECE_SIZE / 2 + c * SQUARE_WIDTH);
        pieceImage.setLayoutY(Y_OFFSET + SQUARE_HEIGHT / 2 - PIECE_SIZE / 2 + r * SQUARE_HEIGHT);
        pieceImage.toFront();
        anchorPane.getChildren().add(pieceImage);
    }

    public void handleSurrendering() {
        updateBoard();
    }

    public void handleExitToMenu() {
        SceneManager assets = SceneManager.getInstance();
        assets.stage.setScene(assets.mainMenu);
    }

    public void highlightLegalMoves(int r, int c) {
        GameTile tile = board.getGameTile(r, c);
        for (GameTile t : tile.getLegalChessMoves()) {
            highlightSquare(t.getRow(), t.getCol());
        }
    }

    public void highlightSquare(int r, int c) {
        Region rect = new Region();
        rect.setPrefWidth(SQUARE_WIDTH);
        rect.setPrefHeight(SQUARE_HEIGHT);
        rect.setStyle("-fx-background-color: transparent; -fx-border-style: solid; -fx-border-width: 5; -fx-border-color: red;");
        rect.setLayoutX(c*SQUARE_WIDTH + X_OFFSET);
        rect.setLayoutY(r*SQUARE_HEIGHT + Y_OFFSET);
        anchorPane.getChildren().add(rect);
    }

    public void removeHighlights() {
        anchorPane.getChildren().removeIf(node -> node.getClass() == Region.class);
    }

    public void handleBoardClicked(MouseEvent mouseEvent) {
        //System.out.println("X: " + mouseEvent.getX() + ", Y: " + mouseEvent.getY());
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();

        int tileX = (int) ((x - X_OFFSET) /  SQUARE_WIDTH);
        int tileY = (int) ((y - Y_OFFSET) /  SQUARE_HEIGHT);

        if (tileX >= 0 && tileX < 8 && tileY >= 0 && tileY < 8) {
            //System.out.println(tileX + " " + tileY);
            tileClicked(tileY, tileX);
        }
    }

    public void tileClicked(int r, int c) {
        if (selection == null) {
            selection = board.getGameTile(r, c);
            highlightLegalMoves(r, c);
            return;
        }
        if (board.move(selection.getRow(), selection.getCol(), r, c)) {
            selection = null;
            removeHighlights();
            updateBoard();
        } else {
            selection = board.getGameTile(r,c);
            removeHighlights();
            highlightLegalMoves(r, c);
        }
    }
}
