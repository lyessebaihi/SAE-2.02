package sae.app;

import sae.model.*;

public class TestPlanificateurRobot {

    public static void main(String[] args) {
        Monde monde = new Monde();

        Robot robot = new Robot(1, TypeMinerai.OR, 0, 0, 5, 5);
        Mine mine = new Mine(1, TypeMinerai.OR, 0, 5, 20);
        Entrepot entrepot = new Entrepot(1, TypeMinerai.OR, 5, 5);

        monde.ajouterRobot(robot);
        monde.ajouterMine(mine);
        monde.ajouterEntrepot(entrepot);

        PlanificateurRobot planificateur = new PlanificateurRobot(monde);

        for (int tour = 1; tour <= 20; tour++) {
            System.out.println("\n===== TOUR " + tour + " =====");

            planificateur.jouerRobot(robot);
            monde.afficherMonde();

            System.out.println("Stock robot : " + robot.getStockActuel());
            System.out.println("Stock mine : " + mine.getStockActuel());
            System.out.println("Stock entrepot : " + entrepot.getStock());

            monde.jouerTour();
        }
    }
}
