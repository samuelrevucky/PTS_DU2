package main.common.stop;

import main.common.dataTypes.StopName;

public interface StopGetter {
    Stop getStop(StopName stopName);
}
