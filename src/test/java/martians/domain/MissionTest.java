package martians.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import martians.domain.*;
import martians.domain.command.CommandFactory;
import java.util.List;

class MissionTest {
    @Test
    void launchesRobotsSequentially() {
        var initial = RobotState.startingAt(new Point(5, 3), Orientation.EAST);
        var mission = new Mission(new Grid(new Point(5, 3)), List.of(
                new RobotMission(initial, List.of("F")), new RobotMission(initial, List.of("F"))), new CommandFactory());
        var results = mission.launch();
        assertEquals(initial.lost(), results.get(0));
        assertEquals(initial, results.get(1));
    }
    @Test
    void launchesEmptyMission() {
        assertTrue(new Mission(new Grid(new Point(5, 3)), List.of(), new CommandFactory()).launch().isEmpty());
    }
}
