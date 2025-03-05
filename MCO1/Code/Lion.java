public class Lion extends Piece {
    public Lion(int x, int y) {
        super("Lion", 6, x, y);
    }

    @Override
    public boolean move(int newX, int newY, Board board) {
        int dx = newX - x;
        int dy = newY - y;

        // Check if the move is horizontal or vertical
        if (dx != 0 && dy != 0) {
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

        // Check if the jump is horizontal or vertical
        if (dx != 0 && dy != 0) {
            return false;
        }

        // Check if the jump is over a lake
        int stepX = dx == 0 ? 0 : dx / Math.abs(dx);
        int stepY = dy == 0 ? 0 : dy / Math.abs(dy);

        int currentX = x + stepX;
        int currentY = y + stepY;

        while (currentX != newX || currentY != newY) {
            if (board.getTerrain(currentX, currentY) != '~') {
                return false; // Not a lake
            }
            if (board.getPiece(currentX, currentY) != null) {
                // Check if a Rat is blocking the path
                if (board.getPiece(currentX, currentY).getName().equals("Rat")) {
                    return false;
                }
            }
            currentX += stepX;
            currentY += stepY;
        }

        // Check the landing square
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

    private boolean isSamePlayer(Piece targetPiece) {
        // Check if the target piece belongs to the same player
        return this.getPlayer().equals(targetPiece.getPlayer());
    }
}