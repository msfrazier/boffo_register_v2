package events;

/**
 *Last Edited: 5/4
 * This class is for passing data within User events.
 * @author Ray
 */
import database.BoffoDbObject;

public class BoffoUserEventData<T> extends BoffoEventData {
    
    public enum EventType {NEW_USER, DELETE_USER};
    EventType eventType;
    
    /**
     * Constructor to call when you do not need to pass any data.
     * @param _newType The new type of event you wish to create.
     */
    public BoffoUserEventData(EventType _newType) {
        this.eventType = _newType;
    }
    
    
    /**
     * Constructor to call when you need to pass a basic data type.
     * @param _newType The new type of event you wish to create.
     * @param _data The basic data type you are trying to pass.
     */
    public BoffoUserEventData(EventType _newType, T _data) {
        this.eventType = _newType;
        this.eventData = _data;
    }
    
    
    /**
     * Constructor to call when you want to pass an entire BoffoDbObject.
     * @param _newType The type of event you wish to create.
     * @param _obj BoffoDbObject you wish to pass.
     */
    public BoffoUserEventData(EventType _newType, BoffoDbObject _obj) {
        this.eventType = _newType;
        this.eventObj = _obj;
    }
    
    
    public void setEventType(EventType _newType) {
        this.eventType = _newType;
    }
}
