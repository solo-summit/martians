package martians.domain.command;

import martians.domain.Grid;
import martians.domain.RobotState;

public final class CommandFactory {
    private final java.util.Map<String, RobotCommand> commands = java.util.Map.of(
            "L", new TurnLeft(), "R", new TurnRight(), "F", new MoveForward());
    public RobotCommand create(String symbol) {
        RobotCommand command = commands.get(symbol);
        if (command == null) throw new IllegalArgumentException("Unknown instruction: " + symbol);
        return command;
    }
}
