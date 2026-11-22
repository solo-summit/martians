package martians.cli;

import martians.domain.Mission;
import martians.domain.RobotState;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

public final class Main {

    public static void main(String[] args) {
        try {
            run(System.in, System.out);
        } catch (IllegalArgumentException exception) {
            System.err.println("Error: " + exception.getMessage());
            System.exit(1);
        } catch (UncheckedIOException exception) {
            System.err.println("Error reading input: " + exception.getCause().getMessage());
            System.exit(2);
        }
    }

    static void run(InputStream input, PrintStream output) {
        String rawInput = readAll(input);
        Mission mission = new MarsMissionParser().parse(rawInput);

        for (RobotState state : mission.launch()) {
            output.println(format(state));
        }
    }

    private static String readAll(InputStream input) {
        try {
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    private static String format(RobotState state) {
        String position = "%d %d %s".formatted(
                state.position().x(),
                state.position().y(),
                state.orientation().symbol()
        );

        if (state.isLost()) {
            return position + " LOST";
        }

        return position;
    }
}