package view;

import java.awt.*;
import javax.swing.*;

import model.*;

/**
 * Handles visual representation of traps on the game board.
 * Coordinates with the {@link Board} class to determine trap positions.
 * Coordinates with the {@link JButton} array to set trap icons on the board tiles.
 */
public class TrapView {
    ImageIcon trap;

    /**
     * Constructs a TrapView object and initializes the trap icon.
     * Loads the trap image from resources and scales it to fit the tile size.
     */
    public TrapView() {
        Image trapImg = new ImageIcon(getClass().getResource("./img/trap.png")).getImage();
        trap = new ImageIcon(trapImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));
    }

    /**
     * Adds trap icons to the board tiles based on the board's trap positions.
     * Iterates through the board's grid and sets the icon for tiles that are trap.
     * 
     * @param board The game board object that contains trap information.
     * @param boardTiles 2D array of JButtons representing the game board tiles.
     */
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
