public class Lion extends Piece {
    // Lake regions (rows 3-5, columns 1-2 and 4-5)
    private static final int[][] LAKE_REGIONS = {
            {3, 1}, {3, 2}, {4, 1}, {4, 2}, {5, 1}, {5, 2},  // Left lake
            {3, 4}, {3, 5}, {4, 4}, {4, 5}, {5, 4}, {5, 5}   // Right lake
    };

    public Lion(int x, int y) {
        super("Lion", 7, x, y);
    }

    @Override
    public boolean move(int newX, int newY, Board board) {
        int dx = newX - x;
        int dy = newY - y;

        // Check basic move validity
        if (!board.isValidPosition(newX, newY) || (dx != 0 && dy != 0)) {
            return false;
        }

        // Check if trying to jump over a lake
        if (isAdjacentToLake() && isJumpingOverLake(dx, dy, board)) {
            return handleLakeJump(dx, dy, board);
        }

        // Regular move (1 square)
        return handleRegularMove(newX, newY, board);
    }

    public boolean isAdjacentToLake() {
        // Check if adjacent to any lake tile
        for (int[] lake : LAKE_REGIONS) {
            int lakeX = lake[0];
            int lakeY = lake[1];
            if ((Math.abs(x - lakeX) == 1 && y == lakeY) ||
                    (Math.abs(y - lakeY) == 1 && x == lakeX)) {
                return true;
            }
        }
        return false;
    }

    public boolean isJumpingOverLake(int dx, int dy, Board board) {
        // Determine jump direction and distance
        int directionX = Integer.signum(dx);
        int directionY = Integer.signum(dy);

        int targetX = x + (4 * directionX);
        int targetY = y + (3 * directionY);

        // Validate target position
        if (!board.isValidPosition(targetX, targetY) ||
                board.isLake(targetX, targetY)) {
            return false;
        }

        // Check intermediate tiles (lake and no Rats)
        int checkX = x + directionX;
        int checkY = y + directionY;
        for (int i = 0; i < 2; i++) {
            if (!board.isLake(checkX, checkY)) return false;
            Piece blocker = board.getPiece(checkX, checkY);
            if (blocker != null && blocker.getName().equals("Rat")) return false;
            checkX += directionX;
            checkY += directionY;
        }

        // Check target square
        Piece target = board.getPiece(targetX, targetY);
        return target == null || canCapture(target);
    }

    public boolean handleLakeJump(int dx, int dy, Board board) {
        int targetX = x + (4 * Integer.signum(dx)); // 4 steps for vertical jump
        int targetY = y + (3 * Integer.signum(dy)); // 3 steps for horizontal jump

        Piece target = board.getPiece(targetX, targetY);
        if (target != null) board.removePiece(target);

        board.updatePiecePosition(this, targetX, targetY);
        return true;
    }

    public boolean handleRegularMove(int newX, int newY, Board board) {
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