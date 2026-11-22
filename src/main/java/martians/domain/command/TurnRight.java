package martians.domain.command;

import martians.domain.Grid;
import martians.domain.RobotState;

public final class TurnRight implements RobotCommand {
    @Override
    public RobotState move(RobotState state, Grid grid) {
        if (state.isLost()) return state;
        return state.withOrientation(state.orientation().turnRight());
    }
}
