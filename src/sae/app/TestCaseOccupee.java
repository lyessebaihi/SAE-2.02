package sae.app;

import org.junit.jupiter.api.Test;
import sae.model.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.;

class TestCaseOccupee {

    @Test
    void testCaseOccupee() {

        Monde monde = new Monde();
        Robot robot1 = new Robot(1, TypeMinerai.OR, 0, 0, 5, 1);
        Robot robot2 = new Robot(2, TypeMinerai.NICKEL, 0, 2, 5, 1);

        monde.ajouterRobot(robot1);
        monde.ajouterRobot(robot2);

        Position depart = new Position(0, 0);
        Position arrivee = new Position(0, 4);

        DijkstraPathFinder dijkstra = new DijkstraPathFinder(monde);

        List<Position> chemin = dijkstra.trouverChemin(depart, arrivee);

        assertFalse(chemin.isEmpty());
        assertEquals(arrivee, chemin.get(chemin.size() - 1));
        assertFalse(chemin.contains(new Position(0, 2)));
    }
}
