import java.awt.*;
import javax.swing.*;

public class GamePanelView{

    private JLabel currentPlayerLabel = new JLabel("Current Player: ");
    private JLabel playerName = new JLabel("I work here");
    private JPanel gameP = new JPanel();
    private JPanel boardP = new JPanel();
    public JPanel mainPanel = new JPanel();
    private JPanel[] board = new JPanel[63];

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

        gameP.add(Box.createRigidArea(new Dimension(0, 100)));
        gameP.add(currentPlayerLabel);
        gameP.add(Box.createRigidArea(new Dimension(0, 90)));
        gameP.add(playerName);

        boardP.setLayout(new GridLayout(9, 7));
        for(int i = 0; i < 63; i++) {
            board[i] = new JPanel();
            board[i].setBackground(Color.decode("#B2FBA5"));
            board[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            boardP.add(board[i]);
        }

        mainPanel.add(gameP, BorderLayout.NORTH);
        mainPanel.add(boardP, BorderLayout.CENTER);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
