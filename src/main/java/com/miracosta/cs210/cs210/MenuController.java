package com.miracosta.cs210.cs210;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;



public class MenuController {
    @FXML
    Pane mainMenuPane;

    @FXML
    void handleSinglePlayer() {
        SceneManager assets = SceneManager.getInstance();
        assets.stage.setScene(assets.spMenu);
    }

    @FXML
    void handleMultiplayer() {
        SceneManager assets = SceneManager.getInstance();
        assets.stage.setScene(assets.mpMenu);
    }
}
