
/**
 * Represents the Cat piece in the game.
 * The Cat has a strength of 2 and is a subclass of Piece.
 * 
 * Currently, the Cat class is not implemented and always returns false.
 */
public class Cat extends Piece {

    /**
     * Constructor for the Cat class at the specified position.
     * @param x The row position of the cat on the board.
     * @param y The column position of the cat on the board,
     */
    public Cat(int x, int y) {
        super("Cat", 2, x, y);
    }

    /**
     * Attempts to move the Cat to the new position.
     * 
     * @param newX The new row position of the Cat.
     * @param newY The new column position of the Cat.
     * @param board The game board.
     * @return false since the Cat cannot yet move.
     */
    @Override
    public boolean move(int newX, int newY, Board board) {
        int dx = newX - x;
        int dy = newY - y;

        System.out.println("newX: " + newX + ", newY: " + newY);
        System.out.println("x: " + x + ", y: " + y);
        System.out.println("dx: " + dx + ", dy: " + dy);

        if (!board.isValidPosition(newX, newY)) {
            return false;
        }

        if (dx == 0 && dy == 0) {
            return false; // Prevent moving to the same position
        }

        if (dx < -1 || dx > 1 || dy < -1 || dy > 1) {
            return false; // Prevent moving more than one square in any direction
        }

        // Only allow straight line movement
        if (dx != 0 && dy != 0) {
            System.out.println("Gotcha");
            return false;
        }

        Piece targetPiece = board.getPiece(newX, newY); // Get the piece at the target position
        
        String tempString = (targetPiece != null ? targetPiece.getName() : "None");

        System.out.println("Cat move to: " + newX + ", " + newY);
        System.out.println("Target piece: " + tempString);

        if (targetPiece != null) {
            System.out.println("Target piece is not null");

            if(targetPiece.getPlayer() == this.player) {
                System.out.println("Target piece is the same player");
            } else {
                System.out.println("Target piece is not the same player");
            }
            return false; // Prevent moving onto a tile occupied by the same player's piece
        }
        
        System.out.println("Target piece is null");

        board.updatePiecePosition(this, newX, newY);
        return true;
    }
}