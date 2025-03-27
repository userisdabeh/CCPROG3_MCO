import java.util.*;
import javax.swing.JLabel;

/**
 * JungleKing class is the main class that runs the Jungle King game.
 * It initializes the game, sets up the board1, and starts the game loop.
 */
public class JungleKing {
    private Board board1;
    public Player player1;
    public Player player2;
    private Player currentPlayer;
    private boolean gameOver;

    private Scanner scanner;

    // First player selection state
    private int selectingPlayer = 1; // 1 for Player 1, 2 for Player 2
    private int[] player1Selection;
    private int[] player2Selection;

    /**
     * Constructor for the JungleKing class.
     * Initializes the game, sets up the board1, and starts the game loop.
     */
    public JungleKing(Board board) {
        scanner = new Scanner(System.in);
        board1 = board;  // Now initializes possible positions internally
        createPlayers();
        //showInitialPositions();
        //determineFirstPlayer();
        //setupboard1();
    }

    /**
     * Creates the players for the game.
     */
    public void createPlayers() {
        player1 = new Player("Player 1", createPieces(board1.getP1Possible()));
        player2 = new Player("Player 2", createPieces(board1.getP2Possible()));
    }

    public String getCurrentPlayerName() {
        return currentPlayer != null ? currentPlayer.getName() : "";
    }

    /**
     * Creates the pieces for the players.
     * Each piece is assigned a random position from the list of possible positions.
     *
     * @param positions The list of possible positions for the pieces.
     * @return An ArrayList of pieces with random positions.
     */
    public ArrayList<Piece> createPieces(ArrayList<int[]> positions) {
        ArrayList<Piece> pieces = new ArrayList<>();

        // Create pieces in fixed order but assign to random positions
        pieces.add(new Elephant(positions.get(0)[0], positions.get(0)[1]));
        pieces.add(new Lion(positions.get(1)[0], positions.get(1)[1]));
        pieces.add(new Tiger(positions.get(2)[0], positions.get(2)[1]));
        pieces.add(new Leopard(positions.get(3)[0], positions.get(3)[1]));
        pieces.add(new Wolf(positions.get(4)[0], positions.get(4)[1]));
        pieces.add(new Dog(positions.get(5)[0], positions.get(5)[1]));
        pieces.add(new Cat(positions.get(6)[0], positions.get(6)[1]));
        pieces.add(new Rat(positions.get(7)[0], positions.get(7)[1]));

        return pieces;
    }

    public boolean handleStartingPosition(int row, int col, JLabel playerName) {
        if (selectingPlayer == 1) {
            if (isValidSelection(row, col, board1.getP1Possible())) {
                player1Selection = new int[]{row, col};
                selectingPlayer = 2;
                //System.out.println("[!] Player 2: Select your starting position!");
                playerName.setText("Player 2");
                return false;
            } else {
                //System.out.println("[!] Invalid position for Player 1!");
                return false;
            }
        } else if (selectingPlayer == 2) {
            if (isValidSelection(row, col, board1.getP2Possible())) {
                player2Selection = new int[]{row, col};
                selectingPlayer = 0;
                determineFirstPlayer();
                return true;
            } else {
                //System.out.println("[!] Invalid position for Player 2!");
                return false;
            }
        }
        return false;
    }

    private boolean isValidSelection(int row, int col, ArrayList<int[]> positions) {
        for (int[] pos : positions) {
            if (pos[0] == row && pos[1] == col) return true;
        }
        return false;
    }

    private void determineFirstPlayer() {
        int p1Strength = getPieceStrength(player1, player1Selection);
        int p2Strength = getPieceStrength(player2, player2Selection);

        currentPlayer = (p1Strength >= p2Strength) ? player1 : player2;
        System.out.println("[!] " + currentPlayer.getName() + " goes first!");
    }

    private int getPieceStrength(Player player, int[] position) {
        for (Piece piece : player.getPieces()) {
            if (piece.getX() == position[0] && piece.getY() == position[1]) {
                return piece.getStrength();
            }
        }
        return 0;
    }

    /**
     * Displays the initial positions of the pieces on the board1.
     */
    public void showInitialPositions() {
        board1.showPossiblePositions();  // Now parameterless
    }

    /**
     * Determines which player goes first based on the strength of the animal piece chosen.
     * The player who chooses the animal with the higher strength goes first.
     */
    public int determineFirstPlayer(int row, int col) {
        //System.out.println("\n== Determine First player ==");
        int p1Strength = selectAnimal(player1, board1.getP1Possible(), row, col);
        int p2Strength = selectAnimal(player2, board1.getP2Possible(), row, col);
        int ret;

        // Choose the first player to go based on the strength of the animal piece chosen
        //System.out.printf("\nPlayer 1 strength: %d | Player 2 strength: %d%n", p1Strength, p2Strength);
        if (p1Strength >= p2Strength) {
            currentPlayer = player1;
            ret = 1;
        } else {
            currentPlayer = player2;
            ret = 2;
        }
        System.out.println("[!] " + currentPlayer.getName() + " goes first!");

        return ret;
    }

    /**
     * Allows the player to select an animal piece to determine the first player.
     * The player selects a piece based on the available positions.
     *
     * @param player The player who is selecting the animal piece.
     * @param positions The list of possible positions for the pieces.
     * @return The strength of the selected animal piece, or 0 if not found.
     */
    public int selectAnimal(Player player, ArrayList<int[]> positions, int row, int col) {
        //System.out.println(player.getName() + ":");
        int[] choice = getValidPosition(positions, row, col);

        // Find the selected piece's strength
        for (Piece piece : player.getPieces()) {
            if (piece.getX() == choice[0] && piece.getY() == choice[1]) {
                return piece.getStrength();
            }
        }
        return 0; // return 0 if not found
    }

    /**
     * Gets a valid position from the player.
     * Ensures that the position entered by the player is valid.
     *
     * @param positions The list of possible positions for the pieces.
     * @return The valid position entered by the player.
     */
    public int[] getValidPosition(ArrayList<int[]> positions, int row, int col) {     
        int x = row;
        int y = col; 
        int[] coord = {x, y};

        for (int[] pos : positions) {
            if (pos[0] == x && pos[1] == y) {
                return coord;
            }
        }
        System.out.println("[!] Not a valid starting position!");

        return new int[]{-1, -1};
    }

    /**
     * Sets up the board1 with the players' pieces.
     */
    public void setupboard1() {
        board1 = new Board();
        placePiecesOnboard1(player1);
        placePiecesOnboard1(player2);
    }

    /**
     * Places the players' pieces on the board1.
     *
     * @param player The player whose pieces are to be placed on the board1.
     */
    public void placePiecesOnboard1(Player player) {
        for (Piece piece : player.getPieces()) {
            board1.placePiece(piece, piece.getX(), piece.getY());
        }
    }

    /**
     * Starts the game loop.
     */
    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        while (!gameOver) {
            playTurn(scanner);
        }
        scanner.close();
    }

    /**
     * Manage the player's turn.
     * Displays the board1, shows the current pieces, and processes the player's move.
     *
     * @param scanner The scanner object to read input from the player.
     */
    public void playTurn(Scanner scanner) {
        board1.displayBoard();
        showCurrentPieces();
        Piece selectedPiece = selectPiece(scanner);
        if (selectedPiece != null) {
            processPlayerMove(scanner, selectedPiece);
        }
    }

    /**
     * Displays the current pieces of the player.
     */
    private void showCurrentPieces() {
        System.out.println("\nCurrent Player's Pieces:");

        // Lion position
        Piece lion = currentPlayer.getPiece("Lion");
        if (lion != null) {
            System.out.println(" - Lion at (" + lion.getX() + "," + lion.getY() + ")");
        } else {
            System.out.println(" - Lion has been captured!");
        }

        // Rat position
        Piece rat = currentPlayer.getPiece("Rat");
        if (rat != null) {
            System.out.println(" - Rat at (" + rat.getX() + "," + rat.getY() + ")");
        } else {
            System.out.println(" - Rat has been captured!");
        }
    }

    /**
     * Allows the player to select a piece to move.
     * The player selects a piece based on the available pieces.
     *
     * @param scanner The scanner object to read input from the player.
     * @return The selected piece, or null if not found.
     */
    public Piece selectPiece(Scanner scanner) {
        System.out.println("\n=== " + currentPlayer.getName() + "'s TURN ===");
        System.out.print("Choose a piece to move (L for Lion, R for Rat): ");
        String input = scanner.nextLine().toUpperCase();

        switch (input) {
            case "L":
                Piece lion = currentPlayer.getPiece("Lion");
                return validatePiece(lion);
            case "R":
                Piece rat = currentPlayer.getPiece("Rat");
                return validatePiece(rat);
            default: // The JungleKing game only has two working pieces, Lion and Rat
                System.out.println("[!] Invalid choice. Please enter L (Lion) or R (Rat).");
                return null;
        }
    }

    /**
     * Validates the selected piece. If the piece is null, it is removed from the board1.
     *
     * @param piece The selected piece to validate.
     * @return The validated piece, or null if the piece is removed.
     */
    public Piece validatePiece(Piece piece) {
        if (piece == null) {
            System.out.println("[!] This piece is already removed from the board1.");
            return null;
        }
        return piece;
    }

    /**
     * Processes the player's move.
     * The player enters a move (W/A/S/D) to move the piece on the board1.
     *
     * @param scanner The scanner object to read input from the player.
     * @param piece The selected piece to move.
     */
    public void processPlayerMove(Scanner scanner, Piece piece) {
        System.out.print("Enter move (W/A/S/D): ");
        String move = scanner.nextLine().toUpperCase();

        int originalX = piece.getX();
        int originalY = piece.getY();
        int newX = originalX;
        int newY = originalY;
        switch (move) {
            case "W":
                newX--;
                break;
            case "A":
                newY--;
                break;
            case "S":
                newX++;
                break;
            case "D":
                newY++;
                break;
            default:
                System.out.println("\n[!] Invalid input. Use W/A/S/D.");
                return;
        }

        if (board1.isValidMove(newX, newY, currentPlayer)) {
            boolean moveSuccess = piece.move(newX, newY, board1);
            if (moveSuccess) {
                // Show move confirmation
                System.out.printf("\nMoved %s from (%d,%d) to (%d,%d)\n",
                        piece.getName(), originalX, originalY, newX, newY);
                checkWin(newX, newY);
                switchTurn();
            } else {
                System.out.println("\n[!] Invalid move. Try again.");
            }
        } else {
            System.out.println("\n[!] Invalid move. Either out of bounds or own base.");
        }
    }

    /**
     * Checks if the current move is a winning move.
     * If the move is a winning move, the game ends and the winner is declared.
     *
     * @param x The row position of the move.
     * @param y The column position of the move.
     */
    public void checkWin(int x, int y) {
        if (isWinningMove(x, y, currentPlayer)) {
            System.out.println("\n=== WINNER ===");
            System.out.println(currentPlayer.getName() + " wins!");
            gameOver = true;
        }
    }

    /**
     * Checks if the current move is a winning move.
     * A winning move is when the player reaches the opponent's base.
     *
     * @param x The row position of the move.
     * @param y The column position of the move.
     * @param player The player making the move.
     * @return True if the move is a winning move, false otherwise.
     */
    public boolean isWinningMove(int x, int y, Player player) {
        // Hardcode base positions
        boolean isP1Base = (x == 8 && y == 3);
        boolean isP2Base = (x == 0 && y == 3);

        // Player 1 wins by reaching P2 base
        if (player == player1 && isP2Base) {
            return true;
        }
        // Player 2 wins by reaching P1 base
        if (player == player2 && isP1Base) {
            return true;
        }

        return false;
    }

    /**
     * Switches the turn to the next player.
     */
    public void switchTurn() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }
}