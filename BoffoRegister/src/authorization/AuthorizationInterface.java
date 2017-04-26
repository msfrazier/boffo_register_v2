package authorization;

import java.util.HashMap;
/*
 *
 * @author CJ
 * @author Thomas Pedraza
 *
 *  This Hashmap should be implemented as protected within Inventory,
    Administration, and Transaction's classes.
 *  Add all of the actions to plan to check into the hashmap as String, and
    the corresponding authorization level needed as the int.
 *  Call the authorization class when needed to check if the
    current user has privilege to do an action.
 *  The authorization class "isAuthorized" will return a boolean based on
    the current user and the minimum auth level.
*/
public interface AuthorizationInterface
{
    static HashMap<String, Integer> authTable = new HashMap<>();
}