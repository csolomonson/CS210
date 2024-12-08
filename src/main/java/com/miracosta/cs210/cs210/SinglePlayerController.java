package com.miracosta.cs210.cs210;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SinglePlayerController {
    public Button whiteColorButton;
    public Button blackColorButton;
    public TextField minutesTextField;
    public TextField minesTextField;

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

    public void handleMediumDifficulty(ActionEvent event) {
    }

    public void handleEasyDifficulty(ActionEvent event) {
    }

    public void handleHardDifficulty(ActionEvent event) {
    }

    public void handleColorSelection(ActionEvent event) {
    }
}
