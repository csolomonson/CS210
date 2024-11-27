package com.miracosta.cs210.cs210.chess.pieces;

import com.miracosta.cs210.cs210.chess.board.ChessTile;

public interface EnPassantPiece {
    void updateEnPassantStatus();
    void setEnPassantStatus(boolean enPassantStatus);
    boolean getEnPassantStatus();
    boolean isCapturingEnPassant();
    ChessTile getCaptureTile();
}
