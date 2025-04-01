import java.util.ArrayList;

/**
 * Represents an abstract game piece in the Jungle King game. Key characteristics:
 * <ul>
 *   <li>Maintains position (x,y) on a 9x7 game board</li>
 *   <li>Has strength value governing capture mechanics</li>
 *   <li>Belongs to a {@link Player} through ownership association</li>
 *   <li>Supports trap mechanics modifying effective strength</li>
 *   <li>Provides base movement/capture logic for concrete pieces to extend</li>
 * </ul>
 * 
 * <p>Concrete implementations must define specific movement patterns through
 * {@link #getValidMoves(Board)} and may override {@link #move(int, int, Board)}.</p>
 */
public abstract class Piece {
    protected String name;
    protected int strength;
    protected int x, y;
    protected Player player;
    protected int trapStrength = 0; // Default strength of animal when on trap
    protected int defaultStrength;

    /**
     * Constructs a game piece with initial position and strength values
     * 
     * @param name      Display name of the piece
     * @param strength  Base capture strength (unmodified by terrain)
     * @param x         Initial row position (0-8)
     * @param y         Initial column position (0-6)
     */
    public Piece(String name, int strength, int x, int y) {
        this.name = name;
        defaultStrength = strength;
        this.strength = defaultStrength;
        this.x = x;
        this.y = y;
    }

    /**
     * Generates all valid movement coordinates for this piece
     * 
     * @param board Current game board state
     * @return List of valid [x,y] coordinates this piece can move to
     */
    public abstract ArrayList<int[]> getValidMoves(Board board);

    /**
     * Validates board coordinates are within game boundaries
     * 
     * @param x     Row coordinate to check
     * @param y     Column coordinate to check
     * @param board Game board reference
     * @return true if (x,y) is within 0-8 rows and 0-6 columns
     */
    protected boolean isValidPosition(int x, int y, Board board) {
        return x >= 0 && x < 9 && y >= 0 && y < 7;
    }

    /**
     * Base movement implementation. Subclasses should override for specialized behavior
     * 
     * @param newX  Target row coordinate
     * @param newY  Target column coordinate
     * @param board Current game board
     * @return true if position update succeeded, false otherwise
     */
    public boolean move(int newX, int newY, Board board) {
        if (isValidPosition(newX, newY, board)) {
            this.x = newX;
            this.y = newY;
            return true;
        }
        return false;
    }

    /**
     * Handles standard orthogonal movement validation:
     * <ol>
     *   <li>Single-square movement check</li>
     *   <li>Board boundary validation</li>
     *   <li>Own base restriction</li>
     *   <li>Capture validation via {@link #canCapture(Piece, Board)}</li>
     * </ol>
     * 
     * @param newX  Target row coordinate
     * @param newY  Target column coordinate
     * @param board Current game board
     * @return true if basic move is valid and executed
     */
    protected boolean basicMove(int newX, int newY, Board board) {
        // Validate 1-step movement
        if (Math.abs(newX - x) + Math.abs(newY - y) != 1) {
            return false;
        }

        // Check board boundaries
        if (!board.isValidPosition(newX, newY)) {
            return false;
        }

        // Prevent moving to own base
        if (board.isOwnBase(newX, newY, player)) {
            return false;
        }

        // Handle captures
        Piece target = board.getPiece(newX, newY);
        if (target != null && !canCapture(target, board)) {
            return false;
        }

        // Update position
        if (target != null) {
            board.removePiece(newX, newY);
        }

        board.updatePiecePosition(this, newX, newY);
        return true;
    }

    /**
     * Manages lake jumping mechanics for predators (Lion/Tiger)
     * 
     * @param dx     X-direction movement delta
     * @param dy     Y-direction movement delta
     * @param board  Current game board
     * @return true if jump was successfully executed
     * @see #isLakeEdge(int, int)
     */
    protected boolean handleLakeJump(int dx, int dy, Board board) {
        int directionX = Integer.signum(dx);
        int directionY = Integer.signum(dy);
        int targetX = x + (4 * directionX);
        int targetY = y + (3 * directionY);
        int numberOfSteps = 0;

        if (directionY == 0) {
            numberOfSteps = 3;
        } else
            numberOfSteps = 2;


        // Validate target position
        if (!board.isValidPosition(targetX, targetY) || board.isLake(targetX, targetY)) {
            return false;
        }

        // Check intermediate tiles
        int checkX = x + directionX;
        int checkY = y + directionY;
        for (int i = 0; i < numberOfSteps; i++) {
            if (!board.isLake(checkX, checkY) || (board.getPiece(checkX, checkY) != null && board.getPiece(checkX, checkY).getName().equals("Rat"))) {
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
     * Identifies if position is at a lake jump point. Valid edges:
     * <ul>
     *   <li>North edges: Row 2, Columns 1-2 and 4-5</li>
     *   <li>South edges: Row 6, Columns 1-2 and 4-5</li>
     *   <li>West edges: Columns 0/3, Rows 3-5</li>
     *   <li>East edges: Columns 3/6, Rows 3-5</li>
     * </ul>
     * 
     * @param x  Row position to check
     * @param y  Column position to check
     * @return true if position is a valid lake jump edge
     */
    public boolean isLakeEdge(int x, int y) {
        boolean northEdge = (x == 2) && ((y >= 1 && y <= 2) || (y >= 4 && y <= 5)); // North edges (row 2) of both lakes
        boolean southEdge = (x == 6) && ((y >= 1 && y <= 2) || (y >= 4 && y <= 5)); // South edges (row 6) of both lakes
        boolean westEdge = (y == 0 || y == 3) && (x >= 3 && x <= 5); // West edges (columns 0 and 3)
        boolean eastEdge = (y == 3 || y == 6) && (x >= 3 && x <= 5); // East edges (columns 3 and 6)

        return northEdge || southEdge || westEdge || eastEdge;
    }

    /**
     * Generates orthogonal movement candidates (N/S/E/W)
     * 
     * @param board Current game board
     * @return List of adjacent [x,y] coordinates
     */
    protected ArrayList<int[]> generateValidMoves(Board board) {
        ArrayList<int[]> moves = new ArrayList<>();
        int[][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}};

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];
            if (isValidPosition(newX, newY, board)) {
                moves.add(new int[]{newX, newY}); // add to list of possible moves
            }
        }
        return moves;
    }

    /**
     * Determines capture eligibility considering:
     * <ul>
     *   <li>Opponent ownership</li>
     *   <li>Current strength comparison</li>
     *   <li>Trap status modifications</li>
     * </ul>
     * 
     * @param target  Piece to potentially capture
     * @param board   Game board reference
     * @return true if capture is permitted by game rules
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
