package administration;

/**
 *
 * @author jonathanholley
 */
public class Administration {

    static AdministrationObject thisAdminObject;

    public static String printCurrency() {
        return Administration.thisAdminObject.getCurrency();
    }

    public static String printLoginID() {
        return Administration.thisAdminObject.getLoginID();
    }

    public static int printPhoneNumber() {
        return Administration.thisAdminObject.getPhoneNumber();
    }

    public static String printReceiptMessage() {
        return Administration.thisAdminObject.getReceiptMessage();
    }

    public static String printSecretKey() {
        return Administration.thisAdminObject.getSecretKey();
    }

    public static double printStoreHours() {
        return Administration.thisAdminObject.getStoreHours();
    }

    public static int printStoreID() {
        return Administration.thisAdminObject.getStoreID();
    }

    public static String printStoreName() {
        return Administration.thisAdminObject.getStoreName();
    }

    public static double printTaxRate() {
        return Administration.thisAdminObject.getTaxRate();
    }

    public static String printTransactionKey() {
        return Administration.thisAdminObject.getTransactionKey();
    }

}
