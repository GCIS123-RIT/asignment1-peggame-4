package model;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPegGame implements PegGame {
    protected int boardSize;
    protected Location[][] board;
    protected List<Location> pegs;

    public AbstractPegGame(int boardSize) {
        this.boardSize = boardSize;
        this.board = new Location[boardSize][boardSize];
        this.pegs = new ArrayList<>();
    }

    @Override
    public boolean hasPegAt(Location location) {
        return board[location.getRow()][location.getCol()] != null;
    }

    @Override
    public int getBoardSize() {
        return boardSize;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // Append the column indices at the top
        sb.append("  ");
        for (int col = 0; col < boardSize; col++) {
            sb.append(col).append(" ");
        }
        sb.append("\n");
        // Append the row indices and board contents
        for (int row = 0; row < boardSize; row++) {
            sb.append(row).append(" ");
            for (int col = 0; col < boardSize; col++) {
                if (board[row][col] != null) {
                    sb.append("o "); // peg exists at this location
                } else {
                    sb.append(". "); // empty space (hole)
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}