# Martian Mission

Java 25 CLI simulation of robots on a bounded Martian grid.

## Build

```bash
mvn clean package
```

## Run

### With input file
Best for sample and custom input. Pass input file 'sample-input.txt' with sample input data or custom input data.
File with sample data can be found in (src/main/resources/sample-input.txt) and should be outside 'target' folder.
e.g. folder structure:
martians/
- target/martians-1.0.0.jar
- sample-input.txt




```bash
java -jar target/martians-1.0.0.jar < sample-input.txt
```
### Command line only:
With sample input
```bash
java -jar target/martians-1.0.0.jar <<EOF
5 3
1 1 E
RFRFRFRF
3 2 N
FRRFLLFFRRFLL
0 3 W
LLFFFLFLFL
EOF
```

## Test

### All Test Cases

```bash
mvn test
```

### End to End Test
Contains 
```bash
martians.cli.EndToEndTest
```

## Structure

- `martians.cli`: input parsing and console output
- `martians.domain`: mission domain orchestration and simulation
- `martians.domain.command`: individual robot instructions

## Input handling

- Empty or whitespace-only input is rejected with a clear error:
```text
  Error: Input is empty: expected grid upper-right coordinates on the first line
```
- Input is read as UTF-8 using `InputStream.readAllBytes()`.
- A grid with no robots is valid and produces no output.
- The maximum value for any grid or robot coordinate is 50.
- A robot position must contain exactly three space-separated values: two integer coordinates followed by `N`, `S`, `E`, or `W`.
- A robot instruction must contain only `L`, `R`, and `F`.
- A robot instruction string must contain fewer than 100 characters.

## Assumptions

- Lower-left coordinate is `(0, 0)`;
- Upper-right coordinate is inclusive.
- White space is defined as space character only (" ").
- The number of position and instruction pairs matches; each robot initial position is followed by an instruction line.
- Robots run sequentially.
- `L` and `R` rotate without moving; `F` moves one square.
- A robot leaving the grid is `LOST` and leaves a scent at its final valid position.
- A later robot ignores an off-grid `F` only when both its position and orientation match an existing scent.
- A scent applies only to the exact position and orientation at which a robot was lost. It protects against the same edge, not a different edge.
- Once `LOST`, a robot ignores remaining instructions.

## Possible future improvements

- Robots can move simultaneously
## Help
Please let me know if you experience any issues running the Martian mission simulation or if you have any questions :)
