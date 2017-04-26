
package events;
import database.BoffoDbObject;
/**
 * Last Edited: 4/25 
 * This class is for passing data within navigation events.
 * @author Ray
 */
public class BoffoNavigateEventData<T> extends BoffoEventData {

    public enum EventType {
        EXIT_PANEL, LOGIN_PANEL, MAIN_PANEL, TRANSACTION_PANEL,
        INVENTORY_PANEL, ADMIN_PANEL
    };
    EventType eventType;

    /**
     * Constructor to call when you do not need to pass any data.
     * @param _newType The type of event you wish to create.
     */
   public BoffoNavigateEventData(EventType _newType){
        this.eventType = _newType;
    }
    
   
    /**
     * Constructor to call when you need to pass a basic data type.
     * @param _newType The type of event you wish to create.
     * @param _data The basic data type you wish to pass.
     */
    public BoffoNavigateEventData(EventType _newType, T _data) {
        this.eventData = _data;
        this.eventType = _newType;
    }

    
    /**
     * Constructor to call when you need to pass a BoffoDbObject.
     * @param _newType The type of event you wish to create.
     * @param _obj The BoffoDbObject you wish to pass.
     */
    public BoffoNavigateEventData(EventType _newType, BoffoDbObject _obj) {
        this.eventType = _newType;
        this.eventObj = _obj;
    }

    
    public void setEventType(EventType _newType){
        this.eventType = _newType;
    }
    
}