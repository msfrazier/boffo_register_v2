package events;

/**
 *Last updated: 4-24-17
 *
 *This class handles the message object and gives methods to recieve the data
 *contained
 *
 * @author Maclean Frazier
 */

public class BoffoMessenger {
    private final BoffoEventData THIS_MESSAGE;
    public BoffoMessenger(BoffoEventData message) {
        this.THIS_MESSAGE = message;
    }

    public BoffoEventData getCode() {
        return this.THIS_MESSAGE;
    }

}