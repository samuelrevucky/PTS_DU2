package main.common;

import java.util.ArrayList;
import java.util.Stack;

public class ConnectionData {

    private String message;
    private ArrayList<Tuple<StopName, LineName, Time>> route;

    public ConnectionData(String message){
        this.message = message;
    }

    public ConnectionData(String message, Stack<Tuple<StopName, LineName, Time>> route){
        this.message = message;
        this.route = new ArrayList<>();
        while(!route.isEmpty()) this.route.add(route.pop());
    }

    public ArrayList<Tuple<StopName, LineName, Time>> getRoute(){
        return route;
    }

    public String getMessage() {
        return message;
    }
}
