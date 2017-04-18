package database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


public class Query {

    private final Statement statement;

    /*
    * Initialize with the current connection
    */
    public Query(Connection dbConnection) throws SQLException{
        statement = dbConnection.createStatement();
    }

    public ResultSet executeQuery(String query) throws SQLException{
        return statement.executeQuery(query);
    }

    public ResultSetMetaData getMetaData(ResultSet rs) throws SQLException{
        return rs.getMetaData();
    }

    public void executeUpdate(String query) throws SQLException{
        statement.executeUpdate(query);
    }

    /*
    * Overload that passes only a table name and field to the select from table below.
    */
    public String selectFromTable(String tName, String tField) throws SQLException{
        return this.selectFromTable(tName, tField, null, null, null);
    }

    /*
    * For use by dbAPI class allowing for select statements to be easily executed.
    */
    public String selectFromTable(String tName, String tField, String tFilterField, String operator, String tFilter) throws SQLException{
        System.out.println("Executing: SELECT " + tField + " FROM " + tName + " WHERE " + tFilterField + " " + operator + " '" + tFilter + "'");

        ResultSet rs;
        ResultSetMetaData rsmd;

        if(tFilterField != null || operator != null || tFilter != null){
            rs = this.executeQuery("SELECT " + tField + " FROM " + tName + " WHERE " + tFilterField+ " " + operator + " '" + tFilter + "'");
            rsmd = this.getMetaData(rs);

            while(rs.next()){
                for(int i = 1; i <= rsmd.getColumnCount(); i++){
                    System.out.print(rs.getString(i) + " ");
                }
                System.out.println("");
            }

        } else {
            rs = this.executeQuery("SELECT " + tField + " FROM " + tName);
            rsmd = this.getMetaData(rs);

            while(rs.next()){
                for(int i = 1; i <= rsmd.getColumnCount(); i++){
                    System.out.print(rs.getString(i) + " ");
                }
                System.out.println("");
            }
        }

        return null;
    }

    public boolean insertIntoTable(String tName, String[] tValues) throws SQLException{

        ResultSetMetaData tableInfo = this.executeQuery("SELECT * FROM " + tName).getMetaData();
        int tableColumnCount = tableInfo.getColumnCount();

        String columns = "";
        for (int i = 1; i <= tableColumnCount; i++) {
            if(i != tableColumnCount){
                columns += tableInfo.getColumnName(i) + ", ";
            } else {
                columns += tableInfo.getColumnName(i);
            }
        }

        String values = "";
        for (int i = 0; i < tValues.length; i++) {
            if(i != tValues.length - 1){
                values += "'"+tValues[i] + "', ";
            } else {
                values += "'" + tValues[i] + "'";
            }
        }

        //System.out.println(columns);
        //System.out.println(values);
        //System.out.println("INSERT INTO " + tName + "(" + columns + ") VALUES(" + values + ")");

        this.executeUpdate("INSERT INTO " + tName + "(" + columns + ") VALUES(" + values + ")");
        return true;
    }

    public boolean updateTable(String tName, String tField, String value, String tFilterField, String operator, String tFilter) throws SQLException{
        this.executeUpdate("UPDATE " + tName + " SET " + tField + " = '" + value + "' WHERE " + tFilterField + " " + operator + " '" + tFilter + "'");
        return true;
    }

    public boolean deleteEntry(String tName, String tFilterField, String operator, String tFilter) throws SQLException{
        this.executeUpdate("DELETE FROM " + tName + " WHERE " + tFilterField + " " + operator + " '" + tFilter + "'");
        return true;
    }

    /*
    * This will print the table name, column names, and all rows in the table.
    */
    public void printTable(String tName) throws SQLException{
        System.out.println("Executing SELECT * FROM " + tName);

        ResultSet rs = statement.executeQuery("SELECT * FROM " + tName);
        ResultSetMetaData rsmd = this.getMetaData(rs);

        System.out.println("Table Name: " + tName + "\n");

        System.out.println("Columns: ");
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
            System.out.print(rsmd.getColumnTypeName(i) + ": " + rsmd.getColumnName(i) +  ", ");
        }
        System.out.println("\n");

        System.out.println("Values: ");
        while(rs.next()){
            for(int i = 1; i <= rsmd.getColumnCount(); i++){
                System.out.print(rs.getString(i) + ", ");
            }
            System.out.println("");
        }
    }
}
