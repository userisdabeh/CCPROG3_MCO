package view;

import java.awt.*;
import javax.swing.*;

import model.*;

/**
 * HomeBaseView class is responsible for displaying the home base icons on the game board.
 * It initializes the blue and green den icons and adds them to the specified board tiles.
 * <p>
 * This class is part of the game and is used to represent the home base of the players.
 * Working with {@link Board} and {@link JButton} to place the icons on the board.
 * </p>
 */
public class HomeBaseView {
    ImageIcon denBlue;
    ImageIcon denGreen;

    /**
     * Constructs a HomeBaseView object and initializes the blue and green den icons.
     * The icons are scaled to a size of 50x50 pixels for display on the game board.
     */
    public HomeBaseView() {
        Image denBlueImg = new ImageIcon(getClass().getResource("./img/den-blue.png")).getImage();
        denBlue = new ImageIcon(denBlueImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));

        Image denGreenImg = new ImageIcon(getClass().getResource("./img/den-green.png")).getImage();
        denGreen = new ImageIcon(denGreenImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));
    }

    /**
     * Adds the den icons to the specified board tiles.
     * Uses fixed coordinates to place the blue den at (0, 3) and the green den at (8, 3).
     * 
     * @param board The game board where the icons will be added.
     * @param boardTiles The 2D array of buttons representing the tiles on the board.
     */
    public void addDenIcons(Board board, JButton[][] boardTiles) {
        boardTiles[0][3].setIcon(denBlue);
        boardTiles[8][3].setIcon(denGreen);
    }
}
