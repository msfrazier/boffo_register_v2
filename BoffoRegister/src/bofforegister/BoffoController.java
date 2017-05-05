package bofforegister;

/*
 * Last Updated: 05/01/2017
 *
 * This class is the controller for the BoffoRegister.
 * The controller 'controls' the BoffoRegisters actions.
 * Very little logic should be implemented within.  The
 * other modules and classes have their logic and this
 * controller class is meant to utilize the methods
 * and pass along events from other modules in order to
 * maintain proper functioning of the BoffoRegister.
 *
 * @author Joshua Brown & Josh Milligan
 */
import administration.Administration;
import administration.AdministrationObject;
import events.BoffoEvent;
import events.*;
import events.BoffoFireObject;
import events.BoffoListenerInterface;
import gui.BoffoRegisterGUI;
import inventory.Inventory;
import javafx.stage.Stage;
import printer.Printer;
import transaction.Transaction;
import user.User;

public class BoffoController extends BoffoFireObject implements BoffoListenerInterface {

    public static User CURRENT_USER = null;

    protected AdministrationObject admin = new AdministrationObject();
    protected BoffoRegisterGUI gui = null;
    protected Inventory inventory = new Inventory();
    protected Printer printer = new Printer();
    protected Transaction transaction = new Transaction();


    BoffoController(Stage _primaryStage) {
        this.gui = new BoffoRegisterGUI(_primaryStage);
    }


    public void initilize() {
        this.gui.addListener(this);
        this.addListener(gui);
    }


    @Override
    public void messageReceived(BoffoEvent _event) {
<<<<<<< HEAD

        /**
         * We need a login event?

         */	 
        if(_event.getMessage().getCode() instanceof BoffoNavigateEventData) {
=======
        // Need a login event.
        if (_event.getMessage().getCode() instanceof BoffoLogInEventData) {
            userEvents(_event);
            return;
        }
        // Need a logout event.
        // Need a print event, can be a generic event if needed.
        else if (_event.getMessage().getCode() instanceof BoffoNavigateEventData) {
>>>>>>> master
            changePanel(_event);
            return;
        }

        fireEvent(_event);
    }


    private void changePanel(BoffoEvent _event) {
        // Get the event data as a seperate object.
<<<<<<< HEAD
        BoffoNavigateEventData eventData = (BoffoNavigateEventData)_event.getMessage().getCode();    
        // 
=======
        BoffoNavigateEventData eventData = (BoffoNavigateEventData) _event.getMessage().getCode();
>>>>>>> master
        switch (eventData.getEventType()) {
            case LOGIN_PANEL:
                CURRENT_USER = null;
                this.removeAllExcept(gui);
                this.gui.loadLoginPanel();
                break;
            case MAIN_PANEL:
                this.removeAllExcept(gui);
                this.gui.loadMainPanel();
                break;
            case ADMIN_PANEL:
                this.gui.loadAdminPanel();
                this.admin.addListener(this);
                this.changeTo(admin);
                break;
            case INVENTORY_PANEL:
                this.gui.loadInventoryPanel();
                this.transaction.addListener(this);
                this.changeTo(inventory);
                break;
            case TRANSACTION_PANEL:
                this.gui.loadTransactionPanel();
                this.inventory.addListener(this);
                this.changeTo(transaction);
                break;
            default:
                // If we have reached this point then something has gone wrong...
                break;
        }
    }


    private void userEvent(BoffoEvent _event) {
        BoffoLogInEventData loginEvent = (BoffoLogInEventData) _event.getMessage().getCode();
        switch (loginEvent.getEventType()) {
            case LOGIN_EVENT:

        }
    }


    private void changeTo(BoffoListenerInterface _listener) {
    this.removeAllExcept(gui);
    this.addListener(_listener);
    }
<<<<<<< HEAD
    
=======


    // Pass in all relevent objects into the printer and let it sort them out.
>>>>>>> master
    private void printReceipt() {
        
    }
}
