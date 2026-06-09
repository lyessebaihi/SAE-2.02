package sae.model;

import java.util.List;

public class PlanificateurRobot {

    private Monde monde;
    private DijkstraPathFinder pathFinder;

    public PlanificateurRobot(Monde monde) {
        this.monde = monde;
        this.pathFinder = new DijkstraPathFinder(monde);
    }

    public void jouerTousLesRobots() {
        for (Robot robot : monde.getRobots()) {
            jouerRobot(robot);
        }

        monde.jouerTour();
    }

    public void jouerRobot(Robot robot) {
        Mine mineDisponible = choisirMine(robot);
        
        if (robot.estPlein()) {
            allerVersEntrepot(robot);
        } 
        else if (mineDisponible != null) {
            allerVersMine(robot);
        }
        else if (robot.getStockActuel() > 0) {
            allerVersEntrepot(robot);
        }
        
    }

    private void allerVersMine(Robot robot) {
        Mine mine = choisirMine(robot);

        if (mine == null) {
            return;
        }

        if (robot.getLigne() == mine.getLigne()
                && robot.getColonne() == mine.getColonne()) {
            robot.recolter(mine);
            return;
        }

        deplacerVers(robot, mine.getLigne(), mine.getColonne());
    }

    private void allerVersEntrepot(Robot robot) {
        Entrepot entrepot = choisirEntrepot(robot);

        if (entrepot == null) {
            return;
        }

        if (robot.getLigne() == entrepot.getLigne()
                && robot.getColonne() == entrepot.getColonne()) {
            robot.deposer(entrepot);
            return;
        }

        deplacerVers(robot, entrepot.getLigne(), entrepot.getColonne());
    }

    private Mine choisirMine(Robot robot) {
        Mine meilleureMine = null;
        int meilleureDistance = Integer.MAX_VALUE;

        for (Mine mine : monde.getMines()) {
            if (mine.getTypeMinerai() == robot.getTypeMinerai()
                    && !mine.estVide()) {

                Position depart = new Position(robot.getLigne(), robot.getColonne());
                Position arrivee = new Position(mine.getLigne(), mine.getColonne());

                List<Position> chemin = pathFinder.trouverChemin(depart, arrivee);

                if (!chemin.isEmpty() && chemin.size() < meilleureDistance) {
                    meilleureDistance = chemin.size();
                    meilleureMine = mine;
                }
            }
        }

        return meilleureMine;
    }

    private Entrepot choisirEntrepot(Robot robot) {
        for (Entrepot entrepot : monde.getEntrepots()) {
            if (entrepot.getTypeMinerai() == robot.getTypeMinerai()) {
                return entrepot;
            }
        }

        return null;
    }

    private void deplacerVers(Robot robot, int ligneBut, int colonneBut) {
        Position depart = new Position(robot.getLigne(), robot.getColonne());
        Position arrivee = new Position(ligneBut, colonneBut);

        List<Position> chemin = pathFinder.trouverChemin(depart, arrivee);

        if (chemin.size() < 2) {
            return;
        }

        Position prochainePosition = chemin.get(1);
        Direction direction = calculerDirection(depart, prochainePosition);

        if (direction == null) {
            return;
        }

        boolean deplacementReussi = monde.deplacerRobot(robot, direction);

        if (!deplacementReussi) {
            essayerDecalage(robot, direction);
        }
    }

    private boolean essayerDecalage(Robot robot, Direction directionBloquee) {
        Direction[] directions;

        if (directionBloquee == Direction.EST || directionBloquee == Direction.OUEST) {
            directions = new Direction[]{Direction.SUD, Direction.NORD};
        } else {
            directions = new Direction[]{Direction.EST, Direction.OUEST};
        }

        for (Direction direction : directions) {
            if (monde.deplacerRobot(robot, direction)) {
                return true;
            }
        }

        return false;
    }

    private Direction calculerDirection(Position depart, Position arrivee) {
        if (arrivee.getLigne() == depart.getLigne() - 1) {
            return Direction.NORD;
        }

        if (arrivee.getLigne() == depart.getLigne() + 1) {
            return Direction.SUD;
        }

        if (arrivee.getColonne() == depart.getColonne() + 1) {
            return Direction.EST;
        }

        if (arrivee.getColonne() == depart.getColonne() - 1) {
            return Direction.OUEST;
        }

        return null;
    }
}
