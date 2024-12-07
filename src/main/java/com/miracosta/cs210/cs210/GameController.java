package com.miracosta.cs210.cs210;

import javafx.event.ActionEvent;
import javafx.scene.control.TitledPane;

public class GameController {
    public TitledPane titledPane;

    public void handleUndoMove(ActionEvent event) {
    }

    public void handleRestartGame(ActionEvent event) {
    }

    public void handleSurrendering(ActionEvent event) {
    }

    public void handleExitToMenu(ActionEvent event) {
        AssetManager assets = AssetManager.getInstance();
        assets.stage.setScene(assets.mainMenu);
    }
}
