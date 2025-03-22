/**
 * Represents a terrain tile on the board that can contain a game piece.
 */
public abstract class Terrain {
    private Piece piece;

    /**
     * Gets the symbol representing this terrain type.
     */
    public abstract char getSymbol();

    /**
     * Gets the piece located on this terrain tile.
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Places a piece on this terrain tile.
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * Checks if this terrain tile contains a piece.
     */
    public boolean hasPiece() {
        return piece != null;
    }

    /**
     * Removes any piece from this terrain tile.
     */
    public void removePiece() {
        this.piece = null;
    }
}