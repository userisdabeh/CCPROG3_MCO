import java.awt.event.*;
import javax.swing.*;

public class GamePanelController implements ActionListener {
    private final JungleKing jungleKing;
    private final GamePanelView view;

    public GamePanelController(GamePanelView view, JungleKing jungleKing) {
        this.view = view;
        this.jungleKing = jungleKing;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        int row = -1;
        int col = -1;

        // Find clicked button coordinates
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                if (view.boardTiles[i][j] == button) {
                    row = i;
                    col = j;
                    break;
                }
            }
        }

        if (row != -1 && col != -1) {
            boolean selectionComplete = jungleKing.handleStartingPosition(row, col, view.playerName);
            if (selectionComplete) {
                view.startActualGame();
            }
        }
    }
}