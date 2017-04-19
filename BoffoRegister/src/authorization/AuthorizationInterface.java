package authorization;

import java.util.HashMap;

public interface AuthorizationInterface
{
    //This Hashmap should be implemented as protected within your class.
    //You will need to add all of the actions you plan to check into this hashmap.
    //Then, you will call the authorization class when you want to check if user has privilege to do said action.
    //The authorization class "isAuthorized" will return a boolean.
    static HashMap<String, Integer> authTable = new HashMap<>();
}