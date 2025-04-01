package model;

import java.util.ArrayList;

/**
 * Represents the Wolf piece in the game.
 * The Wolf has a strength of 5.
 * Extends the {@link Piece} class.
 * The Wolf can move to any adjacent square, including diagonally.
 */
public class Wolf extends Piece {

    /**
     * Constructor for the wolf class at the specified position.
     * 
     * @param x The row position of the wolf on the board.
     * @param y The column position of the wolf on the board,
     */
    public Wolf(int x, int y) {
        super("Wolf", 4, x, y);
    }

    /**
     * Generates a list of valid moves for the wolf piece.
     * 
     * @param board The game board.
     * @return A list of valid moves for the wolf piece.
     */
    @Override
    public ArrayList<int[]> getValidMoves(Board board) {
        return generateValidMoves(board);
    }

    /**
     * Attempts to move the Wolf to the new position.
     * 
     * @param newX The new row position of the Wolf.
     * @param newY The new column position of the Wolf.
     * @param board The game board.
     * @return {@code true} if the move is successful, {@code false} if the move is invalid.
     */
    @Override
    public boolean move(int newX, int newY, Board board) {
        if (board.isLake(newX, newY)) return false;
        return basicMove(newX, newY, board);
    }
}