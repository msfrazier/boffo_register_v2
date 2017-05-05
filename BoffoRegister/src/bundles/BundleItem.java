package bundles;

import database.BoffoDbObject;

/**
 * Used for linking the Bundle lookup table with the Bundle table.
 *
 * TODO : Implementation based on the database loadAll method.
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
}
