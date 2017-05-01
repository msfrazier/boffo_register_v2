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
 * @TODO Use utility format price for toString().
 * @TODO Add updateBundles to receive and return a ArrayList of TicketElements
 * @TODO loadBySKU, loadAll
 *
 * @author Michael Resnik
 * @author Travis Cox
 * @lastEdited: 4/18/2017
 */
public class Bundle extends BoffoDbObject implements TicketElement {

    // List of all bundles, will be removed and be replaced by database call.
    public static List<Bundle> allBundles = new ArrayList();
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
    private final Calendar endDate;

    protected static String tableName = "bundle_tbl";


    public Bundle(){
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
     * Constructor for a bundle
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
     * @param _sku The bundle SKU
     */
    public Bundle(String _name, String _description,
            GroupList<ProductObject> _products, DiscountType _discountType,
            double _discountAmount, int _maxAllowed, String _sku, boolean _active, Calendar _startDate, Calendar _endDate) {
        // TODO Validate input and throw exceptions.
        this.name = _name;
        this.description = _description;
        this.products = _products;
        // BOGO only uses a single item and is created as a bundle of 2 of that item
        if (_discountType == DiscountType.BOGO) {
            ProductObject first = _products.get(0).getElement();
            this.products.clear();
            this.products.add(first, 2);
        }
        this.discountType = _discountType;

        // Ensure that the discount amount is at least 0, and less than 100 if
        // a percentage or BOGO.
        double tempDoubleAmount = Math.max(0, _discountAmount);
        if (_discountType == DiscountType.BOGO || _discountType == DiscountType.PERCENT) {
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
     * Clones the Bundle.
     *
     * @return The cloned Bundle
     */
    @Override
    public Bundle clone() {
        return new Bundle(this.name, this.description, this.products.clone(),
                this.discountType, this.discountAmount, this.maxAllowed, this.sku, this.active, this.startDate, this.endDate);
    }


    /**
     * Generator for a bundle
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
     * @param _sku The bundle SKU
     * @return The generated Bundle.
     */
    public static Bundle generator(String _name, String _description,
            GroupList<ProductObject> _products, DiscountType _discountType,
            double _discountAmount, int _maxAllowed, String _sku, boolean _active,
            String _startDate, String _endDate) {

        Calendar startDate = CalendarUtilities.stringToCalendar(_startDate);
        Calendar endDate = CalendarUtilities.stringToCalendar(_endDate);

        Bundle b = new Bundle(_name, _description, _products, _discountType,
                _discountAmount, _maxAllowed, _sku, _active, startDate, endDate);
        add(b);
        return b.clone();
    }


    public static Bundle generator(String _name, String _description,
            GroupList<ProductObject> _products, DiscountType _discountType,
            double _discountAmount, int _maxAllowed, String _sku, boolean _active,
            String _endDate) {
        Calendar current = CalendarUtilities.currentDate();
        Calendar startDate = current;
        Calendar endDate = CalendarUtilities.stringToCalendar(_endDate);

        Bundle b = new Bundle(_name, _description, _products, _discountType,
                _discountAmount, _maxAllowed, _sku, _active, startDate, endDate);
        add(b);
        return b.clone();
    }


    public static Bundle generator(String _name, String _description,
            GroupList<ProductObject> _products, DiscountType _discountType,
            double _discountAmount, int _maxAllowed, String _sku, boolean _active) {
        Calendar current = CalendarUtilities.currentDate();
        Calendar startDate = current;
        Calendar endDate = null;
        Bundle b = new Bundle(_name, _description, _products, _discountType,
                _discountAmount, _maxAllowed, _sku, _active, startDate, endDate);
        add(b);
        return b.clone();
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


    public static Bundle[] getActiveBundles() {
        ArrayList<Bundle> bundles = new ArrayList();
        for (Bundle bundle : allBundles) {
            if (bundle.active) {
                bundles.add(bundle);
            }
        }
        return bundles.toArray(new Bundle[bundles.size()]);
    }

    public static Bundle[] getBundlesBy(Boolean _active, String _name, String _description, String _sku) {
        ArrayList<Bundle> bundles = new ArrayList();
        for (Bundle bundle : allBundles) {
            if (_active != null && _active != bundle.active){
                continue;
            }
            if(_name != null && _name.equals(bundle.getName()) == false){
                continue;
            }
            if(_description != null && _description.equals(bundle.getDescription()) == false){
                continue;
            }
            if(_sku != null && _sku.equals(bundle.getSku()) == false){
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


    @Override
    public String getSku() {
        return this.sku;
    }


    public boolean inRange() {
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
        return getApplicable(_allProducts, getBundlesBy(true,null,null,null));
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


    // TODO replace following static methods with getBundles()
    // <editor-fold desc="Operations for the static List of Bundles named "allBundles." ">
    private static void add(Bundle _b) {
        // _b.save(_b);
        if (!contains(_b)) {
            allBundles.add(_b);
        }
    }


    public static void remove(Bundle _b) {
        // _b.delete(_b);
        if (contains(_b)) {
            allBundles.remove(indexOf(_b));
        }
    }


    private static boolean contains(Bundle _b) {
        return indexOf(_b) != -1;
    }


    private static int indexOf(Bundle _b) {
        int index = 0;
        for (Bundle bundle : allBundles) {
            if (BYSKU.compare(bundle, _b) == 0) {
                return index;
            }
            index++;
        }
        return -1;
    }

    //</editor-fold>

}
