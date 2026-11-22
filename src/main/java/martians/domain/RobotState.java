package martians.domain;

import java.util.Objects;

public record RobotState(Point position, Orientation orientation, RobotStatus status) {
    public RobotState {
        Objects.requireNonNull(position); Objects.requireNonNull(orientation); Objects.requireNonNull(status);
    }
    public static RobotState startingAt(Point position, Orientation orientation) {
        return new RobotState(position, orientation, RobotStatus.ACTIVE);
    }
    public boolean isLost() { return status == RobotStatus.LOST; }
    public RobotState withPosition(Point position) { return new RobotState(position, orientation, status); }
    public RobotState withOrientation(Orientation orientation) { return new RobotState(position, orientation, status); }
    public RobotState lost() { return new RobotState(position, orientation, RobotStatus.LOST); }
}
