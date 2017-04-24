package user;
/*
Author: SHANSHAN CHEN
Last update: 04/19/2017
*/
import events.BoffoEvent;
import database.BoffoDbObject;
import events.BoffoMessenger;   

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import static user.User.PasswordHash.HASH_BYTE_SIZE;
import static user.User.PasswordHash.PBKDF2_ITERATIONS;
import static user.User.PasswordHash.SALT_BYTE_SIZE;

public class User extends BoffoDbObject{   
    private String username = "";
    private String f_name;
    private String l_name;
    private int id;
    private String pass = "";
    
    
public void User(String username, String pass){
    this.username = username;
    this.pass = pass;
}
        
public String getUsername(){
        return username;
    }       
public void setUsername(String _userName){
        this.username = _userName;
    }  
public String getPass(){
        return pass;
    }
public void setPass(String _userPass){
        this.pass = _userPass;
    }

//set the variables in the passwordhash class.
public class PasswordHash
{
    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";
    public static final int SALT_BYTE_SIZE = 24;
    public static final int HASH_BYTE_SIZE = 24;
    public static final int PBKDF2_ITERATIONS = 1000;

    public static final int ITERATION_INDEX = 0;
    public static final int SALT_INDEX = 1;
    public static final int PBKDF2_INDEX = 2;    
}

    public static String createHash(String password)
        throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        return createHash(password.toCharArray());
    }
    
    public static String createHash(char[] password)
        throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);

        // Hash the password
        byte[] hash = pbkdf2(password, salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
        // format iterations:salt:hash
        return PBKDF2_ITERATIONS + ":" + toHex(salt) + ":" +  toHex(hash);
    }
    
    public static byte[] PasswordHash( final char[] password, final byte[] salt, final int iterations, final int keyLength ) {
 
       try {
           SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA1" );
           PBEKeySpec spec = new PBEKeySpec( password, salt, iterations, keyLength );
           SecretKey key = skf.generateSecret( spec );
           byte[] res = key.getEncoded( );
           return res;
 
       } catch( NoSuchAlgorithmException | InvalidKeySpecException e ) {
           throw new RuntimeException( e );
       }
   }
    public void messageReceived(BoffoEvent event){
        
        switch(event.getMessage().getCode()){

}
}
    private static String toHex(byte[] salt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int PBKDF2_ITERATIONS, int HASH_BYTE_SIZE) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}