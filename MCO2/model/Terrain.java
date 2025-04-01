package model;

/**
 * Represents a terrain tile on the board that can contain a game piece.
 */
public abstract class Terrain {
    private Piece piece;

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
     * Removes any piece from this terrain tile.
     */
    public void removePiece() {
        this.piece = null;
    }
}