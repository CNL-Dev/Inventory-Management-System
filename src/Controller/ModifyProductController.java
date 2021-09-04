/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

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
 *This class contains the logic for the modification of products
 * RUNTIME ERROR: 
 * @author Caleb Lugo
 */
public class ModifyProductController implements Initializable{
    
    @FXML
    private TextField textFieldId;
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
    private Product productToModify;
    private int productIndex;
    
    /**
     * Gets the data required to modify the product
     * Sets all data for the table views as well as the observable lists
     * @param location
     * @param resources 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {       
        productToModify = MainSceneController.getProductToBeModified();
        productIndex = MainSceneController.getProductIndex();
        
        allParts.addAll(InventoryController.getInventory().getAllParts());   
        allAssociatedParts.addAll(productToModify.getAllAssociatedParts());
        
        //Assigns data to the columns
        tableColumnPartId.setCellValueFactory(new PropertyValueFactory<>("id")); 
        tableColumnPartName.setCellValueFactory(new PropertyValueFactory<>("name")); 
        tableColumnPartStock.setCellValueFactory(new PropertyValueFactory<>("stock")); 
        tableColumnPartPrice.setCellValueFactory(new PropertyValueFactory<>("price")); 
        tableViewPart.setItems(allParts);
        
        /**
         * RUNTIME ERROR
         * tableViewAssociatedPart displayed data for tableViewPart. The code below was copied and pasted 
         * from the code above and most of the variable names were changed except for line 111 which caused
         * the issue. This was fixed by giving it the appropriate name instead of setting items in tableViewPart twice.
         */
        tableColumnAssociatedPartId.setCellValueFactory(new PropertyValueFactory<>("id")); 
        tableColumnAssociatedPartName.setCellValueFactory(new PropertyValueFactory<>("name")); 
        tableColumnAssociatedPartStock.setCellValueFactory(new PropertyValueFactory<>("stock")); 
        tableColumnAssociatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price")); 
        tableViewAssociatedPart.setItems(allAssociatedParts);
        
        textFieldId.setText(String.valueOf(productToModify.getId()));
        textFieldName.setText(productToModify.getName());
        textFieldStock.setText(String.valueOf(productToModify.getStock()));
        textFieldPrice.setText(String.valueOf(productToModify.getPrice()));
        textFieldMax.setText(String.valueOf(productToModify.getMax()));
        textFieldMin.setText(String.valueOf(productToModify.getMin()));
    }
    
    /**
     * Handles the text field for parts
     * Checks the part name, if nothing was found check the ID
     * If nothing is still found, display an alert
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
     * Searches for a part via the ID
     * @param partId
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
     * Handles the add button for associated parts
     * If nothing is selected, display an alert
     * @param event
     * @throws IOException 
     */
    public void handleButtonAdd(ActionEvent event)throws IOException{
        /**
         * FUTURE ENHANCEMENT
         * Check for multiple associated parts. Technically, you can add the same part over and over again, but
         * perhaps the associated part list should display how many of each associated part there are as there
         * is an argument to be made for having the same part multiple times depending on the product.
         * Example: Having 2 brakes on a bicycle.
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
     * Removes a associated part from the product
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
     * Saves the modified part and returns to the main scene
     * Throws an alert if a condition is not met
     * @param event
     * @throws IOException 
     */
    public void handleButtonSave(ActionEvent event) throws IOException{
        try {
            int id = productToModify.getId();
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
            InventoryController.getInventory().updateProduct(productIndex, newProduct);
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
     * Returns to the main scene without saving any changes
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
