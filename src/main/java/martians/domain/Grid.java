package martians.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class Grid {
    private final Point upperRight;
    private final Set<Scent> scents = new HashSet<>();
    public Grid(Point upperRight) {
        if (upperRight.x() < 0 || upperRight.y() < 0) throw new IllegalArgumentException("Upper-right coordinate must be non-negative");
        this.upperRight = upperRight;
    }
    public boolean contains(Point position) {
        return position.x() >= 0 && position.y() >= 0 && position.x() <= upperRight.x() && position.y() <= upperRight.y();
    }
    public boolean hasScentAt(Point position, Orientation orientation) { return scents.contains(new Scent(position, orientation)); }
    public void leaveScentAt(Point position, Orientation orientation) { scents.add(new Scent(position, orientation)); }
    public Set<Scent> scents() { return Collections.unmodifiableSet(scents); }
}
