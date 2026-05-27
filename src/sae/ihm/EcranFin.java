
package sae.ihm;

import sae.model.Entrepot;
import sae.model.TypeMinerai;
import javax.swing.*;
import java.awt.*;

public class EcranFin extends JFrame {

    public EcranFin(Entrepot e1,
                    Entrepot e2) {

        setTitle("Fin");

        setSize(700, 500);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        panel.setLayout(
                new BoxLayout(panel,
                        BoxLayout.Y_AXIS)
        );

        JLabel titre =
                new JLabel("FIN DE PARTIE");

        titre.setFont(
                new Font("Arial",
                        Font.BOLD,
                        40)
        );

        titre.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel score1 =
                new JLabel(
                        "OR : "
                                + e1.getStock()
                );

        JLabel score2 =
                new JLabel(
                        "NICKEL : "
                                + e2.getStock()
                );

        score1.setFont(
                new Font("Arial",
                        Font.PLAIN,
                        30)
        );

        score2.setFont(
                new Font("Arial",
                        Font.PLAIN,
                        30)
        );

        score1.setAlignmentX(Component.CENTER_ALIGNMENT);

        score2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton quitter =
                new JButton("Quitter");

        quitter.setFont(
                new Font("Arial",
                        Font.BOLD,
                        25)
        );

        quitter.setAlignmentX(Component.CENTER_ALIGNMENT);

        quitter.addActionListener(e -> System.exit(0));

        panel.add(Box.createVerticalGlue());

        panel.add(titre);

        panel.add(Box.createVerticalStrut(60));

        panel.add(score1);

        panel.add(Box.createVerticalStrut(20));

        panel.add(score2);

        panel.add(Box.createVerticalStrut(50));

        panel.add(quitter);

        panel.add(Box.createVerticalGlue());

        add(panel);

        setVisible(true);
    }
    public static void main(String[] args) {
        Entrepot e1 = new Entrepot(1, TypeMinerai.OR, 0, 0);
        Entrepot e2 = new Entrepot(2, TypeMinerai.NICKEL, 0, 0);

        e1.ajouterMinerais(50);
        e2.ajouterMinerais(30);

        new EcranFin(e1, e2);
    }
}
