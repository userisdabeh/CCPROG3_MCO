import java.util.ArrayList;

/**
 * Represents the Tiger piece in the game.
 * The Tiger has a strength of 6 and is a subclass of Piece.
 * 
 * Currently, the Tiger class is not implemented and always returns false.
 */
public class Tiger extends Piece {

    /**
     * Constructor for the Tiger class at the specified position.
     * @param x The row position of the tiger on the board.
     * @param y The column position of the tiger on the board,
     */
    public Tiger(int x, int y) {
        super("Tiger", 6, x, y);
    }

    @Override
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
    }

    /**
     * Attempts to move the Lion to the new position.
     * The Lion can jump over the lake if no rats are in the way.
     * The Lion can only move orthogonally.
     */
    @Override
    public boolean move(int newX, int newY, Board board) {
        int dx = newX - x;
        int dy = newY - y;

        if (!board.isValidPosition(newX, newY)) {
            return false;
        }

        // Only allow straight line movement
        if (dx != 0 && dy != 0) {
            return false;
        }

        // Check if attempting lake jump
        if (isLakeEdge(x, y) && isJumpingOverLake(dx, dy, board)) {
            return lakeJump(dx, dy, board);
        }

        // Regular move validation
        return normalMove(newX, newY, board);
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

    /**
     * Checks if the Lion is attempting to jump over the Lake.
     * A Lake Jump is only valid if there are no Rats in the way.
     *
     * @param dx The change in x position.
     * @param dy The change in y position.
     * @param board The game board.
     * @return true if the Lion can jump over the lake, false otherwise.
     */
    public boolean isJumpingOverLake(int dx, int dy, Board board) {
        // Determine jump direction and distance
        int directionX = Integer.signum(dx);
        int directionY = Integer.signum(dy);

        int targetX = x + (4 * directionX);
        int targetY = y + (3 * directionY);

        // Validate target position
        if (!board.isValidPosition(targetX, targetY) || board.isLake(targetX, targetY)) {
            return false;
        }

        // Check intermediate tiles (lake and no Rats)
        int checkX = x + directionX;
        int checkY = y + directionY;
        for (int i = 0; i < 2; i++) {
            if (!board.isLake(checkX, checkY)) {
                return false;
            }
            Piece blocker = board.getPiece(checkX, checkY);
            if (blocker != null && blocker.getName().equals("Rat")) {
                return false;
            }
            checkX += directionX;
            checkY += directionY;
        }

        // Check target square
        Piece target = board.getPiece(targetX, targetY);
        return target == null || canCapture(target);
    }

    /**
     * Handles the Lion's lake jump movement. Moving it to the other side of the lake.
     *
     * @param dx The change in x position.
     * @param dy The change in y position.
     * @param board The game board.
     * @return true if the Lion successfully jumps over the lake, false otherwise.
     */
    public boolean lakeJump(int dx, int dy, Board board) {
        int targetX = x + (4 * Integer.signum(dx)); // 4 steps for vertical jump
        int targetY = y + (3 * Integer.signum(dy)); // 3 steps for horizontal jump

        // Remove piece at target position if exists
        if (board.getPiece(targetX, targetY) != null) {
            board.removePiece(targetX, targetY);  // Use coordinate-based removal
        }

        board.updatePiecePosition(this, targetX, targetY);
        return true;
    }

    /**
     * Handles the Lion's normal movement.
     * If the lion is attempting to jump over the lake but there is a rat in the way, the move is invalid.
     *
     * @param newX The new row position of the Lion.
     * @param newY The new column position of the Lion.
     * @param board The game board.
     * @return true if the Lion successfully moves to the new position, false otherwise.
     */
    public boolean normalMove(int newX, int newY, Board board) {
        if(board.isLake(newX, newY)) {
            return false;
        }

        Piece target = board.getPiece(newX, newY);
        if (target != null && !canCapture(target)) {
            return false;
        }

        if (target != null) {
            board.removePiece(newX, newY);
        }
        board.updatePiecePosition(this, newX, newY);
        return true;
    }
}
