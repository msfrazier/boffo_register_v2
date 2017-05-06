package database;

/**
 * A BoffoDbObject that all the modules have the ability to access. The class
 * have basic CRUD functions that tailor to the given object that calls the
 * methods inside this class.
 *
 * @author Thien Le
 * @author Thomas Cole
 * @lastEdited: 5/5/2017
 */
import java.lang.reflect.Constructor;
import java.util.UUID;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoffoDbObject {

    private String uuid;
    protected boolean active;
    protected BoffoDbObject temp;

    static Query query = BoffoDatabaseAPI.getInstance().getQuery();


    public BoffoDbObject() {
        uuid = UUID.randomUUID().toString();
    }


    /**
     * A generic load method
     *
     * @param _loadByField
     * @param _fieldValue
     * @param _obj
     * @return a BoffoDbObject that tailor to callerClass's object.
     */
    public static BoffoDbObject load(String _loadByField, String _fieldValue, BoffoDbObject _obj) {
        String tableName = getTableName(_obj);
        ArrayList<String> selectedItems;

        try {
            selectedItems = query.selectFromTable("*", tableName, _loadByField, "=", _fieldValue);

            if (selectedItems.size() > 1) {
                System.out.println("There is more than one entry for this item please loadAll");
                return null;
            }

            for (String s : selectedItems) {
                String[] tmpList = s.split(",");
                ArrayList<String> invokeList = new ArrayList<String>();

                for (int i = 0; i < tmpList.length; i++) {
                    invokeList.add(tmpList[i]);
                }

                return invokeConstructor(_obj, invokeList);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return null;
    }


    /**
     * Load the entry from the database with a given name.
     *
     * @param _Name
     * @param _obj
     * @return A BoffoDbObject with the given name.
     */
    public static BoffoDbObject loadByName(String _Name, BoffoDbObject _obj) {
        return load("name", _Name, _obj);
    }


    /**
     * Load the entry from the database with a given uuid.
     *
     * @param _UUID
     * @param _obj
     * @return A BoffoDbObject with the given UUID
     */
    public static BoffoDbObject loadByUUID(String _UUID, BoffoDbObject _obj) {
        return load("uuid", _UUID, _obj);
    }


    public static BoffoDbObject[] loadAll(BoffoDbObject _obj) {
        String tableName = getTableName(_obj);
        ArrayList<String> selectedItems;

        try {
            selectedItems = query.selectFromTable("*", tableName);

            BoffoDbObject[] retArray = new BoffoDbObject[selectedItems.size()];

            for (int i = 0; i < selectedItems.size(); i++) {
                String[] tmpList = selectedItems.get(i).split(",");
                ArrayList<String> invokeList = new ArrayList<String>();

                for (int j = 0; j < tmpList.length; j++) {
                    invokeList.add(tmpList[j]);
                }

                retArray[i] = invokeConstructor(_obj, invokeList);
            }

            return retArray;

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return null;
    }


    /**
     * Saves a BoffoDbObject to the database
     *
     * @param _obj
     * @return True if save successful false otherwise.
     */
    public boolean save(BoffoDbObject _obj) {
        try {
            String[] tValues = this.getMatchedValuesSave(_obj).toArray(new String[0]);

            try {
                query.insertIntoTable(_obj.getTableName(_obj), tValues);
            } catch (SQLException e) {
                query.updateTable(_obj.getTableName(_obj), tValues, "UUID", "=", _obj.uuid);
            }

        } catch (SQLException ex) {
            return false;
        }
        return true;
    }


    /**
     * Deletes a BoffoDbObject from the database.
     *
     * @param _obj
     * @return True if delete successful false otherwise.
     */
    public boolean delete(BoffoDbObject _obj) {
        //get the uuid field for the table
        String tableName = getTableName(_obj);
        List<String> tableColumns = query.getTableColumns(tableName);
        String uuidField = "";
        for (String s : tableColumns) {
            if (s.matches(".*_id")) {
                uuidField = s;
            }
        }
        try {
            query.executeUpdate("DELETE FROM " + _obj.getTableName(_obj)
                    + " WHERE " + uuidField + " = '" + _obj.getUuid() + "'");
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }


    /**
     * A static method that create new BoffoDbObject() with the UUID.
     *
     * @return a new dataBaseObject with a unique UUID.
     */
    public static BoffoDbObject create() {
        return new BoffoDbObject();
    }


    /**
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }


    /**
     * Determine the table to act on from the class that calls the method.
     * Improve an the running time.
     *
     * @return a string of that tableName value.
     */
    private static String getTableName(BoffoDbObject _obj) {
        String callerClassName = _obj.getClass().getName();

        try {
            String tableName = "tableName";
            Class reflectionClass = Class.forName(callerClassName);
            Field fieldName = reflectionClass.getDeclaredField(tableName);

            //Make sure the field is accessible.
            fieldName.setAccessible(true);

            Object obj = reflectionClass.newInstance();
            String tableString = (String) fieldName.get(obj);

            return tableString;
        } catch (ClassNotFoundException | NoSuchFieldException | SecurityException e) {
        } catch (IllegalArgumentException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(BoffoDbObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    /**
     * A method to match a class property to the coresponding sql field
     *
     * @param _obj
     * @return String[] of values matched to its sql entry.
     */
    private ArrayList<String> getMatchedValuesSave(BoffoDbObject _obj) {
        ArrayList<String> retList = new ArrayList<String>();

        for (String s : query.getTableColumns(getTableName(_obj))) {
            for (Field f : this.getAllFields()) {
                int subIndex = f.toString().lastIndexOf(".") + 1;
                String trimmedName = f.toString().substring(subIndex);

                if (s.equalsIgnoreCase(trimmedName)) {
                    try {
                        retList.add(f.get(_obj).toString());
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        System.out.println(ex);
                    }
                }
            }
        }

        return retList;
    }


    /**
     * A method to match a class property to the coresponding sql field
     *
     * @param _obj
     * @return String[] of values matched to its sql entry.
     */
    private ArrayList<String> getMatchedValuesLoad(BoffoDbObject _obj) {
        ArrayList<String> retList = new ArrayList<String>();

        for (String s : query.getTableColumns(getTableName(_obj))) {
            for (Field f : this.getAllFields()) {
                int subIndex = f.toString().lastIndexOf(".") + 1;
                String trimmedName = f.toString().substring(subIndex);

                if (s.equalsIgnoreCase(trimmedName)) {
                    try {
                        retList.add(f.get(_obj).toString());
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        System.out.println(ex);
                    }
                }
            }
        }

        return retList;
    }


    /**
     * A method that invoke the Constructor with the largest parameters size of
     * the CallerClass. Cast the values to the proper Object according to the
     * Caller Constructor. Lastly, it will create new instance of that
     * constructor and pass in all the values accordingly.
     *
     * @param _obj of the CallerClass.
     * @param _arr from the resultSet.
     * @return a BoffoDbObject that tailor to the CallerClass constructor.
     */
    private static BoffoDbObject invokeConstructor(BoffoDbObject _obj, ArrayList<String> _arr) {
        try {
            Constructor largestCons = findLargestConstructor(_obj);
            Class[] constructorParams = largestCons.getParameterTypes();
            Object argList[] = findArgObjects(largestCons, _arr);

            //Invoke the constructor with the given parameters
            Object object = _obj.getClass().getConstructor(constructorParams).newInstance(argList);
            return (BoffoDbObject) object;
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException
                | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(BoffoDbObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    /**
     * A method that iterate through all the constructors and find the
     * constructor with the most parameters.
     *
     * @param _obj
     * @return a Constructor object.
     */
    private static Constructor findLargestConstructor(BoffoDbObject _obj) {
        Constructor[] constructorsArray = _obj.getClass().getConstructors();

        //Find the constructor with the largest parameters sizes.
        Constructor largestCons = null;
        String longestString = " ";
        for (Constructor conIndex : constructorsArray) {
            String truString = conIndex.toString().replace("java.lang.", "");
            if (truString.length() > longestString.length()) {
                longestString = truString;
                largestCons = conIndex;
            }
        }
        return largestCons;
    }


    /**
     * A method that match the arrayList to the constructor parameters. Then
     * cast the value to the proper type of object.
     *
     * @param _largestCons - the largest constructor get from
     * findLargestConstructor.
     * @param _arr - an ArrayList<String> from the ResultSet.
     * @return arglist - an Object[].
     */
    private static Object[] findArgObjects(Constructor _largestCons, ArrayList<String> _arr)
            throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class[] constructorParams = _largestCons.getParameterTypes();
        Object argList[] = new Object[constructorParams.length];

        //select entry from the table
        ArrayList<String> myArray = _arr;
        for (int i = 0; i < myArray.size(); i++) {
            for (int j = 0; j < myArray.size(); j++) {
                switch (constructorParams[j].getTypeName().replace("java.lang.", "")) {
                    case "String":
                        argList[j] = (String) myArray.get(i);
                        i++;
                        break;
                    case "int":
                        argList[j] = Integer.parseInt(myArray.get(i));
                        i++;
                        break;
                    case "double":
                        argList[j] = Double.parseDouble(myArray.get(i));
                        i++;
                        break;
                    default: {
                        Object object = Class.forName(constructorParams[j].getTypeName())
                                .getConstructor().newInstance();
                        Method setterMethod = object.getClass().getMethod("setName", String.class);
                        setterMethod.setAccessible(true);
                        setterMethod.invoke(object, myArray.get(i));
                        argList[i] = object;
                        i++;
                    }
                }
            }
        }
        return argList;
    }


    /**
     * A function that iterate through all the fields of the given class using
     * reflection.
     *
     * @return an Array of all the fields on the object class.
     */
    private ArrayList<Field> getAllFields() {
        List<Field> privateFields = new ArrayList<>();
        Field[] allFields;
        ArrayList<Field> listArray = new ArrayList<>();
        try {
            allFields = Class.forName(getCallerClassName(this)).getDeclaredFields();
            listArray.addAll(Arrays.asList(allFields));
            return listArray;
        } catch (ClassNotFoundException | IllegalArgumentException ex) {
            Logger.getLogger(BoffoDbObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    /**
     * A method that locate the directory of the given object.
     *
     * @param _obj
     * @return String of the location of the object that being called.
     */
    public static String getCallerClassName(BoffoDbObject _obj) {
        return _obj.getClass().getName();
    }

}
