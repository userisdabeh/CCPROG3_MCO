package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import model.*;
import view.*;

/**
 * Controller for the GamePanelView in the Jungle King game.
 * Handles user interactions with the game board,
 * including selecting pieces and moving them.
 */
public class GamePanelController implements ActionListener {
    private final JungleKing jungleKing;
    private final GamePanelView view;
    private Piece selectedPiece;

    /**
     * Constructor for GamePanelController.
     * Initializes the controller with the view and game instance.
     * 
     * @param view The GamePanelView instance
     * @param jungleKing The JungleKing game instance
     */
    public GamePanelController(GamePanelView view, JungleKing jungleKing) {
        this.view = view;
        this.jungleKing = jungleKing;
    }

    /**
     * Handles button click events on the game board.
     * 
     * @param e The ActionEvent triggered by the button click
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        int[] coords = findButtonCoordinates(button);

        if (coords != null) {
            handleBoardInteraction(coords[0], coords[1]);
        }
    }

    /**
     * Handles all interactions with the game board.
     * 
     * @param row The row (x-coordinate) of the clicked button
     * @param col The column (y-coordinate) of the clicked button
     */
    private void handleBoardInteraction(int row, int col) {
        if (jungleKing.getSelectingPlayer() != 0) {
            // Handle initial position selection
            boolean selectionComplete = jungleKing.handleStartingPosition(row, col);

            if (jungleKing.selectingPlayer == 2) {
                view.playerName.setText("Player 2: Select starting position");
            }

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

    /**
     * Handles the movement of pieces on the game board.
     * 
     * @param row The row (x-coordinate) to move the piece to
     * @param col The column (y-coordinate) to move the piece to
     */
    private void handleGameMove(int row, int col) {
        Player currentPlayer = jungleKing.getCurrentPlayer();
        if (selectedPiece == null) {
            // Select piece
            selectedPiece = getPieceAt(row, col, currentPlayer);
            if (selectedPiece != null) {
                view.highlightValidMoves(selectedPiece);
            }
        } else {
            // Validate move first
            if (!validateMove(selectedPiece, row, col)) {
                return; // Don't proceed if validation fails
            }

            // Move piece
            boolean moveSuccess = selectedPiece.move(row, col, jungleKing.getBoard());
            if (moveSuccess) {
                if (jungleKing.isWinningMove(row, col, currentPlayer)) {
                    view.showWinner(currentPlayer.getName());
                    jungleKing.setGameOver(true); // end game
                    return;
                }

                if (jungleKing.checkOpponentDefeated(currentPlayer)) {
                    view.showWinner(currentPlayer.getName());
                    jungleKing.setGameOver(true); // end game
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

    /**
     * Retrieves the piece at the specified coordinates for the given player.
     * 
     * @param row The row (x-coordinate) of the piece
     * @param col The column (y-coordinate) of the piece
     * @param player The player whose pieces are being checked
     * @return The Piece at the specified coordinates, or null if not found
     */
    private Piece getPieceAt(int row, int col, Player player) {
        for (Piece piece : player.getPieces()) {
            if (piece.getX() == row && piece.getY() == col) {
                return piece;
            }
        }
        return null;
    }

    /**
     * Finds the coordinates of the button in the game board.
     * 
     * @param button The button clicked
     * @return An array containing the row and column of the button, or null if not found
     */
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

    /**
     * Validates move attempts against game rules. Checks:
     * <ul>
     *  <li>Own base check</li>
     *  <li>Lake entry check</li>
     * </ul>
     */
    private boolean validateMove(Piece piece, int newX, int newY) {
        Board board = jungleKing.getBoard();

        // Own base check
        if (board.isOwnBase(newX, newY, piece.getPlayer())) {
            view.showErrorMessage("You cannot move into your own base!");
            return false;
        }

        // Piece-specific validation
        if (board.isLake(newX, newY)) {
            if (!(piece instanceof Lion) && !(piece instanceof Tiger) && !(piece instanceof Rat)) {
                view.showErrorMessage("This piece cannot enter the lake!");
                return false;
            }
        }

        return true;
    }
}

