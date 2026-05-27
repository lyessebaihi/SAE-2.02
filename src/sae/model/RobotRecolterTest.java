package sae.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RobotRecolterTest {
    @Test
    void recolter_echoue_siMineNull() {
        Robot robot = new Robot(1, TypeMinerai.OR, 0, 0, 10, 3);

        boolean resultat = robot.recolter(null);

        assertFalse(resultat);
        assertEquals(0, robot.getStockActuel());
    }

    @Test
    void recolter_echoue_siMauvaisTypeMine() {
        Robot robot = new Robot(1, TypeMinerai.OR, 0, 0, 10, 3);
        Mine mineNickel = new Mine(1, TypeMinerai.NICKEL, 0, 0, 50);

        boolean resultat = robot.recolter(mineNickel);

        assertFalse(resultat);
        assertEquals(0, robot.getStockActuel());
        assertEquals(50, mineNickel.getStockActuel());
    }

    @Test
    void recolter_echoue_siRobotPlein() {
        Robot robot = new Robot(1, TypeMinerai.OR, 0, 0, 10, 3);
        Mine mineOr = new Mine(1, TypeMinerai.OR, 0, 0, 50);

        robot.recolter(mineOr);
        robot.recolter(mineOr);
        robot.recolter(mineOr);
        robot.recolter(mineOr);

        boolean resultat = robot.recolter(mineOr);

        assertFalse(resultat);
        assertEquals(10, robot.getStockActuel());
        assertEquals(40, mineOr.getStockActuel());
    }

    @Test
    void recolter_reussit_siMineCompatibleEtRobotPasPlein() {
        Robot robot = new Robot(1, TypeMinerai.OR, 0, 0, 10, 3);
        Mine mineOr = new Mine(1, TypeMinerai.OR, 0, 0, 50);

        robot.recolter(mineOr);
        boolean resultat = robot.recolter(mineOr);

        assertTrue(resultat);
        assertEquals(6, robot.getStockActuel());
        assertEquals(44, mineOr.getStockActuel());
    }
}