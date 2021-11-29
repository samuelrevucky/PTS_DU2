package main.common.dataTypes;

public class TimeDiff {

    private final int diff;

    public TimeDiff(int diff) {
        this.diff = diff;
    }

    public TimeDiff(TimeDiff timeDiff){
        this.diff = timeDiff.getDiff();
    }

    public int getDiff() {
        return diff;
    }
}
