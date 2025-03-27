import java.awt.*;
import javax.swing.*;

public class GamePanelView{
    public Board board = new Board();
    private LakeView lakeView = new LakeView();
    private TrapView trapView = new TrapView();
    private HomeBaseView homeBaseView = new HomeBaseView();
    private Piece piece = new Piece();

    private JLabel currentPlayerLabel = new JLabel("Current Player: ");
    public JLabel playerName = new JLabel("Player 1");
    private JPanel gameP = new JPanel();
    private JPanel boardP = new JPanel();
    public JPanel mainPanel = new JPanel();
    public JButton[][] boardTiles = new JButton[9][7];

    private final JungleKing jungleKing;

    ImageIcon tigerGreen;
    ImageIcon tigerBlue;
    ImageIcon lionGreen;
    ImageIcon lionBlue;
    ImageIcon elephantGreen;
    ImageIcon elephantBlue;
    ImageIcon catGreen;
    ImageIcon catBlue;
    ImageIcon dogGreen;
    ImageIcon dogBlue;
    ImageIcon wolfGreen;
    ImageIcon wolfBlue;
    ImageIcon leopardGreen;
    ImageIcon leopardBlue;
    ImageIcon ratGreen;
    ImageIcon ratBlue;
    ImageIcon trap;
    ImageIcon denBlue;
    ImageIcon denGreen;
    ImageIcon lake;
    ImageIcon genIcon;

    public GamePanelView (JungleKing jungleKing) {
        this.jungleKing = jungleKing;

        mainPanel.setLayout(new BorderLayout());

        gameP.setBackground(Color.decode("#B2FBA5"));
        gameP.setLayout(new BoxLayout(gameP, BoxLayout.Y_AXIS));

        currentPlayerLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        currentPlayerLabel.setForeground(Color.decode("#000000"));
        currentPlayerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        playerName.setFont(new Font("Helvetica", Font.BOLD, 20));
        playerName.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerName.setForeground(Color.decode("#000000"));

        gameP.add(Box.createRigidArea(new Dimension(0, 15)));
        gameP.add(currentPlayerLabel);
        gameP.add(Box.createRigidArea(new Dimension(0, 10)));
        gameP.add(playerName);
        gameP.add(Box.createRigidArea(new Dimension(0, 15)));

        initImages();

        boardP.setLayout(new GridLayout(9, 7, 2,2));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                JButton tile = new JButton();
                boardTiles[i][j] = tile;
                tile.setBackground(Color.decode("#FFFFFF"));
                tile.setPreferredSize(new Dimension(50, 50));
                boardTiles[i][j].setPreferredSize(new Dimension(50, 50));
                boardP.add(tile);
                tile.setFocusable(false);
            }
        }

        board.setupPossiblePositions();
        board.setupTerrain();
        addIconsToPositions(1);


        mainPanel.add(gameP, BorderLayout.NORTH);
        mainPanel.add(boardP, BorderLayout.CENTER);
    }

    private void initImages() {
        Image tigerGreenImg = new ImageIcon(getClass().getResource("./img/tiger-green.png")).getImage();
        tigerGreen = new ImageIcon(tigerGreenImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));

        Image tigerBlueImg = new ImageIcon(getClass().getResource("./img/tiger-blue.png")).getImage();
        tigerBlue = new ImageIcon(tigerBlueImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));

        Image lionGreenImg = new ImageIcon(getClass().getResource("./img/lion-green.png")).getImage();
        lionGreen = new ImageIcon(lionGreenImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));

        Image lionBlueImg = new ImageIcon(getClass().getResource("./img/lion-blue.png")).getImage();
        lionBlue = new ImageIcon(lionBlueImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));

        Image elephantGreenImg = new ImageIcon(getClass().getResource("./img/elephant-green.png")).getImage();
        elephantGreen = new ImageIcon(elephantGreenImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));

        Image elephantBlueImg = new ImageIcon(getClass().getResource("./img/elephant-blue.png")).getImage();
        elephantBlue = new ImageIcon(elephantBlueImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));

        Image catGreenImg = new ImageIcon(getClass().getResource("./img/cat-green.png")).getImage();
        catGreen = new ImageIcon(catGreenImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));

        Image catBlueImg = new ImageIcon(getClass().getResource("./img/cat-blue.png")).getImage();
        catBlue = new ImageIcon(catBlueImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));

        Image dogGreenImg = new ImageIcon(getClass().getResource("./img/dog-green.png")).getImage();
        dogGreen = new ImageIcon(dogGreenImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));

        Image dogBlueImg = new ImageIcon(getClass().getResource("./img/dog-blue.png")).getImage();
        dogBlue = new ImageIcon(dogBlueImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));

        Image wolfGreenImg = new ImageIcon(getClass().getResource("./img/wolf-green.png")).getImage();
        wolfGreen = new ImageIcon(wolfGreenImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));

        Image wolfBlueImg = new ImageIcon(getClass().getResource("./img/wolf-blue.png")).getImage();
        wolfBlue = new ImageIcon(wolfBlueImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));

        Image leopardGreenImg = new ImageIcon(getClass().getResource("./img/leopard-green.png")).getImage();
        leopardGreen = new ImageIcon(leopardGreenImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));

        Image leopardBlueImg = new ImageIcon(getClass().getResource("./img/leopard-blue.png")).getImage();
        leopardBlue = new ImageIcon(leopardBlueImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));

        Image ratGreenImg = new ImageIcon(getClass().getResource("./img/rat-green.png")).getImage();
        ratGreen = new ImageIcon(ratGreenImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));

        Image ratBlueImg = new ImageIcon(getClass().getResource("./img/rat-blue.png")).getImage(); 
        ratBlue = new ImageIcon(ratBlueImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));

        Image genIconImg = new ImageIcon(getClass().getResource("./img/shou2025.png")).getImage();
        genIcon = new ImageIcon(genIconImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));
    }

    private void addIconsToPositions(int mode) {
        if (mode == 1) {
            for (int[] pos : board.getP1Possible()) {
                boardTiles[pos[0]][pos[1]].setIcon(genIcon);
            }
    
            for (int[] pos : board.getP2Possible()) {
                boardTiles[pos[0]][pos[1]].setIcon(genIcon);
            }
        } else {
            for (int[] pos : board.getP1Possible()) {
                boardTiles[pos[0]][pos[1]].setIcon(null);
            }
    
            for (int[] pos : board.getP2Possible()) {
                boardTiles[pos[0]][pos[1]].setIcon(null);
            }
        }
        
        lakeView.addLakeIcons(board, boardTiles);
        trapView.addTrapIcons(board, boardTiles);
        homeBaseView.addDenIcons(board, boardTiles);
    }
    
    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setController(GamePanelController controller) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                boardTiles[i][j].addActionListener(controller);
            }
        }
    }

    public void updatePlayerDisplay(String text) {
        playerName.setText(text);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void setCurrentPlayer(String playerName) {
        this.playerName.setText(playerName);
    }

    public void startActualGame() {
        playerName.setText(jungleKing.getCurrentPlayerName());
        addIconsToPositions(2);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}
