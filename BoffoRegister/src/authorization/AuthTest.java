package authorization;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

// This is a test and example class for the ones implementing it.

public class AuthTest implements AuthorizationInterface {
    protected static HashMap<String, ArrayList<Integer>> authTable = new HashMap<>();

    public static void main(String[] args) {
        buildMap();
        testAuthorization();
    }
    //building the HashMap
    //EX: If managers and employees are the only ones that can do something (Not admin's or guests) , add
    //1, 2 to the arraylist.
        public static void buildMap () {
        ArrayList<Integer> addRemoveItemsToDB = new ArrayList<>();
        addRemoveItemsToDB.addAll(Arrays.asList(2, 3));
        ArrayList<Integer> checkoutCustomer = new ArrayList<>();
        checkoutCustomer.addAll(Arrays.asList(0, 1, 2));
        ArrayList<Integer> setStoreActions = new ArrayList<>();
        setStoreActions.add(2);
        ArrayList<Integer> addRemoveTicketItem = new ArrayList<>();
        addRemoveTicketItem.addAll(Arrays.asList(0, 1, 2));
        ArrayList<Integer> changeSystemSettings = new ArrayList<>();
        changeSystemSettings.addAll(Arrays.asList(2, 3));
        ArrayList<Integer> viewEmployeeSSN = new ArrayList<>();
        viewEmployeeSSN.add(2);
        ArrayList<Integer> viewPriceOfItem = new ArrayList<>();
        viewPriceOfItem.addAll(Arrays.asList(0, 1, 2, 3));

        authTable.put("addRemoveItemsToDB", addRemoveItemsToDB);
        authTable.put("checkoutCustomer", checkoutCustomer);
        authTable.put("setStoreActions", setStoreActions);
        authTable.put("addRemoveTicketItem", addRemoveTicketItem);
        authTable.put("changeSystemSettings", changeSystemSettings);
        authTable.put("viewPriceOfItem", viewPriceOfItem);
        authTable.put("viewEmployeeSSN", viewEmployeeSSN);
        }

        public static void testAuthorization () {
                    if(Authorization.isAuthorized(authTable.get("addRemoveItemsToDB"))){
            System.out.println("Authorized!");
        }
        else {
            System.out.println("Unauthorized!");
        }
        if(Authorization.isAuthorized(authTable.get("viewPriceOfItem"))){
            System.out.println("Authorized!");
        }
        else{
            System.out.println("Unauthorized!");
        }
        if(Authorization.isAuthorized(authTable.get("changeSystemSettings"))){
            System.out.println("Authorized!");
        }
        else{
            System.out.println("Unauthorized!");
        }
    }
}