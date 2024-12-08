package com.miracosta.cs210.cs210;


import com.miracosta.cs210.cs210.chess.pieces.ChessPiece;
import com.miracosta.cs210.cs210.game.GameBoard;
import com.miracosta.cs210.cs210.game.GameTile;
import com.miracosta.cs210.cs210.minesweeper.MinesweeperTile;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class GameController {

    final double X_OFFSET = 77;
    final double Y_OFFSET = 64;
    final double SQUARE_WIDTH = 67;
    final double SQUARE_HEIGHT = 67;
    final double PIECE_SIZE = 60;
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
        clearNumbers();
        updateBoard();

    }

    public void drawBoard() {
        for (ChessPiece piece : board.getChessPieces()) {
            renderImage(piece.getImage(), piece.getPosition().getRow(), piece.getPosition().getColumn(), PIECE_SIZE);
        }
    }

    public void clearBoard() {
        anchorPane.getChildren().removeIf(node -> node.getClass() == ImageView.class);
    }

    public void updateBoard() {
        clearBoard();
        drawBurns();
        drawBoard();
        updateMinesweeper();
    }

    private void renderImage(Image piece, int r, int c, double size) {
        ImageView pieceImage = new ImageView(piece);
        pieceImage.setFitHeight(size);
        pieceImage.setFitWidth(size);
        pieceImage.setLayoutX(X_OFFSET + SQUARE_WIDTH / 2 - size / 2 + c * SQUARE_WIDTH);
        pieceImage.setLayoutY(Y_OFFSET + SQUARE_HEIGHT / 2 - size / 2 + r * SQUARE_HEIGHT);
        pieceImage.toFront();
        anchorPane.getChildren().add(pieceImage);
    }

    public void updateMinesweeper() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                renderMinesweeperTile(i,j);
            }
        }
    }

    private void renderMinesweeperTile(int r, int c) {
        GameTile tile = board.getGameTile(r, c);
        switch (tile.getDisplayState()) {
            case NUMBER:
                anchorPane.getChildren().add(getMinesweeperNumber(r,c));
                break;
            case BOMB:
                renderImage(ImageManager.getInstance().bomb, r, c, 30);
                break;
            case FLAG:
                renderImage(ImageManager.getInstance().flag, r, c, 30);
        }
    }

    public Label getMinesweeperNumber(int r, int c) {
        GameTile tile = board.getGameTile(r, c);
        int n = tile.getSurroundingBombs();
        Label label = new Label(Integer.toString(n));
        label.setFont(new Font(50));
        label.setAlignment(Pos.CENTER);
        label.setLayoutX(X_OFFSET + SQUARE_WIDTH / 2 - 15 + c * SQUARE_WIDTH);
        label.setLayoutY(Y_OFFSET + SQUARE_HEIGHT / 2 - 35 + r * SQUARE_HEIGHT);
        switch (n) {
            case 1:
                label.setTextFill(Color.BLUE);
                break;
            case 2:
                label.setTextFill(Color.GREEN);
                break;
            case 3:
                label.setTextFill(Color.RED);
                break;
            case 4:
                label.setTextFill(Color.DARKBLUE);
                break;
            case 5:
                label.setTextFill(Color.BROWN);
                break;
            case 6:
                label.setTextFill(Color.CYAN);
                break;
            case 7:
                label.setTextFill(Color.BLACK);
                break;
            case 8:
                label.setTextFill(Color.GREY);
                break;
            default:
                label.setTextFill(Color.GRAY);
        }
        label.toFront();
        return label;
    }

    public void clearNumbers() {
        anchorPane.getChildren().removeIf(node -> node.getClass() == Label.class);
    }

    public void drawBurns() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getGameTile(i,j).getDisplayState() == GameTile.MinesweeperDisplayState.CRATER) {
                    renderImage(ImageManager.getInstance().burn, i, j, 67);
                }
            }
        }
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
            if (mouseEvent.getButton()  == MouseButton.SECONDARY) {
                addFlag(tileY, tileX);
            } else tileClicked(tileY, tileX);
        }

    }

    public void addFlag(int r, int c) {
        board.getGameTile(r,c).flag();
        updateBoard();
        updateMinesweeper();
    }

    public void tileClicked(int r, int c) {
        boolean explode = board.getGameTile(r, c).getMinesweeperTile().getBombState() == MinesweeperTile.BombState.ACTIVE_BOMB;
        if (selection == null) {
            selection = board.getGameTile(r, c);
            highlightLegalMoves(r, c);
            return;
        }
        if (board.move(selection.getRow(), selection.getCol(), r, c)) {
            selection = null;
            removeHighlights();
            updateBoard();
            if (explode) {
                Timeline beat = new Timeline(
                        new KeyFrame(Duration.ZERO, _ -> renderImage(ImageManager.getInstance().explosion, r, c, 70)),
                        new KeyFrame(Duration.seconds(0.5), _ -> updateBoard())
                );
                beat.setAutoReverse(true);
                beat.setCycleCount(1);
                beat.play();
            }
        } else {
            selection = board.getGameTile(r,c);
            removeHighlights();
            highlightLegalMoves(r, c);
        }
    }
}
