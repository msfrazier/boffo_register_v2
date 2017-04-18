package administration;
/*@author jonathanholley jessicadenney
 Updated: 04/12/17
 */

import database.BoffoDbObject;
import events.BoffoEvent;
import events.BoffoListenerInterface;

public class Administration extends BoffoDbObject implements BoffoListenerInterface {
//Create variables required for Administration class.

    protected Administration admin;
    protected String storeName;
    protected String receiptMessage;
    protected double storeHours;
    protected int phoneNumber;
    protected double taxRate;

//Sets variables to null values.
    public Administration() {

        this.admin = null;
        this.storeName = "";
        this.receiptMessage = "";
        this.storeHours = 0.0;
        this.phoneNumber = 0;
        this.taxRate = 0.0;
    }

//Sets variable values to themselves.
    public Administration(Administration admin, String storeName, String receiptMessage, double storeHours, int phoneNumber, float taxRate) {

        this.admin = admin;
        this.storeName = storeName;
        this.receiptMessage = receiptMessage;
        this.storeHours = storeHours;
        this.phoneNumber = phoneNumber;
        this.taxRate = taxRate;
    }

//Placeholder to pull admin file from database or to pull form a table.
    public Administration getAdministration() {
        return this.admin;
    }

    //Command to set phone number.
    public void setPhoneNumber(int _pN) {
        this.admin.phoneNumber = _pN;
    }

    //Command to set receipt message.
    public void setReceiptMessage(String _rM) {
        this.admin.receiptMessage = _rM;
    }

    //Command to set store hours.
    public void setStoreHours(double _sH) {
        this.admin.storeHours = _sH;
    }

    //Command to set store name.
    public void setStoreName(String _sN) {
        this.admin.storeName = _sN;
    }

    //Command to set tax rate.
    public void setTaxRate(double _tR) {
        this.admin.taxRate = _tR;
    }

    @Override
    public void addBRegisterListener(BoffoListenerInterface _event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
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
