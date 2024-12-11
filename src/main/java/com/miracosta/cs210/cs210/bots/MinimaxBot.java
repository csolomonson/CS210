package com.miracosta.cs210.cs210.bots;

import com.miracosta.cs210.cs210.chess.board.ChessTile;
import com.miracosta.cs210.cs210.chess.gameover.GameOver;
import com.miracosta.cs210.cs210.chess.pieces.ChessPiece;
import com.miracosta.cs210.cs210.chess.pieces.PieceColor;
import com.miracosta.cs210.cs210.game.GameBoard;
import com.miracosta.cs210.cs210.game.GameTile;

import java.util.HashMap;

public class MinimaxBot extends GameBot{
    private GameTile start;
    private GameTile end;
    int depth;
    int movesPondered = 0;
    int movesPruned = 0;
    HashMap<String, Double> calculated;

    public MinimaxBot(int depth) {
        super();
        this.depth = depth;
        calculated = new HashMap<>();
    }

    @Override
    public void botMove() throws GameOver {
        calculated.clear();
        movesPondered = movesPruned = 0;
        double eval;
        if (turn == PieceColor.WHITE) eval = maxi(depth, gameBoard, -9999999999., 9999999999.);
        else eval = mini(depth, gameBoard, -9999999999., 9999999999.);
        System.out.println(eval);
        System.out.println("Pondered: " + movesPondered);
        System.out.println("Pruned: " + movesPruned);
        gameBoard.move(start, end);
    }

    private double mini(int depth, GameBoard board, double alpha, double beta) {
        String saveString = board.toString() + depth + "min";
        if (depth < 1) return evaluate(board);
        if (calculated.containsKey(saveString)) {
            movesPruned++;
            return calculated.get(saveString);
        }
        double min = 999999999;
        double score;
        GameTile start = null;
        GameTile end = null;
        for (ChessPiece piece : board.getPieces(board.getColorToMove())) {
            for (ChessTile tile : piece.getLegalMoves()) {
                movesPondered++;
                score = maxi(depth - 1, board.hypotheticalMove(piece.getPosition(), tile), alpha, beta);
                if (score < min) {
                    start = board.getGameTile(piece.getPosition());
                    end = board.getGameTile(tile);
                    min = score;
                }
                if (min < beta) beta = min;
                if (alpha >= beta) {
                    calculated.put(saveString, min);
                    this.start = start;
                    this.end = end;
                    return min;
                }
            }
        }
        calculated.put(saveString, min);
        this.start = start;
        this.end = end;
        return min;
    }

    private double maxi(int depth, GameBoard board, double alpha, double beta) {
        String saveString = board.toString() + depth + "max";
        if (depth < 1) return evaluate(board);
        if (calculated.containsKey(saveString)) {
            movesPruned++;
            return calculated.get(saveString);
        }
        double max = -999999999;
        double score;
        GameTile start, end;
        start = end = null;
        for (ChessPiece piece : board.getPieces(board.getColorToMove())) {
            for (ChessTile tile : piece.getLegalMoves()) {
                movesPondered++;
                score = mini(depth - 1, board.hypotheticalMove(piece.getPosition(), tile), alpha, beta);
                if (score > max) {
                    start = board.getGameTile(piece.getPosition());
                    end = board.getGameTile(tile);
                    max = score;
                }
                if (max > alpha) alpha = max;

                if (beta >= alpha) {
                    calculated.put(saveString, max);
                    this.start = start;
                    this.end = end;
                    return max;
                }
            }
        }
        calculated.put(saveString, max);
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
