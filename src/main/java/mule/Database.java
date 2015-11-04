package mule;

import java.sql.*;

/**
 * Created by david on 10/14/2015.
 */
public class Database {
    private Connection connection;

    public Database() {
        try {
            Class.forName("org.sqlite.JDBC");
            if (connection == null) {
                connection = DriverManager.getConnection("jdbc:sqlite:gamesaves.db");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error connecting to DB");
        }

    }

    public Statement getStatement() {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
        } catch (Exception e) {
            System.out.println("Could not create statement");
        }
        return stmt;
    }

    /*
        Gets the maximum (most recent) id from a table
        @param Statement statement for a database
        @param String tablename the table name to query
     */
    public int getMaxID(Statement statement, String tableName) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT id from " + tableName + " ORDER BY id DESC LIMIT 1;");
        //Get game ID
        int id = 0;
        while(rs.next()) {
            id = rs.getInt("id");
        }
        return id;
    }
}