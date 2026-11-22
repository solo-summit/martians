package martians.domain.command;

import martians.domain.Grid;
import martians.domain.RobotState;

public sealed interface RobotCommand permits TurnLeft, TurnRight, MoveForward {
    RobotState move(RobotState state, Grid grid);
}
