package bundles;

import static bundles.TicketElement.*;
import java.util.List;

// TODO will be implemented by Transaction
public class Ticket_Test {

    private GroupList<Product_Test> allProducts;
    private GroupList<TicketElement> allElements;


    public Ticket_Test() {
        allProducts = new GroupList(BYSKU);
        allElements = new GroupList(BYSKU);
    }


    // <editor-fold desc="Add methods.">
    public void add(GroupList<Product_Test> _pairList) {
        add(_pairList.toList());
        this.updateElements();
    }


    public void add(List<Group<Product_Test>> _listPairs) {
        for (Group<Product_Test> pair : _listPairs) {
            add(pair);
        }
        this.updateElements();
    }


    public int add(Group<Product_Test> _pair) {
        int retValue = allProducts.add(_pair);
        for (Product_Test product : _pair.getContents()) {
            add(product, 1);
        }
        this.updateElements();
        return retValue;
    }


    public int add(Product_Test _p, int _number) {
        int added = _number + allProducts.numberOf(_p);
        for (int i = 0; i < _number; i++) {
            allProducts.add(_p);
        }
        this.updateElements();
        return added;
    }


    public int add(Product_Test _p) {
        this.updateElements();
        return add(_p, 1);
    }

    // </editor-fold>

    public double getAmountSaved() {
        return allProducts.getTotal() - allElements.getTotal();
    }


    public GroupList<TicketElement> getElements() {
        updateElements();
        return allElements.clone();
    }


    public double getTotal() {
        return allElements.getTotal();
    }


    public int remove(Product_Test _p, int _number) {
        for (int i = 0; i < _number; i++) {
            allProducts.remove(_p);
        }
        int remaining = allProducts.numberOf(_p);
        this.updateElements();
        return remaining;
    }


    public int remove(Product_Test _p) {
        this.updateElements();
        return remove(_p, 1);
    }


    private void updateElements() {
        this.allElements = Bundle.updateBundles(allProducts);
    }


    @Override // TODO allProducts and allBundles replaced with getter methods.
    public String toString() {
        String string_products = "Products:" + Product_Test.allProducts.toString();
        String string_bundles = "Bundles:" + Bundle.allBundles.toString();
        String string_elements = "Elements:" + getElements();
        String string_line = "\n ---- \n";
        return string_products + string_line + string_bundles + string_line + string_elements + string_line;
    }

}
