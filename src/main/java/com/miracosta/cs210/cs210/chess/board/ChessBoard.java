package com.miracosta.cs210.cs210.chess.board;

import com.miracosta.cs210.cs210.chess.pieces.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static com.miracosta.cs210.cs210.chess.pieces.Color.BLACK;
import static com.miracosta.cs210.cs210.chess.pieces.Color.WHITE;

public class ChessBoard {
    private final int BOARD_ROWS = 8;
    private final int BOARD_COLUMNS = 8;
    private ChessTile[][] board;

    public ChessBoard() {
        board = new ChessTile[BOARD_ROWS][BOARD_COLUMNS];
        for (int r = 1; r <= BOARD_ROWS; r++) {
            for (int c = 1; c <= BOARD_COLUMNS; c++) {
                board[r-1][c-1] = new ChessTile(r-1, c-1);
            }
        }
        resetBoard();
    }

    public ChessTile getTile(int row, int col) {
        return board[row][col];
    }

    public ChessPiece getPiece(int row, int col) {
        return getTile(row, col).getPiece();
    }


    private void clearBoard() {
        for (int i = 0; i < BOARD_ROWS; i++) {
            for (int j = 0; j < BOARD_COLUMNS; j++) {
                board[i][j].clearPiece();
            }
        }
    }

    private void resetBoard() {
        clearBoard();
        //do the pawns
        for (int i = 0; i < BOARD_COLUMNS; i++) {
            board[1][i].forceSetPiece(new Pawn(BLACK));
            board[BOARD_ROWS - 2][i].forceSetPiece(new Pawn(WHITE));
        }
        setSymmetrical4Way(Rook.class, 0,0);
        setSymmetrical4Way(Knight.class, 0, 1);
        setSymmetrical4Way(Bishop.class, 0, 2);
        setSymmetrical2Way(Queen.class, 0, 3);
        setSymmetrical2Way(King.class, 0, 4);
    }

    private void setSymmetrical4Way(Class PieceType, int row, int col) {
        try {
            Constructor constructor = PieceType.getDeclaredConstructor(Color.class);
            constructor.setAccessible(true);

            board[row][col].forceSetPiece((ChessPiece) constructor.newInstance(BLACK));
            board[row][BOARD_COLUMNS - 1 - col].forceSetPiece((ChessPiece) constructor.newInstance(BLACK));

            board[BOARD_ROWS - 1 - row][col].forceSetPiece((ChessPiece) constructor.newInstance(WHITE));
            board[BOARD_ROWS - 1 - row][BOARD_COLUMNS - 1 - col].forceSetPiece((ChessPiece) constructor.newInstance(WHITE));

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
            //TODO do this better
        }
    }

    private void setSymmetrical2Way(Class PieceType, int row, int col) {
        Constructor constructor = null;
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
