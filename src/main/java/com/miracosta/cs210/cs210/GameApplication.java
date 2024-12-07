package com.miracosta.cs210.cs210;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameApplication extends Application {
    AssetManager assets;
    @Override
    public void start(Stage stage) throws Exception {
        assets = AssetManager.getInstance();
        assets.stage = stage;
        Scene scene = assets.mainMenu;
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
