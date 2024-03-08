package peggame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Project1Main {
   // Main method to start the game
   public static void main(String[] args) {
       // Create a new SquarePegGame object, but it will be initialized later
       SquarePegGame game = null;
       try {
           // Initialize the game object by calling the PegGameFactory to create a PegGame from a file
           game = (SquarePegGame) PegGameFactory.createPegGame("src/resources/pegs.txt");
       } catch (IOException e) {
           // Print an error message, print the stack trace, and exit the program if there is an IOException
           System.err.println("Error creating peg game from file.");
           e.printStackTrace();
           System.exit(1);
       }

       // Create a new CommandLineInterface object
       CommandLineInterface cli = new CommandLineInterface();
       // Start the game by calling the play method on the CommandLineInterface object
       cli.play(game);
   }
}
