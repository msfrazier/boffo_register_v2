/**
 * A BoffoDbObject that all the modules have the ability to access. The class
 * have basic CRUD functions that tailor to the given object that calls the methods
 * inside this class.
 *
 * @author Thien Le and Thomas Cole
 */
package database;

import java.util.UUID;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoffoDbObject {

    private String uuid;
    protected boolean active;
    protected BoffoDbObject temp;

    Query query = BoffoDatabaseAPI.getInstance().getQuery();

    public static void main(String[] args) {
        ConnectionManager con = new ConnectionManager();
        con.connectToDB("jdbc:mysql://localhost:3306/demo", "student", "student");
    }

    public BoffoDbObject() {
        uuid = UUID.randomUUID().toString();
    }

    public static BoffoDbObject load(BoffoDbObject obj, String field, String value) {
        return new BoffoDbObject();
    }

    public boolean save() {
        return false;
    }

    public boolean update(BoffoDbObject obj, String field, String value) {
//        getAllFields();
//        System.out.println(getCallerClassName(this));
        return false;
    }

    public boolean delete() {
        return active;
    }

    public BoffoDbObject insert() {
        return temp;
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
     */
    private String getTableName() {
        String tracedName;

        String callerClassName = getCallerClassName(this);
        tracedName = callerClassName;

        try {
            String tableName = "tableName";
            Class reflectionClass = Class.forName(tracedName);
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
     * A method that take the value all the getter methods with the given class
     * using reflection Method();
     * @return void.
     */
    public void grabValue(BoffoDbObject someObject) {
        for (Method method : someObject.getClass().getDeclaredMethods()) {
            if (Modifier.isPublic(method.getModifiers())
                    && method.getParameterTypes().length == 0
                    && method.getReturnType() != void.class
                    && (method.getName().startsWith("get") || method.getName().startsWith("is"))) {
                try {
                    Object value = method.invoke(someObject);
                    if (value != null) {
                        System.out.println(method.getName() + "=" + value);
                    }
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(BoffoDbObject.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * A method that locate the directory of the given object.
     * @return String of the location of the object that being called.
     */
    public static String getCallerClassName(BoffoDbObject obj) {
        return obj.getClass().getName();
    }

}
