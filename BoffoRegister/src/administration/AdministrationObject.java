package administration;

/*@author jonathanholley
 @author jessicadenney

 Updated: 05/01/17

 System settings for BoffoRegister. Allows user to set values for store name,
 store hours, tax rate, phone number, and a meassage for the receipt. Also allows
 other classes to find required information.
 */
import events.BoffoFireObject;

import events.BoffoEvent;

import events.BoffoListenerInterface;

public class AdministrationObject extends BoffoFireObject implements BoffoListenerInterface {

//Create variables required for Administration class.
    protected String currency = "";
    protected String loginID = "";
    protected int phone_num = 0;
    protected String receipt_msg = "";
    protected String secretKey = "";
    protected double store_hours = 0.0;
    protected int store_id = 0;
    protected String store_name = "";
    protected double tax_rate = 0.0;
    protected String transactionKey = "";

//Sets variable values to themselves.
    public AdministrationObject(String currency, String store_name, String receipt_msg, double store_hours,
            int phone_num, float tax_rate, String transactionKey, String loginID, int store_id,
            String secretKey) {

        this.currency = currency;
        this.loginID = loginID;
        this.phone_num = phone_num;
        this.receipt_msg = receipt_msg;
        this.secretKey = secretKey;
        this.store_hours = store_hours;
        this.store_id = store_id;
        this.store_name = store_name;
        this.tax_rate = tax_rate;
        this.transactionKey = transactionKey;

    }

//Placeholder to pull admin file from database or to pull form a table.
    /*
     public static AdministrationObject loadAdminObject() {
     return (AdministrationObject) AdministrationObject.load(null, null, new AdministrationObject(store_info_tbl));
     }

     public static AdministrationObject loadByCurrency(String _c) {
     return (AdministrationObject) AdministrationObject.load("currency", _c.AdministrationObject(store_info_tbl));
     }

     public static AdministrationObject loadByLoginID(String _lID) {
     return (AdministrationObject) AdministrationObject.load("login ID", _lID, AdministrationObject(store_info_tbl));
     }

     public static AdministrationObject loadByPhoneNumber(String _pH) {
     return (AdministrationObject) AdministrationObject.load("phone number", _pH, AdministrationObject(store_info_table));
     }

     public static AdministrationObject loadByReceiptMessage(String _rM) {
     return (AdministrationObject) AdministrationObject.load("receipt message", _rM, AdministrationObject(store_info_table));
     }

     public static AdministrationObject loadBySecretKey(String _sK) {
     return (AdministrationObject) AdministrationObject.load("secret key", _sK, AdministrationObject(store_info_tbl));
     }

     public static AdministrationObject loadByStoreHours(String _sH) {
     return (AdministrationObject) AdministrationObject.load("store hours", _sH, AdministrationObject(store_info_tbl));
     }

     public static AdministrationObject loadByStoreID(String _sID) {
     return (AdministrationObject) AdministrationObject.load("store ID", _sID, AdministrationObject(store_info_table));
     }

     public static AdministrationObject loadByStoreName(String _sN) {
     return (AdministrationObject) AdministrationObject.load("store name", _sN, AdministrationObject(store_info_table));
     }

     public static AdministrationObject loadByTaxRate(String _tR) {
     return (AdministrationObject) AdministrationObject.load("tax rate", _tR, AdministrationObject(store_info_tbl));
     }

     public static AdministrationObject loadByTransactionKey(String _tK) {
     return (AdministrationObject) AdministrationObject.load("transaction key", _tK, AdministrationObject(store_info_tbl));
     }
     */
    //return currency type.
    public String getCurrency() {

        return this.currency;
    }

    //Return login ID.
    public String getLoginID() {

        return this.loginID;
    }

    //Return phone number.
    public int getPhoneNumber() {

        return this.phone_num;
    }

    //Return receipt message.
    public String getReceiptMessage() {

        return this.receipt_msg;
    }

    //Return key value.
    public String getSecretKey() {

        return this.secretKey;
    }

    //Return store hours.
    public double getStoreHours() {

        return this.store_hours;
    }

    //Return store ID.
    public int getStoreID() {
        return this.store_id;
    }

    //Return store name.
    public String getStoreName() {

        return this.store_name;
    }

    //Return tax rate.
    public double getTaxRate() {

        return this.tax_rate;
    }

    //Return transaction key.
    public String getTransactionKey() {

        return this.transactionKey;
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

        this.phone_num = _pN;
    }

    //Command to set receipt message.
    public void setReceiptMessage(String _rM) {

        this.receipt_msg = _rM;
    }

    //Command to set secret key value.
    public void setSecretKey(String _sK) {

        this.secretKey = _sK;
    }

    //Command to set store hours.
    public void setStoreHours(double _sH) {

        this.store_hours = _sH;
    }

    //Command to set store ID.
    public void setStoreID(int _sID) {
        this.store_id = _sID;
    }

    //Command to set store name.
    public void setStoreName(String _sN) {

        this.store_name = _sN;
    }

    //Command to set tax rate.
    public void setTaxRate(double _tR) {

        this.tax_rate = _tR;
    }

    //Command to set transaction key
    public void setTransactionKey(String _tK) {

        this.transactionKey = _tK;
    }

    public void addBRegisterListener(BoffoListenerInterface _event) {

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void removeBRegisterListener(BoffoListenerInterface _event) {

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override

    public void fireEvent(BoffoEvent _event) {

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override

    public void messageReceived(BoffoEvent event) {

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
