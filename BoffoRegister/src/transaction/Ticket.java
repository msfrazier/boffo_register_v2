package transaction;

import events.BoffoEvent;
import events.BoffoListenerInterface;
import java.util.ArrayList;
import java.util.List;
import product.ProductObject;

public class Ticket extends Transaction {
    //Declare the variables.
    protected double total;
    protected int upc;
    protected String name, sku;

    private List<ProductObject> products;

    Ticket(List<ProductObject> products) {

    }

    public Ticket() {
        //Initiate the variables.
        this.name = "";
        this.sku = "";
        this.total= 0.00;
        this.upc = 0;

    }

    public ProductObject addProductUPC(int UPC) {
        // ProductObject product = (ProductObject) product.add(null, null);
        // this.products.add(product);
        // return product;
        return null;
    }

    public ProductObject addProductName(String name) {
        return null;
    }

    public ProductObject removeProductUPC(int UPC) {

        return null;
    }

    public ProductObject removeProductName(String name) {

        return null;
    }

    public double getTotalPrice() {
        double totalPrice = 0;
//	for (int i=0; i< ProductObject.size(); i++) {
//	    Object obj = ProductObject.get(i);
//	    Product p = (ProductObject)obj;
//	    totalPrice = totalPrice + p.getPrice();
//	}
        return totalPrice;
    }

    public void fireEvent(BoffoEvent _event) {
    }

    public void addListener(BoffoListenerInterface _event) {
    }

    public void removeListener(BoffoListenerInterface _event) {
    }
}
