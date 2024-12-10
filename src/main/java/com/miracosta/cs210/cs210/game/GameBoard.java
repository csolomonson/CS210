package com.miracosta.cs210.cs210.game;

import com.miracosta.cs210.cs210.chess.board.ChessBoard;
import com.miracosta.cs210.cs210.chess.board.ChessTile;
import com.miracosta.cs210.cs210.chess.gameover.GameOver;
import com.miracosta.cs210.cs210.chess.pieces.ChessPiece;
import com.miracosta.cs210.cs210.chess.pieces.PieceColor;
import com.miracosta.cs210.cs210.minesweeper.MinesweeperBoard;
import com.miracosta.cs210.cs210.minesweeper.MinesweeperTile;

import java.util.ArrayList;
import java.util.Objects;

public class GameBoard implements Cloneable {
    private final int NUM_ROWS = 8;
    private final int NUM_COLS = 8;
    private ChessBoard chessBoard;
    private MinesweeperBoard minesweeperBoard;
    private GameTile[][] board;
    GameBoard previous;
    PieceColor checkmate = PieceColor.EMPTY;
    PieceColor stalemate = PieceColor.EMPTY;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameBoard gameBoard = (GameBoard) o;
        return Objects.equals(getChessBoard(), gameBoard.getChessBoard()) && Objects.equals(getMinesweeperBoard(), gameBoard.getMinesweeperBoard());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getChessBoard(), getMinesweeperBoard());
    }
    
    public void checkEndgames() {
        checkEndgames(PieceColor.BLACK);
        checkEndgames(PieceColor.WHITE);
    }

    private void checkEndgames(PieceColor color) {
        boolean legalMoves = false;
        for (ChessPiece piece : getPieces(color)) {
            if (!piece.getLegalMoves().isEmpty()) {
                legalMoves = true;
                break;
            }
        }
        if (!legalMoves) {
            if (getChessBoard().getCheck(color)) checkmate = color;
            else stalemate = color;
        }
    }

    public PieceColor getCheckmate() {
        return checkmate;
    }

    public PieceColor getStalemate() {
        return stalemate;
    }

    public GameBoard(int numBombs) {
        chessBoard = new ChessBoard();
        minesweeperBoard = new MinesweeperBoard(numBombs);
        board = new GameTile[NUM_ROWS][NUM_COLS];
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                board[i][j] = new GameTile(i,j,this);

                if (chessBoard.getTile(i,j).isOccupied()) {
                    board[i][j].disarm();
                    board[i][j].trigger();
                }
            }
        }
    }

    public ArrayList<ChessPiece> getPieces(PieceColor pieceColor) {
        ArrayList<ChessPiece> pieces = new ArrayList<>();
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                if (getChessBoard().getTile(i,j).isOccupied()) {
                    ChessPiece piece = getChessBoard().getTile(i,j).getPiece();
                    if (piece.getColor() == pieceColor) pieces.add(piece);
                }
            }
        }
        return  pieces;
    }

    public ArrayList<ChessPiece> getPieces() {
        ArrayList<ChessPiece> pieces = getPieces(PieceColor.BLACK);
        pieces.addAll(getPieces(PieceColor.WHITE));
        return pieces;
    }

    public GameBoard getPrevious() {
        return previous;
    }

    public GameBoard() {
        this(10);
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public MinesweeperBoard getMinesweeperBoard() {
        return minesweeperBoard;
    }

    public GameTile getGameTile(int row, int col) {
        return board[row][col];
    }

    public GameBoard hypotheticalMove(int row1, int col1, int row2, int col2) {
        GameBoard clone = clone();
        clone.move(row1, col1, row2, col2, false);
        return clone;
    }

    public GameBoard hypotheticalMove(ChessTile start, ChessTile end) {
        return  hypotheticalMove(start.getRow(), start.getColumn(), end.getRow(), end.getColumn());
    }

    public ArrayList<GameBoard> getAllPossibleNextStates() {
        ArrayList<GameBoard> states = new ArrayList<>();
        for (ChessPiece piece : getPieces(getColorToMove())) {
            for (ChessTile tile : piece.getLegalMoves()) {
                states.add(hypotheticalMove(piece.getPosition().getRow(), piece.getPosition().getColumn(), tile.getRow(), tile.getColumn()));
            }
        }
        return states;
    }

    public boolean move(int row1, int col1, int row2, int col2, boolean savePrevious) {
        GameBoard clone = null;
        if (savePrevious) clone = clone();
        if (chessBoard.move(row1, col1, row2, col2)) {
            previous = clone;
            try {
                getGameTile(row2, col2).trigger();
            } catch (GameOver g) {
                System.out.println(g.getMessage());
            }
            chessBoard.update();
            return true;
        }
        return false;
    }

    public boolean move(int row1, int col1, int row2, int col2) {
        return move(row1, col1, row2, col2, true);
    }

    public boolean move(GameTile start, GameTile end) {
        return move(start.getRow(), start.getCol(), end.getRow(), end.getCol());
    }

    public GameTile getGameTile(ChessTile chessTile) {
        return getGameTile(chessTile.getRow(), chessTile.getColumn());
    }

    public ArrayList<ChessPiece> getChessPieces() {
        return chessBoard.getPieces();
    }
    public GameTile getGameTile(MinesweeperTile minesweeperTile) {
        return getGameTile(minesweeperTile.getRow(), minesweeperTile.getColumn());
    }

    public PieceColor getColorToMove() {
        return chessBoard.getColorToMove();
    }

    @Override
    public GameBoard clone() {
        try {
            GameBoard clone = (GameBoard) super.clone();
            clone.chessBoard = chessBoard.clone();
            clone.chessBoard.update();
            clone.minesweeperBoard = minesweeperBoard.clone();
            if (previous != null)  clone.previous = this.previous.clone();
            clone.board = new GameTile[NUM_ROWS][NUM_COLS];
            for (int i = 0; i < NUM_ROWS; i++) {
                for (int j = 0; j < NUM_COLS; j++) {
                    clone.board[i][j] = new GameTile(i, j, clone);
                }
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
