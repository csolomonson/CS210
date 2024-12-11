package com.miracosta.cs210.cs210;

import com.miracosta.cs210.cs210.game.GameBoard;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MultiplayerController {
    public TextField minutesTextField;
    public TextField minesTextField;
    public TextField player1NameField;
    public TextField player2NameField;

    @FXML
    void handleBackToMenu() {
        SceneManager assets = SceneManager.getInstance();
        assets.stage.setScene(assets.mainMenu);

    }
    @FXML
    void handleStartGame() {
        GameSettings.getInstance().setNumMines(Integer.parseInt(minesTextField.getText()));
        GameSettings.getInstance().setMultiplayer(true);
        GameSettings.getInstance().setBoard(new GameBoard(GameSettings.getInstance().getNumMines()));
        SceneManager assets = SceneManager.getInstance();
        assets.initGame();
        assets.stage.setScene(assets.game);
    }
}
