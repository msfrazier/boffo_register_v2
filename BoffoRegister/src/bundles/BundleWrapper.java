package bundles;

/**
 * A wrapper class for representing bundles along with the specific product
 * objects that it contains (rather than the general products that are used to
 * represent the bundle itself)
 *
 * @author Michael Resnik
 * @author Travis Cox
 * @lastEdited: 4/18/2017
 */
public class BundleWrapper implements TicketElement {

    // Bundle stores static product objects
    private final Bundle bundle;
    // Actual product objects
    private final GroupList<Product_Test> products;


    /**
     * Bundle wrapper constructor
     *
     * @param _bundle A object of the bundle to create a wrapper for.
     * @param _products A GroupList of specific products to wrap with the
     * bundle.
     */
    public BundleWrapper(Bundle _bundle, GroupList<Product_Test> _products) {
        this.bundle = _bundle;
        this.products = _products;
    }


    /**
     * Get a clone of the BundleWrapper.
     *
     * @return The cloned BundleWrapper.
     */
    @Override
    public TicketElement clone() {
        return new BundleWrapper(this.bundle.clone(), this.products.clone());
    }


    /**
     * Get the bundle contained in the wrapper.
     *
     * @return The bundle object.
     */
    public Bundle getBundle() {
        return this.bundle;
    }


    /**
     * Get the description.
     *
     * @return The description.
     */
    @Override
    public String getDescription() {
        return bundle.getDescription();
    }


    /**
     * Get the name.
     *
     * @return The name.
     */
    @Override
    public String getName() {
        return bundle.getName();
    }


    /**
     * Get the price.
     *
     * @return The price.
     */
    @Override
    public double getPrice() {
        return bundle.getPrice();
    }


    /**
     * Get the GroupList that contains the specific products in the bundle
     * wrapper.
     *
     * @return The GroupList of products.
     */
    public GroupList<Product_Test> getProducts() {
        return this.products.clone();
    }


    /**
     * Get the SKU.
     *
     * @return The SKU.
     */
    @Override
    public String getSku() {
        return this.bundle.getSku();
    }


    /**
     * Return a string representation of the BundleWrapper
     *
     * @return The string that represents this BundleWrapper
     */
    @Override
    public String toString() {
        return bundle.toString();
    }

}
