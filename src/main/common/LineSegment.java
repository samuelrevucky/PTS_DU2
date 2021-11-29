package main.common;

import java.util.TreeMap;

public class LineSegment {

    private final TimeDiff timeToNextStop;
    private final TreeMap<Time, Integer> numberOfPassengers;
    private final int capacity;
    private final StopName nextStop;
    private final LineName lineName;
    private final StopGetter stops;
    private final LineFactory lineFactory;

    public LineSegment(TimeDiff timeToNextStop, TreeMap<Time, Integer> numberOfPassengers,
                       int capacity, StopName nextStop, LineName lineName, StopGetter stops, LineFactory lineFactory) {
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
        Time time = numberOfPassengers.ceilingKey(startTime);
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
        //numberOfPassengers.replace(time, numberOfPassengers.get(time), numberOfPassengers.get(time) + 1);
        lineFactory.incrementCapacity(lineName, time);
    }
}
