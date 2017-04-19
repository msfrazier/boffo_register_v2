package gui;

/**
 * Boffo Register GUI
 *
 * @author Logan Stanfield and Kevin Keomalaythong Updated 2017-04-19
 */
import events.*;
import java.util.Optional;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public final class BoffoRegisterGUI extends BoffoFireObject {

    private final Stage BoffoStage;

    //Window size properties.
    private final int screenWidth = 800;
    private final int screenHeight = 600;

    //VARIABLES FOR TESTING PURPOSES ONLY//
    int counter = 0;
    //VARIABLES FOR TESTING PURPOSES ONLY//

    public BoffoRegisterGUI(Stage _stage) {
        this.BoffoStage = _stage;
        this.loadLoginPanel();
    }

    public void loadAdminPanel() {
        System.out.println("Loading Administration Panel");
        BoffoStage.setTitle("Administration");
        Scene sceneAdmin = buildAdminPanel();
        BoffoStage.setScene(sceneAdmin);
        BoffoStage.show();
    }

    public void loadInventoryPanel() {
        System.out.println("Loading Inventory Panel");
        BoffoStage.setTitle("Inventory");
        Scene sceneInventory = buildInventoryPanel();
        BoffoStage.setScene(sceneInventory);
        BoffoStage.show();
    }

    public void loadLoginPanel() {
        System.out.println("Loading Login Panel");
        BoffoStage.setTitle("BoffoRegister Login");
        Scene sceneLogin = this.buildLoginScene();
        this.BoffoStage.setScene(sceneLogin);

        //Set up the login stage.
        this.BoffoStage.show();
    }

    public void loadMainPanel() {
        System.out.println("Loading Main Panel");
        BoffoStage.setTitle("Boffo Register Main Menu");
        Scene sceneMain = this.buildMainPanel();

        //Set up the main stage.
        BoffoStage.setScene(sceneMain);
        this.BoffoStage.show();
    }

    public void loadTransactionPanel() {
        System.out.println("Loading Transaction Panel");
        BoffoStage.setTitle("Transaction");
        Scene sceneTransaction = buildTransactionPanel();
        BoffoStage.setScene(sceneTransaction);
        BoffoStage.show();
    }

    //TODO: Add more buttons & associated events to the Administration panel.
    public Scene buildAdminPanel() {
        Button btnExit = new Button("Exit");

        VBox adminOptions = this.addVBox("Select Operation", 10, Pos.BASELINE_LEFT);
        adminOptions.getChildren().add(btnExit);

        //Fire an event to go back to the Main panel.
        btnExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
                loadMainPanel();
//                BoffoEvent evtExit = new BoffoEvent(this, Routing.EXIT_PANEL);
//                fireEvent(evtExit);
            }
        });

        //Set up the Admin panel.
        return new Scene(adminOptions, screenWidth, screenHeight);
    }

    //TODO: Add more buttons & associated events to the Inventory panel.
    public Scene buildInventoryPanel() {
        Button btnExit = new Button("Exit");

        VBox inventoryOptions = this.addVBox("Select Operation", 10, Pos.BASELINE_LEFT);
        inventoryOptions.getChildren().add(btnExit);

        btnExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
                loadMainPanel();
//                BoffoEvent evtExit = new BoffoEvent(this, Routing.EXIT_PANEL);
//                fireEvent(evtExit);
            }
        });

        //Set up the Inventory panel.
        return new Scene(inventoryOptions, screenWidth, screenHeight);
    }

    //Login screen with username & password text fields, plus a sign-in button.
    //TODO: Add event-firing code to the Sign In button.
    public Scene buildLoginScene() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        //Add a title with specified font and text to the scene.
        Text sceneTitle = new Text("Welcome to BoffoRegister");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Button signInBtn = new Button("Sign In");
        HBox signInHB = new HBox(10);
        signInHB.setAlignment(Pos.BOTTOM_RIGHT);
        signInHB.getChildren().add(signInBtn);
        grid.add(signInHB, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        //Fire an event upon pressing the Sign In button.
        signInBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent _e) {
                loadMainPanel();
                //TODO: Check credentials here.
                //TODO: Implement event firing.
            }
        });

        signInBtn.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    loadMainPanel();
                    //Check credentials here.
                }
            }
        });

        pwBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    loadMainPanel();
                    //
                }
            }
        });

        userTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    loadMainPanel();
                //TODO: Check credentials here.
                //TODO: Implement event firing.
                }
            }
        });

        return new Scene(grid, screenWidth, screenHeight);
    }

    public Scene buildMainPanel() {
        Button btnTransaction = new Button("Transaction");
        Button btnInventory = new Button("Inventory");
        Button btnAdministration = new Button("Administration");
        Button btnExit = new Button("Logout");

        VBox vbox = this.addVBox("Select Operation", 10, Pos.BASELINE_LEFT);
        vbox.getChildren().add(btnTransaction);
        vbox.getChildren().add(btnInventory);
        vbox.getChildren().add(btnAdministration);
        vbox.getChildren().add(btnExit);

        //Fire an event to go to the Transaction panel.
        btnTransaction.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
                loadTransactionPanel();
//                BoffoEvent evtTransaction =
//                        new BoffoEvent(this, Routing.TRANSACTION_PANEL);
//                fireEvent(evtTransaction);
            }
        });

        //Fire an event to go to the Inventory panel.
        btnInventory.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
                loadInventoryPanel();
                //BoffoEvent object declaration for Inventory goes here.
            }
        });

        //Fire an event to go to the Administration panel.
        btnAdministration.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
                loadAdminPanel();
                //BoffoEvent object declaration for Administration goes here.
            }
        });

        //Fire an event to sign out.
        btnExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
                loadLoginPanel();
//                BoffoEvent evtExit = new BoffoEvent(this, Routing.EXIT_PANEL);
//                fireEvent(evtExit);
            }
        });

        // Create the scene and return.
        return new Scene(vbox, screenWidth, screenHeight);
    }

    /*
     * TODO: Display ticket items in a TableView or list.
     *       Create event handlers for the buttons.
     */
    public Scene buildTransactionPanel() {
        //Split pane options.
        SplitPane transactionPanel = new SplitPane();
        transactionPanel.setDividerPosition(1, .5);

        //Transaction options settings and configurations.
        Button btnAddItem1 = new Button("Add item 1");
        Button btnAddItem2 = new Button("Add item 2");
        Button btnSubmit = new Button("Submit");
        Button btnExit = new Button("Cancel Transaction");

        //SKU/UPC TextField settings and configurations.
        VBox textFieldVbox = this.addVBox("Enter SKU/UPC", 10, Pos.BOTTOM_LEFT);
        textFieldVbox.setMinHeight(350);
        final TextField skuInputField = new TextField();
        textFieldVbox.getChildren().addAll(skuInputField, btnSubmit);

        //Creates a new VBox for Transaction options.
        VBox transactionOptions = this.addVBox("Select Operation", 10, Pos.BASELINE_LEFT);
        transactionOptions.getChildren().add(btnAddItem1);
        transactionOptions.getChildren().add(btnAddItem2);
        transactionOptions.getChildren().add(btnExit);
        transactionOptions.getChildren().add(textFieldVbox);

        //ticketView settings and layout.
        final ListView ticketView = new ListView();
        ticketView.setMinHeight(400);
        ticketView.setMinWidth(250);

        VBox ticketViewOptions = new VBox(ticketView);
        ticketViewOptions.setAlignment(Pos.TOP_RIGHT);
        ticketViewOptions.setPadding(new Insets(10));
        transactionPanel.getItems().addAll(transactionOptions, ticketViewOptions);

        //Button actions below.
        btnAddItem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
                ticketView.getItems().add("Item 1");
//                BoffoEvent evtAddItem1 =
//                        new BoffoEvent(this, Routing.ADD_TRANSACTION);
//                fireEvent(evtAddItem1);
            }
        });

        btnAddItem2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
                ticketView.getItems().add("Item 2");
//                BoffoEvent evtAddItem2 =
//                        new BoffoEvent(this, Routing.ADD_TRANSACTION);
//                fireEvent(evtAddItem2);
            }
        });

        //Button actions below.
        btnExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
                displayExitAlert("Confirmation",
                        "You will lose any entered data.", "Are you sure?");
            }
        });

        skuInputField.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER) && skuInputField.getText().isEmpty() != true) {
                    ticketView.getItems().add(skuInputField.getText());
                    skuInputField.clear();
                }
            }
        });

        btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (skuInputField.getText().isEmpty() != true) {
                    ticketView.getItems().add(skuInputField.getText());
                    skuInputField.clear();
                }
            }
        });

        btnSubmit.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER) && skuInputField.getText().isEmpty() != true) {
                    ticketView.getItems().add(skuInputField.getText());
                    skuInputField.clear();
                }
            }
        });

        return new Scene(transactionPanel, screenWidth, screenHeight);
    }

    //TODO: Stubbed in method that checks to see if user is logged in.
    private boolean isLoggedIn(/*User object*/) {
        return true;
    }

    //TODO: Stubbed in method to check if entered credentials are valid
    /**
     * @param _username The username obtained from the login panel.
     * @param _password The password obtained from the login panel.
     * @return Boolean
     */
    private boolean isValidUser(String _username, String _password) {
        //Compare String objects to those in the database.
        //If credentials exist then return true.
        //If credentials do not exist then prompt user to reenter credentials.
        return true;
    }

    /**
     * This is a method returns a VBox object which is later used to build the
     * interface.
     *
     * @param _header The title set at the top of the VBox.
     * @param _insets The number of pixels away from the edges.
     */
    private VBox addVBox(String _header, int _insets, Pos value) {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);
        vbox.setAlignment(value);
        Text title = new Text(_header);
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        vbox.getChildren().add(title);
        return vbox;
    }

    private void displayExitAlert(String _title, String _header,
            String _message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(_title);
        alert.setHeaderText(_header);
        alert.setContentText(_message);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(BoffoStage);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            loadMainPanel();
            //BoffoEvent evtExit = new BoffoEvent(this, Routing.EXIT_PANEL);
            //fireEvent(evtExit);
        } else {
            alert.close();
        }
    }
}
