package product;

/*
* Last update: 5/1/2017
*
* @Description: Class ProductObject constructs the product object and includes
*             find methods that search by attributes.
*
* @Author: John Kaiserlik
*/

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
        protected HashMap map = new HashMap();
        protected String description = "";

    public ProductObject(){
        BoffoDbObject.create();
    }


    public ProductObject(String _tableName){
        ProductObject.tableName = _tableName;
    }


    public ProductObject(String _name, int _quant, double _price, int _UPC, String _sk, Rating _rat, String _uuid, String _tableName, String _description) {
       this.name = _name;
       this.quantity = _quant;
       this.price = _price;
       this.UPC = _UPC;
       this.SKU = _sk;
       this.rat = _rat;
       this.uuid = _uuid;
       ProductObject.tableName = _tableName;
       this.description = _description;
    }

    @Override
    public ProductObject clone(){
        return new ProductObject(this.name, this.quantity, this.price, this.UPC, this.SKU, this.rat, this.uuid, tableName, this.description);
    }


    public ProductObject generator(String _name, int _quant, double _price, int _UPC, String _sku, Rating _rat, String _uuid, String _tableName, String _description) {
        return new ProductObject(_name, _quant, _price, _UPC, _sku, _rat, _uuid, _tableName, _description);
    }


    @Override
    public String getDescription() {
        return this.description;
    }


    @Override
    public String getName() {
        return this.name;
    }


    @Override
    public double getPrice() {
        return this.price;
    }


    public ProductObject getProduct() {
        return new ProductObject(this.name, this.quantity, this.price, this.UPC, this.SKU, this.rat, this.uuid, tableName, this.description);
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
            this.map.put("uuid", this.uuid);
            this.map.put("description", description);

            return this.map;
        }
        return null;
    }


    public int getQuantity() {
        return this.quantity;
    }


    public Rating getRating() {
        return this.rat;
    }


    @Override
    public String getSku() {
        return this.SKU;
    }


    public int getUPC() {
        return this.UPC;
    }

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


    public void setDescription(String _description) {
        this.description = _description;
    }


    public void setName(String _name) {
        this.name = _name;
    }


    public void setPrice(double _price) {
        this.price = _price;
    }


    public void setQuantity(int _quant) {
        this.quantity = _quant;
    }


    public void setRating(Rating _rat) {
        this.rat = _rat;
    }


    public void setSKU(String _sku) {
        this.SKU = _sku;
    }


    public void setUPC(int _upc) {
        this.UPC = _upc;
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