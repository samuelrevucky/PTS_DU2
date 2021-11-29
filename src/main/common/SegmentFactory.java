package main.common;

import java.util.ArrayList;

public interface SegmentFactory {
    public abstract void setStops(Stops stops);
    public abstract ArrayList<LineSegment> createSegments(LineName lineName);
    //void incrementCapacity(LineSegment lineSegment, )
}
