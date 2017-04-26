package inventory;

import authorization.Authorization;
import authorization.AuthorizationInterface;
import database.BoffoDbObject;    
import events.BoffoEvent;
import events.BoffoListenerInterface;
import events.BoffoMessenger;   
import java.util.ArrayList; 
import transaction.Ticket;
import user.User;
  
//hold a list of InventoryRecord object as ArrayList(dynamic list )
//does not currently have a method to update inventory from ticket object

public class Inventory extends BoffoDbObject implements AuthorizationInterface, BoffoListenerInterface{ //extends BoffoFireObject bc DBobject inherit from it
    ArrayList<InventoryRecord> list=null;
   public String table="inventory_tbl";
     
   
    public Inventory(){
        System.out.println("Inventory loaded"); 
        }   
    //update inventory fron ticket
    public void UpdateInvRecord(Ticket _ticket){
    //we need more from ticket; something to let us know how much to remove or add to inventory based on transaction made
//ticket holds a list of pruduct, so it make sense to use ticket object as paarameter
//this method will update inventory record based on Ticket object
    }
    //return quantity of specific record 
    public int getInvRecordCount(String _sku){
        int count =0;
        if(this.list.iterator().next().getSku()==_sku){
        count= this.list.iterator().next().getQuantity();
        }
       return count;
        }
    //delete record 
    public void deleteInvRecord(String _sku){
        if(this.list.iterator().next().getSku()==_sku){
        this.list.remove(this.list.iterator().next());
        }
      if(this.list.iterator().next().getSku()!=_sku){
        System.out.println("record not found");
        }
        }
    //increment quantity of specific record by specified amount
    //resulting quantity should alwasy be above 0 when adding new quantity
    public void incrementRecordQuantity(String _sku,int _quantity){
        if(this.list.iterator().next().getSku()==_sku){
        this.list.iterator().next().setQuantity(this.list.iterator().next().getQuantity()+_quantity);
        }
        if(this.list.iterator().next().getSku()!=_sku){
        System.out.println("record not found");
        }
    
    }
    //decrement quantity of specific record by specified amount
    //if resulting quantity is 0, record is deleted from list
    public void decrementRecordQuantity(String _sku,int _quantity){
        if(this.list.iterator().next().getSku()==_sku){
        this.list.iterator().next().setQuantity(this.list.iterator().next().getQuantity()-_quantity);
        }
        if((this.list.iterator().next().getQuantity()-_quantity)<0){
                this.list.remove(this.list.iterator().next());
         }
        if(this.list.iterator().next().getSku()!=_sku){
        System.out.println("record not found");
        }
    
    }
    public void addInventoryRecord(String _uuid,String _sku,String _upc,int _quantity, StateOfInvetory _state,String _location, String _productName,int _price,String _vender){
        //if already on list based on sku,update quantity of inventory 
         if(this.list.iterator().next().getSku()==_sku){
         this.list.iterator().next().setQuantity(this.list.iterator().next().getQuantity()+_quantity);
         System.out.println("inventory added");
         
         }
         //if NOT already on list based on sku,create new Inventory Record and add to list
         if(this.list.iterator().next().getSku()!=_sku){
            InventoryRecord r=new InventoryRecord(_uuid,_sku,_upc,_quantity,_state,_location,_productName,_price,_vender);
            this.list.add(r);
            System.out.println("inventory added");
         }
        }
     
     public void removeInventory(int _quantity,String _sku){ 
         //if already on list based on ProductSku, update its quantity
        if(this.list.iterator().next().getSku()==_sku){
            if(this.list.iterator().next().getQuantity()>0){
                this.list.iterator().next().setQuantity(this.list.iterator().next().getQuantity()-_quantity);
            }
            //if new quantity is negative, remove InventoryRecord from list
            if((this.list.iterator().next().getQuantity()-_quantity)<0){
                this.list.remove(this.list.iterator().next());
            }
        }
        //if not on list, message
         if(this.list.iterator().next().getSku()!=_sku){
             System.out.println("no inventory with sku: "+_sku+" is found.");
         }
         } 
     //return ArrayList of InventoryRecord(search by productName)
     public ArrayList searchInventoryByName(String _productName){
         ArrayList<InventoryRecord> l=null;//create empty list to hold invenotry record with specified productName
         //add inventory found  in this.list(ArrayList) to l(ArrayList) 
         if(this.list.iterator().next().getProductName()==_productName){
             l.add(this.list.iterator().next());
        }
         return l;
        } 
     //return ArrayList of InventoryRecord(search by sku)
     public ArrayList searchInventoryBySku(String _sku){
         ArrayList<InventoryRecord> l=null;//create empty list to hold inventory record with specified sku
         //add product found  in this.list(ArrayList) to l(ArrayList) 
         if(this.list.iterator().next().getSku()==_sku){
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
         if(this.list.iterator().next().getPrice()==_price){
             l.add(this.list.iterator().next());
        }
         return l;
        } 
      
     
    public void messageReceived(BoffoEvent event){
        
        switch(event.getMessage().getCode()){ 
            
//            case BoffoMessenger.ADD_INVENTORY:
//                this.addInventory();
//                break;
//            case BoffoMessenger.REMOVE_INVENTORY:
//                this.removeInventory();
//                break;
//                case BoffoMessenger.SEARCH_INVENTORY:
//                this.searchInventory();
//                break;
//            default:
//                //do nothing 
//                break;
           
        } 
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

