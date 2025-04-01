package model;

/**
 * Represents a trap on the board.
 * Extends the {@link Terrain} class.
 */
public class Trap extends Terrain {
    private Player owner; // Player who owns this trap (null if neutral)

    public Trap(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public static void setTrapArea(Terrain[][] grid, int sRow, int sCol, int eRow, int eCol, Player owner) {
        for (int i = sRow; i <= eRow; i++) {
            for (int j = sCol; j <= eCol; j++) {
                grid[i][j] = new Trap(owner);
            }
        }
    }
}