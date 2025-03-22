/**
 * Represents a home base on the board.
 */
public class HomeBase extends Terrain {
    private final char baseSymbol;

    public HomeBase(char symbol) {
        this.baseSymbol = symbol;
    }

    @Override
    public char getSymbol() {
        return baseSymbol;
    }

    public static void setHomeBases(Terrain[][] grid) {
        grid[8][3] = new HomeBase('1');  // Player 1's base
        grid[0][3] = new HomeBase('2');  // Player 2's base
    }
}