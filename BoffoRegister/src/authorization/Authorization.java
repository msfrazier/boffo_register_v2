package authorization;

import user.User;
import java.util.HashMap;
public interface Authorization
{
    //This Hashmap should be implemented as protected within your class.
    static HashMap<String, Integer> authTable = new HashMap<>();
    
    boolean isAuthorized(User user, String action);

    /*The best way (we could think of) to implment this is to use a hashmap
    that takes a string 'Action', what the user is trying to do, and a returns
    an integer authorization level, the rank of said action. 
    
    If there is a docs file, the info will be there instead of here.
    */
}