package transaction;

/*
 * Last updated: 4-25-17
 *
 * This class create a product list and allow to add or remove item 
 * from the list
 *
 * @author Fan Yang
 */
import bundles.Bundle;
import bundles.Product_Test;
import bundles.TicketElement;
import events.BoffoEvent;
import events.BoffoListenerInterface;
import java.util.List;
import product.ProductObject;

public class Ticket extends Transaction {

    //Declare the variables.
    protected double total;
    protected int upc;
    protected String name, sku;

//public class Ticket extends BoffoDbObject implements BoffoListenerInterface
    private List<ProductObject> products;
    private List<TicketElement> productbundles;

    public Ticket() {
        //Initiate the variables.
        this.name = " ";
        this.sku = " ";
        this.total = 0.00;
        this.upc = 0;
    }

    public ProductObject addProductbyUPC(String UPC, List<ProductObject> _products) {
        ProductObject product = (ProductObject) ProductObject.loadByUpc(UPC);
        this.products.add(product);
        this.productbundles = Bundle.updateBundles(_products);
        return product;
    }

    public ProductObject addProductbyName(String name, List<ProductObject> _products) {
        ProductObject product = (ProductObject) ProductObject.loadByName(name);
        this.products.add(product);
        this.productbundles = Bundle.updateBundles(_products);
        return product;
    }

    public void removeProductbyUPC(String UPC, List<ProductObject> _products) {
        ProductObject product = (ProductObject) ProductObject.loadByUpc(UPC);
        this.products.remove(product);
        this.productbundles = Bundle.updateBundles(_products);
    }

    public void removeProductbyName(String name, List<ProductObject> _products) {
        ProductObject product = (ProductObject) ProductObject.loadByName(name);
        this.products.remove(product);
        this.productbundles = Bundle.updateBundles(_products);
    }

    public double getTotalPrice(String String_price) {
        double totalPrice = 0;
        for (int i = 0; i < products.size(); i++) {
            Object obj = ProductObject.loadByPrice(String_price);
            ProductObject p = (ProductObject) obj;
            totalPrice = totalPrice + p.getPrice();
        }
        return totalPrice;
    }

    public void fireEvent(BoffoEvent _event) {
    }

    public void addListener(BoffoListenerInterface _event) {
    }

    public void removeListener(BoffoListenerInterface _event) {
    }

    public void messageReceived(BoffoEvent event) {

    }
}
