package tests.inMemory;

import main.common.dataTypes.*;
import main.common.line.Line;
import main.common.stop.Stop;
import main.common.stop.StopGetter;
import main.inMemory.InMemLineFactory;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;


public class InMemLineFactoryTest {

    private InMemLineFactory factory;
    private Map<LineName, Pair<List<Time>, StopName>> lines;
    private Map<LineName, List<Data<TimeDiff, TreeMap<Time, Integer>, Integer, StopName>>> lineSegments;

    private void setup() {
        lines = new HashMap<>();
        lineSegments = new HashMap<>();
        lines.put(new LineName("1"), new Pair<>(new ArrayList<>() {{
            add(new Time(1));
            add(new Time(13));
            add(new Time(20));
        }}, new StopName("Zastavka1")));
        lineSegments.put(new LineName("1"), new ArrayList<>() {{
            add(new Data<>(new TimeDiff(4), new TreeMap<>() {{
                put(new Time(1), 0);
                put(new Time(13), 0);
                put(new Time(20), 0);
            }}, 10, new StopName("Zastavka2")));
            add(new Data<>(new TimeDiff(7), new TreeMap<>() {{
                put(new Time(5), 0);
                put(new Time(17), 0);
                put(new Time(24), 0);
            }}, 10, new StopName("Zastavka3")));
        }});
        factory = new InMemLineFactory(lines, lineSegments, new FakeGetter());
    }

    @Test
    public void isLineCorrectTest() {
        setup();
        Line line = factory.createLine(new LineName("1"));
        assertEquals("Zastavka2", line.updateCapacityAndGetPreviousStop(new StopName("Zastavka3"), new Time(30)).getStopName());
        lines.clear();
        lineSegments.clear();
        Line line1 = factory.createLine(new LineName("1"));
        assertEquals("Zastavka2", line1.updateCapacityAndGetPreviousStop(new StopName("Zastavka3"), new Time(30)).getStopName());
    }

    private static class FakeGetter implements StopGetter {

        @Override
        public Stop getStop(StopName stopName) {
            return null;
        }
    }
}
