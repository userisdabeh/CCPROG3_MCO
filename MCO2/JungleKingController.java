import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller component for the Jungle King game.
 * Handles user interactions and updates the view accordingly.
 * Implements {@link ActionListener} to respond to button clicks.
 */
public class JungleKingController implements ActionListener {
    private final JungleKingView view;
    private final JungleKing jungleKing;

    /**
     * Constructs a controller for the Jungle King game.
     * 
     * @param view The {@link JungleKingView} instance to be controlled for UI operations.
     * @param jungleKing The {@link JungleKing} instance representing the game logic.
     */
    public JungleKingController(JungleKingView view, JungleKing jungleKing) {
        this.view = view;
        this.jungleKing = jungleKing;
    }

    /**
     * Handles action events from UI components.
     * Once the user clicks the "Start Game" button, it triggers the game start process.
     * 
     * @param e The action event triggered by the button click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getLandingButton()) {
            handleStartGame();
        }
    }

    /**
     * Initiates the game start process by calling the view's startGame method.
     */
    private void handleStartGame() {
        view.startGame();
    }
}