/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class creates a product object
 * @author Caleb Lugo
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    
    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    
    /**
     * Returns the ID
     * @return id
     */
    public int getId(){
        return id;
    }
    
    /**
     * Sets the ID
     * @param id 
     */
    public void setId(int id){
        this.id = id;
    }
    
    
    /**
     * Returns the name
     * @return name
     */
    public String getName(){
        return name;
    }
    
    
    /**
     * Sets the name
     * @param name 
     */
    public void setName(String name){
        this.name = name;
    }
    
    
    /**
     * Returns the price
     * @return price
     */
    public double getPrice(){
        return price;
    }
    
    
    /**
     * Sets the price
     * @param price 
     */
    public void setPrice(double price){
        this.price = price;
    }
    
    /**
     * Returns the stock
     * @return stock
     */
    public int getStock(){
        return stock;
    }
    
    /**
     * Sets the stock
     * @param stock 
     */
    public void setStock(int stock){
        this.stock = stock;
    }
    
    
    /**
     * Returns the min
     * @return min
     */
    public int getMin(){
        return min;
    }
    
    /**
     * Sets the min
     * @param min 
     */
    public void setMin(int min){
        this.min = min;
    }
    
    /**
     * Returns the max
     * @return max
     */
    public int getMax(){
        return max;
    }
    
    /**
     * sets the max
     * @param max 
     */
    public void setMax(int max){
        this.max = max;
    }
    
    /**
     * Adds an associated part to the product
     * @param part 
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    
    /**
     * Removes a associated part from the product
     * @param selectedAssociatedPart
     * @return true or false depending in whether the function was successful or not
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        //Seaches associated part list for part to be removed
        for (int i = 0; i < associatedParts.size(); i++){
            //If associated part is found, remove it
            if (selectedAssociatedPart.getId() == associatedParts.get(i).getId()){
                associatedParts.remove(selectedAssociatedPart);
                return true;
            }
        }
        //If part is not found, return false
        return false;
    }
    
    /**
     * Returns all associated parts of the product
     * @return associated parts
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
