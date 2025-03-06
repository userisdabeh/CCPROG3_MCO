import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Piece> pieces;

    public Player(String name, Piece lion, Piece rat) {
        this.name = name;
        this.pieces = new ArrayList<Piece>();

        setPiece(lion); // Set the player for the Lion
        setPiece(rat);  // Set the player for the Rat
    }

    public String getName() {
        return name;
    }

//    public ArrayList<Piece> getPieces() {
//        return pieces;
//    }

    public Piece getPiece(String pieceName) {
        for (Piece piece : pieces) {
            if (piece.getName().equalsIgnoreCase(pieceName)) {
                return piece;
            }
        }
        return null;
    }

    public void setPiece(Piece piece) {
        if (piece != null) {
            piece.setPlayer(this);
            pieces.add(piece);
        }
    }

    public void removePiece(Piece piece) {
        pieces.remove(piece); // Remove the piece from the list of a player
    }
}