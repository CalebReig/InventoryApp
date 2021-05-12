package com.calebreigada;

/**
 * This class is a child of the 'Part' class.
 * It is used for parts that are outsourced and
 * implements a company name variable.
 */
public class Outsourced extends Part{
    private String companyName;

    /**
     * This is the constructor method.
     * It creates an instance of the 'Outsourced' class.
     * @param id The part's unique id
     * @param name The name of the part
     * @param price The price of the part
     * @param stock The amount of the part in storage
     * @param min The minimum stock of the part that is allowed
     * @param max The maximum stock of the part that is allowed
     * @param companyName The name of the company that created the part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * This method sets the 'companyName' variable.
     * @param companyName The name of the company that created the part.
     */
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    /**
     * This method returns the 'companyName' variable.
     * @return Returns the part's company name.
     */
    public String getCompanyName(){
        return this.companyName;
    }
}