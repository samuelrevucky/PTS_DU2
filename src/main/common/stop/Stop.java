package main.common.stop;

import main.common.dataTypes.LineName;
import main.common.dataTypes.Pair;
import main.common.dataTypes.Time;
import java.util.List;

public class Stop {

    private Time reachableAt;
    private LineName reachableVia;
    private final List<LineName> lines;

    public Stop(List<LineName> lines) {
        this.lines = lines;
    }

    public void updateReachableAt(Time time, LineName line){
        if(reachableAt == null || time.compareTo(reachableAt) < 0) {
            reachableAt = time;
            reachableVia = line;
        }
    }

    public Pair<Time, LineName> getReachableAt() {
        return new Pair<>(reachableAt, reachableVia);
    }

    public List<LineName> getLines(){
        return List.copyOf(lines);
    }
}
