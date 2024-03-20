package peggame;

import static org.junit.Assert.*;
import org.junit.Test;
import peggame.Location;

public class Locationtest {

    @Test
    public void testLocationEquals() {
        Location location1 = new Location(1, 1);
        Location location2 = new Location(1, 1);
        Location location3 = new Location(2, 2);
        assertEquals(location1, location2);
        assertNotEquals(location1, location3);
    }

    @Test
    public void testLocationHashCode() {
        Location location1 = new Location(1, 1);
        Location location2 = new Location(1, 1);
        assertEquals(location1.hashCode(), location2.hashCode());
    }

    
}

