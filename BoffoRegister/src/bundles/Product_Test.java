package bundles;

import java.util.ArrayList;
import java.util.List;

// TODO will be implemented by Product
public class Product_Test implements TicketElement {

    // TODO replace allProducts with getProducts()
    public static List<Product_Test> allProducts = new ArrayList();
    private final double price;
    private final String name, description;
    private final String sku;


    public static Product_Test generate(String _name, String _description, double _price, String _sku) {
        Product_Test p = new Product_Test(_name, _description, _price, _sku);
        add(p);
        return p.clone();
    }


    private Product_Test(String _name, String _description, double _price, String _sku) {
        this.name = _name;
        this.description = _description;
        this.price = _price;
        this.sku = _sku;
    }


    @Override
    public Product_Test clone() {
        return new Product_Test(this.name, this.description, this.price, this.sku);
    }


    @Override
    public String getDescription() {
        return this.description;
    }


    @Override
    public double getPrice() {
        return this.price;
    }


    @Override
    public String getName() {
        return this.name;
    }


    @Override
    public String getSku() {
        return this.sku;
    }


    @Override
    public String toString() {
        return "Product:" + "[" + this.name + this.description + "]";
    }


    // TODO replace following static methods with getProducts()
    // <editor-fold desc="Operations for the static List of Products named "allProducts." ">
    private static void add(Product_Test p) {
        if (!contains(p)) {
            allProducts.add(p);
        }
    }


    public static void remove(Product_Test p) {
        if (contains(p)) {
            allProducts.remove(indexOf(p));
        }
    }


    private static boolean contains(Product_Test p) {
        return indexOf(p) != -1;
    }


    private static int indexOf(Product_Test p) {
        int index = 0;
        for (Product_Test product : allProducts) {
            if (product.equals(p)) {
                return index;
            }
            index++;
        }
        return -1;
    }
    // </editor-fold>

}
