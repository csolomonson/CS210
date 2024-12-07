package com.miracosta.cs210.cs210;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Singleton class to handle loading of FX and image resources
 */
public class AssetManager {
    private static AssetManager instance;
    public Parent mainMenu;
    public Parent spMenu;
    public Parent mpMenu;
    public Parent game;

    private AssetManager() {
        loadFXAssets();
    }

    public static AssetManager getInstance() {
        if (instance == null) {
            instance = new AssetManager();
        }
        return instance;
    }

    private void loadFXAssets() {
        try {
            mainMenu = new FXMLLoader(GameApplication.class.getResource("MainMenu.fxml")).load();
        } catch (IOException e) {
            System.out.println("main menu");
        }
        try {
            spMenu = new FXMLLoader(GameApplication.class.getResource("SPGameSetup.fxml")).load();
        } catch (IOException e) {
            System.out.println("sp");
        }
        try {
            mpMenu = new FXMLLoader(GameApplication.class.getResource("MPGameSetup.fxml")).load();
        } catch (IOException e) {
            System.out.println("mp");
        }
        try {
            game = new FXMLLoader(GameApplication.class.getResource("chessBoard.fxml")).load();
        } catch (IOException e) {
            System.out.println("game");
        }
    }
}
