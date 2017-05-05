package database;

/**
 * A class that handles with the query using JDBC.
 *
 * @author Thien Le
 * @author Thomas Cole
 * @lastEdited: 5/5/2017
 */
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class Query {

    private final Statement statement;


    public Query(Connection _dbConnection) throws SQLException {
        statement = _dbConnection.createStatement();
    }


    public ResultSet executeQuery(String _query) throws SQLException {
        return statement.executeQuery(_query);
    }


    public ResultSetMetaData getMetaData(ResultSet _rs) throws SQLException {
        return _rs.getMetaData();
    }


    /**
     * Method to get the column names from the a database table.
     *
     * @return ArrayList<String> with all the column names.
     */
    public ArrayList<String> getTableColumns(String _tName) {
        ArrayList<String> retList = new ArrayList<String>();

        try {
            ResultSetMetaData rsmd = getMetaData(executeQuery("SELECT * FROM " + _tName));

            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                retList.add(rsmd.getColumnName(i));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return retList;
    }


    public void executeUpdate(String _query) throws SQLException {
        statement.executeUpdate(_query);
    }


    /**
     * A method that selecting information from the table without a filter.
     *
     * @param _tField
     * @param _tName
     * @return ArrayList with all the values.
     * @throws java.sql.SQLException
     */
    public ArrayList<String> selectFromTable(String _tField, String _tName) throws SQLException {

        ResultSet rs = statement.executeQuery("SELECT " + _tField + " FROM " + _tName);
        ResultSetMetaData rsmd = this.getMetaData(rs);

        ArrayList<String> retString = new ArrayList<String>();
        String tableEntry = "";

        while (rs.next()) {
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                if (i != rsmd.getColumnCount()) {
                    tableEntry += rs.getString(i) + ",";
                } else {
                    tableEntry += rs.getString(i);
                }

            }

            retString.add(tableEntry);
            tableEntry = "";
        }
        return retString;
    }


    /**
     * A overloaded method that select from the table with a filter.
     *
     * @param _tField
     * @param _tName
     * @param _tFilterField
     * @param _operator
     * @param _tFilter
     * @return ArrayList<String> with the values from the database.
     * @throws java.sql.SQLException
     */
    public ArrayList<String> selectFromTable(String _tField, String _tName, String _tFilterField,
            String _operator, String _tFilter) throws SQLException {
        System.out.println("Executing: SELECT " + _tField + " FROM " + _tName + " WHERE "
                + _tFilterField + " " + _operator + " '" + _tFilter + "'");

        ResultSet rs = statement.executeQuery("SELECT " + _tField + " FROM "
                + _tName + " WHERE " + _tFilterField + " " + _operator + " '" + _tFilter + "'");;
        ResultSetMetaData rsmd = this.getMetaData(rs);

        ArrayList<String> retString = new ArrayList<String>();
        String tableEntry = "";

        while (rs.next()) {
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                if (i != rsmd.getColumnCount()) {
                    tableEntry += rs.getString(i) + ",";
                } else {
                    tableEntry += rs.getString(i);
                }

            }

            retString.add(tableEntry);
            tableEntry = "";
        }
        return retString;
    }


    /**
     * A method that insert an object into the SQL Database.
     * @param _tName
     * @param _tValues
     * @return boolean true if inserted, false otherwise.
     * @throws java.sql.SQLException
     */
    public boolean insertIntoTable(String _tName, String[] _tValues) throws SQLException {

        ResultSetMetaData tableInfo = this.executeQuery("SELECT * FROM " + _tName).getMetaData();
        int tableColumnCount = tableInfo.getColumnCount();

        String columns = "";
        for (int i = 1; i <= tableColumnCount; i++) {
            if (i != tableColumnCount) {
                columns += tableInfo.getColumnName(i) + ", ";
            } else {
                columns += tableInfo.getColumnName(i);
            }
        }

        String values = "";
        for (int i = 0; i < _tValues.length; i++) {
            if (i != _tValues.length - 1) {
                values += "'" + _tValues[i] + "', ";
            } else {
                values += "'" + _tValues[i] + "'";
            }
        }

        //System.out.println(columns);
        //System.out.println(values);
        this.executeUpdate("INSERT INTO " + _tName + " VALUES(" + values + ")");
        return true;
    }


    /**
     * A method that update an object in the data to the database.
     *
     * @param _tName
     * @param _tFields
     * @param _tFilterField
     * @param _operator
     * @param _tFilter
     * @return boolean, true if updated successfully, false other wise.
     * @throws java.sql.SQLException
     */
    public boolean updateTable(String _tName, String[] _tFields, String _tFilterField,
            String _operator, String _tFilter) throws SQLException {
        String setString = "";
        ArrayList<String> columns = this.getTableColumns(_tName);

        for (int i = 0; i < columns.size(); i++) {
            if (i != columns.size() - 1) {
                setString += " " + columns.get(i) + " = '" + _tFields[i] + "',";
            } else {
                setString += " " + columns.get(i) + " = '" + _tFields[i] + "'";
            }
        }

        this.executeUpdate("UPDATE " + _tName + " SET " + setString + " WHERE "
                + _tFilterField + " " + _operator + " '" + _tFilter + "'");
        return true;
    }


    /**
     * A method that print the table out in a proper format.
     *
     * @param _tName
     * @throws java.sql.SQLException
     */
    public void printTable(String _tName) throws SQLException {
        //System.out.println("Executing SELECT * FROM " + tName);

        ResultSet rs = statement.executeQuery("SELECT * FROM " + _tName);
        ResultSetMetaData rsmd = this.getMetaData(rs);

        System.out.println("Table Name: " + _tName + "\n");

        System.out.println("Columns: ");
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
            System.out.print(rsmd.getColumnTypeName(i) + ": " + rsmd.getColumnName(i) + ", ");
        }
        System.out.println("\n");

        System.out.println("Values: ");
        while (rs.next()) {
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                System.out.print(rs.getString(i) + ", ");
            }
            System.out.println("");
        }
    }
}
