package main.common;

public interface LineFactory {
    public Line createLine(LineName lineName);
    public void setStops(Stops stops);

}
