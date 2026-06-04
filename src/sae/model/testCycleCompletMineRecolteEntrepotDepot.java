package sae.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class CycleCompletRobotTest {

    @Test
    void testCycleCompletMineRecolteEntrepotDepot() {
        Monde monde = new Monde();

        Robot robot = new Robot(1, TypeMinerai.OR, 0, 0, 3, 1);
        Mine mine = new Mine(1, TypeMinerai.OR, 0, 2, 10);
        Entrepot entrepot = new Entrepot(1, TypeMinerai.OR, 0, 4);

        monde.ajouterRobot(robot);
        monde.ajouterMine(mine);
        monde.ajouterEntrepot(entrepot);

        PlanificateurRobot planificateur = new PlanificateurRobot(monde);

        for (int i = 0; i < 20; i++) {
            planificateur.jouerRobot(robot);
            monde.jouerTour(); }

        assertTrue(mine.getStockActuel() < 10);
        assertTrue(entrepot.getStock() > 0);
        assertTrue(robot.getStockActuel() >= 0);
        assertTrue(robot.getStockActuel() <= robot.getCapaciteStockage());
        assertEquals(20, monde.getTourActuel());}
}
