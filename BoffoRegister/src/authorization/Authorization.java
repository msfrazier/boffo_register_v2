package authorization;

import bofforegister.BoffoController;

/**
 * @author CJ
 * @author Thomas Pedraza
 *
 * The sole purpose of this class is for Administration, Transaction, and
 * Inventory to pass an Integer from their HashMaps.
 *
 */

public class Authorization {
    public static boolean isAuthorized (int minAuthLevel){
        return (BoffoController.CURRENT_USER.getAuthlevel() >= minAuthLevel);
    }
}
