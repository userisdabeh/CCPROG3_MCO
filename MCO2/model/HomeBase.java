package model;

/**
 * Represents a player's home base terrain in the JungleKing game.
 * Each player's base has fixed coordinates.
 * Extends the {@link Terrain} class.
 */
public class HomeBase extends Terrain {
    // Base coordinates (fixed per Jungle game rules)
    public static final int[] P1_BASE = {8, 3};
    public static final int[] P2_BASE = {0, 3};

    private final Player owner;

    /**
     * Contructs a HomeBase Terrain object owned by a player.
     * 
     * @param owner the {@link Player} who owns this base
     */
    public HomeBase(Player owner) {
        this.owner = owner;
    }

    /**
     * Returns the coordinates of the base for a given player. 
     * Uses the player's name to determine the base.
     * 
     * @param player the {@link Player} whose base coordinates are needed
     * @return an int array containing the x and y coordinates of the base in the format {x, y}
     */
    public static int[] getBasePosition(Player player) {
        return player.getName().equals("Player 1") ? P1_BASE : P2_BASE;
    }

    /**
     * Returns the owner of this base.
     * 
     * @return the {@link Player} who owns this base
     */
    public Player getOwner() {
        return owner;
    }
}