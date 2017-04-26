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
import events.BoffoEventData;
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
    protected BoffoListenerInterface currentModule = null;

    BoffoController(Stage _primaryStage) {
        this.gui = new BoffoRegisterGUI(_primaryStage);
        //
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
         * 
         */
        switch(_event.getMessage().getCode().getEventType) {
            case EventType.LOGIN_REQUEST:
                //if(User.loginUser(_event)) {
                CURRENT_USER = new User(/*
                        _event.getMessage.getCode().getEventData(),
                _event.getMessageValue(MessageCodes.USER_PASS)*/);
                this.gui.loadMainPanel();
                this.gui.addListener(this);
                //}
                break;
        // If the messageString does not fall within range, ignore it.
            case EventType.LOGOUT_REQUEST:
                // TODO Call a static logout class?
                this.fireEvent(_event);
                this.removeAllListeners();
                // TODO Null out all modules? Somehow wait for them to finish tasks before-hand?
                CURRENT_USER = null;
                this.gui.loadLoginPanel();
                break;
            case EventType.FIND_UPC:
                //If currentModule is a Transaction class remove inventory from listeners
                // else remove transaction from listeners because both don't need
                // to edit look for UPC at the same time.
                if (this.currentModule.getClass().equals(transaction.getClass()))
                    this.removeListener(inventory);
                else
                    this.removeListener(transaction);
                this.fireEvent(_event);
                this.addListener(currentModule);
                break;
            case EventType.NEW_UPC:
                //This should only run by inventory when they add a new item.
                this.removeListener(transaction);
                this.fireEvent(_event);
                break;
            case EventType.PRINT_RECEIPT:
                printReceipt();
                break;
            default:
                this.changePanel(_event);
                break;
        }
    }

    private void changePanel(BoffoEvent _event) {
        //Think I want to change the parameter to changePanel(BoffoBaseModule module)
        switch (_event.getMessage().getCode().getEventType()) {

            case EventType.LOGIN_PANEL:
                // log out the current user and change to the login panel.
                this.gui.loadLoginPanel();

                break;

            case EventType.MAIN_PANEL:
                // Change to the main GUI panel.
                this.gui.loadMainPanel();
                this.gui.addListener(this);

                break;

            case EventType.ADMIN_PANEL:
                // If there is no Administration object, create it.
                // Change to the admin GUI panel.
                this.gui.loadAdminPanel();
                this.currentModule = admin;
                this.addListener(admin);

                break;

            case EventType.INVENTORY_PANEL:
                // If there is no Inventory object, create one.
                // Change to the Inventory GUI panel.
                this.gui.loadInventoryPanel();
                this.addListener(inventory);
                this.inventory.addListener(this);

                break;

            case EventType.TRANSACTION_PANEL:
                // Change to the Transaction GUI panel.
                this.gui.loadTransactionPanel();
                this.addListener(transaction);
                this.transaction.addListener(this);

                break;

            default:
                // If we have reached this point then the message is not for us.
                this.fireEvent(_event);
                break;
        }
    }

    private void printReceipt() {
        // Pass in all relevent objects into the printer and let it sort them out.
    }
}
