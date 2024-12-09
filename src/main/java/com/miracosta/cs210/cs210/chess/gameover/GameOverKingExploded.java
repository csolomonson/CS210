package com.miracosta.cs210.cs210.chess.gameover;

import com.miracosta.cs210.cs210.chess.pieces.PieceColor;

public class GameOverKingExploded extends GameOver{
    public GameOverKingExploded(PieceColor victor) {
        super(victor);
    }

    @Override
    public String getMessage() {
        return winner + " wins; " + loser + " king exploded.";
    }
}
