package main.common;

import java.util.List;
import java.util.ArrayList;

public class Stop {

    private Time reachableAt;
    private LineName reachableVia;
    private final ArrayList<LineName> lines;

    public Stop(ArrayList<LineName> lines) {
        this.lines = new ArrayList<>(lines);
    }

    public void updateReachableAt(Time time, LineName line){
        reachableAt = time;
        reachableVia = line;
    }

    public Pair<Time, LineName> getReachableAt() {
        return new Pair<>(reachableAt, reachableVia);
    }

    public List<LineName> getLines(){
        return List.copyOf(lines);
    }
}
