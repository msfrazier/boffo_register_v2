package bofforegister;

/*
 * Last Updated: 04/09/2017
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

        /**
         * We need a login event?

         */	 
        if(_event.getMessage().getCode() instanceof BoffoNavigateEventData) {
            changePanel(_event);
            return;
        }

        fireEvent(_event);
    }

    private void changePanel(BoffoEvent _event) {
        // Get the event data as a seperate object.
        BoffoNavigateEventData eventData = (BoffoNavigateEventData)_event.getMessage().getCode();    
        // 
        switch (eventData.getEventType()) {
            case LOGIN_PANEL:
                toLogin();
                break;
            case MAIN_PANEL:
                toMainMenu();
                break;
            case ADMIN_PANEL:
                toAdmin();
                break;
            case INVENTORY_PANEL:
                toInventory();
                break;
            case TRANSACTION_PANEL:
                toTransaction();
                break;
            default:
                // If we have reached this point then something has gone wrong...
                break;
        }
    }

    private void toLogin(){
        // log out the current user and change to the login panel.
        CURRENT_USER = null;
        this.removeAllListeners();
        // Remove all our modules.
        this.addListener(gui);
        this.gui.addListener(this);
        this.gui.loadLoginPanel();
    }

    private void toMainMenu() {
        // Change to the main GUI panel.
        this.gui.loadMainPanel();
        this.removeListener(admin);
        this.removeListener(inventory);
        this.removeListener(transaction);
    }

    private void toAdmin() {
        // Change to the admin GUI panel.
        this.gui.loadAdminPanel();
        this.addListener(admin);
        this.admin.addListener(this);
        this.removeListener(inventory);
        this.removeListener(transaction);
    }

    private void toTransaction() {
        // Change to the Inventory GUI panel.
        this.gui.loadTransactionPanel();
        this.addListener(transaction);
        this.transaction.addListener(this);
        this.removeListener(inventory);
        this.removeListener(admin);
    }

    private void toInventory() {
        // Change to the Transaction GUI panel.
        this.gui.loadInventoryPanel();
        this.addListener(inventory);
        this.inventory.addListener(this);
        this.removeListener(inventory);
        this.removeListener(admin);
    }
    
    private void printReceipt() {
        // Pass in all relevent objects into the printer and let it sort them out.
    }
}