package sae.model;

public class Secteur {

    private int ligne;
    private int colonne;
    private boolean estEau;
    private Mine mine;
    private Entrepot entrepot;
    private Robot robot;

    public Secteur(int ligne, int colonne, boolean estEau) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.estEau = estEau;
        this.mine = null;
        this.entrepot = null;
        this.robot = null;
    }

    public boolean estAccessible() {
        return !estEau && robot == null;
    }

    public boolean estOccupeParRobot() {
        return robot != null;
    }

    public void placerRobot(Robot robot) {
        this.robot = robot;
    }

    public void retirerRobot() {
        this.robot = null;
    }

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }

    public boolean isEstEau() {
        return estEau;
    }

    public Mine getMine() {
        return mine;
    }

    public void setMine(Mine mine) {
        this.mine = mine;
    }

    public Entrepot getEntrepot() {
        return entrepot;
    }

    public void setEntrepot(Entrepot entrepot) {
        this.entrepot = entrepot;
    }

    public Robot getRobot() {
        return robot;
    }
}