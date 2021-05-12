package com.calebreigada;

/**
 * This class is a child of the 'Part' class.
 * It is used for parts made in-house and implements a
 * machine id variable.
 */
public class InHouse extends Part{
    private int machineId;

    /**
     * This is the constructor method.
     * It creates an instance of the 'InHouse' class.
     * @param id The part's unique id
     * @param name The name of the part
     * @param price The price of the part
     * @param stock The amount of the part in storage
     * @param min The minimum stock of the part that is allowed
     * @param max The maximum stock of the part that is allowed
     * @param machineId The id of the machine that created the part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * This method sets the 'machineId' variable.
     * @param machineId The id of the machine that created the part
     */
    public void setMachineId(int machineId){
        this.machineId = machineId;
    }

    /**
     * This method return the 'machineId' variable.
     * @return Returns the part's machine id.
     */
    public int getMachineId(){
        return this.machineId;
    }
}
