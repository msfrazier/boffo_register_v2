package bundles;

/**
 * A public interface or blueprint of a generic TicketElement which some Objects
 * may implement, like Product or Bundle.
 *
 * @author Michael Resnik
 * @author Travis Cox
 * @lastEdited 04/18/2017
 */
import java.util.Comparator;

public interface TicketElement {

    /**
     * Compares TicketElements based on non case-sensitive, and on alphabetical
     * order of sku.
     */
    public static final SkuComparator BYSKU = new SkuComparator();
    /**
     * Compares TicketElements based on non case-sensitive, and on alphabetical
     * order of name.
     */
    public static final NameComparator BYNAME = new NameComparator();
    /**
     * Compares TicketElements based on their price.
     */
    public static final PriceComparator BYPRICE = new PriceComparator();


    /**
     * Returns the description of the TicketElement.
     *
     * @return A String object which describes the TicketElement.
     */
    public String getDescription();


    /**
     * Returns a name identifier of the TicketElement.
     *
     * @return A String object which is a name identifier of TicketElement.
     */
    public String getName();


    /**
     * Returns the price of the TicketElement (in USD).
     *
     * @return The price of the TicketElement of type double.
     */
    public double getPrice();


    /**
     * Returns the assigned SKU value of the TicketElement.
     *
     * @return A String object which represents the TicketElement.
     */
    public String getSku();


    /**
     * Returns a copy of TicketElement, allowing for immutability.
     *
     * @return A copy of the TicketElement in question,
     */
    public TicketElement clone();

    /**
     * Compares two TicketElements by their Name attribute in a non
     * case-sensitive, alphabetic manner.
     */
    public static class NameComparator implements Comparator<TicketElement> {

        @Override
        public int compare(TicketElement _te1, TicketElement _te2) {
            return _te1.getName().compareToIgnoreCase(_te2.getName());
        }

    }

    /**
     * Compares two TicketElements by their SKU attribute in a non
     * case-sensitive, alphabetic manner.
     */
    public static class SkuComparator implements Comparator<TicketElement> {

        @Override
        public int compare(TicketElement _te1, TicketElement _te2) {
            return _te1.getSku().compareToIgnoreCase(_te2.getSku());
        }

    }

    /**
     * Compares two TicketElements by their Price attribute.
     */
    public static class PriceComparator implements Comparator<TicketElement> {

        @Override
        public int compare(TicketElement _te1, TicketElement _te2) {
            return Double.compare(_te1.getPrice(), _te2.getPrice());
        }

    }

}
