package bundles;

import static bundles.TicketElement.*;
import java.util.Comparator;

public class BundleTests {

    public static void main(String[] args) {
        long base = System.currentTimeMillis();
        testRecursive(Group.BYPRICE);
        System.out.println("Time Taken ms:" + (System.currentTimeMillis() - base));
        // System.out.println("--");
        // testTicket();
        // testSort();
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


    public static void testRecursive(Comparator<Group<TicketElement>> _sortBy) {

        //<editor-fold desc="Create Products, PairLists, and Bundles.">
        /**
         * Create Products for later PairList and Bundle parameters.
         */
        Product_Test p1 = Product_Test.generate("p1", "", 3.75, "P1");
        Product_Test p2 = Product_Test.generate("p2", "", 4.00, "P2");
        Product_Test p3 = Product_Test.generate("p3", "", 3.50, "P3");
        Product_Test p4 = Product_Test.generate("p4", "", 3.25, "P4");
        Product_Test p5 = Product_Test.generate("p5", "", 3.00, "P5");
        GroupList<Product_Test> products = new GroupList(BYSKU);
        products.add(p1, 3);
        products.add(p2, 5);
        products.add(p3, 4);
        products.add(p4, 6);
        products.add(p5, 4);

        /**
         * Make PairList parameters in order to make Bundles later.
         */
        GroupList<Product_Test> b1PL = new GroupList(BYNAME);
        GroupList<Product_Test> b2PL = new GroupList(BYNAME);
        GroupList<Product_Test> b3PL = new GroupList(BYNAME);
        GroupList<Product_Test> b4PL = new GroupList(BYNAME);
        GroupList<Product_Test> b5PL = new GroupList(BYNAME);
        GroupList<Product_Test> b6PL = new GroupList(BYNAME);

        // <editor-fold desc="Add all Products to Bundles.">
        /**
         * Add all Products to Bundle : b1.
         * <b1> : <p1, 2> , <p2, 3>, <p3, 1>
         */
        b1PL.add(p1, 2);
        b1PL.add(p2, 3);
        b1PL.add(p3, 1);
        Bundle b1 = Bundle.generator("b1", "", b1PL, DiscountType.PERCENT, 10, 2, "b1");

        /**
         * Add all Products to Bundle : b2.
         * <b2> : <p1, 1>, <p2, 2>, <p5, 1>
         */
        b2PL.add(p1, 1);
        b2PL.add(p2, 2);
        b2PL.add(p5, 1);
        Bundle b2 = Bundle.generator("b2", "", b2PL, DiscountType.PERCENT, 30, 1, "b2");

        /**
         * Add all Products to Bundle : b3.
         * <b3> : <p4, 1>, <p5, 1>
         */
        b3PL.add(p4, 1);
        b3PL.add(p5, 1);
        Bundle b3 = Bundle.generator("b3", "", b3PL, DiscountType.PERCENT, 25, 1, "b3");

        /**
         * Add all Products to Bundle : b4.
         * <b4> : <p2, 2>, <p3, 2>
         */
        b4PL.add(p2, 2);
        b4PL.add(p3, 2);
        Bundle b4 = Bundle.generator("b4", "", b4PL, DiscountType.PERCENT, 05, 3, "b4");

        /**
         * Add all Products to Bundle : b5.
         * <b5> : <p1, 1>, <p3, 2>
         */
        b5PL.add(p1, 1);
        b5PL.add(p3, 2);
        Bundle b5 = Bundle.generator("b5", "", b5PL, DiscountType.PERCENT, 10, 3, "b5");

        /**
         * Add all Products to Bundle : b6.
         * <b6> : <p4, 2>
         */
        b6PL.add(p4, 2);
        Bundle b6 = Bundle.generator("b6", "", b6PL, DiscountType.BOGO, 100, 2, "b6");

        // </editor-fold>
        //</editor-fold>
        GroupList<TicketElement> elements = Bundle.updateBundles(products);
        elements.sort(_sortBy);
        printElements(elements);
    }


    private static void testSort() {
        GroupList<TicketElement> elements = new GroupList(BYPRICE);
        Product_Test p1 = Product_Test.generate("p1", "", 3.75, "P1");
        Product_Test p2 = Product_Test.generate("p2", "", 4.00, "P2");
        Product_Test p3 = Product_Test.generate("p3", "", 3.50, "P3");
        Product_Test p4 = Product_Test.generate("p4", "", 3.25, "P4");
        Product_Test p5 = Product_Test.generate("p5", "", 3.00, "P5");
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


    private static void testTicket() {
        Ticket_Test ticket = new Ticket_Test();
        Product_Test p1 = Product_Test.generate("p1", "", 3.75, "P1");
        Product_Test p2 = Product_Test.generate("p2", "", 4.00, "P2");
        Product_Test p3 = Product_Test.generate("p3", "", 3.50, "P3");
        Product_Test p4 = Product_Test.generate("p4", "", 3.25, "P4");
        Product_Test p5 = Product_Test.generate("p5", "", 3.00, "P5");
        ticket.add(p1, 3);
        ticket.add(p2, 5);
        ticket.add(p3, 4);
        ticket.add(p4, 6);
        ticket.add(p5, 4);

        System.out.println(ticket.toString());

    }

}
