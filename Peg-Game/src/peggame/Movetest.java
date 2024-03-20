package peggame;

import static org.junit.Assert.*;
import org.junit.Test;
import peggame.Location;
import peggame.Move;

public class Movetest {

    @Test
    public void testMoveToString() {
        Location from = new Location(0, 0);
        Location to = new Location(1, 1);
        Move move = new Move(from, to);
        assertEquals("Move{from=(0, 0), to=(1, 1)}", move.toString());
    }

    
    
}

