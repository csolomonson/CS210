package com.miracosta.cs210.cs210.chess.pieces;

import com.miracosta.cs210.cs210.chess.board.ChessBoard;
import com.miracosta.cs210.cs210.chess.board.ChessTile;

import java.util.ArrayList;

import static com.miracosta.cs210.cs210.chess.pieces.Color.WHITE;

public class Pawn extends ChessPiece{
    public Pawn(Color color) {
        super(color);
    }
    public Pawn() {
        super();
    }

    @Override
    public ArrayList<ChessTile> getValidMoves(ChessBoard board) {
        ArrayList<ChessTile> moves = new ArrayList<>();
        if (canDoubleMove()) checkAndAddPush(moves, board, 2);
        else checkAndAddPush(moves, board, 1);
        checkAndAddCapture(moves, board);
        return moves;
    }

    private int getPushDirection() {
        if (getColor() == WHITE) return -1;
        return 1;
    }

    private void checkAndAddPush(ArrayList<ChessTile> arr, ChessBoard board, int range) {
        for (int i = 1; i <= range; i++) {
            ChessTile target = board.getTileByOffset(getPosition(), i * getPushDirection(), 0);
            if (target == null) return;
            if (target.isOccupied()) return;
            arr.add(target);
        }
    }

    private void checkAndAddCapture(ArrayList<ChessTile> arr, ChessBoard board) {
        ChessTile leftTarget = board.getTileByOffset(getPosition(), getPushDirection(), -1);
        ChessTile rightTarget = board.getTileByOffset(getPosition(), getPushDirection(), 1);
        if (leftTarget != null) {
            if (leftTarget.getColor() == getOppositeColor()) arr.add(leftTarget);
        }
        if (rightTarget != null) {
            if (rightTarget.getColor() == getOppositeColor()) arr.add(rightTarget);
        }
    }

    private boolean canDoubleMove() {
        if (getColor() == WHITE) {
            return getPosition().getRow() == 6;
        }
        return getPosition().getRow() == 1;
    }
}
