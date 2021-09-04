/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Controller class for the add product scene
 * @author Caleb Lugo
 */
public class AddProductController implements Initializable{
    
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldStock;
    @FXML
    private TextField textFieldPrice;
    @FXML
    private TextField textFieldMax;
    @FXML
    private TextField textFieldMin;
    @FXML
    private TableView<Part> tableViewPart;
    @FXML
    private TableColumn tableColumnPartId;
    @FXML
    private TableColumn tableColumnPartName;
    @FXML
    private TableColumn tableColumnPartStock;
    @FXML
    private TableColumn tableColumnPartPrice;
    @FXML
    private TableView<Part> tableViewAssociatedPart;
    @FXML
    private TableColumn tableColumnAssociatedPartId;
    @FXML
    private TableColumn tableColumnAssociatedPartName;
    @FXML
    private TableColumn tableColumnAssociatedPartStock;
    @FXML
    private TableColumn tableColumnAssociatedPartPrice;
    @FXML
    private TextField textFieldSearch;
    
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Part> allAssociatedParts = FXCollections.observableArrayList();
    
    private Part partToAdd;
    private Part partToRemove;
    
    /**
     * Gets all parts and associated parts and displays them in their respective table views and columns
     * @param location
     * @param resources 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /**
         * RUNTIME ERROR
         * ID's in the fxml file were different than the assigned ones in the class. This took a while to fix as the error you
         * receive in the output window is not incredibly descriptive. Eventually this issue was fixed by looking at the fxml file.
         */
        allParts.addAll(InventoryController.getInventory().getAllParts());        
        
        //Assigns data to the columns
        tableColumnPartId.setCellValueFactory(new PropertyValueFactory<>("id")); 
        tableColumnPartName.setCellValueFactory(new PropertyValueFactory<>("name")); 
        tableColumnPartStock.setCellValueFactory(new PropertyValueFactory<>("stock")); 
        tableColumnPartPrice.setCellValueFactory(new PropertyValueFactory<>("price")); 
        tableViewPart.setItems(allParts);
        
        tableColumnAssociatedPartId.setCellValueFactory(new PropertyValueFactory<>("id")); 
        tableColumnAssociatedPartName.setCellValueFactory(new PropertyValueFactory<>("name")); 
        tableColumnAssociatedPartStock.setCellValueFactory(new PropertyValueFactory<>("stock")); 
        tableColumnAssociatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price")); 
        tableViewAssociatedPart.setItems(allAssociatedParts);
    }
    
    /**
     * Handles the text field for parts
     * If no part is found, display an alert
     * If the part(s) is found, display them in the table view
     * @param event 
     */
    public void handleTextFieldParts(ActionEvent event){
        String sTextField = textFieldSearch.getText();
        ObservableList<Part> parts = InventoryController.getInventory().lookupPart(sTextField);
        
        //If no parts were found, double check via the ID
        if (parts.size() == 0){
            try{
                int id = Integer.parseInt(sTextField);
                Part p = searchPartId(id);
                if (p != null){
                    parts.add(p);
                    }
            } catch (NumberFormatException e){  
                //Empty
            }            
        }
        
        //If the item is still not found, display an error window
        if (parts.size() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No results found");
            alert.setContentText("This item does not exist.");
            alert.showAndWait();
        }
        
        tableViewPart.setItems(parts);
    }
    
    /**
     * Searches for a part via its part ID
     * Loops through all parts in a list looking for a match
     * @param partId the target part ID
     * @return null if nothing was found
     */
    private Part searchPartId(int partId){
        for (int i = 0; i < allParts.size(); i++){
            Part p = allParts.get(i);
            
            if (p.getId() == partId){
                return p;
            }
        }
        return null;
    }
    
    /**
     * Adds a associated part from the table view
     * Displays an alert if nothing was selected
     * @param event
     * @throws IOException 
     */
    public void handleButtonAdd(ActionEvent event)throws IOException{
        /**
         * FUTURE ENHANCEMENT
         * Allow for multiple associated parts to be added at once.
         */
        partToAdd = tableViewPart.getSelectionModel().getSelectedItem();
        if (partToAdd == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No part selected");
            alert.setContentText("There is no part selected");
        } else {
            allAssociatedParts.add(partToAdd);
            tableViewAssociatedPart.setItems(allAssociatedParts);
        }
    }
    
    /**
     * Removes an associated part from the product via the table view
     * If nothing is selected, display an alert
     * @param event
     * @throws IOException 
     */
    public void handleButtonRemove(ActionEvent event) throws IOException{
        partToRemove = tableViewAssociatedPart.getSelectionModel().getSelectedItem();
        if (partToRemove == null){
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("No part selected");
            alert1.setContentText("There is no part selected");
        } else {
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
            alert2.setTitle("Remove part");
            alert2.setContentText("Are you sure you would like to remove this associated part?");
            Optional<ButtonType> answer = alert2.showAndWait();
            if (answer.get() == ButtonType.OK){
                tableViewAssociatedPart.getItems().remove(partToRemove);
            }
        }
    }
    
    /**
     * Saves the new part and returns to the main scene
     * Will display an alert if certain requirements are not met
     * @param event
     * @throws IOException 
     */
    public void handleButtonSave(ActionEvent event) throws IOException{
        try {
            int id = Inventory.generateUniqueId();
            String name = textFieldName.getText();
            int stock = Integer.parseInt(textFieldStock.getText());
            double price = Double.parseDouble(textFieldPrice.getText());
            int max = Integer.parseInt(textFieldMax.getText());
            int min = Integer.parseInt(textFieldMin.getText());
        
            if (max <= min){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Max cannot be smaller or equal to than min.");
                alert.showAndWait();
            }else if (max < stock){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Stock cannot be bigger than max.");
                alert.showAndWait();
            }else if (min > stock) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Stock cannot be smaller than min.");
                alert.showAndWait();
            }else if (max < min){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Min cannot be bigger than max.");
                alert.showAndWait();
            } else {        
                Product newProduct = new Product(id, name, price, stock, min, max);
                if (allAssociatedParts.size() != 0){
                    for (Part p : allAssociatedParts){
                        newProduct.addAssociatedPart(p);
                    }
                }
            InventoryController.getInventory().addProduct(newProduct);
            Parent root = FXMLLoader.load(getClass().getResource("/View/MainScene.fxml"));
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            }   
        } catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Illegal entry");
            alert.setContentText("Illegal entry in text field, please look at the entries and try again.");
            alert.showAndWait();
        }
    }
    
    /**
     * Returns to the main scene and does not save any changes
     * @param event
     * @throws IOException 
     */
    public void handleButtonCancel(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScene.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
