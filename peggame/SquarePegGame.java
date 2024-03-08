package peggame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SquarePegGame implements PegGame {
    private Location[][] board;
    private List<Location> pegs;

    public SquarePegGame(int size) {
        if (size % 2 == 0) {
            throw new IllegalArgumentException("Size must be odd.");
        }

        board = new Location[size][size];
        pegs = new ArrayList<>();

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if ((r + c) % 2 == 0) {
                    board[r][c] = new Location(r, c);
                    pegs.add(board[r][c]);
                }
            }
        }
    }

    private boolean isValidLocation(int r, int c) {
        if (r < 0 || r >= board.length || c < 0 || c >= board[r].length) {
            return false;
        }
        return board[r][c] == null;
    }

    public void addPeg(int r, int c) {
        if (!isValidLocation(r, c)) {
            throw new IllegalArgumentException("Invalid location: " + r + " " + c);
        }
        board[r][c] = new Location(r, c);
        pegs.add(board[r][c]);
    }

    @Override
    public Collection<Move> getPossibleMoves() {
        List<Move> moves = new ArrayList<>();

        for (Location peg : pegs) {
            int r = peg.getRow();
            int c = peg.getCol();

            if (isValidLocation(r - 2, c - 1)) {
                moves.add(new Move(peg, board[r - 2][c - 1]));
            }
            if (isValidLocation(r - 2, c + 1)) {
                moves.add(new Move(peg, board[r - 2][c + 1]));
            }
            if (isValidLocation(r - 1, c - 2)) {
                moves.add(new Move(peg, board[r - 1][c - 2]));
            }
            if (isValidLocation(r - 1, c + 2)) {
                moves.add(new Move(peg, board[r - 1][c + 2]));
            }
            if (isValidLocation(r + 1, c - 2)) {
                moves.add(new Move(peg, board[r + 1][c - 2]));
            }
            if (isValidLocation(r + 1, c + 2)) {
                moves.add(new Move(peg, board[r + 1][c + 2]));
            }
            if (isValidLocation(r + 2, c - 1)) {
                moves.add(new Move(peg, board[r + 2][c - 1]));
            }
            if (isValidLocation(r + 2, c + 1)) {
                moves.add(new Move(peg, board[r + 2][c + 1]));
            }
        }

        return moves;
    }

    @Override
    public GameState getGameState() {
        if (pegs.isEmpty()) {
            return GameState.WON;
        }

        List<Move> possibleMoves = (List<Move>) getPossibleMoves();
        if (possibleMoves.isEmpty()) {
            return GameState.STALEMATE;
        }

        return GameState.IN_PROGRESS;
    }

    @Override
        public void makeMove(Move move) throws PegGameException {
            Location from = move.getFrom();
            Location to = move.getTo();

            if (!pegs.contains(from)) {
                throw new PegGameException("No peg at start location: " + from);
            }

            if (!isValidLocation(to.getRow(), to.getCol())) {
                throw new PegGameException("Invalid location: " + to);
            }

            if (pegs.contains(to)) {
                throw new PegGameException("Peg already exists at end location: " + to);
            }

            board[to.getRow()][to.getCol()] = from;
            board[from.getRow()][from.getCol()] = null;
            pegs.remove(from);
            pegs.add(to);
        }
}
