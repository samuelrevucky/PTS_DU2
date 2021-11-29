package main.common.dataTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ConnectionData {

    private final String message;
    private List<Tuple<StopName, LineName, Time>> route;

    public ConnectionData(String message){
        this.message = message;
    }

    public ConnectionData(String message, Stack<Tuple<StopName, LineName, Time>> route){
        this.message = message;
        this.route = new ArrayList<>();
        while(!route.isEmpty()) this.route.add(route.pop());
    }

    public List<Tuple<StopName, LineName, Time>> getRoute(){
        return route;
    }

    public String getMessage() {
        return message;
    }
}
