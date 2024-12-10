package com.miracosta.cs210.cs210;

import com.miracosta.cs210.cs210.bots.BotDifficulty;
import com.miracosta.cs210.cs210.chess.pieces.PieceColor;
import com.miracosta.cs210.cs210.game.GameBoard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SinglePlayerController {
    public Button whiteColorButton;
    public Button blackColorButton;
    public TextField minesTextField;
    public Circle whiteCircle;
    public Circle blackCircle;
    public Button mediumButton;
    public Button easyButton;
    public Button hardButton;


    public void initialize() {
        easyButton.setDefaultButton(true);
    }

    @FXML
    void handleBackToMenu() {
        SceneManager assets = SceneManager.getInstance();
        assets.stage.setScene(assets.mainMenu);
    }
    @FXML
    void handleStartGame() {
        GameSettings.getInstance().setMultiplayer(false);
        GameSettings.getInstance().setNumMines(Integer.parseInt(minesTextField.getText()));
        GameSettings.getInstance().setBoard(new GameBoard(GameSettings.getInstance().getNumMines()));
        SceneManager assets = SceneManager.getInstance();
        assets.initGame();
        assets.stage.setScene(assets.game);
    }

    public void handleMediumDifficulty(ActionEvent event) {
        GameSettings.getInstance().setDifficulty(BotDifficulty.MEDIUM);
        mediumButton.setDefaultButton(true);
        easyButton.setDefaultButton(false);
        hardButton.setDefaultButton(false);
    }

    public void handleEasyDifficulty(ActionEvent event) {
        GameSettings.getInstance().setDifficulty(BotDifficulty.EASY);
        mediumButton.setDefaultButton(false);
        easyButton.setDefaultButton(true);
        hardButton.setDefaultButton(false);
    }

    public void handleHardDifficulty(ActionEvent event) {
        GameSettings.getInstance().setDifficulty(BotDifficulty.HARD);
        mediumButton.setDefaultButton(false);
        easyButton.setDefaultButton(false);
        hardButton.setDefaultButton(true);
    }

    public void handleChooseWhite(MouseEvent mouseEvent) {
        GameSettings.getInstance().setBotColor(PieceColor.BLACK);
        whiteCircle.setStroke(Color.BLUE);
        blackCircle.setStroke(Color.BLACK);
        blackCircle.setStrokeWidth(1);
        whiteCircle.setStrokeWidth(4);
    }

    public void handleChooseBlack(MouseEvent mouseEvent) {
        GameSettings.getInstance().setBotColor(PieceColor.WHITE);
        whiteCircle.setStroke(Color.BLACK);
        blackCircle.setStroke(Color.BLUE);
        blackCircle.setStrokeWidth(4);
        whiteCircle.setStrokeWidth(1);
    }
}
