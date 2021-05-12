package com.calebreigada;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class stores all parts and products information.
 * It includes static methods to manipulate the stored data.
 */
public class Inventory {

    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * This method adds a new part to the 'allParts' list.
     * The 'allParts' list stores data on all parts in the application.
     * @param newPart The part to be added
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * This method adds a new product to the 'allProducts' list.
     * The 'allProducts' list stores data on all products in the application.
     * @param newProduct The product to be added
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * This method searches for a part based on its id.
     * @param partId The part's id
     * @return Returns the part with a matching id, else returns null.
     */
    public static Part lookupPart(int partId) {
        for (Part part:allParts) {
            if (part.getId() == partId) return part;
        }
        return null;
    }

    /**
     * This method searches for a product based on its id.
     * @param productId The product's id
     * @return Returns the product with a matching id, else returns null.
     */
    public static Product lookupProduct(int productId) {
        for (Product product:allProducts) {
            if (product.getId()==productId) return product;
        }
        return null;
    }

    /**
     * This method searches for parts based on the part name.
     * It can search for parts with a partial name search.
     * It can search for multiple parts at once if names are similar.
     * @param partName The part's name or a partial spelling of the name
     * @return Returns a list of all parts with similar names to the search
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partsToShow = FXCollections.observableArrayList();
        for (Part part:allParts) {
            if (part.getName().startsWith(partName)) {
                partsToShow.add(part);
            }
        }
        return partsToShow;
    }

    /**
     * This method searches for products based on the part name.
     * It can search for products with a partial name search.
     * It can search for multiple products at once if names are similar.
     * @param productName The product's name or a partial spelling of the name
     * @return Returns a list of all products with similar names to the search
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productsToShow = FXCollections.observableArrayList();
        for (Product product:allProducts) {
            if (product.getName().startsWith(productName)) {
                productsToShow.add(product);
            }
        }
        return productsToShow;
    }

    /**
     * This method updates a pre-existing part with new data.
     * @param index The id of the part to update
     * @param newPart The new part to replicate
     */
    public static void updatePart(int index, Part newPart){
        for (Part part: allParts) {
            if (part.getId()==index) {
                if (part instanceof InHouse == newPart instanceof InHouse) {
                    if (part instanceof InHouse) {
                        ((InHouse) part).setMachineId(((InHouse) newPart).getMachineId());
                    }
                    else {
                        ((Outsourced) part).setCompanyName(((Outsourced) newPart).getCompanyName());
                    }
                    part.setName(newPart.getName());
                    part.setPrice(newPart.getPrice());
                    part.setStock(newPart.getStock());
                    part.setMin(newPart.getMin());
                    part.setMax(newPart.getMax());
                }
                else {
                        Inventory.deletePart(part);
                        Inventory.addPart(newPart);
                }
            }
        }
    }

    /**
     * This method updates a pre-existing product with new data.
     * @param index The id of the product to update
     * @param newProduct The new product to replicate
     */
    public static void updateProduct(int index, Product newProduct){
        for (Product product: allProducts) {
            if (product.getId()==index) {
                product.setName(newProduct.getName());
                product.setPrice(newProduct.getPrice());
                product.setStock(newProduct.getStock());
                product.setMin(newProduct.getMin());
                product.setMax(newProduct.getMax());
            }
        }
    }

    /**
     * This method deletes a part.
     * @param selectedPart The part to be deleted
     * @return Returns true if a part was deleted.
     */
    public static boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    }

    /**
     * This method deletes a product.
     * @param selectedProduct The product to be deleted
     * @return Returns true if a product was deleted.
     */
    public static boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }

    /**
     * This method returns all the parts in the application.
     * @return Returns a list of all the parts.
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     * This method returns all the products in the application.
     * @return Returns a list of all the products.
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
}
