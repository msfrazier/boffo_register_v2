
package inventory;


import static inventory.StateOfInvetory.*;
 //To be use in Inventory class as list of Object
//Product Object can be use as attribute here but not necessary 
public class InventoryRecord {
    int quantity;
    StateOfInvetory state;
    String location;
    String uuid;
    String sku;
    String upc;
    int price;
    String vender;
    String productName;
    
public InventoryRecord(){
this.quantity=0;
this.state=NEW;  
this.location=null;
this.uuid=null;
this.sku=null;
this.upc=null;
this.price=0; 
this.vender=null;
this.productName=null;
}
public InventoryRecord(String _uuid,String _sku,String _upc,int _quantity, StateOfInvetory _state,String _location, String _productName,int _price,String _vender){
this.quantity=_quantity;
this.state=_state;
this.location=_location;
this.productName=_productName;
this.price=_price; 
this.vender=_vender;
this.uuid=_uuid;
this.sku=_sku;
this.upc=_upc;
}

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getVender() {
        return vender;
    }

    public void setVender(String vender) {
        this.vender = vender;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int qantity) {
        this.quantity = qantity;
    }

    public StateOfInvetory getState() {
        return state;
    }

    public void setState(StateOfInvetory state) {
        this.state = state;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
   
}

