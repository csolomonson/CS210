package com.miracosta.cs210.cs210.bots;

import com.miracosta.cs210.cs210.chess.gameover.GameOver;
import com.miracosta.cs210.cs210.chess.pieces.PieceColor;
import com.miracosta.cs210.cs210.game.GameBoard;

public abstract class GameBot {
    protected GameBoard gameBoard;
    protected PieceColor turn;

    public GameBot(GameBoard gameBoard, PieceColor turn) {
        setGameBoard(gameBoard);
        setTurn(turn);
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public PieceColor getTurn() {
        return turn;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void setTurn(PieceColor turn) {
        this.turn = turn;
    }

    abstract public void botMove() throws GameOver;

}
