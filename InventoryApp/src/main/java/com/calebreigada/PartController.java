package com.calebreigada;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * This class controls events for the Part window.
 */
public class PartController {
    @FXML private Label titleLabel;
    @FXML private  TextField idField;
    @FXML private  TextField nameField;
    @FXML private  TextField priceField;
    @FXML private  TextField stockField;
    @FXML private  TextField maxField;
    @FXML private  TextField minField;
    @FXML private  TextField altField;
    @FXML private  Label altText;
    @FXML private  RadioButton inHouseRadioButton;
    @FXML private  RadioButton outsourcedRadioButton;
    private static boolean modifyView;
    private static Part selectedPart;

    /**
     * This method sets the initial settings for the Part window.
     * It sets up the window depending on if the 'Add Part' button
     * or the 'Modify Part' button was pressed.
     */
    public void initialize() {
        ToggleGroup group = new ToggleGroup();
        inHouseRadioButton.setToggleGroup(group);
        outsourcedRadioButton.setToggleGroup(group);
        if (modifyView) {
            modifyPartView(selectedPart);
        }
        else {
            addPartView();
        }
    }

    /**
     * This method sets up the view if the 'Add Part' button was pressed.
     */
    private void addPartView() {
        titleLabel.setText("Add Part");
        idField.setText("Auto-ID");
        nameField.setText(null);
        priceField.setText(null);
        stockField.setText(null);
        minField.setText(null);
        maxField.setText(null);
        altField.setText(null);
        altText.setText("Machine ID");
        inHouseRadioButton.setSelected(true);
    }

    /**
     * This method sets up the view if the 'Modify Part' button was pressed.
     * @param part The part that is being modified
     */
    private void modifyPartView(Part part) {
        titleLabel.setText("Modify Part");
        idField.setText(String.valueOf(part.getId()));
        nameField.setText(part.getName());
        priceField.setText(String.valueOf(part.getPrice()));
        stockField.setText(String.valueOf(part.getStock()));
        minField.setText(String.valueOf(part.getMin()));
        maxField.setText(String.valueOf(part.getMax()));
        if (part instanceof InHouse) {
            altField.setText(String.valueOf(((InHouse) part).getMachineId()));
            altText.setText("Machine ID");
            inHouseRadioButton.setSelected(true);
        }
        else {
            altField.setText(((Outsourced) part).getCompanyName());
            altText.setText("Company Name");
            outsourcedRadioButton.setSelected(true);
        }
    }

    /**
     * This method adds a new part to the list of all parts.
     * It takes user input in the text fields to set the part's
     * variables. If the part is being modified instead, the part's
     * variables are updated. An error is produced if the inputs are not valid
     * or logic is broken.
     */
    @FXML private void addPart() {
        try {
            int id;
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(stockField.getText());
            int min = Integer.parseInt(minField.getText());
            int max = Integer.parseInt(maxField.getText());
            String alt = altField.getText();
            boolean isInHouse = inHouseRadioButton.isSelected();
            Part newPart;
            //Logical Error Check
            if (min > max) throw new Exception();
            if (stock < min || stock > max) throw new Exception();

            if (modifyView) {
                id = selectedPart.getId();
                if (isInHouse) newPart = new InHouse(id, name, price, stock, min, max, Integer.parseInt(alt));
                else newPart = new Outsourced(id, name, price, stock, min, max, alt);
                Inventory.updatePart(id, newPart);
            }
            else {
                id = App.idGenerator("Part");
                if (isInHouse) newPart = new InHouse(id, name, price, stock, min, max, Integer.parseInt(alt));
                else newPart = new Outsourced(id, name, price, stock, min, max, alt);
                Inventory.addPart(newPart);
            }
                switchToPrimary();
        }
        catch(Exception e) {
            String altFieldError = "Company Name: String";
            if (inHouseRadioButton.isSelected()) altFieldError = "Machine ID: Int";

            App.displayError("Input Error", "The below fields need the following input.\n\n" +
                                                                "Name: String\n" +
                                                                "Price: Double\n" +
                                                                "Stock: Int\n" +
                                                                "Min: Int (less than Max)\n" +
                                                                "Max: Int (more then Min)\n" +
                                                                altFieldError);
        }
    }

    /**
     * This method sets up the view if the 'Outsourced' radio button is selected.
     */
    @FXML private void outsourcedView() {
        altText.setText("Company Name");
    }

    /**
     * This method sets up the view if the 'In-House' radio button is selected.
     */
    @FXML private void inHouseView() {
        altText.setText("Machine ID");
    }

    /**
     * This method switches the window back to the primary.
     */
    @FXML private void switchToPrimary() throws IOException {
        selectedPart = null;
        App.setRoot("primary");
    }

    /**
     * This method sets the class's modifyView variable.
     * @param view True if window is in modify part view
     */
    public static void setModifyView(boolean view) {
        modifyView = view;
    }

    /**
     * This method sets the class's modifyView variable.
     * @param view True if window is in modify part view
     * @param part The selected part to be modified
     */
    public static void setModifyView(boolean view, Part part) {
        modifyView = view;
        selectedPart = part;

    }
}