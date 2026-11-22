package martians.domain;

import java.util.List;
import java.util.Objects;

public record RobotMission(RobotState initialState, List<String> instructions) {
    public RobotMission {
        Objects.requireNonNull(initialState);
        instructions = List.copyOf(instructions);
    }
}
