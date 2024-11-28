package com.miracosta.cs210.cs210.chess.pieces;

import com.miracosta.cs210.cs210.chess.board.ChessTile;

public interface EnPassantPiece {
    /**
     * Check if this piece can either capture or be captured en passant on this next move
     */
    void updateEnPassantStatus();

    /**
     * Set this piece's ability to be captured en passant on the next move
     * @param enPassantStatus true if this piece can be captured en passant on the next move
     */
    void setEnPassantStatus(boolean enPassantStatus);

    /**
     * Check if this piece can currently be captured en passant
     * @return true if this piece can be captured en passant
     */
    boolean getEnPassantStatus();

    /**
     * Check if this piece can capture another piece en passant in this move
     * @return true if there is an eligible en passant capture for this piece
     */
    boolean isCapturingEnPassant();

    /**
     * Get the tile containing the piece that this piece could capture en passant
     * Since only one pawn can be eligible to be captured en passant at a time, only one is needed
     * @return Null if there is no en passant-able piece. Else, the ChessTile where the piece is located
     */
    ChessTile getCaptureTile();
}
