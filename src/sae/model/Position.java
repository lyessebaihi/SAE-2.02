package sae.model;


import java.util.Objects;

public class Position {
    private  int ligne;
    private  int colonne;

    public Position(int ligne, int colonne) {
        this.ligne = ligne;
        this.colonne = colonne;
    }

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Position position)) return false;
        return ligne == position.ligne && colonne == position.colonne;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ligne, colonne);
    }

    @Override
    public String toString() {
        return "(" + ligne + "," + colonne + ")";
    }
}
