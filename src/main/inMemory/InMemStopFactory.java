package main.inMemory;

import main.common.dataTypes.LineName;
import main.common.dataTypes.StopName;
import main.common.stop.Stop;
import main.common.stop.StopFactory;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemStopFactory implements StopFactory {

    private final Map<StopName, List<LineName>> stops;

    public InMemStopFactory(Map<StopName, List<LineName>> stops) {
        this.stops = new HashMap<>(stops);
    }

    @Override
    public Stop createStop(StopName stopName) throws InvalidParameterException {
        if (!stops.containsKey(stopName)) throw new InvalidParameterException();
        return new Stop(stops.get(stopName));
    }
}
