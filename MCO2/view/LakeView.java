package view;

import java.awt.*;
import javax.swing.*;

import model.*;

/**
 * Handles visual representation of lakes on the game board.
 * Coordinates with the {@link Board} class to determine lake positions.
 * Coordinates with the {@link JButton} array to set lake icons on the board tiles.
 */
public class LakeView {

    ImageIcon lake;

    /**
     * Constructs a LakeView object and initializes the lake icon.
     * Loads the lake image from resources and scales it to fit the tile size.
     */
    public LakeView() {
        Image lakeImg = new ImageIcon(getClass().getResource("./img/lake.png")).getImage();
        lake = new ImageIcon(lakeImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));
    }
    
    /**
     * Adds lake icons to the board tiles based on the board's lake positions.
     * Iterates through the board's grid and sets the icon for tiles that are lakes.
     * 
     * @param board The game board object that contains lake information.
     * @param boardTiles 2D array of JButtons representing the game board tiles.
     */
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
