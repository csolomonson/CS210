package com.miracosta.cs210.cs210.chess.pieces;

import com.miracosta.cs210.cs210.chess.board.ChessBoard;
import com.miracosta.cs210.cs210.chess.board.ChessTile;

import static com.miracosta.cs210.cs210.chess.pieces.Color.WHITE;

public class Pawn extends ChessPiece{
    public Pawn(Color color) {
        super(color);
    }
    public Pawn() {
        super();
    }

    @Override
    public void calculateValidMoves(ChessBoard board) {
        legalMoves.clear();
        if (canDoubleMove()) checkAndAddPush(board, 2);
        else checkAndAddPush(board, 1);
        checkAndAddCapture(board);
    }

    private int getPushDirection() {
        if (getColor() == WHITE) return -1;
        return 1;
    }

    private void checkAndAddPush(ChessBoard board, int range) {
        for (int i = 1; i <= range; i++) {
            ChessTile target = board.getTileByOffset(getPosition(), i * getPushDirection(), 0);
            if (target == null) return;
            if (target.isOccupied()) return;
            legalMoves.add(target);
        }
    }

    private void checkAndAddCapture(ChessBoard board) {
        ChessTile leftTarget = board.getTileByOffset(getPosition(), getPushDirection(), -1);
        ChessTile rightTarget = board.getTileByOffset(getPosition(), getPushDirection(), 1);
        if (leftTarget != null) {
            if (leftTarget.getColor() == getOppositeColor()) legalMoves.add(leftTarget);
        }
        if (rightTarget != null) {
            if (rightTarget.getColor() == getOppositeColor()) legalMoves.add(rightTarget);
        }
    }

    private boolean canDoubleMove() {
        if (getColor() == WHITE) {
            return getPosition().getRow() == 6;
        }
        return getPosition().getRow() == 1;
    }
}
