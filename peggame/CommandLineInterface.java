package peggame;

import java.util.Collection;
import java.util.Scanner;

public class CommandLineInterface {
    private static final String QUIT = "quit";
    private PegGame game;

    public void play(PegGame game) {
        this.game = game;
        Scanner scanner = new Scanner(System.in);
        boolean playing = true;

        while (playing) {
            System.out.println(game);
            System.out.println("Enter your move (row1 col1 row2 col2) or " + QUIT + " to quit:");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase(QUIT)) {
                playing = false;
                System.out.println("Goodbye!");
            } else {
                String[] parts = input.split(" ");
                if (parts.length == 5) {
                    int row1 = Integer.parseInt(parts[0]);
                    int col1 = Integer.parseInt(parts[1]);
                    int row2 = Integer.parseInt(parts[2]);
                    int col2 = Integer.parseInt(parts[3]);
                    try {
                        Move move = new Move(new Location(row1, col1), new Location(row2, col2));
                        game.makeMove(move);
                        if (game.getGameState() != GameState.IN_PROGRESS) {
                            System.out.println(game);
                            System.out.println("Game over!");
                            playing = false;
                        }
                    } catch (PegGameException e) {
                        System.err.println(e.getMessage());
                    }
                } else {
                    System.err.println("Invalid input.");
                }
            }
        }
    }
}

       