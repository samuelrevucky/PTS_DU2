package common;

public class StopName {

    private final String stopName;

    public StopName(String stopName) {
        this.stopName = stopName;
    }

    public StopName(StopName stopName){
        this.stopName = stopName.getStopName();
    }

    public String getStopName() {
        return stopName;
    }
}
