/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Controller.InventoryController;
import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import Model.Product;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class for the Inventory Management System
 * Javadocs are located in InventoryManagementSystem/dist
 * This is essentially the default javafx fxml file that you start with in netbeans but with some modifications
 * @author Caleb Lugo
 */
public class Main extends Application {
    
    /**
     * This function open the main scene window
     * @param stage
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        //Launches the main scene window
        
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScene.fxml"));       
        Scene scene = new Scene(root);        
        stage.setScene(scene);
        stage.show();        
    }

    /**
     * This is the main function of the program
     * It loads the inventory and adds test data into it
     * @param args the command line arguments
     */
    public static void main(String[] args) { 
        InventoryController.getInventory();
        
        //Create test data for testing purposes
        Part part1 = new InHouse(Inventory.generateUniqueId(), "Chain", 10.99, 5, 1, 5, 10);
        Part part2 = new InHouse(Inventory.generateUniqueId(), "Brake", 7.49, 50, 15, 37, 11);
        Part part3 = new InHouse(Inventory.generateUniqueId(), "Frame", 49.99, 3, 1, 7, 12);
        
        InventoryController.getInventory().addPart(part1);
        InventoryController.getInventory().addPart(part2);
        InventoryController.getInventory().addPart(part3);
        
        Part part4 = new Outsourced(Inventory.generateUniqueId(), "Pedal", 6.99, 12, 4, 20, "Bike Part Co.");
        Part part5 = new Outsourced(Inventory.generateUniqueId(), "Handle", 19.99, 2, 1, 3, "Bike Part Co.");
        Part part6 = new Outsourced(Inventory.generateUniqueId(), "Flashlight", 10.99, 5, 5, 17, "Joe's Electronic Shop");
        
        InventoryController.getInventory().addPart(part4);
        InventoryController.getInventory().addPart(part5);
        InventoryController.getInventory().addPart(part6);
        
        Product product1 = new Product(Inventory.generateUniqueId(), "Bicycle", 149.99, 23, 20, 50);
        product1.addAssociatedPart(part1);
        product1.addAssociatedPart(part2);
        product1.addAssociatedPart(part3);
        product1.addAssociatedPart(part4);
        product1.addAssociatedPart(part5);
        
        InventoryController.getInventory().addProduct(product1);
        
        Product product2 = new Product(Inventory.generateUniqueId(), "Flashlight Bicycle", 164.99, 3, 2, 5);
        product2.addAssociatedPart(part1);
        product2.addAssociatedPart(part2);
        product2.addAssociatedPart(part3);
        product2.addAssociatedPart(part4);
        product2.addAssociatedPart(part5);
        product2.addAssociatedPart(part6);
        
        InventoryController.getInventory().addProduct(product2);
        
        Product product3 = new Product(Inventory.generateUniqueId(), "Tire Pump", 14.99, 7, 5, 15);
        
        InventoryController.getInventory().addProduct(product3);
        
        launch(args);
    }
}
