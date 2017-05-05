package events;

/*
Last edited: 4/23

This object is used to fire events and will be inherited by any module thagt needs
to fire events

@author Maclean Frazier
 */

import database.BoffoDbObject;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class BoffoFireObject extends BoffoDbObject {
    //Lists the liseners an event firing class will have.
    private final List listeners = new ArrayList();

    public BoffoFireObject() {
    }
    //Generic add and remove.
    public synchronized void addListener(BoffoListenerInterface _listener) {
        if (!this.listeners.contains(_listener))
            listeners.add(_listener);
    }

    public synchronized void removeListener(BoffoListenerInterface _listener) {
        if (this.listeners.contains(_listener))
            listeners.remove(_listener);
    }

    public synchronized void removeAllListeners() {
        listeners.clear();
    }


    public synchronized void removeAllExcept(BoffoListenerInterface _listener) {
        listeners.clear();
        listeners.add(_listener);
    }


    public synchronized boolean checkListener(BoffoListenerInterface _listener){
        if(this.listeners.contains(_listener)){
            return true;
        }
        else{
            return false;
        }
    }

    protected synchronized void fireEvent(BoffoEvent _event) {
        // Clone the active listeners.
        Object[] tempList = this.listeners.toArray();
        //Cycle through listeners and fire events.
        for (Object tempList1 : tempList) {
            BoffoListenerInterface tempObj = (BoffoListenerInterface) tempList1;
            tempObj.messageReceived(_event);
        }
    }
    //This method fires an event to only one specific listener.
    protected synchronized void fireSpecificEvent(BoffoEvent _event, BoffoListenerInterface _listener){
        if(this.checkListener(_listener)){
            _listener.messageReceived(_event);
        }
    }
}