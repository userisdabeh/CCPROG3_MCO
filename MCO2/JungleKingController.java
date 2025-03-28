import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JungleKingController implements ActionListener {
    private final JungleKingView view;
    private final JungleKing jungleKing;

    public JungleKingController(JungleKingView view, JungleKing jungleKing) {
        this.view = view;
        this.jungleKing = jungleKing;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getLandingButton()) {
            handleStartGame();
        }
    }

    private void handleStartGame() {
        view.startGame();
    }
}