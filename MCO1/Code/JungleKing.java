import java.util.*;

public class JungleKing {
    private Board board;
    public static Player player1;
    public static Player player2;
    private Player currentPlayer;
    private List<int[]> p1Possible;
    private List<int[]> p2Possible;
    private boolean gameOver;

    private Scanner scanner;

    public JungleKing() {
        scanner = new Scanner(System.in);
        setupPossiblePositions();
        createPlayers();
        showInitialPositions();
        determineFirstPlayer();
        setupBoard();
    }

    public void setupPossiblePositions() {
        p1Possible = new ArrayList<>();
        p2Possible = new ArrayList<>();

        // Player 1 possible piece positions
        p1Possible.add(new int[]{8,6});
        p1Possible.add(new int[]{6,6});
        p1Possible.add(new int[]{7,5});
        p1Possible.add(new int[]{6,4});
        p1Possible.add(new int[]{6,2});
        p1Possible.add(new int[]{7,1});
        p1Possible.add(new int[]{8,0});
        p1Possible.add(new int[]{6,0});

        // Player 2 possible piece positions
        p2Possible.add(new int[]{0,0});
        p2Possible.add(new int[]{2,0});
        p2Possible.add(new int[]{1,1});
        p2Possible.add(new int[]{2,2});
        p2Possible.add(new int[]{2,4});
        p2Possible.add(new int[]{1,5});
        p2Possible.add(new int[]{0,6});
        p2Possible.add(new int[]{2,6});

        // Shuffle the pieces in the arraylist
        Collections.shuffle(p1Possible);
        Collections.shuffle(p2Possible);
    }

    public void createPlayers() {
        player1 = new Player("Player 1", createPieces((ArrayList<int[]>) p1Possible));
        player2 = new Player("Player 2", createPieces((ArrayList<int[]>) p2Possible));
    }

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

    public void showInitialPositions() {
        board = new Board();
        board.showPossiblePositions((ArrayList<int[]>) p1Possible, (ArrayList<int[]>) p2Possible);
    }

    public void determineFirstPlayer() {
        System.out.println("\n== Determine First player ==");
        int p1Strength = selectAnimal(player1, (ArrayList<int[]>) p1Possible);
        int p2Strength = selectAnimal(player2, (ArrayList<int[]>) p2Possible);

        // Choose the first player to go based on the strength of the animal piece chosen
        System.out.printf("\nPlayer 1 strength: %d | Player 2 strength: %d%n", p1Strength, p2Strength);
        if (p1Strength >= p2Strength) {
            currentPlayer = player1;
        } else {
            currentPlayer = player2;
        }
        System.out.println("[!] " + currentPlayer.getName() + " goes first!");
    }

    public int selectAnimal(Player player, ArrayList<int[]> positions) {
        System.out.println(player.getName() + ":");
        int[] choice = getValidPosition(positions);

        // Find the selected piece's strength
        for(Piece piece : player.getPieces()) {
            if(piece.getX() == choice[0] && piece.getY() == choice[1]) {
                return piece.getStrength();
            }
        }
        return 0; // return 0 if not found
    }

    public int[] getValidPosition(ArrayList<int[]> positions) {
        while(true) {
            System.out.print("Enter coordinates (x,y): ");
            String input = scanner.nextLine().trim();

            if(input.contains(",")) {  // Will only work if the input contains ","
                try { // Might throw exceptions
                    String[] parts = input.split(",");
                    int x = Integer.parseInt(parts[0].trim());
                    int y = Integer.parseInt(parts[1].trim());
                    int[] coord = {x, y};

                    for(int[] pos : positions) {
                        if(pos[0] == x && pos[1] == y) {
                            return coord;
                        }
                    }
                    System.out.println("[!] Not a valid starting position!");
                }
                catch(NumberFormatException e) { // Learned through CCINFOM, it catches exception when trying to convert non-numeric text to numbers
                    System.out.println("[!] Please enter numbers only!");
                }
            } else {
                System.out.println("[!] Invalid format! Use comma between numbers");
            }
        }
    }

    public void setupBoard() {
        board = new Board();
        placePiecesOnBoard(player1);
        placePiecesOnBoard(player2);
    }

    public void placePiecesOnBoard(Player player) {
        for(Piece piece : player.getPieces()) {
            board.placePiece(piece, piece.getX(), piece.getY());
        }
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        while (!gameOver) {
            playTurn(scanner);
        }
        scanner.close();
    }

    public void playTurn(Scanner scanner) {
        board.displayBoard();
        Piece selectedPiece = selectPiece(scanner);
        if (selectedPiece != null) {
            processPlayerMove(scanner, selectedPiece);
        }
    }

    public Piece selectPiece(Scanner scanner) {
        System.out.print("\n" + currentPlayer.getName() + "'s turn. Choose a piece to move (L for Lion, R for Rat): ");
        String input = scanner.nextLine().toUpperCase();

        switch (input) {
            case "L":
                Piece lion = currentPlayer.getPiece("Lion");
                return validatePiece(lion);
            case "R":
                Piece rat = currentPlayer.getPiece("Rat");
                return validatePiece(rat);
            default:
                System.out.println("[!] Invalid choice. Please enter L (Lion) or R (Rat).");
                return null;
        }
    }

    public Piece validatePiece(Piece piece) {
        if (piece == null) {
            System.out.println("[!] This piece is already removed from the board.");
            return null;
        }
        return piece;
    }

    public void processPlayerMove(Scanner scanner, Piece piece) {
        System.out.print("Enter move (W/A/S/D): ");
        String move = scanner.nextLine().toUpperCase();

        int newX = piece.getX();
        int newY = piece.getY();
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
                checkWinCondition(newX, newY);
                switchTurn();
            } else {
                System.out.println("\n[!] Invalid move. Try again.");
            }
        } else {
            System.out.println("\n[!] Invalid move. Either out of bounds or own base.");
        }
    }

    public void checkWinCondition(int x, int y) {
        if (isWinningMove(x, y, currentPlayer, board)) {
            System.out.println(currentPlayer.getName() + " wins!");
            gameOver = true;
        }
    }

    public static boolean isWinningMove(int x, int y, Player player, Board board) {
        char terrain = board.getTerrain(x, y);
        return (player == player1 && terrain == '2') || (player == player2 && terrain == '1');
    }

    public void switchTurn() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }
}