package database;

/**
 * A class that connect to the Database.
 *
 * @author Thien Le
 * @author Thomas Cole
 * @lastEdited: 5/5/2017
 */
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class ConnectionManager {

    private Connection dbConnection;


    public ConnectionManager() {

    }


    /**
     * Connect to the DataBase
     * @param _url, username, password
     * @return true if the connection is successful and return false if otherwise
     */
    public boolean connectToDB(String _url, String _uName, String _uPass) {
        try {
            dbConnection = DriverManager.getConnection(_url, _uName, _uPass);
            System.out.println("Connection Successful.");
            return true;
        } catch (SQLException e) {
            System.out.print("Something went wrong trying to connect. " + e);
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
