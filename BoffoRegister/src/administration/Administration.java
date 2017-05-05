package administration;

/**
 *
 * @author jonathanholley
 * @author jessicadenney Updated: 05/04/2017
 *
 * Static class for the getters so that the classes that need access to the
 * information for the system settings can access them.
 */
public class Administration {

    static AdministrationObject thisAdminObject;


    //Static call to get currency.
    public static String printCurrency() {
        return Administration.thisAdminObject.getCurrency();
    }


    //Static call to get login ID.
    public static String printLoginID() {
        return Administration.thisAdminObject.getLoginID();
    }


    //Static call to get phone number.
    public static int printPhoneNumber() {
        return Administration.thisAdminObject.getPhoneNumber();
    }


    //Static call to get receipt message.
    public static String printReceiptMessage() {
        return Administration.thisAdminObject.getReceiptMessage();
    }


    //Static call to get secret key.
    public static String printSecretKey() {
        return Administration.thisAdminObject.getSecretKey();
    }


    //Static call to get store hours.
    public static double printStoreHours() {
        return Administration.thisAdminObject.getStoreHours();
    }


    //Static call to get store ID.
    public static int printStoreID() {
        return Administration.thisAdminObject.getStoreID();
    }


    //Static call to get store name.
    public static String printStoreName() {
        return Administration.thisAdminObject.getStoreName();
    }


    //Static call to get tax rate.
    public static double printTaxRate() {
        return Administration.thisAdminObject.getTaxRate();
    }


    //Static call to get transaction key.
    public static String printTransactionKey() {
        return Administration.thisAdminObject.getTransactionKey();
    }

}
