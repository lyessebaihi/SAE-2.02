package sae.app;

import sae.model.*;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        Monde monde = new Monde();

        // Eau aléatoire
        for (int i = 0; i < 8; i++) {

            int ligne = random.nextInt(10);
            int colonne = random.nextInt(10);

            if (monde.caseLibre(ligne, colonne)) {
                monde.ajouterEau(ligne, colonne);
            }

            else {
                i--;
            }
        }

        // Mines
        int[] positionMine1 = positionAleatoireLibre(monde, random);

        Mine mine1 = new Mine(
                1,
                TypeMinerai.OR,
                positionMine1[0],
                positionMine1[1],
                nombreAleatoire(random, 50, 100)
        );

        int[] positionMine2 = positionAleatoireLibre(monde, random);

        Mine mine2 = new Mine(
                2,
                TypeMinerai.NICKEL,
                positionMine2[0],
                positionMine2[1],
                nombreAleatoire(random, 50, 100)
        );

        monde.ajouterMine(mine1);
        monde.ajouterMine(mine2);

        // Entrepôts
        int[] positionEntrepot1 = positionAleatoireLibre(monde, random);

        Entrepot entrepot1 = new Entrepot(
                1,
                TypeMinerai.OR,
                positionEntrepot1[0],
                positionEntrepot1[1]
        );

        int[] positionEntrepot2 = positionAleatoireLibre(monde, random);

        Entrepot entrepot2 = new Entrepot(
                2,
                TypeMinerai.NICKEL,
                positionEntrepot2[0],
                positionEntrepot2[1]
        );

        monde.ajouterEntrepot(entrepot1);
        monde.ajouterEntrepot(entrepot2);

        // Robots
        int[] positionRobot1 = positionAleatoireLibre(monde, random);

        Robot robot1 = new Robot(
                1,
                TypeMinerai.OR,
                positionRobot1[0],
                positionRobot1[1],
                nombreAleatoire(random, 5, 9),
                nombreAleatoire(random, 1, 3)
        );

        int[] positionRobot2 = positionAleatoireLibre(monde, random);

        Robot robot2 = new Robot(
                2,
                TypeMinerai.NICKEL,
                positionRobot2[0],
                positionRobot2[1],
                nombreAleatoire(random, 5, 9),
                nombreAleatoire(random, 1, 3)
        );

        monde.ajouterRobot(robot1);
        monde.ajouterRobot(robot2);

        while (true) {

            monde.afficherMonde();

            if (mine1.estVide() && mine2.estVide()) {

                System.out.println();
                System.out.println("===== FIN DU JEU =====");
                System.out.println("Toutes les mines sont vides.");

                break;
            }
            for (Robot robotChoisi : monde.getRobots()) {
            System.out.println();
            System.out.println("Quel robot veux-tu contrôler ?");
            System.out.println("1 = Robot OR");
            System.out.println("2 = Robot NICKEL");
            System.out.println("X = Quitter");
            System.out.print("Choix robot : ");

            String choixRobot = scanner.nextLine();

            if (choixRobot.equalsIgnoreCase("x")) {

                System.out.println("Fin du programme.");
                scanner.close();
                return;
            }

            System.out.println();
            System.out.println("===== ACTIONS ROBOT "
                    + robotChoisi.getNumero() + " =====");

            System.out.println("Z = Nord");
            System.out.println("S = Sud");
            System.out.println("D = Est");
            System.out.println("Q = Ouest");
            System.out.println("R = Recolter");
            System.out.println("P = Deposer");

            System.out.print("Votre choix : ");

            String touche = scanner.nextLine();

            if (touche.equalsIgnoreCase("z")) {

                if (!monde.deplacerRobot(robotChoisi, Direction.NORD)) {
                    System.out.println("Déplacement impossible.");
                }
            }

            else if (touche.equalsIgnoreCase("s")) {

                if (!monde.deplacerRobot(robotChoisi, Direction.SUD)) {
                    System.out.println("Déplacement impossible.");
                }
            }

            else if (touche.equalsIgnoreCase("d")) {

                if (!monde.deplacerRobot(robotChoisi, Direction.EST)) {
                    System.out.println("Déplacement impossible.");
                }
            }

            else if (touche.equalsIgnoreCase("q")) {

                if (!monde.deplacerRobot(robotChoisi, Direction.OUEST)) {
                    System.out.println("Déplacement impossible.");
                }
            }

            else if (touche.equalsIgnoreCase("r")) {

                Mine mine = monde.getMineSurCase(robotChoisi);

                if (robotChoisi.recolter(mine)) {
                    System.out.println("Récolte réussie.");
                }

                else {
                    System.out.println("Récolte impossible.");
                }
            }

            else if (touche.equalsIgnoreCase("p")) {

                Entrepot entrepot = monde.getEntrepotSurCase(robotChoisi);

                if (robotChoisi.deposer(entrepot)) {
                    System.out.println("Dépôt réussi.");
                }

                else {
                    System.out.println("Dépôt impossible.");
                }
            }

            else {
                System.out.println("Action invalide.");
            }
            }

            monde.jouerTour();
        }

        scanner.close();
    }

    public static int nombreAleatoire(Random random,
                                      int min,
                                      int max) {

        return random.nextInt(max - min + 1) + min;
    }
// genere aleatoirement
    public static int[] positionAleatoireLibre(Monde monde,
                                               Random random) {

        int ligne = random.nextInt(10);
        int colonne = random.nextInt(10);

        while (!monde.caseLibre(ligne, colonne)) {

            ligne = random.nextInt(10);
            colonne = random.nextInt(10);
        }

        return new int[]{ligne, colonne};
    }
}
