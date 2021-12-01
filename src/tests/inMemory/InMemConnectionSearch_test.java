package tests.inMemory;

import main.common.ConnectionSearch;
import main.common.dataTypes.*;
import main.common.line.Lines;
import main.common.stop.Stops;
import main.inMemory.InMemLineFactory;
import main.inMemory.InMemStopFactory;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;


public class InMemConnectionSearch_test {

    private ConnectionSearch connectionSearch;
    private Map<LineName, Pair<List<Time>, StopName>> lines;
    private Map<LineName, List<Data<TimeDiff, TreeMap<Time, Integer>, Integer, StopName>>>  lineSegments;
    private Map<StopName, List<LineName>> stops;

    private void setup_lines_segments_stops(){
        lines = new HashMap<>();
        lines.put(new LineName("1"), new Pair<>(new ArrayList<>(){{
            add(new Time(1));
            add(new Time(13));
            add(new Time(20));
        }}, new StopName("Zastavka1")));
        lineSegments = new HashMap<>();
        lineSegments.put(new LineName("1"), new ArrayList<>(){{
            add(new Data<>(new TimeDiff(4), new TreeMap<>(){{
                put(new Time(1), 0);
                put(new Time(13), 0);
                put(new Time(20), 0);
            }}, 10, new StopName("Zastavka2")));
            add(new Data<>(new TimeDiff(7), new TreeMap<>(){{
                put(new Time(5), 0);
                put(new Time(17), 0);
                put(new Time(24), 0);
            }}, 10, new StopName("Zastavka3")));
        }});
        stops = new HashMap<>();
        stops.put(new StopName("Zastavka1"), new ArrayList<>(){{
            add(new LineName("1"));
        }});
        stops.put(new StopName("Zastavka2"), new ArrayList<>(){{add(new LineName("1")); add(new LineName("2"));}});
        stops.put(new StopName("Zastavka3"), new ArrayList<>(){{add(new LineName("1"));}});
        lines.put(new LineName("2"), new Pair<>(new ArrayList<>(){{
            add(new Time(3));
            add(new Time(10));
            add(new Time(20));
        }}, new StopName("Zastavka2")));
        lineSegments.put(new LineName("2"), new ArrayList<>(){{
            add(new Data<>(new TimeDiff(10), new TreeMap<>(){{
                put(new Time(3), 0);
                put(new Time(10), 0);
                put(new Time(20), 0);
            }}, 6, new StopName("Zastavka4")));
        }});
        stops.put(new StopName("Zastavka4"), new ArrayList<>(){{add(new LineName("2"));}});
    }
    private void setup_connection_search(){
        if(lineSegments == null || lines == null || stops == null) setup_lines_segments_stops();

        InMemStopFactory stopFactory = new InMemStopFactory(stops);
        Stops STOPS = new Stops(stopFactory);
        InMemLineFactory lineFactory = new InMemLineFactory(lines, lineSegments, STOPS);

        connectionSearch = new ConnectionSearch(STOPS,new Lines(lineFactory));
    }

    @Test
    public void test(){
        setup_connection_search();
        ConnectionData connectionData = connectionSearch.search(new StopName("Zastavka1"), new StopName("Zastavka4"), new Time(13));
        ArrayList<Tuple<StopName, LineName, Time>> list = new ArrayList<>();
        list.add(new Tuple<>(new StopName("Zastavka1"), new LineName(" "), new Time(13)));
        list.add(new Tuple<>(new StopName("Zastavka2"), new LineName("1"), new Time(17)));
        list.add(new Tuple<>(new StopName("Zastavka4"), new LineName("2"), new Time(30)));
        for(int i = 0; i < 3; ++i){
            assertEquals(list.get(i), connectionData.getRoute().get(i));
        }
    }
}
