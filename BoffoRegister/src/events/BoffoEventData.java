package events;

/**
 * Last Edited: 4/25
 * This class is for passing data between generic events.
 * @author Ray
 */
import database.BoffoDbObject;
import java.util.*;

public class BoffoEventData<T> {

    T eventData;
    BoffoDbObject eventObj;
    public enum EventType{PRINT, SAVE, LOAD};
    EventType eventType;
    
   public BoffoEventData() {
        
    }
    
    /**
     * Constructor to pass a basic data type.
     * @param _data The basic data type you wish to pass.
     */
   public BoffoEventData(T _data) {
        this.eventData = _data;
    }

   
/**
 *  Constructor to pass entire BoffoDbObjects in an event.  
 * @param _obj BoffoDbObject you wish to pass.
 */
    public BoffoEventData(BoffoDbObject _obj) {
        this.eventObj = _obj;
    }

    
    //getters and setters
   public T getEventData() {
        return this.eventData;
    }


    public BoffoDbObject getEventObj() {
        return this.eventObj;
    }


    public void setEventData(T _data) {
        this.eventData = _data;
    }


    public void setEventObj(BoffoDbObject _obj) {
        this.eventObj = _obj;
    }
    
    public EventType getEventType(){
        return this.eventType;
    }
    
    public void setEventType( EventType _newType){
        this.eventType = _newType;
    }

}
