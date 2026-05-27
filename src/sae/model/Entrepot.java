package sae.model;

public class Entrepot {

    private int numero;
    private TypeMinerai typeMinerai;
    private int ligne;
    private int colonne;
    private int stock;

    public Entrepot(int numero, TypeMinerai typeMinerai, int ligne, int colonne) {
        this.numero = numero;
        this.typeMinerai = typeMinerai;
        this.ligne = ligne;
        this.colonne = colonne;
        this.stock = 0;
    }

    public void ajouterMinerais(int quantite) {
        if (quantite > 0) {
            stock += quantite;
        }
    }

    public int getNumero() {
        return numero;
    }

    public TypeMinerai getTypeMinerai() {
        return typeMinerai;
    }

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }

    public int getStock() {
        return stock;
    }
}