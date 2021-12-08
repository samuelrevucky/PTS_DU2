package main.common.dataTypes;

import java.util.Objects;

public class StopName {

    private final String stopName;

    public StopName(String stopName) {
        this.stopName = stopName;
    }

    public StopName(StopName stopName) {
        this.stopName = stopName.getStopName();
    }

    public String getStopName() {
        return stopName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StopName stopName1 = (StopName) o;
        return stopName.equals(stopName1.stopName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stopName);
    }
}
