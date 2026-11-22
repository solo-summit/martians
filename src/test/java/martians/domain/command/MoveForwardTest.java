package martians.domain.command;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import martians.domain.*;
import martians.domain.command.CommandFactory;
import java.util.List;

class MoveForwardTest {
    @Test
    void preservesSafeMovesAndDistinguishesEdges() {
        Grid grid = new Grid(new Point(5, 3));
        var command = new MoveForward();
        var east = RobotState.startingAt(new Point(5, 3), Orientation.EAST);
        assertTrue(command.move(east, grid).isLost());
        assertEquals(east, command.move(east, grid));
        var north = RobotState.startingAt(new Point(5, 3), Orientation.NORTH);
        assertTrue(command.move(north, grid).isLost());
        var south = RobotState.startingAt(new Point(5, 3), Orientation.SOUTH);
        assertEquals(new Point(5, 2), command.move(south, grid).position());
    }
}
