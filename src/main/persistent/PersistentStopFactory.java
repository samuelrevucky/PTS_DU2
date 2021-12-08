package main.persistent;

import main.common.dataTypes.LineName;
import main.common.dataTypes.StopName;
import main.common.stop.Stop;
import main.common.stop.StopFactory;

import java.security.InvalidParameterException;
import java.sql.*;
import java.util.ArrayList;


public class PersistentStopFactory implements StopFactory {

    private final String databasePath;
    private Connection connection = null;

    public PersistentStopFactory(String databasePath) throws SQLException {
        this.databasePath = "jdbc:sqlite:" + databasePath;
        openConnection();
        closeConnection();
    }

    private void openConnection() throws SQLException {
        connection = DriverManager.getConnection(databasePath);
        connection.setAutoCommit(false);
    }

    private void closeConnection() throws SQLException {
        connection.close();
        connection = null;
    }

    @Override
    public Stop createStop(StopName stopName) throws InvalidParameterException, SQLException {
        openConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT line FROM stopLines WHERE stop = ?");
        statement.setString(1, stopName.getStopName());
        ResultSet rs = statement.executeQuery();
        connection.commit();
        ArrayList<LineName> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new LineName(rs.getString("line")));
        }
        closeConnection();
        return new Stop(list);
    }
}
