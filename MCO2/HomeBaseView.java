import java.awt.*;
import javax.swing.*;

public class HomeBaseView {
    ImageIcon denBlue;
    ImageIcon denGreen;

    public HomeBaseView() {
        Image denBlueImg = new ImageIcon(getClass().getResource("./img/den-blue.png")).getImage();
        denBlue = new ImageIcon(denBlueImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));

        Image denGreenImg = new ImageIcon(getClass().getResource("./img/den-green.png")).getImage();
        denGreen = new ImageIcon(denGreenImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));
    }

    public void addDenIcons(Board board, JButton[][] boardTiles) {
        boardTiles[0][3].setIcon(denBlue);
        boardTiles[8][3].setIcon(denGreen);
    }
}
