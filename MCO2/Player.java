import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the JungleKing game.
 * A player has a name and a list of pieces.
 */
public class Player {
    private String name;
    private ArrayList<Piece> pieces;

    /**
     * Constructor for the Player class.
     * @param name The name of the player.
     * @param pieces The list of pieces owned by the player.
     */
    public Player(String name, List<Piece> pieces) {
        this.name = name;
        this.pieces = new ArrayList<>();

        for (Piece piece : pieces) {
            addPiece(piece); // Add pieces under ownership of a player
        }
    }

    /**
     * Returns the name of the player.
     *
     * @return The player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the list of pieces owned by the player.
     *
     * @return A new list of pieces owned by the player.
     */
    public List<Piece> getPieces() {
        return new ArrayList<>(pieces);
    }

    /**
     * Returns the piece with the specified name.
     *
     * @param pieceName The name of the piece to retrieve.
     * @return The piece with the specified name, or null if not found.
     */
    public Piece getPiece(String pieceName) {
        for (Piece piece : pieces) {
            if (piece.getName().equalsIgnoreCase(pieceName)) {
                return piece;
            }
        }
        return null;
    }

    /**
     * Adds a piece to the player's list of pieces.
     *
     * @param piece The piece to add.
     */
    public void addPiece(Piece piece) {
        if (piece != null) {
            piece.setPlayer(this); // Sets ownership
            pieces.add(piece); // Adds to list
        }
    }

    /**
     * Removes a piece from the player's list of pieces.
     *
     * @param piece The piece to remove.
     */
    public void removePiece(Piece piece) {
        pieces.remove(piece);
    }
}