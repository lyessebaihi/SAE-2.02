package sae.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RobotDeposerTest {

    @Test
    void deposer_reussit_siEntrepotMemeTypeEtRobotAvecStock() {
        Robot robot = new Robot(1, TypeMinerai.OR, 0, 0, 5, 2);
        Mine mine = new Mine(1, TypeMinerai.OR, 0, 0, 10);
        Entrepot entrepot = new Entrepot(1, TypeMinerai.OR, 0, 0);

        robot.recolter(mine); 

        boolean resultat = robot.deposer(entrepot);

        assertTrue(resultat);
        assertEquals(0, robot.getStockActuel());
        assertEquals(2, entrepot.getStock());
    }

    @Test
    void deposer_echoue_siEntrepotNull() {
        Robot robot = new Robot(1, TypeMinerai.OR, 0, 0, 5, 2);

        boolean resultat = robot.deposer(null);

        assertFalse(resultat);
        assertEquals(0, robot.getStockActuel());
    }

    @Test
    void deposer_echoue_siMauvaisTypeEntrepot() {RobotDeposerTest.java

        Robot robot = new Robot(1, TypeMinerai.OR, 0, 0, 5, 2);
        Mine mine = new Mine(1, TypeMinerai.OR, 0, 0, 10);
        Entrepot entrepotNickel = new Entrepot(1, TypeMinerai.NICKEL, 0, 0);

        robot.recolter(mine); // stock robot = 2

        boolean resultat = robot.deposer(entrepotNickel);
RobotDeposerTest.java

        assertFalse(resultat);
        assertEquals(2, robot.getStockActuel());
        assertEquals(0, entrepotNickel.getStock());
    }

    @Test
    void deposer_echoue_siRobotVide() {
        Robot robot = new Robot(1, TypeMinerai.OR, 0, 0, 5, RobotDeposerTest.java
2);
        Entrepot entrepot = new Entrepot(1, TypeMinerai.OR, 0, 0);

        boolean resultat = robot.deposer(entrepot);

        assertFalse(resultat);
        assertEquals(0, robot.getStockActuel());
        assertEquals(0, entrepot.getStock());
    }
}
