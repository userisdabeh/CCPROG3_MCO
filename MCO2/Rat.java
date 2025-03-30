import java.util.ArrayList;

/**
 * Represents the Rat piece in the Jungle King game.
 * The Rat has a strength of 1 and is the only piece that can enter water.
 * It can also capture the Elephant under specific conditions.
 */
public class Rat extends Piece {

    /**
     * Constructs a Rat piece at the specified position.
     * 
     * @param x The row position of the Rat on the board.
     * @param y The column position of the Rat on the board.
     */
    public Rat(int x, int y) {
        super("Rat", 1, x, y);
    }

    @Override
    public ArrayList<int[]> getValidMoves(Board board) {
        return generateValidMoves(board);
    }

    /**
     * Attempts to move the Rat to a new position.
     * The Rat moves one step in any cardinal direction (up, down, left, or right).
     * It can enter water and has special capture rules.
     *
     * @param newX The target row position.
     * @param newY The target column position.
     * @param board The game board.
     * @return True if the move is valid and executed, false otherwise.
     */
    @Override
    public boolean move(int newX, int newY, Board board) {
        // Custom water handling
        if (board.isLake(newX, newY)) {
            return handleWaterMove(newX, newY, board);
        }
        return basicMove(newX, newY, board);
    }

    private boolean handleWaterMove(int newX, int newY, Board board) {
        // Validate water-specific movement
        if (!board.isLake(newX, newY)) return false;
        if (Math.abs(newX - x) + Math.abs(newY - y) != 1) return false;

        Piece target = board.getPiece(newX, newY);
        boolean currentInWater = board.isLake(x, y);

        // Handle captures in water
        if (target != null) {
            if (target.getPlayer() == player) return false;

            // Special rat vs rat in water rules
            if (target instanceof Rat) {
                if (!board.isLake(target.getX(), target.getY())) return false;
                if (strength < target.getStrength()) return false;
            }
            // Can't capture non-rats in water
            else {
                return false;
            }

            board.removePiece(newX, newY);
        }

        // Prevent moving from land to water if occupied
        if (!currentInWater && target != null) return false;

        board.updatePiecePosition(this, newX, newY);
        return true;
    }

    @Override
    protected boolean canCapture(Piece target, Board board) {
        // Special case: Rat can capture Elephant on land
        if (target instanceof Elephant &&
                !board.isLake(x, y) &&
                !board.isLake(target.getX(), target.getY())) {
            return true;
        }
        return super.canCapture(target, board);
    }
}
