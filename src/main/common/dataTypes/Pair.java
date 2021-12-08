package main.common.dataTypes;

import java.util.Objects;

public class Pair<I, II> {
    public I x;
    public II y;

    public Pair(I x, II y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair<?, ?> pair)) return false;
        return x.equals(pair.x) && y.equals(pair.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
