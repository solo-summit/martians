package martians.cli;

import martians.domain.Grid;
import martians.domain.Mission;
import martians.domain.Orientation;
import martians.domain.Point;
import martians.domain.RobotMission;
import martians.domain.RobotState;
import martians.domain.command.CommandFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class MarsMissionParser {

    private static final int MAXIMUM_COORDINATE_VALUE = 50;
    private static final int MAXIMUM_INSTRUCTION_LENGTH = 99;
    private static final String SPACE = " ";
    private static final String VALID_INSTRUCTION_CHARACTERS = "LRF";

    public Mission parse(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(
                    "Input is empty: expected grid upper-right coordinates on the first line"
            );
        }

        try (Scanner scanner = new Scanner(input)) {
            Grid grid = parseGrid(scanner.nextLine().trim());
            List<RobotMission> robotMissions = prepareRobotMissions(scanner);
            return new Mission(grid, robotMissions, new CommandFactory());
        }
    }

    private Grid parseGrid(String line) {
        int[] upperRightCoordinate = parseUpperRight(line);
        return new Grid(new Point(upperRightCoordinate[0], upperRightCoordinate[1]));
    }

    private List<RobotMission> prepareRobotMissions(Scanner scanner) {
        List<RobotMission> robotMissions = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String positionLine = nextNonBlankLine(scanner);

            if (positionLine == null) {
                break;
            }

            String instructionLine = nextNonBlankLine(scanner);

            if (instructionLine == null) {
                throw new IllegalArgumentException(
                        "Robot position must be followed by an instruction line"
                );
            }

            robotMissions.add(planRobotMission(positionLine, instructionLine));
        }

        return robotMissions;
    }

    private String nextNonBlankLine(Scanner scanner) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            if (!line.isEmpty()) {
                return line;
            }
        }

        return null;
    }

    private RobotMission planRobotMission(String positionLine, String instructionLine) {

        RobotState initialRobotPosition = parseInitialRobotPosition(positionLine);

        List<String> instructions = parseInstructions(instructionLine);

        return new RobotMission(
                initialRobotPosition,
                instructions
        );
    }

    private RobotState parseInitialRobotPosition(String positionLine) {
        String[] parts = positionLine.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException(
                    "Robot position must contain x, y, and orientation: " + positionLine
            );
        }
        int x = parseCoordinate(parts[0], positionLine);
        int y = parseCoordinate(parts[1], positionLine);

        Orientation orientation = Orientation.fromSymbol(parts[2].toUpperCase());

        return RobotState.startingAt(new Point(x, y), orientation);
    }


    private List<String> parseInstructions(String instructionLine) {

        if (instructionLine.length() > MAXIMUM_INSTRUCTION_LENGTH) {
            throw new IllegalArgumentException(
                    "Instruction string must be less than 100 characters: " + instructionLine
            );
        }

        List<String> instructions = new ArrayList<>(instructionLine.length());

        for (int index = 0; index < instructionLine.length(); index++) {
            char instruction = instructionLine.charAt(index);

            if (VALID_INSTRUCTION_CHARACTERS.indexOf(instruction) == -1) {
                throw new IllegalArgumentException(
                        "Instruction string must contain only L, R, and F: " + instructionLine
                );
            }

            instructions.add(String.valueOf(instruction));
        }
        return instructions;
    }

    private int parseCoordinate(String value, String line) {
        int coordinate;

        try {
            coordinate = Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(
                    "Invalid coordinate for position: " + line,
                    exception
            );
        }

        if (coordinate > MAXIMUM_COORDINATE_VALUE) {
            throw new IllegalArgumentException(
                    "Coordinate values must not exceed 50: " + line
            );
        }

        return coordinate;
    }

    private int[] parseUpperRight(String line) {
        int coordinatesParts = 2;
        String[] parts = line.trim().split(SPACE);

        if (parts.length != coordinatesParts) {
            throw new IllegalArgumentException(
                    "grid upper-right coordinate"
                            + " must contain "
                            + coordinatesParts
                            + " integers: "
                            + line
            );
        }

        int[] upperRightCoordinate = new int[coordinatesParts];

        for (int index = 0; index < coordinatesParts; index++) {
            upperRightCoordinate[index] = parseCoordinate(parts[index], line);
        }

        return upperRightCoordinate;
    }
}