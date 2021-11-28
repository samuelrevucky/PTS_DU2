package common;

import java.security.InvalidParameterException;

public interface StopFactory {
    public Stop createStop(StopName stopName) throws InvalidParameterException;
}
