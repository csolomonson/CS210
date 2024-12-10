package com.miracosta.cs210.cs210;


import com.miracosta.cs210.cs210.bots.BotDifficulty;
import com.miracosta.cs210.cs210.bots.GameBot;
import com.miracosta.cs210.cs210.bots.RandomBot;
import com.miracosta.cs210.cs210.chess.board.ChessBoard;
import com.miracosta.cs210.cs210.chess.pieces.ChessPiece;
import com.miracosta.cs210.cs210.chess.pieces.PieceColor;
import com.miracosta.cs210.cs210.game.GameBoard;
import com.miracosta.cs210.cs210.game.GameTile;
import com.miracosta.cs210.cs210.minesweeper.MinesweeperTile;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
    boolean multiplayer = false;
    BotDifficulty difficulty = BotDifficulty.EASY;
    GameBot bot;
    PieceColor botColor = PieceColor.BLACK;

    public GameController() {
        images = ImageManager.getInstance();

    }
    public void initialize() {
        board = new GameBoard();
        updateBoard();
        switch (difficulty) {
            case EASY:
                bot = new RandomBot(board, botColor);
        }
        attemptBotMove();
    }

    public void attemptBotMove() {
        if (!multiplayer && board.getColorToMove() == botColor) {
            bot.setGameBoard(board); //in case undo changes board reference
            bot.botMove();
            updateBoard();
        }
    }

    public void handleUndoMove() {
        removeHighlights();
        GameBoard previous = board;
        if (board.getPrevious() != null) previous = board.getPrevious();
        if (!multiplayer && previous.getColorToMove() == botColor && previous.getPrevious() != null) {
            previous = previous.getPrevious();
        } else previous = board;
        board = previous;
        board.getChessBoard().update();
        updateBoard();
    }

    public void handleRestartGame() {
        board = new GameBoard();
        removeHighlights();
        clearNumbers();
        updateBoard();
        attemptBotMove();

    }

    public void drawBoard() {
        for (ChessPiece piece : board.getChessPieces()) {
            renderImage(piece.getImage(), piece.getPosition().getRow(), piece.getPosition().getColumn(), PIECE_SIZE, -12,0);
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

    private void renderImage(Image piece, int r, int c, double size, double xOff, double yOff) {
        ImageView pieceImage = new ImageView(piece);
        pieceImage.setFitHeight(size);
        pieceImage.setFitWidth(size);
        pieceImage.setLayoutX(X_OFFSET + SQUARE_WIDTH / 2 - size / 2 + c * SQUARE_WIDTH + xOff);
        pieceImage.setLayoutY(Y_OFFSET + SQUARE_HEIGHT / 2 - size / 2 + r * SQUARE_HEIGHT + yOff);
        pieceImage.toFront();
        anchorPane.getChildren().add(pieceImage);
    }

    private void renderImage(Image piece, int r, int c, double size) {
        renderImage(piece, r, c, size, 0, 0);
    }

    public void updateMinesweeper() {
        clearNumbers();
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
                renderImage(ImageManager.getInstance().bomb, r, c, 30, SQUARE_WIDTH/4, -SQUARE_HEIGHT/4);
                break;
            case FLAG:
                renderImage(ImageManager.getInstance().flag, r, c, 30);
        }
    }

    public Text getMinesweeperNumber(int r, int c) {
        GameTile tile = board.getGameTile(r, c);
        int n = tile.getSurroundingBombs();
        Text number = new Text(Integer.toString(n));
        number.setFont(new Font(40));
        number.setLayoutX(X_OFFSET + SQUARE_WIDTH / 2 + 5 + c * SQUARE_WIDTH);
        number.setLayoutY(Y_OFFSET + SQUARE_HEIGHT / 2 + 3 + r * SQUARE_HEIGHT);
        number.setStroke(Color.BLACK);
        number.setStrokeWidth(1);
        switch (n) {
            case 1:
                number.setFill(Color.BLUE);
                break;
            case 2:
                number.setFill(Color.GREEN);
                break;
            case 3:
                number.setFill(Color.RED);
                break;
            case 4:
                number.setFill(Color.DARKBLUE);
                break;
            case 5:
                number.setFill(Color.BROWN);
                break;
            case 6:
                number.setFill(Color.CYAN);
                break;
            case 7:
                number.setFill(Color.BLACK);
                break;
            case 8:
                number.setFill(Color.GREY);
                break;
            default:
                number.setFill(Color.GRAY);
        }
        number.toFront();
        return number;
    }

    public void clearNumbers() {
        anchorPane.getChildren().removeIf(node -> node.getClass() == Text.class);
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
            attemptBotMove();
        } else {
            selection = board.getGameTile(r,c);
            removeHighlights();
            highlightLegalMoves(r, c);
        }
    }
}
