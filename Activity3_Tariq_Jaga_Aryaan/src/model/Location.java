package model;

// Class to represent a location on the board with a row and column
public class Location {
    private  int row;
    private  int col;

    // Constructor to initialize the location with a row and column
    public Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // Getter for the row
    public int getRow() {
        return row;
    }

    // Getter for the column
    public int getCol() {
        return col;
    }

    // Override the equals method to check if two locations are equal
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location location = (Location) o;
        return getRow() == location.getRow() && getCol() == location.getCol();
    }

    // Override the hashcode method to generate a hashcode based on the row and column
    @Override
    public int hashCode() {
        int result = getRow();
        result = 31 * result + getCol();
        return result;
    }

    // Override the toString method to return a string representation of the location
    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}