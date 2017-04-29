package authorization;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

public class AuthTest implements AuthorizationInterface {
    protected static HashMap<String, ArrayList<Integer>> test = new HashMap<>();

    public static void main(String[] args) {
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

        test.put("addRemoveItemsToDB", addRemoveItemsToDB);
        test.put("checkoutCustomer", checkoutCustomer);
        test.put("setStoreActions", setStoreActions);
        test.put("addRemoveTicketItem", addRemoveTicketItem);
        test.put("changeSystemSettings", changeSystemSettings);
        test.put("viewPriceOfItem", viewPriceOfItem);
        test.put("viewEmployeeSSN", viewEmployeeSSN);
        if(Authorization.isAuthorized(addRemoveItemsToDB)){
            System.out.println("Authorized!");
        }
        if(Authorization.isAuthorized(test.get("addRemoveItemsToDB"))){
            System.out.println("Authorized!");
        }
        else {
            System.out.println("Unauthorized!");
        }
        if(Authorization.isAuthorized(test.get("viewPriceOfItem"))){
            System.out.println("Authorized!");
        }
        else{
            System.out.println("Unauthorized!");
        }
        if(Authorization.isAuthorized(test.get("changeSystemSettings"))){
            System.out.println("Authorized!");
        }
        else{
            System.out.println("Unauthorized!");
        }
    }
}