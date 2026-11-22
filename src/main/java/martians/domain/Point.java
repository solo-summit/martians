package martians.domain;

public record Point(int x, int y) {
    public Point moveBy(int deltaX, int deltaY) {
        return new Point(x + deltaX, y + deltaY);
    }
}
