package main.common.dataTypes;

import java.util.Objects;

public class Time implements Comparable<Time> {

    private final Integer time;

    public Time(Integer time) {
        this.time = time;
    }

    public Time(Time time) {
        this.time = time.getTime();
    }

    public Integer getTime() {
        return time;
    }

    @Override
    public int compareTo(Time o) {
        return time - o.getTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Time time1 = (Time) o;
        return time.equals(time1.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time);
    }
}
