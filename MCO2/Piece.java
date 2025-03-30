import java.util.ArrayList;

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

    public ArrayList<int[]> getValidMoves(Board board) {
        ArrayList<int[]> moves = new ArrayList<>();
        // Add orthogonal moves
        int[][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}};

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];

            if (isValidPosition(newX, newY, board)) {
                moves.add(new int[]{newX, newY});
            }
        }
        return moves;
    };

    protected boolean isValidPosition(int x, int y, Board board) {
        return x >= 0 && x < 9 && y >= 0 && y < 7;
    }

    /**
     * Abstract method to be implemented by subclasses defining how a piece moves.
     * 
     * @param newX The target row position.
     * @param newY The target column position.
     * @param board The game board where the piece moves.
     * @return True if the move is valid and executed, false otherwise.
     */
    public boolean move(int newX, int newY, Board board) {
        System.out.println("newX: " + newX + ", newY: " + newY);

        if (!isValidPosition(newX, newY, board)) {
            return false;
        }

        Piece targetPiece = board.getPiece(newX, newY); // Get the piece at the target position
        if (targetPiece != null && targetPiece.getPlayer() == this.player) {
            return false; // Prevent moving onto a tile occupied by the same player's piece
        }

        this.x = newX;
        this.y = newY;
        return true;
    }

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
