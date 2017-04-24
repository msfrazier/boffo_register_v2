
package inventory;

/**   
 *
 * @author Tey
 */
public class maintest {
    public static void main(String[] args){
    Inventory inv;
        inv = new Inventory();
    //add inventory
    inv.addInventoryRecord("uuid123","sku345", "upc678", 5, StateOfInvetory.USED, "Greensboro", "pillow", 567.76, "bedsuplier");
    inv.addInventoryRecord("uuid567","sku986", "upc789", 2, StateOfInvetory.DAMAGED, "Charlotte", "car", 2.99, "carsuplier");
    inv.addInventoryRecord("uuid3567","sku678", "upc986", 11, StateOfInvetory.NEW, "Virginia", "movie", 90.90, "moviesuplier");
    inv.addInventoryRecord("uuid245","sku089", "upc789897", 989, StateOfInvetory.NEW, "South Carolina", "controller", 20.00, "controllersuplier");
    //test Inventory search methods by printing list return
        for (Object toArray : inv.searchInventoryBySku("sku345").toArray()) {
            System.out.println(toArray);
        }
    
    }
} 
