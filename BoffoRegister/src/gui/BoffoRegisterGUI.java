package gui;

/**
 * Boffo Register GUI
 *
 * @purpose This class contains the Graphical User Interface (GUI) for the
 * BoffoRegister application. The GUI consists of a login panel to start and
 * then after logging in the user is presented with the main panel. The main
 * panel consists of Transaction, Inventory, and Administration. This class is
 * organized as defined by the style guide handout.
 *
 * @author Logan Stanfield and Kevin Keomalaythong.
 * @updated 2017-05-01
 */

import events.*;
import events.BoffoNavigateEventData.EventType;
import static events.BoffoNavigateEventData.EventType.*;
import inventory.Inventory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public final class BoffoRegisterGUI extends BoffoFireObject implements BoffoListenerInterface {

    // List of items added in the table.
    private final Stage boffoStage;

    //Window properties.
    private final int screenWidth = 800;
    private final int screenHeight = 600;
    private final boolean isResizable = false;

    //Dialog size properties.
    private final int dialogWidth = 400;
    private final int dialogHeight = 150;
    private final int addEntryDialogWidth = 400;
    private final int addEntryDialogHeight = 250;

    //Filepaths for images used for various buttons.
    private final String boffoLogoFilepath = "res/boffo_logo/boffo_logo.png";
    private final String administrationBtnNormalImagePath = "res/administration/button_main.png";
    private final String administrationBtnHoverImagePath = "res/administration/button_hover.png";
    private final String administrationBtnPressedImagePath = "res/administration/button_click.png";
    private final String transactionBtnNormalImagePath = "res/transaction/button_main.png";
    private final String transactionBtnHoverImagePath = "res/transaction/button_hover.png";
    private final String transactionBtnPressedImagePath = "res/transaction/button_click.png";
    private final String inventoryBtnNormalImagePath = "res/inventory/button_main.png";
    private final String inventoryBtnHoverImagePath = "res/inventory/button_hover.png";
    private final String inventoryBtnPressedImagePath = "res/inventory/button_click.png";
    private final String logoutBtnNormalImagePath = "res/logout/button_main.png";
    private final String logoutBtnHoverImagePath = "res/logout/button_hover.png";
    private final String logoutBtnPressedImagePath = "res/logout/button_click.png";
    private final String signInBtnNormalImagePath = "res/signin/button_main.png";
    private final String signInBtnHoverImagePath = "res/signin/button_hover.png";
    private final String signSinBtnPressedImagePath = "res/signin/button_click.png";

    //Styling for buttons properties.
    private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 5, 5, 5, 5;";
    private final String STYLE_PRESSED = "-fx-background-color: transparent; -fx-padding: 6 4 4 6;";

    /**
     * This constructor sets up the stage and loads the login panel.
     *
     * @param _stage
     */
    public BoffoRegisterGUI(Stage _stage) {
        this.boffoStage = _stage;
        this.boffoStage.setResizable(this.isResizable);
        this.loadLoginPanel();
    }


    /**
     * This method loads the admin panel to the stage and is built by calling
     * the buildAdminPanel() method.
     */
    public void loadAdminPanel() {
        System.out.println("Loading Administration Panel");
        this.boffoStage.setTitle("Administration");
        Scene sceneAdmin = this.buildAdminPanel();
        this.boffoStage.setScene(sceneAdmin);
        this.boffoStage.show();
    }


    /**
     * This method loads the inventory panel to the stage and is built by
     * calling the buildInventoryPanel() method.
     */
    public void loadInventoryPanel() {
        System.out.println("Loading Inventory Panel");
        this.boffoStage.setTitle("Inventory");
        Scene sceneInventory = this.buildInventoryPanel();
        this.boffoStage.setScene(sceneInventory);
        this.boffoStage.show();
    }


    /**
     * This method loads the login panel to the stage and is built by calling
     * the buildLoginPanel() method.
     */
    public void loadLoginPanel() {
        System.out.println("Loading Login Panel");
        this.boffoStage.setTitle("BoffoRegister Login");
        Scene sceneLogin = this.buildLoginPanel();
        this.boffoStage.setScene(sceneLogin);

        //Set up the login stage.
        this.boffoStage.show();
    }


    /**
     * This method loads the main panel to the stage and is built by calling the
     * buildMainPanel() method.
     */
    public void loadMainPanel() {
        System.out.println("Loading Main Panel");
        this.boffoStage.setTitle("Boffo Register Main Menu");
        Scene sceneMain = this.buildMainPanel();

        //Set up the main stage.
        this.boffoStage.setScene(sceneMain);
        this.boffoStage.show();
    }


    /**
     * This method loads the transaction panel to the stage and is built by
     * calling the buildTransactionPanel() method.
     */
    public void loadTransactionPanel() {
        System.out.println("Loading Transaction Panel");
        this.boffoStage.setTitle("Transaction");
        Scene sceneTransaction = this.buildTransactionPanel();
        this.boffoStage.setScene(sceneTransaction);
        boffoStage.show();
    }


    /**
     * This is the defined messageReceieved in the BoffoListenerInterface.
     *
     * @param _event
     */
    @Override
    public void messageReceived(BoffoEvent _event) {
        fireEvent(_event);
    }


    /**
     * This navigatorEvent method is used for the events that still don't fully
     * work. :(
     *
     * @param _et
     */
    public void navigatorEvent(EventType _et) {
        BoffoNavigateEventData evtData = new BoffoNavigateEventData(_et);
        BoffoEvent evt = new BoffoEvent(this, (BoffoEventData) evtData);
        fireEvent(evt);
    }


    /**
     * This method returns a button with an image as the button.
     *
     * @param _imageFilePath
     * @return btn
     */
    private Button addButtonWithBackground(String _imageFilePath) {
        Button btn = new Button();

        //Operations menu configuarations.
        try {
            btn.setGraphic(new ImageView(new Image(new FileInputStream(_imageFilePath))));
            btn.setStyle(STYLE_NORMAL);

        } catch (Exception _e) {
            System.out.println("Error loading image");
        }

        return btn;
    }


    /**
     *
     * @param _columnHeaders
     * @param _minTableHeight
     * @param _minTableWidth
     * @param _minHeaderWidth
     * @return
     */
    private TableView addTableView(String[] _columnHeaders,
            int _minTableHeight, int _minTableWidth, int _minHeaderWidth) {

        final TableView tableView = new TableView();
        tableView.setMinHeight(_minTableHeight);
        tableView.setMinWidth(_minTableWidth);
        tableView.setEditable(true);

        TableColumn tempColumn = null;
        for (String _columnHeader : _columnHeaders) {
            tempColumn = new TableColumn(_columnHeader);
            tableView.getColumns().add(tempColumn);
            tempColumn.setMinWidth(_minHeaderWidth);
        }
        return tableView;
    }


    /**
     * This is a method returns a VBox object which is later used to build the
     * interface.
     *
     * @param _header The title set at the top of the VBox.
     * @param _insets The number of pixels away from the edges.
     */
    private VBox addVBox(String _header, int _insets, Pos _value) {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(_insets));
        vbox.setSpacing(8);
        vbox.setAlignment(_value);
        Text title = new Text(_header);
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        vbox.getChildren().add(title);
        return vbox;
    }


    /**
     * This method adds a warning label which we be used in several similar
     * windows.
     *
     * @param text
     * @param fontSize
     * @param color
     * @return warningLabel
     */
    private Label addWarningLabel(String _text, int _fontSize, Color _color) {
        final Label warningLabel = new Label(_text);
        warningLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, _fontSize));
        warningLabel.setTextFill(_color);
        warningLabel.setVisible(false);

        return warningLabel;
    }


    /**
     * This method builds the entire Administration panel. The root of the stage
     * is a StackPane and has several layers added on top of it.
     *
     * @return new Scene
     */
    private Scene buildAdminPanel() {
        StackPane root = new StackPane();

        //Button settings and configurations.
        Button btnExit = new Button("Exit");
        Button btnSetPhoneNumber = new Button("Change Store Phone Number");
        Button btnSetReceiptMsg = new Button("Change Receipt Message");
        Button btnSetStoreHrs = new Button("Change Store Hours");
        Button btnSetStoreId = new Button("Change Store ID");
        Button btnSetStoreName = new Button("Change Store Name");
        Button btnSetTaxRate = new Button("Change Tax Rate");

        //VBox settings and configurations.
        VBox adminBtnVbox = this.addVBox("Select Operation", 10, Pos.BASELINE_LEFT);
        adminBtnVbox.getChildren().addAll(btnSetPhoneNumber, btnSetStoreHrs,
                btnSetStoreId, btnSetReceiptMsg, btnSetStoreName,
                btnSetTaxRate, btnExit);

        //ImageView settigs and configurations.
        FileInputStream input = null;

        //Check to see if the file path for the images will successfully load.
        try {
            input = new FileInputStream(boffoLogoFilepath);
        } catch (FileNotFoundException _ex) {
            Logger.getLogger(BoffoRegisterGUI.class.getName()).log(Level.SEVERE, null, _ex);
        }
        //Load the image into the imageView
        ImageView imageView = new ImageView(new Image(input));
        VBox imageVbox = this.addVBox("", 15, Pos.BOTTOM_RIGHT);
        imageVbox.getChildren().add(imageView);

        root.getChildren().addAll(imageVbox, adminBtnVbox);

        //Button settings below.
        btnExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
                loadMainPanel();
//                BoffoEvent evtExit = new BoffoEvent(this, Routing.EXIT_PANEL);
//                fireEvent(evtExit);
            }
        });

        btnSetPhoneNumber.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(boffoStage);

                //Creates a VBox to hold the TextFields and buttons.
                VBox removeEntryVbox = new VBox(10);
                removeEntryVbox.setPadding(new Insets(10));

                //Creates title for vbox.
                Text title = new Text("Change Store Phone Number");
                title.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));

                //TextField setting and configurations.
                final TextField skuTextField = new TextField();
                skuTextField.setPromptText("Enter Store Phone Number");

                //Button settings and configurations.
                HBox buttonsHbox = new HBox(10);
                Button btnRemoveItem = new Button("Save");
                Button btnCancel = new Button("Cancel");
                //Label settings and configurations.
                final Label warningLabel = addWarningLabel("*All fields must have valid data*",
                        8, Color.RED);
                buttonsHbox.getChildren().addAll(btnRemoveItem, btnCancel, warningLabel);

                removeEntryVbox.getChildren().addAll(title, skuTextField,
                        buttonsHbox);

                Scene dialogScene = new Scene(removeEntryVbox, dialogWidth, dialogHeight);
                dialog.setScene(dialogScene);
                dialog.show();

                btnRemoveItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent _e) {
                        String itemToRemove = skuTextField.getText();
                        if (skuTextField.getText().trim().equals("")) {
                            warningLabel.setVisible(true);

                        } else {
                            warningLabel.setVisible(false);
                            dialog.close();
                            //TODO: Implement adding item to inventory.
                        }
                        //TODO: Implement removal of an entry when events are available.
                    }
                });

                btnCancel.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent _e) {
                        dialog.close();
                    }
                });
            }
        });

        btnSetReceiptMsg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(boffoStage);

                //Creates a VBox to hold the TextFields and buttons.
                VBox removeEntryVbox = new VBox(10);
                removeEntryVbox.setPadding(new Insets(10));

                //Creates title for vbox.
                Text title = new Text("Change Receipt Message");
                title.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));

                //TextField setting and configurations.
                final TextField skuTextField = new TextField();
                skuTextField.setPromptText("Enter Receipt Message");

                //Button settings and configurations.
                HBox buttonsHbox = new HBox(10);
                Button btnRemoveItem = new Button("Save");
                Button btnCancel = new Button("Cancel");
                //Label settings and configurations.
                final Label warningLabel = addWarningLabel("*All fields must have valid data*",
                        8, Color.RED);
                buttonsHbox.getChildren().addAll(btnRemoveItem, btnCancel, warningLabel);

                removeEntryVbox.getChildren().addAll(title, skuTextField,
                        buttonsHbox);

                Scene dialogScene = new Scene(removeEntryVbox, dialogWidth, dialogHeight);
                dialog.setScene(dialogScene);
                dialog.show();

                btnRemoveItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent _e) {
                        String itemToRemove = skuTextField.getText();
                        if (skuTextField.getText().trim().equals("")) {
                            warningLabel.setVisible(true);

                        } else {
                            warningLabel.setVisible(false);
                            dialog.close();
                            //TODO: Implement adding item to inventory.
                        }
                        //TODO: Implement removal of an entry when events are available.
                    }
                });

                btnCancel.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent _e) {
                        dialog.close();
                    }
                });
            }
        });

        btnSetStoreHrs.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(boffoStage);

                //Creates a VBox to hold the TextFields and buttons.
                VBox removeEntryVbox = new VBox(10);
                removeEntryVbox.setPadding(new Insets(10));

                //Creates title for vbox.
                Text title = new Text("Change Store Hours");
                title.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));

                //TextField setting and configurations.
                final TextField skuTextField = new TextField();
                skuTextField.setPromptText("Enter Store Hours");

                //Button settings and configurations.
                HBox buttonsHbox = new HBox(10);
                Button btnRemoveItem = new Button("Save");
                Button btnCancel = new Button("Cancel");
                //Label settings and configurations.
                final Label warningLabel = addWarningLabel("*All fields must have valid data*",
                        8, Color.RED);
                buttonsHbox.getChildren().addAll(btnRemoveItem, btnCancel, warningLabel);

                removeEntryVbox.getChildren().addAll(title, skuTextField,
                        buttonsHbox);

                Scene dialogScene = new Scene(removeEntryVbox, dialogWidth, dialogHeight);
                dialog.setScene(dialogScene);
                dialog.show();

                btnRemoveItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent _e) {
                        String itemToRemove = skuTextField.getText();
                        if (skuTextField.getText().trim().equals("")) {
                            warningLabel.setVisible(true);

                        } else {
                            warningLabel.setVisible(false);
                            dialog.close();
                            //TODO: Implement adding item to inventory.
                        }
                        //TODO: Implement removal of an entry when events are available.
                    }
                });

                btnCancel.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent _e) {
                        dialog.close();
                    }
                });
            }
        });

        btnSetStoreId.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(boffoStage);

                //Creates a VBox to hold the TextFields and buttons.
                VBox removeEntryVbox = new VBox(10);
                removeEntryVbox.setPadding(new Insets(10));

                //Creates title for vbox.
                Text title = new Text("Change Store ID");
                title.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));

                //TextField setting and configurations.
                final TextField skuTextField = new TextField();
                skuTextField.setPromptText("Enter Store ID");

                //Button settings and configurations.
                HBox buttonsHbox = new HBox(10);
                Button btnRemoveItem = new Button("Save");
                Button btnCancel = new Button("Cancel");
                //Label settings and configurations.
                final Label warningLabel = addWarningLabel("*All fields must have valid data*",
                        8, Color.RED);
                buttonsHbox.getChildren().addAll(btnRemoveItem, btnCancel, warningLabel);

                removeEntryVbox.getChildren().addAll(title, skuTextField,
                        buttonsHbox);

                Scene dialogScene = new Scene(removeEntryVbox, dialogWidth, dialogHeight);
                dialog.setScene(dialogScene);
                dialog.show();

                btnRemoveItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent _e) {
                        String itemToRemove = skuTextField.getText();
                        if (skuTextField.getText().trim().equals("")) {
                            warningLabel.setVisible(true);

                        } else {
                            warningLabel.setVisible(false);
                            dialog.close();
                            //TODO: Implement adding item to inventory.
                        }
                        //TODO: Implement removal of an entry when events are available.
                    }
                });

                //Cancel button properties.
                btnCancel.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent _e) {
                        dialog.close();
                    }
                });
            }
        });

        btnSetStoreName.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(boffoStage);

                //Creates a VBox to hold the TextFields and buttons.
                VBox removeEntryVbox = new VBox(10);
                removeEntryVbox.setPadding(new Insets(10));

                //Creates title for vbox.
                Text title = new Text("Change Store Name");
                title.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));

                //TextField setting and configurations.
                final TextField skuTextField = new TextField();
                skuTextField.setPromptText("Enter Store Name");

                //Button settings and configurations.
                HBox buttonsHbox = new HBox(10);
                Button btnRemoveItem = new Button("Save");
                Button btnCancel = new Button("Cancel");
                //Label settings and configurations.
                final Label warningLabel = addWarningLabel("*All fields must have valid data*",
                        8, Color.RED);
                buttonsHbox.getChildren().addAll(btnRemoveItem, btnCancel, warningLabel);

                removeEntryVbox.getChildren().addAll(title, skuTextField,
                        buttonsHbox);

                Scene dialogScene = new Scene(removeEntryVbox, dialogWidth, dialogHeight);
                dialog.setScene(dialogScene);
                dialog.show();

                btnRemoveItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent _e) {
                        String itemToRemove = skuTextField.getText();
                        if (skuTextField.getText().trim().equals("")) {
                            warningLabel.setVisible(true);

                        } else {
                            warningLabel.setVisible(false);
                            dialog.close();
                            //TODO: Implement adding item to inventory.
                        }
                        //TODO: Implement removal of an entry when events are available.
                    }
                });

                btnCancel.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent _e) {
                        dialog.close();
                    }
                });
            }
        });

        btnSetTaxRate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(boffoStage);

                //Creates a VBox to hold the TextFields and buttons.
                VBox removeEntryVbox = new VBox(10);
                removeEntryVbox.setPadding(new Insets(10));

                //Creates title for vbox.
                Text title = new Text("Change Tax Rate (Decimal Form)");
                title.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));

                //TextField setting and configurations.
                final TextField skuTextField = new TextField();
                skuTextField.setPromptText("Enter Tax Rate");

                //Button settings and configurations.
                HBox buttonsHbox = new HBox(10);
                Button btnRemoveItem = new Button("Save");
                Button btnCancel = new Button("Cancel");
                //Label settings and configurations.
                final Label warningLabel = addWarningLabel("*All fields must have valid data*",
                        8, Color.RED);
                buttonsHbox.getChildren().addAll(btnRemoveItem, btnCancel, warningLabel);

                removeEntryVbox.getChildren().addAll(title, skuTextField,
                        buttonsHbox);

                Scene dialogScene = new Scene(removeEntryVbox, dialogWidth, dialogHeight);
                dialog.setScene(dialogScene);
                dialog.show();

                btnRemoveItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent _e) {
                        String itemToRemove = skuTextField.getText();
                        if (skuTextField.getText().trim().equals("")) {
                            warningLabel.setVisible(true);

                        } else {
                            warningLabel.setVisible(false);
                            dialog.close();
                            //TODO: Implement adding item to inventory.
                        }
                        //TODO: Implement removal of an entry when events are available.
                    }
                });

                btnCancel.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent _e) {
                        dialog.close();
                    }
                });
            }
        });

        //Set up the Admin panel.
        return new Scene(root, this.screenWidth, this.screenHeight);
    }


    /**
     * This method builds the entire Inventory panel. The root pane of the stage
     * is a SplitPane and has several layers added on top of it.
     *
     * @return Scene
     */
    private Scene buildInventoryPanel() {
        SplitPane inventoryPanel = new SplitPane();
        inventoryPanel.setDividerPositions(.25);
        final ObservableList<String> itemList = FXCollections.observableArrayList();

        //Initialize the inventory. TODO: Remove after events are available.
        final Inventory inventory = new Inventory();

        //Inventory Operations settings and configurations.
        Button btnAddEntry = new Button("Add Entry");
        Button btnExit = new Button("Exit");
        Button btnLoadInventory = new Button("Load Inventory");
        Button btnRemoveEntry = new Button("Remove Entry");
        Button btnSave = new Button("Save");
        Button btnSearchByName = new Button("Search Item by Name");
        Button btnSearchBySku = new Button("Search Item by SKU");
        Button btnSearchByUuid = new Button("Search Item by UUID");
        Button btnSearchByPrice = new Button("Search Item by Price");

        VBox inventoryOptions = this.addVBox("Select Operation", 10, Pos.BASELINE_LEFT);
        inventoryOptions.getChildren().addAll(btnLoadInventory, btnAddEntry,
                btnRemoveEntry, btnSearchByName, btnSearchBySku, btnSearchByUuid,
                btnSearchByPrice, btnSave, btnExit);

        //TableView configurations and settings.
        String[] tableHeaders = {"Item Name", "SKU", "UUID", "Price"};
        final TableView inventoryTbl = addTableView(tableHeaders, 400, 250, 140);

        // Add the item list to the table.
        inventoryTbl.setItems(itemList);

        VBox inventoryTblVbox = new VBox(inventoryTbl);
        inventoryTblVbox.setAlignment(Pos.TOP_LEFT);
        inventoryTblVbox.setPadding(new Insets(10));

        inventoryPanel.getItems().addAll(inventoryOptions, inventoryTblVbox);

        btnAddEntry.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(boffoStage);

                //Creates a VBox to hold the TextFields and buttons.
                VBox addEntryVbox = new VBox(10);
                addEntryVbox.setPadding(new Insets(10));

                //Creates title for vbox.
                Text title = new Text("Add Entry");
                title.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));

                //Name, SKU, UUID, and Price TextField properties below.
                final TextField nameTextField = new TextField();
                nameTextField.setPromptText("Enter Item Name");

                final TextField skuTextField = new TextField();
                skuTextField.setPromptText("Enter Item SKU");

                final TextField uuidTextField = new TextField();
                uuidTextField.setPromptText("Enter Itme UUID");

                final TextField priceTextField = new TextField();
                priceTextField.setPromptText("Enter Item Price");
                addEntryVbox.getChildren().addAll(title, nameTextField, skuTextField,
                        uuidTextField, priceTextField);

                //Cancel and add buttons.
                Button btnAddItem = new Button("Add item");
                Button btnCancel = new Button("Cancel");
                //Label settings and configurations.
                final Label warningLabel = addWarningLabel("*All fields must contain valid data*",
                        10, Color.RED);

                final HBox buttonsHbox = new HBox(10);
                buttonsHbox.getChildren().addAll(btnAddItem, btnCancel, warningLabel);
                addEntryVbox.getChildren().add(buttonsHbox);

                Scene dialogScene = new Scene(addEntryVbox, addEntryDialogWidth, addEntryDialogHeight);
                dialog.setScene(dialogScene);
                dialog.show();

                btnAddItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent _event) {
                        String itemName = nameTextField.getText();
                        String skuValue = skuTextField.getText();
                        String uuidValue = uuidTextField.getText();
                        String price = priceTextField.getText();

                        if (nameTextField.getText().trim().equals("")
                                || skuTextField.getText().trim().equals("")
                                || uuidTextField.getText().trim().equals("")
                                || priceTextField.getText().trim().equals("")) {
                            warningLabel.setVisible(true);

                        } else {
                            warningLabel.setVisible(false);
                            dialog.close();
                            //TODO: Implement adding item to inventory.
                        }
                    }
                });

                btnCancel.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent _e) {
                        dialog.close();
                    }
                });

//                BoffoEvent evtExit = new BoffoEvent(this, Routing.EXIT_PANEL);
//                fireEvent(evtExit);
            }
        });

        btnExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
                loadMainPanel();
//                BoffoEvent evtExit = new BoffoEvent(this, Routing.EXIT_PANEL);
//                fireEvent(evtExit);
            }
        });

        btnLoadInventory.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {

//                BoffoEvent evtExit = new BoffoEvent(this, Routing.EXIT_PANEL);
//                fireEvent(evtExit);
            }
        });

        btnRemoveEntry.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(boffoStage);

                //Creates a VBox to hold the TextFields and buttons.
                VBox removeEntryVbox = new VBox(10);
                removeEntryVbox.setPadding(new Insets(10));

                //Creates title for vbox.
                Text title = new Text("Remove Entry");
                title.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));

                //TextField setting and configurations.
                final TextField skuTextField = new TextField();
                skuTextField.setPromptText("Enter Item SKU");

                //Button settings and configurations.
                HBox buttonsHbox = new HBox(10);
                Button btnRemoveItem = new Button("Remove Item");
                Button btnCancel = new Button("Cancel");
                final Label warningLabel = addWarningLabel("*All fields must contain valid data*",
                        10, Color.RED);
                buttonsHbox.getChildren().addAll(btnRemoveItem, btnCancel, warningLabel);

                removeEntryVbox.getChildren().addAll(title, skuTextField,
                        buttonsHbox);

                Scene dialogScene = new Scene(removeEntryVbox, dialogWidth, dialogHeight);
                dialog.setScene(dialogScene);
                dialog.show();

                btnRemoveItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent _e) {
                        String itemToRemove = skuTextField.getText();
                        if (skuTextField.getText().trim().equals("")) {
                            warningLabel.setVisible(true);

                        } else {
                            warningLabel.setVisible(false);
                            dialog.close();
                            //TODO: Implement removing item to inventory.
                        }
                        //TODO: Implement removal of an entry when events are available.
                    }
                });

                btnCancel.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent _e) {
                        dialog.close();
                    }
                });
            }
        });

        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {

                //TODO: Implement updating the inventory.
//                BoffoEvent evtExit = new BoffoEvent(this, Routing.EXIT_PANEL);
//                fireEvent(evtExit);
            }
        });

        btnSearchByName.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(boffoStage);

                //Creates a VBox to hold the TextFields and buttons.
                VBox searchEntryVbox = new VBox(10);
                searchEntryVbox.setPadding(new Insets(10));

                //Creates title for vbox.
                Text title = new Text("Search for Item by Name");
                title.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));

                //TextField setting and configurations.
                final TextField nameTextField = new TextField();
                nameTextField.setPromptText("Enter Item Name");

                //Button settings and configurations.
                HBox buttonsHbox = new HBox(10);
                Button btnSearchItem = new Button("Search");
                Button btnCancel = new Button("Cancel");
                //Label configurations and settings.
                final Label warningLabel = addWarningLabel("*All fields must contain valid data*",
                        10, Color.RED);
                buttonsHbox.getChildren().addAll(btnSearchItem, btnCancel, warningLabel);

                searchEntryVbox.getChildren().addAll(title, nameTextField,
                        buttonsHbox);

                Scene dialogScene = new Scene(searchEntryVbox, dialogWidth, dialogHeight);
                dialog.setScene(dialogScene);
                dialog.show();

                btnSearchItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent _e) {
                        String itemToSearch = nameTextField.getText();
//                        ArrayList items = new ArrayList();
//                        items = inventory.searchInventoryBySku(nameTextField.getText());
                        if (nameTextField.getText().trim().equals("")) {
                            warningLabel.setVisible(true);
                        } else {
                            warningLabel.setVisible(false);
                            dialog.close();
                        }

                        //Clear data on the TableView with search results.
                        clearTableView(inventoryTbl);
                        //TODO: Implement search of an entry when events are available.
                    }
                });

                btnCancel.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent _e) {
                        dialog.close();
                    }
                });
//                BoffoEvent evtExit = new BoffoEvent(this, Routing.EXIT_PANEL);
//                fireEvent(evtExit);
            }
        });

        btnSearchBySku.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(boffoStage);

                //Creates a VBox to hold the TextFields and buttons.
                VBox searchEntryVbox = new VBox(10);
                searchEntryVbox.setPadding(new Insets(10));

                //Creates title for vbox.
                Text title = new Text("Search for Item by SKU");
                title.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));

                //TextField setting and configurations.
                final TextField skuTextField = new TextField();
                skuTextField.setPromptText("Enter Item SKU");

                //Button settings and configurations.
                HBox buttonsHbox = new HBox(10);
                Button btnSearchItem = new Button("Search");
                Button btnCancel = new Button("Cancel");
                //Label settings and configurations.
                final Label warningLabel = addWarningLabel("*All fields must contain valid data*",
                        10, Color.RED);
                buttonsHbox.getChildren().addAll(btnSearchItem, btnCancel, warningLabel);

                searchEntryVbox.getChildren().addAll(title, skuTextField,
                        buttonsHbox);

                Scene dialogScene = new Scene(searchEntryVbox, dialogWidth, dialogHeight);
                dialog.setScene(dialogScene);
                dialog.show();

                btnSearchItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent _e) {
                        String itemToSearch = skuTextField.getText();
                        ArrayList items = new ArrayList();
                        //items = inventory.searchInventoryByName(skuTextField.getText());

                        if (skuTextField.getText().trim().equals("")) {
                            warningLabel.setVisible(true);
                        } else {
                            warningLabel.setVisible(false);
                            dialog.close();
                        }

                        //Clear data on the TableView with search results.
                        clearTableView(inventoryTbl);
                        //TODO: Implement search of an entry when events are available.
                    }
                });

                btnCancel.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent _e) {
                        dialog.close();
                    }
                });
//                BoffoEvent evtExit = new BoffoEvent(this, Routing.EXIT_PANEL);
//                fireEvent(evtExit);
            }
        });

        btnSearchByUuid.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(boffoStage);

                //Creates a VBox to hold the TextFields and buttons.
                VBox searchEntryVbox = new VBox(10);
                searchEntryVbox.setPadding(new Insets(10));

                //Creates title for vbox.
                Text title = new Text("Search for Item by UUID");
                title.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));

                //TextField setting and configurations.
                final TextField uuidTextField = new TextField();
                uuidTextField.setPromptText("Enter Item UUID");

                //Button settings and configurations.
                HBox buttonsHbox = new HBox(10);
                Button btnSearchItem = new Button("Search");
                Button btnCancel = new Button("Cancel");
                //Label settings and configurations.
                final Label warningLabel = addWarningLabel("*All fields must contain valid data*",
                        10, Color.RED);
                buttonsHbox.getChildren().addAll(btnSearchItem, btnCancel, warningLabel);

                searchEntryVbox.getChildren().addAll(title, uuidTextField,
                        buttonsHbox);

                Scene dialogScene = new Scene(searchEntryVbox, dialogWidth, dialogHeight);
                dialog.setScene(dialogScene);
                dialog.show();

                btnSearchItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent _e) {

                        if (uuidTextField.getText().trim().equals("")) {
                            warningLabel.setVisible(true);
                        } else {
                            warningLabel.setVisible(false);
                            dialog.close();
                        }

                        //Clear data on the TableView with search results.
                        clearTableView(inventoryTbl);
                        //TODO: Implement search of an entry when events are available.
                    }
                });

                btnCancel.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent _e) {
                        dialog.close();
                    }
                });
//                BoffoEvent evtExit = new BoffoEvent(this, Routing.EXIT_PANEL);
//                fireEvent(evtExit);
            }
        });

        btnSearchByPrice.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(boffoStage);

                //Creates a VBox to hold the TextFields and buttons.
                VBox searchEntryVbox = new VBox(10);
                searchEntryVbox.setPadding(new Insets(10));

                //Creates title for vbox.
                Text title = new Text("Search for Item by Price");
                title.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));

                //TextField setting and configurations.
                final TextField priceTextField = new TextField();
                priceTextField.setPromptText("Enter Item Price");

                //Button settings and configurations.
                HBox buttonsHbox = new HBox(10);
                Button btnSearchItem = new Button("Search");
                Button btnCancel = new Button("Cancel");
                //Label settings and configurations.
                final Label warningLabel = addWarningLabel("*All fields must contain valid data*",
                        10, Color.RED);
                buttonsHbox.getChildren().addAll(btnSearchItem, btnCancel, warningLabel);

                searchEntryVbox.getChildren().addAll(title, priceTextField,
                        buttonsHbox);

                Scene dialogScene = new Scene(searchEntryVbox, 350, 150);
                dialog.setScene(dialogScene);
                dialog.show();

                btnSearchItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent _e) {
                        String itemToSearch = priceTextField.getText();
                        ArrayList items = new ArrayList();
                        //items = inventory.searchInventoryByName(priceTextField.getText());

                        if (priceTextField.getText().trim().equals("")) {
                            warningLabel.setVisible(true);
                        } else {
                            warningLabel.setVisible(false);
                            dialog.close();
                        }

                        //Clear data on the TableView with search results.
                        clearTableView(inventoryTbl);
                        //TODO: Implement search of an entry when events are available.
                    }
                });

                btnCancel.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent _e) {
                        dialog.close();
                    }
                });
//                BoffoEvent evtExit = new BoffoEvent(this, Routing.EXIT_PANEL);
//                fireEvent(evtExit);
            }
        });

        //Set up the Inventory panel.
        return new Scene(inventoryPanel, this.screenWidth, this.screenHeight);
    }


    /**
     * This method builds the entire Login panel. The root pane of the stage is
     * a GridPane and has several layers added on top of it. The login panel also
     * has custom buttons that are images.
     *
     * @return Scene
     */
    private Scene buildLoginPanel() {
        StackPane root = new StackPane();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));

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

        final Button signInBtn = this.addButtonWithBackground(this.signInBtnNormalImagePath);
        HBox signInHB = new HBox(10);
        signInHB.setAlignment(Pos.TOP_RIGHT);
        signInHB.getChildren().add(signInBtn);
        grid.add(signInHB, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        //Image view properties.
        FileInputStream input = null;
        try {
            input = new FileInputStream(boffoLogoFilepath);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BoffoRegisterGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        ImageView imageView = new ImageView(new Image(input));
        HBox hbox = new HBox(imageView);
        hbox.setPadding(new Insets(15, 15, 15, 15));
        hbox.setAlignment(Pos.BOTTOM_RIGHT);

        root.getChildren().addAll(hbox, grid);

        //Fire an event upon pressing the Sign In button.
        signInBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent _e) {
                //TODO: Check credentials.

                /* BEGIN EVENTS CODE  */
//                BoffoEventData evtDataLogIn = new BoffoEventData();
//                evtDataLogIn.setEventData("LOG IN");
//                BoffoEvent evtLogIn = new BoffoEvent(this, evtDataLogIn);
//                messageReceived(evtLogIn);
                /* END EVENTS CODE  */
                loadMainPanel();
            }
        });

        signInBtn.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent _event) {
                if (_event.getCode().equals(KeyCode.ENTER)) {
                    /* BEGIN EVENTS CODE  */
//                    BoffoLogInEvent evtLogIn = new BoffoLogInEvent();
//                    fireEvent(evtLogIn);
                    /* END EVENTS CODE  */

                    // TODO: Check credentials.
                    loadMainPanel();
                }
            }
        });

        signInBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent _event) {
                try {
                    signInBtn.setGraphic(new ImageView(new Image(new FileInputStream(signSinBtnPressedImagePath))));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BoffoRegisterGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                signInBtn.setStyle(STYLE_NORMAL);
            }
        });

        signInBtn.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent _event) {
                try {
                    signInBtn.setGraphic(new ImageView(new Image(new FileInputStream(signInBtnHoverImagePath))));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BoffoRegisterGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                signInBtn.setStyle(STYLE_PRESSED);
            }
        });

        signInBtn.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent _event) {
                try {
                    signInBtn.setGraphic(new ImageView(new Image(new FileInputStream(signInBtnNormalImagePath))));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BoffoRegisterGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                signInBtn.setStyle(STYLE_PRESSED);
            }
        });

        signInBtn.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent _event) {
                try {
                    signInBtn.setGraphic(new ImageView(new Image(new FileInputStream(signInBtnNormalImagePath))));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BoffoRegisterGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                signInBtn.setStyle(STYLE_NORMAL);
            }
        });

        pwBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent _event) {
                if (_event.getCode().equals(KeyCode.ENTER)) {
                    /* BEGIN EVENTS CODE  */
//                    BoffoLogInEvent evtLogIn = new BoffoLogInEvent();
//                    fireEvent(evtLogIn);
                    /* END EVENTS CODE  */

                    // TODO: Check credentials.
                    loadMainPanel();
                }
            }
        });

        userTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent _event) {
                if (_event.getCode().equals(KeyCode.ENTER)) {
                    /* BEGIN EVENTS CODE  */
//                    BoffoLogInEvent evtLogIn = new BoffoLogInEvent();
//                    fireEvent(evtLogIn);
                    /* END EVENTS CODE  */

                    // TODO: Check credentials.
                    loadMainPanel();
                }
            }
        });

        return new Scene(root, this.screenWidth, this.screenHeight);
    }


    private Scene buildMainPanel() {
        StackPane root = new StackPane();
        final Button btnTransaction = this.addButtonWithBackground(transactionBtnNormalImagePath);
        final Button btnInventory = this.addButtonWithBackground(inventoryBtnNormalImagePath);
        final Button btnAdministration = this.addButtonWithBackground(administrationBtnNormalImagePath);
        final Button btnLogout = this.addButtonWithBackground(logoutBtnNormalImagePath);

        VBox vbox = this.addVBox("Select Operation", 10, Pos.CENTER);
        vbox.getChildren().addAll(btnTransaction, btnInventory, btnAdministration, btnLogout);

        //ImageView settigs and configurations.
        FileInputStream input = null;
        try {
            input = new FileInputStream(boffoLogoFilepath);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BoffoRegisterGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        ImageView imageView = new ImageView(new Image(input));
        VBox imageVbox = this.addVBox("", 15, Pos.BOTTOM_RIGHT);
        imageVbox.getChildren().add(imageView);

        root.getChildren().addAll(imageVbox, vbox);

        //Fire an event to go to the Transaction panel.
        btnTransaction.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
                navigatorEvent(TRANSACTION_PANEL);
                loadTransactionPanel();
            }
        });

        btnTransaction.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent _event) {
                try {
                    btnTransaction.setGraphic(new ImageView(new Image(new FileInputStream(transactionBtnPressedImagePath))));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BoffoRegisterGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                btnTransaction.setStyle(STYLE_PRESSED);
            }
        });

        btnTransaction.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent _event) {
                try {
                    btnTransaction.setGraphic(new ImageView(new Image(new FileInputStream(transactionBtnNormalImagePath))));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BoffoRegisterGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                btnTransaction.setStyle(STYLE_NORMAL);
            }
        });

        btnTransaction.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent _event) {
                try {
                    btnTransaction.setGraphic(new ImageView(new Image(new FileInputStream(transactionBtnHoverImagePath))));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BoffoRegisterGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                btnTransaction.setStyle(STYLE_PRESSED);
            }
        });

        btnTransaction.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent _event) {
                try {
                    btnTransaction.setGraphic(new ImageView(new Image(new FileInputStream(transactionBtnNormalImagePath))));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BoffoRegisterGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                btnTransaction.setStyle(STYLE_PRESSED);
            }
        });

        //Fire an event to go to the Inventory panel.
        btnInventory.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
                navigatorEvent(INVENTORY_PANEL);
                loadInventoryPanel();
            }
        });

        btnInventory.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent _event) {
                try {
                    btnInventory.setGraphic(new ImageView(new Image(new FileInputStream(inventoryBtnPressedImagePath))));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BoffoRegisterGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                btnInventory.setStyle(STYLE_PRESSED);
            }
        });

        btnInventory.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent _event) {
                try {
                    btnInventory.setGraphic(new ImageView(new Image(new FileInputStream(inventoryBtnHoverImagePath))));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BoffoRegisterGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                btnInventory.setStyle(STYLE_NORMAL);
            }
        });

        btnInventory.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent _event) {
                try {
                    btnInventory.setGraphic(new ImageView(new Image(new FileInputStream(inventoryBtnNormalImagePath))));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BoffoRegisterGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                btnInventory.setStyle(STYLE_NORMAL);
            }
        });

        //Fire an event to go to the Administration panel.
        btnAdministration.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
                navigatorEvent(INVENTORY_PANEL);
                loadAdminPanel();
            }
        });

        btnAdministration.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent _event) {
                try {
                    btnAdministration.setGraphic(new ImageView(new Image(new FileInputStream(administrationBtnPressedImagePath))));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BoffoRegisterGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                btnAdministration.setStyle(STYLE_PRESSED);
            }
        });

        btnAdministration.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent _event) {
                try {
                    btnAdministration.setGraphic(new ImageView(new Image(new FileInputStream(administrationBtnNormalImagePath))));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BoffoRegisterGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                btnAdministration.setStyle(STYLE_NORMAL);
            }
        });

        btnAdministration.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent _event) {
                try {
                    btnAdministration.setGraphic(new ImageView(new Image(new FileInputStream(administrationBtnHoverImagePath))));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BoffoRegisterGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                btnAdministration.setStyle(STYLE_NORMAL);
            }
        });

        btnAdministration.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent _event) {
                try {
                    btnAdministration.setGraphic(new ImageView(new Image(new FileInputStream(administrationBtnNormalImagePath))));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BoffoRegisterGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                btnAdministration.setStyle(STYLE_NORMAL);
            }
        });

        //Fire an event to sign out.
        btnLogout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
                navigatorEvent(EXIT_PANEL);
                loadLoginPanel();
            }
        });

        btnLogout.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent _event) {
                try {
                    btnLogout.setGraphic(new ImageView(new Image(new FileInputStream(logoutBtnPressedImagePath))));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BoffoRegisterGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                btnLogout.setStyle(STYLE_PRESSED);
            }
        });

        btnLogout.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent _event) {
                try {
                    btnLogout.setGraphic(new ImageView(new Image(new FileInputStream(logoutBtnHoverImagePath))));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BoffoRegisterGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                btnLogout.setStyle(STYLE_NORMAL);
            }
        });

        btnLogout.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent _event) {
                try {
                    btnLogout.setGraphic(new ImageView(new Image(new FileInputStream(logoutBtnNormalImagePath))));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BoffoRegisterGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                btnLogout.setStyle(STYLE_NORMAL);
            }
        });

        btnLogout.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent _event) {
                try {
                    btnLogout.setGraphic(new ImageView(new Image(new FileInputStream(logoutBtnNormalImagePath))));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BoffoRegisterGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                btnLogout.setStyle(STYLE_NORMAL);
            }
        });

        // Create the scene and return.
        return new Scene(root, this.screenWidth, this.screenHeight);
    }


    /*
     * TODO: Display ticket items in a TableView or list.
     *       Create event handlers for the buttons.
     */
    private Scene buildTransactionPanel() {
        //Split pane options.
        SplitPane transactionPanel = new SplitPane();
        transactionPanel.setDividerPosition(1, .5);
        final ObservableList<String> itemList = FXCollections.observableArrayList();

        //Transaction options settings and configurations.
        Button btnRemoveItem = new Button("Remove Selected Item");
        Button btnSubmit = new Button("Submit");
        Button btnExit = new Button("Cancel Transaction");

        //SKU/UPC TextField settings and configurations.
        VBox textFieldVbox = this.addVBox("Enter SKU/UPC", 10, Pos.BOTTOM_LEFT);
        textFieldVbox.setMinHeight(400);
        final TextField skuInputField = new TextField();
        textFieldVbox.getChildren().addAll(skuInputField, btnSubmit);

        //Creates a new VBox for Transaction options.
        VBox transactionOptions = this.addVBox(
                "Select Operation", 10, Pos.BASELINE_LEFT);
        transactionOptions.getChildren().addAll(
                btnRemoveItem, btnExit, textFieldVbox);

        //TableView settings and configurations.
        String[] headers = {"Name", "SKU", "Price"};
        final TableView ticketTbl = addTableView(headers, 450, 200, 125);

        // Add the item list to the table.
        ticketTbl.setItems(itemList);

        VBox ticketTblOptions = new VBox(ticketTbl);
        ticketTblOptions.setAlignment(Pos.TOP_LEFT);
        ticketTblOptions.setPadding(new Insets(10));
        transactionPanel.getItems().addAll(
                transactionOptions, ticketTblOptions);

        // Button actions follow.
        btnExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
                displayExitAlert(
                        "Confirmation",
                        "You will lose any entered data.",
                        "Are you sure?"
                );
            }
        });

        btnRemoveItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
//
//                Item selectedItem
//                        = (Item) ticketTbl.getSelectionModel().getSelectedItem();
//                ticketTbl.getItems().remove(selectedItem);
            }
        });

        btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (skuInputField.getText().isEmpty() != true) {
//                    Item itemSKU = new Item(
//                            "SKU Item", skuInputField.getText(), 0.99);
//                    itemList.add(itemSKU);
                    skuInputField.clear();
                }
            }
        });

        btnRemoveItem.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
//                    Item selectedItem = (Item) ticketTbl.getSelectionModel().getSelectedItem();
//                    ticketTbl.getItems().remove(selectedItem);
                }
            }
        });

        btnSubmit.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)
                        && skuInputField.getText().isEmpty() != true) {
//                    Item itemSKU = new Item(
//                            "SKU Item", skuInputField.getText(), 0.99);
//                    itemList.add(itemSKU);
                    skuInputField.clear();
                }
            }
        });

        skuInputField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)
                        && skuInputField.getText().isEmpty() != true) {

                    //itemList.add();
                    skuInputField.clear();
                }
            }
        });

        return new Scene(transactionPanel, this.screenWidth, this.screenHeight);
    }


    /**
     * This method clears a TableView that is passed in as a parameter. This was
     * going to be used if the other modules were implemented fully.
     *
     * @param _tableView
     * @return _tableView
     */
    private TableView clearTableView(TableView _tableView) {
        if (_tableView.getItems().size() > 0) {
            for (int i = 0; i <= _tableView.getItems().size(); i++) {
                _tableView.getItems().clear();
            }
        }
        return _tableView;
    }


    /**
     * This method is used to display an alert box when called. If okay is
     * pressed then the GUI returns to the main panel.
     *
     * @param _title
     * @param _header
     * @param _message
     */
    private void displayExitAlert(String _title, String _header,
            String _message) {

        //Alert settings and configurations.
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(_title);
        alert.setHeaderText(_header);
        alert.setContentText(_message);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(boffoStage);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            loadMainPanel();
            //BoffoEvent evtExit = new BoffoEvent(this, Routing.EXIT_PANEL);
            //fireEvent(evtExit);
        } else {
            alert.close();
        }
    }


    /**
     * This method is used to determine if the user is logged in.
     *
     * @param user
     * @return true
     */
    private boolean isLoggedIn(/*User object*/) {
        return true;
    }


    /**
     * This method would have been used to check to see if the user is a valid
     * login and determines if the Administration button will be grayed out.
     *
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
}
