package main.common.stop;

import main.common.dataTypes.StopName;
import java.security.InvalidParameterException;
import java.sql.SQLException;

public interface StopFactory {
    Stop createStop(StopName stopName) throws InvalidParameterException, SQLException;
}
