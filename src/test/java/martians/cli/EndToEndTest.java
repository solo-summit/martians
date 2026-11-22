package martians.cli;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import martians.domain.*;
import martians.domain.command.CommandFactory;
import java.util.List;

class EndToEndTest {
    private String run(String input) {
        var output = new java.io.ByteArrayOutputStream();
        Main.run(new java.io.ByteArrayInputStream(input.getBytes(java.nio.charset.StandardCharsets.UTF_8)),
                new java.io.PrintStream(output, true, java.nio.charset.StandardCharsets.UTF_8));
        return output.toString(java.nio.charset.StandardCharsets.UTF_8).replace("\r\n", "\n");
    }

    @Test
    void processesSampleMission() {
        assertEquals("""
                1 1 E
                3 3 N LOST
                2 3 S
                """,

                run("""
                5 3
                1 1 E
                RFRFRFRF
                3 2 N
                FRRFLLFFRRFLL
                0 3 W
                LLFFFLFLFL
                """));
    }

    @Test
    void printsLostAfterPositionAndOrientation() {
        assertEquals("""
                5 3 E LOST
                """,

                run("""
                5 3
                5 3 E
                F
                """));
    }

    @Test
    void distinguishesEdges() {
        assertEquals("""
                5 3 E LOST
                5 3 E
                5 3 N LOST
                """,

                run("""
                5 3
                5 3 E
                F
                5 3 E
                F
                5 3 N
                F
                """));
    }

}
