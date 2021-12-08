package tests.persistent;

import main.common.ConnectionSearch;
import main.common.dataTypes.*;
import main.common.line.Lines;
import main.common.stop.Stops;
import main.persistent.PersistentLineFactory;
import main.persistent.PersistentStopFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PersistentConnectionSearchTest {

    private ConnectionSearch connectionSearch;
    private Connection c;

    @Before
    public void setup() throws SQLException, IOException {
        c = DriverManager.getConnection("jdbc:sqlite:test.db");
        ScriptRunner scriptRunner = new ScriptRunner(c, false, true);
        scriptRunner.runScript(new FileReader("src/tests/persistent/datDef.sql"));
        scriptRunner.runScript(new FileReader("src/tests/persistent/testDataInsertion.sql"));
        Stops stops = new Stops(new PersistentStopFactory("test.db"));
        connectionSearch = new ConnectionSearch(stops, new Lines(new PersistentLineFactory("test.db", stops)));
    }

    @Test
    public void test() throws SQLException {
        ConnectionData connectionData = connectionSearch.search(new StopName("STOP1"), new StopName("STOP4"), new Time(13));
        ArrayList<Tuple<StopName, LineName, Time>> list = new ArrayList<>();
        list.add(new Tuple<>(new StopName("STOP1"), new LineName(" "), new Time(13)));
        list.add(new Tuple<>(new StopName("STOP2"), new LineName("LINE1"), new Time(17)));
        list.add(new Tuple<>(new StopName("STOP4"), new LineName("LINE2"), new Time(30)));
        for (int i = 0; i < 3; ++i) {
            assertEquals(list.get(i), connectionData.getRoute().get(i));
        }

        assertEquals(c.createStatement().executeQuery(
                "SELECT pCount FROM passengers WHERE lineName = 'LINE1' AND sIndex = 0 AND time = 13;").getInt("pCount"), 1);
        assertEquals(c.createStatement().executeQuery(
                "SELECT pCount FROM passengers WHERE lineName = 'LINE2' AND sIndex = 0 AND time = 20;").getInt("pCount"), 1);
    }


    @After
    public void end() throws SQLException, IOException {
        c.close();
        Files.delete(Path.of("test.db"));
    }
}
