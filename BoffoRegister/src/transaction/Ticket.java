package transaction;

/*
 * Last updated: 4-25-17
 *
 * This class create a product list and allow to add or remove item 
 * from the list
 *
 * @author Fan Yang
 */
import database.BoffoDbObject;
import events.BoffoEvent;
import events.BoffoListenerInterface;
import java.util.List;
import product.ProductObject;

public class Ticket extends BoffoDbObject implements BoffoListenerInterface {

    private List<ProductObject> products;

    public Ticket() {

    }

    public ProductObject addProductbyUPC(String UPC) {
        ProductObject product = (ProductObject) ProductObject.loadByUpc(UPC);
        this.products.add(product);
        return product;
    }

    public ProductObject addProductbyName(String name) {
        ProductObject product = (ProductObject) ProductObject.loadByName(name);
        this.products.add(product);
        return product;
    }

    public void removeProductbyUPC(String UPC) {
        ProductObject product = (ProductObject) ProductObject.loadByUpc(UPC);
        this.products.remove(product);
    }

    public void removeProductbyName(String name) {
        ProductObject product = (ProductObject) ProductObject.loadByName(name);
        this.products.remove(product);
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
