package com.miracosta.cs210.cs210.game;

import com.miracosta.cs210.cs210.chess.board.ChessBoard;
import com.miracosta.cs210.cs210.chess.board.ChessTile;
import com.miracosta.cs210.cs210.chess.pieces.ChessPiece;
import com.miracosta.cs210.cs210.chess.pieces.Color;
import com.miracosta.cs210.cs210.minesweeper.MinesweeperBoard;
import com.miracosta.cs210.cs210.minesweeper.MinesweeperTile;

import java.util.ArrayList;
import java.util.Objects;

public class GameBoard implements Cloneable {
    private final int NUM_ROWS = 8;
    private final int NUM_COLS = 8;
    private ChessBoard chessBoard;
    private MinesweeperBoard minesweeperBoard;
    private GameTile[][] board;
    GameBoard previous;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameBoard gameBoard = (GameBoard) o;
        return Objects.equals(getChessBoard(), gameBoard.getChessBoard()) && Objects.equals(getMinesweeperBoard(), gameBoard.getMinesweeperBoard());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getChessBoard(), getMinesweeperBoard());
    }

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

    public GameBoard getPrevious() {
        return previous;
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
        GameBoard clone = clone();
        if (chessBoard.move(row1, col1, row2, col2)) {
            previous = clone;
            getGameTile(row2, col2).trigger();
            chessBoard.update();
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

    public Color getColorToMove() {
        return chessBoard.getColorToMove();
    }

    @Override
    public GameBoard clone() {
        try {
            GameBoard clone = (GameBoard) super.clone();
            clone.chessBoard = chessBoard.clone();
            clone.minesweeperBoard = minesweeperBoard.clone();
            if (previous != null)  clone.previous = this.previous.clone();
            clone.board = new GameTile[NUM_ROWS][NUM_COLS];
            for (int i = 0; i < NUM_ROWS; i++) {
                for (int j = 0; j < NUM_COLS; j++) {
                    clone.board[i][j] = new GameTile(i, j, clone);
                }
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
