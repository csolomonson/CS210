package com.miracosta.cs210.cs210.bots;

import com.miracosta.cs210.cs210.chess.board.ChessTile;
import com.miracosta.cs210.cs210.chess.gameover.GameOver;
import com.miracosta.cs210.cs210.chess.pieces.ChessPiece;
import com.miracosta.cs210.cs210.chess.pieces.PieceColor;
import com.miracosta.cs210.cs210.game.GameBoard;
import com.miracosta.cs210.cs210.game.GameTile;

public class MinimaxBot extends GameBot{
    private GameTile start;
    private GameTile end;
    int depth;

    public MinimaxBot(int depth) {
        super();
        this.depth = depth;
    }

    public MinimaxBot(GameBoard board, PieceColor color, int depth) {
        super(board, color);
        this.depth = depth;
    }

    @Override
    public void botMove() throws GameOver {
        double eval;
        if (turn == PieceColor.WHITE) eval = maxi(depth, gameBoard);
        else eval = mini(depth, gameBoard);
        System.out.println(eval);
        gameBoard.move(start, end);
    }

    private double mini(int depth, GameBoard board) {
        if (depth < 1) return evaluate(board);
        double min = 999999999;
        double score;
        GameTile start = null;
        GameTile end = null;
        for (ChessPiece piece : board.getPieces(board.getColorToMove())) {
            for (ChessTile tile : piece.getLegalMoves()) {
                score = maxi(depth - 1, board.hypotheticalMove(piece.getPosition(), tile));
                if (score < min) {
                    start = board.getGameTile(piece.getPosition());
                    end = board.getGameTile(tile);
                    min = score;
                }
            }
        }
        this.start = start;
        this.end = end;
        return min;
    }

    private double maxi(int depth, GameBoard board) {
        if (depth < 1) return evaluate(board);
        double max = -999999999;
        double score;
        GameTile start, end;
        start = end = null;
        for (ChessPiece piece : board.getPieces(board.getColorToMove())) {
            for (ChessTile tile : piece.getLegalMoves()) {
                score = mini(depth - 1, board.hypotheticalMove(piece.getPosition(), tile));
                if (score > max) {
                    start = board.getGameTile(piece.getPosition());
                    end = board.getGameTile(tile);
                    max = score;
                }
            }
        }
        this.start = start;
        this.end = end;
        return max;
    }

    private double evaluate(GameBoard board) {
        //more positive for white winning, more negative for black winning
        double evaluation = 0;
        for (ChessPiece whitePiece : board.getPieces(PieceColor.WHITE)) {
            evaluation += whitePiece.getValue();
        }

        for (ChessPiece blackPiece : board.getPieces(PieceColor.BLACK)) {
            evaluation -= blackPiece.getValue();
        }

        if (board.getCheckmate() == PieceColor.WHITE || board.getStalemate() == PieceColor.WHITE) evaluation += 1000000;
        if (board.getCheckmate() == PieceColor.BLACK || board.getStalemate() == PieceColor.BLACK) evaluation -= 1000000;
        return evaluation;
    }
}
