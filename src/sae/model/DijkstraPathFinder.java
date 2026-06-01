package sae.model;

import java.util.*;

public class DijkstraPathFinder {

    private final Monde monde;

    public DijkstraPathFinder(Monde monde) {
        this.monde = monde;
    }

    public List<Position> trouverChemin(Position depart, Position arrivee) {
        Map<Position, Integer> distances = new HashMap<>();
        Map<Position, Position> precedents = new HashMap<>();
        Set<Position> visites = new HashSet<>();

        PriorityQueue<Position> file = new PriorityQueue<>(
                Comparator.comparingInt(position -> distances.getOrDefault(position, Integer.MAX_VALUE))
        );

        distances.put(depart, 0);
        file.add(depart);

        while (!file.isEmpty()) {
            Position actuelle = file.poll();

            if (visites.contains(actuelle)) {
                continue;
            }

            visites.add(actuelle);

            if (actuelle.equals(arrivee)) {
                return reconstruireChemin(precedents, depart, arrivee);
            }

            for (Position voisin : getVoisins(actuelle, arrivee)) {
                int nouvelleDistance = distances.get(actuelle) + 1;

                if (nouvelleDistance < distances.getOrDefault(voisin, Integer.MAX_VALUE)) {
                    distances.put(voisin, nouvelleDistance);
                    precedents.put(voisin, actuelle);
                    file.add(voisin);
                }
            }
        }

        return new ArrayList<>();
    }

    private List<Position> getVoisins(Position position, Position arrivee) {
        List<Position> voisins = new ArrayList<>();

        int ligne = position.getLigne();
        int colonne = position.getColonne();

        ajouterSiAccessible(voisins, ligne - 1, colonne, arrivee);
        ajouterSiAccessible(voisins, ligne + 1, colonne, arrivee);
        ajouterSiAccessible(voisins, ligne, colonne - 1, arrivee);
        ajouterSiAccessible(voisins, ligne, colonne + 1, arrivee);

        return voisins;
    }

    private void ajouterSiAccessible(List<Position> voisins, int ligne, int colonne, Position arrivee) {
        if (!monde.positionValide(ligne, colonne)) {
            return;
        }

        Secteur secteur = monde.getSecteur(ligne, colonne);

        boolean estArrivee = ligne == arrivee.getLigne() && colonne == arrivee.getColonne();

        if (secteur.estAccessible() || estArrivee) {
            voisins.add(new Position(ligne, colonne));
        }
    }

    private List<Position> reconstruireChemin(Map<Position, Position> precedents, Position depart, Position arrivee) {
        List<Position> chemin = new ArrayList<>();
        Position actuelle = arrivee;

        while (!actuelle.equals(depart)) {
            chemin.add(0, actuelle);
            actuelle = precedents.get(actuelle);

            if (actuelle == null) {
                return new ArrayList<>();
            }
        }

        chemin.add(0, depart);
        return chemin;
    }
}
