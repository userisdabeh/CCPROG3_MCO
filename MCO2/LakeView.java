import java.awt.*;
import javax.swing.*;

public class LakeView {

    ImageIcon lake;

    public LakeView() {
        Image lakeImg = new ImageIcon(getClass().getResource("./img/lake.png")).getImage();
        lake = new ImageIcon(lakeImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));
    }
    
    public void addLakeIcons(Board board, JButton[][] boardTiles) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                if(board.isLake(i, j)) {
                    boardTiles[i][j].setIcon(lake);
                }
            }
        }
    }
}
