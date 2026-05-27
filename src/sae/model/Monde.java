package sae.model;

import java.util.ArrayList;

public class Monde {

    private Secteur[][] secteurs;
    private ArrayList<Robot> robots;
    private ArrayList<Mine> mines;
    private ArrayList<Entrepot> entrepots;
    private int tourActuel;

    public Monde() {
        secteurs = new Secteur[10][10];
        robots = new ArrayList<>();
        mines = new ArrayList<>();
        entrepots = new ArrayList<>();
        tourActuel = 0;

        creerSecteurs();
    }

    private void creerSecteurs() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                secteurs[i][j] = new Secteur(i, j, false);
            }
        }
    }

    public boolean positionValide(int ligne, int colonne) {
        return ligne >= 0 && ligne < 10 && colonne >= 0 && colonne < 10;
    }

    public boolean caseLibre(int ligne, int colonne) {
        if (!positionValide(ligne, colonne)) {
            return false;
        }

        Secteur secteur = secteurs[ligne][colonne];

        return !secteur.isEstEau()
                && secteur.getRobot() == null
                && secteur.getMine() == null
                && secteur.getEntrepot() == null;
    }

    public void ajouterEau(int ligne, int colonne) {
        if (caseLibre(ligne, colonne)) {
            secteurs[ligne][colonne] = new Secteur(ligne, colonne, true);
        }
    }

    public void ajouterRobot(Robot robot) {
        robots.add(robot);
        secteurs[robot.getLigne()][robot.getColonne()].placerRobot(robot);
    }

    public void ajouterMine(Mine mine) {
        mines.add(mine);
        secteurs[mine.getLigne()][mine.getColonne()].setMine(mine);
    }

    public void ajouterEntrepot(Entrepot entrepot) {
        entrepots.add(entrepot);
        secteurs[entrepot.getLigne()][entrepot.getColonne()].setEntrepot(entrepot);
    }

    public boolean deplacerRobot(Robot robot, Direction direction) {
        int ancienneLigne = robot.getLigne();
        int ancienneColonne = robot.getColonne();

        int nouvelleLigne = ancienneLigne;
        int nouvelleColonne = ancienneColonne;

        if (direction == Direction.NORD) {
            nouvelleLigne--;
        } else if (direction == Direction.SUD) {
            nouvelleLigne++;
        } else if (direction == Direction.EST) {
            nouvelleColonne++;
        } else if (direction == Direction.OUEST) {
            nouvelleColonne--;
        }

        if (!positionValide(nouvelleLigne, nouvelleColonne)) {
            return false;
        }

        if (!secteurs[nouvelleLigne][nouvelleColonne].estAccessible()) {
            return false;
        }

        secteurs[ancienneLigne][ancienneColonne].retirerRobot();
        robot.setPosition(nouvelleLigne, nouvelleColonne);
        secteurs[nouvelleLigne][nouvelleColonne].placerRobot(robot);

        return true;
    }

    public Mine getMineSurCase(Robot robot) {
        return secteurs[robot.getLigne()][robot.getColonne()].getMine();
    }

    public Entrepot getEntrepotSurCase(Robot robot) {
        return secteurs[robot.getLigne()][robot.getColonne()].getEntrepot();
    }

    public Secteur getSecteur(int ligne, int colonne) {
        return secteurs[ligne][colonne];
    }

    public void afficherMonde() {
        System.out.println("\n\n\n\n\n");

        System.out.println("========== MONDE ==========");
        System.out.println("Tour : " + tourActuel);
        System.out.println();

        System.out.print("    ");
        for (int j = 0; j < 10; j++) {
            System.out.print(j + "  ");
        }
        System.out.println();

        for (int i = 0; i < 10; i++) {
            System.out.print(i + " | ");

            for (int j = 0; j < 10; j++) {
                Secteur secteur = secteurs[i][j];

                if (secteur.isEstEau()) {
                    System.out.print("XX ");
                } else if (secteur.getMine() != null) {
                    System.out.print("M" + secteur.getMine().getNumero() + " ");
                } else if (secteur.getEntrepot() != null) {
                    System.out.print("E" + secteur.getEntrepot().getNumero() + " ");
                } else {
                    System.out.print("-- ");
                }
            }

            System.out.println();

            System.out.print("  | ");

            for (int j = 0; j < 10; j++) {
                Secteur secteur = secteurs[i][j];

                if (secteur.isEstEau()) {
                    System.out.print("XX ");
                } else if (secteur.getRobot() != null) {
                    System.out.print("R" + secteur.getRobot().getNumero() + " ");
                } else {
                    System.out.print("-- ");
                }
            }

            System.out.println();
        }

        System.out.println();
        System.out.println("========== INFORMATIONS ==========");

        for (Robot robot : robots) {
            System.out.println(
                    "R" + robot.getNumero()
                            + " | Type : " + robot.getTypeMinerai()
                            + " | Position : (" + robot.getLigne() + "," + robot.getColonne() + ")"
                            + " | Stock : " + robot.getStockActuel() + "/" + robot.getCapaciteStockage()
                            + " | Extraction : " + robot.getCapaciteExtraction()
            );
        }

        System.out.println();

        for (Mine mine : mines) {
            System.out.println(
                    "M" + mine.getNumero()
                            + " | Type : " + mine.getTypeMinerai()
                            + " | Position : (" + mine.getLigne() + "," + mine.getColonne() + ")"
                            + " | Stock : " + mine.getStockActuel() + "/" + mine.getStockInitial()
            );
        }

        System.out.println();

        for (Entrepot entrepot : entrepots) {
            System.out.println(
                    "E" + entrepot.getNumero()
                            + " | Type : " + entrepot.getTypeMinerai()
                            + " | Position : (" + entrepot.getLigne() + "," + entrepot.getColonne() + ")"
                            + " | Stock : " + entrepot.getStock()
            );
        }
    }
    public ArrayList<Robot> getRobots(){
        return robots;
    }

    public void jouerTour() {
        tourActuel++;
    }
    public int getTourActuel() {
        return tourActuel;
    }


}
