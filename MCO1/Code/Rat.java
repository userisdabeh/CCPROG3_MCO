public class Rat extends Piece {
    public Rat(int x, int y) {
        super("Rat", 1, x, y);
    }

    @Override
    public boolean move(int newX, int newY, Board board) {
        // Adjacent move check
        if (Math.abs(newX - x) + Math.abs(newY - y) != 1) {
            return false;
        }

        Piece target = board.getPiece(newX, newY);
        boolean currentInWater = board.isLake(x, y);
        boolean newInWater = board.isLake(newX, newY);

        if (target != null) {
            boolean targetInWater = board.isLake(target.getX(), target.getY());

            // Check if same team
            if (this.player == target.getPlayer()) {
                return false;
            }

            // Capture elephant case
            if (target.getName().equals("Elephant")) {
                if (!currentInWater && !targetInWater) {
                    board.removePiece(target);
                } else {
                    return false;
                }
            }
            // Capture rat case
            else if (target.getName().equals("Rat")) {
                if (!(newInWater && targetInWater)) {
                    return false;
                }
                if (this.strength < target.getStrength()) {
                    return false;
                }
                board.removePiece(target);
            }

            // Handle normal capture
            else {
                if (this.strength < target.getStrength()) {
                    return false;
                }
                if (newInWater) {
                    return false;
                }
                board.removePiece(target);
            }
        }

        board.updatePiecePosition(this, newX, newY);
        return true;
    }
}