import java.awt.event.*;

public class JungleKingController implements ActionListener {
    private JungleKingView view;

    public JungleKingController(JungleKingView view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        view.startGame();
    }
    
}
