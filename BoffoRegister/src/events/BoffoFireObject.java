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

public class BoffoFireObject extends BoffoDbObject {
    //Lists the liseners an event firing class will have
    private final List listeners = new ArrayList();

    public BoffoFireObject() {
    }
    //Generic add and remove
    public synchronized void addListener(BoffoListenerInterface listener) {
        if (!this.listeners.contains(listener))
            listeners.add(listener);
    }

    public synchronized void removeListener(BoffoListenerInterface listener) {
        if (this.listeners.contains(listener))
            listeners.remove(listener);
    }

    public synchronized void removeAllListeners() {
        listeners.clear();
    }

    protected synchronized void fireEvent(BoffoEvent event) {
        // Clone the active listeners.
        Object[] tempList = this.listeners.toArray();
        //Cycle through listeners and fire events
        for (Object tempList1 : tempList) {
            BoffoListenerInterface tempObj = (BoffoListenerInterface) tempList1;
            tempObj.messageReceived(event);
        }
    }
}