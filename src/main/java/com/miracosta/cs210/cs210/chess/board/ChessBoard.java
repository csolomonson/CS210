package com.miracosta.cs210.cs210.chess.board;

import com.miracosta.cs210.cs210.chess.pieces.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static com.miracosta.cs210.cs210.chess.pieces.Color.BLACK;
import static com.miracosta.cs210.cs210.chess.pieces.Color.WHITE;

/**
 * An entire chessboard, made of ChessTiles that might contain ChessPieces
 */
public class ChessBoard {
    private final int BOARD_ROWS = 8;
    private final int BOARD_COLUMNS = 8;
    private final ChessTile[][] board;
    int moveNumber;
    boolean printMoves = false;

    /**
     * Sets up a chessboard with the starting position (use clearBoard if you want a blank board)
     */
    public ChessBoard() {
        board = new ChessTile[BOARD_ROWS][BOARD_COLUMNS];
        for (int r = 1; r <= BOARD_ROWS; r++) {
            for (int c = 1; c <= BOARD_COLUMNS; c++) {
                board[r-1][c-1] = new ChessTile(r-1, c-1, this);
            }
        }
        resetBoard();
        moveNumber = 0;
    }

    /**
     * Get the tile at the given coordinates
     * @param row Row number (0->h, 7->a)
     * @param col Column number (0->1, 7->8)
     * @return the Tile, or null if coordinates are invalid
     */
    public ChessTile getTile(int row, int col) {
        if (row > 7 || row < 0) return null;
        if (col > 7 || col < 0) return null;
        return board[row][col];
    }

    /**
     * Decide whether the board should print a representation of the board state each time a move is made
     * @param printMoves Whether the board should print at each move
     */
    public void setPrintMoves(boolean printMoves) {
        this.printMoves = printMoves;
    }

    /**
     * Get the piece on the tile at the given coordinates
     * @param row Row number (0->h, 7->a)
     * @param col Column number (0->1, 7->8)
     * @return The piece at the given coordinates, or null if there is no piece there, or the coordinates are invalid
     */
    public ChessPiece getPiece(int row, int col) {
        if (getTile(row, col) == null) return null;
        return getTile(row, col).getPiece();
    }

    /**
     * Forcibly put the given piece in the given position
     * @param piece Piece to place
     * @param row Row number (0->h, 7->a)
     * @param col Column number (0->1, 7->8)
     */
    public void setPiece(ChessPiece piece, int row, int col) {
        getTile(row, col).forceSetPiece(piece);
    }

    /**
     * Get the number of moves made on the Board
     * @return number of moves made on this Board
     */
    public int getMoveNumber() {
        return moveNumber;
    }

    /**
     * Get the Tile a certain offset away from the given Tile
     * @param start offset relative to this Tile
     * @param rowOffset offset number of rows (+ or -)
     * @param columnOffset offset number of columns (+ or -)
     * @return Tile that is the given offset away from the start tile, or null if out of bounds
     */
    public ChessTile getTileByOffset(ChessTile start, int rowOffset, int columnOffset) {
        int newRow = start.getRow() + rowOffset;
        int newColumn = start.getColumn() + columnOffset;
        if (newRow > 7 || newRow < 0) return null;
        if (newColumn > 7 || newColumn < 0) return null;
        return board[newRow][newColumn];
    }

    /**
     * Recalculate legal moves, en passant status, and castling legibility (eventually) for all pieces
     */
    public void update() {
        for (int i = 0; i < BOARD_ROWS; i++) {
            for (int j = 0; j < BOARD_COLUMNS; j++) {
                if (board[i][j].isOccupied()) {
                    ChessPiece piece = board[i][j].getPiece();
                    piece.calculateValidMoves(this);
                    if (piece instanceof EnPassantPiece) {
                        ((EnPassantPiece) piece).updateEnPassantStatus();
                    }
                }
            }
        }
    }

    /**
     * Get rid of all the pieces on the board
     */
    public void clearBoard() {
        for (int i = 0; i < BOARD_ROWS; i++) {
            for (int j = 0; j < BOARD_COLUMNS; j++) {
                board[i][j].clearPiece();
            }
        }
    }

    /**
     * Attempt to move the Piece on one location to another location
     * @param row1 row of the starting square (0->h, 7->a)
     * @param col1 column of the starting square (0->1, 7->8)
     * @param row2 row of target square
     * @param col2 column of target square
     * @return True if the move was made; false if the move is illegal or invalid
     */
    public boolean move(int row1, int col1, int row2, int col2) {
        ChessPiece piece = getPiece(row1, col1);
        if (piece == null) return false;
        if (piece.move(getTile(row2, col2))) {
            moveNumber++;
            update();
            if (printMoves) System.out.println(this);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("\n");
        for (ChessTile[] row : board) {
            for (ChessTile tile : row) {
                if (tile.isOccupied()) {
                    str.append(" ").append(tile.getPiece().toString()).append(" ");
                } else str.append(" \u2003 ");
            } str.append("\n");
        }
        return str.toString();
    }

    /**
     * Reset board as starting position
     */
    private void resetBoard() {
        clearBoard();
        //do the pawns
        for (int i = 0; i < BOARD_COLUMNS; i++) {
            setSymmetrical2Way(Pawn.class, 1, i);
        }
        setSymmetrical4Way(Rook.class, 0,0);
        setSymmetrical4Way(Knight.class, 0, 1);
        setSymmetrical4Way(Bishop.class, 0, 2);
        setSymmetrical2Way(Queen.class, 0, 3);
        setSymmetrical2Way(King.class, 0, 4);
        update();
    }

    /**
     * Set 4 pieces, mirrored across rows and columns (useful for setting rooks, knights, and bishops in starting pos)
     * @param PieceType Class of piece to set in 4 location
     * @param row Row of location of a BLACK piece in one quadrant
     * @param col Column of location of a piece in one quadrant
     */
    private void setSymmetrical4Way(Class<?> PieceType, int row, int col) {
        try {
            Constructor<?> constructor = PieceType.getDeclaredConstructor(Color.class);
            constructor.setAccessible(true);

            board[row][col].forceSetPiece((ChessPiece) constructor.newInstance(BLACK));
            board[row][BOARD_COLUMNS - 1 - col].forceSetPiece((ChessPiece) constructor.newInstance(BLACK));

            board[BOARD_ROWS - 1 - row][col].forceSetPiece((ChessPiece) constructor.newInstance(WHITE));
            board[BOARD_ROWS - 1 - row][BOARD_COLUMNS - 1 - col].forceSetPiece((ChessPiece) constructor.newInstance(WHITE));

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a BLACK piece to the given location, and a WHITE piece in the corresponding mirrored location
     * @param PieceType Class of the pieces to set
     * @param row row for the BLACK piece (0->h, 7->a)
     * @param col column for the BLACK piece (0->1, 7->8)
     */
    private void setSymmetrical2Way(Class<?> PieceType, int row, int col) {
        Constructor<?> constructor = null;
        try {
            constructor = PieceType.getDeclaredConstructor(Color.class);
            constructor.setAccessible(true);
            board[row][col].forceSetPiece((ChessPiece) constructor.newInstance(BLACK));
            board[BOARD_ROWS - 1 - row][col].forceSetPiece((ChessPiece) constructor.newInstance(WHITE));
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

}
