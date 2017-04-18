package authorization;

import user.User;

//Authors: CJ and Thomas Pedraza

public class Authorization {
    public boolean isAuthorized (int minAuthLevel){
        //return (User.getAuthLevel >= minAuthLevel);
        //below is to avoid errrors until user has a getAuthLevel method.
        return false;
    }
}
