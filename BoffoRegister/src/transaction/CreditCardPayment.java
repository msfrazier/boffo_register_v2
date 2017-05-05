package transaction;

/**
 * Updated: May 5, 2017
 *
 * Class that handles credit card payments in a transaction.
 *
 * @author Mabelyn Espinoza
 */
public class CreditCardPayment extends Transaction {

    public static final String AMERICANEXPRESS = "American Express";
    public static final String DISCOVER = "Discover";
    public static final String MASTERCARD = "MasterCard";
    public static final String VISA = "Visa";
    public static final String OTHER = "Other";

    //Prefixes for each card type.
    public static final String[] AMEX_PREFIX = {"34", "37"};
    public static final String[] DISC_PREFIX = {"60", "62", "64", "65"};
    public static final String[] MC_PREFIX = {"2221", "2222", "2223", "2224", 
        "2225", "2226", "2227", "2228", "2229", "223", "224", "225", "226",
        "227", "228", "229", "23", "24", "25", "26", "270", "271", "2720",
        "50", "51", "52", "53", "54", "55"};
    public static final String[] VISA_PREFIX = {"4"};
    
    //The card number length.
    public static final int MAXLENGTH = 16;
    public static final int AMEX_MAXLENGTH = 15;
    
    //Other variables.
    protected String number, cvc;
    protected int expMonth, expYear;
    protected String name, address1, address2, city, state, 
            zip, country;
    protected String last4;
    protected String brand;
//    protected String funding;
 //   protected String fingerprint;
   // protected String country;
    protected String currency;
    
    public CreditCardPayment(String number, int expMonth, int expYear, 
            String cvc, String name, String address1, String address2, 
            String city, String zip, String country, String currency){
   this(number,
                expMonth,
                expYear,
                cvc,
                name,
                address1,
                address2,
                city,
                state,
                zip,
                country,
                null,
                null,
                null,
                null,
                null,
                currency);
    
    
}
    
    

}
