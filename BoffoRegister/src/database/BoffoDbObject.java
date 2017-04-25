package database;

/**
 * A BoffoDbObject that all the modules have the ability to access. The class
 * have basic CRUD functions that tailor to the given object that calls the methods
 * inside this class.
 *
 * @author Thien Le
 * @author Thomas Cole
 * @lastEdited: 4/25/2017
 */

import java.lang.reflect.Constructor;
import java.util.UUID;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
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
     * @param _tableName
     * @return a BoffoDbObject that tailor to callerClass's object.
     */
    public static BoffoDbObject load(String _loadByField, String _fieldValue, String _tableName) {

        //get the field for the table
        List<String> tableColumns = query.getTableColumns(_tableName);
        String fieldName = "";
        for (String s : tableColumns) {
            if (s.matches(".*" + _loadByField + ".*")) {
                System.out.println(s);
                fieldName = s;
            }
        }
        try {
            ArrayList<String> entry = query.selectFromTable("*", _tableName, fieldName, "=", _fieldValue);
        } catch (SQLException e) {
            System.out.println("Could not load entry: " + e);
        }
        return null;
    }


    /**
     * Load the entry from the database with a given name.
     * @param _Name
     * @param _tableName
     * @return A BoffoDbObject with the given name.
     */
    public static BoffoDbObject loadByName(String _Name, String _tableName) {

        //get the name field for the table
        List<String> tableColumns = query.getTableColumns(_tableName);
        String nameField = "";
        for (String s : tableColumns) {
            if (s.matches(".*name")) {
                nameField = s;
            }
        }

        try {
            ArrayList<String> entry = query.selectFromTable("*", _tableName, nameField, "=", _Name);
            System.out.println(entry);
        } catch (SQLException e) {
            System.out.println("Could not load entry: " + e);
        }

        return null;
    }


    /**
     * Load the entry from the database with a given uuid.
     * @param _UUID
     * @param _tableName
     * @return A BoffoDbObject with the given UUID
     */
    public static BoffoDbObject loadByUUID(String _UUID, String _tableName) {

        //get the uuid field for the table
        List<String> tableColumns = query.getTableColumns(_tableName);
        String uuidField = "";
        for (String s : tableColumns) {
            if (s.matches(".*_id")) {
                uuidField = s;
            }
        }

        ArrayList<String> entry;
        try {
            entry = query.selectFromTable("*", _tableName, uuidField, "=", _UUID);
            System.out.println(entry);
        } catch (SQLException e) {
            System.out.println("Could not load entry: " + e);
        }

        return null;
    }

    /**
     * Saves a BoffoDbObject to the database
     * @param _obj
     * @return True if save successful false otherwise.
     */
    public boolean save(BoffoDbObject _obj) {

        try {
            ArrayList<String> entry = query.selectFromTable("*", _obj.getTableName(), "UUID", "=", _obj.getUuid());
            String[] tValues = getMatchedValues(_obj);

            if (entry.size() == 0) {
                //System.out.println("Product not found!");
                query.insertIntoTable(_obj.getTableName(), tValues);
            } else {
                //System.out.println("Product found.");
                query.updateTable(_obj.getTableName(), tValues, "UUID", "=", _obj.uuid);
            }
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }

    /**
     * Deletes a BoffoDbObject from the database.
     * @param _obj
     * @return True if delete successful false otherwise.
     */
    public boolean delete(BoffoDbObject _obj) {
        try {
            query.executeUpdate("DELETE FROM " + _obj.getTableName() + " WHERE UUID = '" + _obj.getUuid() + "'");
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }


    /**
     * A static method that create new BoffoDbObject() with the UUID.
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
     * @return a string of that tableName value.
     */
    private String getTableName() {
        String callerClassName = getCallerClassName(this);

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
     * A function that iterate through all the fields of the given class
     * using reflection.
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
     * A method similar to grabValue but gets the method name instead of the value.
     * @param _someObject
     * @return ArrayList<String>
     */
    private ArrayList<String> getMethods(BoffoDbObject _someObject) {
        ArrayList<String> retList = new ArrayList<String>();

        for (Method method : _someObject.getClass().getDeclaredMethods()) {
            if (Modifier.isPublic(method.getModifiers())
                    && method.getParameterTypes().length == 0
                    && method.getReturnType() != void.class
                    && (method.getName().startsWith("get") || method.getName().startsWith("is"))) {
                try {
                    Object value = method.invoke(_someObject);
                    if (value != null) {
                        //System.out.println(method.getName());
                        retList.add(method.getName());
                    }
                } catch (IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException ex) {
                    Logger.getLogger(BoffoDbObject.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return retList;
    }


    /**
     * A method that take the value all the getter methods with the given class
     * using reflection Method();
     * @return ArrayList<String>.
     */
    private ArrayList<String> grabValue(BoffoDbObject _someObject) {
        ArrayList<String> retList = new ArrayList<String>();

        for (Method method : _someObject.getClass().getDeclaredMethods()) {
            if (Modifier.isPublic(method.getModifiers())
                    && method.getParameterTypes().length == 0
                    && method.getReturnType() != void.class
                    && (method.getName().startsWith("get") || method.getName().startsWith("is"))) {
                try {
                    Object value = method.invoke(_someObject);
                    if (value != null) {
                        //System.out.println(method.getName() + "=" + value);
                        retList.add(value.toString());
                    }
                } catch (IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException ex) {
                    Logger.getLogger(BoffoDbObject.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        retList.add(_someObject.uuid);
        return retList;
    }


    /**
     * A method to preform some black magic voo-doo to match(MOSTLY) a class property to
     * the coresponding sql field
     * @param _obj
     * @return String[] of values matched to its sql entry.
     */
    private String[] getMatchedValues(BoffoDbObject _obj) {
        List<String> fields = query.getTableColumns(_obj.getTableName());
        List<String> values = grabValue(_obj);
        String[] tValues = new String[values.size()];

        List<String> methodNames = getMethods(_obj);

        try {
            //Get ready to see some shit.
            for (int i = 0; i < fields.size(); i++) {
                String tmpField = fields.get(i);
                for (int j = 0; j < methodNames.size(); j++) {
                    String tmpName = methodNames.get(j);
                    //Remove get from the name.
                    tmpName = tmpName.substring(3);

                    //Test to see if names match
                    if (tmpField.matches(".*" + tmpName + ".*")) {
                        //System.out.println(tmpField + " : " + tmpName);
                        tValues[i] = values.get(j);
                    }
                }
            }

            //Grab the UUID since it is added last to the values list.
            tValues[tValues.length - 1] = values.get(values.size() - 1);
        } catch (Exception e) {
            System.out.println("Unable to save: " + e);
        }

        return tValues;
    }


    /**
     * A method that track the process of returning the Object back to the Caller
     * in a proper manner.
     * @param _obj of the caller.
     * @return a new instance of BoffoObject with all the proper value attached to it.
     */
    public static BoffoDbObject testMethod(BoffoDbObject _obj) {
        ArrayList<String> arr = new ArrayList<>();
        arr.add("Thien");
        arr.add("5");
        arr.add("5.5");
        arr.add("3");
        arr.add("Rating");
        arr.add("String Value");
        return BoffoDbObject.invokeConstructor(_obj, arr);
    }


    /**
     * A method that invoke the Constructor with the largest parameters size of the CallerClass.
     * Cast the values to the proper Object according to the Caller Constructor.
     * Lastly, it will create new instance of that constructor and pass in all the values accordingly.
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
     * A method that iterate through all the constructors and find the constructor
     * with the most parameters.
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
     * A method that match the arrayList to the constructor parameters. Then cast the
     * value to the proper type of object.
     * @param _largestCons - the largest constructor get from findLargestConstructor.
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
     * A method that locate the directory of the given object.
     * @return String of the location of the object that being called.
     */
    public static String getCallerClassName(BoffoDbObject _obj) {
        return _obj.getClass().getName();
    }

}
