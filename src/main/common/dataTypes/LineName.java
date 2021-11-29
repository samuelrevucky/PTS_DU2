package main.common.dataTypes;

import java.util.Objects;

public class LineName {

    private final String lineName;

    public LineName(String lineName) {
        this.lineName = lineName;
    }

    public LineName(LineName lineName){
        this.lineName = lineName.getLineName();
    }

    public String getLineName() {
        return lineName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineName lineName1 = (LineName) o;
        return lineName.equals(lineName1.lineName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineName);
    }
}
