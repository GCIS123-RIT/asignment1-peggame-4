package peggame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Project1Main {
    public static void main(String[] args) {
        SquarePegGame game = null;
        try {
            game = (SquarePegGame) PegGameFactory.createPegGame("src/resources/pegs.txt");
        } catch (IOException e) {
            System.err.println("Error creating peg game from file.");
            e.printStackTrace();
            System.exit(1);
        }

        CommandLineInterface cli = new CommandLineInterface();
        cli.play(game);
    }
}
