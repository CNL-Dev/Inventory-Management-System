/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *This is the inventory class
 * This class will handle everything to do with parts and products
 * @author Caleb Lugo
 */
public class Inventory {
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    static int id = 0;
    
    /**
     * Adds a new part into the part observable list
     * @param newPart 
     */
    public void addPart(Part newPart){
        allParts.add(newPart);
    }
    
    /**
     * Adds a new product into the product observable list
     * @param newProduct 
     */
    public void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    
    /**
     * Finds a part via the part ID, else returns null
     * @param partId
     * @return part or null
     */
    public Part lookupPart(int partId){
        for (int i = 0; i < allParts.size(); i++){
            if (allParts.get(i).getId() == partId){
                return allParts.get(i);
            }
        }
        //If nothing was found, return null
        return null;
    }
    
    /**
     * Finds a product via the product ID, else returns null
     * @param productId
     * @return product or null
     */
    public Product lookupProduct(int productId){
        for (int i = 0; i < allProducts.size(); i++){
            if (allProducts.get(i).getId() == productId)
                return allProducts.get(i);
        }
        //If nothing was found, return null
        return null;
    }
    
    /**
     * Finds a part via the part name, else returns null
     * @param partName
     * @return part or null(search list is empty)
     */   
    public ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> searchedParts = FXCollections.observableArrayList();
        
        for (Part part : getAllParts()) {
            if(part.getName().contains(partName)){
                searchedParts.add(part);
            }
        }
        return searchedParts;
    }
    
    /**
     * Finds a product via the product name, else returns null
     * @param productName
     * @return product or null(search list is empty)
     */
    public ObservableList<Product> lookupProduct( String productName){
        ObservableList<Product> searchedProducts = FXCollections.observableArrayList();
        
        for (Product product : getAllProducts()) {
            if(product.getName().contains(productName)){
                searchedProducts.add(product);
            }
        }
        return searchedProducts;
    }
    
    /**
     * Updates a part via the part index
     * @param index of list
     * @param selectedPart 
     */
    public void updatePart(int index, Part selectedPart){
         allParts.set(index, selectedPart);
    }
    
    /**
     * Updates a product via the product index
     * @param index of list
     * @param selectedProduct 
     */
    public void updateProduct(int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);
    }
    
    /**
     * Deletes the selected part
     * @param selectedPart
     * @return either selected part or null
     */
    public boolean deletePart(Part selectedPart){
        //Searches entire list to find the part that is to be deleted
        for (int i = 0; i < allParts.size(); i++){
            //If found, remove the part
            if(selectedPart.getId() == allParts.get(i).getId()){
                allParts.remove(i);
                return true;
            }
        }
        //If not found, return false
        return false;
    }
    
    /**
     * Deletes the selected product
     * @param selectedProduct
     * @return true or false depending in whether the function was successful or not
     */
    public boolean deleteProduct(Product selectedProduct){
        //Searches entire list to find the product that is to be deleted
        for (int i = 0; i < allProducts.size(); i++){
            //If found, remove the product
            if(selectedProduct.getId() == allProducts.get(i).getId()){
                allProducts.remove(i);
                return true;
            }
        }
        //If not found, return false
        return false;
    }
    
    /**
     * Returns all parts in the observable list
     * @return all parts
     */
    public ObservableList<Part> getAllParts(){
        return allParts;
    }
    
    /**
     * Returns all products in the observable list
     * @return all products
     */
    public ObservableList<Product> getAllProducts(){
        return allProducts;
    }
    
    /**
     * Generates a unique ID for parts and products
     * @return a unique id
     */
    public static int generateUniqueId(){
        id++;
        return id;
    }

}
