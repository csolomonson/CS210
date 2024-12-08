package com.miracosta.cs210.cs210.minesweeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

/**
 * An 8x8 minesweeper board
 */
public class MinesweeperBoard {
    private final int NUM_ROWS = 8;
    private final int NUM_COLS = 8;

    private final int numBombs;
    private final MinesweeperTile[][] board;

    /**
     * Create a fully connected 8x8 board with the given number of bombs
     * @param numBombs Number of bombs to distribute randomly
     */
    public MinesweeperBoard(int numBombs) {
        board = new MinesweeperTile[NUM_ROWS][NUM_COLS];
        this.numBombs = numBombs;
        createEmptyBoard();
        assignBombs();
        calculateNeighbors();
    }

    /**
     * Fill in the board 2d array with no-bomb tiles
     */
    private void createEmptyBoard() {
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j<NUM_COLS; j++) {
                board[i][j] = new MinesweeperTile(i, j, MinesweeperTile.BombState.NO_BOMB, this);
            }
        }
    }

    /**
     * Randomly assign numBombs tiles in the board 2d array to be active bombs
     * PRE: run createEmptyBoard first
     * PRE: assign numBombs to the desired number of bombs
     * POST: BombState of random tiles is changed to ACTIVE_BOMB
     */
    private void assignBombs() {
        ArrayList<MinesweeperTile> tiles = new ArrayList<>();
        for (int i = 0; i < NUM_ROWS; i++) {
            tiles.addAll(Arrays.asList(board[i]));
        }
        //randomize the order of a list, and assign the first chunk to be bombs
        Collections.shuffle(tiles);
        for (int i = 0; i < numBombs && i < NUM_COLS * NUM_ROWS; i++) {
            tiles.get(i).setBombState(MinesweeperTile.BombState.ACTIVE_BOMB);
        }
    }

    /**
     * Fill out each tile's HashSet of neighbors, and calculate the number of surrounding bombs for each
     * PRE: run createEmptyBoard first
     * PRE: run assignBombs first
     */
    private void calculateNeighbors() {
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j<NUM_COLS; j++) {
                board[i][j].calculateNeighbors();
            }
        }
    }

    /**
     * Get tile at the given position
     * @param row Row number
     * @param col Column number
     * @return The tile at the given location, or null if invalid
     */
    public MinesweeperTile getTile(int row, int col) {
        try {
            return board[row][col];
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MinesweeperBoard that = (MinesweeperBoard) o;
        return numBombs == that.numBombs && Objects.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numBombs, Arrays.deepHashCode(board));
    }

    public String getBombMap() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j<NUM_COLS; j++) {
                if (board[i][j].getBombState() == MinesweeperTile.BombState.NO_BOMB) s.append("-");
                else s.append("*");
            }
            s.append("\n");
        }
        return s.toString();
    }
}
