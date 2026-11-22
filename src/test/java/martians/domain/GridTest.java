package martians.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import martians.domain.*;
import martians.domain.command.CommandFactory;
import java.util.List;

class GridTest {
    @Test
    void includesBoundary() {
        Grid grid = new Grid(new Point(5, 3));
        assertTrue(grid.contains(new Point(0, 0)));
        assertTrue(grid.contains(new Point(5, 3)));
        assertFalse(grid.contains(new Point(6, 3)));
        assertFalse(grid.contains(new Point(0, -1)));
    }
    @Test
    void matchesPositionAndDirectionOnly() {
        Grid grid = new Grid(new Point(5, 3));
        grid.leaveScentAt(new Point(5, 3), Orientation.EAST);
        assertTrue(grid.hasScentAt(new Point(5, 3), Orientation.EAST));
        assertFalse(grid.hasScentAt(new Point(5, 3), Orientation.NORTH));
        assertFalse(grid.hasScentAt(new Point(5, 2), Orientation.EAST));
    }
}
