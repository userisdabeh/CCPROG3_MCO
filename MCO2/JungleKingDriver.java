/**
 * Main entry point for the JungleKing Application.
 * Initializes the game board, view, and controller.
 * <ul>
 *  <li>{@link Board} and {@link JungleKing} as the model layer</li>
 *  <li>{@link JungleKingView} as the view layer</li>
 *  <li>{@link JungleKingController} as the controller layer</li>
 * </ul>
 */
public class JungleKingDriver {
    
    /**
     * Main method to launch the JungleKing game.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        Board board = new Board();
        JungleKing jungleKing = new JungleKing(board);
        JungleKingView view = new JungleKingView(jungleKing);
        JungleKingController controller = new JungleKingController(view, jungleKing);
        view.setController(controller);
    }
}