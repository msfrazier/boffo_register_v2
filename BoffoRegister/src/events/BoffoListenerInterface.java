package events;

//Maclean Frazier

import java.util.ArrayList;
import java.util.List;


public interface BoffoListenerInterface {
    final List boffoListener = new ArrayList();
    public void addBRegisterListener(BoffoListenerInterface _event);
    public void removeBRegisterListener(BoffoListenerInterface _event);
    public void fireEvent(BoffoEvent _event);
    public void messageReceived(BoffoEvent event);
}
