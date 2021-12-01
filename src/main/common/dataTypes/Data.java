package main.common.dataTypes;

import java.util.Objects;

public class Data<I, II, III, IV>{

    public final I x;
    public final II y;
    public final III w;
    public final IV z;

    public Data(I x, II y, III w, IV z) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Data<?, ?, ?, ?> data)) return false;
        return Objects.equals(x, data.x) && Objects.equals(y, data.y) && Objects.equals(w, data.w) && Objects.equals(z, data.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, w, z);
    }
}
