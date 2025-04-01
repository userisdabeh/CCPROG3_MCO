/**
 * Represents a trap on the board.
 * Extends the {@link Terrain} class.
 */
public class Trap extends Terrain {
    public static void setTrapArea(Terrain[][] grid, int sRow, int sCol, int eRow, int eCol) {
        for (int i = sRow; i <= eRow; i++) {
            for (int j = sCol; j <= eCol; j++) {
                grid[i][j] = new Trap();
            }
        }
    }
}