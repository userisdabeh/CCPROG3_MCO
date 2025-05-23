package view;

import controller.*;
import java.awt.*;
import javax.swing.*;
import model.*;

/**
 * GamePanelView class is responsible for rendering the game board and its components.
 * It initializes the game board, sets up the player icons, and handles user interactions.
 */
public class GamePanelView {
    public Board board = new Board();
    private LakeView lakeView = new LakeView();
    private TrapView trapView = new TrapView();
    private HomeBaseView homeBaseView = new HomeBaseView();
    private JLabel currentPlayerLabel = new JLabel("Current Player: ");
    public JLabel playerName = new JLabel("");
    private JPanel gameP = new JPanel();
    private JPanel boardP = new JPanel();
    public JPanel mainPanel = new JPanel();
    public JButton[][] boardTiles = new JButton[9][7];
    private final JungleKing jungleKing;

    // Animal icons
    ImageIcon tigerGreen, tigerBlue, lionGreen, lionBlue, elephantGreen, elephantBlue;
    ImageIcon catGreen, catBlue, dogGreen, dogBlue, wolfGreen, wolfBlue;
    ImageIcon leopardGreen, leopardBlue, ratGreen, ratBlue, genIcon;

    /**
     * Constructs the GamePanelView and initializes UI components for the guessing of piece.
     * 
     * @param jungleKing The JungleKing instance that manages the game state.
     */
    public GamePanelView(JungleKing jungleKing) {
        this.jungleKing = jungleKing;
        initializeComponents();
    }

    /**
     * Initializes the UI components for the game board and player display.
     */
    private void initializeComponents() {
        mainPanel.setLayout(new BorderLayout());
        gameP.setBackground(Color.decode("#FAF9F6"));
        gameP.setLayout(new BoxLayout(gameP, BoxLayout.Y_AXIS));

        currentPlayerLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        currentPlayerLabel.setForeground(Color.decode("#000000"));
        currentPlayerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        playerName.setFont(new Font("Helvetica", Font.BOLD, 20));
        playerName.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerName.setForeground(Color.decode("#000000"));
        playerName.setText("Player 1: Select starting position");

        gameP.add(Box.createRigidArea(new Dimension(0, 15)));
        gameP.add(currentPlayerLabel);
        gameP.add(Box.createRigidArea(new Dimension(0, 10)));
        gameP.add(playerName);
        gameP.add(Box.createRigidArea(new Dimension(0, 15)));

        initImages();
        createBoardTiles();
        addIconsToPositions();

        mainPanel.add(gameP, BorderLayout.NORTH);
        mainPanel.add(boardP, BorderLayout.CENTER);
    }

    /**
     * Creates the game board tiles and adds them to the board panel as buttons.
     */
    private void createBoardTiles() {
        boardP.setLayout(new GridLayout(9, 7, 2, 2));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                JButton tile = new JButton();
                boardTiles[i][j] = tile;
                tile.setBackground(Color.decode("#FFFFFF"));
                tile.setPreferredSize(new Dimension(50, 50));
                tile.setFocusable(false);
                boardP.add(tile);
            }
        }
    }

    /**
     * Initializes the animal icons used in the game.
     * This method loads the images from the resources and scales them to a uniform size.
     */
    private void initImages() {
        try {
            tigerGreen = createScaledIcon("./img/tiger-green.png");
            tigerBlue = createScaledIcon("./img/tiger-blue.png");
            lionGreen = createScaledIcon("./img/lion-green.png");
            lionBlue = createScaledIcon("./img/lion-blue.png");
            elephantGreen = createScaledIcon("./img/elephant-green.png");
            elephantBlue = createScaledIcon("./img/elephant-blue.png");
            catGreen = createScaledIcon("./img/cat-green.png");
            catBlue = createScaledIcon("./img/cat-blue.png");
            dogGreen = createScaledIcon("./img/dog-green.png");
            dogBlue = createScaledIcon("./img/dog-blue.png");
            wolfGreen = createScaledIcon("./img/wolf-green.png");
            wolfBlue = createScaledIcon("./img/wolf-blue.png");
            leopardGreen = createScaledIcon("./img/leopard-green.png");
            leopardBlue = createScaledIcon("./img/leopard-blue.png");
            ratGreen = createScaledIcon("./img/rat-green.png");
            ratBlue = createScaledIcon("./img/rat-blue.png");
            genIcon = createScaledIcon("./img/shou2025.png");
        } catch (Exception e) {
            e.printStackTrace(); // only if image is not found within files
        }
    }

    /**
     * Creates a scaled icon from the given image path.
     * 
     * @param path The path to the image resource.
     * @return A scaled ImageIcon.
     */
    private ImageIcon createScaledIcon(String path) {
        Image image = new ImageIcon(getClass().getResource(path)).getImage();
        return new ImageIcon(image.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    }

    /**
     * Adds icons to the possible positions on the board for both players.
     */
    private void addIconsToPositions() {
        for (int[] pos : board.getP1Possible()) {
            boardTiles[pos[0]][pos[1]].setIcon(genIcon);
        }
        for (int[] pos : board.getP2Possible()) {
            boardTiles[pos[0]][pos[1]].setIcon(genIcon);
        }
        lakeView.addLakeIcons(board, boardTiles);
        trapView.addTrapIcons(board, boardTiles);
        homeBaseView.addDenIcons(board, boardTiles);
    }

    /**
     * Updates all animal icons on the board by redrawing all elements.
     */
    public void updateAllAnimalIcons() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                boardTiles[i][j].setIcon(null);
            }
        }

        // Redraw terrain
        lakeView.addLakeIcons(board, boardTiles);
        trapView.addTrapIcons(board, boardTiles);
        homeBaseView.addDenIcons(board, boardTiles);

        // Redraw all pieces
        setPlayerIcons(jungleKing.getPlayer1(), 1);
        setPlayerIcons(jungleKing.getPlayer2(), 2);

        // to refresh the display when changes are made
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    /**
     * Sets the player icons on the board based on the player's pieces.
     * 
     * @param player The player whose pieces are to be displayed.
     * @param playerNumber The number of the player (1 or 2).
     */
    private void setPlayerIcons(Player player, int playerNumber) {
        for (Piece piece : player.getPieces()) {
            int x = piece.getX();
            int y = piece.getY();
            boardTiles[x][y].setIcon(getAnimalIcon(piece, playerNumber));
        }
    }

    /**
     * Returns the appropriate icon for the given piece and player number.
     * 
     * @param piece The piece for which the icon is to be retrieved.
     * @param playerNumber The number of the player (1 or 2).
     */
    private ImageIcon getAnimalIcon(Piece piece, int playerNumber) {
        String className = piece.getClass().getSimpleName();
        switch (className) {
            case "Elephant": return playerNumber == 1 ? elephantBlue : elephantGreen;
            case "Lion": return playerNumber == 1 ? lionBlue : lionGreen;
            case "Tiger": return playerNumber == 1 ? tigerBlue : tigerGreen;
            case "Leopard": return playerNumber == 1 ? leopardBlue : leopardGreen;
            case "Wolf": return playerNumber == 1 ? wolfBlue : wolfGreen;
            case "Dog": return playerNumber == 1 ? dogBlue : dogGreen;
            case "Cat": return playerNumber == 1 ? catBlue : catGreen;
            case "Rat": return playerNumber == 1 ? ratBlue : ratGreen;
            default: return genIcon;
        }
    }

    /**
     * Returns the main panel containing the game board and player display.
     * 
     * @return The main JPanel for the game that contains all panels.
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * Sets the controller for the game panel, allowing it to handle button clicks.
     * 
     * @param controller The GamePanelController instance that handles user interactions.
     */
    public void setController(GamePanelController controller) {
        for (JButton[] row : boardTiles) {
            for (JButton button : row) {
                button.addActionListener(controller);
            }
        }
    }

    /**
     * Updates the player display with the current player's name.
     * 
     * @param text The text to display for the current player.
     */
    public void updatePlayerDisplay(String text) {
        playerName.setText(text);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    /**
     * Highlights the valid moves for the given piece on the board.
     * 
     * @param piece The piece for which valid moves are to be highlighted.
     */
    public void highlightValidMoves(Piece piece) {
        clearHighlights();
        for (int[] move : piece.getValidMoves(board)) {
            int x = move[0], y = move[1];
            boardTiles[x][y].setBackground(Color.YELLOW);
        }
    }

    /**
     * Highlights the tile where the piece is currently located.
     *
     */
    public void clearHighlights() {
        for (JButton[] row : boardTiles) {
            for (JButton tile : row) {
                tile.setBackground(Color.WHITE);
            }
        }
    }

    /**
     * Displays a popup message indicating the winner of the game.
     * 
     * @param winnerName The name of the winning player.
     */
    public void showWinner(String winnerName) {
        // Show popup
        Component frame = null;
        JOptionPane.showMessageDialog(
                frame,
                winnerName + " wins!",
                "Game Over",
                JOptionPane.INFORMATION_MESSAGE
        );

        // Disable board interactions
        for (JButton[] row : boardTiles) {
            for (JButton tile : row) {
                tile.setEnabled(false);
            }
        }
    }

    /**
     * Displays an error message indicating an invalid move.
     * 
     * @param message The error message to display.
     */
    public void showErrorMessage(String message) {
        Component frame = null;
        JOptionPane.showMessageDialog(
                frame,
                message,
                "Invalid Move",
                JOptionPane.WARNING_MESSAGE
        );
    }
}