package bundles;

import database.BoffoDbObject;
import java.util.ArrayList;

/**
 * Used for linking the Bundle lookup table with the Bundle table.
 *
 *
 * @author Michael Resnik
 * @author Travis Cox
 * @lastEdited: 05/05/2017
 */
class BundleItem extends BoffoDbObject {

    private String bundleID;
    private String productID;
    private int qty;
    private static final String tableName = "bundle_items_tbl";

    /**
     * Gets the bundleID.
     *
     * @return The bundleID.
     */
    public String getBundleID() {
        return this.bundleID;
    }

    /**
     * Get the productID.
     *
     * @return The productID.
     */
    public String getProductID() {
        return this.productID;
    }

    /**
     * Get the qty on the BundleItem.
     *
     * @return The qty.
     */
    public int getQty() {
        return this.qty;
    }

    /**
     * Generic BundleItem Constructor.
     *
     */
    public BundleItem() {
        BundleItem.create();
        this.bundleID = null;
        this.productID = null;
        this.qty = 0;
    }

    /**
     * BundleItem constructor that takes parameters.
     *
     * @param _bundleID the UUID of the Bundle.
     * @param _productID the UUID of the Product.
     * @param _qty the qty of the product on a bundle.
     */
    public BundleItem(String _bundleID, String _productID, int _qty) {
        this.bundleID = _bundleID;
        this.productID = _productID;
        this.qty = _qty;
    }

    /**
     * Get all the BundleItems that have a specific bundleID.
     *
     * @return Array of BundleItems with the bundleID.
     */
    public static BundleItem[] loadByBundleID(String _bundleID) {
        BundleItem[] bundleItems = (BundleItem[]) loadAll(new Bundle());
        ArrayList<BundleItem> bundleItemsList = new ArrayList();
        for (BundleItem bundleItem : bundleItems) {
            if (bundleItem.getBundleID() == _bundleID) {
                bundleItemsList.add(bundleItem);
            }
        }
        return (BundleItem[]) bundleItemsList.toArray();
    }

}
