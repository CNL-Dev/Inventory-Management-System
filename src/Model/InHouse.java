/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * Class that handles InHouse objects
 * @author Caleb Lugo
 */
public class InHouse extends Part {
    private int machineID;
    /**
     * Constructor that creates an InHouse object
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineID 
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
        
    }
    
    /**
     * Sets the machine ID
     * @param num 
     */
    public void setMachineID(int num){
        machineID = num;
    }
    
    /**
     * Returns the machine ID
     * @return machine ID
     */
    public int getMachineID(){
        return machineID;
    }
    
}
