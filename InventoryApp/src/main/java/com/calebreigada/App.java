package com.calebreigada;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;

/**
 * This class that starts the application and initializes sample parts/products.
 * It will set up the application window in JavaFX and load
 * all FXML files. Also, it contains static methods that are
 * used by multiple other classes.
 *
 * <p><strong>Error Example: </strong>
 * The most difficult error while making this app has been
 * the add associated parts feature while adding a new product.
 * When modifying an existing product, adding an associated part
 * can be done instantly by calling to the <i>addAssociatedPart</i>
 * method of the <i>Product</i> class. However, in order to
 * accomplish the same functionality for a not yet existing
 * product, a similar solution would not work. Instead, I created
 * a temporary Observable List variable <i>partsToAssociate</i>
 * which stores all the parts that the user adds while making the
 * new product and adds them to the product once the user presses
 * the save button. (more details starting on line 115 (<i>addAssociatedPart</i>
 *  method) in ProductController.java</p>
 *
 * <p><strong>New Feature for Update: </strong>
 * Currently, when adding/modifying a product, it is
 * possible to add multiple of the same associated parts.
 * In a future update of this app, an associated part duplicate
 * validation should be implemented. This will check if multiple
 * instances of the same part are in the <i>associatedParts</i>
 * Observable List and display an error if so. This could be
 * implemented using similar logic to the <i>updatePart</i>/
 * <i>updateProduct</i> methods of the <i>Inventory</i> class
 * (using the objects id and a for-each loop to identify the
 * same object)</p>
 */

public class App extends Application {

    private static Scene mainPage;
    public static Stage window;

    /**
     * This method starts the application.
     * The sample parts and products are initialized in this method.
     * @param stage The stage of the JavaFX application
     */
    @Override
    public void start(Stage stage) throws IOException {
        //load the initial Parts and Products
        InHouse wheel = new InHouse(1, "wheel", 4.90, 10, 2, 21, 331);
        Outsourced seat = new Outsourced(2, "seat", 8.60, 20, 5, 22, "Big Seats");
        Outsourced handle = new Outsourced(3, "handle bars", 10.99, 3, 2, 5, "Handles R Us");
        Inventory.addPart(wheel);
        Inventory.addPart(seat);
        Inventory.addPart(handle);
        Product bike = new Product(1, "bike", 39.99, 12, 2, 99);
        Product tractor = new Product(2, "tractor", 1099.99, 2, 1, 3);
        Inventory.addProduct(bike);
        Inventory.addProduct(tractor);
        bike.addAssociatedPart(wheel);
        bike.addAssociatedPart(seat);
        bike.addAssociatedPart(handle);


        //Setup the Window
        window = stage;
        mainPage = new Scene(loadFXML("primary"));
        window.setScene(mainPage);
        window.show();
    }

    /**
     * This method sets the root of the application's FXML documents.
     * @param fxml The FXML document
     */
    static void setRoot(String fxml) throws IOException {
        mainPage.setRoot(loadFXML(fxml));
    }

    /**
     * This method loads the application's FXML documents.
     * @param fxml The FXML document
     * @return Returns and loads the FXML document.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * This method produces a confirmation pop-up.
     * It is used to confirm if the user wants to delete an item.
     * @param itemType The item type (Part/Product)
     * @return Returns true if the user presses 'OK', else false
     */
    public static boolean deletionConfirmation(String itemType) {
        boolean confirmation;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete " + itemType);
        alert.setContentText("Are you sure you want to delete this " + itemType.toLowerCase() + "?");
        Optional<ButtonType> result = alert.showAndWait();

        confirmation = result.get() == ButtonType.OK;
        alert.close();
        return confirmation;
    }

    /**
     * This method produces an error pop-up.
     * @param errorTitle The title of the pop-up
     * @param errorMessage The message displayed on the pop-up
     */
    public static void displayError(String errorTitle, String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(errorTitle);
        alert.setContentText(errorMessage);

        alert.showAndWait();
    }

    /**
     * This method generates a unique id.
     * It is used to generate ids for parts and products.
     * @param type The object type (Part/Product)
     * @return Returns a unique id.
     */
    public static int idGenerator(String type) {
        int id = 1;
        if (type.equals("Part")) {
            for (Part part : Inventory.getAllParts()) {
                if (part.getId() > id) id = part.getId();
            }
        }
        else {
            for (Product product : Inventory.getAllProducts()) {
                if (product.getId() > id) id = product.getId();
            }
        }
        return id + 1;
    }

    /**
     * This is the main method.
     * @param args Runs the program.
     */
    public static void main(String[] args) {
        launch();
    }

}