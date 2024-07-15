package threadsafety.delegatingvehicletracker;

import net.jcip.annotations.Immutable;

@Immutable
public record Point(int x, int y) {
    Point() {
        this(0, 0);
    }
}
