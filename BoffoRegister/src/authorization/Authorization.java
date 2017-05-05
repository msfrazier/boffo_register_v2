package authorization;

import bofforegister.BoffoController;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author CJ
 * @author Thomas Pedraza
 *
 * The sole purpose of this class is for Administration, Transaction, and
 * Inventory to pass an ArrayList<Integer> from their HashMaps.
 *
 */

public class Authorization {


    public static int failedAttempts = 0;


    public static boolean isAuthorized (ArrayList<Integer> authorizedGroups) {
        if(authorizedGroups.isEmpty()){
            return false;
        }
        Iterator<Integer> it = authorizedGroups.iterator();
        while(it.hasNext()){
            int currInt = it.next();
            if(currInt == BoffoController.CURRENT_USER.getAuthlevel()){
                return true;
            }
        }
        failedAttempts++;
        return false;
    }

    public static String getFailedAttempts()
    {
        if(BoffoController.CURRENT_USER.getAuthlevel() >= 2)
        {
            return "" + failedAttempts;
        }
        else
        {
            return "Not authorized for this action.";
        }
    }

    public static String resetFailedAttempts()
    {
        if(BoffoController.CURRENT_USER.getAuthlevel() >= 2)
        {
            failedAttempts = 0;
            return "Reset successful.";
        }
        else
        {
            return "Not authorized for this action.";
        }
    }
}
