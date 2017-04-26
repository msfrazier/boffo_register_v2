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

    public BoffoEvent(Object source, BoffoEventData messageString) {
        super(source);
        this.message = new BoffoMessenger(messageString);
    }

    public BoffoMessenger getMessage() {
        return message;
    }

}
