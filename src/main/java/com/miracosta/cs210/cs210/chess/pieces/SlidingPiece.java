package com.miracosta.cs210.cs210.chess.pieces;

import com.miracosta.cs210.cs210.chess.board.ChessBoard;
import com.miracosta.cs210.cs210.chess.board.ChessTile;

/**
 * Class to represent pieces that can slide in some direction until they meet another piece or the edge of the board.
 * Bishops, Rooks, Queens, Kings (Kings simply have an imposed 'range' of 1)
 */
public abstract class SlidingPiece extends ChessPiece {

    protected boolean diagonal;
    protected boolean lateral;
    protected boolean longitudinal;
    protected int range;

    public SlidingPiece() {
        this(Color.WHITE);
    }
    public SlidingPiece(Color color) {
        super(color);
    }

    @Override
    public void calculateValidMoves(ChessBoard board) {
        legalMoves.clear();
        boolean left, right, up, down, upLeft, upRight, downLeft, downRight;
        left = right = up = down = upLeft = upRight = downRight = downLeft = true;
        for(int i = 1; i <= range; i++) {
            if (longitudinal) {
                if (left) left = checkAndAdd(board, 0, -i);
                if (right) right = checkAndAdd(board, 0, i);
            }
            if (lateral) {
                if (up) up = checkAndAdd(board, -i, 0);
                if (down) down = checkAndAdd(board, i, 0);
            }
            if (diagonal) {
                if (upLeft) upLeft = checkAndAdd(board, -i, -i);
                if (upRight) upRight = checkAndAdd(board, -i, i);
                if (downLeft) downLeft = checkAndAdd(board, i, -i);
                if (downRight) downRight = checkAndAdd(board, i, i);
            }
        }
    }

    /**
     * Check if a tile exists and is not blocked, and add it to the list of legal moves if so
     * @param board board to check for obstacles and boundaries
     * @param rowOffset offset number of rows to check tile
     * @param colOffset offset number of columns to check tile
     * @return true if the tile with given offset from this position makes a legal move, and has been added to the set.
     */
    private boolean checkAndAdd(ChessBoard board, int rowOffset, int colOffset) {
        ChessTile target = board.getTileByOffset(getPosition(), rowOffset, colOffset);
        //if the tile is out of bounds, we can't keep going
        if (target == null) return false;
        //if the tile we check is empty, we can keep going
        if (!target.isOccupied()) {
            legalMoves.add(target);
            return true;
        }
        //if the tile we check has an opponent, we can capture it, but we can't go any further
        if (target.getPiece().getColor() == getOppositeColor()) {
            legalMoves.add(target);
            return false;
        }
        //only other option is that a teammate is blocking our path
        //in that case, we can't go to the target, nor can we go further
        return false;
    }
}
