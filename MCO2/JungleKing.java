import java.util.*;

/**
 * JungleKing class is the main class that runs the Jungle King game.
 * It initializes the game, sets up the board, and starts the game loop.
 */
public class JungleKing {
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private boolean gameOver;

    private Scanner scanner;

    /**
     * Constructor for the JungleKing class.
     * Initializes the game, sets up the board, and starts the game loop.
     */
    public JungleKing() {
        scanner = new Scanner(System.in);
        board = new Board();  // Now initializes possible positions internally
        createPlayers();
        showInitialPositions();
        determineFirstPlayer();
        setupBoard();
    }

    /**
     * Creates the players for the game.
     */
    public void createPlayers() {
        player1 = new Player("Player 1", createPieces(board.getP1Possible()));
        player2 = new Player("Player 2", createPieces(board.getP2Possible()));
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

    /**
     * Displays the initial positions of the pieces on the board.
     */
    public void showInitialPositions() {
        board.showPossiblePositions();  // Now parameterless
    }

    /**
     * Determines which player goes first based on the strength of the animal piece chosen.
     * The player who chooses the animal with the higher strength goes first.
     */
    public void determineFirstPlayer() {
        System.out.println("\n== Determine First player ==");
        int p1Strength = selectAnimal(player1, board.getP1Possible());
        int p2Strength = selectAnimal(player2, board.getP2Possible());

        // Choose the first player to go based on the strength of the animal piece chosen
        System.out.printf("\nPlayer 1 strength: %d | Player 2 strength: %d%n", p1Strength, p2Strength);
        if (p1Strength >= p2Strength) {
            currentPlayer = player1;
        } else {
            currentPlayer = player2;
        }
        System.out.println("[!] " + currentPlayer.getName() + " goes first!");
    }

    /**
     * Allows the player to select an animal piece to determine the first player.
     * The player selects a piece based on the available positions.
     *
     * @param player The player who is selecting the animal piece.
     * @param positions The list of possible positions for the pieces.
     * @return The strength of the selected animal piece, or 0 if not found.
     */
    public int selectAnimal(Player player, ArrayList<int[]> positions) {
        System.out.println(player.getName() + ":");
        int[] choice = getValidPosition(positions);

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
    public int[] getValidPosition(ArrayList<int[]> positions) {
        while (true) {
            System.out.print("Enter coordinates (x,y): ");
            String input = scanner.nextLine().trim();

            if (input.contains(",")) {  // Will only work if the input contains ","
                try { // Might throw exceptions
                    String[] parts = input.split(",");
                    int x = Integer.parseInt(parts[0].trim());
                    int y = Integer.parseInt(parts[1].trim());
                    int[] coord = {x, y};

                    for (int[] pos : positions) {
                        if (pos[0] == x && pos[1] == y) {
                            return coord;
                        }
                    }
                    System.out.println("[!] Not a valid starting position!");
                } catch (NumberFormatException e) { // Catches exception when trying to convert non-numeric text to numbers
                    System.out.println("[!] Please enter numbers only!");
                }
            } else {
                System.out.println("[!] Invalid format! Use comma between numbers");
            }
        }
    }

    /**
     * Sets up the board with the players' pieces.
     */
    public void setupBoard() {
        board = new Board();
        placePiecesOnBoard(player1);
        placePiecesOnBoard(player2);
    }

    /**
     * Places the players' pieces on the board.
     *
     * @param player The player whose pieces are to be placed on the board.
     */
    public void placePiecesOnBoard(Player player) {
        for (Piece piece : player.getPieces()) {
            board.placePiece(piece, piece.getX(), piece.getY());
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
     * Displays the board, shows the current pieces, and processes the player's move.
     *
     * @param scanner The scanner object to read input from the player.
     */
    public void playTurn(Scanner scanner) {
        board.displayBoard();
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
     * Validates the selected piece. If the piece is null, it is removed from the board.
     *
     * @param piece The selected piece to validate.
     * @return The validated piece, or null if the piece is removed.
     */
    public Piece validatePiece(Piece piece) {
        if (piece == null) {
            System.out.println("[!] This piece is already removed from the board.");
            return null;
        }
        return piece;
    }

    /**
     * Processes the player's move.
     * The player enters a move (W/A/S/D) to move the piece on the board.
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

        if (board.isValidMove(newX, newY, currentPlayer)) {
            boolean moveSuccess = piece.move(newX, newY, board);
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