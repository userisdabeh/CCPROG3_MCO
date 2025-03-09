import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private ArrayList<Piece> pieces;

    public Player(String name, List<Piece> pieces) {
        this.name = name;
        this.pieces = new ArrayList<>();

        for (Piece piece : pieces) {
            addPiece(piece); // Add pieces under ownership of a player
        }
    }

    public String getName() {
        return name;
    }

    public List<Piece> getPieces() {
        return new ArrayList<>(pieces);
    }

    public Piece getPiece(String pieceName) {
        for (Piece piece : pieces) {
            if (piece.getName().equalsIgnoreCase(pieceName)) {
                return piece;
            }
        }
        return null;
    }

    public void addPiece(Piece piece) {
        if (piece != null) {
            piece.setPlayer(this); // Sets ownership
            pieces.add(piece); // Adds to list
        }
    }

    public void removePiece(Piece piece) {
        pieces.remove(piece);
    }
}