package inventory;

import database.BoffoDbObject;
import events.BoffoEvent;
import events.BoffoMessenger;   
import Product.Product;
//Inventory has to be able to minipulate the data base

// Note from Instructor: Inventory should extend the BoffoDBObject.
public class Inventory{
    BoffoDbObject DB;
    Product pr;

    
    public Inventory(){
    System.out.println("Inventory loaded"); 
    }
     public void addInventory(){
         this.DB.add();
     System.out.println("Inventory added");
     }
     public void removeInventory(){ 
         this.DB.delete();
     System.out.println("Inventory removed");
     } 
     public void searchInventory(){
         boolean found=false;
     System.out.println("searching Inventory");
     if(found==true){
     System.out.println("Inventory found");
     }else if(found==false)
     {
     System.out.println("Inventory cannot found");
     }
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
}

