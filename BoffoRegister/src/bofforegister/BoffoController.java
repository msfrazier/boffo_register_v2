package bofforegister;

/*
 * Last Updated: 05/05/2017
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
    protected Printer printer = null;
    protected Transaction transaction = new Transaction();


    BoffoController(Stage _primaryStage) {
        this.gui = new BoffoRegisterGUI(_primaryStage);
    }


    /**
     * This method adds the gui to our list of listeners and then adds us
     * to the gui's list of listeners.  This must be called from BoffoRegister
     * class immediately after constructing the BoffoController object.
     */
    public void initilize() {
        this.gui.addListener(this);
        this.addListener(gui);
    }


    /**
     * This method overrides the BoffoListenerInterface method used
     * to identify the incoming event types.
     * @param _event This represents any BoffoEventData object
     */
    @Override
    public void messageReceived(BoffoEvent _event) {
        System.out.println("Controller received some boffo event.");
        if(_event.getMessage().getCode().getEventType() == BoffoEventData.EventType.PRINT) {
            printReceipt();
            return;
        }
        if (_event.getMessage().getCode() instanceof BoffoUserEventData) {
            userEvent(_event);
            return;
        }
        else if (_event.getMessage().getCode() instanceof BoffoNavigateEventData) {
            changePanel(_event);
            return;
        }

        fireEvent(_event);
    }


    /**
     * This method takes panel related events. The gui class is called
     * to change panels based on the event object passed.
     * @param _event This represents a BoffoNavigateEventData object for panel changing.
     */
    private void changePanel(BoffoEvent _event) {
        // Get the event data as a seperate object.
        BoffoNavigateEventData eventData = (BoffoNavigateEventData) _event.getMessage().getCode();
        System.out.println("Controller received the change panel event.");
        switch (eventData.getNavigateEventType()) {
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


    /**
     * This method removes all listeners except the gui and
     * attaches the relevant module as a listener.
     * @param _listener This takes any possible BoffoListenerInterface module.
     */
    private void changeTo(BoffoListenerInterface _listener) {
        this.removeAllExcept(gui);
        this.addListener(_listener);
    }


    /**
     * This method prints a receipt by passing in a Transaction and
     * AdministrationObject.
     */
    private void printReceipt() {
        try{
            this.printer.receiveData(transaction, admin);
        }
        catch(Exception e) {

        }
    }

    /**
     * Takes an event object to for logging the user into the system
     * @param _event This represents a UserEventData object.
     */
    private void userEvent(BoffoEvent _event) {
        BoffoUserEventData loginEvent = (BoffoUserEventData) _event.getMessage().getCode();
        System.out.println("Controller received the user event.");
        CURRENT_USER = new User((String)loginEvent.getUserName(), (String)loginEvent.getUserPass());
        this.gui.loadMainPanel();
    }
}
