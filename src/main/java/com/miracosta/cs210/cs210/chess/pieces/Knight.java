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
        return null;
    }
}
