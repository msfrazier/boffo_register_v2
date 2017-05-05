package transaction;

/**
 * Transaction class
 * @author Fang Yang
 * @author Mabelyn Espinoza
 */
import events.BoffoEvent;
import events.BoffoFireObject;
import events.BoffoListenerInterface;

public class Transaction extends BoffoFireObject implements BoffoListenerInterface {

    public Transaction() {
        System.out.println("Transaction Module Loaded");
    }
    
    /**
     * Sends event to listener and awaits response.
     * @param event
     */
    @Override
    public void messageReceived(BoffoEvent event) {
//        switch (event.getMessage().getCode()) {
//            case RegisterMessage.addProduct:
//                this.addItem();
//                break;
//            case RegisterMessage.removeProduct:
//                this.removeItem();
//                break;
//            default:
//                System.out.println("Ignoring Message Event. "
//                        + "Irrelevant to Transaction" + event.getMessage().getCode());
//                break;
//        }
    }
}
