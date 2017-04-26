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

<<<<<<< HEAD
public class Ticket extends Transaction {
    //Declare the variables.
    protected double total;
    protected int upc;
    protected String name, sku;
=======
public class Ticket extends BoffoDbObject implements BoffoListenerInterface {
>>>>>>> master

    private List<ProductObject> products;

    public Ticket() {
        //Initiate the variables.
        this.name = "";
        this.sku = "";
        this.total= 0.00;
        this.upc = 0;

    }

<<<<<<< HEAD
    public ProductObject addProductUPC(int _upc) {
        ProductObject product = ProductObject.loadByUpc(name);
=======
    public ProductObject addProductbyUPC(String UPC) {
        ProductObject product = (ProductObject) ProductObject.loadByUpc(UPC);
>>>>>>> master
        this.products.add(product);
        return product;
    }

<<<<<<< HEAD
    public ProductObject addProductName(String _name) {
        ProductObject product = ProductObject.loadByName(name);
        this.products.add(product);
        return product;
    }
    
    public ProductObject addProductSKU(String _sku){
        ProductObject product = ProductObject.loadBySKU(sku);
=======
    public ProductObject addProductbyName(String name) {
        ProductObject product = (ProductObject) ProductObject.loadByName(name);
>>>>>>> master
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
