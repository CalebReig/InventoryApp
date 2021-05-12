package com.calebreigada;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * This class controls events for the Product window.
 */
public class ProductController {
    @FXML private Label titleLabel;
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField priceField;
    @FXML private TextField stockField;
    @FXML private TextField minField;
    @FXML private TextField maxField;
    @FXML private TextField partSearchBox;
    private static boolean modifyView;
    private static Product selectedProduct;
    private final ObservableList<Part> partsToAssociate = FXCollections.observableArrayList();
    //allParts Table setup
    @FXML private TableView<Part> allPartsTable;
    @FXML private TableColumn<Part, String> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, String> partStockColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;
    //associatedParts Table Setup
    @FXML private TableView<Part> associatedPartsTable;
    @FXML private TableColumn<Part, String> associatedPartIdColumn;
    @FXML private TableColumn<Part, String> associatedPartNameColumn;
    @FXML private TableColumn<Part, String> associatedPartStockColumn;
    @FXML private TableColumn<Part, Double> associatedPartPriceColumn;

    /**
     * This method sets up the all parts table and associated parts table.
     * It also sets up the Add Product view or the Modify Product view.
     */
    public void initialize() {
        //allParts Table Columns Setup
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        allPartsTable.setItems(Inventory.getAllParts());
        //associatedParts Table Columns Setup
        associatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        if (modifyView) {
            modifyProductView(selectedProduct);
        }
        else {
            addProductView();
        }
    }

    /**
     * This method adds a new product to the list of all products.
     * It takes user input in the text fields to set the product's
     * variables. If the product is being modified instead, the product's
     * variables are updated. An error is produced if the inputs are not valid
     * or logic is broken.
     */
    @FXML private void addProduct() {
        try {
            int id;
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(stockField.getText());
            int min = Integer.parseInt(minField.getText());
            int max = Integer.parseInt(maxField.getText());
            //Logical Error Check
            if (min > max) throw new Exception();
            if (stock < min || stock > max) throw new Exception();

            if (modifyView) {
                id = selectedProduct.getId();
                Product product = new Product(id, name, price, stock, min, max);
                Inventory.updateProduct(id, product);
            }
            else {
                id = App.idGenerator("Product");
                Product product = new Product(id, name, price, stock, min, max);
                Inventory.addProduct(product);

                if (partsToAssociate.size() > 0) {
                    for (Part part : partsToAssociate) {
                        product.addAssociatedPart(part);
                    }
                }
            }
            switchToPrimary();
        }
        catch (Exception e) {
            App.displayError("Input Error", "The below fields need the following input.\n\n" +
                    "Name: String\n" +
                    "Price: Double\n" +
                    "Stock: Int\n" +
                    "Min: Int (less than Max)\n" +
                    "Max: Int (more then Min)\n");
        }
    }

    /**
     * This method adds a part to the product's associated parts list.
     * The added part is then populated in the window's associated parts table.
     */
    @FXML private void addAssociatedPart() {
        //-------------------------DETAILED COMMENTS------------------------
        //In order to solve this problem, first the selected part to be added
        //needed to be stored in a variable (see below 'part')
        Part part = allPartsTable.getSelectionModel().getSelectedItem();
        //Next, we need to know whether we are adding a new product or modifying
        //an existing product. Each case will have a different solution. The
        //'modifyView' boolean variable is true when we are modifying an existing product
        if (modifyView) {
            //This is the simplest case and is pretty straight forward. The pre-existing
            //product's associated parts list is modified and the associated parts table is
            //updated to reflect this.
            selectedProduct.addAssociatedPart(part);
            associatedPartsTable.setItems(selectedProduct.getAllAssociatedParts());
        }
        else {
            //This is the case that was causing errors; when attempting to add an
            //associated part to a not yet existing product. Instead of adding the part to the product's
            //associated parts, the part is stored in a temporary list 'partsToAssociate'. Once the new
            //product is created when the user presses the save button, the product is created then the
            //associated parts are added. This can be viewed in more detail in the 'addProduct' method.
            partsToAssociate.add(part);
            associatedPartsTable.setItems(partsToAssociate);
        }
        //There is still a flaw with this solution; when adding an associated part to a pre-existing
        //product, the parts get associated before the user presses the save button. This is possible
        //fix in a future update, but I believe it is outside the scope of the initial assignment.
    }

    /**
     * This method removes a part from the product's associated parts list.
     * The removed part is then unpopulated in the window's associated parts table.
     */
    @FXML private void removeAssociatedPart() {
        boolean confirmation = App.deletionConfirmation("Associated Part");
        if (confirmation) {
            Part part = associatedPartsTable.getSelectionModel().getSelectedItem();
            if (modifyView) {
                if (selectedProduct.deleteAssociatedPart(part)) {
                    associatedPartsTable.setItems(selectedProduct.getAllAssociatedParts());
                } else {
                    App.displayError("Deletion Error", "No product was selected.");
                }
            } else {
                if (partsToAssociate.remove(part)) {
                    associatedPartsTable.setItems(partsToAssociate);
                } else {
                    App.displayError("Deletion Error", "No product was selected.");
                }
            }
        }
    }

    /**
     * This method searches for a part(s) based on its id or name.
     */
    @FXML private void searchForPart() {
        String input = partSearchBox.getText();
        try {
            int inputId = Integer.parseInt(input);
            ObservableList<Part> searchedParts = FXCollections.observableArrayList();
            searchedParts.add(Inventory.lookupPart(inputId));
            allPartsTable.setItems(searchedParts);
        }
        catch(Exception exception) {
            allPartsTable.setItems(Inventory.lookupPart(input));
        }
    }

    /**
     * This method sets up the view if the 'Modify Product' button was pressed.
     * @param product The product that is being modified
     */
    private void modifyProductView(Product product) {
        titleLabel.setText("Modify Product");
        idField.setText(String.valueOf(product.getId()));
        nameField.setText(product.getName());
        priceField.setText(String.valueOf(product.getPrice()));
        stockField.setText(String.valueOf(product.getStock()));
        minField.setText(String.valueOf(product.getMin()));
        maxField.setText(String.valueOf(product.getMax()));

        associatedPartsTable.setItems(selectedProduct.getAllAssociatedParts());
    }

    /**
     * This method sets up the view if the 'Add Product' button was pressed.
     */
    private void addProductView() {
        titleLabel.setText("Add Product");
        idField.setText("Auto-ID");
        nameField.setText(null);
        priceField.setText(null);
        stockField.setText(null);
        minField.setText(null);
        maxField.setText(null);
    }

    /**
     * This method sets the class's modifyView variable.
     * @param view True if window is in modify product view
     */
    public static void setModifyView(boolean view) {
        modifyView = view;
    }

    /**
     * This method sets the class's modifyView variable.
     * @param view True if window is in modify product view
     * @param product The selected product to be modified
     */
    public static void setModifyView(boolean view, Product product) {
        modifyView = view;
        selectedProduct = product;
    }

    /**
     * This method switches the window back to the primary.
     */
    @FXML private void switchToPrimary() throws IOException {
        partsToAssociate.removeAll();
        App.setRoot("primary");
    }
}