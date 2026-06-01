package sae.app;

import sae.model.*;

import java.util.List;

public class TestDijkstra2 {

    public static void main(String[] args) {
        Monde monde = new Monde();

        // Mur d'eau à contourner
        monde.ajouterEau(0, 3);
        monde.ajouterEau(1, 1);
        monde.ajouterEau(1, 3);
        monde.ajouterEau(2, 1);
        monde.ajouterEau(3, 1);
        monde.ajouterEau(3, 2);
        monde.ajouterEau(3, 3);
        monde.ajouterEau(4,3);

        Robot robot = new Robot(1, TypeMinerai.OR, 0, 0, 5, 1);
        monde.ajouterRobot(robot);

        Position depart = new Position(robot.getLigne(), robot.getColonne());
        Position arrivee = new Position(4, 4);

        DijkstraPathFinder dijkstra = new DijkstraPathFinder(monde);
        List<Position> chemin = dijkstra.trouverChemin(depart, arrivee);
        AffichageChemin.afficher(monde, chemin, depart, arrivee);

        System.out.println("Chemin trouvé :");

        if (chemin.isEmpty()) {
            System.out.println("Aucun chemin trouvé.");
        } else {
            for (Position position : chemin) {
                System.out.println(position);
            }
        }
    }
}
