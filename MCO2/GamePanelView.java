import java.awt.*;
import javax.swing.*;

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
    ImageIcon leopardGreen, leopardBlue, ratGreen, ratBlue, trap, denBlue, denGreen, lake, genIcon;

    public GamePanelView(JungleKing jungleKing) {
        this.jungleKing = jungleKing;
        initializeComponents();
    }

    private void initializeComponents() {
        mainPanel.setLayout(new BorderLayout());
        gameP.setBackground(Color.decode("#B2FBA5"));
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
            e.printStackTrace();
        }
    }

    private ImageIcon createScaledIcon(String path) {
        Image image = new ImageIcon(getClass().getResource(path)).getImage();
        return new ImageIcon(image.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    }

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

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void clearGenIcons() {
        for (int[] pos : board.getP1Possible()) boardTiles[pos[0]][pos[1]].setIcon(null);
        for (int[] pos : board.getP2Possible()) boardTiles[pos[0]][pos[1]].setIcon(null);
    }

    private void setPlayerIcons(Player player, int playerNumber) {
        for (Piece piece : player.getPieces()) {
            int x = piece.getX();
            int y = piece.getY();
            boardTiles[x][y].setIcon(getAnimalIcon(piece, playerNumber));
        }
    }

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

    public JPanel getMainPanel() { return mainPanel; }

    public void setController(GamePanelController controller) {
        for (JButton[] row : boardTiles) {
            for (JButton button : row) {
                button.addActionListener(controller);
            }
        }
    }

    public void updatePlayerDisplay(String text) {
        playerName.setText(text);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void highlightValidMoves(Piece piece) {
        clearHighlights();
        for (int[] move : piece.getValidMoves(board)) {
            int x = move[0], y = move[1];
            boardTiles[x][y].setBackground(Color.YELLOW);
        }
    }

    public void clearHighlights() {
        for (JButton[] row : boardTiles) {
            for (JButton tile : row) {
                tile.setBackground(Color.WHITE);
            }
        }
    }

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
}