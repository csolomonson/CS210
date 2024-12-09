package com.miracosta.cs210.cs210.chess.board;

import com.miracosta.cs210.cs210.chess.pieces.ChessPiece;
import com.miracosta.cs210.cs210.chess.pieces.Color;
import com.miracosta.cs210.cs210.chess.pieces.EnPassantPiece;

import java.util.Objects;

/**
 * Class to represent a specific tile (identified by a row and column number) on a ChessBoard, and any piece it may contain.
 * (row 7, col 0) is a1.
 * (row 7, col 7) is h1.
 * (row 0, col 7) is h8.
 * (row 0, col 0) is a8.
 */
public class ChessTile implements Cloneable {
    private final int row;
    private final int column;
    private ChessPiece piece;
    private final ChessBoard board;

    /**
     * Default tile with no connected board, and in the h1 position
     */
    public ChessTile() {
        this(0,0, null);
    }

    /**
     * Tile with given row and column (board is null)
     * @param r row number
     * @param c column number
     */
    public ChessTile(int r, int c) {
        row = r;
        column = c;
        board = null;
    }

    /**
     * Tile with given position and reference to ChessBoard
     * @param r row number (0->h, 7->a)
     * @param c column number (0->1, 7->8)
     * @param board Reference to ChessBoard for to pass to ChessPieces
     */
    public ChessTile(int r, int c, ChessBoard board) {
        row = r;
        column = c;
        piece = null;
        this.board = board;
    }

    /**
     * Set a ChessPiece on this tile iff it isn't occupied
     * @param piece Piece to try to place here
     * @return true if the piece was placed, false if this tile is occupied
     */
    public boolean setPiece(ChessPiece piece) {
        if (isOccupied()) return false;
        forceSetPiece(piece);
        return true;
    }

    /**
     * Get the row number
     * @return Row number (0->h, 7->a)
     */
    public int getRow() {
        return row;
    }

    /**
     * Get the column number
     * @return Column number (0->1, 7->8)
     */
    public int getColumn() {
        return column;
    }

    /**
     * Put a piece here, deleting the former piece if it's occupied
     * @param piece Piece to forcibly put here
     */
    public void forceSetPiece(ChessPiece piece) {
        this.piece = piece;
        this.piece.setPosition(this);
    }

    /**
     * Get the piece that lives on this tile
     * @return piece that lives here, or null if unoccupied
     */
    public ChessPiece getPiece() {
        return piece;
    }

    /**
     * Delete the piece on this tile, if one exists
     */
    public void clearPiece() {
        this.piece = null;
    }

    /**
     * Check if there is a piece on this tile
     * @return True if there is a piece on this tile, else false
     */
    public boolean isOccupied() {
        return (piece != null);
    }

    /**
     * Get the color of the piece on this tile
     * @return Color of the piece on this tile (EMPTY if unoccupied)
     */
    public Color getColor() {
        if (isOccupied()) return getPiece().getColor();
        return Color.EMPTY;
    }

    /**
     * Get the board reference passed to this tile
     * @return The board that this tile is on (might be null, if not set)
     */
    public ChessBoard getBoard() {
        return board;
    }

    /**
     * Check if the piece on this tile happens to be a piece of the given color that can be captured with en passant
     * @param pieceColor Color of the piece that might be on this tile
     * @return True if this tile contains a pawn that can currently be captured by en passant and is of the given Color
     */
    public boolean hasEnPassantablePiece(Color pieceColor) {
        if (!isOccupied()) return false;
        if (!(piece instanceof EnPassantPiece epp)) return false;
        if (!(getColor() == pieceColor)) return false;
        return epp.getEnPassantStatus();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessTile chessTile = (ChessTile) o;
        return getRow() == chessTile.getRow() && getColumn() == chessTile.getColumn();
    }

    @Override
    public ChessTile clone() {
        try {
            return (ChessTile) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
