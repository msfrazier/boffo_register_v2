package events;

/**
 *Last updated: 4-24-17
 *
 *This method defines the BoffoEvent
 *
 *
 * @author Maclean Frazier
 */

import java.util.EventObject;

public class BoffoEvent extends EventObject {

    private final BoffoMessenger message;

    public BoffoEvent(Object _source, BoffoEventData _messageString) {
        super(_source);
        this.message = new BoffoMessenger(_messageString);
    }

    public BoffoMessenger getMessage() {
        return message;
    }

}
