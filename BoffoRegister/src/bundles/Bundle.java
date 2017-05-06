package bundles;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import product.ProductObject;
import database.BoffoDbObject;

/**
 * Bundle class for storing and using bundles and discounts, contains methods
 * for processing products into lists including discounts.
 *
 * @author Michael Resnik
 * @author Travis Cox
 * @lastEdited: 05/05/2017
 */
public class Bundle extends BoffoDbObject implements TicketElement {

    // List of the products that comprise a bundle or discount.
    private final GroupList<ProductObject> products;
    private final DiscountType discountType;
    // Discount amount (based on discountType).
    private final double discountAmount;
    // The max number of the bundle or discount that can be on one transaction.
    private final int maxAllowed;
    private final String name;
    private final String description;
    private final String sku;
    private final Calendar startDate;
    private Calendar endDate;

    protected static String tableName = "bundle_tbl";

    /**
     * Adds Product objects to Bundle's personal products GroupList.
     *
     * @param _products Product objects to be added to the Bundle's GroupList.
     */
    private void addProducts(GroupList<ProductObject> _products) {
        this.products.add(_products);
    }

    /**
     * Constructor for a Bundle object, needed to be a BoffoDbObject, syntax
     * mirrored from Product().
     *
     * @see Product()
     * @see BoffoDbObject.create()
     */
    public Bundle() {
        BoffoDbObject.create();
        this.name = "";
        this.description = "";
        this.products = null;
        this.endDate = null;
        this.startDate = null;
        this.discountType = null;
        this.sku = "";
        this.discountAmount = 0.00;
        this.maxAllowed = 0;
        this.active = false;
    }

    /**
     * Constructor for a Bundle object.
     *
     * @param _name The name of the bundle.
     * @param _description The bundle description.
     * @param _products The GroupList of products in the bundle, a single item
     * for just a discount.
     * @param _discountType The type of discount.
     * @param _discountAmount The amount of the discount, context is determined
     * by the discount type. Percentages are represented such that 75% = 75.0
     * @param _maxAllowed The maximum number of this bundle that can be on one
     * transaction. For no limit use 0.
     * @param _sku The bundle object's SKU.
     * @param _active Whether or not the Bundle is active.
     * @param _startDate The StartDate of the Bundle.
     * @param _endDate The EndDate of the bundle.
     */
    public Bundle(String _name, String _description,
            GroupList<ProductObject> _products, DiscountType _discountType,
            double _discountAmount, int _maxAllowed, String _sku,
            boolean _active, Calendar _startDate, Calendar _endDate) {
        this.name = _name;
        this.description = _description;
        this.products = _products;
        // BOGO only uses a single item and is created as a bundle of 2 of that item.
        if (_discountType == DiscountType.BOGO) {
            ProductObject first = _products.get(0).getElement();
            this.products.clear();
            this.products.add(first, 2);
        }
        this.discountType = _discountType;
        /*
         * Ensure that the discount amount is between 0 and 100.
         * If discountType is percentage or BOGO.
         */
        double tempDoubleAmount = Math.max(0, _discountAmount);
        if (_discountType == DiscountType.BOGO
                || _discountType == DiscountType.PERCENT) {
            tempDoubleAmount = Math.min(tempDoubleAmount, 100);
        }

        this.discountAmount = tempDoubleAmount;
        this.maxAllowed = _maxAllowed;
        this.sku = _sku;
        this.active = _active;
        this.startDate = _startDate;
        this.endDate = _endDate;
    }

    /**
     * Returns a clone of the Bundle object.
     *
     * @return The cloned Bundle
     */
    @Override
    public Bundle clone() {
        return new Bundle(this.name, this.description, this.products.clone(),
                this.discountType, this.discountAmount, this.maxAllowed,
                this.sku, this.active, this.startDate, this.endDate);
    }

    /**
     * Disables the current bundle and updates the endDate. Can only disable
     * valid bundles.
     *
     * @return True if disabled succesfully, false if disable fails or bundle
     * already disabled.
     */
    public boolean disable() {
        if (this.active && (endDate == null
                || this.endDate.compareTo(CalendarUtilities.currentDate()) > 0)) {
            this.active = false;
            this.endDate = CalendarUtilities.currentDate();
            return this.save(this);
        } else {
            return false;
        }
    }

    /**
     * Generator for a bundle, which adds the Bundle to the database and returns
     * the new object. For when startDate and endDate is known.
     *
     * @param _name The name of the bundle.
     * @param _description The bundle description.
     * @param _products The GroupList of products in the bundle, a single item
     * for just a discount.
     * @param _discountType The type of discount.
     * @param _discountAmount The amount of the discount, context is determined
     * by the discount type. Percentages are represented such that 75% = 75.0
     * @param _maxAllowed The maximum number of this bundle that can be on one
     * transaction. For no limit use 0.
     * @param _sku The bundle SKU.
     * @param _active Boolean telling whether the Bundle object is active in the
     * database.
     * @param _startDate String formatted as YYYY-MM-DD to determine date
     * started.
     * @param _endDate String formatted as YYYY-MM-DD to determine date ended.
     * @return The generated Bundle.
     */
    public static Bundle generator(String _name, String _description,
            ArrayList<ProductObject> _products, DiscountType _discountType,
            double _discountAmount, int _maxAllowed, String _sku,
            boolean _active, String _startDate, String _endDate) {
        // Create calendars for the dates.
        Calendar startDate = CalendarUtilities.stringToCalendar(_startDate);
        Calendar endDate = CalendarUtilities.stringToCalendar(_endDate);
        // Take the ArrayList of products and put in a GroupList.
        GroupList<ProductObject> products = new GroupList(BYSKU);
        for (ProductObject prod : _products) {
            products.add(prod);
        }
        // Create the bundle and save it.
        Bundle bundle = new Bundle(_name, _description, products, _discountType,
                _discountAmount, _maxAllowed, _sku, _active, startDate, endDate);

        boolean saved = bundle.save(bundle);
        // If save is succesful, save the products to the db using BundleItems
        if (saved) {
            for (Group<ProductObject> prodGroup : products.toList()) {
                if (saved) {
                    BundleItem item = new BundleItem(bundle.getUuid(),
                            prodGroup.getElement().getUuid(), prodGroup.size());
                    item.save(item);
                }
            }
        }
        // If saving was succesful, return the bundle, if not return false.
        if (saved) {
            return bundle;
        } else {
            return null;
        }
    }

    /**
     * Generator for a bundle, which adds the Bundle to the database and returns
     * the new object. For when endDate is known. startDate is implied to be
     * today's date.
     *
     * @param _name The name of the bundle.
     * @param _description The bundle description.
     * @param _products The GroupList of products in the bundle, a single item
     * for just a discount.
     * @param _discountType The type of discount.
     * @param _discountAmount The amount of the discount, context is determined
     * by the discount type. Percentages are represented such that 75% = 75.0
     * @param _maxAllowed The maximum number of this bundle that can be on one
     * transaction. For no limit use 0.
     * @param _sku The bundle SKU.
     * @param _active Boolean telling whether the Bundle object is active in the
     * database.
     * @param _endDate String formatted as YYYY-MM-DD to determine date ended.
     * @return The generated Bundle.
     */
    public static Bundle generator(String _name, String _description,
            ArrayList<ProductObject> _products, DiscountType _discountType,
            double _discountAmount, int _maxAllowed, String _sku, boolean _active,
            String _endDate) {
        Calendar current = CalendarUtilities.currentDate();
        Calendar startDate = current;
        // Call base generator with the generated startDate.
        return generator(_name, _description, _products, _discountType,
                _discountAmount, _maxAllowed, _sku, _active,
                CalendarUtilities.calendarToString(startDate), _endDate);
    }

    /**
     * Generator for a bundle, which adds the Bundle to the database and returns
     * the new object. Creates a Bundle that will last an indefinite amount of
     * time, starting from today's date.
     *
     * @param _name The name of the bundle.
     * @param _description The bundle description.
     * @param _products The GroupList of products in the bundle, a single item
     * for just a discount.
     * @param _discountType The type of discount.
     * @param _discountAmount The amount of the discount, context is determined
     * by the discount type. Percentages are represented such that 75% = 75.0
     * @param _maxAllowed The maximum number of this bundle that can be on one
     * transaction. For no limit use 0.
     * @param _sku The bundle SKU.
     * @param _active Boolean telling whether the Bundle object is active in the
     * database.
     * @return The generated Bundle.
     */
    public static Bundle generator(String _name, String _description,
            ArrayList<ProductObject> _products, DiscountType _discountType,
            double _discountAmount, int _maxAllowed, String _sku, boolean _active) {
        Calendar current = CalendarUtilities.currentDate();
        Calendar startDate = current;
        Calendar endDate = null;
        // Call base generator with generated start and end dates.
        return generator(_name, _description, _products, _discountType,
                _discountAmount, _maxAllowed, _sku, _active,
                CalendarUtilities.calendarToString(startDate),
                CalendarUtilities.calendarToString(endDate));
    }

    /**
     * Get the Bundle description.
     *
     * @return The Bundle description.
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Indexes the database and returns an array of Bundles which are active.
     *
     * @return An array of Bundle objects
     */
    public static Bundle[] getActiveBundles() {
        ArrayList<Bundle> bundles = new ArrayList();
        for (Bundle bundle : loadAll()) {
            Calendar current = CalendarUtilities.currentDate();
            if (bundle.active && bundle.inDate()) {
                bundles.add(bundle);
            }
        }
        return bundles.toArray(new Bundle[bundles.size()]);
    }

    /**
     * Indexes the database, returning an array of Bundles which match the
     * variable list. Values that don't matter when indexing are specified to be
     * null.
     *
     * @param _active Whether a Bundle object in the database is active.
     * @param _name The name identifier of a Bundle object in the database.
     * @param _description The database description of a Bundle.
     * @param _sku The database SKU of a Bundle.
     * @return
     */
    public static Bundle[] getBundlesBy(Boolean _active, String _name,
            String _description, String _sku) {
        ArrayList<Bundle> bundles = new ArrayList();
        for (Bundle bundle : loadAll()) {
            if (_active != null && _active != bundle.active) {
                continue;
            }
            if (_name != null && _name.equals(bundle.getName())) {
                continue;
            }
            if (_description != null
                    && _description.equals(bundle.getDescription())) {
                continue;
            }
            if (_sku != null && _sku.equals(bundle.getSku())) {
                continue;
            }
            bundles.add(bundle);
        }
        return bundles.toArray(new Bundle[bundles.size()]);
    }

    /**
     * Get the Bundle name.
     *
     * @return The Bundle name.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Gets the max number of the bundle that can be on a single transaction.
     *
     * @return Max allowed of the bundle.
     */
    public int getMaxAllowed() {
        return this.maxAllowed;
    }

    /**
     * Get the price of the bundle which is the price of the items minus the
     * discount amount.
     *
     * @return The Bundle price.
     */
    @Override
    public double getPrice() {
        switch (discountType) {
            case PERCENT:
                return this.products.getTotal() * (100 - this.discountAmount) / 100;
            case DOLLAR:
                return this.products.getTotal() - this.discountAmount;
            case BOGO:
                double elementPrice = this.products.getTotal() / 2;
                return (elementPrice + elementPrice * (100 - this.discountAmount) / 100);
            default:
                return this.products.getTotal();
        }
    }

    /**
     * Get a GroupList consisting of the products that comprise the bundle.
     *
     * @return GroupList of products.
     */
    public GroupList<ProductObject> getProducts() {
        return this.products.clone();
    }

    /**
     * Get the amount of money the bundle saves in dollars.
     *
     * @return The amount saved.
     */
    public double getSavings() {
        return this.products.getTotal() - this.getPrice();
    }

    /**
     * Get the amount of SKU of the Bundle
     *
     * @return The SKU.
     */
    @Override
    public String getSku() {
        return this.sku;
    }

    /**
     * Returns whether today's date is within the Bundle object's startDate and
     * endDate.
     *
     * @return A boolean representation of whether a Bundle is within the
     * specified Calendar range.
     */
    public boolean inDate() {
        if (startDate == null) {
            return true;
        }
        Calendar current = CalendarUtilities.currentDate();
        if (startDate.compareTo(current) > 0) {
            return false;
        }
        if (endDate == null) {
            return true;
        }
        if (current.compareTo(endDate) <= 0) {
            return true;
        }
        return false;
    }

    /**
     * Method that returns all bundles in the database.
     *
     * @return Array of Bundles
     */
    public static Bundle[] loadAll() {
        Bundle[] bundles = (Bundle[]) loadAll(new Bundle());
        // Loop and load the products in each bundle.
        for (Bundle bundle : bundles) {
            loadProducts(bundle);
        }
        return bundles;
    }

    /**
     * Load the Bundle with the given SKU.
     *
     * @param _sku The SKU to match on.
     * @return The Bundle with matching SKU.
     */
    public static Bundle loadBySku(String _sku) {
        Bundle bundle = (Bundle) Bundle.load("sku", _sku, new Bundle());
        // Load the products for the Bundle.
        loadProducts(bundle);
        return bundle;
    }

    /**
     * Load the Bundle with matching UUID.
     *
     * @param _uuid The UUID to match on.
     * @return The matching Bundle.
     */
    public static Bundle loadByUUID(String _uuid) {
        Bundle bundle = (Bundle) Bundle.load("uuid", _uuid, new Bundle());
        // Load the products for the Bundle.
        loadProducts(bundle);
        return bundle;
    }

    /**
     * Method for loading a Bundle's products.
     *
     * @param _bundle The Bundle to load the Product objects from.
     */
    private static void loadProducts(Bundle _bundle) {
        // Verify that bundles aren't already loaded.
        if (_bundle.getProducts().size() == 0) {
            // Get all the BundleItems that represent the products and add each
            // to the Bundle.
            BundleItem[] items = BundleItem.loadByBundleID(_bundle.getUuid());
            GroupList<ProductObject> prodList = new GroupList(BYSKU);
            for (BundleItem item : items) {
                ProductObject prod = (ProductObject) ProductObject.loadByUUID(item.getProductID(), new ProductObject());
                prodList.add(prod, item.getQty());
            }
            _bundle.addProducts(prodList);
        }
    }

    /**
     * Bundles cannot be saved, only created or disabled
     *
     * @return Always returns false.
     */
    @Override
    public boolean save(BoffoDbObject _obj) {
        return false;
    }

    private boolean save() {
        return super.save(this);
    }

    /**
     * Returns a String representation of the Bundle.
     *
     * @return The String.
     */
    @Override
    public String toString() {
        return "Bundle:" + "[" + this.name + this.description + "]" + " "
                + this.discountType.formatString(this.discountAmount);
    }

    /**
     * Returns a List of TicketElements (products and bundles). Takes a list of
     * products that represent all the items on a transaction. The returned list
     * will contain the products passed in as well as bundles. The bundles will
     * represent the best possible discount, including changing the bundles that
     * were in the original list.
     *
     * @param _products A List of Products.
     * @return A List of TicketElements containing Bundles and Products.
     */
    public static List<TicketElement> updateBundles(List<ProductObject> _products) {
        GroupList<ProductObject> products = new GroupList(BYSKU);
        for (ProductObject product : _products) {
            products.add(product);
        }
        return updateBundles(products).toAbsoluteList();
    }

    // <editor-fold desc="Helper Methods of updateBundles().">
    /**
     * Gets the list of bundles that apply to a list of products from a list of
     * bundles. The list of bundles will have the maximum number of each bundle
     * that could be applied.
     *
     * @param _allProducts The list of Products to check.
     * @param _fromBundles The list of Bundles to add from.
     * @return A GroupList of Bundles containing all applicable Bundles.
     */
    private static GroupList<Bundle> getApplicable(GroupList<ProductObject> _allProducts,
            Bundle[] _fromBundles) {
        GroupList<Bundle> applicable = new GroupList(BYSKU);
        // Iterate over all the bundles and see if there are matching products.
        for (Bundle bundle : _fromBundles) {
            GroupList<ProductObject> bundleProducts = bundle.getProducts();
            int num = 0;
            // If _allProducts contains the products in bundle, then loop adding
            // to the count of bundle until the max number that the products can
            // support or max allowed is reached.
            if (_allProducts.contains(bundle.getProducts())) {
                while (_allProducts.contains(bundleProducts)) {
                    for (Group<ProductObject> p : bundle.getProducts().toList()) {
                        bundleProducts.add(p.getContents());
                    }
                    num++;
                    if (num >= bundle.getMaxAllowed()) {
                        break;
                    }
                }
            }
            // Add the bundle to the list of bundles.
            applicable.add(bundle.clone(), num);
        }
        return applicable;
    }

    /**
     * Gets the list of bundles that apply to a list of products. The list of
     * bundles will have the maximum number of each bundle that could be
     * applied.
     *
     * @param _allProducts The list of Products to check.
     * @return A GroupList of Bundles containing all applicable Bundles.
     */
    private static GroupList<Bundle> getApplicable(GroupList<ProductObject> _allProducts) {
        // Call getApplicable with allBundles. Will be replaced by DB call
        // loading all bundles.
        return getApplicable(_allProducts, getBundlesBy(true, null, null, null));
    }

    /**
     * Method for pruning the list of bundles based on the a list of products.
     * After selecting a bundle to use, the items removed from products may make
     * some bundles no longer applicable and they are removed from the bundles
     * list (or their quantity is reduced to match the available products).
     *
     * @param _products The list of products.
     * @param _bundles The list of bundles.
     * @param _elements The list of elements.
     * @return A GroupList of Bundles that no longer includes invalidated
     * bundles
     */
    private static GroupList<Bundle> pruneBundles(GroupList<ProductObject> _products, GroupList<Bundle> _bundles) {
        GroupList<Bundle> retList = new GroupList(BYSKU);
        // for each bundle in _bundles get the max number that is possible with
        // _products and add that many of the bundle to retList
        for (Group<Bundle> group : _bundles.toList()) {
            int num = group.size();
            for (Group<ProductObject> prod : group.getElement().getProducts().toList()) {
                num = Math.min(num, _products.numberOf(prod.getElement()) / prod.size());
            }
            if (num > 0) {
                retList.add(group.getElement(), num);
            }
        }
        return retList;
    }

    /**
     * Recursive method for finding optimal bundles and discounts. It
     * recursively tries every combination of using (left recursion) or not
     * using (right recursion) each possible bundle. It then returns the best
     * (lowest total price) returned Elements list.
     *
     * @param _products The list of of products remaining that can be used.
     * @param _bundles The list of bundles left to check.
     * @param _elements The list of elements that have been added to the ticket.
     * @return A GroupList of Bundles that no longer includes invalidated
     * bundles
     */
    private static GroupList<TicketElement> recursiveUpdateBundles(GroupList<ProductObject> _products,
            GroupList<Bundle> _bundles,
            GroupList<TicketElement> _elements) {
        // If the bundle list being referenced is empty, add remaining products
        // to elements and return elements.
        if (_bundles.isEmpty()) {
            for (Group<ProductObject> group : _products.toList()) {
                for (ProductObject product : group.getContents()) {
                    _elements.add(product);
                }
            }
            return _elements;
        }

        // Get the bundle to process on this recursion and reduce the number in _bundles by 1
        Group<Bundle> bundleGroup = _bundles.get(0);
        _bundles.remove(bundleGroup.getElement(), 1);
        // Perform right recursion with copies of current info (bundle not used).
        GroupList<TicketElement> rightElements = recursiveUpdateBundles(_products.clone(),
                _bundles.clone(), _elements.clone());

        // Remove products from _products and add them to a GroupList to create
        // a BundleWrapper containing the bundle and products it contains.
        GroupList<ProductObject> productsToAdd = new GroupList(_products.comparator());
        for (Group p : bundleGroup.getElement().getProducts().toList()) {
            List<ProductObject> productList = _products.remove(p);
            productsToAdd.add(productList);
        }
        BundleWrapper bw = new BundleWrapper(bundleGroup.getElement(), productsToAdd);
        _elements.add(bw);
        // Since left recursion removes products, prune before recursing.
        _bundles = pruneBundles(_products, _bundles);
        GroupList<TicketElement> leftElements = recursiveUpdateBundles(_products, _bundles, _elements);
        if (GroupList.BYPRICE.compare(leftElements, rightElements) <= 0) {
            return leftElements;
        } else {
            return rightElements;
        }
    }

    /**
     * Returns a GroupList of TicketElements (products and bundles). Takes a
     * group list of products that represent all the items on a transaction
     * (with no bundles). The returned list will contain the products passed in
     * as well as bundles. When bundles are added, the corresponding products
     * are removed and added as to the bundle that represents them. This method
     * preprocesses the lists and then calls the recursive method.
     *
     * @param _products A GroupList of Products.
     * @return A GroupList of TicketElements containing Bundles and Products.
     */
    private static GroupList<TicketElement> updateBundles(GroupList<ProductObject> _products) {
        // Get list of applicable bundles and create new (empty) list of elements.
        GroupList<Bundle> bundles = getApplicable(_products);
        GroupList<TicketElement> elements = new GroupList(BYSKU);
        return Bundle.recursiveUpdateBundles(_products.clone(), bundles.clone(),
                elements.clone());
    }

    // </editor-fold>
}
