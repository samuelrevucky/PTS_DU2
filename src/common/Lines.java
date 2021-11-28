package common;

import java.util.List;
import java.util.HashMap;

public class Lines {

    private final LineFactory lineFactory;
    private final HashMap<LineName, Line> lines = new HashMap<>();

    public Lines(LineFactory lineFactory) {
        this.lineFactory = lineFactory;
    }

    public Time updateReachable(List<LineName> lineNames, StopName stop, Time time) {
        int lowestStartTime = Integer.MAX_VALUE;
        for(LineName x : lineNames){
            if(!lines.containsKey(x)) {
                lines.put(x, lineFactory.createLine(x));
                lowestStartTime = Math.min(lowestStartTime, lines.get(x).updateReachable(stop, time));
            }
        }
        if(lowestStartTime == Integer.MAX_VALUE) return new Time(-1);
        else return new Time(lowestStartTime);
    }

    public StopName updateCapacityAndGetPreviousStop(LineName lineName, StopName stopName, Time time){
        return lines.get(lineName).updateCapacityAndGetPreviousStop(stopName, time);
    }

    public void clean() {
        lines.clear();
    }
}
