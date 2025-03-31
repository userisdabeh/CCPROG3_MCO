import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GamePanelController implements ActionListener {
    private final JungleKing jungleKing;
    private final GamePanelView view;
    private Piece selectedPiece;

    public GamePanelController(GamePanelView view, JungleKing jungleKing) {
        this.view = view;
        this.jungleKing = jungleKing;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        int[] coords = findButtonCoordinates(button);

        if (coords != null) {
            handleBoardInteraction(coords[0], coords[1]);
        }
    }

    private void handleBoardInteraction(int row, int col) {
        if (jungleKing.getSelectingPlayer() != 0) {
            // Handle initial position selection
            boolean selectionComplete = jungleKing.handleStartingPosition(row, col);
            if (selectionComplete) {
                SwingUtilities.invokeLater(() -> {
                    view.updatePlayerDisplay(jungleKing.getCurrentPlayerName() + " goes first!");
                    view.updateAllAnimalIcons();
                });
            }
        } else {
            // Handle gameplay moves
            handleGameMove(row, col);
        }
    }

    private void handleGameMove(int row, int col) {
        Player currentPlayer = jungleKing.getCurrentPlayer();
        if (selectedPiece == null) {
            // Select piece
            selectedPiece = getPieceAt(row, col, currentPlayer);
            if (selectedPiece != null) {
                view.highlightValidMoves(selectedPiece);
            }
        } else {
            // Move piece
            boolean moveSuccess = selectedPiece.move(row, col, jungleKing.getBoard());
            if (moveSuccess) {
                if (jungleKing.isWinningMove(row, col, currentPlayer)) {
                    view.showWinner(currentPlayer.getName());
                    jungleKing.setGameOver(true);
                    return;
                }

                if (jungleKing.checkOpponentDefeated(currentPlayer)) {
                    view.showWinner(currentPlayer.getName());
                    jungleKing.setGameOver(true);
                    return;
                }

                jungleKing.switchTurn();
                view.updatePlayerDisplay(jungleKing.getCurrentPlayerName() + "'s turn");
                view.updateAllAnimalIcons();
            }
            selectedPiece = null;
            view.clearHighlights();
        }
    }

    private Piece getPieceAt(int row, int col, Player player) {
        for (Piece piece : player.getPieces()) {
            if (piece.getX() == row && piece.getY() == col) {
                return piece;
            }
        }
        return null;
    }

    private int[] findButtonCoordinates(JButton button) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                if (view.boardTiles[i][j] == button) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
}

