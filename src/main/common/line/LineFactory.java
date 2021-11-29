package main.common.line;

import main.common.stop.StopGetter;
import main.common.dataTypes.LineName;
import main.common.dataTypes.Time;

public interface LineFactory {
    public Line createLine(LineName lineName, StopGetter stopGetter);
    public void incrementCapacity(LineName lineName, Time time);
}
