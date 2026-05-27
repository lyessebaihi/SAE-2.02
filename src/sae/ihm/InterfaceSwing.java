package sae.ihm;

import sae.model.*;


import sae.model.Direction;
import sae.model.Entrepot;
import sae.model.Mine;
import sae.model.Monde;
import sae.model.Robot;
import sae.model.Secteur;
import sae.model.TypeMinerai;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class InterfaceSwing extends JFrame {

    private Monde monde;
    private Robot robot1, robot2, robotChoisi;
    private Mine mine1, mine2;
    private Entrepot entrepot1, entrepot2;

    private JLabel[][] cases = new JLabel[20][20];
    private JTextArea infos = new JTextArea();
    private JLabel labelTour = new JLabel("Tour : 0");
    private JLabel labelRobot = new JLabel("Robot choisi : aucun");

    private boolean robot1AJoue = false;
    private boolean robot2AJoue = false;

    public InterfaceSwing() {

        monde = new Monde();
        creerMonde();

        setTitle("SAE Robots Mineurs");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel principal = new JPanel(new BorderLayout());

        JLabel titre = new JLabel("SAE Robots Mineurs", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 24));

        principal.add(titre, BorderLayout.NORTH);
        principal.add(creerGrille(), BorderLayout.CENTER);
        principal.add(creerCommandes(), BorderLayout.EAST);

        infos.setEditable(false);
        infos.setFont(new Font("Arial", Font.PLAIN, 14));
        principal.add(infos, BorderLayout.SOUTH);

        add(principal);

        afficherGrille();
        afficherInfos();

        setVisible(true);
    }

    private JPanel creerGrille() {
        JPanel grille = new JPanel(new GridLayout(20, 20));

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {

                JLabel caseGraphique = new JLabel("", SwingConstants.CENTER);
                caseGraphique.setOpaque(true);
                caseGraphique.setFont(new Font("Arial", Font.BOLD, 13));

                int haut = (i % 2 == 0) ? 2 : 1;
                int gauche = (j % 2 == 0) ? 2 : 1;
                int bas = (i % 2 == 1) ? 2 : 1;
                int droite = (j % 2 == 1) ? 2 : 1;

                caseGraphique.setBorder(BorderFactory.createMatteBorder(
                        haut, gauche, bas, droite, Color.BLACK
                ));

                cases[i][j] = caseGraphique;
                grille.add(caseGraphique);
            }
        }

        return grille;
    }

    private JPanel creerCommandes() {
        JPanel commandes = new JPanel();
        commandes.setLayout(new BoxLayout(commandes, BoxLayout.Y_AXIS));
        commandes.setPreferredSize(new Dimension(230, 600));

        JButton boutonR1 = new JButton("Robot 1");
        JButton boutonR2 = new JButton("Robot 2");

        boutonR1.addActionListener(e -> {
            robotChoisi = robot1;
            labelRobot.setText("Robot choisi : R1");
        });

        boutonR2.addActionListener(e -> {
            robotChoisi = robot2;
            labelRobot.setText("Robot choisi : R2");
        });

        JPanel fleches = new JPanel(new GridLayout(3, 3));
        fleches.setMaximumSize(new Dimension(150, 150));

        JButton haut = new JButton("↑");
        JButton bas = new JButton("↓");
        JButton gauche = new JButton("←");
        JButton droite = new JButton("→");

        fleches.add(new JLabel(""));
        fleches.add(haut);
        fleches.add(new JLabel(""));
        fleches.add(gauche);
        fleches.add(new JLabel(""));
        fleches.add(droite);
        fleches.add(new JLabel(""));
        fleches.add(bas);
        fleches.add(new JLabel(""));

        JButton recolter = new JButton("Récolter");
        JButton deposer = new JButton("Déposer");

        haut.addActionListener(e -> action("NORD"));
        bas.addActionListener(e -> action("SUD"));
        gauche.addActionListener(e -> action("OUEST"));
        droite.addActionListener(e -> action("EST"));
        recolter.addActionListener(e -> action("RECOLTER"));
        deposer.addActionListener(e -> action("DEPOSER"));

        commandes.add(new JLabel("Robot choisi"));
        commandes.add(boutonR1);
        commandes.add(boutonR2);
        commandes.add(labelRobot);
        commandes.add(labelTour);
        commandes.add(Box.createVerticalStrut(20));
        commandes.add(new JLabel("Déplacement"));
        commandes.add(fleches);
        commandes.add(Box.createVerticalStrut(20));
        commandes.add(recolter);
        commandes.add(deposer);

        return commandes;
    }

    private void action(String action) {

        if (robotChoisi == null) {
            JOptionPane.showMessageDialog(this, "Choisis un robot.");
            return;
        }

        if (robotChoisi == robot1 && robot1AJoue) {
            JOptionPane.showMessageDialog(this, "Robot 1 a déjà joué ce tour.");
            return;
        }

        if (robotChoisi == robot2 && robot2AJoue) {
            JOptionPane.showMessageDialog(this, "Robot 2 a déjà joué ce tour.");
            return;
        }

        boolean ok = false;

        if (action.equals("NORD")) {
            ok = monde.deplacerRobot(robotChoisi, Direction.NORD);
        } else if (action.equals("SUD")) {
            ok = monde.deplacerRobot(robotChoisi, Direction.SUD);
        } else if (action.equals("EST")) {
            ok = monde.deplacerRobot(robotChoisi, Direction.EST);
        } else if (action.equals("OUEST")) {
            ok = monde.deplacerRobot(robotChoisi, Direction.OUEST);
        } else if (action.equals("RECOLTER")) {
            ok = robotChoisi.recolter(monde.getMineSurCase(robotChoisi));
        } else if (action.equals("DEPOSER")) {
            ok = robotChoisi.deposer(monde.getEntrepotSurCase(robotChoisi));
        }

        if (!ok) {
            JOptionPane.showMessageDialog(this, "Action impossible.");
            return;
        }

        if (robotChoisi == robot1) {
            robot1AJoue = true;
        } else if (robotChoisi == robot2) {
            robot2AJoue = true;
        }

        robotChoisi = null;
        labelRobot.setText("Robot choisi : aucun");

        if (robot1AJoue && robot2AJoue) {
            monde.jouerTour();
            robot1AJoue = false;
            robot2AJoue = false;
            JOptionPane.showMessageDialog(this, "Fin du tour. Nouveau tour.");
        }

        afficherGrille();
        afficherInfos();
    }

    private void afficherGrille() {

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                cases[i][j].setText("");
                cases[i][j].setBackground(Color.WHITE);
            }
        }

        for (int ligne = 0; ligne < 10; ligne++) {
            for (int colonne = 0; colonne < 10; colonne++) {

                Secteur secteur = monde.getSecteur(ligne, colonne);

                int l = ligne * 2;
                int c = colonne * 2;

                if (secteur.isEstEau()) {
                    remplirBloc(l, c, "X", Color.CYAN);
                }

                if (secteur.getMine() != null) {
                    cases[l][c].setText("M");
                    cases[l][c + 1].setText("" + secteur.getMine().getNumero());
                    cases[l][c].setBackground(Color.GREEN);
                    cases[l][c + 1].setBackground(Color.GREEN);
                }

                if (secteur.getEntrepot() != null) {
                    cases[l][c].setText("E");
                    cases[l][c + 1].setText("" + secteur.getEntrepot().getNumero());
                    cases[l][c].setBackground(Color.YELLOW);
                    cases[l][c + 1].setBackground(Color.YELLOW);
                }

                if (secteur.getRobot() != null) {
                    cases[l + 1][c].setText("R");
                    cases[l + 1][c + 1].setText("" + secteur.getRobot().getNumero());
                    cases[l + 1][c].setBackground(Color.PINK);
                    cases[l + 1][c + 1].setBackground(Color.PINK);
                }
            }
        }
    }

    private void remplirBloc(int l, int c, String texte, Color couleur) {
        cases[l][c].setText(texte);
        cases[l][c + 1].setText(texte);
        cases[l + 1][c].setText(texte);
        cases[l + 1][c + 1].setText(texte);

        cases[l][c].setBackground(couleur);
        cases[l][c + 1].setBackground(couleur);
        cases[l + 1][c].setBackground(couleur);
        cases[l + 1][c + 1].setBackground(couleur);
    }

    private void afficherInfos() {
        labelTour.setText("Tour : " + monde.getTourActuel());

        infos.setText(
                "ROBOTS\n"
                        + "R1 | " + robot1.getTypeMinerai()
                        + " | Position : (" + robot1.getLigne() + "," + robot1.getColonne() + ")"
                        + " | Stock : " + robot1.getStockActuel() + "/" + robot1.getCapaciteStockage()
                        + "\n"
                        + "R2 | " + robot2.getTypeMinerai()
                        + " | Position : (" + robot2.getLigne() + "," + robot2.getColonne() + ")"
                        + " | Stock : " + robot2.getStockActuel() + "/" + robot2.getCapaciteStockage()
                        + "\n\nMINES\n"
                        + "M1 | " + mine1.getTypeMinerai()
                        + " | Position : (" + mine1.getLigne() + "," + mine1.getColonne() + ")"
                        + " | Stock : " + mine1.getStockActuel() + "/" + mine1.getStockInitial()
                        + "\n"
                        + "M2 | " + mine2.getTypeMinerai()
                        + " | Position : (" + mine2.getLigne() + "," + mine2.getColonne() + ")"
                        + " | Stock : " + mine2.getStockActuel() + "/" + mine2.getStockInitial()
                        + "\n\nENTREPOTS\n"
                        + "E1 | " + entrepot1.getTypeMinerai()
                        + " | Position : (" + entrepot1.getLigne() + "," + entrepot1.getColonne() + ")"
                        + " | Stock : " + entrepot1.getStock()
                        + "\n"
                        + "E2 | " + entrepot2.getTypeMinerai()
                        + " | Position : (" + entrepot2.getLigne() + "," + entrepot2.getColonne() + ")"
                        + " | Stock : " + entrepot2.getStock()
        );
    }

    private void creerMonde() {
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            int[] pos = positionLibre(random);
            monde.ajouterEau(pos[0], pos[1]);
        }

        int[] p1 = positionLibre(random);
        mine1 = new Mine(1, TypeMinerai.OR, p1[0], p1[1], nombreAleatoire(random, 50, 100));
        monde.ajouterMine(mine1);

        int[] p2 = positionLibre(random);
        mine2 = new Mine(2, TypeMinerai.NICKEL, p2[0], p2[1], nombreAleatoire(random, 50, 100));
        monde.ajouterMine(mine2);

        int[] e1 = positionLibre(random);
        entrepot1 = new Entrepot(1, TypeMinerai.OR, e1[0], e1[1]);
        monde.ajouterEntrepot(entrepot1);

        int[] e2 = positionLibre(random);
        entrepot2 = new Entrepot(2, TypeMinerai.NICKEL, e2[0], e2[1]);
        monde.ajouterEntrepot(entrepot2);

        int[] r1 = positionLibre(random);
        robot1 = new Robot(1, TypeMinerai.OR, r1[0], r1[1],
                nombreAleatoire(random, 5, 9), nombreAleatoire(random, 1, 3));
        monde.ajouterRobot(robot1);

        int[] r2 = positionLibre(random);
        robot2 = new Robot(2, TypeMinerai.NICKEL, r2[0], r2[1],
                nombreAleatoire(random, 5, 9), nombreAleatoire(random, 1, 3));
        monde.ajouterRobot(robot2);
    }

    private int[] positionLibre(Random random) {
        int ligne = random.nextInt(10);
        int colonne = random.nextInt(10);

        while (!monde.caseLibre(ligne, colonne)) {
            ligne = random.nextInt(10);
            colonne = random.nextInt(10);
        }

        return new int[]{ligne, colonne};
    }

    private int nombreAleatoire(Random random, int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static void main(String[] args) {
        new InterfaceSwing();
    }
}