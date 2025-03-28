public class JungleKingDriver {
    public static void main(String[] args) {
        Board board = new Board();
        JungleKing jungleKing = new JungleKing(board);
        JungleKingView view = new JungleKingView(jungleKing);
        JungleKingController controller = new JungleKingController(view, jungleKing);
        view.setController(controller);
    }
}