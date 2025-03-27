import java.awt.event.*;
import javax.swing.JButton;

public class GamePanelController implements ActionListener{
    private JButton[][] boardTiles;
    private Board board;
    private JungleKing jungleKing;
    private GamePanelView view1;

    public GamePanelController(GamePanelView view) {
        view1 = view;
        boardTiles = view.boardTiles;
        board = view.board;
        jungleKing = new JungleKing(board);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        int row = -1;
        int col = -1;
        int flag;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                if (boardTiles[i][j] == button) {
                    row = i;
                    col = j;
                    break;
                }
            }
        }

        System.out.println("Button pressed at: " + row + ", " + col);

        jungleKing.handleStartingPosition(row, col);
    }
}
