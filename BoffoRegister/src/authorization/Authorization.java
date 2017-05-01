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
        return false;
    }
}
