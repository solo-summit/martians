package martians.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import martians.domain.*;
import martians.domain.command.CommandFactory;
import java.util.List;

class RobotTest {
    private final Grid grid = new Grid(new Point(5, 3));
    private final CommandFactory commands = new CommandFactory();
    private Robot robotAt(Point point, Orientation orientation) {
        return new Robot(grid, RobotState.startingAt(point, orientation), commands);
    }
    @Test
    void turnsWithoutMoving() {
        Robot robot = robotAt(new Point(1, 1), Orientation.EAST);
        robot.invoke(List.of("L", "L", "R"));
        assertEquals(RobotStatus.ACTIVE, robot.state().status());
        assertEquals(new Point(1, 1), robot.state().position());
        assertEquals(Orientation.NORTH, robot.state().orientation());
    }
    @Test
    void movesForwardWithinGrid() {
        Robot robot = robotAt(new Point(1, 1), Orientation.EAST);
        robot.invoke(List.of("F", "F"));
        assertEquals(RobotStatus.ACTIVE, robot.state().status());
        assertEquals(new Point(3, 1), robot.state().position());
        assertEquals(Orientation.EAST, robot.state().orientation());
    }
    @Test
    void robotIsLostWhenMovingOffGrid() {
        Robot robot = robotAt(new Point(5, 3), Orientation.EAST);
        robot.invoke(List.of("F"));
        assertEquals(RobotStatus.LOST, robot.state().status());
        assertEquals(new Point(5, 3), robot.state().position());
        assertEquals(Orientation.EAST, robot.state().orientation());
    }
    @Test
    void stopsProcessingAfterLoss() {
        Robot robot = robotAt(new Point(5, 3), Orientation.EAST);
        robot.invoke(List.of("F", "L", "F"));
        assertEquals(RobotStatus.LOST, robot.state().status());
        assertEquals(new Point(5, 3), robot.state().position());
        assertEquals(Orientation.EAST, robot.state().orientation());
    }
    @Test
    void scentPreventsLaterRobotFallingOffSameEdge() {
        Robot first = robotAt(new Point(5, 3), Orientation.EAST);
        first.invoke(List.of("F"));
        assertEquals(RobotStatus.LOST, first.state().status());
        assertEquals(new Point(5, 3), first.state().position());
        assertEquals(Orientation.EAST, first.state().orientation());
        assertTrue(grid.hasScentAt(new Point(5, 3), Orientation.EAST));
        Robot second = robotAt(new Point(5, 3), Orientation.EAST);
        second.invoke(List.of("F"));
        assertEquals(RobotStatus.ACTIVE, second.state().status());
        assertEquals(new Point(5, 3), second.state().position());
        assertEquals(Orientation.EAST, second.state().orientation());
    }
    @Test
    void scentDoesNotPreventLaterRobotFallingOffDifferentEdge() {
        Robot first = robotAt(new Point(5, 3), Orientation.EAST);
        first.invoke(List.of("F"));
        assertEquals(RobotStatus.LOST, first.state().status());
        assertEquals(new Point(5, 3), first.state().position());
        assertEquals(Orientation.EAST, first.state().orientation());
        assertTrue(grid.hasScentAt(new Point(5, 3), Orientation.EAST));
        Robot second = robotAt(new Point(5, 3), Orientation.NORTH);
        second.invoke(List.of("F"));
        assertEquals(RobotStatus.LOST, second.state().status());
        assertEquals(new Point(5, 3), second.state().position());
        assertEquals(Orientation.NORTH, second.state().orientation());
    }

}
