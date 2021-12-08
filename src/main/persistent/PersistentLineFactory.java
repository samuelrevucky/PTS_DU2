package main.persistent;

import main.common.dataTypes.LineName;
import main.common.dataTypes.StopName;
import main.common.dataTypes.Time;
import main.common.dataTypes.TimeDiff;
import main.common.line.Line;
import main.common.line.LineFactory;
import main.common.line.LineSegment;
import main.common.stop.StopGetter;

import java.sql.*;
import java.util.ArrayList;
import java.util.TreeMap;

public class PersistentLineFactory implements LineFactory {

    private final String databasePath;
    private final StopGetter stopGetter;
    private final ArrayList<String> queries = new ArrayList<>();
    private Connection connection = null;

    public PersistentLineFactory(String databasePath, StopGetter stopGetter) throws SQLException {
        this.databasePath = "jdbc:sqlite:" + databasePath;
        this.stopGetter = stopGetter;
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
    public Line createLine(LineName lineName) throws SQLException {

        openConnection();
        PreparedStatement getFirstStop = connection.prepareStatement("SELECT firstStop FROM lines WHERE name = ?");
        getFirstStop.setString(1, lineName.getLineName());

        StopName firstStop = new StopName(getFirstStop.executeQuery().getString("firstStop"));

        PreparedStatement getLineSegments = connection.prepareStatement(
                "SELECT * FROM lineSegments WHERE lineName = ? ORDER BY sIndex ASC");
        getLineSegments.setString(1, lineName.getLineName());
        ResultSet rs = getLineSegments.executeQuery();

        ArrayList<Time> startingTimes = new ArrayList<>();
        ArrayList<LineSegment> lineSegments = new ArrayList<>();

        while (rs.next()) {
            TimeDiff timeToNextStop = new TimeDiff(rs.getInt("duration"));
            int capacity = rs.getInt("capacity");
            StopName nextStop = new StopName(rs.getString("nextStop"));
            int index = rs.getInt("sIndex");

            PreparedStatement getNumOfPassengers = connection.prepareStatement(
                    "SELECT time, pCount FROM passengers WHERE lineName = ? AND sIndex = ? ORDER BY time ASC");
            getNumOfPassengers.setString(1, lineName.getLineName());
            getNumOfPassengers.setString(2, String.valueOf(index));
            ResultSet rs1 = getNumOfPassengers.executeQuery();

            TreeMap<Time, Integer> numberOfPassengers = new TreeMap<>();

            while (rs1.next()) {
                numberOfPassengers.put(new Time(rs1.getInt("time")), rs1.getInt("pCount"));
            }
            if (index == 0) startingTimes = new ArrayList<>(numberOfPassengers.keySet());
            lineSegments.add(new LineSegment(index, timeToNextStop, numberOfPassengers, capacity, nextStop,
                    new LineName(lineName), stopGetter, this));
        }
        connection.commit();
        closeConnection();
        return new Line(startingTimes, lineSegments, firstStop);
    }

    @Override
    public void incrementCapacity(LineName lineName, int index, Time time, int count) {
        queries.add("UPDATE passengers SET pCount = " + count + " WHERE lineName = '" + lineName.getLineName() +
                "' AND sIndex = " + index + " AND time = " + time.getTime() + ";");
    }

    @Override
    public void pushUpdates() throws SQLException {
        if (!queries.isEmpty()) {
            openConnection();
            for (String x : queries) {
                connection.createStatement().executeUpdate(x);
            }
            connection.commit();
            closeConnection();
        }
    }
}
