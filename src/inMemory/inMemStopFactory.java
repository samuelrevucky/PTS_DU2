package inMemory;

import common.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;

public class inMemStopFactory implements StopFactory {

    HashMap<StopName, ArrayList<LineName>> stops = new HashMap<>();

    public void addStopData(StopName stopName, ArrayList<LineName> lineNames){
        stops.put(new StopName(stopName), new ArrayList<>(lineNames));
    }

    @Override
    public Stop createStop(StopName stopName) throws InvalidParameterException {
        if(!stops.containsKey(stopName)) throw new InvalidParameterException();
        return new Stop(stops.get(stopName));
    }
}
