package com.miracosta.cs210.cs210.chess.pieces;

import com.miracosta.cs210.cs210.chess.board.ChessBoard;
import com.miracosta.cs210.cs210.chess.board.ChessTile;

import java.util.ArrayList;

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
    public ArrayList<ChessTile> getValidMoves(ChessBoard board) {
        boolean left, right, up, down, upLeft, upRight, downLeft, downRight;
        left = right = up = down = upLeft = upRight = downRight = downLeft = true;
        ArrayList<ChessTile> moves = new ArrayList<>();
        for(int i = 1; i <= range; i++) {
            if (longitudinal) {
                if (left) left = checkAndAdd(moves, board, 0, -i);
                if (right) right = checkAndAdd(moves, board, 0, i);
            }
            if (lateral) {
                if (up) up = checkAndAdd(moves, board, -i, 0);
                if (down) down = checkAndAdd(moves, board, i, 0);
            }
            if (diagonal) {
                if (upLeft) upLeft = checkAndAdd(moves, board, -i, -i);
                if (upRight) upRight = checkAndAdd(moves, board, -i, i);
                if (downLeft) downLeft = checkAndAdd(moves, board, i, -i);
                if (downRight) downRight = checkAndAdd(moves, board, i, i);
            }
        }
        return moves;
    }

    private boolean checkAndAdd(ArrayList<ChessTile> arr, ChessBoard board, int rowOffset, int colOffset) {
        ChessTile target = board.getTileByOffset(getPosition(), rowOffset, colOffset);
        //if the tile is out of bounds, we can't keep going
        if (target == null) return false;
        //if the tile we check is empty, we can keep going
        if (!target.isOccupied()) {
            arr.add(target);
            return true;
        }
        //if the tile we check has an opponent, we can capture it, but we can't go any further
        if (target.getPiece().getColor() == getOppositeColor()) {
            arr.add(target);
            return false;
        }
        //only other option is that a teammate is blocking our path
        //in that case, we can't go to the target, nor can we go further
        return false;
    }
}
