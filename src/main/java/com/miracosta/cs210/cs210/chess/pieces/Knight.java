package com.miracosta.cs210.cs210.chess.pieces;

import com.miracosta.cs210.cs210.chess.board.ChessBoard;
import com.miracosta.cs210.cs210.chess.board.ChessTile;

public class Knight extends ChessPiece{
    public Knight(Color color) {
        super(color);
    }
    public Knight() {
        super();
    }

    @Override
    public void calculateValidMoves(ChessBoard board) {
        legalMoves.clear();
        //knights can move every combination of +/-(2,1)
        addIfInBounds(board, 1, 2);
        addIfInBounds(board, -1, 2);
        addIfInBounds(board, 1, -2);
        addIfInBounds(board, -1, -2);
        addIfInBounds(board, 2, 1);
        addIfInBounds(board, -2, 1);
        addIfInBounds(board, 2, -1);
        addIfInBounds(board, -2, -1);

    }

    private void addIfInBounds(ChessBoard board, int rowOffset, int columnOffset) {
       ChessTile target = board.getTileByOffset(getPosition(), rowOffset, columnOffset);
       if (target == null) return;
       //if we're occupied by a teammate, you can't go there
       if (target.isOccupied()) {
           if (target.getPiece().getColor() == getColor()) return;
           //if the color is opposite, we can capture, so the move is legal
       }
       legalMoves.add(target);
    }
}
