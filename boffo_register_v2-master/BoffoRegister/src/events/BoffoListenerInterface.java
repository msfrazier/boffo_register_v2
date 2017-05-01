package events;

/**
 *Last updated: 4-24-17
 *
 *This interface is used by modules to define how events sent to it are interpreted
 *
 *
 * @author Maclean Frazier
 */

import java.util.ArrayList;
import java.util.List;


public interface BoffoListenerInterface {
    public void messageReceived(BoffoEvent event);
}
