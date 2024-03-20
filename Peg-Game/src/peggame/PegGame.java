package peggame;

// Interface to represent a peg game
import java.util.Collection;

public interface PegGame {
    // Method to get all possible moves for the current state of the game
    Collection<Move> getPossibleMoves();

    // Method to get the current state of the game
    GameState getGameState();

    // Method to make a move on the game
    void makeMove(Move move) throws PegGameException;

    // Method to get the board size
    int getBoardSize();

    // Method to check if there is a peg at a given location
    boolean hasPegAt(Location location);

    boolean isValidMove(Location from, Location to) throws PegGameException;
}