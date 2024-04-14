package model;

// Class to represent a move on the board from one location to another
public class Move {
    private final Location from;
    private final Location to;

    // Constructor to initialize the move with a start and end location
    public Move(Location from, Location to) {
        this.from = from;
        this.to = to;
    }

    // Getter for the start location
    public Location getFrom() {
        return from;
    }

    // Getter for the end location
    public Location getTo() {
        return to;
    }

    // Override the toString method to return a string representation of the move
    @Override
    public String toString() {
        return "Move{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}