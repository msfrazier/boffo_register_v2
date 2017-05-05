package transaction;

/*
 * Last updated: 5-5-17
 *
 * This class create a product list and allow to add or remove item 
 * from the list
 *
 * @author Fan Yang
 * @author Mabelyn Espinoza
 */
import bundles.Bundle;
import bundles.TicketElement;
import events.BoffoEvent;
import events.BoffoEventData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import product.ProductObject;

public class Ticket extends Transaction {

    //Declare the variables.
    protected double total;
    protected int upc;
    protected String name, sku;
    protected static HashMap<String, ArrayList<Integer>> ticket_hash
            = new HashMap<>();

    private List<ProductObject> products;
    private List<TicketElement> productbundles;
     BoffoEventData data = new BoffoEventData();
     BoffoEvent update = new BoffoEvent(this,data);

    //Initiate the variables.
    public Ticket() {
        this.name = " ";
        this.sku = " ";
        this.total = 0.00;
        this.upc = 0;
    }

    public ProductObject addProductbyUPC(String _UPC, List<ProductObject> _products) {
        ProductObject product = (ProductObject) ProductObject.loadByUpc(_UPC);

        this.products.add(product);
        this.productbundles = Bundle.updateBundles(_products);
        this.fireEvent(update);
        return product;
    }

    public ProductObject addProductbyName(String _name, List<ProductObject> _products) {
        ProductObject product = (ProductObject) ProductObject.loadByName(_name);
        this.products.add(product);
        this.productbundles = Bundle.updateBundles(_products);
        this.fireEvent(update);
        return product;
    }

    public void removeProductbyUPC(String _UPC, List<ProductObject> _products) {
        ProductObject product = (ProductObject) ProductObject.loadByUpc(_UPC);

        this.products.remove(product);
        this.productbundles = Bundle.updateBundles(_products);
        this.fireEvent(update);
    }

    public void removeProductbyName(String _name, List<ProductObject> _products) {
        ProductObject product = (ProductObject) ProductObject.loadByName(_name);

        this.products.remove(product);
        this.productbundles = Bundle.updateBundles(_products);
        this.fireEvent(update);
    }


    //Return total price.
    public double getTotalPrice(String String_price) {
        for (int i = 0; i < productbundles.size(); i++) {
            Object object = ProductObject.loadByPrice(String_price);
            ProductObject price = (ProductObject) object;
            this.total = this.total + price.getPrice();

        }
        return this.total;
    }


    public static void buildMap() {
        ArrayList<Integer> addProductbyUPC = new ArrayList<>();
        addProductbyUPC.addAll(Arrays.asList(2));

        ArrayList<Integer> removeProductbyUPC = new ArrayList<>();
        removeProductbyUPC.addAll(Arrays.asList(2));



    public static void buildMap() {

        ArrayList<Integer> addProductbyUPC = new ArrayList<>();
        addProductbyUPC.addAll(Arrays.asList(2));

        ArrayList<Integer> removeProductbyUPC = new ArrayList<>();
        removeProductbyUPC.addAll(Arrays.asList(2));


        ArrayList<Integer> addProductbyName = new ArrayList<>();
        addProductbyName.addAll(Arrays.asList(2));

        ArrayList<Integer> removeProductbyName = new ArrayList<>();
        removeProductbyName.addAll(Arrays.asList(2));

        ArrayList<Integer> getTotalPrice = new ArrayList<>();
        getTotalPrice.addAll(Arrays.asList(2));

        ticket_hash.put("addProductbyUPC", addProductbyUPC);
        ticket_hash.put("removeProductbyUPC", removeProductbyUPC);
        ticket_hash.put("addProductbyName", addProductbyName);
        ticket_hash.put("removeProductbyName", removeProductbyName);
        ticket_hash.put("getTotalPrice", getTotalPrice);
    }

    @Override
    public void messageReceived(BoffoEvent _event) {
        if (_event.getMessage().getCode() instanceof BoffoTicketEventData) {

        ArrayList<Integer> getTotalPrice = new ArrayList<>();
        getTotalPrice.addAll(Arrays.asList(2));

        ticket_hash.put("addProductbyUPC", addProductbyUPC);
        ticket_hash.put("removeProductbyUPC", removeProductbyUPC);
        ticket_hash.put("addProductbyName", addProductbyName);
        ticket_hash.put("removeProductbyName", removeProductbyName);
        ticket_hash.put("getTotalPrice", getTotalPrice);
    }


    public void messageReceived(BoffoEvent _event) {
        if (_event.getMessage().getCode() instanceof BoffoTicketEventData) {
            return;

        }
    }
}