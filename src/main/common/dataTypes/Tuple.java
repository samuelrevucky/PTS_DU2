package main.common.dataTypes;

public class Tuple<I, II, III> {
    public I x;
    public II y;
    public III z;

    public Tuple(I x, II y, III z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
