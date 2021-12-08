package tests.persistent;

import main.common.dataTypes.LineName;
import main.common.dataTypes.StopName;
import main.common.stop.Stop;
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
import java.sql.Statement;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class PersistentStopFactoryTest {

    private PersistentStopFactory factory;
    private Connection c;

    @Before
    public void setup() throws SQLException, IOException {
        c = DriverManager.getConnection("jdbc:sqlite:test.db");
        ScriptRunner scriptRunner = new ScriptRunner(c, false, true);
        scriptRunner.runScript(new FileReader("src/tests/persistent/datDef.sql"));
        factory = new PersistentStopFactory("test.db");
    }

    @Test
    public void correctStopCreatedTest() throws SQLException {
        Statement stmt = c.createStatement();
        String sql = "INSERT INTO stops VALUES ('STOP1');" +
                "INSERT INTO stopLines VALUES ('STOP1', 'LINE1'), ('STOP1', 'LINE2');";
        stmt.executeUpdate(sql);
        Stop stop = factory.createStop(new StopName("STOP1"));
        assertEquals(new HashSet<LineName>() {{
                         add(new LineName("LINE1"));
                         add(new LineName("LINE2"));
                     }},
                new HashSet<>(stop.getLines()));
    }

    @After
    public void end() throws SQLException, IOException {
        c.close();
        Files.delete(Path.of("test.db"));
    }

}
