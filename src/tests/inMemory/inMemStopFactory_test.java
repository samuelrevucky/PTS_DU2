package tests.inMemory;

import main.common.dataTypes.LineName;
import main.common.dataTypes.StopName;
import main.common.stop.Stop;
import main.inMemory.inMemStopFactory;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class inMemStopFactory_test {

    private inMemStopFactory factory;

    private void setup(){
        Map<StopName, List<LineName>> stops = new HashMap<>();
        stops.put(new StopName("Stop1"), new ArrayList<>(){{
            add(new LineName("1"));
            add(new LineName("2"));
        }});
        factory = new inMemStopFactory(stops);
    }

    @Test
    public void isStopCorrectTest(){
        setup();
        Stop stop = factory.createStop(new StopName("Stop1"));
        assertEquals(new ArrayList<LineName>(){{
            add(new LineName("1"));
            add(new LineName("2"));
        }}, stop.getLines());
    }
}
