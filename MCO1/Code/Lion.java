public class Lion extends Piece {
    public Lion(int x, int y) {
        super("Lion", 6, x, y);
    }

    @Override
    public boolean move(int newX, int newY, Board board) {
        int dx = newX - x;
        int dy = newY - y;

        // Check if the move is within bounds
        if (newX < 0 || newX >= 9 || newY < 0 || newY >= 7) {
            return false;
        }

        // Check if the move is horizontal or vertical
        if (dx != 0 && dy != 0) {
            return false; // Diagonal moves are not allowed
        }

        // Check if the move is onto a lake (lions cannot move onto lakes)
        if (board.getTerrain(newX, newY) == '~') {
            return false;
        }

        // Check if the move is a lake jump
        if (Math.abs(dx) > 1 || Math.abs(dy) > 1) {
            return canJumpLake(newX, newY, board);
        }

        // Normal move (one square)
        Piece targetPiece = board.getPiece(newX, newY);
        if (targetPiece == null) {
            board.updatePiecePosition(this, newX, newY);
            return true;
        } else if (!isSamePlayer(targetPiece)) {
            // Check if the Lion can capture the opponent's piece
            if (this.strength >= targetPiece.getStrength()) {
                board.removePiece(targetPiece); // Remove the opponent's piece
                board.updatePiecePosition(this, newX, newY);
                return true;
            }
        }

        return false;
    }

    private boolean canJumpLake(int newX, int newY, Board board) {
        int dx = newX - x;
        int dy = newY - y;

        // Check if the move is horizontal or vertical
        if (dx != 0 && dy != 0) {
            return false; // Diagonal moves are not allowed
        }

        // Check if the jump is exactly 3 squares (the width of the lake)
        if (Math.abs(dx) != 3 && Math.abs(dy) != 3) {
            return false; // Lion must jump exactly 3 squares to cross the lake
        }

        // Determine the direction of the jump
        int stepX = dx == 0 ? 0 : dx / Math.abs(dx);
        int stepY = dy == 0 ? 0 : dy / Math.abs(dy);

        // Check if the lion is jumping over one of the two lakes
        boolean isJumpingOverLake = false;

        // Lake 1: Columns 1-2, Rows 3-5
        if ((x == 3 && y == 0 && newX == 3 && newY == 3) || // Left to right
                (x == 3 && y == 3 && newX == 3 && newY == 0)) { // Right to left
            isJumpingOverLake = true;
        }

        // Lake 2: Columns 4-5, Rows 3-5
        if ((x == 3 && y == 3 && newX == 3 && newY == 6) || // Left to right
                (x == 3 && y == 6 && newX == 3 && newY == 3)) { // Right to left
            isJumpingOverLake = true;
        }

        if (!isJumpingOverLake) {
            return false; // Lion is not jumping over a lake
        }

        // Check if the path over the lake is clear
        int currentX = x + stepX;
        int currentY = y + stepY;

        while (currentX != newX || currentY != newY) {
            // Check if the current square is a lake
            if (board.getTerrain(currentX, currentY) != '~') {
                return false; // Path is not entirely over the lake
            }

            // Check if a Rat is blocking the path
            Piece blockingPiece = board.getPiece(currentX, currentY);
            if (blockingPiece != null && blockingPiece.getName().equals("Rat")) {
                return false; // Rat is blocking the path
            }

            currentX += stepX;
            currentY += stepY;
        }

        // Check the landing square
        Piece targetPiece = board.getPiece(newX, newY);
        if (targetPiece == null || (this.strength >= targetPiece.getStrength() && !isSamePlayer(targetPiece))) {
            board.updatePiecePosition(this, newX, newY);
            return true;
        }

        return false;
    }

    private boolean isSamePlayer(Piece targetPiece) {
        // Check if the target piece belongs to the same player
        return this.getPlayer().equals(targetPiece.getPlayer());
    }
}