package database;

/**
 * A main class that will initialize the main connections.
 *
 * @author Thien Le
 * @author Thomas Cole
 * @lastEdited: 5/5/2017
 */
import java.sql.SQLException;

public class BoffoDatabaseAPI {

    private final String dbUrl = "jdbc://localhost:3306/SCHEMANAME";
    private ConnectionManager dbConnection;
    private Query dbQuery;

    private static BoffoDatabaseAPI instance;


    private BoffoDatabaseAPI() {
        dbConnection = new ConnectionManager();
        instance = this;
    }


    public static BoffoDatabaseAPI getInstance() {
        if (instance == null) {
            instance = new BoffoDatabaseAPI();
        }
        return instance;
    }


    public boolean dbLogin(String _uName, String _uPass) {
        if (dbConnection.connectToDB(dbUrl, "admin", "password")) {
            try {
                dbQuery = new Query(dbConnection.getConnection());
            } catch (SQLException ex) {
                System.out.println("dbQueryError :" + ex);
            }
            return true;
        } else {
            return false;
        }
    }


    public void dbLogout() {
        dbConnection.closeConnection();
    }


    public Query getQuery() {
        return dbQuery;
    }
}
