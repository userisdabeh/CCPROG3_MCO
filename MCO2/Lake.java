/**
 * Represents a lake on the board.
 */
public class Lake extends Terrain {
    @Override
    public char getSymbol() {
        return '~';
    }

    public static void setLakeArea(Terrain[][] grid, int sRow, int sCol, int eRow, int eCol) {
        for (int i = sRow; i <= eRow; i++) {
            for (int j = sCol; j <= eCol; j++) {
                grid[i][j] = new Lake();
            }
        }
    }
}