package martians.domain;

public enum Orientation {
    NORTH(0, 1), EAST(1, 0), SOUTH(0, -1), WEST(-1, 0);
    private final int deltaX;
    private final int deltaY;
    Orientation(int deltaX, int deltaY) { this.deltaX = deltaX; this.deltaY = deltaY; }
    public Orientation turnLeft() { return values()[(ordinal() + 3) % 4]; }
    public Orientation turnRight() { return values()[(ordinal() + 1) % 4]; }
    public Point forwardFrom(Point position) { return position.moveBy(deltaX, deltaY); }
    public String symbol() { return name().substring(0, 1); }
    public static Orientation fromSymbol(String symbol) {
        return switch (symbol) {
            case "N" -> NORTH; case "E" -> EAST; case "S" -> SOUTH; case "W" -> WEST;
            default -> throw new IllegalArgumentException("Unknown orientation: " + symbol);
        };
    }
}
