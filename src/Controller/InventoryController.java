/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Inventory;

/**
 *This class handles the inventory object
 * Any changes to the inventory must go through this class
 * @author Caleb Lugo
 */
public class InventoryController{
    
    private static Inventory inventory = null;
    
    /**
     * Empty constructor
     */
    private void InventoryController(){
        
    }
    
    /**
     * If no inventory object exists, make one
     * @return inventory
     */
    public static Inventory getInventory(){
        if (inventory == null){
            inventory = new Inventory();
        }
        return inventory;
    }
}
