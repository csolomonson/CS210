package com.miracosta.cs210.cs210.chess.board;

import com.miracosta.cs210.cs210.chess.pieces.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static com.miracosta.cs210.cs210.chess.pieces.Color.BLACK;
import static com.miracosta.cs210.cs210.chess.pieces.Color.WHITE;

public class ChessBoard {
    private final int BOARD_ROWS = 8;
    private final int BOARD_COLUMNS = 8;
    private final ChessTile[][] board;
    int moveNumber;

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

    public ChessTile getTile(int row, int col) {
        return board[row][col];
    }

    public ChessPiece getPiece(int row, int col) {
        return getTile(row, col).getPiece();
    }

    public void setPiece(ChessPiece piece, int row, int col) {
        getTile(row, col).forceSetPiece(piece);
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public ChessTile getTileByOffset(ChessTile start, int rowOffset, int columnOffset) {
        int newRow = start.getRow() + rowOffset;
        int newColumn = start.getColumn() + columnOffset;
        if (newRow > 7 || newRow < 0) return null;
        if (newColumn > 7 || newColumn < 0) return null;
        return board[newRow][newColumn];
    }

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


    public void clearBoard() {
        for (int i = 0; i < BOARD_ROWS; i++) {
            for (int j = 0; j < BOARD_COLUMNS; j++) {
                board[i][j].clearPiece();
            }
        }
    }

    public boolean move(int row1, int col1, int row2, int col2) {
        ChessPiece piece = getPiece(row1, col1);
        if (piece == null) return false;
        if (piece.move(getTile(row2, col2))) {
            moveNumber++;
            update();
            return true;
        }
        return false;
    }

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
