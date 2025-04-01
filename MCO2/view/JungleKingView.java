package view;

import java.awt.*;
import javax.swing.*;

import model.*;
import controller.*;


/**
 * Main view class for the Jungle King game.
 * This class sets up the initial menu screen and handles the transition to the game panel.
 * Connects to the {@link JungleKingController} to handle button actions.
 */
public class JungleKingView {
    private JFrame frame = new JFrame("Jungle King Game");
    private JPanel menuPanel = new JPanel();
    private JLabel landingLabel = new JLabel("Welcome to Jungle King!");
    private JButton landingButton = new JButton("Start Game");
    private Font font = new Font("Helvetica", Font.BOLD, 27);
    private final JungleKing jungleKing;

    /**
     * Contructs the game view and initialized UI components.
     * 
     * @param jungleKing The JungleKing instance that manages the game logic.
     */
    public JungleKingView(JungleKing jungleKing) {
        this.jungleKing = jungleKing;
        initializeFrame();
    }

    /**
     * Initializes the main frame and sets up the menu panel with components.
     * 
     */
    private void initializeFrame() {
        frame.setSize(500, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        menuPanel.setBackground(Color.decode("#FAF9F6"));
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

    /**
     * Transition from the first player determination screen to the game panel.
     * Creates and displays game panel components through the {@link GamePanelView} class.
     * Initializes the game panel controller to handle game logic and user interactions.
     */
    public void startGame() {
        frame.getContentPane().removeAll();
        GamePanelView gamePanel = new GamePanelView(jungleKing);
        GamePanelController gamePanelController = new GamePanelController(gamePanel, jungleKing);
        gamePanel.setController(gamePanelController);
        frame.setContentPane(gamePanel.getMainPanel());
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Returns the landing button used to start the game.
     * 
     * @return The landing button used to start the game.
     */
    public JButton getLandingButton() {
        return landingButton;
    }
    
    /**
     * Sets the controller used to handle button actions.
     * 
     * @param controller The controller instance that handles button actions.
     */
    public void setController(JungleKingController controller) {
        landingButton.addActionListener(controller);
    }
}