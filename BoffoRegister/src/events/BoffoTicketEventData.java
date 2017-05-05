package events;

/**
 * Last Edited: 5/4
 * This class is for passing data between Ticket events.
 *
 * @author Ray
 */
import database.BoffoDbObject;

public class BoffoTicketEventData<T> extends BoffoEventData {

    public enum EventType {NEW_TICKET, CLOSED_TICKET, LOAD_TICKET};
    public EventType eventType;

    /**
     * Constructor to call when you do not need to pass any data.
     *
     * @param _newType The type of event you wish to create.
     */
    public BoffoTicketEventData(EventType _newType) {
        this.eventType = _newType;
    }


    /**
     * Constructor for passing a basic data type.
     *
     * @param _newType The type of event you wish to create.
     * @param _data The basic data type you wish to pass.
     */
    public BoffoTicketEventData(EventType _newType, T _data) {
        this.eventType = _newType;
        this.eventData = _data;
    }


    /**
     * Constructor for passing a BoffoDbObject.
     *
     * @param _newType The type of event you wish to create.
     * @param _obj The BoffoDbObject you wish to pass.
     */
    public BoffoTicketEventData(EventType _newType, BoffoDbObject _obj) {
        this.eventType = _newType;
        this.eventObj = _obj;
    }


    public EventType getTicketEventType() {
        return this.eventType;
    }


    public void setEventType(EventType _newType) {
        this.eventType = _newType;
    }

}
