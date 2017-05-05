
package events;

/**
 *Last updated: 4-26-17
 *
 *This class extends the generic BoffoEvent and is used for anyone who fires
 * events that open other panels
 *
 * @author Maclean Frazier
 */
public class BoffoNavigatorEvent extends BoffoEvent{
      public BoffoNavigatorEvent(Object _source, BoffoEventData _messageString){
          super(_source, _messageString);
      }
}
