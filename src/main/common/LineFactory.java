package main.common;

public interface LineFactory {
    public Line createLine(LineName lineName, StopGetter stopGetter);
    public void incrementCapacity(LineName lineName, Time time);
}
