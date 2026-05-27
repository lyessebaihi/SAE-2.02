package sae.ihm;


import javax.swing.*;
import java.awt.*;



public class EcranAccueil {

        public static void main(String[] args) {

            JFrame application = new JFrame();

            application.setTitle("Simulation ROBOT");
            application.setPreferredSize(new Dimension(900, 600));
            application.setLocationRelativeTo(null);

            JLabel titre1 = new JLabel("Simulation de robots mineurs");

            Font police1 = new Font("Arial", Font.BOLD, 40);
            titre1.setFont(police1);

            JPanel panel1 = new JPanel();

            panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));

            JButton button1 = new JButton("Commencer");
            button1.setFont(new Font("Arial", Font.BOLD, 30));

            button1.addActionListener(e -> {

                application.dispose();

                new InterfaceSwing();
            });

            titre1.setAlignmentX(Component.CENTER_ALIGNMENT);
            button1.setAlignmentX(Component.CENTER_ALIGNMENT);

            panel1.add(Box.createVerticalGlue());
            panel1.add(titre1);
            panel1.add(Box.createVerticalStrut(100));
            panel1.add(button1);
            panel1.add(Box.createVerticalGlue());

            application.add(panel1);

            application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            application.pack();

            application.setVisible(true);
        }
    }

