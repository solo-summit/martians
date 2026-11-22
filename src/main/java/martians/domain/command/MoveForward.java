package martians.domain.command;

import martians.domain.Grid;
import martians.domain.RobotState;

public final class MoveForward implements RobotCommand {
    @Override
    public RobotState move(RobotState state, Grid grid) {
        if (state.isLost()) return state;
        var destination = state.orientation().forwardFrom(state.position());
        if (grid.contains(destination)) return state.withPosition(destination);
        if (grid.hasScentAt(state.position(), state.orientation())) return state;
        grid.leaveScentAt(state.position(), state.orientation());
        return state.lost();
    }
}
