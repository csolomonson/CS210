package com.miracosta.cs210.cs210;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameApplication extends Application {
    SceneManager assets;
    @Override
    public void start(Stage stage) throws Exception {
        assets = SceneManager.getInstance();
        assets.stage = stage;
        Scene scene = assets.mainMenu;
        stage.getIcons().add(ImageManager.getInstance().bomb);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
