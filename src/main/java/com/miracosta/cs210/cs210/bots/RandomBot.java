package com.miracosta.cs210.cs210.bots;

import com.miracosta.cs210.cs210.chess.board.ChessTile;
import com.miracosta.cs210.cs210.chess.gameover.GameOver;
import com.miracosta.cs210.cs210.chess.gameover.GameOverCheckmate;
import com.miracosta.cs210.cs210.chess.gameover.GameOverStalemate;
import com.miracosta.cs210.cs210.chess.pieces.ChessPiece;
import com.miracosta.cs210.cs210.chess.pieces.PieceColor;
import com.miracosta.cs210.cs210.game.GameBoard;

import java.util.ArrayList;
import java.util.Collections;

public class RandomBot extends GameBot{

    public RandomBot(GameBoard gameBoard, PieceColor turn) {
        super(gameBoard, turn);
    }

    public RandomBot() {
        super();
    }

    @Override
    public void botMove() throws GameOver {
        ArrayList<ChessPiece> possiblePieces = gameBoard.getPieces(turn);
        Collections.shuffle(possiblePieces);

        for (ChessPiece piece : possiblePieces) {
            ArrayList<ChessTile> possibleMoves = piece.getLegalMoves();
            if (possibleMoves.isEmpty()) continue;
            Collections.shuffle(possibleMoves);
            ChessTile move = possibleMoves.getFirst();
            gameBoard.move(piece.getPosition().getRow(), piece.getPosition().getColumn(), move.getRow(), move.getColumn());
            return;
        }
        //no legal moves
        if (gameBoard.getChessBoard().getCheck(turn)) {
            throw new GameOverCheckmate(turn);
        }
        throw new GameOverStalemate(turn);
    }
}
