package main.common.dataTypes;

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
}
