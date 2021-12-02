package main.common.line;

import main.common.stop.Stop;
import main.common.stop.StopGetter;
import main.common.dataTypes.*;

import java.util.TreeMap;

public class LineSegment {

    private final int index;
    private final TimeDiff timeToNextStop;
    private final TreeMap<Time, Integer> numberOfPassengers;
    private final int capacity;
    private final StopName nextStop;
    private final LineName lineName;
    private final StopGetter stops;
    private final LineFactory lineFactory;

    public LineSegment(int index, TimeDiff timeToNextStop, TreeMap<Time, Integer> numberOfPassengers,
                       int capacity, StopName nextStop, LineName lineName, StopGetter stops, LineFactory lineFactory) {

        this.index = index;
        this.timeToNextStop = timeToNextStop;
        this.numberOfPassengers = numberOfPassengers;
        this.capacity = capacity;
        this.lineName = lineName;
        this.nextStop = nextStop;
        this.stops = stops;
        this.lineFactory = lineFactory;
    }

    public Pair<Time, StopName> nextStop(Time startTime){
        return new Pair<>(new Time(timeToNextStop.getDiff() + numberOfPassengers.ceilingKey(startTime).getTime()), nextStop);
    }

    public StopName nextStop(){
        return nextStop;
    }

    public Tuple<Time, StopName, Boolean> nextStopAndUpdateReachable(Time startTime){
        Time time = new Time(numberOfPassengers.ceilingKey(startTime).getTime() + timeToNextStop.getDiff());
        Tuple<Time, StopName, Boolean> tuple = new Tuple<>(time, nextStop, null);
        if(numberOfPassengers.ceilingEntry(startTime).getValue() >= capacity) {
            tuple.z = false;
            return tuple;
        }
        Stop stop = stops.getStop(nextStop);
        stop.updateReachableAt(time, lineName);
        tuple.z = true;
        return tuple;
    }

    public void incrementCapacity(Time finishTime){
        Time time = new Time(finishTime.getTime() - timeToNextStop.getDiff());
        numberOfPassengers.put(time, numberOfPassengers.get(time) + 1);
        lineFactory.incrementCapacity(lineName, index, time, numberOfPassengers.get(time));
    }
}
