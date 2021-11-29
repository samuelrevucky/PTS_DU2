package main.common.stop;

import main.common.dataTypes.StopName;
import java.security.InvalidParameterException;

public interface StopFactory {
    public Stop createStop(StopName stopName) throws InvalidParameterException;
}
