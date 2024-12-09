package com.miracosta.cs210.cs210.chess.pieces;

import com.miracosta.cs210.cs210.ImageManager;
import com.miracosta.cs210.cs210.chess.board.ChessBoard;
import com.miracosta.cs210.cs210.chess.board.ChessTile;
import javafx.scene.image.Image;

import static com.miracosta.cs210.cs210.chess.pieces.PieceColor.WHITE;

/**
 * A Knight that can jump in an L-ish shape
 */
public class Knight extends ChessPiece{
    /**
     * Create a Knight of the given Color
     * @param pieceColor Color of the Knight
     */
    public Knight(PieceColor pieceColor) {
        super(pieceColor);
    }

    /**
     * Create a WHITE Knight
     */
    public Knight() {
        super();
    }

    @Override
    public void calculateValidMoves(ChessBoard board) {
        legalMoves.clear();
        //knights can move every combination of +/-(2,1)
        addIfInBounds(board, 1, 2);
        addIfInBounds(board, -1, 2);
        addIfInBounds(board, 1, -2);
        addIfInBounds(board, -1, -2);
        addIfInBounds(board, 2, 1);
        addIfInBounds(board, -2, 1);
        addIfInBounds(board, 2, -1);
        addIfInBounds(board, -2, -1);

    }

    @Override
    public String toString() {
        if (getColor() == WHITE) return "♘";
        return "♞";
    }

    @Override
    public Image getImage() {
        ImageManager imageManager = ImageManager.getInstance();
        if (getColor() == WHITE) return imageManager.whiteKnight;
        return imageManager.blackKnight;
    }

    /**
     * Check if an offset results in an in-bounds square that is not occupied by a teammate, and add it to the legalMoves list if so
     * @param board ChessBoard to check
     * @param rowOffset difference in rows (+/-)
     * @param columnOffset difference in columns (+/-)
     */
    private void addIfInBounds(ChessBoard board, int rowOffset, int columnOffset) {
       ChessTile target = board.getTileByOffset(getPosition(), rowOffset, columnOffset);
       if (target == null) return;
       //if we're occupied by a teammate, you can't go there
       if (target.isOccupied()) {
           if (target.getPiece().getColor() == getColor()) return;
           //if the color is opposite, we can capture, so the move is legal
       }
       legalMoves.add(target);
    }
}
