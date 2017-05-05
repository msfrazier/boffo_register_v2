
package events;

/**
 *Last updated: 4-24-17
 *
 *This class extends the generic BoffoEvent and is used for anyone who fires
 * events that do not fit into the other three catagories.
 *
 * @author Maclean Frazier
 */
public class BoffoGenericEvent extends BoffoEvent{

    public BoffoGenericEvent(Object _source, BoffoEventData _messageString) {
        super(_source, _messageString);
    }
}
