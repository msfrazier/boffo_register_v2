package user;

import events.BoffoEvent;
import database.BoffoDbObject;
import events.BoffoMessenger;   

public class User{
    private String username;
    private String f_name;
    private String l_name;
    private int id;
    private String pass;
    

public User(){
    
    this.username = "";
    this.pass = "";
    
}
    
public void User(String username, String pass){

    this.username = username;
    this.pass = pass;

}

public void User(){
   
    this.username = username;
    this.id = id;
    this.f_name = f_name;
    this.l_name = l_name;
}
        
public String getUsername(){
        return username;
    }       
public void setUsername(String username){
        this.username = username;
    }  
public String getPass(){
        return pass;
    }
public void setPass(String pass){
        this.pass = pass;
    }

public void messageReceived(BoffoEvent event){
        
        switch(event.getMessage().getCode()){

}
}
}