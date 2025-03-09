public abstract class Piece {
    protected String name;
    protected int strength;
    protected int x, y;
    protected Player player;

    public Piece(String name, int strength, int x, int y) {
        this.name = name;
        this.strength = strength;
        this.x = x;
        this.y = y;
    }

    public abstract boolean move(int newX, int newY, Board board);

    protected boolean canCapture(Piece target) {
        return target != null && !isSamePlayer(target) && this.strength >= target.strength;
    }

    protected boolean isSamePlayer(Piece target) {
        return this.player != null && target.player != null && this.player.equals(target.player);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x; this.y = y;
    }

    public String getName() {
        return name;
    }

    public int getStrength() {
        return strength;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}