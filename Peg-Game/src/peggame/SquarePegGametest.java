package peggame;

import static org.junit.Assert.*;
import org.junit.Test;
import peggame.SquarePegGame;

public class SquarePegGametest {

    @Test
    public void testSquarePegGameInitialization() {
        SquarePegGame game = new SquarePegGame(5);
        assertEquals(5, game.getBoardSize());
        // Add more assertions to test the initialization of the game
    }

    
}

