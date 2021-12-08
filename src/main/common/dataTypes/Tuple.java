package main.common.dataTypes;

import java.util.Objects;

public class Tuple<I, II, III> {
    public I x;
    public II y;
    public III z;

    public Tuple(I x, II y, III z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tuple<?, ?, ?> tuple)) return false;
        return x.equals(tuple.x) && y.equals(tuple.y) && z.equals(tuple.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
