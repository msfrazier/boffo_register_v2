package product;

import utility.Utility;
import bundles.*;
import java.util.HashMap;
import database.BoffoDbObject;

public class ProductObject extends BoffoDbObject implements TicketElement{
        protected String name = "";
        protected int quantity = 0;
        protected double price = 0.00;
        protected int UPC = 0;
        protected String SKU = "";
        protected Rating rat = null;
        protected static String tableName = "product";
        protected String uuid = "";
        protected HashMap map = null;
        protected String description = "";

    public ProductObject(){
        BoffoDbObject.create();
    }


    public ProductObject(String _tableName){
        ProductObject.tableName = _tableName;
    }


    public ProductObject(String _name, int _quant, double _price, int _UPC, String _sk, Rating _rat, String _upc, String _tableName, String _description) {
       this.name = _name;
       this.quantity = _quant;
       this.price = _price;
       this.UPC = _UPC;
       this.SKU = _sk;
       this.rat = _rat;
       this.uuid = _upc;
       ProductObject.tableName = _tableName;
       this.description = _description;
    }

    @Override
    public ProductObject clone(){
        return new ProductObject();
    }


    public void setDescription(String _description) {
        this.description = _description;
    }


    @Override
    public String getDescription() {
        return this.description;
    }


    public void setName(String _name) {
        this.name = _name;
    }


    @Override
    public String getName() {
        return this.name;
    }


    public void setPrice(double _price) {
        this.price = _price;
    }

    @Override
    public double getPrice() {
        return this.price;
    }


    public ProductObject getProduct() {
        return new ProductObject(this.name, this.quantity, this.price, this.UPC, this.SKU, this.rat, this.uuid, tableName, this.description);
    }


    public void setQuantity(int _quant) {
        this.quantity = _quant;
    }


    public int getQuantity() {
        return this.quantity;
    }


    public void setRating(Rating _rat) {
        this.rat = _rat;
    }


    public Rating getRating() {
        return this.rat;
    }


    public void setUPC(int _upc) {
        this.UPC = _upc;
    }


    public int getUPC() {
        return this.UPC;
    }


    public void setSKU(String _sku) {
        this.SKU = _sku;
    }

    @Override
    public String getSku() {
        return this.SKU;
    }


    //ProductObject.load() is a BoffoDbObject so still cast as ProductObject
    public static ProductObject loadBySKU(String _sku) {
        return (ProductObject)ProductObject.load("sku", _sku, new ProductObject(tableName));
    }


    public static ProductObject loadByUpc(String _upc) {
        return (ProductObject)ProductObject.load("upc", _upc, new ProductObject(tableName));
    }


    public static ProductObject loadByName(String _name) {
       return (ProductObject)ProductObject.load("name", _name, new ProductObject(tableName));
    }


    public static ProductObject loadByQuantity(String _quant) {
       return (ProductObject)ProductObject.load("quantity", _quant, new ProductObject(tableName));
    }


    public static ProductObject loadByRating(String _rat) {
        return (ProductObject)ProductObject.load("rating", _rat, new ProductObject(tableName));
    }


    public static ProductObject loadByPrice(String _price) {
        return (ProductObject)ProductObject.load("price", _price, new ProductObject(tableName));
    }


    public HashMap getProductMap(){
        this.map.clear();

        if (this.map.isEmpty()){
            this.map.put("name", this.name);
            this.map.put("quantity", this.quantity);
            this.map.put("price", Utility.formatPrice(this.getPrice()));
            this.map.put("upc", this.UPC);
            this.map.put("sku", this.SKU);
            this.map.put("rating", this.rat);

            return this.map;
        }
        return null;
    }


    @Override
    public String toString() {
        String str = "Name: " + this.getName() + "\n" +
                     "Quantity: " + this.getQuantity() + "\n" +
                     "Price: " + Utility.formatPrice(this.getPrice()) + "\n" +
                     "UPC: " + this.getUPC() + "\n" +
                     "SKU: " + this.getSku() + "\n" +
                     "Rating: " + this.getRating() + "\n" +
                     "UUID: " + this.uuid + "\n" +
                     "Description: " + this.description + "\n";
        return str;
    }
}