package main.common.line;

import main.common.stop.StopGetter;
import main.common.dataTypes.LineName;
import main.common.dataTypes.Time;

import java.sql.SQLException;

public interface LineFactory {
    Line createLine(LineName lineName) throws SQLException;
    void incrementCapacity(LineName lineName, int index, Time time, int count);
    void pushUpdates() throws SQLException;
}
