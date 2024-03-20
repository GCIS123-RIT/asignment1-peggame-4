package peggame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PegGameFactory {
    public static PegGame createPegGame(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));

        int boardSize = Integer.parseInt(reader.readLine());
        System.out.println("Board size: " + boardSize);

        SquarePegGame game = new SquarePegGame(boardSize);

        // Read the board from the file
        for (int row = 0; row < boardSize; row++) {
            String line = reader.readLine();
            System.out.println("Row " + row + ": " + line);

            for (int col = 0; col < boardSize; col++) {
                Location location = new Location(row, col);
                if (line.charAt(col) == 'o') {
                    if (!game.hasPegAt(location)) { // Check if the location is not already occupied
                        game.placePeg(row, col); // Add a peg
                    }
                } else {
                    game.board[row][col] = null; // Mark as empty space
                }
            }
        }

        // Print the initialized game board for debugging
        System.out.println("Initialized game board:");
        System.out.println(game);

        return game;
    }
}
