package inventory;

import events.BoffoEvent;
import events.BoffoListenerInterface;
import java.util.ArrayList;
import inventory.InventoryRecord;
import product.ProductObject;
import user.User;

//hold a list of InventoryRecord object as ArrayList(dynamic list )
//does not currently have a method to update inventory from ticket object

public class Inventory { //extends BoffoFireObject bc DBobject inherit from it
     ArrayList<InventoryRecord> list=null;
   public String table="inventory_tbl";
   public Inventory(){
//   protected HashMap<String, Integer> inventory_hash = new HashMap<>();
//
//   //1=user
//   //2=manager
//   //3-administration
//   public Inventory(){
//System.out.println("Inventory loaded");
//inventory_hash.put("addInvnetory", 2);

                }
   //increase quantity by one
    public void incrementInventory(String _sku){
        if(this.list.iterator().next().product.getSku()==_sku){
        this.list.iterator().next().setQuantity(this.list.iterator().next().getQuantity()+1);
    }}
    //decrease quantity by one
    public void decrementInventory(String _sku){
        if(this.list.iterator().next().product.getSku()==_sku){
        this.list.iterator().next().setQuantity(this.list.iterator().next().getQuantity()-1);
    }}
    //return count of specific product with sku value
    public int getInvRecordCount(String _sku){
        int count =0;
        if(this.list.iterator().next().product.getSku()==_sku){
        count= this.list.iterator().next().getQuantity();
        }
       return count;
        }
    //delete record
    public void deleteInvRecord(String _sku){
        if(this.list.iterator().next().product.getSku()==_sku){
        this.list.remove(this.list.iterator().next());
        }
      if(this.list.iterator().next().product.getSku()!=_sku){
        System.out.println("record not found");
        }
        }
    //increase quantity of specific record by specified amount
    //resulting quantity should always be above 0 when adding new quantity
    public void increaseRecordQuantity(String _sku,int _quantity){
        if(this.list.iterator().next().product.getSku()==_sku){
        this.list.iterator().next().setQuantity(this.list.iterator().next().getQuantity()+_quantity);
        }
        if(this.list.iterator().next().product.getSku()!=_sku){
        System.out.println("record not found");
        }

    }
    //decrease quantity of specific record by specified amount
    //if resulting quantity is 0, record is deleted from list
    public void decreaseRecordQuantity(String _sku,int _quantity){
        if(this.list.iterator().next().product.getSku()==_sku){
        this.list.iterator().next().setQuantity(this.list.iterator().next().getQuantity()-_quantity);
        }
        if((this.list.iterator().next().getQuantity()-_quantity)<0){
                this.list.remove(this.list.iterator().next());
         }
        if(this.list.iterator().next().product.getSku()!=_sku){
        System.out.println("record not found");
        }

    }
    public void addInventoryRecord(ProductObject _product,String _uuid,int _quantity, StateOfInvetory _status,String _location,String _vender){
        //if already on list based on sku,update quantity of inventory
         if(this.list.iterator().next().product.getSku()==_product.getSku()){
         this.list.iterator().next().setQuantity(this.list.iterator().next().getQuantity()+_quantity);
         System.out.println("inventory added");

         }
         //if NOT already on list based on sku,create new Inventory Record and add to list
         if(this.list.iterator().next().product.getSku()!=_product.getSku()){
            InventoryRecord r=new InventoryRecord(_product,_uuid,_quantity,_status,_location,_vender);
            this.list.add(r);
            System.out.println("inventory added");
         }
        }
     //return ArrayList of InventoryRecord(search by productName)
     public ArrayList searchInventoryByName(String _productName){
         ArrayList<InventoryRecord> l=null;//create empty list to hold invenotry record with specified productName
         //add inventory found  in this.list(ArrayList) to l(ArrayList)
         if(this.list.iterator().next().product.getName()==_productName){
             l.add(this.list.iterator().next());
        }
         return l;
        }
     //return ArrayList of InventoryRecord(search by sku)
     public ArrayList searchInventoryBySku(String _sku){
         ArrayList<InventoryRecord> l=null;//create empty list to hold inventory record with specified sku
         //add product found  in this.list(ArrayList) to l(ArrayList)
         if(this.list.iterator().next().product.getSku()==_sku){
             l.add(this.list.iterator().next());
        }
         return l;
        }
     //return ArrayList of InventoryRecord(search by uuid)
     public ArrayList searchInventoryByUuid(String _uuid){
         ArrayList<InventoryRecord> l=null;//create empty list to hold inventory record with specified uuid
         //add product found  in this.list(ArrayList) to l(ArrayList)
         if(this.list.iterator().next().getUuid()==_uuid){
             l.add(this.list.iterator().next());
        }
         return l;
        }
     //return ArrayList of InventoryRecord(search by price)
     public ArrayList searchInventoryByPrice(double _price){
         ArrayList<InventoryRecord> l=null;//create empty list to hold invenotry record with specified price
         //add product found  in this.list(ArrayList) to l(ArrayList)
         if(this.list.iterator().next().product.getPrice()==_price){
             l.add(this.list.iterator().next());
        }
         return l;
        }
     //return size of inventory list
      public int GetListSize(){
     return this.list.size();
      }
    public boolean isAuthorized(User user, String action) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addBRegisterListener(BoffoListenerInterface _event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void removeBRegisterListener(BoffoListenerInterface _event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void fireEvent(BoffoEvent _event) {
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

