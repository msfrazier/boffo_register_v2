
package events;

/**
 *Last updated: 4-27-17
 *
 *This class extends the generic BoffoEvent and is used for anyone who fires
 * events that handle logging in
 *
 * @author Maclean Frazier
 */
public class BoffoLogInEvent extends BoffoEvent {
    BoffoLogInEvent(Object _source, BoffoEventData _messageString){
        super(_source, _messageString);
    }
}
