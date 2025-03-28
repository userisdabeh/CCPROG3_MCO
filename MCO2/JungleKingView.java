import javax.swing.*;
import java.awt.*;

public class JungleKingView {
    private JFrame frame = new JFrame("Jungle King Game");
    private JPanel menuPanel = new JPanel();
    private JLabel landingLabel = new JLabel("Welcome to Jungle King!");
    private JButton landingButton = new JButton("Start Game");
    private Font font = new Font("Helvetica", Font.BOLD, 30);
    private final JungleKing jungleKing;

    public JungleKingView(JungleKing jungleKing) {
        this.jungleKing = jungleKing;
        initializeFrame();
    }

    private void initializeFrame() {
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        menuPanel.setBackground(Color.decode("#B2FBA5"));
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        landingLabel.setFont(font);
        landingLabel.setForeground(Color.decode("#000000"));
        landingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        landingButton.setFont(font);
        landingButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        landingButton.setFocusPainted(false);

        menuPanel.add(Box.createRigidArea(new Dimension(0, 150)));
        menuPanel.add(landingLabel);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 90)));
        menuPanel.add(landingButton);

        frame.add(menuPanel);
        frame.setVisible(true);
    }

    public void startGame() {
        frame.getContentPane().removeAll();
        GamePanelView gamePanel = new GamePanelView(jungleKing);
        GamePanelController gamePanelController = new GamePanelController(gamePanel, jungleKing);
        gamePanel.setController(gamePanelController);
        frame.setContentPane(gamePanel.getMainPanel());
        frame.revalidate();
        frame.repaint();
    }

    public JButton getLandingButton() { return landingButton; }
    public void setController(JungleKingController controller) {
        landingButton.addActionListener(controller);
    }
}