public class Player {
    private String name;
    private Piece lion;
    private Piece rat;

    public Player(String name, Piece lion, Piece rat) {
        this.name = name;
        this.lion = lion;
        this.rat = rat;
        this.lion.setPlayer(this); // Set the player for the Lion
        this.rat.setPlayer(this);  // Set the player for the Rat
    }

    public String getName() {
        return name;
    }

    public Piece getLion() {
        return lion;
    }

    public Piece getRat() {
        return rat;
    }

    public void removePiece(Piece piece) {
        if (piece == lion) {
            lion = null; // Remove the Lion
        } else if (piece == rat) {
            rat = null; // Remove the Rat
        }
    }
}