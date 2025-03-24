import java.awt.*;
import javax.swing.*;

public class JungleKingView{
    private JFrame frame = new JFrame("Jungle King Game");
    private JPanel panel = new JPanel();
    private JLabel landingLabel = new JLabel("Welcome to Jungle King!");
    private JButton landingButton = new JButton("Start Game");
    private Font font = new Font("Helvetica", Font.BOLD, 30);

    public JungleKingView() {
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        panel.setBackground(Color.decode("#B2FBA5"));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        landingLabel.setFont(font);
        landingLabel.setForeground(Color.decode("#FF5733"));
        landingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        landingButton.setFont(font);
        landingButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        landingButton.setFocusPainted(false);
        
        panel.add(Box.createRigidArea(new Dimension(0, 150)));
        panel.add(landingLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 90)));
        panel.add(landingButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}
