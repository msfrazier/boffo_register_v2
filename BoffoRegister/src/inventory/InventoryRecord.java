
package inventory;

import authorization.AuthorizationInterface;
import events.BoffoEvent;
import events.BoffoFireObject;
import events.BoffoListenerInterface;
import static inventory.StateOfInvetory.*;
<<<<<<< HEAD
 //To be use in Inventory class as list of Object
//Product Object can be use as attribute here but not necessary 
public class InventoryRecord {
    int quantity;
    StateOfInvetory state;
    String location;
    String uuid;
    String sku;
    String upc;
    double price;
    String vender;
    String productName;
=======
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;
import java.util.HashMap;
import product.ProductObject;

/*
This class is to be manage by Inventory class as list of Object
used to update table.
authurs: Chad Schmidt, Tey Tang
last updated 4/29/17
*/

public class InventoryRecord extends BoffoFireObject{
    protected StateOfInvetory status;
    protected String location;
    protected String vender;
    protected ProductObject product;
    protected int quantity;
    protected HashMap<String, Integer> inventory_hash = new HashMap<>();
    protected String tableName="inventory_tbl";
>>>>>>> master

public InventoryRecord(){
    this.status = null;
    this.location = null;
    this.vender = null;
    this.product = null;
    this.quantity = 0;
       //
//
//   //1=user
//   //2=manager
//   //3-administration
//   public Inventory(){
//System.out.println("Inventory loaded");
//inventory_hash.put("addInvnetory", 2);
}
<<<<<<< HEAD
public InventoryRecord(String _uuid,String _sku,String _upc,int _quantity, StateOfInvetory _state,String _location, String _productName,double _price,String _vender){
this.quantity=_quantity;
this.state=_state;
this.location=_location;
this.productName=_productName;
this.price=_price; 
this.vender=_vender;
this.uuid=_uuid;
this.sku=_sku;
this.upc=_upc;
=======


public InventoryRecord(ProductObject _product,int _quantity, StateOfInvetory _status,String _location,String _vender){
    this.status = _status;
    this.location = _location;
    this.vender = _vender;
    this.product = _product;
    this.quantity = _quantity;
>>>>>>> master
}


    public StateOfInvetory getStatus() {
        return status;
    }


    public void setStatus(StateOfInvetory _status) {
        this.status = _status;
    }


    public String getLocation() {
        return location;
    }


    public void setLocation(String _location) {
        this.location = _location;
    }


    public String getVender() {
        return vender;
    }


    public void setVender(String _vender) {
        this.vender = _vender;
    }


    public ProductObject getProduct() {
        return product;
    }


    public void setProduct(ProductObject _product) {
        this.product = _product;
    }


<<<<<<< HEAD
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
=======
    public int getQuantity() {
        return quantity;
    }


    public void setQuantity(int _quantity) {
        this.quantity = _quantity;
>>>>>>> master
    }
}

