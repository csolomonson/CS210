package com.miracosta.cs210.cs210.minesweeper;

import java.util.HashSet;
import java.util.Objects;

/**
 * A single tile on a minesweeper board
 */
public class MinesweeperTile {
    public enum BombState {
        NO_BOMB, ACTIVE_BOMB, INACTIVE_BOMB, EXPLODED_BOMB
    }
    public enum TileState {
        UNMARKED, UNCOVERED, FLAGGED
    }
    private BombState bombState;
    private TileState tileState;
    private int surroundingBombs;
    private HashSet<MinesweeperTile> neighbors;
    private int row;
    private int col;
    MinesweeperBoard board;

    /**
     * Create a tile
     * @param row Row number of this tile
     * @param col Column number of this tile
     * @param state Whether the tile is a bomb or not
     * @param board Reference to the MinesweeperBoard this tile is a part of
     */
    public MinesweeperTile(int row, int col, BombState state, MinesweeperBoard board) {
        this.row = row;
        this.col = col;
        this.bombState = state;
        neighbors = new HashSet<>();
        this.board = board;
        surroundingBombs = 0;
        tileState = TileState.UNMARKED;
    }

    /**
     * Set whether this tile is a bomb
     * @param state Enum representing bomb status (only set to NO_BOMB, INACTIVE_BOMB, or ACTIVE_BOMB)
     */
    public void setBombState(BombState state) {
        this.bombState = state;
    }

    /**
     * Get an enum representing whether this tile is a bomb
     * @return NO_BOMB means no bomb, all others are states of bombs
     */
    public BombState getBombState() {
        return bombState;
    }

    /**
     * Get the number of bombs in the eight surrounding tiles
     * @return Number to display on a revealed non-bomb tile
     */
    public int getSurroundingBombs() {
        return surroundingBombs;
    }

    /**
     * Add neighboring tiles to the HashSet, and update the number of surrounding bombs
     */
    public void calculateNeighbors() {
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for (int colOffset = -1; colOffset <= 1; colOffset++) {
                MinesweeperTile tile = board.getTile(row + rowOffset, col + colOffset);
                if (tile == null) continue;
                if (tile == this) continue;
                if (tile.getBombState() != BombState.NO_BOMB) surroundingBombs++;
                neighbors.add(tile);
            }
        }
    }

    public void trigger() {
        switch (bombState) {
            case NO_BOMB:
                uncover();
                break;
            case ACTIVE_BOMB:
                explode();
                break;
            case INACTIVE_BOMB:
                activate();
                break;
        }
    }

    public void disarm() {
        if (bombState == BombState.ACTIVE_BOMB) bombState = BombState.INACTIVE_BOMB;
    }

    private void activate() {
        tileState = TileState.UNCOVERED;
        bombState = BombState.ACTIVE_BOMB;
    }

    private void explode() {
        tileState = TileState.UNCOVERED;
        bombState = BombState.EXPLODED_BOMB;
    }

    private void uncover() {
        if (tileState != TileState.UNCOVERED) {
            tileState = TileState.UNCOVERED;
            if (surroundingBombs == 0 && bombState == BombState.NO_BOMB) {
                for (MinesweeperTile neighbor : neighbors) {
                    neighbor.trigger();
                }
            }
        }
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return col;
    }

    public TileState getTileState() {
        return tileState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MinesweeperTile that = (MinesweeperTile) o;
        return row == that.row && col == that.col && getBombState() == that.getBombState() && tileState == that.tileState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
