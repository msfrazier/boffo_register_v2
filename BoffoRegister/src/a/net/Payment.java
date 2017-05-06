package a.net;

/*@author DaneHasnen

 Updated: 05/05/17

 This is a payment object used as a container.
 */

public class Payment extends administration.Administration {
    /* This declares a payment object where a payment's information is stored
    in one data structure. By extending adminsitration, the variables for the
    secret key and transaction key are able to be accessed.
    */
    String _cardNumber;
    double _chargeAmt;
    int _confirmationNum;
    int _confirmationResNum;
    String _confirmation;
    String _chargeResponse;
    // Creating the variables for the payment object.

    public Payment(String _cardNumber, double _amt) {
        this._cardNumber = _cardNumber;
        this._chargeAmt = _amt;
        this._confirmationNum = -1;
        this._chargeResponse = "No Card Charged";
        this._confirmation = "No Card Charged";
        // Constructing the payment object and setting variables to "default."
    }

    public void chargeAmt(double _chargeChange) {
        this._chargeAmt = _chargeChange;
        /* Modifies chargeAmt by replacing the current value with the value
        in chargeChange.
        */
    }

    public void confirmation(String conf) {
        this._confirmation = conf;
        /* Modifies confirmation by replacing the current value with the value
        in conf.
        */
    }

    public void confirmationNum(int confNum) {
        this._confirmationNum = confNum;
        /* Modifies confirmaton by replacing the current value with the value
        in confNum.
        */
    }

    public void chargeRes(String response) {
        _chargeResponse = response;
        /* Modifies chargeRes by replacing the current value with the value
        in response.
        */
    }

    public String getCardNumber() {
        return _cardNumber;
    }

    public void setCardNumber(String _cardNumber) {
        this._cardNumber = _cardNumber;
    }

    public double getChargeAmt() {
        return _chargeAmt;
    }

    public void setChargeAmt(double _chargeAmt) {
        this._chargeAmt = _chargeAmt;
    }

    public int getConfirmationNum() {
        return _confirmationNum;
    }

    public void setConfirmationNum(int _confirmationNum) {
        this._confirmationNum = _confirmationNum;
    }

    public int getConfirmationResNum() {
        return _confirmationResNum;
    }

    public void setConfirmationResNum(int _confirmationResNum) {
        this._confirmationResNum = _confirmationResNum;
    }

    public String getConfirmation() {
        return _confirmation;
    }

    public void setConfirmation(String _confirmation) {
        this._confirmation = _confirmation;
    }

    public String getChargeResponse() {
        return _chargeResponse;
    }

    public void setChargeResponse(String _chargeResponse) {
        this._chargeResponse = _chargeResponse;
    }
    
    

}