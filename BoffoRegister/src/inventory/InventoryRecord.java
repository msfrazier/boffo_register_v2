
package inventory;


import authorization.AuthorizationInterface; 
import events.BoffoEvent;
import events.BoffoFireObject;
import events.BoffoListenerInterface;
import static inventory.StateOfInvetory.*;
import product.ProductObject;
 //To be use in Inventory class as list of Object
//Product Object can be use as attribute here but not necessary
public class InventoryRecord extends BoffoFireObject {
    protected StateOfInvetory status;
    protected String location;
    protected String vender;
    protected ProductObject product;
    protected String uuid;
    protected int quantity;
public InventoryRecord(){
    this.status=null;
    this.location=null;
    this.vender=null;
    this.product=null;
    this.uuid=null;
    this.quantity=0;

}
public InventoryRecord(ProductObject _product,String _uuid,int _quantity, StateOfInvetory _status,String _location,String _vender){
    this.status=_status;
    this.location=_location;
    this.vender=_vender;
    this.product=_product;
    this.uuid=_uuid;
    this.quantity=_quantity;

} 
    @Override
    protected synchronized void fireEvent(BoffoEvent event) {
        super.fireEvent(event); //To change body of generated methods, choose Tools | Templates.
    }

    public StateOfInvetory getStatus() {
        return status;
    }

    public void setStatus(StateOfInvetory status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getVender() {
        return vender;
    }

    public void setVender(String vender) {
        this.vender = vender;
    }

    public ProductObject getProduct() {
        return product;
    }

    public void setProduct(ProductObject product) {
        this.product = product;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}

