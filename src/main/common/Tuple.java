package main.common;

public class Tuple<I, II, III> {
    I x;
    II y;
    III z;

    public Tuple(I x, II y, III z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
