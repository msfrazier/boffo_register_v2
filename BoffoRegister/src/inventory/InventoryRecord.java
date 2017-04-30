package inventory;

import authorization.AuthorizationInterface;
import events.BoffoEvent;
import events.BoffoFireObject;
import events.BoffoListenerInterface;
import static inventory.StateOfInvetory.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import product.ProductObject;

/*
This class is to be manage by Inventory class as list of Object
used to update table.
authurs: Chad Schmidt, Tey Tang
last updated 4/30/17
*/

public class InventoryRecord extends BoffoFireObject{
    protected StateOfInvetory status;
    protected String location;
    protected String vender;
    protected ProductObject product;
    protected int quantity;
    protected HashMap<String, Integer> inventory_hash = new HashMap<>();
    protected String tableName="inventory_tbl";

public InventoryRecord(){
    this.status = null;
    this.location = null;
    this.vender = null;
    this.product = null;
    this.quantity = 0;
}


public InventoryRecord(ProductObject _product,int _quantity, StateOfInvetory _status,String _location,String _vender){
    this.status = _status;
    this.location = _location;
    this.vender = _vender;
    this.product = _product;
    this.quantity = _quantity;
    }


  public String getLocation() {
        return location;
    }


   public ProductObject getProduct() {
        return product;
    }


    public StateOfInvetory getStatus() {
        return status;
    }


     public int getQuantity() {
        return quantity;
    }


    public String getVender() {
        return vender;
    }


    public void setLocation(String _location) {
        this.location = _location;
    }


    public void setProduct(ProductObject _product) {
        this.product = _product;
    }


    public void setStatus(StateOfInvetory _status) {
        this.status = _status;
    }


    public void setQuantity(int _quantity) {
        this.quantity = _quantity;
    }


    public void setVender(String _vender) {
        this.vender = _vender;
    }
}