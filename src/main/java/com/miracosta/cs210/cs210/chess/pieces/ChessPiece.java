package com.miracosta.cs210.cs210.chess.pieces;

import com.miracosta.cs210.cs210.chess.board.ChessBoard;
import com.miracosta.cs210.cs210.chess.board.ChessTile;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Generic class for any type of chess piece (Pawn, Knight, Bishop, Rook, Queen, King)
 */
public abstract class ChessPiece implements Cloneable {
    private ChessTile position;
    private PieceColor pieceColor;
    private ArrayList<ChessPiece> canAttack;
    private ArrayList<ChessPiece> canBeAttacked;
    protected ArrayList<ChessTile> legalMoves;
    protected ChessBoard board;
    //protected ImageManager imageManager;

    //TODO Can't move King into check
    //TODO Can't reveal checks
    //TODO Castling
    //TODO Promotion

    /**
     * Initializes a white generic piece
     */
    ChessPiece() {
        canAttack = new ArrayList<>();
        canBeAttacked = new ArrayList<>();
        legalMoves = new ArrayList<>();
        position = new ChessTile();
        pieceColor = PieceColor.WHITE;
        //imageManager = ImageManager.getInstance();
    }

    /**
     * Initializes a generic piece of the specified color
     *
     * @param pieceColor Color enum for this piece
     */
    ChessPiece(PieceColor pieceColor) {
        this();
        this.pieceColor = pieceColor;
    }

    /**
     * Get this piece's color
     *
     * @return Color enum representing this piece's color
     */
    public PieceColor getColor() {
        return pieceColor;
    }

    /**
     * Change the color of this piece
     *
     * @param pieceColor new Color enum
     */
    public void setColor(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }

    /**
     * Get ChessTile representation of current board position
     *
     * @return ChessTile that this piece is on
     */
    public ChessTile getPosition() {
        return position;
    }

    /**
     * Set this piece's position (and board)
     *
     * @param position ChessTile to put this piece on
     */
    public void setPosition(ChessTile position) {
        this.position = position;
        board = position.getBoard();
    }

    /**
     * Clear the ArrayList of pieces that can capture this piece
     */
    public void clearAttackers() {
        canBeAttacked.clear();
    }

    /**
     * Add a piece that can capture this piece (to be used for minimax)
     *
     * @param attacker Piece of opposite color that could capture this piece on the next move
     * @return true if a new attacker was added, false if it already existed.
     */
    public boolean addAttacker(ChessPiece attacker) {
        if (isAttacker(attacker)) {
            return false;
        }
        return canBeAttacked.add(attacker);
    }

    /**
     * Check if a given piece is attacking this piece (for minimax and checks)
     *
     * @param attacker Piece that might attack this piece
     * @return whether the given piece can capture this piece in one move
     */
    public boolean isAttacker(ChessPiece attacker) {
        return canBeAttacked.contains(attacker);
    }

    /**
     * Clear list of pieces that this piece can capture in one move (for minimax)
     */
    public void clearTargets() {
        canAttack.clear();
    }

    /**
     * Add a piece to the list of pieces that this piece can capture in one move (for minimax)
     *
     * @param target Piece that this Piece can capture
     * @return true if this adds a new piece
     */
    public boolean addTarget(ChessPiece target) {
        if (isTarget(target)) {
            return false;
        }
        return canAttack.add(target);
    }

    /**
     * Check if a piece can be captured by this piece in one move
     *
     * @param target Piece that can possibly be captured by this piece
     * @return True if this piece can capture the given piece in one move
     */
    public boolean isTarget(ChessPiece target) {
        return canAttack.contains(target);
    }

    /**
     * Check if a move is legal, and move if so
     *
     * @param moveTo ChessTile representation of target position
     * @return true if the move has been made; false if move is illegal
     */
    public boolean move(ChessTile moveTo) {
        if (moveTo == null) return false;
        if (!getLegalMoves().contains(moveTo)) return false;
        //clear piece if en passant-ing
        if (this instanceof EnPassantPiece epp) {
            if (epp.isCapturingEnPassant() && epp.getCaptureTile().hasEnPassantablePiece(getOppositeColor()) && epp.getLandingTile().equals(moveTo)) {
                epp.getCaptureTile().clearPiece();
            }
            epp.updateEnPassantStatus();
        }
        //make the move
        getPosition().clearPiece();
        moveTo.forceSetPiece(this);
        //board.update();
        return true;
    }

    /**
     * Look at the board and make a list of the moves this piece can legally make
     *
     * @param board The ChessBoard to consider
     */
    public abstract void calculateValidMoves(ChessBoard board);

    public abstract String toString();

    public abstract Image getImage();


    public void removeCheckingMoves() {
        ArrayList<ChessTile> toRemove = new ArrayList<>();
        for (int i = 0; i < legalMoves.size(); i++) {
            ChessTile move = legalMoves.get(i);
            ChessBoard hypothetical = board.hypotheticalMove(position.getRow(), position.getColumn(), move.getRow(), move.getColumn());
            if (hypothetical.getCheck(getColor())) {
                toRemove.add(move);
                //System.out.println("A MOVE THAT SHOULD BE REMOVED WAS FOUND");
            }
        }
        for (ChessTile move : toRemove) {
            legalMoves.remove(move);//System.out.println(this + "CAN NOT LEGALLY MOVE TO " + move.getRow() +", " + move.getColumn());
        }
        //System.out.println(legalMoves.size());
    }

    /**
     * Get a list of every ChessTile that this piece can legally move to
     *
     * @return ArrayList of legal ending positions (captures included)
     */
    public ArrayList<ChessTile> getLegalMoves() {
        return legalMoves;
    }

    /**
     * Get the color of the opponents
     *
     * @return BLACK if this piece is WHITE, WHITE if this piece is BLACK
     */
    protected PieceColor getOppositeColor() {
        if (pieceColor == PieceColor.WHITE) return PieceColor.BLACK;
        return PieceColor.WHITE;
    }

    /**
     * Check if a tile can legally be moved to
     *
     * @param row    row position of tile to check
     * @param column column position of tile to check
     * @return True if this piece can legally move to (row, column) on this move.
     */
    public boolean isLegalMove(int row, int column) {
        return getLegalMoves().contains(new ChessTile(row, column));
    }

    /**
     * Checks if two pieces are the same type.
     * e.g. All BLACK Rooks are equal to each other, regardless of position.
     *
     * @param o Object to check for equality
     * @return True if Object is the same color and piece type
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return getColor() == that.getColor();
    }

    @Override
    public ChessPiece clone() {
        try {
            ChessPiece clone = (ChessPiece) super.clone();
            clone.setColor(getColor());
            clone.canAttack = new ArrayList<>();
            clone.canBeAttacked = new ArrayList<>();
            clone.legalMoves = new ArrayList<>();
            clone.position = null;

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public ChessPiece oppositeClone() {
        ChessPiece clone = clone();
        if (getColor() == PieceColor.WHITE) clone.setColor(PieceColor.BLACK);
        else clone.setColor(PieceColor.WHITE);
        return clone;
    }

    public PieceColor lookForChecks() {
        for (ChessTile tile : legalMoves) {
            //no need to even look if the opponent is already in check
            if (board.getCheck(getOppositeColor())) return PieceColor.EMPTY;
            if (tile.isOccupied()) {
                if (tile.getPiece() instanceof King && tile.getPiece().getColor() == getOppositeColor()) {
                    //System.out.println("CHECK FROM " + this);
                    return getOppositeColor();
                }
            }
        }
        return PieceColor.EMPTY;
    }
}
