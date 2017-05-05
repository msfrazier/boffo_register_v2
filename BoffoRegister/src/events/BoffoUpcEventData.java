package events;

/**
 * Last Edited: 5/4
 * This class is for passing data within UPC events.
 * @author Ray
 */

import database.BoffoDbObject;

public class BoffoUpcEventData<T> extends BoffoEventData {


    public enum EventType {FIND_UPC, NEW_UPC, ADD_UPC};
    public EventType eventType;

    /**
     * Constructor to call when you do not need to pass any data.
     * @param _newType The type of event you wish to create.
     */
  public BoffoUpcEventData(EventType _newType){
        this.eventType = _newType;
    }


    /**
     * Constructor to call when you need to pass a basic data type.
     * @param _newType The type of event you wish to create.
     * @param _data The basic data type you wish to pass.
     */
    public BoffoUpcEventData(EventType _newType, T _data) {
        this.eventData = _data;
        this.eventType = _newType;
    }


    /**
     * Constructor to call when you want to pass a BoffoDbObject.
     * @param _newType The type of event you wish to create.
     * @param _obj The BoffoDbObject you wish to pass.
     */
    public BoffoUpcEventData(EventType _newType, BoffoDbObject _obj) {
        this.eventType = _newType;
        this.eventObj = _obj;
    }


    public EventType getUpcEventType() {
        return this.eventType;
    }
    
    
    public void setEventType(EventType _newType){
        this.eventType = _newType;
    }

}
