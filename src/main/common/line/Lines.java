package main.common.line;

import main.common.dataTypes.*;
import java.sql.SQLException;
import java.util.List;
import java.util.HashMap;

public class Lines {

    private final LineFactory lineFactory;
    private final HashMap<LineName, Line> lines = new HashMap<>();

    public Lines(LineFactory lineFactory) {
        this.lineFactory = lineFactory;
    }

    public Time updateReachable(List<LineName> lineNames, StopName stop, Time time) throws SQLException {
        int lowestStartTime = Integer.MAX_VALUE;
        for(LineName x : lineNames){
            if(!lines.containsKey(x))
                lines.put(x, lineFactory.createLine(x));
            lowestStartTime = Math.min(lowestStartTime, lines.get(x).updateReachable(stop, time));
        }
        if(lowestStartTime == Integer.MAX_VALUE) return time;
        else return new Time(lowestStartTime);
    }

    public StopName updateCapacityAndGetPreviousStop(LineName lineName, StopName stopName, Time time){
        return lines.get(lineName).updateCapacityAndGetPreviousStop(stopName, time);
    }

    public void clean() throws SQLException {
        lines.clear();
        lineFactory.pushUpdates();
    }
}
