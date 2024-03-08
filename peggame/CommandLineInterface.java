package peggame;

import java.util.Collection;
import java.util.Scanner;

public class CommandLineInterface {
    // Constant variable for the quit command
    private static final String QUIT = "quit";
    //peg game instance
    private PegGame game;

    /**
     * Starts the game and handles user input
     *
     * @param game PegGame instance
     */
    public void play(PegGame game) {
        this.game = game;
        Scanner scanner = new Scanner(System.in); //create a new scanner to read user input
        boolean playing = true; //flag to check if the game is still running

        while (playing) {
            //print the current game state
            System.out.println(game);
            //prompt the user to enter their move or quit
            System.out.println("Enter your move (row1 col1 row2 col2) or " + QUIT + " to quit:");
            String input = scanner.nextLine(); //read the user input
            if (input.equalsIgnoreCase(QUIT)) {
                playing = false; //set the playing flag to false if the user wants to quit
                System.out.println("Goodbye!");
            } else {
                String[] parts = input.split(" "); //split the user input by space
                if (parts.length == 5) { //check if the input is valid
                    int row1 = Integer.parseInt(parts[0]); //get the row1 from the user input
                    int col1 = Integer.parseInt(parts[1]); //get the col1 from the user input
                    int row2 = Integer.parseInt(parts[2]); //get the row2 from the user input
                    int col2 = Integer.parseInt(parts[3]); //get the col2 from the user input
                    try {
                        //create a move object from the user input
                        Move move = new Move(new Location(row1, col1), new Location(row2, col2));
                        //make the move on the game
                        game.makeMove(move);
                        //check if the game is over
                        if (game.getGameState() != GameState.IN_PROGRESS) {
                            System.out.println(game);
                            System.out.println("Game over!");
                            playing = false;
                        }
                    } catch (PegGameException e) {
                        //print the error message if there is an error in making the move
                        System.err.println(e.getMessage());
                    }
                } else {
                    System.err.println("Invalid input."); //print an error message if the user input is invalid
                }
            }
        }
    }
}