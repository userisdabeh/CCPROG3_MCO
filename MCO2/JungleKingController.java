import java.awt.event.*;

public class JungleKingController implements ActionListener {
    private JungleKingView view;
    private JungleKing jungleKing;

    public JungleKingController(JungleKingView view, JungleKing jungleKing) {
        this.view = view;
        this.jungleKing = jungleKing;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        view.startGame();
    }
}
