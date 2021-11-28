package inMemory;

import common.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class inMemSegmentFactory implements SegmentFactory {

    private static class Data<I, II, III, IV>{
        private final I x;
        private final II y;
        private final III w;
        private final IV z;

        private Data(I x, II y, III w, IV z){
            this.x = x;
            this.y = y;
            this.w = w;
            this.z = z;
        }
    }

    private StopGetter stops;

    private final HashMap<LineName, ArrayList<Data<TimeDiff, TreeMap<Time, Integer>, Integer, StopName>>> segments = new HashMap<>();

    public void addSegmentData(LineName lineName, TimeDiff timeToNextStop, TreeMap<Time, Integer> numberOfPassengers,
                               int capacity, StopName nextStop){
        if(!segments.containsKey(lineName))
        segments.put(new LineName(lineName), new ArrayList<>(){{
            add(new Data<>(
                    new TimeDiff(timeToNextStop),
                    new TreeMap<>(numberOfPassengers),
                    capacity,
                    new StopName(nextStop)
            ));
        }});
        else{
            segments.get(lineName).add(new Data<>(
                    new TimeDiff(timeToNextStop),
                    new TreeMap<>(numberOfPassengers),
                    capacity,
                    new StopName(nextStop)
            ));
        }
    }

    @Override
    public void setStops(Stops stops) {
        this.stops = stops;
    }

    @Override
    public ArrayList<LineSegment> createSegments(LineName lineName) {
        if(stops == null) throw new RuntimeException("Stops instance has to be assigned to segment " +
                "factory before creating segments!");
        ArrayList<LineSegment> list = new ArrayList<>();
        for(Data<TimeDiff, TreeMap<Time, Integer>, Integer, StopName> x : segments.get(lineName)){
            list.add(new LineSegment(x.x, x.y, x.w, new LineName(lineName), x.z, stops, this));
        }
        return list;
    }
}
