
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

    /**
     * Attempts to move the Tiger to the new position.
     * 
     * @param newX The new row position of the Tiger.
     * @param newY The new column position of the Tiger.
     * @param board The game board.
     * @return false since the Tiger cannot yet move.
     */
    @Override
    public boolean move(int newX, int newY, Board board) {
        return false; // Cannot move
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
