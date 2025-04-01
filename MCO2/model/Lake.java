package model;
/**
 * Represents a lake terrain on the JungleKing Game.
 * Extends the {@link Terrain} class.
 */
public class Lake extends Terrain {

    /**
     * Constructs a Lake object with a specific terrain type.
     * @param grid The 2D terrain grid to modify {@code Terrain[][]}
     * @param sRow The starting row index of the lake area.
     * @param sCol The starting column index of the lake area.
     * @param eRow The ending row index of the lake area.
     * @param eCol The ending column index of the lake area.
     */
    public static void setLakeArea(Terrain[][] grid, int sRow, int sCol, int eRow, int eCol) {
        for (int i = sRow; i <= eRow; i++) {
            for (int j = sCol; j <= eCol; j++) {
                grid[i][j] = new Lake();
            }
        }
    }
}