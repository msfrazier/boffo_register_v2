package events;

/*
Last edited: 4/23

This class pairs a string with a T or BoffoDbObject so events can pass
specified data.

Ray Cockerham
 */
import database.BoffoDbObject;

public class BoffoEventData<T> {

    T eventData;
    String eventType;
    BoffoDbObject eventObj;

    //constructor to pass any value in an event.
    BoffoEventData(String _str, T _data) {
        this.eventType = _str;
        this.eventData = _data;
    }


    //overloaded constructor to pass entire BoffoDbObjects in an event.
    BoffoEventData(String _str, BoffoDbObject _obj) {
        this.eventType = _str;
        this.eventObj = _obj;
    }


    //getters and setters
    T getEventData() {
        return this.eventData;
    }


    BoffoDbObject getEventObj() {
        return this.eventObj;
    }


    String getEventType() {
        return this.eventType;
    }


    void setEventData(T _data) {
        this.eventData = _data;
    }


    void setEventObj(BoffoDbObject _obj) {
        this.eventObj = _obj;
    }


    void setEventType(String _str) {
        this.eventType = _str;
    }

}
