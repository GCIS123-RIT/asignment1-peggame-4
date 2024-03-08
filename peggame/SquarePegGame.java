package peggame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SquarePegGame implements PegGame {
    // Class to implement the Square Peg Game
    private Location[][] board; // 2D array to represent the game board
    private List<Location> pegs; // List to keep track of the locations of the pegs on the board

    public SquarePegGame(int size) {
        // Constructor to initialize the game board
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
        // Method to check if a location on the board is valid (i.e. within the board and empty)
        if (r < 0 || r >= board.length || c < 0 || c >= board[r].length) {
            return false;
        }
        return board[r][c] == null;
    }

    public void addPeg(int r, int c) {
        // Method to add a peg to the board at the given location
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
        // Method to get the current state of the game
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
        /*
        throws PegGameException if there is no peg at the start location,
        if the end location is invalid,
        or if there is already a peg at the end location.
        */

        //Makes a move on the game board from one location to another.

        Location from = move.getFrom(); // Get the start and end locations of the move

        Location to = move.getTo();

        if (!pegs.contains(from)) {
            // Check if there is a peg at the start location
            throw new PegGameException("No peg at start location: " + from);
        }

        if (!isValidLocation(to.getRow(), to.getCol())) {
            // Check if the end location is valid
            throw new PegGameException("Invalid location: " + to);
        }

        if (pegs.contains(to)) {
           // Check if there is already a peg at the end location
            throw new PegGameException("Peg already exists at end location: " + to);
        }
        
        // Move the peg from the start location to the end location
        board[to.getRow()][to.getCol()] = from;
        board[from.getRow()][from.getCol()] = null;
        pegs.remove(from);
        pegs.add(to);
    }
}
