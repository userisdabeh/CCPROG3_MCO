/**
 * The entry point of the JungleKing game.
 * Initializes the game and starts the game loop.
 */
public class JungleKingDriver {

    /**
     * The main method that starts the JungleKing game.
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        JungleKingView view = new JungleKingView(null);
        JungleKingController controller = new JungleKingController(view);
        view.setController(controller);
    }
}