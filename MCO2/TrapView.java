import java.awt.*;
import javax.swing.*;

public class TrapView {
    ImageIcon trap;

    public TrapView() {
        Image trapImg = new ImageIcon(getClass().getResource("./img/trap.png")).getImage();
        trap = new ImageIcon(trapImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));
    }

    public void addTrapIcons (Board board, JButton[][] boardTiles) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                if (board.isTrap(i, j)) {
                    boardTiles[i][j].setIcon(trap);
                }
            }
        }
    }
}
