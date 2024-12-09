package com.miracosta.cs210.cs210.chess.gameover;

import com.miracosta.cs210.cs210.chess.pieces.Color;

public class GameOverStalemate extends GameOver{
    public GameOverStalemate(Color victor) {
        super(victor);
    }

    @Override
    public String getMessage() {
        return "Game ends in draw by stalemate (no legal moves for " + loser + ").";
    }
}
