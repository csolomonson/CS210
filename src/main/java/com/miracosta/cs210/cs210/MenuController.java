package com.miracosta.cs210.cs210;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;



public class MenuController {
    @FXML
    Pane mainMenuPane;

    @FXML
    void handleSinglePlayer() {

        mainMenuPane.getScene().setRoot(AssetManager.getInstance().spMenu);
    }

    @FXML
    void handleMultiplayer() {
        mainMenuPane.getScene().setRoot(AssetManager.getInstance().mpMenu);
    }
}
