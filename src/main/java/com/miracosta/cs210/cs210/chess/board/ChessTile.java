package com.miracosta.cs210.cs210.chess.board;

import com.miracosta.cs210.cs210.chess.pieces.ChessPiece;
import com.miracosta.cs210.cs210.chess.pieces.Color;

import java.util.Objects;

public class ChessTile {
    private final int row;
    private final int column;
    private ChessPiece piece;
    private final ChessBoard board;

    public ChessTile() {
        this(1,1, null);
    }

    public ChessTile(int r, int c) {
        row = r;
        column = c;
        board = null;
    }

    public ChessTile(int r, int c, ChessBoard board) {
        row = r;
        column = c;
        piece = null;
        this.board = board;
    }

    public boolean setPiece(ChessPiece piece) {
        if (isOccupied()) return false;
        forceSetPiece(piece);
        return true;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void forceSetPiece(ChessPiece piece) {
        this.piece = piece;
        this.piece.setPosition(this);
    }

    public ChessPiece getPiece() {
        return piece;
    }

    public void clearPiece() {
        this.piece = null;
    }

    public boolean isOccupied() {
        return (piece != null);
    }

    public Color getColor() {
        if (isOccupied()) return getPiece().getColor();
        return Color.EMPTY;
    }

    public ChessBoard getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessTile chessTile = (ChessTile) o;
        return getRow() == chessTile.getRow() && getColumn() == chessTile.getColumn();
    }

}
