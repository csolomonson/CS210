package com.miracosta.cs210.cs210.game;

import com.miracosta.cs210.cs210.chess.board.ChessBoard;
import com.miracosta.cs210.cs210.chess.board.ChessTile;
import com.miracosta.cs210.cs210.minesweeper.MinesweeperBoard;
import com.miracosta.cs210.cs210.minesweeper.MinesweeperTile;

public class GameBoard {
    private final int NUM_ROWS = 8;
    private final int NUM_COLS = 8;
    private ChessBoard chessBoard;
    private MinesweeperBoard minesweeperBoard;
    private GameTile[][] board;

    public GameBoard(int numBombs) {
        chessBoard = new ChessBoard();
        minesweeperBoard = new MinesweeperBoard(numBombs);
        board = new GameTile[NUM_ROWS][NUM_COLS];
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                board[i][j] = new GameTile(i,j,this);
            }
        }
    }

    public GameBoard() {
        this(10);
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public MinesweeperBoard getMinesweeperBoard() {
        return minesweeperBoard;
    }

    public GameTile getGameTile(int row, int col) {
        return board[row][col];
    }

    public GameTile getGameTile(ChessTile chessTile) {
        return getGameTile(chessTile.getRow(), chessTile.getColumn());
    }

    public GameTile getGameTile(MinesweeperTile minesweeperTile) {
        return getGameTile(minesweeperTile.getRow(), minesweeperTile.getColumn());
    }
}
