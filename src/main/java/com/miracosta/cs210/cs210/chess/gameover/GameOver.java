package com.miracosta.cs210.cs210.chess.gameover;

import com.miracosta.cs210.cs210.chess.pieces.Color;

import java.util.Objects;

public class GameOver extends RuntimeException{
    protected String winner;
    protected String loser;
    Color victor;

    public GameOver(Color victor) {
        super();
        this.victor = victor;
        if (Objects.requireNonNull(victor) == Color.WHITE) {
            winner = "White";
            loser = "Black";
        } else {
            winner = "Black";
            loser = "White";
        }
    }

    public Color getVictor() {
        return victor;
    }
}
