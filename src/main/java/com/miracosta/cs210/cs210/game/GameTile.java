package com.miracosta.cs210.cs210.game;

import com.miracosta.cs210.cs210.chess.board.ChessTile;
import com.miracosta.cs210.cs210.chess.pieces.ChessPiece;
import com.miracosta.cs210.cs210.minesweeper.MinesweeperTile;

import java.util.ArrayList;

public class GameTile {
    private final  int row;
    private final int col;
    private final ChessTile chessTile;
    private final MinesweeperTile minesweeperTile;
    private final GameBoard gameBoard;

    public enum MinesweeperDisplayState {
        EMPTY, FLAG, NUMBER, BOMB, CRATER
    }
    public GameTile(int row, int col, GameBoard gameBoard) {
        this.row = row;
        this.col = col;
        this.gameBoard = gameBoard;
        chessTile = gameBoard.getChessBoard().getTile(row, col);
        minesweeperTile = gameBoard.getMinesweeperBoard().getTile(row, col);
    }

    public ChessPiece getChessPiece() {
        return chessTile.getPiece();
    }

    public int getSurroundingBombs() {
        return minesweeperTile.getSurroundingBombs();
    }

    public MinesweeperDisplayState getDisplayState() {
        if (minesweeperTile.getTileState() == MinesweeperTile.TileState.UNMARKED) {
            return MinesweeperDisplayState.EMPTY;
        }
        if (minesweeperTile.getTileState() == MinesweeperTile.TileState.FLAGGED) {
            return MinesweeperDisplayState.FLAG;
        }
        return switch(minesweeperTile.getBombState()) {
            case NO_BOMB ->MinesweeperDisplayState.NUMBER;
            case EXPLODED_BOMB -> MinesweeperDisplayState.CRATER;
            default -> MinesweeperDisplayState.BOMB;
        };
    }

    public int getRow() {
        return row;
    }

    public ArrayList<GameTile> getLegalChessMoves() {
        ArrayList<GameTile> arr = new ArrayList<>();
        if (chessTile.getPiece() == null)  return arr;
        ChessPiece piece = chessTile.getPiece();
        for (ChessTile move : piece.getLegalMoves()) {
            arr.add(gameBoard.getGameTile(move));
        }
        return arr;
    }

    public void trigger() {
        if (minesweeperTile.getBombState() == MinesweeperTile.BombState.ACTIVE_BOMB) {
            chessTile.clearPiece();
            //System.out.println("Explosion at " + row + ", " + col + "!");
            chessTile.getBoard().update();
        }
        minesweeperTile.trigger();
    }

    public void flag() {
        minesweeperTile.flag();
    }

    public void disarm() {
        minesweeperTile.disarm();
    }
    public int getCol() {
        return col;
    }

    public ChessTile getChessTile() {
        return chessTile;
    }

    public MinesweeperTile getMinesweeperTile() {
        return minesweeperTile;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }
}
