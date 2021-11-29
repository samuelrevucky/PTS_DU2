package main.common.dataTypes;

public class Time implements Comparable<Time>{

    private final int time;

    public Time(int time) {
        this.time = time;
    }

    public Time(Time time){
        this.time = time.getTime();
    }

    public int getTime() {
        return time;
    }

    @Override
    public int compareTo(Time o) {
        return time - o.getTime();
    }
}
