import java.awt.*;
import javax.swing.*;

public class GamePanelView{

    private JLabel currentPlayerLabel = new JLabel("Current Player: ");
    private JLabel playerName = new JLabel("Player 1");
    private JPanel gameP = new JPanel();
    private JPanel boardP = new JPanel();
    public JPanel mainPanel = new JPanel();
    private JButton[] board = new JButton[63];

    ImageIcon tigerGreen = new ImageIcon(getClass().getResource("./img/tiger-green.png"));

    public GamePanelView (){
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

        boardP.setLayout(new GridLayout(9, 7));
        for(int i = 0; i < 63; i++) {
            JButton tile = new JButton();
            board[i] = tile;
            boardP.add(tile);
            tile.setIcon(tigerGreen);
        }

        mainPanel.add(gameP, BorderLayout.NORTH);
        mainPanel.add(boardP, BorderLayout.CENTER);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
