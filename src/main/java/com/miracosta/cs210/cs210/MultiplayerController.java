package com.miracosta.cs210.cs210;

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
        SceneManager assets = SceneManager.getInstance();
        assets.stage.setScene(assets.game);
    }
}
