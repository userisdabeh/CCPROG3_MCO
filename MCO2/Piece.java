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
    protected int trapStrength = 0; // Default strength of animal when on trap
    protected int defaultStrength;

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
        defaultStrength = strength;
        this.strength = defaultStrength;
        this.x = x;
        this.y = y;
    }

    public abstract ArrayList<int[]> getValidMoves(Board board);

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
        if (isValidPosition(newX, newY, board)) {
            this.x = newX;
            this.y = newY;
            return true;
        }
        return false;
    }

    protected boolean basicMove(int newX, int newY, Board board) {
        // Validate 1-step movement
        if (Math.abs(newX - x) + Math.abs(newY - y) != 1) return false;

        // Check board boundaries
        if (!board.isValidPosition(newX, newY)) return false;

        // Prevent moving to own base
        if (board.isOwnBase(newX, newY, player)) return false;

        // Handle captures
        Piece target = board.getPiece(newX, newY);
        if (target != null && !canCapture(target, board)) return false;

        // Update position
        if (target != null) board.removePiece(newX, newY);

        board.updatePiecePosition(this, newX, newY);
        return true;
    }

    protected boolean handleLakeJump(int dx, int dy, Board board) {
        if (!isLakeEdge(x, y)) return false;

        int directionX = Integer.signum(dx);
        int directionY = Integer.signum(dy);
        int targetX = x + (4 * directionX);
        int targetY = y + (3 * directionY);

        // Validate target position
        if (!board.isValidPosition(targetX, targetY) || board.isLake(targetX, targetY)) {
            return false;
        }

        // Check intermediate tiles
        int checkX = x + directionX;
        int checkY = y + directionY;
        for (int i = 0; i < 2; i++) {
            if (!board.isLake(checkX, checkY) ||
                    (board.getPiece(checkX, checkY) != null &&
                            board.getPiece(checkX, checkY).getName().equals("Rat"))) {
                return false;
            }
            checkX += directionX;
            checkY += directionY;
        }

        // Check target piece
        Piece target = board.getPiece(targetX, targetY);
        if (target != null && !canCapture(target, board)) {
            return false;
        }

        // Execute the jump
        if (target != null) board.removePiece(targetX, targetY);
        board.updatePiecePosition(this, targetX, targetY);
        return true;
    }

    /**
     * Checks if the Lion is at the edge of a lake, enabling a possible lake jump.
     *
     * @param x The row position of the Lion.
     * @param y The column position of the Lion.
     * @return true if the Lion is at the edge of a lake, false otherwise.
     */
    public boolean isLakeEdge(int x, int y) {
        boolean northEdge = (x == 2) && ((y >= 1 && y <= 2) || (y >= 4 && y <= 5)); // North edges (row 2) of both lakes
        boolean southEdge = (x == 6) && ((y >= 1 && y <= 2) || (y >= 4 && y <= 5)); // South edges (row 6) of both lakes
        boolean westEdge = (y == 0 || y == 3) && (x >= 3 && x <= 5); // West edges (columns 0 and 3)
        boolean eastEdge = (y == 3 || y == 6) && (x >= 3 && x <= 5); // East edges (columns 3 and 6)

        return northEdge || southEdge || westEdge || eastEdge;
    }

    protected ArrayList<int[]> generateValidMoves(Board board) {
        ArrayList<int[]> moves = new ArrayList<>();
        int[][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}};

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];
            if (isValidPosition(newX, newY, board)) {
                moves.add(new int[]{newX, newY});
            }
        }
        return moves;
    }

    /**
     * Determines if this piece can capture another piece.
     * A piece can capture another if it is not on the same team and has equal or greater strength.
     * 
     * @param target The piece to capture.
     * @return True if this piece can capture the target, false otherwise.
     */
    protected boolean canCapture(Piece target, Board board) {
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

    public int getDefaultStrength() {
        return defaultStrength;
    }

    /**
     * Gets the player who owns this piece.
     * 
     * @return The player who controls the piece.
     */
    public Player getPlayer() {
        return this.player;
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
