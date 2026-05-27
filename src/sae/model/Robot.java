package sae.model;

public class Robot {

    private int numero;
    private TypeMinerai typeMinerai;
    private int ligne;
    private int colonne;
    private int stockActuel;
    private int capaciteStockage;
    private int capaciteExtraction;

    public Robot(int numero, TypeMinerai typeMinerai, int ligne, int colonne,
                 int capaciteStockage, int capaciteExtraction) {
        this.numero = numero;
        this.typeMinerai = typeMinerai;
        this.ligne = ligne;
        this.colonne = colonne;
        this.stockActuel = 0;
        this.capaciteStockage = capaciteStockage;
        this.capaciteExtraction = capaciteExtraction;
    }

    public boolean recolter(Mine mine) {
        if (mine == null) {
            return false;
        }

        if (mine.getTypeMinerai() != typeMinerai) {
            return false;
        }

        if (mine.estVide() || estPlein()) {
            return false;
        }

        int placeDisponible = capaciteStockage - stockActuel;
        int quantiteDemandee = Math.min(capaciteExtraction, placeDisponible);
        int quantiteExtraite = mine.extraire(quantiteDemandee);

        stockActuel += quantiteExtraite;

        return quantiteExtraite > 0;
    }

    public boolean deposer(Entrepot entrepot) {
        if (entrepot == null) {
            return false;
        }

        if (entrepot.getTypeMinerai() != typeMinerai) {
            return false;
        }

        if (estVide()) {
            return false;
        }

        entrepot.ajouterMinerais(stockActuel);
        stockActuel = 0;

        return true;
    }

    public boolean estPlein() {
        return stockActuel >= capaciteStockage;
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

    public int getCapaciteStockage() {
        return capaciteStockage;
    }

    public int getCapaciteExtraction() {
        return capaciteExtraction;
    }

    public void setPosition(int ligne, int colonne) {
        this.ligne = ligne;
        this.colonne = colonne;
    }
}