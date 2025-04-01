package model;

/**
 * Represents a trap on the board.
 * Extends the {@link Terrain} class.
 */
public class Trap extends Terrain {
    private Player owner; // Player who owns this trap (null if neutral)

    /**
     * Constructor for Trap.
     * Initializes the trap with the specified owner.
     *
     * @param owner The player who owns this trap.
     */
    public Trap(Player owner) {
        this.owner = owner;
    }

    /**
     * Returns the owner of the trap.
     *
     * @return The owner of the trap.
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Sets the location of the trap on the board.
     * 
     * @param grid The grid representing the board.
     * @param sRow The starting row of the trap area.
     * @param sCol The starting column of the trap area.
     * @param eRow The ending row of the trap area.
     * @param eCol The ending column of the trap area.
     * @param owner The player who owns this trap.
     */
    public static void setTrapArea(Terrain[][] grid, int sRow, int sCol, int eRow, int eCol, Player owner) {
        for (int i = sRow; i <= eRow; i++) {
            for (int j = sCol; j <= eCol; j++) {
                grid[i][j] = new Trap(owner);
            }
        }
    }
}