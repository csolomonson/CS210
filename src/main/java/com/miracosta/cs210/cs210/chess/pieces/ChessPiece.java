package com.miracosta.cs210.cs210.chess.pieces;

import com.miracosta.cs210.cs210.chess.board.ChessTile;

import java.util.ArrayList;

public abstract class ChessPiece {
    private ChessTile position;
    private Color color;
    private final ArrayList<ChessPiece> canAttack;
    private final ArrayList<ChessPiece> canBeAttacked;

    ChessPiece() {
        canAttack = new ArrayList<>();
        canBeAttacked = new ArrayList<>();
        position = new ChessTile();
        color = Color.WHITE;
    }

    ChessPiece(Color color) {
        this();
        this.color = color;
    }


    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ChessTile getPosition() {
        return position;
    }

    public void setPosition(ChessTile position) {
        this.position = position;
    }

    public void clearAttackers() {
        canBeAttacked.clear();
    }

    public boolean addAttacker(ChessPiece attacker) {
        if (isAttacker(attacker)) {
            return false;
        }
        return canBeAttacked.add(attacker);
    }

    public boolean isAttacker(ChessPiece attacker) {
        return canBeAttacked.contains(attacker);
    }

    public void clearTargets() {
        canAttack.clear();
    }

    public boolean addTarget(ChessPiece target) {
        if (isTarget(target)) {
            return false;
        }
        return canAttack.add(target);
    }

    public boolean isTarget(ChessPiece target) {
        return canAttack.contains(target);
    }

    public abstract boolean move(ChessTile moveTo);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return getColor() == that.getColor();
    }
}
