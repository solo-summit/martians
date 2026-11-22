package martians.cli;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import martians.domain.*;
import martians.domain.command.CommandFactory;
import java.util.List;

class MarsMissionParserTest {
    private final MarsMissionParser parser = new MarsMissionParser();

    @Test
    void rejectsRobotPositionWithNonIntegerCoordinate() {
        var exception = assertThrows(IllegalArgumentException.class, () -> parser.parse("""
                5 3
                A 1 N
                F
        """));
        assertEquals("Invalid coordinate for position: A 1 N", exception.getMessage());
    }
    @Test
    void rejectsRobotPositionWithInvalidOrientation() {
        var exception = assertThrows(IllegalArgumentException.class, () -> parser.parse("""
                5 3
                1 1 X
                F
        """));
        assertEquals("Unknown orientation: X", exception.getMessage());
    }
    @Test
    void rejectsRobotPositionWithTooManyValues() {
        var exception = assertThrows(IllegalArgumentException.class, () -> parser.parse("""
                5 3
                1 1 N extra
                F
        """));
        assertEquals("Robot position must contain x, y, and orientation: 1 1 N extra", exception.getMessage());
    }
    @Test
    void rejectsRobotPositionWithTooFewValues() {
        var exception = assertThrows(IllegalArgumentException.class, () -> parser.parse("""
                5 3
                1 N
                F
        """));
        assertEquals("Robot position must contain x, y, and orientation: 1 N", exception.getMessage());
    }
    @Test
    void rejectsCoordinateAboveMaximumValue() {
        var exception = assertThrows(IllegalArgumentException.class, () -> parser.parse("""
                51 3
        """));
        assertEquals("Coordinate values must not exceed 50: 51 3", exception.getMessage());
    }
    @Test
    void rejectsInstructionWithInvalidCharacter() {
        var exception = assertThrows(IllegalArgumentException.class, () -> parser.parse("""
                5 3
                1 1 N
                LX
        """));
        assertEquals("Instruction string must contain only L, R, and F: LX", exception.getMessage());
    }
    @Test
    void rejectsEmptyInput() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse(""));
    }

    @Test
    void rejectsWhitespaceOnlyInput() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse("   "));
    }

    @Test
    void acceptsGridWithNoRobots() {
        var mission = parser.parse("""
                5 3
                """);
        assertTrue(mission.robotMissions().isEmpty());
    }

    @Test
    void acceptsMaximumCoordinateValue() {
        var mission = parser.parse("""
                50 50
                50 50 N
                F
                """);
        assertEquals(new Point(50, 50), mission.robotMissions().getFirst().initialState().position());
    }

    @Test
    void rejectsInstructionStringOf100Characters() {
        String instructions = "F".repeat(100);
        var exception = assertThrows(IllegalArgumentException.class, () -> parser.parse("""
                5 3
                1 1 N
                %s
                """.formatted(instructions)));
        assertEquals("Instruction string must be less than 100 characters: " + instructions, exception.getMessage());
    }

    @Test
    void acceptsInstructionStringOf99Characters() {
        String instructions = "F".repeat(99);
        var mission = parser.parse("""
                5 3
                1 1 N
                %s
                """.formatted(instructions));
        assertEquals(99, mission.robotMissions().getFirst().instructions().size());
    }

    @Test
    void parsesGridAndRobots() {
        var mission = parser.parse("""
                5 3
                1 1 E
                LRF
                3 2 N
                F
                """);
        assertEquals(2, mission.robotMissions().size());
    }

}
