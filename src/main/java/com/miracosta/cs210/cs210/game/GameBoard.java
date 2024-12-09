package com.miracosta.cs210.cs210.game;

import com.miracosta.cs210.cs210.chess.board.ChessBoard;
import com.miracosta.cs210.cs210.chess.board.ChessTile;
import com.miracosta.cs210.cs210.chess.pieces.ChessPiece;
import com.miracosta.cs210.cs210.minesweeper.MinesweeperBoard;
import com.miracosta.cs210.cs210.minesweeper.MinesweeperTile;

import java.util.ArrayList;

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

                if (chessBoard.getTile(i,j).isOccupied()) {
                    board[i][j].disarm();
                    board[i][j].trigger();
                }
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

    public boolean move(int row1, int col1, int row2, int col2) {
        if (chessBoard.move(row1, col1, row2, col2)) {
            getGameTile(row2, col2).trigger();
            return true;
        }
        return false;
    }

    public GameTile getGameTile(ChessTile chessTile) {
        return getGameTile(chessTile.getRow(), chessTile.getColumn());
    }

    public ArrayList<ChessPiece> getChessPieces() {
        return chessBoard.getPieces();
    }
    public GameTile getGameTile(MinesweeperTile minesweeperTile) {
        return getGameTile(minesweeperTile.getRow(), minesweeperTile.getColumn());
    }
}
