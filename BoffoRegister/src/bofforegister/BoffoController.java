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
import events.BoffoEvent;
import events.*;
import events.BoffoFireObject;
import gui.BoffoRegisterGUI;
import javafx.stage.Stage;
import events.BoffoListenerInterface;
import inventory.Inventory;
import printer.Printer;
import transaction.Transaction;
import user.User;

public class BoffoController extends BoffoFireObject implements BoffoListenerInterface {

    public static User CURRENT_USER = null;

    protected Transaction transaction = new Transaction();
    protected Inventory inventory = new Inventory();
    protected Administration admin = new Administration();
    protected Printer printer = new Printer();
    protected BoffoRegisterGUI gui = null;

    BoffoController(Stage _primaryStage) {
        this.gui = new BoffoRegisterGUI(_primaryStage);
    }

    /**
     *
     */
    public void initilize() {
        this.gui.addListener(this);
        this.addListener(gui);
    }

    /**
     * Changes the active module, changes the GUI panel, and calls out to
     * registerPanelListener change the current listener.
     *
     * @param _event
     */
    @Override
    public void messageReceived(BoffoEvent _event) {
        // Need a login event.
        // Need a logout event.
        // Need a print event, can be a generic event if needed.
        if(_event.getMessage().getCode() instanceof BoffoNavigateEventData) {
            changePanel(_event);
            return;
        }

        fireEvent(_event);
    }

    /**
     *
     * @param _event
     */
    private void changePanel(BoffoEvent _event) {
        // Get the event data as a seperate object.
        BoffoNavigateEventData eventData = (BoffoNavigateEventData)_event.getMessage().getCode();
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

    /**
     *
     * @param _listener
     */
    private void changeTo(BoffoListenerInterface _listener) {
        this.removeAllExcept(gui);
        this.addListener(_listener);
    }

    /**
     *
     */
    private void printReceipt() {
        // Pass in all relevent objects into the printer and let it sort them out.
    }
}