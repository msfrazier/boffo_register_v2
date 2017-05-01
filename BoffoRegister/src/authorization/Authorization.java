package authorization;

import bofforegister.BoffoController;
<<<<<<< HEAD
=======
import java.util.ArrayList;
import java.util.Iterator;
>>>>>>> master

/**
 * @author CJ
 * @author Thomas Pedraza
 *
 * The sole purpose of this class is for Administration, Transaction, and
<<<<<<< HEAD
 * Inventory to pass an Integer from their HashMaps.
=======
 * Inventory to pass an ArrayList<Integer> from their HashMaps.
>>>>>>> master
 *
 */

public class Authorization {
<<<<<<< HEAD
    public static boolean isAuthorized (int minAuthLevel){
        return (BoffoController.CURRENT_USER.getAuthlevel() >= minAuthLevel);
=======
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
>>>>>>> master
    }
}
