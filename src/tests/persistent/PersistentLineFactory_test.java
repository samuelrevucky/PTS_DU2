package tests.persistent;

import main.common.dataTypes.LineName;
import main.common.dataTypes.StopName;
import main.common.dataTypes.Time;
import main.common.line.Line;
import main.common.stop.Stop;
import main.common.stop.StopGetter;
import main.persistent.PersistentLineFactory;
import main.persistent.PersistentStopFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class PersistentLineFactory_test {

    private static class FakeGetter implements StopGetter {

        @Override
        public Stop getStop(StopName stopName) {
            return null;
        }
    }

    PersistentLineFactory factory;
    Connection c;

    @Before
    public void setup() throws SQLException, IOException {
        c = DriverManager.getConnection("jdbc:sqlite:test.db");
        ScriptRunner scriptRunner = new ScriptRunner(c, false, true);
        scriptRunner.runScript(new FileReader("src/tests/persistent/datDef.sql"));
        scriptRunner.runScript(new FileReader("src/tests/persistent/testDataInsertion.sql"));
        factory = new PersistentLineFactory("test.db", new FakeGetter());
    }

    @Test
    public void isLineCorrectTest() throws SQLException {
        Line line = factory.createLine(new LineName("LINE1"));
        assertEquals("STOP2", line.updateCapacityAndGetPreviousStop(new StopName("STOP3"), new Time(30)).getStopName());
    }

    @After
    public void end() throws SQLException, IOException {
        c.close();
        Files.delete(Path.of("test.db"));
    }
}
