package com.miracosta.cs210.cs210;

import com.miracosta.cs210.cs210.bots.BotDifficulty;
import com.miracosta.cs210.cs210.bots.GameBot;
import com.miracosta.cs210.cs210.bots.MinimaxBot;
import com.miracosta.cs210.cs210.bots.RandomBot;
import com.miracosta.cs210.cs210.chess.pieces.PieceColor;
import com.miracosta.cs210.cs210.game.GameBoard;

public class GameSettings {

    private static GameSettings instance;
    private boolean multiplayer;
    private BotDifficulty difficulty;
    private PieceColor botColor;
    private int numMines;
    private GameBot bot;
    private GameBoard board;

    private GameSettings() {
        multiplayer = false;
        difficulty = BotDifficulty.EASY;
        botColor = PieceColor.BLACK;
        numMines = 10;
        bot = new RandomBot();
    }

    public static GameSettings getInstance() {
        if (instance == null) instance = new GameSettings();
        return instance;
    }

    public boolean isMultiplayer() {
        return multiplayer;
    }

    public void setMultiplayer(boolean multiplayer) {
        this.multiplayer = multiplayer;

    }

    public BotDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(BotDifficulty difficulty) {
        this.difficulty = difficulty;
        switch (difficulty) {
            case EASY:
                bot = new RandomBot();
                break;
            case MEDIUM:
                bot = new MinimaxBot(2);
                break;
            case HARD:
                bot = new MinimaxBot(4);
        }
    }

    public PieceColor getBotColor() {
        return botColor;
    }

    public void setBotColor(PieceColor botColor) {
        this.botColor = botColor;
    }

    public int getNumMines() {
        return numMines;
    }

    public void setNumMines(int numMines) {
        this.numMines = numMines;
    }

    public GameBot getGameBot(GameBoard board) {
        bot.setGameBoard(board);
        bot.setTurn(botColor);
        return bot;
    }

    public GameBoard getBoard() {
        return board;
    }

    public void setBoard(GameBoard board) {
        this.board = board;
        if (!multiplayer && this.board.getColorToMove() == botColor) getGameBot(board).botMove();
    }
}
