package inventory;

import authorization.Authorization;
import authorization.AuthorizationInterface;
import database.BoffoDbObject;
import events.BoffoEvent;
import events.BoffoEventData;
import events.BoffoFireObject;
import events.BoffoListenerInterface;
import java.util.ArrayList;
import inventory.InventoryRecord;
import java.util.Arrays;
import product.ProductObject;
import product.ProductObject;
import user.User;

/*
* This class holds a list of InventoryRecord object as ArrayList(dynamic list ).
* It uses the list to manage seaching and updating table in data base.
* authors: Chad Schmidt, Tey Tang
* last updated: 5/1/2017
*/

public class Inventory extends BoffoFireObject implements AuthorizationInterface{

     private ArrayList<InventoryRecord> list = null;
     BoffoEventData data = new BoffoEventData("updated");
     BoffoEvent update = new BoffoEvent(this,data);

    public Inventory(){
       System.out.println("Inventory loaded");
                }


    //if already on list/table based on sku,update quantity of inventory/table
    //if NOT already on list/table based on sku,create new Inventory Record and add to list/table
    public void addInventoryRecord(ProductObject _product,int _quantity, StateOfInvetory _status,String _location,String _vender){
         if(this.list.iterator().next().product.getSku() == _product.getSku()){
         this.list.iterator().next().product.setQuantity(this.list.iterator().next().getQuantity()+_quantity);
         save(this.list.iterator().next().product);//update in table
         fireEvent(update);
         System.out.println("inventory added");
         }else
          if(this.list.iterator().next().product.getSku() != _product.getSku()){
            InventoryRecord r = new InventoryRecord(_product,_quantity,_status,_location,_vender);
            this.list.add(r);
           save(r.product);
           fireEvent(update);
         }
        }


    public static void buildMap(){
         ArrayList<Integer> addInventoryRecord = new ArrayList<>();
        addInventoryRecord.addAll(Arrays.asList(2, 3));
        ArrayList<Integer> deleteInventoryRecord = new ArrayList<>();
        deleteInventoryRecord.addAll(Arrays.asList(2, 3));
        ArrayList<Integer> increaseRecordQuantity = new ArrayList<>();
        increaseRecordQuantity.addAll(Arrays.asList(1, 2, 3));
        ArrayList<Integer> decreaseRecordQuantity = new ArrayList<>();
        decreaseRecordQuantity.addAll(Arrays.asList(1, 2, 3));
        ArrayList<Integer> incrementInventory = new ArrayList<>();
        incrementInventory.addAll(Arrays.asList(1, 2, 3));
        ArrayList<Integer> decrementInventory = new ArrayList<>();
        decrementInventory.addAll(Arrays.asList(1, 2, 3));
        ArrayList<Integer> searchInventoryByName = new ArrayList<>();
        searchInventoryByName.addAll(Arrays.asList(0, 1, 2, 3));
        ArrayList<Integer> searchInventoryByPrice = new ArrayList<>();
        searchInventoryByPrice.addAll(Arrays.asList(0, 1, 2, 3));
        ArrayList<Integer> searchInventoryBySku = new ArrayList<>();
        searchInventoryBySku.addAll(Arrays.asList(0, 1, 2, 3));
        ArrayList<Integer> searchInventoryByUuid = new ArrayList<>();
        searchInventoryByUuid.addAll(Arrays.asList(0, 1, 2, 3));

        authTable.put("addInventoryRecord", addInventoryRecord);
        authTable.put("deleteInventoryRecord", deleteInventoryRecord);
        authTable.put("increaseRecordQuantity", increaseRecordQuantity);
        authTable.put("decreaseRecordQuantity", decreaseRecordQuantity);
        authTable.put("incrementInventory", incrementInventory);
        authTable.put("decrementInventory", decrementInventory);
        authTable.put("searchInventoryByName", searchInventoryByName);
        authTable.put("searchInventoryByPrice", searchInventoryByPrice);
        authTable.put("searchInventoryBySku", searchInventoryBySku);
        authTable.put("searchInventoryByUuid", searchInventoryByUuid);
    }


//decrease quantity of specific record by specified amount
    //if resulting quantity is 0, record is deleted from list/table
    public void decreaseRecordQuantity(String _sku,int _quantity){
        if(this.list.iterator().next().product.getSku() == _sku){
        this.list.iterator().next().product.setQuantity(this.list.iterator().next().getQuantity()-_quantity);
         save(this.list.iterator().next().product);
         fireEvent(update);
        }else
        if((this.list.iterator().next().getQuantity()-_quantity)<0){
                this.list.remove(this.list.iterator().next());
                delete(this.list.iterator().next().product);
                fireEvent(update);
         }else
        if(this.list.iterator().next().product.getSku() != _sku){
            System.out.println("record not found");
        }
    }


    //decrease quantity by one
    public void decrementInventory(String _sku){
        if(this.list.iterator().next().product.getSku() == _sku){
        this.list.iterator().next().product.setQuantity(this.list.iterator().next().getQuantity()-1);
        save(this.list.iterator().next().product);
        fireEvent(update);
        }
    }


    //delete record
 public void deleteInventoryRecord(String _sku){
        if(this.list.iterator().next().product.getSku() == _sku){
        this.list.remove(this.list.iterator().next());
        delete(this.list.iterator().next().product);
        fireEvent(update);
        }else
      if(this.list.iterator().next().product.getSku() != _sku){
        System.out.println("record not found");
            }
        }


    //return count of specific product with sku value
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


    //increase quantity of specific record by specified amount
    public void increaseRecordQuantity(String _sku,int _quantity){
        if(this.list.iterator().next().product.getSku() == _sku){
        this.list.iterator().next().product.setQuantity(this.list.iterator().next().getQuantity()+_quantity);
         save(this.list.iterator().next().product);//update in table
         fireEvent(update);
        }else
        if(this.list.iterator().next().product.getSku() != _sku){
        System.out.println("record not found");
        }
    }


    public void incrementInventory(String _sku){
        if(this.list.iterator().next().product.getSku() == _sku){
        this.list.iterator().next().product.setQuantity(this.list.iterator().next().getQuantity()+1);
        save(this.list.iterator().next().product);//update table
        fireEvent(update);
        }
    }


     //return ArrayList of InventoryRecord(search by productName)
     public ArrayList searchInventoryByName(String _productName){
         ArrayList<InventoryRecord> l = null;
         if(this.list.iterator().next().product.getName() == _productName){
             l.add(this.list.iterator().next());
        }
         return l;
        }


     //return ArrayList of InventoryRecord(search by price)
     public ArrayList searchInventoryByPrice(double _price){
         ArrayList<InventoryRecord> l = null;
         if(this.list.iterator().next().product.getPrice() == _price){
             l.add(this.list.iterator().next());
        }
         return l;
        }


     //return ArrayList of InventoryRecord(search by sku)
     public ArrayList searchInventoryBySku(String _sku){
         ArrayList<InventoryRecord> l = null;
         if(this.list.iterator().next().product.getSku() == _sku){
             l.add(this.list.iterator().next());
        }
         return l;
        }


     //return ArrayList of InventoryRecord(search by uuid)
     public ArrayList searchInventoryByUuid(String _uuid){
         ArrayList<InventoryRecord> l = null;
         if(this.list.iterator().next().product.getUuid() == _uuid){
             l.add(this.list.iterator().next());
        }
         return l;
        }


     public static void testAuthorization () {
                    if(Authorization.isAuthorized(authTable.get("addInventoryRecord"))){
            System.out.println("Authorized!");
        }
        else {
            System.out.println("Unauthorized!");
        }
        if(Authorization.isAuthorized(authTable.get("deleteInventoryRecord"))){
            System.out.println("Authorized!");
        }
        else{
            System.out.println("Unauthorized!");
        }
        if(Authorization.isAuthorized(authTable.get("decreaseRecordQuantity"))){
            System.out.println("Authorized!");
        }
        else{
            System.out.println("Unauthorized!");
        }
        if(Authorization.isAuthorized(authTable.get("increaseRecordQuantity"))){
            System.out.println("Authorized!");
        }
        else{
            System.out.println("Unauthorized!");
        }
        if(Authorization.isAuthorized(authTable.get("incrementInventory"))){
            System.out.println("Authorized!");
        }
        else{
            System.out.println("Unauthorized!");
        }
        if(Authorization.isAuthorized(authTable.get("decrementInventory"))){
            System.out.println("Authorized!");
        }
        else{
            System.out.println("Unauthorized!");
        }
        if(Authorization.isAuthorized(authTable.get("searchInventoryByName"))){
            System.out.println("Authorized!");
        }
        else{
            System.out.println("Unauthorized!");
        }
        if(Authorization.isAuthorized(authTable.get("searchInventoryByPrice"))){
            System.out.println("Authorized!");
        }
        else{
            System.out.println("Unauthorized!");
        }
        if(Authorization.isAuthorized(authTable.get("searchInventoryBySku"))){
            System.out.println("Authorized!");
        }
        else{
            System.out.println("Unauthorized!");
        }
        if(Authorization.isAuthorized(authTable.get("searchInventoryByUuid"))){
            System.out.println("Authorized!");
        }
        else{
            System.out.println("Unauthorized!");
        }
    }
}