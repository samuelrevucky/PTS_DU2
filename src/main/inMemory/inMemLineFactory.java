package main.inMemory;

import main.common.*;

import java.util.*;

public class inMemLineFactory implements LineFactory{

    private final Map<LineName, Pair<List<Time>, StopName>> lines;
    private final Map<LineName, List<Data<TimeDiff, TreeMap<Time, Integer>, Integer, StopName>>>  lineSegments;

    public inMemLineFactory(Map<LineName, Pair<List<Time>, StopName>> lines, Map<LineName, List<Data<TimeDiff,
            TreeMap<Time, Integer>, Integer, StopName>>>  lineSegments ) {
        this.lines = Collections.unmodifiableMap(lines);
        this.lineSegments = Collections.unmodifiableMap(lineSegments);
    }
/*
    public void addLineData(LineName lineName, List<Time> startTimes, StopName firstStop){
        ArrayList<Time> list = new ArrayList<>();
        for(Time x : startTimes) list.add(new Time(x));
        lines.put(new LineName(lineName), new Pair<>(list, new StopName(firstStop)));
    }
*/
    @Override
    public Line createLine(LineName lineName, StopGetter stopGetter) {
        List<Data<TimeDiff, TreeMap<Time, Integer>, Integer, StopName>> value = lineSegments.get(lineName);
        List<LineSegment> segments = new ArrayList<>();
        for(Data<TimeDiff, TreeMap<Time, Integer>, Integer, StopName> x : value){
            segments.add(new LineSegment(x.x, x.y, x.w, x.z, new LineName(lineName), stopGetter, this));
        }
        return new Line(lines.get(lineName).x, segments, lines.get(lineName).y);
    }

    @Override
    public void incrementCapacity(LineName lineName, Time time) {

    }
}
