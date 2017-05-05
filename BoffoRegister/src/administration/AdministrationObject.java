package administration;

/*@author jonathanholley
 @author jessicadenney

 Updated: 05/04/2017

 System settings for BoffoRegister. Allows user to set values for currency type,
 authorize.net API login ID, phone number, receipt message, authorize.net API secret key,
 store hours, store ID, store name, tax rate, and authorize.net API transaction key.
 Also sets authorization level to managers only.
 */
import authorization.AuthorizationInterface;
import events.BoffoFireObject;
import events.BoffoEvent;
import events.BoffoListenerInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class AdministrationObject extends BoffoFireObject implements AuthorizationInterface, BoffoListenerInterface {

//Create variables required for Administration class.
    protected String currency = "";
    protected static HashMap<String, ArrayList<Integer>> administration_hash = new HashMap<>();
    protected String loginID = "";
    protected int phoneNum = 0;
    protected String receiptMsg = "";
    protected String secretKey = "";
    protected double storeHours = 0.0;
    protected int storeID = 0;
    protected String storeName = "";
    protected static String tableName = "store_info_tbl";
    protected double taxRate = 0.0;
    protected String transactionKey = "";


    public AdministrationObject() {

    }


    //Sets variable values to themselves.
    public AdministrationObject(String _currency, String _loginID, int _phoneNum,
            String _receiptMsg, String _secretKey, double _storeHours, int _storeID,
            String _storeName, double _taxRate, String _transactionKey, String _tableName) {

        this.currency = _currency;
        this.loginID = _loginID;
        this.phoneNum = _phoneNum;
        this.receiptMsg = _receiptMsg;
        this.secretKey = _secretKey;
        this.storeHours = _storeHours;
        this.storeID = _storeID;
        this.storeName = _storeName;
        this.taxRate = _taxRate;
        this.transactionKey = _transactionKey;
        AdministrationObject.tableName = _tableName;
    }


    public AdministrationObject(String _tableName) {
        AdministrationObject.tableName = _tableName;
    }


    public static void buildMap() {

        ArrayList<Integer> setCurrency = new ArrayList<>();
        setCurrency.addAll(Arrays.asList(2));

        ArrayList<Integer> setLoginID = new ArrayList<>();
        setLoginID.addAll(Arrays.asList(2));

        ArrayList<Integer> setPhoneNumber = new ArrayList<>();
        setPhoneNumber.add(2);

        ArrayList<Integer> setReceiptMessage = new ArrayList<>();
        setReceiptMessage.addAll(Arrays.asList(2));

        ArrayList<Integer> setSecretKey = new ArrayList<>();
        setSecretKey.addAll(Arrays.asList(2));

        ArrayList<Integer> setStoreHours = new ArrayList<>();
        setStoreHours.add(2);

        ArrayList<Integer> setStoreID = new ArrayList<>();
        setStoreID.addAll(Arrays.asList(2));

        ArrayList<Integer> setStoreName = new ArrayList<>();
        setStoreName.addAll(Arrays.asList(2));

        ArrayList<Integer> setTaxRate = new ArrayList<>();
        setTaxRate.addAll(Arrays.asList(2));

        ArrayList<Integer> setTransactionKey = new ArrayList<>();
        setTransactionKey.add(2);

        administration_hash.put("setCurrency", setCurrency);
        administration_hash.put("setLoginID", setLoginID);
        administration_hash.put("setPhoneNumber", setPhoneNumber);
        administration_hash.put("setReceiptMessage", setReceiptMessage);
        administration_hash.put("setSecretKey", setSecretKey);
        administration_hash.put("setStoreHours", setStoreHours);
        administration_hash.put("setStoreID", setStoreID);
        administration_hash.put("setStoreName", setStoreName);
        administration_hash.put("setTaxRate", setTaxRate);
        administration_hash.put("setTransactionKeys", setTransactionKey);
    }


    //Return currency type.
    public String getCurrency() {

        return this.currency;
    }


    //Return login ID.
    public String getLoginID() {

        return this.loginID;
    }


    //Return phone number.
    public int getPhoneNumber() {

        return this.phoneNum;
    }


    //Return receipt message.
    public String getReceiptMessage() {

        return this.receiptMsg;
    }


    //Return secret key value.
    public String getSecretKey() {

        return this.secretKey;
    }


    //Return store hours.
    public double getStoreHours() {

        return this.storeHours;
    }


    //Return store ID.
    public int getStoreID() {
        return this.storeID;
    }


    //Return store name.
    public String getStoreName() {

        return this.storeName;
    }


    //Return tax rate.
    public double getTaxRate() {

        return this.taxRate;
    }


    //Return transaction key.
    public String getTransactionKey() {

        return this.transactionKey;
    }


    public static AdministrationObject loadAdminObject() {
        return (AdministrationObject) AdministrationObject.load(null, null, new AdministrationObject(tableName));
    }

    /*
     public static AdministrationObject loadByCurrency(String _c) {
     return (AdministrationObject) AdministrationObject.load("currency", _c, AdministrationObject(tableName));
     }

     public static AdministrationObject loadByLoginID(String _lID) {
     return (AdministrationObject) AdministrationObject.load("login ID", _lID, AdministrationObject(tableName));
     }

     public static AdministrationObject loadByPhoneNumber(String _pH) {
     return (AdministrationObject) AdministrationObject.load("phone number", _pH, AdministrationObject(tableName));
     }

     public static AdministrationObject loadByReceiptMessage(String _rM) {
     return (AdministrationObject) AdministrationObject.load("receipt message", _rM, AdministrationObject(tableName));
     }

     public static AdministrationObject loadBySecretKey(String _sK) {
     return (AdministrationObject) AdministrationObject.load("secret key", _sK, AdministrationObject(tableName));
     }

     public static AdministrationObject loadByStoreHours(String _sH) {
     return (AdministrationObject) AdministrationObject.load("store hours", _sH, AdministrationObject(tableName));
     }

     public static AdministrationObject loadByStoreID(String _sID) {
     return (AdministrationObject) AdministrationObject.load("store ID", _sID, AdministrationObject(tableName));
     }

     public static AdministrationObject loadByStoreName(String _sN) {
     return (AdministrationObject) AdministrationObject.load("store name", _sN, AdministrationObject(tableName));
     }

     public static AdministrationObject loadByTaxRate(String _tR) {
     return (AdministrationObject) AdministrationObject.load("tax rate", _tR, AdministrationObject(tableName));
     }

     public static AdministrationObject loadByTransactionKey(String _tK) {
     return (AdministrationObject) AdministrationObject.load("transaction key", _tK, AdministrationObject(tableName));
     }
     */

    public void messageReceived(BoffoEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    //Command to set currency.
    public void setCurrency(String _c) {

        this.currency = _c;
    }


    //Command to set login ID.
    public void setLoginID(String _lID) {

        this.loginID = _lID;
    }


    //Command to set phone number.
    public void setPhoneNumber(int _pN) {

        this.phoneNum = _pN;
    }


    //Command to set receipt message.
    public void setReceiptMessage(String _rM) {

        this.receiptMsg = _rM;
    }


    //Command to set secret key value.
    public void setSecretKey(String _sK) {

        this.secretKey = _sK;
    }


    //Command to set store hours.
    public void setStoreHours(double _sH) {

        this.storeHours = _sH;
    }


    //Command to set store ID.
    public void setStoreID(int _sID) {
        this.storeID = _sID;
    }


    //Command to set store name.
    public void setStoreName(String _sN) {

        this.storeName = _sN;
    }


    //Command to set tax rate.
    public void setTaxRate(double _tR) {

        this.taxRate = _tR;
    }


    //Command to set transaction key
    public void setTransactionKey(String _tK) {

        this.transactionKey = _tK;
    }
}
