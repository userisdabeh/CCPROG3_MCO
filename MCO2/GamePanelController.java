import java.awt.event.*;
import javax.swing.JButton;

public class GamePanelController implements ActionListener{
    private JButton[][] boardTiles;
    private Board board;

    public GamePanelController(JButton[][] boardTiles, Board board) {
        this.boardTiles = boardTiles;
        this.board = board;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        int row = -1;
        int col = -1;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                if (boardTiles[i][j] == button) {
                    row = i;
                    col = j;
                    break;
                }
            }
        }

        if(boardTiles[row][col].getIcon() == null) {
            boardTiles[row][col].setIcon(board.getIcon(row, col));
        } else {
            boardTiles[row][col].setIcon(null);
        }
    }
}
