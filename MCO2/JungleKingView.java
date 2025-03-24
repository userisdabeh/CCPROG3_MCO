import java.awt.*;
import javax.swing.*;

public class JungleKingView{
    private JFrame frame = new JFrame("Jungle King Game");
    private JPanel menuPanel = new JPanel();
    private JLabel landingLabel = new JLabel("Welcome to Jungle King!");
    private JButton landingButton = new JButton("Start Game");
    private Font font = new Font("Helvetica", Font.BOLD, 30);

    public JungleKingView(JungleKingController controller) {
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        menuPanel.setBackground(Color.decode("#B2FBA5"));
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        landingLabel.setFont(font);
        landingLabel.setForeground(Color.decode("#FF5733"));
        landingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        landingButton.setFont(font);
        landingButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        landingButton.setFocusPainted(false);
        landingButton.addActionListener(controller);
        
        menuPanel.add(Box.createRigidArea(new Dimension(0, 150)));
        menuPanel.add(landingLabel);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 90)));
        menuPanel.add(landingButton);

        frame.add(menuPanel);
        frame.setVisible(true);
    }

    public void startGame() {
        JungleKing game = new JungleKing();
        game.startGame();
    }

    public void setController(JungleKingController controller) {
        landingButton.addActionListener(controller);
    }
}