package martians.domain;

import java.util.Objects;

public record Scent(Point position, Orientation orientation) {
    public Scent { Objects.requireNonNull(position); Objects.requireNonNull(orientation); }
}
