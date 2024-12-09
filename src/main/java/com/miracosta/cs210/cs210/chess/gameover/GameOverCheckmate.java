package com.miracosta.cs210.cs210.chess.gameover;

import com.miracosta.cs210.cs210.chess.pieces.Color;

public class GameOverCheckmate extends GameOver{
    public GameOverCheckmate(Color victor) {
        super(victor);
    }

    @Override
    public String getMessage() {
        return winner + "defeats " + loser +" by Checkmate.";
    }
}
