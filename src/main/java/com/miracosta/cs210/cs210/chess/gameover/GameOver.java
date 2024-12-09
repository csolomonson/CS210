package com.miracosta.cs210.cs210.chess.gameover;

import com.miracosta.cs210.cs210.chess.pieces.PieceColor;

import java.util.Objects;

public class GameOver extends RuntimeException{
    protected String winner;
    protected String loser;
    PieceColor defeated;

    public GameOver(PieceColor defeated) {
        super();
        this.defeated = defeated;
        if (Objects.requireNonNull(this.defeated) == PieceColor.BLACK) {
            winner = "White";
            loser = "Black";
        } else {
            winner = "Black";
            loser = "White";
        }
    }

    public PieceColor getDefeated() {
        return defeated;
    }
}
