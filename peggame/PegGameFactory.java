package peggame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PegGameFactory {
   // Method to create a peg game from a file
   public static PegGame createPegGame(String filename) throws IOException {
       // Create a BufferedReader to read the file
       BufferedReader reader = new BufferedReader(new FileReader(filename));
       // Read the board size from the first line of the file
       int boardSize = Integer.parseInt(reader.readLine());
       // Create a new SquarePegGame object with the given board size
       SquarePegGame game = new SquarePegGame(boardSize);
   
       // Loop through each row of the board
       for (int row = 0; row < boardSize; row++) {
           // Read a line from the file
           String line = reader.readLine();
           // Loop through each column of the current row
           for (int col = 0; col < boardSize; col++) {
               // If the current cell contains an 'o', add a peg to that location on the board
               if (line.charAt(col) == 'o') {
                   game.addPeg(row, col);
               }
           }
       }
   
       // Return the created PegGame object
       return game;
   }
}