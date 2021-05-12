package com.calebreigada;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class creates and modifies the product objects.
 */

public class Product{
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * This is a constructor method.
     * It creates an instance of the 'Product' class.
     * @param id The product's unique id
     * @param name The name of the product
     * @param price The price of the product
     * @param stock The amount of the product in storage
     * @param min The minimum stock of the product that is allowed
     * @param max The maximum stock of the product that is allowed
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * This method returns the product's id.
     * @return Returns the product's id
     */
    public int getId() {
        return id;
    }

    /**
     * This method sets the product's id.
     * @param id The id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method returns the product's name.
     * @return Returns the name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * This method sets the product's name.
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method returns the product's price.
     * @return Returns the price of the product.
     */
    public double getPrice() {
        return price;
    }

    /**
     * This method sets the product's price.
     * @param price The price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * This method returns the product's stock.
     * @return Returns the stock of the product.
     */
    public int getStock() {
        return stock;
    }

    /**
     * This method sets the product's stock.
     * @param stock The stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * This method returns the product's min.
     * @return Returns the min of the product.
     */
    public int getMin() {
        return min;
    }

    /**
     * This method sets the product's min.
     * @param min The min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * This method returns the product's max.
     * @return Returns the max of the product.
     */
    public int getMax() {
        return max;
    }

    /**
     * This product sets the product's max.
     * @param max The max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * This method adds a part to the product's list of associated parts.
     * @param part The part to be added to the product's associated parts
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * This method deletes a part from the product's list of associated parts.
     * @param selectedAssociatedPart The product that is being modified
     * @return Returns true if a part was deleted
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * This method returns a list of the product's associated parts.
     * @return Returns all associated parts of a product.
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}