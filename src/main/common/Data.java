package main.common;

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
}
