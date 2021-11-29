package main.common;

import java.util.List;

public class Line {

    private final List<Time> startTimes;
    private final List<LineSegment> lineSegments;
    private final StopName firstStop;

    public Line(List<Time> startingTimes, List<LineSegment> lineSegments, StopName firstStop) {
        this.startTimes = startingTimes;
        this.lineSegments = lineSegments;
        this.firstStop = firstStop;
    }

    public int updateReachable(StopName stop, Time time){

        int result = Integer.MAX_VALUE;

        for(int i = startTimes.size() - 1; i >= 0; --i) {

            Time startTime = startTimes.get(i);
            if (startTime.getTime() < time.getTime()) break;

            int startingStopIndex = 0;

            if(!stop.getStopName().equals(firstStop.getStopName())){
                for (int j = 0; j < lineSegments.size(); ++j) {
                    Pair<Time, StopName> pair = lineSegments.get(j).nextStop(startTime);
                    startTime = (pair.x);
                    if(pair.y.getStopName().equals(stop.getStopName())){
                        startingStopIndex = j;
                        break;
                    }
                }
            }

            for (int j = startingStopIndex; j < lineSegments.size(); ++j) {
                Tuple<Time, StopName, Boolean> tuple = lineSegments.get(j).nextStopAndUpdateReachable(startTime);
                if(j == startingStopIndex){
                    if(tuple.z) result = startTime.getTime();
                    else break;
                }
                startTime = tuple.x;
            }
        }
        return result;
    }

    public StopName updateCapacityAndGetPreviousStop(StopName stopName, Time time){
        for(int i = lineSegments.size() - 1; i >= 0; --i){
            if(lineSegments.get(i).nextStop().getStopName().equals(stopName.getStopName())) {
                lineSegments.get(i).incrementCapacity(time);
                if(i > 0) return lineSegments.get(i - 1).nextStop();
                else return firstStop;
            }
        }
        return firstStop;
    }
}
