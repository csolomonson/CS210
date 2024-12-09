package com.miracosta.cs210.cs210.chess.gameover;

import com.miracosta.cs210.cs210.chess.pieces.PieceColor;

public class GameOverCheckmate extends GameOver{
    public GameOverCheckmate(PieceColor victor) {
        super(victor);
    }

    @Override
    public String getMessage() {
        return winner + "defeats " + loser +" by Checkmate.";
    }
}
