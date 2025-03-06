import java.util.Scanner;

public class JungleKing {
    private Board board;
    private Player player1, player2;
    private Player currentPlayer;

    public JungleKing() {
        board = new Board();
        player1 = new Player("Player 1", new Lion(8, 6), new Rat(6, 6)); // Player 1's Lion and Rat
        player2 = new Player("Player 2", new Lion(0, 0), new Rat(2, 0)); // Player 2's Lion and Rat
        currentPlayer = player1;

        // Place the pieces on the board
        board.updatePiecePosition(player1.getPiece("Lion"), 8, 6);
        board.updatePiecePosition(player1.getPiece("Rat"), 6, 6);
        board.updatePiecePosition(player2.getPiece("Lion"), 0, 0);
        board.updatePiecePosition(player2.getPiece("Rat"), 2, 0);
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        boolean gameOver = false;

        while (!gameOver) {
            board.displayBoard();
            System.out.print(currentPlayer.getName() + "'s turn. Choose a piece to move (L for Lion, R for Rat): ");
            String pieceChoice = scanner.nextLine().toUpperCase();

            Piece piece = null;
            boolean validPieceChoice = false;

            if (pieceChoice.equals("L")) {
                piece = currentPlayer.getPiece("Lion");
                validPieceChoice = true;
            } else if (pieceChoice.equals("R")) {
                piece = currentPlayer.getPiece("Rat");
                validPieceChoice = true;
            } else {
                System.out.println();
                System.out.println("[!] Invalid choice. Please enter L (Lion) or R (Rat).");
            }

            if (validPieceChoice) {
                // Check if the piece is null (already removed from the board)
                if (piece == null) {
                    System.out.println();
                    System.out.println("[!] This piece is already removed from the board.");
                } else {
                    System.out.print("Enter move (W/A/S/D): ");
                    String move = scanner.nextLine().toUpperCase();

                    int newX = -1, newY = -1;
                    boolean validMoveInput = false;

                    switch (move) {
                        case "W":
                            newX = piece.getX() - 1;
                            newY = piece.getY();
                            validMoveInput = true;
                            break;
                        case "A":
                            newX = piece.getX();
                            newY = piece.getY() - 1;
                            validMoveInput = true;
                            break;
                        case "S":
                            newX = piece.getX() + 1;
                            newY = piece.getY();
                            validMoveInput = true;
                            break;
                        case "D":
                            newX = piece.getX();
                            newY = piece.getY() + 1;
                            validMoveInput = true;
                            break;
                        default:
                            System.out.println();
                            System.out.println("[!] Invalid input. Use W/A/S/D.");
                    }

                    if (validMoveInput) {
                        // Check if the move is out of bounds
                        if (newX < 0 || newX >= 9 || newY < 0 || newY >= 7) {
                            System.out.println();
                            System.out.println("[!] Invalid move. You cannot move out of bounds.");
                        } else {
                            // Check if the move is to the player's own base
                            char terrain = board.getTerrain(newX, newY);
                            if ((currentPlayer == player1 && terrain == '1') || (currentPlayer == player2 && terrain == '2')) {
                                System.out.println();
                                System.out.println("[!] You cannot capture your own base.");
                            } else {
                                if (piece.move(newX, newY, board)) {
                                    // Check if the move results in a win
                                    if (checkWin(newX, newY)) {
                                        System.out.println();
                                        System.out.println(currentPlayer.getName() + " wins!");
                                        gameOver = true;
                                    } else {
                                        switchTurn();
                                    }
                                } else {
                                    System.out.println();
                                    System.out.println("[!] Invalid move. Try again.");
                                }
                            }
                        }
                    }
                }
            }
        }
        scanner.close();
    }

    // New method to check if the current player has won
    private boolean checkWin(int newX, int newY) {
        char terrain = board.getTerrain(newX, newY);
        return (currentPlayer == player1 && terrain == '2') || (currentPlayer == player2 && terrain == '1');
    }

    private void switchTurn() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }
}