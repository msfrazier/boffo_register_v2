package bundles;

import static bundles.TicketElement.*;
import database.BoffoDatabaseAPI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import product.ProductObject;
import product.Rating;

public class BundleTests {

    public static void main(String[] args) {
        BoffoDatabaseAPI.getInstance().dbLogin("mike", "resnik");
        long base = System.currentTimeMillis();
        //testRecursive(Group.BYPRICE);
        testWithLists(Group.BYPRICE);
        System.out.println("Time Taken ms:" + (System.currentTimeMillis() - base));
        // System.out.println("--");
        // testTicket();
        // testSort();
        testDates();
    }


    private static void printElements(GroupList<TicketElement> _elements) {
        System.out.println("Optimized Elements");

        for (Group<TicketElement> ePair : _elements.toList()) {
            System.out.println("$" + (ePair.getElement()).getPrice() * ePair.size() + "\t\t" + ePair.toString() + "\t SKU:" + ePair.getElement().getSku());
            if (ePair.getElement().getClass().equals(BundleWrapper.class)) {
                for (int i = 0; i < ePair.size(); i++) {
                    System.out.println("\t\t\t-->" + ((BundleWrapper) ePair.getElement()).getBundle().getProducts().toList().toString());
                }
            }
        }
        System.out.println("--------");
        System.out.println("final price:" + _elements.getTotal());

        System.out.println(_elements.comparator());

    }


    private static void printElements(List<TicketElement> _elements) {
        System.out.println("Optimized Elements");
        double total = 0.0;
        for (TicketElement element : _elements) {
            total += element.getPrice();
            System.out.println("$" + element.getPrice() + "\t\t" + element.toString() + "\t SKU:" + element.getSku());
            if (element.getClass().equals(BundleWrapper.class)) {
                String products = "";
                for (ProductObject prod : ((BundleWrapper) element).getProducts().toAbsoluteList()) {
                    products += prod.toString() + " ";
                }
                System.out.println("\t\t\t-->" + products);
            }
        }
        System.out.println("--------");
        System.out.println("final price:" + total);

        //System.out.println(_elements.comparator());
    }


    public static void testRecursive(Comparator<Group<TicketElement>> _sortBy) {

        //<editor-fold desc="Create Products, PairLists, and Bundles.">
        /**
         * Create Products for later PairList and Bundle parameters.
         */
        
        // String _name, int _quant, double _price, int _UPC, String _sk, Rating _rat, String _upc, String _tableName, String _description
        ProductObject p1 = generateProduct("p1", "", 3.75, "P1");
        ProductObject p2 = generateProduct("p2", "", 4.00, "P2");
        ProductObject p3 = generateProduct("p3", "", 3.50, "P3");
        ProductObject p4 = generateProduct("p4", "", 3.25, "P4");
        ProductObject p5 = generateProduct("p5", "", 3.00, "P5");
        GroupList<ProductObject> products = new GroupList(BYSKU);
        products.add(p1, 3);
        products.add(p2, 5);
        products.add(p3, 4);
        products.add(p4, 6);
        products.add(p5, 4);

        /**
         * Make PairList parameters in order to make Bundles later.
         */
        GroupList<ProductObject> b1PL = new GroupList(BYNAME);
        GroupList<ProductObject> b2PL = new GroupList(BYNAME);
        GroupList<ProductObject> b3PL = new GroupList(BYNAME);
        GroupList<ProductObject> b4PL = new GroupList(BYNAME);
        GroupList<ProductObject> b5PL = new GroupList(BYNAME);
        GroupList<ProductObject> b6PL = new GroupList(BYNAME);

        // <editor-fold desc="Add all Products to Bundles.">
        /**
         * Add all Products to Bundle : b1.
         * <b1> : <p1, 2> , <p2, 3>, <p3, 1>
         */
        b1PL.add(p1, 2);
        b1PL.add(p2, 3);
        b1PL.add(p3, 1);
        Bundle b1 = Bundle.generator("b1", "", b1PL, DiscountType.PERCENT, 10, 2, "b1", true, "2017-12-25");

        /**
         * Add all Products to Bundle : b2.
         * <b2> : <p1, 1>, <p2, 2>, <p5, 1>
         */
        b2PL.add(p1, 1);
        b2PL.add(p2, 2);
        b2PL.add(p5, 1);
        Bundle b2 = Bundle.generator("b2", "", b2PL, DiscountType.PERCENT, 30, 1, "b2", true, "2016-01-01", "2017-05-24");

        /**
         * Add all Products to Bundle : b3.
         * <b3> : <p4, 1>, <p5, 1>
         */
        b3PL.add(p4, 1);
        b3PL.add(p5, 1);
        Bundle b3 = Bundle.generator("b3", "", b3PL, DiscountType.PERCENT, 25, 1, "b3", true);

        /**
         * Add all Products to Bundle : b4.
         * <b4> : <p2, 2>, <p3, 2>
         */
        b4PL.add(p2, 2);
        b4PL.add(p3, 2);
        Bundle b4 = Bundle.generator("b4", "", b4PL, DiscountType.PERCENT, 05, 3, "b4", true);

        /**
         * Add all Products to Bundle : b5.
         * <b5> : <p1, 1>, <p3, 2>
         */
        b5PL.add(p1, 1);
        b5PL.add(p3, 2);
        Bundle b5 = Bundle.generator("b5", "", b5PL, DiscountType.PERCENT, 10, 3, "b5", true);

        /**
         * Add all Products to Bundle : b6.
         * <b6> : <p4, 2>
         */
        b6PL.add(p4, 2);
        Bundle b6 = Bundle.generator("b6", "", b6PL, DiscountType.BOGO, 100, 2, "b6", true);

        // </editor-fold>
        //</editor-fold>
//        GroupList<TicketElement> elements = Bundle.updateBundles(products);
//        elements.sort(_sortBy);
//        printElements(elements);
    }


    private static void testSort() {
        GroupList<TicketElement> elements = new GroupList(BYPRICE);
        ProductObject p1 = generateProduct("p1", "", 3.75, "P1");
        ProductObject p2 = generateProduct("p2", "", 4.00, "P2");
        ProductObject p3 = generateProduct("p3", "", 3.50, "P3");
        ProductObject p4 = generateProduct("p4", "", 3.25, "P4");
        ProductObject p5 = generateProduct("p5", "", 3.00, "P5");
        elements.add(p1, 3);
        elements.add(p2, 5);
        elements.add(p3, 4);
        elements.add(p4, 6);
        elements.add(p5, 4);
        elements.sort(Group.BYPRICE);
        for (Group<TicketElement> pair : elements.toList()) {
            System.out.println(pair.toString() + pair.getPrice());
        }

    }


    /**
     * testTicket(). private static void testTicket() { Ticket_Test ticket = new
     * Ticket_Test(); ProductObject p1 = generateProduct("p1", "", 3.75,
     * "P1"); ProductObject p2 = generateProduct("p2", "", 4.00, "P2");
     * ProductObject p3 = generateProduct("p3", "", 3.50, "P3");
     * ProductObject p4 = generateProduct("p4", "", 3.25, "P4");
     * ProductObject p5 = generateProduct("p5", "", 3.00, "P5");
     * ticket.add(p1, 3); ticket.add(p2, 5); ticket.add(p3, 4); ticket.add(p4,
     * 6); ticket.add(p5, 4);
     *
     * System.out.println(ticket.toString());
     *
     * }
     * //
     */
    public static void testWithLists(Comparator<Group<TicketElement>> _sortBy) {

        //<editor-fold desc="Create Products, PairLists, and Bundles.">
        /**
         * Create Products for later PairList and Bundle parameters.
         */
        ProductObject p1 = generateProduct("p1", "", 3.75, "P1");
        ProductObject p2 = generateProduct("p2", "", 4.00, "P2");
        ProductObject p3 = generateProduct("p3", "", 3.50, "P3");
        ProductObject p4 = generateProduct("p4", "", 3.25, "P4");
        ProductObject p5 = generateProduct("p5", "", 3.00, "P5");

        /**
         * Make PairList parameters in order to make Bundles later.
         */
        GroupList<ProductObject> b1PL = new GroupList(BYSKU);
        GroupList<ProductObject> b2PL = new GroupList(BYSKU);
        GroupList<ProductObject> b3PL = new GroupList(BYSKU);
        GroupList<ProductObject> b4PL = new GroupList(BYSKU);
        GroupList<ProductObject> b5PL = new GroupList(BYSKU);
        GroupList<ProductObject> b6PL = new GroupList(BYSKU);

        // <editor-fold desc="Add all Products to Bundles.">
                
        /**
         * Add all Products to Bundle : b1.
         * <b1> : <p1, 2> , <p2, 3>, <p3, 1>
         */
        b1PL.add(p1, 2);
        b1PL.add(p2, 3);
        b1PL.add(p3, 1);
        Bundle b1 = Bundle.generator("b1", "", b1PL, DiscountType.PERCENT, 10, 2, "b1", true);

        /**
         * Add all Products to Bundle : b2.
         * <b2> : <p1, 1>, <p2, 2>, <p5, 1>
         */
        b2PL.add(p1, 1);
        b2PL.add(p2, 2);
        b2PL.add(p5, 1);
        Bundle b2 = Bundle.generator("b2", "", b2PL, DiscountType.PERCENT, 30, 1, "b2", true);

        /**
         * Add all Products to Bundle : b3.
         * <b3> : <p4, 1>, <p5, 1>
         */
        b3PL.add(p4, 1);
        b3PL.add(p5, 1);
        Bundle b3 = Bundle.generator("b3", "", b3PL, DiscountType.PERCENT, 25, 1, "b3", false);

        /**
         * Add all Products to Bundle : b4.
         * <b4> : <p2, 2>, <p3, 2>
         */
        b4PL.add(p2, 2);
        b4PL.add(p3, 2);
        Bundle b4 = Bundle.generator("b4", "", b4PL, DiscountType.PERCENT, 05, 3, "b4", true);

        /**
         * Add all Products to Bundle : b5.
         * <b5> : <p1, 1>, <p3, 2>
         */
        b5PL.add(p1, 1);
        b5PL.add(p3, 2);
        Bundle b5 = Bundle.generator("b5", "", b5PL, DiscountType.PERCENT, 10, 3, "b5", true);

        /**
         * Add all Products to Bundle : b6.
         * <b6> : <p4, 2>
         */
        b6PL.add(p4, 2);
        Bundle b6 = Bundle.generator("b6", "", b6PL, DiscountType.BOGO, 100, 2, "b6", true);

        // </editor-fold>
        //</editor-fold>
        List<TicketElement> elements;
        List<ProductObject> products = new ArrayList();
//        GroupList<TicketElement> products = Bundle.updateBundles(products);
        products.add(generateProduct("p1-1", "", 3.75, "P1"));
//        elements = Bundle.updateBundles(products);
//        printElements(elements);
        products.add(generateProduct("p2-1", "", 4.00, "P2"));
//        elements = Bundle.updateBundles(products);
//        printElements(elements);
        products.add(generateProduct("p4-1", "", 3.25, "P4"));
//        elements = Bundle.updateBundles(products);
//        printElements(elements);
        products.add(generateProduct("p3-1", "", 3.50, "P3"));
//        elements = Bundle.updateBundles(products);
//        printElements(elements);
        products.add(generateProduct("p1-2", "", 3.75, "P1"));
//        elements = Bundle.updateBundles(products);
//        printElements(elements);
        products.add(generateProduct("p5-1", "", 3.00, "P5"));
//        elements = Bundle.updateBundles(products);
//        printElements(elements);
        products.add(generateProduct("p2-2", "", 4.00, "P2"));
//        elements = Bundle.updateBundles(products);
//        printElements(elements);
        products.add(generateProduct("p1-3", "", 3.75, "P1"));
//        elements = Bundle.updateBundles(products);
//        printElements(elements);
        products.add(generateProduct("p2-3", "", 4.00, "P2"));
//        elements = Bundle.updateBundles(products);
//        printElements(elements);
        products.add(generateProduct("p4-2", "", 3.25, "P4"));
//        elements = Bundle.updateBundles(products);
//        printElements(elements);
        products.add(generateProduct("p2-4", "", 4.00, "P2"));
//        elements = Bundle.updateBundles(products);
//        printElements(elements);
        products.add(generateProduct("p3-2", "", 3.50, "P3"));
//        elements = Bundle.updateBundles(products);
//        printElements(elements);
        products.add(generateProduct("p5-2", "", 3.00, "P5"));
//        elements = Bundle.updateBundles(products);
//        printElements(elements);
        products.add(generateProduct("p4-3", "", 3.25, "P4"));
//        elements = Bundle.updateBundles(products);
//        printElements(elements);
        products.add(generateProduct("p2-5", "", 4.00, "P2"));
//        elements = Bundle.updateBundles(products);
//        printElements(elements);
        products.add(generateProduct("p3-3", "", 3.50, "P3"));
//        elements = Bundle.updateBundles(products);
//        printElements(elements);
        products.add(generateProduct("p4-4", "", 3.25, "P4"));
//        elements = Bundle.updateBundles(products);
//        printElements(elements);
        products.add(generateProduct("p5-3", "", 3.00, "P5"));
//        elements = Bundle.updateBundles(products);
//        printElements(elements);
        products.add(generateProduct("p3-4", "", 3.50, "P3"));
//        elements = Bundle.updateBundles(products);
//        printElements(elements);
        products.add(generateProduct("p4-5", "", 3.25, "P4"));
//        elements = Bundle.updateBundles(products);
//        printElements(elements);
        products.add(generateProduct("p5-4", "", 3.00, "P5"));
//        elements = Bundle.updateBundles(products);
//        printElements(elements);
        products.add(generateProduct("p4-6", "", 3.25, "P4"));
        elements = Bundle.updateBundles(products);
        printElements(elements);

    }


    public static void testDates() {
        ProductObject p1 = generateProduct("p1", "", 3.75, "P1");
        ProductObject p2 = generateProduct("p2", "", 4.00, "P2");
        ProductObject p3 = generateProduct("p3", "", 3.50, "P3");
        ProductObject p4 = generateProduct("p4", "", 3.25, "P4");
        ProductObject p5 = generateProduct("p5", "", 3.00, "P5");

        /**
         * Make PairList parameters in order to make Bundles later.
         */
        GroupList<ProductObject> b1PL = new GroupList(BYSKU);
        GroupList<ProductObject> b2PL = new GroupList(BYSKU);
        GroupList<ProductObject> b3PL = new GroupList(BYSKU);
        GroupList<ProductObject> b4PL = new GroupList(BYSKU);
        GroupList<ProductObject> b5PL = new GroupList(BYSKU);
        GroupList<ProductObject> b6PL = new GroupList(BYSKU);

        // <editor-fold desc="Add all Products to Bundles.">
        /**
         * Add all Products to Bundle : b1.
         * <b1> : <p1, 2> , <p2, 3>, <p3, 1>
         */
        b1PL.add(p1, 2);
        b1PL.add(p2, 3);
        b1PL.add(p3, 1);
        Bundle b1 = Bundle.generator("b1", "", b1PL, DiscountType.PERCENT, 10, 2, "b1", true, "2016-05-12", "2017-05-15");

        System.out.println(b1.inRange());
    }
    
    public static ProductObject generateProduct(String _name, String _description, double _price, String _sku){
        ProductObject retProduct = new ProductObject(_name, 1, _price, 0, _sku, new Rating("e", 5), "upc", "product", _description);
        return retProduct;
    }


}
