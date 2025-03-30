public class HomeBase extends Terrain {
    // Base coordinates (fixed per Jungle game rules)
    public static final int[] P1_BASE = {8, 3};
    public static final int[] P2_BASE = {0, 3};

    private final Player owner;

    public HomeBase(Player owner) {
        this.owner = owner;
    }

    // Get base coordinates for any player
    public static int[] getBasePosition(Player player) {
        return player.getName().equals("Player 1") ? P1_BASE : P2_BASE;
    }

    public Player getOwner() {
        return owner;
    }
}