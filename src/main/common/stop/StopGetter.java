package main.common.stop;

import main.common.dataTypes.StopName;

public interface StopGetter {
    public Stop getStop(StopName stopName);
}
