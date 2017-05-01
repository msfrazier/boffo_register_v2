package bundles;

/**
 * Enumeration for representing the different types of discounts that can be
 * applied to a bundle.
 *
 * @author Michael Resnik
 * @author Travis Cox
 * @lastEdited: 4/18/2017
 */
public enum DiscountType {
    /**
     * Discount an item (or bundle) by a specific percentage.
     */
    PERCENT,
    /**
     * Discount an item (or bundle) by a specific dollar amount.
     */
    DOLLAR,
    /**
     * Discount an item (or bundle) as buy one get a percentage off the second;
     * a discount of 100% is buy one get one free.
     */
    BOGO;


    /**
     * Outputs a string representing the discount based on the type of discount
     * and amount.
     *
     * @param _discountAmount The amount of the discount to format.
     * @return The formatted string.
     */
    public String formatString(double _discountAmount) {
        switch (this) {
            case PERCENT:
                return _discountAmount + "%";
            case DOLLAR:
                return "$" + _discountAmount;
            case BOGO:
                if (_discountAmount == 100.0) {
                    return "BOGO";
                }
                return "BOGO " + _discountAmount + "%";
            default:
                return this.toString();
        }
    }


    /**
     * Returns the string representing the discount type.
     *
     * @return the string representation of the discount type.
     */
    @Override
    public String toString() {
        switch (this) {
            case PERCENT:
                return "%";
            case DOLLAR:
                return "$";
            case BOGO:
                return "BOGO";
            default:
                throw new IllegalArgumentException();
        }
    }
}
