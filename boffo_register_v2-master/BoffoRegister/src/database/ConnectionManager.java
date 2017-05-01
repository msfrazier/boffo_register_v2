package database;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class ConnectionManager {

    private Connection dbConnection;

    public ConnectionManager() {

    }

    /**
     * Connect to the DataBase
     * @param url, username, password
     * @return true if the connection is successful and return false if otherwise
     */
    public boolean connectToDB(String url, String uName, String uPass) {
        try {
            dbConnection = DriverManager.getConnection(url, uName, uPass);
            System.out.println("Connection Successful.");
            return true;
        } catch(SQLException e) {
            System.out.print("Something went wrong trying to connect. "+e);
            return false;
        }
    }

    /**
     * Close the DataBase
     * @param
     * @return null
     */
    public void closeConnection() {
        try {
            dbConnection.close();
            System.out.println("Connection closed.");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    /**
     *
     * @param null
     * @return Connection
     */
    public Connection getConnection() {
        return dbConnection;
    }

}
