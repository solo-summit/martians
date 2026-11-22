package martians.domain;

import martians.domain.command.CommandFactory;

import java.util.List;

public final class Robot {

    private final Grid grid;
    private final CommandFactory commandFactory;
    private RobotState state;

    public Robot(Grid grid, RobotState initialState, CommandFactory commandFactory) {
        this.grid = grid;
        this.commandFactory = commandFactory;
        this.state = initialState;
    }

    public RobotState state() {
        return state;
    }

    public void invoke(List<String> instructions) {
        for (String symbol : instructions) {
            if (state.isLost()) {
                return;
            }

            state = commandFactory.create(symbol).move(state, grid);
        }
    }
}