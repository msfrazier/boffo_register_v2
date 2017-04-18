package bundles;

public enum DiscountType {
    PERCENT,
    DOLLAR,
    BOGO;

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

    public String formatString(double amount) {
        switch (this) {
            case PERCENT:
                return amount + "%";
            case DOLLAR:
                return "$" + amount;
            default:
                return toString();
        }
    }
}
