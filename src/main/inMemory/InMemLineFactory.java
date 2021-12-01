package main.inMemory;

import main.common.dataTypes.*;
import main.common.line.*;
import main.common.stop.StopGetter;
import java.util.*;

public class InMemLineFactory implements LineFactory {

    private final Map<LineName, Pair<List<Time>, StopName>> lines;
    private final Map<LineName, List<Data<TimeDiff, TreeMap<Time, Integer>, Integer, StopName>>>  lineSegments;
    private final StopGetter stopGetter;
    public InMemLineFactory(Map<LineName, Pair<List<Time>, StopName>> lines, Map<LineName, List<Data<TimeDiff,
            TreeMap<Time, Integer>, Integer, StopName>>> lineSegments, StopGetter stopGetter) {
        this.lines = Collections.unmodifiableMap(lines);
        this.lineSegments = Collections.unmodifiableMap(lineSegments);
        this.stopGetter = stopGetter;
    }

    @Override
    public Line createLine(LineName lineName) {
        List<Data<TimeDiff, TreeMap<Time, Integer>, Integer, StopName>> value = lineSegments.get(lineName);
        List<LineSegment> segments = new ArrayList<>();
        for(Data<TimeDiff, TreeMap<Time, Integer>, Integer, StopName> x : value){
            segments.add(new LineSegment(x.x, x.y, x.w, x.z, new LineName(lineName), stopGetter, this));
        }
        return new Line(lines.get(lineName).x, segments, lines.get(lineName).y);
    }

    @Override
    public void incrementCapacity(LineName lineName, Time time) {}
}
