
package events;

/**
 *Last updated: 4-24-17
 *
 *This class will define events that pass UPC codes back and forth
 *
 *
 * @author msf10_000
 */
public class BoffoUPCEvent extends BoffoEvent {

    public BoffoUPCEvent(Object source, BoffoEventData messageString) {
        super(source, messageString);
    }

    public BoffoUPCEvent(Object source, BoffoEventData messageString, String UPC){
        super(source, messageString);
    }

}
