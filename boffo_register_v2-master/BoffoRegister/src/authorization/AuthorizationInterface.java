package authorization;

import java.util.HashMap;
import java.util.ArrayList;

/*
 *
 * @author CJ
 * @author Thomas Pedraza
 *
 *  This Hashmap should be implemented as protected within Inventory,
    Administration, and Transaction's classes.
 *  Add all of the actions one plans to check into the hashmap as String, and
    the corresponding authorization level needed as the int.
 *  Call the authorization class when needed to check if the
    current user has privilege to do an action.
 *  The authorization class "isAuthorized" will return a boolean based on
    the current user and the groups allowed in your ArrayList..
*/
public interface AuthorizationInterface
{
    static HashMap<String, ArrayList<Integer>> authTable = new HashMap<>();
}