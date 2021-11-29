package main.common;

import java.util.List;
import java.util.Optional;
import java.util.Stack;

public class ConnectionSearch {

    private final Lines lines;
    private final Stops stops;

    private ConnectionSearch(LineFactory lineFactory, StopFactory stopFactory) {
        stops = new Stops(stopFactory);
        lines = new Lines(lineFactory, stops);
    }

    private ConnectionData search(StopName from, StopName to, Time when) {
        if (!stops.setStartingStop(from, when)) return new ConnectionData("Invalid starting stop!");

        List<LineName> lineNames = stops.getLines(from);
        Time time = lines.updateReachable(lineNames, from, when);
        Optional<Pair<Time, StopName>> pair = stops.earliestReachableStopAfter(time);

        while (true) {
            if (pair.isEmpty()) {
                lines.clean();
                return new ConnectionData("There is no possible connection!");
            }
            if (pair.get().y.getStopName().equals(to.getStopName())) break;
            lineNames = stops.getLines(pair.get().y);
            time = lines.updateReachable(lineNames, pair.get().y, pair.get().x);
            pair = stops.earliestReachableStopAfter(time);
        }

        Stack<Tuple<StopName, LineName, Time>> stack = new Stack<>();
        Pair<Time, LineName> toPair = stops.getReachableAt(to);
        stack.push(new Tuple<>(new StopName(to), toPair.y, toPair.x));
        StopName stopName = lines.updateCapacityAndGetPreviousStop(toPair.y, to, toPair.x);

        while (!stopName.getStopName().equals(from.getStopName())) {
            toPair = stops.getReachableAt(stopName);
            stack.push(new Tuple<>(new StopName(stopName), toPair.y, toPair.x));
            stopName = lines.updateCapacityAndGetPreviousStop(toPair.y, stopName, toPair.x);
        }

        stack.push(new Tuple<>(new StopName(stopName), null, when));
        lines.clean();
        return new ConnectionData("Fastest route found successfully!", stack);
    }
}
