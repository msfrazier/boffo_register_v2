package database;

/**
 * A BoffoDbObject that all the modules have the ability to access. The class
 * have basic CRUD functions that tailor to the given object that calls the methods
 * inside this class.
 *
 * @author Thien Le and Thomas Cole
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
     * @param obj
     * @return True if save successful false otherwise.
     */
    public boolean save(BoffoDbObject obj) {

        try {
            ArrayList<String> entry = query.selectFromTable("*", obj.getTableName(), "UUID", "=", obj.getUuid());
            String[] tValues = getMatchedValues(obj);

            if (entry.size() == 0) {
                //System.out.println("Product not found!");
                query.insertIntoTable(obj.getTableName(), tValues);
            } else {
                //System.out.println("Product found.");
                query.updateTable(obj.getTableName(), tValues, "UUID", "=", obj.uuid);
            }
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }

    /**
     * Deletes a BoffoDbObject from the database.
     * @param obj
     * @return True if delete successful false otherwise.
     */
    public boolean delete(BoffoDbObject obj) {
        try {
            query.executeUpdate("DELETE FROM " + obj.getTableName() + " WHERE UUID = '" + obj.getUuid() + "'");
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

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
        ArrayList<Field> listArray = new ArrayList<Field>();
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
     * @param someObject
     * @return ArrayList<String>
     */
    private ArrayList<String> getMethods(BoffoDbObject someObject) {
        ArrayList<String> retList = new ArrayList<String>();

        for (Method method : someObject.getClass().getDeclaredMethods()) {
            if (Modifier.isPublic(method.getModifiers())
                    && method.getParameterTypes().length == 0
                    && method.getReturnType() != void.class
                    && (method.getName().startsWith("get") || method.getName().startsWith("is"))) {
                try {
                    Object value = method.invoke(someObject);
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
    private ArrayList<String> grabValue(BoffoDbObject someObject) {
        ArrayList<String> retList = new ArrayList<String>();

        for (Method method : someObject.getClass().getDeclaredMethods()) {
            if (Modifier.isPublic(method.getModifiers())
                    && method.getParameterTypes().length == 0
                    && method.getReturnType() != void.class
                    && (method.getName().startsWith("get") || method.getName().startsWith("is"))) {
                try {
                    Object value = method.invoke(someObject);
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
        retList.add(someObject.uuid);
        return retList;
    }

    /**
     * A method to preform some black magic voo-doo to match(MOSTLY) a class property to
     * the coresponding sql field
     * @param obj
     * @return String[] of values matched to its sql entry.
     */
    private String[] getMatchedValues(BoffoDbObject obj) {
        List<String> fields = query.getTableColumns(obj.getTableName());
        List<String> values = grabValue(obj);
        String[] tValues = new String[values.size()];

        List<String> methodNames = getMethods(obj);

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

    private BoffoDbObject invokeConstructor(ArrayList<String> arr) {
        try {
            Constructor[] constructorsArray = Class.forName(getCallerClassName(this)).getConstructors();

            Constructor largestCons = null;
            String longestString = " ";
            for (Constructor conIndex : constructorsArray) {
                String truString = conIndex.toString().replace("java.lang.", "");
                if (truString.length() > longestString.length()) {
                    longestString = truString;
                    largestCons = conIndex;
                }
            }

            //System.out.println("ConstructorsArray" + Arrays.asList(constructorsArray));

            Class[] constructorParams = largestCons.getParameterTypes();
            Object argList[] = new Object[constructorParams.length];

            //select entry from the table
            ArrayList<String> myArray = arr;

            for(String s: myArray){

                String[] tmp = s.split(" ");
                for (int i = 0; i < myArray.size(); i++) {
                    switch(constructorParams[i].getTypeName().replace("java.lang.", "")){
                        case "String":
                            argList[i] = (String)tmp[i];
                            break;
                        case "int":
                            argList[i] = Integer.parseInt(tmp[i]);
                            break;
                        case "double":
                            argList[i] = Double.parseDouble(tmp[i]);
                            break;
                        default:
                        System.out.println("NO MATCHING CLASS!!");
                }
                //System.out.println(argList[i]);
                }
            }

            try {
                //Invoke the constructor with the given parameters
                Object object = Class.forName(getCallerClassName(this))
                        .getConstructor(constructorParams).newInstance(argList);
//                System.out.println("ArgList" + Arrays.asList(argList));
                return (BoffoDbObject) object;
            } catch (NoSuchMethodException | SecurityException | ClassNotFoundException
                    | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(BoffoDbObject.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BoffoDbObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    /**
     * A method that locate the directory of the given object.
     * @return String of the location of the object that being called.
     */
    public static String getCallerClassName(BoffoDbObject obj) {
        return obj.getClass().getName();
    }

}