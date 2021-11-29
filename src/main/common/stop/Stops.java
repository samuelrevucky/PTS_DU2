package main.common.stop;

import main.common.dataTypes.*;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Stops implements StopGetter{

    private final StopFactory stopFactory;
    private final HashMap<StopName, Stop> stops = new HashMap<>();

    public Stops(StopFactory stopFactory) {
        this.stopFactory = stopFactory;
    }

    @Override
    public Stop getStop(StopName stopName){
        if(!stops.containsKey(stopName)){
            Stop stop = stopFactory.createStop(stopName);
            stops.put(stopName, stop);
            return stop;
        }
        return stops.get(stopName);
    }

    public Optional<Pair<Time, StopName>> earliestReachableStopAfter(Time time){
        StopName bestStopSoFar = null;
        int bestTimeSoFar = Integer.MAX_VALUE;
        for(Map.Entry<StopName, Stop> x : stops.entrySet()){
            int tmp = x.getValue().getReachableAt().x.getTime();
            if(time.getTime() < tmp && tmp < bestTimeSoFar){
                bestTimeSoFar = tmp;
                bestStopSoFar = x.getKey();
            }
        }
        if(bestStopSoFar == null) return Optional.empty();
        return Optional.of(new Pair<>(new Time(bestTimeSoFar), bestStopSoFar));
    }

    public boolean setStartingStop(StopName stopName, Time time){
        Stop stop;
        try{
            stop = stopFactory.createStop(stopName);
            stops.put(stopName, stop);
        } catch (InvalidParameterException e){
            return false;
        }
        stop.updateReachableAt(time, null);
        return true;
    }

    public Pair<Time, LineName> getReachableAt(StopName stop){
        return stops.get(stop).getReachableAt();
    }

    public List<LineName> getLines(StopName stop){
        return stops.get(stop).getLines();
    }

}
