package transaction;

/**
 * Updated: May 5, 2017
 *
 * Class that handles credit card payments in a transaction.
 *
 * @author Mabelyn Espinoza
 * @author Fan Yang
 */
import a.net.BoffoPayment;

public class CreditCardPayment extends Transaction 
extends a.net.chargeCreditCard{
//
//    public static final String AMERICANEXPRESS = "American Express";
//    public static final String DISCOVER = "Discover";
//    public static final String MASTERCARD = "MasterCard";
//    public static final String VISA = "Visa";
//    public static final String OTHER = "Other";
//
//    //Prefixes for each card type.
//    public static final String[] AMEX_PREFIX = {"34", "37"};
//    public static final String[] DISC_PREFIX = {"60", "62", "64", "65"};
//    public static final String[] MC_PREFIX = {"2221", "2222", "2223", "2224",
//        "2225", "2226", "2227", "2228", "2229", "223", "224", "225", "226",
//        "227", "228", "229", "23", "24", "25", "26", "270", "271", "2720",
//        "50", "51", "52", "53", "54", "55"};
//    public static final String[] VISA_PREFIX = {"4"};

    //The card number length.
    public static final int MAXLENGTH = 16;
//    public static final int AMEX_MAXLENGTH = 15;

    //Other variables.
    protected String number, cvc;
    protected int expMonth, expYear;
    protected String name, address1, address2, city, state,
            zip, country;
    protected String currency;

    /**
     *
     * @param _number Card Number
     * @param _expMonth Expiration Month
     * @param _expYear Expiration Year
     * @param _cvc CVC Code
     * @param _name CardHolder Name
     * @param _address1 Address Line 1 of Billing Address
     * @param _address2 Address Line 2 of Billing Address
     * @param _city City of Billing Address
     * @param _state State of Billing Address
     * @param _zip Zip Code of Billing Address
     * @param _country Country of Billing Address
     * @param _currency Type of Currency
     */
    public CreditCardPayment(String _number, int _expMonth, int _expYear,
            String _cvc, String _name, String _address1, String _address2,
            String _city, String _state, String _zip, String _country, 
            String _currency) {
        this.number = _number;
        this.expMonth = _expMonth;
        this.expYear = _expYear;
        this.cvc = _cvc;
        this.name = _name;
        this.address1 = _address1;
        this.address2 = _address2;
        this.city = _city;
        this.state = _state;
        this.zip = _zip;
        this.country = _country;
        this.currency = _currency;
    }

    /**
     * @return card number
     */
    public String getNumber() {
        return this.number;
    }

    /**
     * Setter for the card number.
     *
     * @param _number
     */
    public void setNumber(String _number) {
        this.number = _number;
    }

    /**
     * @return CVC Code
     */
    public String getCVC() {
        return this.cvc;
    }

    /**
     * Setter for the CVC Code.
     *
     * @param _cvc
     */
    public void setCVC(String _cvc) {
        this.cvc = _cvc;
    }

    /**
     * @return the expiration month
     */
    public int getExpMonth() {
        return this.expMonth;
    }

    /**
     * @param _expMonth
     */
    public void setExpMonth(int _expMonth) {
        this.expMonth = _expMonth;
    }

    /**
     * @return the expiration year
     */
    public int getExpYear() {
        return this.expYear;
    }

    /**
     * @param _expYear
     */
    public void setExpYear(int _expYear) {
        this.expYear = _expYear;
    }

    /**
     * @return the name of the card holder
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param _name
     */
    public void setName(String _name) {
        this.name = _name;
    }

    /**
     * @return address line 1
     */
    public String getAddressLine1() {
        return this.address1;
    }

    /**
     * @param _address1
     */
    public void setAddressLine1(String _address1) {
        this.address1 = _address1;
    }

    /**
     * @return address line 2
     */
    public String getAddressLine2() {
        return this.address2;
    }

    /**
     * @param _address2
     */
    public void setAddressLine2(String _address2) {
        this.address2 = _address2;
    }

    /**
     * @return city of billing address.
     */
    public String getAddressCity() {
        return this.city;
    }

    /**
     * @param _city
     */
    public void setAddressCity(String _city) {
        this.city = _city;
    }

    /**
     * @return zip code of billing address.
     */
    public String getAddressZip() {
        return this.zip;
    }

    /**
     * @param _zip
     */
    public void setAddressZip(String _zip) {
        this.zip = _zip;
    }

    /**
     * @return state of billing address
     */
    public String getAddressState() {
        return this.state;
    }

    /**
     * @param _state
     */
    public void setAddressState(String _state) {
        this.state = _state;
    }

    /**
     * @return country of billing address.
     */
    public String getAddressCountry() {
        return this.country;
    }

    /**
     * @param _country
     */
    public void setAddressCountry(String _country) {
        this.country = _country;
    }

    /**
     * @return currency
     */
    public String getCurrency() {
        return this.currency;
    }

    /**
     * @param _currency
     */
    public void setCurrency(String _currency) {
        this.currency = _currency;
    }

    public BoffoPayment processCreditPayment(double _amount, String _creditCardNumber,
            String ticketID){
        BoffoPayment card = ()
        return;
    }
}


