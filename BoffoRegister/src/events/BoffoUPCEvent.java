
package events;

/**
 *Last updated: 4-26-17
 *
 *This class will define events that pass UPC codes back and forth
 *
 *
 * @author msf10_000
 */
public class BoffoUPCEvent extends BoffoEvent {

    public BoffoUPCEvent(Object _source, BoffoEventData _messageString) {
        super(_source, _messageString);
    }
}
