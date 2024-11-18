package com.miracosta.cs210.cs210.chess.board;

import com.miracosta.cs210.cs210.chess.pieces.ChessPiece;

public class ChessTile {
    private int row;
    private int column;
    private ChessPiece piece;

    public ChessTile() {
        this(1,1);
    }

    public ChessTile(int r, int c) {
        row = r;
        column = c;
        piece = null;
    }

    boolean setPiece(ChessPiece piece) {
        if (isOccupied()) return false;
        forceSetPiece(piece);
        return true;
    }

    void forceSetPiece(ChessPiece piece) {
        this.piece = piece;
        this.piece.setPosition(this);
    }

    ChessPiece getPiece() {
        return piece;
    }

    void clearPiece() {
        this.piece = null;
    }

    boolean isOccupied() {
        return (piece != null);
    }

}
