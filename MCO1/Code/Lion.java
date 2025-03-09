public class Lion extends Piece {
    public Lion(int x, int y) {
        super("Lion", 7, x, y);
    }

    @Override
    public boolean move(int newX, int newY, Board board) {
        int dx = newX - x;
        int dy = newY - y;

        if (!board.isValidPosition(newX, newY)) return false;

        // Only allow straight line movement
        if (dx != 0 && dy != 0) return false;

        // Check if attempting lake jump
        if (isLakeEdge(x, y) && isJumpingOverLake(dx, dy, board)) {
            return lakeJump(dx, dy, board);
        }

        // Regular move validation
        return normalMove(newX, newY, board);
    }

    public boolean isLakeEdge(int x, int y) {
        // North edges (row 2) of both lakes
        boolean northEdge = (x == 2) &&
                ((y >= 1 && y <= 2) || (y >= 4 && y <= 5));

        // South edges (row 6) of both lakes
        boolean southEdge = (x == 6) &&
                ((y >= 1 && y <= 2) || (y >= 4 && y <= 5));

        // West edges (columns 0 and 3)
        boolean westEdge = (y == 0 || y == 3) &&
                (x >= 3 && x <= 5);

        // East edges (columns 3 and 6)
        boolean eastEdge = (y == 3 || y == 6) &&
                (x >= 3 && x <= 5);

        return northEdge || southEdge || westEdge || eastEdge;
    }

    public boolean isJumpingOverLake(int dx, int dy, Board board) {
        // Determine jump direction and distance
        int directionX = Integer.signum(dx);
        int directionY = Integer.signum(dy);

        int targetX = x + (4 * directionX);
        int targetY = y + (3 * directionY);

        // Validate target position
        if (!board.isValidPosition(targetX, targetY) || board.isLake(targetX, targetY)) {
            return false;
        }

        // Check intermediate tiles (lake and no Rats)
        int checkX = x + directionX;
        int checkY = y + directionY;
        for (int i = 0; i < 2; i++) {
            if (!board.isLake(checkX, checkY)) {
                return false;
            }
            Piece blocker = board.getPiece(checkX, checkY);
            if (blocker != null && blocker.getName().equals("Rat")) {
                return false;
            }
            checkX += directionX;
            checkY += directionY;
        }

        // Check target square
        Piece target = board.getPiece(targetX, targetY);
        return target == null || canCapture(target);
    }

    public boolean lakeJump(int dx, int dy, Board board) {
        int targetX = x + (4 * Integer.signum(dx)); // 4 steps for vertical jump
        int targetY = y + (3 * Integer.signum(dy)); // 3 steps for horizontal jump

        Piece target = board.getPiece(targetX, targetY);
        if (target != null) board.removePiece(target);

        board.updatePiecePosition(this, targetX, targetY);
        return true;
    }

    public boolean normalMove(int newX, int newY, Board board) {
        if(board.isLake(newX, newY)) {
            return false;
        }
        
        Piece target = board.getPiece(newX, newY);
        if (target != null && !canCapture(target)) {
            return false;
        }

        if (target != null) {
            board.removePiece(target);
        }
        board.updatePiecePosition(this, newX, newY);
        return true;
    }
}