public abstract class Piece {
    protected String name;
    protected int strength;
    protected int x, y;
    protected Player player; // Reference to the player who owns the piece

    public Piece(String name, int strength, int x, int y) {
        this.name = name;
        this.strength = strength;
        this.x = x;
        this.y = y;
    }

    public abstract boolean move(int newX, int newY, Board board);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public int getStrength() {
        return this.strength;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }
}