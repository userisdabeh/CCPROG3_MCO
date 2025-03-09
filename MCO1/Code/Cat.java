public class Cat extends Piece {
    public Cat(int x, int y) {
        super("Cat", 2, x, y);
    }
    @Override
    public boolean move(int newX, int newY, Board board) {
        return false;
    }
}