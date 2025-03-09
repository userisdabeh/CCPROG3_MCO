/**
 * Represents an abstract game piece in the Jungle King game.
 * Each piece has a name, strength, position, and an associated player.
 * Specific game pieces extend this class and implement movement logic.
 */
public abstract class Piece {
    protected String name;
    protected int strength;
    protected int x, y;
    protected Player player;

    /**
     * Constructs a Piece with a given name, strength, and initial position.
     * 
     * @param name The name of the piece.
     * @param strength The strength level of the piece (used for capturing logic).
     * @param x The initial row position of the piece on the board.
     * @param y The initial column position of the piece on the board.
     */
    public Piece(String name, int strength, int x, int y) {
        this.name = name;
        this.strength = strength;
        this.x = x;
        this.y = y;
    }

    /**
     * Abstract method to be implemented by subclasses defining how a piece moves.
     * 
     * @param newX The target row position.
     * @param newY The target column position.
     * @param board The game board where the piece moves.
     * @return True if the move is valid and executed, false otherwise.
     */
    public abstract boolean move(int newX, int newY, Board board);

    /**
     * Determines if this piece can capture another piece.
     * A piece can capture another if it is not on the same team and has equal or greater strength.
     * 
     * @param target The piece to capture.
     * @return True if this piece can capture the target, false otherwise.
     */
    protected boolean canCapture(Piece target) {
        return target != null && !isSamePlayer(target) && this.strength >= target.strength;
    }

    /**
     * Checks if another piece belongs to the same player.
     * 
     * @param target The piece to compare.
     * @return True if both pieces belong to the same player, false otherwise.
     */
    protected boolean isSamePlayer(Piece target) {
        return this.player != null && target.player != null && this.player.equals(target.player);
    }

    /**
     * Gets the row position of the piece.
     * 
     * @return The x-coordinate (row) of the piece on the board.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the column position of the piece.
     * 
     * @return The y-coordinate (column) of the piece on the board.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the position of the piece on the board.
     * 
     * @param x The new row position.
     * @param y The new column position.
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the name of the piece.
     * 
     * @return The name of the piece.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the strength level of the piece.
     * 
     * @return The strength value of the piece.
     */
    public int getStrength() {
        return strength;
    }

    /**
     * Gets the player who owns this piece.
     * 
     * @return The player who controls the piece.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the owner of this piece.
     * 
     * @param player The player to assign as the owner of this piece.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}
