package main.common;

public class Time implements Comparable{

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
    public int compareTo(Object o) {
        return time - ((Time) o).getTime();
    }
}
