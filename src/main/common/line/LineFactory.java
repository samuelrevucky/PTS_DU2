package main.common.line;

import main.common.stop.StopGetter;
import main.common.dataTypes.LineName;
import main.common.dataTypes.Time;

public interface LineFactory {
    Line createLine(LineName lineName);
    void incrementCapacity(LineName lineName, Time time);
}
