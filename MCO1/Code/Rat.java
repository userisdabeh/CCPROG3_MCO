public class Rat extends Piece {
    public Rat(int x, int y) {
        super("Rat", 1, x, y);
    }

    @Override
    public boolean move(int newX, int newY, Board board) {
        // Check if the move is to an adjacent square
        int dx = Math.abs(newX - x);
        int dy = Math.abs(newY - y);
        if ((dx == 1 && dy == 0) || (dx == 0 && dy == 1)) {
            // Check if the target square is a lake
            if (board.getTerrain(newX, newY) == '~') {
                // Rat can swim on lakes
                Piece targetPiece = board.getPiece(newX, newY);
                if (targetPiece == null || (targetPiece.getName().equals("Rat") && this.strength >= targetPiece.getStrength() && !isSamePlayer(targetPiece))) {
                    if (targetPiece != null) {
                        board.removePiece(targetPiece); // Remove the opponent's piece
                    }
                    board.updatePiecePosition(this, newX, newY);
                    return true;
                }
            } else {
                // Normal move on land
                Piece targetPiece = board.getPiece(newX, newY);
                if (targetPiece == null || (targetPiece.getName().equals("Elephant") && this.strength == 1) || (this.strength >= targetPiece.getStrength() && !isSamePlayer(targetPiece))) {
                    if (targetPiece != null) {
                        board.removePiece(targetPiece); // Remove the opponent's piece
                    }
                    board.updatePiecePosition(this, newX, newY);
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isSamePlayer(Piece targetPiece) {
        // Check if the target piece belongs to the same player
        return this.getPlayer().equals(targetPiece.getPlayer());
    }
}