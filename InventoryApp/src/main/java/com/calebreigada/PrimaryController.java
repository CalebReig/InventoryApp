package com.calebreigada;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * This class controls events for the main window.
 */
public class PrimaryController {
    @FXML private TextField partSearchBox;
    @FXML private TextField productSearchBox;
    //allParts Table setup
    @FXML private TableView<Part> allPartsTable;
    @FXML private TableColumn<Part, String> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, String> partStockColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;
    //allProducts Table Setup
    @FXML private TableView<Product> allProductsTable;
    @FXML private TableColumn<Part, String> productIdColumn;
    @FXML private TableColumn<Part, String> productNameColumn;
    @FXML private TableColumn<Part, String> productStockColumn;
    @FXML private TableColumn<Part, Double> productPriceColumn;

    /**
     * This method sets up the parts table and products table.
     */
    public void initialize() {
        //allParts Table Columns Setup
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        allPartsTable.setItems(Inventory.getAllParts());
        //allProducts Table Columns Setup
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        allProductsTable.setItems(Inventory.getAllProducts());
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
     * This method searches for a product(s) based on its id or name
     */
    @FXML private void searchForProduct() {
        String input = productSearchBox.getText();
        try {
            int inputId = Integer.parseInt(input);
            ObservableList<Product> searchedProducts = FXCollections.observableArrayList();
            searchedProducts.add(Inventory.lookupProduct(inputId));
            allProductsTable.setItems(searchedProducts);
        }
        catch(NumberFormatException e) {
            allProductsTable.setItems(Inventory.lookupProduct(input));
        }
    }

    /**
     * This method switches the application to the Part window/Add Part view.
     */
    @FXML private void switchToAddPart() throws IOException {
        PartController.setModifyView(false);
        App.setRoot("part");
    }

    /**
     * This method switches the application to the Part window/Modify Part view.
     */
    @FXML private void switchToModifyPart() {
        try {
            Part selectedPart = allPartsTable.getSelectionModel().getSelectedItem();
            PartController.setModifyView(true, selectedPart);
            App.setRoot("part");
        }
        catch (Exception e) {
            App.displayError("Selection Error", "A part needs to be selected.");
        }
    }

    /**
     * This method switches the application to the Product window/Add Product view.
     */
    @FXML private void switchToAddProduct() throws IOException {
        ProductController.setModifyView(false);
        App.setRoot("product");
    }

    /**
     * This method switches the application to the Product window/Modify Product view.
     */
    @FXML private void switchToModifyProduct() {
        try {
            Product selectedProduct = allProductsTable.getSelectionModel().getSelectedItem();
            ProductController.setModifyView(true, selectedProduct);
            App.setRoot("product");
        }
        catch (Exception e) {
            App.displayError("Selection Error", "A product needs to be selected.");
        }
    }

    /**
     * This method deletes a user selected part in the parts table.
     * A confirmation box is produced before the part is deleted.
     */
    @FXML private void deletePart() {
        boolean confirmation = App.deletionConfirmation("Part");
        if (confirmation) {
            Part selectedPart = allPartsTable.getSelectionModel().getSelectedItem();
            if (Inventory.deletePart(selectedPart)) {
                allPartsTable.setItems(Inventory.getAllParts());
            }
            else {
                App.displayError("Deletion Error", "No part was selected.");
            }
        }
    }

    /**
     * This method deletes a user selected product in the all products table.
     * A confirmation box is produced before the product is deleted and an
     * error is triggered if the selected product has associated parts.
     */
    @FXML private void deleteProduct() {
        boolean confirmation = App.deletionConfirmation("Product");
        if (confirmation) {
            Product selectedProduct = allProductsTable.getSelectionModel().getSelectedItem();
            if (selectedProduct.getAllAssociatedParts().size() > 0) {
                App.displayError("Deletion Error", "Cannot delete a product with associated parts");
            }
            else if (Inventory.deleteProduct(selectedProduct)) {
                allProductsTable.setItems(Inventory.getAllProducts());
            }
            else {
                App.displayError("Deletion Error", "No product was selected.");
            }
        }
    }

    /**
     * This method closes the application.
     */
    @FXML private void exit() {
        App.window.close();
    }
}
