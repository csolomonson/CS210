package com.miracosta.cs210.cs210;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Singleton class to handle loading of FX and image resources
 */
public class SceneManager {
    private static SceneManager instance;
    public Scene mainMenu;
    public Scene spMenu;
    public Scene mpMenu;
    public Scene game;
    public Stage stage;

    private SceneManager() {
        loadFXAssets();
    }

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    private void loadFXAssets() {
        try {
            mainMenu = new Scene(new FXMLLoader(GameApplication.class.getResource("MainMenu.fxml")).load(), 299, 187);
        } catch (IOException e) {
            System.out.println("main menu");
        }
        try {
            spMenu = new Scene(new FXMLLoader(GameApplication.class.getResource("SPGameSetup.fxml")).load(),327,508);
        } catch (IOException e) {
            System.out.println("sp");
        }
        try {
            mpMenu = new Scene(new FXMLLoader(GameApplication.class.getResource("MPGameSetup.fxml")).load(),327,512);
        } catch (IOException e) {
            System.out.println("mp");
        }
        try {
            game = new Scene(new FXMLLoader(GameApplication.class.getResource("chessBoard.fxml")).load(), 910, 689);
        } catch (IOException e) {
            System.out.println("game");
        }
    }
}
