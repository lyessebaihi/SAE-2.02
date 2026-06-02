package sae.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlanificateurRobotTest {

    @Test
    void testChoisitMineLaPlusProche() {
        Monde monde = new Monde();

        Robot robot = new Robot(1, TypeMinerai.OR, 0, 0, 5, 5);

        Mine mineProche = new Mine(1, TypeMinerai.OR, 0, 3, 20);
        Mine mineLoin = new Mine(2, TypeMinerai.OR, 8, 8, 20);

        Entrepot entrepot = new Entrepot(1, TypeMinerai.OR, 5, 5);

        monde.ajouterRobot(robot);
        monde.ajouterMine(mineProche);
        monde.ajouterMine(mineLoin);
        monde.ajouterEntrepot(entrepot);

        PlanificateurRobot planificateur = new PlanificateurRobot(monde);

        for (int i = 0; i < 4; i++) {
            planificateur.jouerRobot(robot);
            monde.jouerTour();
        }

        assertEquals(0, robot.getLigne());
        assertEquals(3, robot.getColonne());

        assertTrue(mineProche.getStockActuel() < 20);
        assertEquals(20, mineLoin.getStockActuel());
    }

    @Test
    void testIgnoreMineVideEtChoisitAutreMine() {
        Monde monde = new Monde();

        Robot robot = new Robot(1, TypeMinerai.OR, 0, 0, 5, 5);

        Mine mineVideProche = new Mine(1, TypeMinerai.OR, 0, 1, 0);
        Mine minePleineLoin = new Mine(2, TypeMinerai.OR, 0, 4, 20);

        monde.ajouterRobot(robot);
        monde.ajouterMine(mineVideProche);
        monde.ajouterMine(minePleineLoin);
        monde.ajouterEntrepot(new Entrepot(1, TypeMinerai.OR, 5, 5));

        PlanificateurRobot planificateur = new PlanificateurRobot(monde);

        planificateur.jouerRobot(robot);
        monde.jouerTour();

        assertEquals(0, robot.getLigne());
        assertEquals(1, robot.getColonne());

        planificateur.jouerRobot(robot);
        monde.jouerTour();

        assertEquals(0, robot.getLigne());
        assertEquals(2, robot.getColonne());
    }

    @Test
    void testIgnoreMineMauvaisType() {
        Monde monde = new Monde();

        Robot robot = new Robot(1, TypeMinerai.OR, 0, 0, 5, 5);

        Mine mineNickelProche = new Mine(1, TypeMinerai.NICKEL, 0, 1, 20);
        Mine mineOrLoin = new Mine(2, TypeMinerai.OR, 0, 4, 20);

        monde.ajouterRobot(robot);
        monde.ajouterMine(mineNickelProche);
        monde.ajouterMine(mineOrLoin);
        monde.ajouterEntrepot(new Entrepot(1, TypeMinerai.OR, 5, 5));

        PlanificateurRobot planificateur = new PlanificateurRobot(monde);

        planificateur.jouerRobot(robot);
        monde.jouerTour();

        assertEquals(0, robot.getLigne());
        assertEquals(1, robot.getColonne());

        planificateur.jouerRobot(robot);
        monde.jouerTour();

        assertEquals(0, robot.getLigne());
        assertEquals(2, robot.getColonne());

        assertEquals(20, mineNickelProche.getStockActuel());
    }


}
