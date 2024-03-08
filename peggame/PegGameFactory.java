package peggame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PegGameFactory {
    public static PegGame createPegGame(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        int boardSize = Integer.parseInt(reader.readLine());
        SquarePegGame game = new SquarePegGame(boardSize);
    
        for (int row = 0; row < boardSize; row++) {
            String line = reader.readLine();
            for (int col = 0; col < boardSize; col++) {
                if (line.charAt(col) == 'o') {
                    game.addPeg(row, col);
                }
            }
        }
    
        return game;
    }
}