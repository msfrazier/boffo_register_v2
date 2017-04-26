package bundles;

import java.util.ArrayList;
import java.util.List;

/**
 * Bundle class for storing and using bundles and discounts, contains methods
 * for processing products into lists including discounts.
 *
 * @author Michael Resnik
 * @author Travis Cox
 * @lastEdited: 4/18/2017
 */
public class Bundle implements TicketElement {

    // List of all bundles, will be removed and be replaced by database call.
    public static List<Bundle> allBundles = new ArrayList();
    // List of the products that comprise a bundle or discount.
    private final GroupList<Product_Test> products;

    private final DiscountType discountType;
    // Discount amount (based on discountType).
    private final double discountAmount;
    // The max number of the bundle or discount that can be on one transaction.
    private final int maxAllowed;
    private String name;
    private String description;
    private String sku;


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
    private Bundle(String _name, String _description,
            GroupList<Product_Test> _products, DiscountType _discountType,
            double _discountAmount, int _maxAllowed, String _sku) {
        // TODO Validate input and throw exceptions.
        this.name = _name;
        this.description = _description;
        this.products = _products;
        // BOGO only uses a single item and is created as a bundle of 2 of that item
        if (_discountType == DiscountType.BOGO) {
            Product_Test first = _products.get(0).getElement();
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
    }


    /**
     * Clones the Bundle.
     *
     * @return The cloned Bundle
     */
    @Override
    public Bundle clone() {
        return new Bundle(this.name, this.description, this.products.clone(), this.discountType, this.discountAmount, this.maxAllowed, this.sku);
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
    public static Bundle generator(String _name, String _description, GroupList<Product_Test> _products, DiscountType _discountType, double _discountAmount, int _maxAllowed, String _sku) {
        Bundle b = new Bundle(_name, _description, _products, _discountType, _discountAmount, _maxAllowed, _sku);
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
                return products.getTotal() * (100 - discountAmount) / 100;
            case DOLLAR:
                return products.getTotal() - discountAmount;
            case BOGO:
                double elementPrice = products.getTotal() / 2;
                return (elementPrice + elementPrice * (100 - discountAmount) / 100);
            default:
                return products.getTotal();
        }
    }


    /**
     * Get a GroupList consisting of the products that comprise the bundle.
     *
     * @return GroupList of products.
     */
    public GroupList<Product_Test> getProducts() {
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


    @Override
    public String toString() {
        return "Bundle:" + "[" + this.name + this.description + "]" + " " + this.discountType.formatString(this.discountAmount);
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
    public static GroupList<TicketElement> updateBundles(GroupList<Product_Test> _products) {
        // Get list of applicable bundles and create new (empty) list of elements.
        GroupList<Bundle> bundles = getApplicable(_products);
        GroupList<TicketElement> elements = new GroupList(BYSKU);
        return Bundle.recursiveMethod(_products.clone(), bundles.clone(),
                elements.clone());
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
    private static GroupList<Bundle> getApplicable(GroupList<Product_Test> _allProducts, List<Bundle> _fromBundles) {
        GroupList<Bundle> applicable = new GroupList(BYSKU);
        // Iterate over all the bundles and see if there are matching products.
        for (Bundle bundle : _fromBundles) {
            GroupList<Product_Test> bundleProducts = bundle.getProducts();
            int num = 0;
            // If _allProducts contains the products in bundle, then loop adding
            // to the count of bundle until the max number that the products can
            // support or max allowed is reached.
            if (_allProducts.contains(bundle.getProducts())) {
                while (_allProducts.contains(bundleProducts)) {
                    for (Group<Product_Test> p : bundle.getProducts().toList()) {
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
    private static GroupList<Bundle> getApplicable(GroupList<Product_Test> _allProducts) {
        // Call getApplicable with allBundles. Will be replaced by DB call
        // loading all bundles.
        return getApplicable(_allProducts, allBundles);
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
    private static GroupList<Bundle> pruneBundles(GroupList<Product_Test> _products, GroupList<Bundle> _bundles) {
        GroupList<Bundle> retList = new GroupList(BYSKU);
        // for each bundle in _bundles get the max number that is possible with
        // _products and add that many of the bundle to retList
        for (Group<Bundle> group : _bundles.toList()) {
            int num = group.size();
            for (Group<Product_Test> prod : group.getElement().getProducts().toList()) {
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
    private static GroupList<TicketElement> recursiveMethod(GroupList<Product_Test> _products, GroupList<Bundle> _bundles,
            GroupList<TicketElement> _elements) {
        // If the bundle list being referenced is empty, add remaining products
        // to elements and return elements.
        if (_bundles.isEmpty()) {
            for (Group<Product_Test> group : _products.toList()) {
                for (Product_Test product : group.getContents()) {
                    _elements.add(product);
                }
            }
            return _elements;
        }

        // Get the bundle to process on this recursion and reduce the number in _bundles by 1
        Group<Bundle> bundleGroup = _bundles.get(0);
        _bundles.remove(bundleGroup.getElement(), 1);
        // Perform right recursion with copies of current info (bundle not used).
        GroupList<TicketElement> rightElements = recursiveMethod(_products.clone(), _bundles.clone(), _elements.clone());

        // Remove products from _products and add them to a GroupList to create
        // a BundleWrapper containing the bundle and products it contains.
        GroupList<Product_Test> productsToAdd = new GroupList(_products.comparator());
        for (Group p : bundleGroup.getElement().getProducts().toList()) {
            List<Product_Test> productList = _products.remove(p);
            productsToAdd.add(productList);
        }
        BundleWrapper bw = new BundleWrapper(bundleGroup.getElement(), productsToAdd);
        _elements.add(bw);
        // Since left recursion removes products, prune before recursing.
        _bundles = pruneBundles(_products, _bundles);
        GroupList<TicketElement> leftElements = recursiveMethod(_products, _bundles, _elements);
        if (GroupList.BYPRICE.compare(leftElements, rightElements) <= 0) {
            return leftElements;
        } else {
            return rightElements;
        }
    }
    // </editor-fold>


    // TODO replace following static methods with getBundles()
    // <editor-fold desc="Operations for the static List of Bundles named "allBundles." ">
    private static void add(Bundle _b) {
        if (!contains(_b)) {
            allBundles.add(_b);
        }
    }


    public static void remove(Bundle _b) {
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
