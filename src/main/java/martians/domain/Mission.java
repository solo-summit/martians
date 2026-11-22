package martians.domain;

import martians.domain.command.CommandFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record Mission(
        Grid grid,
        List<RobotMission> robotMissions,
        CommandFactory commandFactory
) {

    public Mission(Grid grid, List<RobotMission> robotMissions, CommandFactory commandFactory) {
        this.grid = Objects.requireNonNull(grid, "grid must not be null");
        this.commandFactory = Objects.requireNonNull(commandFactory, "commandFactory must not be null");
        this.robotMissions = List.copyOf(robotMissions);
    }

    public List<RobotState> launch() {
        List<RobotState> results = new ArrayList<>();

        for (RobotMission robotMission : robotMissions) {
            Robot robot = new Robot(
                    grid,
                    robotMission.initialState(),
                    commandFactory
            );

            robot.invoke(robotMission.instructions());
            results.add(robot.state());
        }

        return results;
    }
}