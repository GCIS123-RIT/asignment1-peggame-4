package peggame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SquarePegGame extends AbstractPegGame {
    // Class to implement the Square Peg Game

        public SquarePegGame(int boardSize) {
    super(boardSize);

    if (boardSize % 2 == 0) {
        throw new IllegalArgumentException("Size must be odd.");
    }

    for (int r = 0; r < boardSize; r++) {
        for (int c = 0; c < boardSize; c++) {
            Location pegLocation = new Location(r, c);
            if ((r + c) % 2 == 0) {
                board[r][c] = pegLocation;
                pegs.add(pegLocation);
            } else {
                board[r][c] = null; // Mark as empty space
            }
        }
    }
}


    private boolean isValidLocation(int r, int c) {
        // Method to check if a location on the board is valid (i.e. within the board and empty)
        if (r < 0 || r >= board.length || c < 0 || c >= board[r].length) {
            return false;
        }
        // Check if the location is not null (i.e., contains a peg)
        return board[r][c] == null;
    }

    @Override
    public Collection<Move> getPossibleMoves() {
        List<Move> moves = new ArrayList<>();
    
        for (Location peg : pegs) {
            int r = peg.getRow();
            int c = peg.getCol();
    
            // Check if the current location is a hole
            if (board[r][c] == null) {
                continue; // Skip this location if it's a hole
            }
    
            // Check possible moves from the current location
            // Add moves only if the destination is not a hole
            if (isValidLocation(r - 2, c - 1) && !hasPegAt(new Location(r - 1, c))) {
                moves.add(new Move(peg, board[r - 2][c - 1]));
            }
            if (isValidLocation(r - 2, c + 1) && !hasPegAt(new Location(r - 1, c))) {
                moves.add(new Move(peg, board[r - 2][c + 1]));
            }
            if (isValidLocation(r - 1, c - 2) && !hasPegAt(new Location(r, c - 1))) {
                moves.add(new Move(peg, board[r - 1][c - 2]));
            }
            if (isValidLocation(r - 1, c + 2) && !hasPegAt(new Location(r, c + 1))) {
                moves.add(new Move(peg, board[r - 1][c + 2]));
            }
            if (isValidLocation(r + 1, c - 2) && !hasPegAt(new Location(r, c - 1))) {
                moves.add(new Move(peg, board[r + 1][c - 2]));
            }
            if (isValidLocation(r + 1, c + 2) && !hasPegAt(new Location(r, c + 1))) {
                moves.add(new Move(peg, board[r + 1][c + 2]));
            }
            if (isValidLocation(r + 2, c - 1) && !hasPegAt(new Location(r + 1, c))) {
                moves.add(new Move(peg, board[r + 2][c - 1]));
            }
            if (isValidLocation(r + 2, c + 1) && !hasPegAt(new Location(r + 1, c))) {
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
        
        // Calculate the position of the intermediate location
        int intermediateRow = (from.getRow() + to.getRow()) / 2;
        int intermediateCol = (from.getCol() + to.getCol()) / 2;
        Location intermediate = new Location(intermediateRow, intermediateCol);
    
        if (hasPegAt(intermediate)) {
            // Remove the peg at the intermediate location
            board[intermediateRow][intermediateCol] = null;
            pegs.remove(intermediate);
        }
    
        // Move the peg from the start location to the end location
        board[to.getRow()][to.getCol()] = from;
        board[from.getRow()][from.getCol()] = null; // Remove the peg from its original location
        pegs.remove(from);
        pegs.add(to);
    }
    

    @Override
    public int getBoardSize() {
        return boardSize;
    }   

    public void addPeg(int row, int col) {
        if (row < 0 || row >= boardSize || col < 0 || col >= boardSize) {
            throw new IllegalArgumentException("Invalid location: " + row + " " + col);
        }
        if (board[row][col] != null) {
            throw new IllegalArgumentException("Location already occupied: " + row + " " + col);
        }
        Location pegLocation = new Location(row, col);
        board[row][col] = pegLocation;
        pegs.add(pegLocation);
    }

    public boolean hasPegAt(Location pegLocation) {
        if (pegLocation.getRow() < 0 || pegLocation.getRow() >= boardSize || pegLocation.getCol() < 0 || pegLocation.getCol() >= boardSize) {
            throw new IllegalArgumentException("Invalid location: " + pegLocation);
        }
        return board[pegLocation.getRow()][pegLocation.getCol()] != null;
    }

    @Override
    public boolean isValidMove(Location from, Location to) throws PegGameException {
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
    
        return true;
    }
    

    public void placePeg(int row, int col) {
        if (row < 0 || row >= boardSize || col < 0 || col >= boardSize) {
            throw new IllegalArgumentException("Invalid location: " + row + ", " + col);
        }
        if (board[row][col] != null) {
            throw new IllegalArgumentException("Location already occupied: " + row + ", " + col);
        }
        board[row][col] = new Location(row, col);
        pegs.add(new Location(row, col));
    }
    
}
