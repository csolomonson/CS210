package com.miracosta.cs210.cs210.chess.pieces;

import com.miracosta.cs210.cs210.chess.board.ChessBoard;
import com.miracosta.cs210.cs210.chess.board.ChessTile;

import java.util.ArrayList;

public class Knight extends ChessPiece{
    public Knight(Color color) {
        super(color);
    }
    public Knight() {
        super();
    }

    @Override
    public ArrayList<ChessTile> getValidMoves(ChessBoard board) {
        ArrayList<ChessTile> moves = new ArrayList<>();
        //knights can move every combination of +/-(2,1)
        addIfInBounds(moves, board, 1, 2);
        addIfInBounds(moves, board, -1, 2);
        addIfInBounds(moves, board, 1, -2);
        addIfInBounds(moves, board, -1, -2);
        addIfInBounds(moves, board, 2, 1);
        addIfInBounds(moves, board, -2, 1);
        addIfInBounds(moves, board, 2, -1);
        addIfInBounds(moves, board, -2, -1);

        return moves;
    }

    private void addIfInBounds(ArrayList<ChessTile> arr, ChessBoard board, int rowOffset, int columnOffset) {
       ChessTile target = board.getTileByOffset(getPosition(), rowOffset, columnOffset);
       if (target == null) return;
       //if we're occupied by a teammate, you can't go there
       if (target.isOccupied()) {
           if (target.getPiece().getColor() == getColor()) return;
           //if the color is opposite, we can capture, so the move is legal
       }
       arr.add(target);
    }
}
