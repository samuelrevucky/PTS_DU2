package main.common;

import main.common.dataTypes.*;
import main.common.line.Lines;
import main.common.stop.Stops;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

public class ConnectionSearch {

    private final Lines lines;
    private final Stops stops;

    public ConnectionSearch(Stops stops, Lines lines) {
        this.stops = stops;
        this.lines = lines;
    }

    public ConnectionData search(StopName from, StopName to, Time when) {

        if (!stops.setStartingStop(from, when))
            return new ConnectionData("Invalid starting stop!");

        List<LineName> lineNames = stops.getLines(from);
        Time time = null;
        try {
            time = lines.updateReachable(lineNames, from, when);
        } catch (SQLException e) {
            e.printStackTrace();
            stops.clean();
            try {
                lines.clean();
            } catch (SQLException ignored) {}
            return new ConnectionData("There was a problem getting line data from/to DB!");
        }
        Optional<Pair<Time, StopName>> pair = stops.earliestReachableStopAfter(time);

        while (true) {
            if (pair.isEmpty()) {
                try {
                    lines.clean();
                } catch (SQLException ignored){}
                stops.clean();
                return new ConnectionData("There is no possible connection!");
            }
            if (pair.get().y.equals(to)) break;

            lineNames = stops.getLines(pair.get().y);
            try {
                time = lines.updateReachable(lineNames, pair.get().y, pair.get().x);
            } catch (SQLException e) {
                e.printStackTrace();
                stops.clean();
                try {
                    lines.clean();
                } catch (SQLException ignored){}
                return new ConnectionData("There was a problem getting line data from DB!");
            }
            pair = stops.earliestReachableStopAfter(time);
        }
        Stack<Tuple<StopName, LineName, Time>> stack = new Stack<>();
        Pair<Time, LineName> toPair = stops.getReachableAt(to);
        stack.push(new Tuple<>(new StopName(to), toPair.y, toPair.x));
        StopName stopName = lines.updateCapacityAndGetPreviousStop(toPair.y, to, toPair.x);

        while (!stopName.equals(from)) {
            toPair = stops.getReachableAt(stopName);
            stack.push(new Tuple<>(new StopName(stopName), toPair.y, toPair.x));
            stopName = lines.updateCapacityAndGetPreviousStop(toPair.y, stopName, toPair.x);
        }

        stack.push(new Tuple<>(new StopName(stopName), new LineName(" "), when));
        try {
            lines.clean();
        } catch (SQLException e) {
            System.out.println("Problem updating DB!");
            e.printStackTrace();
        }
        stops.clean();
        return new ConnectionData("Fastest route found successfully!", stack);
    }
}
