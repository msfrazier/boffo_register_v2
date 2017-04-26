package gui;

/**
 * Boffo Register GUI
 *
 * @purpose This class contains the Graphical User Interface (GUI) for the
 * BoffoRegister application.
 *
 * @status The GUI does not yet support event handling since the event module is
 * awaiting full implementation.
 *
 * @author Logan Stanfield and Kevin Keomalaythong
 * @updated 2017-04-26
 */

import events.*;
import inventory.Inventory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.cell.PropertyValueFactory;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public final class BoffoRegisterGUI extends BoffoFireObject implements BoffoListenerInterface {

    // Temporary class used for storing item attributes.
    // Needed in order to add items to the TableView.
    public static class Item {

        private SimpleStringProperty itemName;
        private SimpleStringProperty SKU;
        private double price;

        private Item(String _itemName, String _SKU, double _price) {
            this.itemName = new SimpleStringProperty(_itemName);
            this.SKU = new SimpleStringProperty(_SKU);
            this.price = _price;
        }

        public String getItemName() {
            return this.itemName.get();
        }

        public void setItemName(String _itemName) {
            this.itemName.set(_itemName);
        }

        public String getSKU() {
            return this.SKU.get();
        }

        public void setSKU(String _SKU) {
            this.SKU.set(_SKU);
        }

        public double getPrice() {
            return this.price;
        }

        public void setPrice(double _price) {
            this.price = _price;
        }
    }

    // List of items added in the table.
    private final Stage boffoStage;

    //Window size properties.
    private final int screenWidth = 800;
    private final int screenHeight = 600;

    //VARIABLES FOR TESTING PURPOSES ONLY//
    int counter = 0;
    //VARIABLES FOR TESTING PURPOSES ONLY//

    public BoffoRegisterGUI(Stage _stage) {
        this.boffoStage = _stage;
        this.loadLoginPanel();
    }

    public void loadAdminPanel() {
        System.out.println("Loading Administration Panel");
        boffoStage.setTitle("Administration");
        Scene sceneAdmin = buildAdminPanel();
        boffoStage.setScene(sceneAdmin);
        boffoStage.show();
    }

    public void loadInventoryPanel() {
        System.out.println("Loading Inventory Panel");
        boffoStage.setTitle("Inventory");
        Scene sceneInventory = buildInventoryPanel();
        boffoStage.setScene(sceneInventory);
        boffoStage.show();
    }

    public void loadLoginPanel() {
        System.out.println("Loading Login Panel");
        boffoStage.setTitle("BoffoRegister Login");
        Scene sceneLogin = this.buildLoginPanel();
        this.boffoStage.setScene(sceneLogin);

        //Set up the login stage.
        this.boffoStage.show();
    }

    public void loadMainPanel() {
        System.out.println("Loading Main Panel");
        boffoStage.setTitle("Boffo Register Main Menu");
        Scene sceneMain = this.buildMainPanel();

        //Set up the main stage.
        boffoStage.setScene(sceneMain);
        this.boffoStage.show();
    }

    public void loadTransactionPanel() {
        System.out.println("Loading Transaction Panel");
        boffoStage.setTitle("Transaction");
        Scene sceneTransaction = buildTransactionPanel();
        boffoStage.setScene(sceneTransaction);
        boffoStage.show();
    }

    //TODO: Add more buttons & associated events to the Administration panel.
    public Scene buildAdminPanel() {
        StackPane root = new StackPane();

        Button btnExit = new Button("Exit");
        Button btnSetPhoneNumber = new Button("Change Store Phone Number");
        Button btnSetReceiptMsg = new Button("Change Receipt Message");
        Button btnSetStoreHrs = new Button("Change Store Hours");
        Button btnSetStoreId = new Button("Change Store ID");
        Button btnSetStoreName = new Button("Change Store Name");
        Button btnSetTaxRate = new Button("Change Tax Rate");

        VBox adminBtnVbox = this.addVBox("Select Operation", 10, Pos.BASELINE_LEFT);
        adminBtnVbox.getChildren().addAll(btnSetPhoneNumber, btnSetStoreHrs,
                btnSetStoreId, btnSetReceiptMsg, btnSetStoreName,
                btnSetTaxRate, btnExit);

        //ImageView settigs and configurations.
        FileInputStream input = null;
        try {
            input = new FileInputStream("res/boffo_logo.png");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BoffoRegisterGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        ImageView imageView = new ImageView(new Image(input));
        VBox imageVbox = this.addVBox("", 15, Pos.BOTTOM_RIGHT);
        imageVbox.getChildren().add(imageView);

        root.getChildren().addAll(imageVbox, adminBtnVbox);

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
        return new Scene(root, screenWidth, screenHeight);
    }

    public Scene buildInventoryPanel() {
        SplitPane inventoryPanel = new SplitPane();
        inventoryPanel.setDividerPositions(.25);
        final ObservableList<Item> itemList = FXCollections.observableArrayList();

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
        final TableView inventoryTbl = new TableView();
        inventoryTbl.setMinHeight(400);
        inventoryTbl.setMinWidth(250);

        inventoryTbl.setEditable(true);

        // Establish the columns and associate them with Item attributes.
        final TableColumn nameCol = new TableColumn("Name");
        nameCol.setMinWidth(125);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<Item, String>("itemName"));

        final TableColumn skuCol = new TableColumn("SKU");
        skuCol.setMinWidth(155);
        skuCol.setCellValueFactory(
                new PropertyValueFactory<Item, String>("SKU"));

        final TableColumn uuidCol = new TableColumn("UUID");
        uuidCol.setMinWidth(155);
        uuidCol.setCellFactory(
                new PropertyValueFactory<Item, String>("UUID"));

        final TableColumn priceCol = new TableColumn("Price");
        priceCol.setMinWidth(125);
        priceCol.setCellValueFactory(
                new PropertyValueFactory<Item, String>("price"));

        // Add the item list to the table.
        inventoryTbl.setItems(itemList);
        inventoryTbl.getColumns().addAll(nameCol, skuCol, uuidCol, priceCol);

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

                final HBox buttonsHbox = new HBox(10);
                buttonsHbox.getChildren().addAll(btnAddItem, btnCancel);
                addEntryVbox.getChildren().add(buttonsHbox);

                //Label settings and configurations.
                //This is used as a warning message for not having the 
                final Label warningLabel = new Label("All fields must be valid");
                warningLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 8));
                warningLabel.setTextFill(Color.RED);
                buttonsHbox.getChildren().add(warningLabel);
                warningLabel.setVisible(false);

                Scene dialogScene = new Scene(addEntryVbox, 325, 250);
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
                String sku = "";

                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(boffoStage);

                //Creates a VBox to hold the TextFields and buttons.
                VBox removeEntryVbox = new VBox(10);
                removeEntryVbox.setPadding(new Insets(10));

                //Creates title for vbox.
                Text title = new Text("Add Entry");
                title.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));

                //TextField setting and configurations.
                final TextField skuTextField = new TextField();
                skuTextField.setPromptText("Enter Item SKU");

                //Button settings and configurations.
                HBox buttonsHbox = new HBox(10);
                Button btnRemoveItem = new Button("Remove Item");
                Button btnCancel = new Button("Cancel");
                buttonsHbox.getChildren().addAll(btnRemoveItem, btnCancel);

                //Label settings and configurations.
                final Label warningLabel = new Label("All fields must be valid");
                warningLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 8));
                warningLabel.setTextFill(Color.RED);
                buttonsHbox.getChildren().add(warningLabel);
                warningLabel.setVisible(false);

                removeEntryVbox.getChildren().addAll(title, skuTextField,
                        buttonsHbox);

                Scene dialogScene = new Scene(removeEntryVbox, 325, 150);
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
                Button btnSearchItem = new Button("Search Inventory");
                Button btnCancel = new Button("Cancel");
                buttonsHbox.getChildren().addAll(btnSearchItem, btnCancel);

                //Label configurations and settings.
                final Label warningLabel = new Label("All fields must be valid");
                warningLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 8));
                warningLabel.setTextFill(Color.RED);
                buttonsHbox.getChildren().add(warningLabel);
                warningLabel.setVisible(false);

                searchEntryVbox.getChildren().addAll(title, nameTextField,
                        buttonsHbox);

                Scene dialogScene = new Scene(searchEntryVbox, 325, 150);
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
                Button btnSearchItem = new Button("Search Inventory");
                Button btnCancel = new Button("Cancel");
                buttonsHbox.getChildren().addAll(btnSearchItem, btnCancel);

                //Label settings and configurations.
                final Label warningLabel = new Label("All fields must be valid");
                warningLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 8));
                warningLabel.setTextFill(Color.RED);
                buttonsHbox.getChildren().add(warningLabel);
                warningLabel.setVisible(false);

                searchEntryVbox.getChildren().addAll(title, skuTextField,
                        buttonsHbox);

                Scene dialogScene = new Scene(searchEntryVbox, 325, 150);
                dialog.setScene(dialogScene);
                dialog.show();

                btnSearchItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent _e) {
                        String itemToSearch = skuTextField.getText();
                        ArrayList items = new ArrayList();
                        items = inventory.searchInventoryByName(skuTextField.getText());

                        if (skuTextField.getText().trim().equals("")) {
                            warningLabel.setVisible(true);
                        } else {
                            warningLabel.setVisible(false);
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
                Button btnSearchItem = new Button("Search Inventory");
                Button btnCancel = new Button("Cancel");
                buttonsHbox.getChildren().addAll(btnSearchItem, btnCancel);

                //Label settings and configurations.
                final Label warningLabel = new Label("All fields must be valid");
                warningLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 8));
                warningLabel.setTextFill(Color.RED);
                buttonsHbox.getChildren().add(warningLabel);
                warningLabel.setVisible(false);

                searchEntryVbox.getChildren().addAll(title, uuidTextField,
                        buttonsHbox);

                Scene dialogScene = new Scene(searchEntryVbox, 325, 150);
                dialog.setScene(dialogScene);
                dialog.show();

                btnSearchItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent _e) {

                        if (uuidTextField.getText().trim().equals("")) {
                            warningLabel.setVisible(true);
                        } else {
                            warningLabel.setVisible(false);
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
                Button btnSearchItem = new Button("Search Inventory");
                Button btnCancel = new Button("Cancel");
                buttonsHbox.getChildren().addAll(btnSearchItem, btnCancel);

                //Label settings and configurations.
                final Label warningLabel = new Label("All fields must be valid");
                warningLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 8));
                warningLabel.setTextFill(Color.RED);
                buttonsHbox.getChildren().add(warningLabel);
                warningLabel.setVisible(false);

                searchEntryVbox.getChildren().addAll(title, priceTextField,
                        buttonsHbox);

                Scene dialogScene = new Scene(searchEntryVbox, 325, 150);
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
        return new Scene(inventoryPanel, screenWidth, screenHeight);
    }

    //Login screen with username & password text fields, plus a sign-in button.
    //TODO: Add event-firing code to the Sign In button.
    public Scene buildLoginPanel() {
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

        Button signInBtn = new Button("Sign In");
        HBox signInHB = new HBox(10);
        signInHB.setAlignment(Pos.BOTTOM_RIGHT);
        signInHB.getChildren().add(signInBtn);
        grid.add(signInHB, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        //Image view properties.
        FileInputStream input = null;
        try {
            input = new FileInputStream("res/boffo_logo.png");
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
                loadMainPanel();
                //TODO: Check credentials here.
                //TODO: Implement event firing.
            }
        });

        signInBtn.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent _event) {
                if (_event.getCode().equals(KeyCode.ENTER)) {
                    loadMainPanel();
                    //Check credentials here.
                }
            }
        });

        pwBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent _event) {
                if (_event.getCode().equals(KeyCode.ENTER)) {
                    loadMainPanel();
                    //
                }
            }
        });

        userTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent _event) {
                if (_event.getCode().equals(KeyCode.ENTER)) {
                    loadMainPanel();
                    //TODO: Check credentials here.
                    //TODO: Implement event firing.
                }
            }
        });

        return new Scene(root, screenWidth, screenHeight);
    }

    public Scene buildMainPanel() {
        StackPane root = new StackPane();

        //Operations menu configuarations.
        Button btnTransaction = new Button("Transaction");
        Button btnInventory = new Button("Inventory");
        Button btnAdministration = new Button("Administration");
        Button btnExit = new Button("Logout");

        VBox vbox = this.addVBox("Select Operation", 10, Pos.BASELINE_LEFT);
        vbox.getChildren().addAll(btnTransaction, btnInventory, btnAdministration, btnExit);

        //ImageView settigs and configurations.
        FileInputStream input = null;
        try {
            input = new FileInputStream("res/boffo_logo.png");
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
                loadTransactionPanel();
                //BoffoEvent evtTransaction =
                //        new BoffoEvent(this, Routing.TRANSACTION_PANEL);
                //fireEvent(evtTransaction);
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
            }
        });

        // Create the scene and return.
        return new Scene(root, screenWidth, screenHeight);
    }

    /*
     * TODO: Display ticket items in a TableView or list.
     *       Create event handlers for the buttons.
     */
    public Scene buildTransactionPanel() {
        //Split pane options.
        SplitPane transactionPanel = new SplitPane();
        transactionPanel.setDividerPosition(1, .5);
        final ObservableList<Item> itemList = FXCollections.observableArrayList();

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
        transactionOptions.getChildren().addAll(btnRemoveItem, btnExit, textFieldVbox);

        //TableView settings and configurations.
        final TableView ticketTbl = new TableView();
        ticketTbl.setMinHeight(400);
        ticketTbl.setMinWidth(250);

        ticketTbl.setEditable(true);

        // Establish the columns and associate them with Item attributes.
        final TableColumn nameCol = new TableColumn("Name");
        nameCol.setMinWidth(125);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<Item, String>("itemName"));

        final TableColumn SKUCol = new TableColumn("SKU");
        SKUCol.setMinWidth(125);
        SKUCol.setCellValueFactory(
                new PropertyValueFactory<Item, String>("SKU"));

        final TableColumn priceCol = new TableColumn("Price");
        priceCol.setMinWidth(125);
        priceCol.setCellValueFactory(
                new PropertyValueFactory<Item, String>("price"));

        // Add the item list to the table.
        ticketTbl.setItems(itemList);
        ticketTbl.getColumns().addAll(nameCol, SKUCol, priceCol);

        VBox ticketTblOptions = new VBox(ticketTbl);
        ticketTblOptions.setAlignment(Pos.TOP_LEFT);
        ticketTblOptions.setPadding(new Insets(10));
        transactionPanel.getItems().addAll(
                transactionOptions, ticketTblOptions);

        // Button actions follow.
        btnExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
                displayExitAlert("Confirmation",
                        "You will lose any entered data.", "Are you sure?");
            }
        });
        btnRemoveItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent _e) {
                Item selectedItem = (Item) 
                        ticketTbl.getSelectionModel().getSelectedItem();
                ticketTbl.getItems().remove(selectedItem);
            }
        });

        btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (skuInputField.getText().isEmpty() != true) {
                    Item itemSKU = new Item(
                            "SKU Item", skuInputField.getText(), 0.99);
                    itemList.add(itemSKU);
                    skuInputField.clear();
                }
            }
        });

        btnRemoveItem.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    Item selectedItem = (Item) ticketTbl.getSelectionModel().getSelectedItem();
                    ticketTbl.getItems().remove(selectedItem);
                }
            }
        });

        btnSubmit.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)
                        && skuInputField.getText().isEmpty() != true) {
                    Item itemSKU = new Item(
                            "SKU Item", skuInputField.getText(), 0.99);
                    itemList.add(itemSKU);
                    skuInputField.clear();
                }
            }
        });

        skuInputField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)
                        && skuInputField.getText().isEmpty() != true) {
                    Item itemSKU = new Item(
                            "SKU Item", skuInputField.getText(), 0.99);
                    itemList.add(itemSKU);
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
    
    @Override
    public void messageReceived(BoffoEvent event) {
        
    }
}
