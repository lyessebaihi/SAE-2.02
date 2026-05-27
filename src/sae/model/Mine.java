package sae.model;

public class Mine {

    private int numero;
    private TypeMinerai typeMinerai;
    private int ligne;
    private int colonne;
    private int stockActuel;
    private int stockInitial;

    public Mine(int numero, TypeMinerai typeMinerai, int ligne, int colonne, int stockInitial) {
        this.numero = numero;
        this.typeMinerai = typeMinerai;
        this.ligne = ligne;
        this.colonne = colonne;
        this.stockInitial = stockInitial;
        this.stockActuel = stockInitial;
    }

    public int extraire(int quantite) {
        if (quantite <= 0 || stockActuel <= 0) {
            return 0; //si il y a rien
        }

        int quantiteExtraite = Math.min(quantite, stockActuel);
        stockActuel -= quantiteExtraite;
        return quantiteExtraite;
    }

    public boolean estVide() {
        return stockActuel == 0;
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

    public int getStockActuel() {
        return stockActuel;
    }

    public int getStockInitial() {
        return stockInitial;
    }
}