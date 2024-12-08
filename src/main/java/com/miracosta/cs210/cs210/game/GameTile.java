package com.miracosta.cs210.cs210.game;

import com.miracosta.cs210.cs210.chess.board.ChessTile;
import com.miracosta.cs210.cs210.minesweeper.MinesweeperTile;

public class GameTile {
    private final  int row;
    private final int col;
    private ChessTile chessTile;
    private MinesweeperTile minesweeperTile;
    private final GameBoard gameBoard;

    public GameTile(int row, int col, GameBoard gameBoard) {
        this.row = row;
        this.col = col;
        this.gameBoard = gameBoard;
        chessTile = gameBoard.getChessBoard().getTile(row, col);
        minesweeperTile = gameBoard.getMinesweeperBoard().getTile(row, col);
    }


}
