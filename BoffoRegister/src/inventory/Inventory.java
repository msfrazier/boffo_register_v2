package inventory;

import authorization.Authorization;
import authorization.AuthorizationInterface;
import events.BoffoEvent;
import events.BoffoEventData;
import events.BoffoFireObject;
import events.BoffoListenerInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import product.ProductObject;

/*
* This class holds a list of InventoryRecord object as ArrayList(dynamic list ).
* It uses the list to manage seaching and updating table in data base.
* authors: Chad Schmidt, Tey Tang
* last updated: 5/5/2017
*/

public class Inventory extends BoffoFireObject implements AuthorizationInterface, BoffoListenerInterface{

     private ArrayList<InventoryRecord> list = null;
     BoffoEventData data = new BoffoEventData("updated");
     BoffoEvent update = new BoffoEvent(this,data);
     protected static HashMap<String, ArrayList<Integer>> inventory_hash = new HashMap<>();
     protected String tableName = "inventory_tbl";

    public Inventory(){
       System.out.println("Inventory loaded");
         this.buildMap();
                }


    /*
    *  Checks to see if inventory record is already in the list/table.
    *  If it is NOT already in the list/table, then it gets added.
    */
    public void addInventoryRecord(ProductObject _product,int _quantity, StateOfInvetory _status,String _location,String _vender){
         if(this.list.iterator().next().product.getSku() == _product.getSku()){
         this.list.iterator().next().product.setQuantity(this.list.iterator().next().getQuantity()+_quantity);
         this.save(this.list.iterator().next().product);//update in table
         this.fireEvent(update);
         System.out.println("inventory added");
         }else
          if(this.list.iterator().next().product.getSku() != _product.getSku()){
            InventoryRecord r = new InventoryRecord(_product,_quantity,_status,_location,_vender);
            this.list.add(r);
           this.save(r.product);
           this.fireEvent(update);
         }
        }


    public static void buildMap(){
         ArrayList<Integer> addInventoryRecord = new ArrayList<>();
        addInventoryRecord.addAll(Arrays.asList(2));
        ArrayList<Integer> deleteInventoryRecord = new ArrayList<>();
        deleteInventoryRecord.addAll(Arrays.asList(2));
        ArrayList<Integer> increaseRecordQuantity = new ArrayList<>();
        increaseRecordQuantity.addAll(Arrays.asList(1, 2));
        ArrayList<Integer> decreaseRecordQuantity = new ArrayList<>();
        decreaseRecordQuantity.addAll(Arrays.asList(1, 2));
        ArrayList<Integer> incrementInventory = new ArrayList<>();
        incrementInventory.addAll(Arrays.asList(1, 2));
        ArrayList<Integer> decrementInventory = new ArrayList<>();
        decrementInventory.addAll(Arrays.asList(1, 2));
        ArrayList<Integer> searchInventoryByName = new ArrayList<>();
        searchInventoryByName.addAll(Arrays.asList(1, 2));
        ArrayList<Integer> searchInventoryByPrice = new ArrayList<>();
        searchInventoryByPrice.addAll(Arrays.asList(1, 2));
        ArrayList<Integer> searchInventoryBySku = new ArrayList<>();
        searchInventoryBySku.addAll(Arrays.asList(1, 2));
        ArrayList<Integer> searchInventoryByUuid = new ArrayList<>();
        searchInventoryByUuid.addAll(Arrays.asList(1, 2));

        inventory_hash.put("addInventoryRecord", addInventoryRecord);
        inventory_hash.put("deleteInventoryRecord", deleteInventoryRecord);
        inventory_hash.put("increaseRecordQuantity", increaseRecordQuantity);
        inventory_hash.put("decreaseRecordQuantity", decreaseRecordQuantity);
        inventory_hash.put("incrementInventory", incrementInventory);
        inventory_hash.put("decrementInventory", decrementInventory);
        inventory_hash.put("searchInventoryByName", searchInventoryByName);
        inventory_hash.put("searchInventoryByPrice", searchInventoryByPrice);
        inventory_hash.put("searchInventoryBySku", searchInventoryBySku);
        inventory_hash.put("searchInventoryByUuid", searchInventoryByUuid);
    }


    /*
    *  Decreases quantity of specific record by a specified amount, and
    *  if resulting quantity is 0, record is deleted from the list/table.
    */
    public void decreaseRecordQuantity(String _sku,int _quantity){
        if(this.list.iterator().next().product.getSku() == _sku){
        this.list.iterator().next().product.setQuantity(this.list.iterator().next().getQuantity()-_quantity);
         this.save(this.list.iterator().next().product);
         this.fireEvent(update);
        }else
        if((this.list.iterator().next().getQuantity()-_quantity)<0){
                this.list.remove(this.list.iterator().next());
                this.delete(this.list.iterator().next().product);
                fireEvent(update);
         }else
        if(this.list.iterator().next().product.getSku() != _sku){
            System.out.println("record not found");
        }
    }


    /*
    *  Decreases the quantity by one.
    */
    public void decrementInventory(String _sku){
        if(this.list.iterator().next().product.getSku() == _sku){
        this.list.iterator().next().product.setQuantity(this.list.iterator().next().getQuantity()-1);
        this.save(this.list.iterator().next().product);
        this.fireEvent(update);
        }
    }


    /*
    *  Deletes the record of specified product by sku.
    */
    public void deleteInventoryRecord(String _sku){
        if(this.list.iterator().next().product.getSku() == _sku){
        this.list.remove(this.list.iterator().next());
        delete(this.list.iterator().next().product);
        this.fireEvent(update);
        }else
      if(this.list.iterator().next().product.getSku() != _sku){
        System.out.println("record not found");
            }
        }


    /*
    *  Returns the count of a specific product with sku value.
    */
    public int getInvRecordCount(String _sku){
        int count = 0;
        if(this.list.iterator().next().product.getSku() == _sku){
        count= this.list.iterator().next().getQuantity();
            }
       return count;
        }


    public int GetListSize(){
        return this.list.size();
      }


    /*
    *  Increases the quantity of a specific record by specified amount.
    */
    public void increaseRecordQuantity(String _sku,int _quantity){
        if(this.list.iterator().next().product.getSku() == _sku){
        this.list.iterator().next().product.setQuantity(this.list.iterator().next().getQuantity()+_quantity);
         this.save(this.list.iterator().next().product);//update in table
         this.fireEvent(update);
        }else
        if(this.list.iterator().next().product.getSku() != _sku){
        System.out.println("record not found");
        }
    }


    /*
    *  Increases the quantity by one.
    */
    public void incrementInventory(String _sku){
        if(this.list.iterator().next().product.getSku() == _sku){
        this.list.iterator().next().product.setQuantity(this.list.iterator().next().getQuantity()+1);
        this.save(this.list.iterator().next().product);//update table
        this.fireEvent(update);
        }
    }


    @Override
    public void messageReceived(BoffoEvent _event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


     public ArrayList searchInventoryByName(String _productName){
         ArrayList<InventoryRecord> l = null;
         if(this.list.iterator().next().product.getName() == _productName){
             l.add(this.list.iterator().next());
        }
         return l;
        }


     public ArrayList searchInventoryByPrice(double _price){
         ArrayList<InventoryRecord> l = null;
         if(this.list.iterator().next().product.getPrice() == _price){
             l.add(this.list.iterator().next());
        }
         return l;
        }


     public ArrayList searchInventoryBySku(String _sku){
         ArrayList<InventoryRecord> l = null;
         if(this.list.iterator().next().product.getSku() == _sku){
             l.add(this.list.iterator().next());
        }
         return l;
        }


     public ArrayList searchInventoryByUuid(String _uuid){
         ArrayList<InventoryRecord> l = null;
         if(this.list.iterator().next().product.getUuid() == _uuid){
             l.add(this.list.iterator().next());
        }
         return l;
        }
}
