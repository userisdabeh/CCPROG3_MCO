package model;

import java.util.ArrayList;

/**
 * Represents the Dog piece in the Jungle King game.
 * The Dog has a strength of 3.
 * Extends the {@link Piece} class.
 * The Dog can move to any adjacent square, including diagonally.
 * The Dog has no special abilities or restrictions.
 */
public class Dog extends Piece {

    /**
     * Constructor for the Dog class at the specified position.
     * @param x The row position of the Dog on the board.
     * @param y The column position of the Dog on the board,
     */
    public Dog(int x, int y) {
        super("Dog", 3, x, y);
    }

    /**
     * Generates a list of valid moves for the Dog piece.
     * 
     * @param board The game board.
     * @return A list of valid moves for the Dog piece.
     */
    @Override
    public ArrayList<int[]> getValidMoves(Board board) {
        return generateValidMoves(board);
    }

    /**
     * Attempts to move the Dog to the new position.
     * 
     * @param newX The new row position of the Dog.
     * @param newY The new column position of the Dog.
     * @param board The game board.
     * @return {@code true} if the move is successful, {@code false} if the move is invalid.
     */
    @Override
    public boolean move(int newX, int newY, Board board) {
        if (board.isLake(newX, newY)) return false;
        return basicMove(newX, newY, board);
    }
}