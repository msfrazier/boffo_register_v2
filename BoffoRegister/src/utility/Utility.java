package utility;

/**
 *
 * @author sjwhyatt
 */

public class Utility {

    /*Formats and returns a double as a String.
    *Format for String is ($#.##)
    */
    public String formatPrice(double price){
        return String.format("$%.2f", price);
    }
}
