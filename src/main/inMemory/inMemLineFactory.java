package main.inMemory;

import main.common.*;

import java.util.ArrayList;
import java.util.HashMap;

public class inMemLineFactory implements LineFactory{

    private final HashMap<LineName, Pair<ArrayList<Time>, StopName>> lines = new HashMap<>();
    private final SegmentFactory segmentFactory;

    public inMemLineFactory(SegmentFactory segmentFactory) {
        this.segmentFactory = segmentFactory;
    }

    public void addLineData(LineName lineName, ArrayList<Time> startTimes, StopName firstStop){
        ArrayList<Time> list = new ArrayList<>();
        for(Time x : startTimes) list.add(new Time(x));
        lines.put(new LineName(lineName), new Pair<>(list, new StopName(firstStop)));
    }

    @Override
    public Line createLine(LineName lineName) {
        return new Line(lines.get(lineName).x, segmentFactory.createSegments(lineName), lines.get(lineName).y);
    }

    @Override
    public void setStops(Stops stops) {
        segmentFactory.setStops(stops);
    }
}
