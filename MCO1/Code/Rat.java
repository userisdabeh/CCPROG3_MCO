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
        // Check if the move is exactly one step in any cardinal direction
        if (Math.abs(newX - x) + Math.abs(newY - y) != 1) {
            return false;
        }

        Piece target = board.getPiece(newX, newY);
        boolean currentInWater = board.isLake(x, y);
        boolean newInWater = board.isLake(newX, newY);

        if (target != null) {
            boolean targetInWater = board.isLake(target.getX(), target.getY());

            // Prevent capturing a piece from the same team
            if (this.player == target.getPlayer()) {
                return false;
            }

            // Special case: The Rat can capture an Elephant only if both are on land
            if (target.getName().equals("Elephant")) {
                if (!currentInWater && !targetInWater) {
                    board.removePiece(target);
                } else {
                    return false;
                }
            }

            // Special case: Rats can capture other Rats only if both are in water
            else if (target.getName().equals("Rat")) {
                if (!(newInWater && targetInWater)) {
                    return false;
                }
                if (this.strength < target.getStrength()) {
                    return false;
                }
                board.removePiece(target);
            }

            // Standard capture rules: The Rat can capture weaker or equal-strength pieces but not in water
            else {
                if (this.strength < target.getStrength()) {
                    return false;
                }
                if (newInWater) {
                    return false;
                }
                board.removePiece(target);
            }
        }

        // Update the Rat's position on the board
        board.updatePiecePosition(this, newX, newY);
        return true;
    }
}
