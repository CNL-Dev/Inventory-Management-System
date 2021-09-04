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
 * Controller for the main scene window. This class controls the logic for the main scene and the logic for opening other scenes
 * RUNTIME ERROR: Scenes could not communicate inventory data between them. This was solved by creating a InventoryController class 
 * That creates an instance of the Inventory class in the main program. As long as the program is running the Inventory object
 * will continue to exist, and allow other controllers to access its data. Check InventoryController.java for the code of this object.
 * FUTURE ENHANCEMENT: Modify the search fields to allow for non case sensitive browsing. The current search fields are case sensitive.
 * 
 * @author Caleb Lugo
 */
public class MainSceneController implements Initializable {
    
    @FXML
    private TableView<Part> tableViewPart;
    @FXML
    private TableView<Product> tableViewProduct;
    @FXML
    private TextField textFieldParts;
    @FXML
    private TextField textFieldProducts;
    @FXML
    private TableColumn tableColumnPartId;
    @FXML
    private TableColumn tableColumnPartName;
    @FXML
    private TableColumn tableColumnPartInv;
    @FXML
    private TableColumn tableColumnPartPrice;
    @FXML
    private TableColumn tableColumnProductId;
    @FXML
    private TableColumn tableColumnProductName;
    @FXML
    private TableColumn tableColumnProductInv;
    @FXML
    private TableColumn tableColumnProductPrice;
    
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    public static Part partToBeModified;
    private Part partToBeDeleted;
    public static Product productToBeModified;
    private Product productToBeDeleted;
    
    public static int partIndex;
    public static int productIndex;
   
    /**
     * Upon initialization, allParts and allProducts set their data equal to the inventory
     * All table columns and table views assign themselves to the appropriate data
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        
        allParts.setAll(InventoryController.getInventory().getAllParts());
        allProducts.setAll(InventoryController.getInventory().getAllProducts()); 
                
        //Assigns data to the columns
        tableColumnPartId.setCellValueFactory(new PropertyValueFactory<>("id"));       
        tableColumnPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        tableColumnPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableViewPart.setItems(allParts);
        
        tableColumnProductId.setCellValueFactory(new PropertyValueFactory<>("id"));        
        tableColumnProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnProductInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        tableColumnProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableViewProduct.setItems(allProducts);
    }    
    
    /**
     * Searches for a part via the part name or the part id
     * Displays an alert if nothing was found
     * @param event 
     */
    public void handlePartsTextField(ActionEvent event){
        String pTextField = textFieldParts.getText();
        
        ObservableList<Part> parts = InventoryController.getInventory().lookupPart(pTextField);
        
        //If the item was not found, try the id
        if (parts.size() == 0){
            try{
                    int id = Integer.parseInt(pTextField);
                    Part p = InventoryController.getInventory().lookupPart(id);
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
     * Searches for a product via the product name or the product id
     * Displays an alert if nothing was found
     * @param event 
     */
    public void handleProductsTextField(ActionEvent event){
        String pTextField = textFieldProducts.getText();
        
        ObservableList<Product> products = InventoryController.getInventory().lookupProduct(pTextField);
        
        //If the item was not found, try the id
        if (products.size() == 0){
            try{
                int id = Integer.parseInt(pTextField);
                Product p = InventoryController.getInventory().lookupProduct(id);
                if (p != null){
                    products.add(p);
                } 
            } catch (NumberFormatException e){
                //Empty
            }
        }    
        
        //If the item is still not found, display an error window
        if (products.size() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No results found");
            alert.setContentText("This item does not exist.");
            alert.showAndWait();
        }
        
        tableViewProduct.setItems(products);
    }
    
    /**
     * Opens the add part window when the add part button is clicked
     * @param event
     * @throws IOException 
     */
    public void handleButtonAddPart(ActionEvent event) throws IOException{        
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddPartScene.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();   
    }
    
    /**
     * Opens the modify part window when a part is selected and the modify part button is pressed
     * Displays an alert if no part was selected
     * @param event
     * @throws IOException 
     */
    public void handleButtonModifyPart(ActionEvent event) throws IOException{
        partToBeModified = tableViewPart.getSelectionModel().getSelectedItem();
        partIndex = InventoryController.getInventory().getAllParts().indexOf(partToBeModified);
        
        if(partToBeModified == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No part selected");
            alert.setContentText("There is no part selected.");
            alert.showAndWait();
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("/View/ModifyPartScene.fxml"));
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    
    /**
     * Deletes the selected part when the delete button is clicked
     * Displays an alert if nothing is selected
     * @param event 
     */
    public void handleButtonDeletePart(ActionEvent event){
        partToBeDeleted = tableViewPart.getSelectionModel().getSelectedItem();
        
        if (partToBeDeleted == null){
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("No part selected");
            alert1.setContentText("There are no parts selected.");
            alert1.showAndWait();
        } else if (partToBeDeleted != null){
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
            alert2.setTitle("Delete part");
            alert2.setContentText("Are you sure you would like to delete this?");
            Optional<ButtonType> answer = alert2.showAndWait();
            if (answer.get() == ButtonType.OK){
                InventoryController.getInventory().deletePart(partToBeDeleted);
                allParts.remove(partToBeDeleted);
                tableViewPart.setItems(allParts);
            }
        }
    }
    
    /**
     * Opens the add product window when the add product button is clicked
     * @param event
     * @throws IOException 
     */
    public void handleButtonAddProduct(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddProductScene.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Opens the modify product window when a product is selected and the modify product button is pressed
     * Displays an alert if no product was selected
     * @param event
     * @throws IOException 
     */
    public void handleButtonModifyProduct(ActionEvent event) throws IOException{
        productToBeModified = tableViewProduct.getSelectionModel().getSelectedItem();
        productIndex = InventoryController.getInventory().getAllProducts().indexOf(productToBeModified);
        
        if (productToBeModified == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No product selected");
            alert.setContentText("There is no product selected.");
            alert.showAndWait();
        } else {        
            Parent root = FXMLLoader.load(getClass().getResource("/View/ModifyProductScene.fxml"));
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    
    /**
     * Deletes the selected product when the delete button is clicked
     * Displays an alert if there are associated parts with the product
     * Displays an alert if nothing is selected
     * @param event 
     */
    public void handleButtonDeleteProduct(ActionEvent event){
        productToBeDeleted = tableViewProduct.getSelectionModel().getSelectedItem();
        
        if (productToBeDeleted == null){
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("No product selected");
            alert1.setContentText("There are no products selected.");
            alert1.showAndWait();
        } else if (productToBeDeleted != null){
            if (productToBeDeleted.getAllAssociatedParts().size() != 0){
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Associated Parts");
                alert2.setContentText("Please delete associated parts with the modify product screen before deleting a product.");
                alert2.showAndWait();
            } else {            
                Alert alert3 = new Alert(Alert.AlertType.CONFIRMATION);
                alert3.setTitle("Delete product");
                alert3.setContentText("Are you sure you would like to delete this?");
                Optional<ButtonType> answer = alert3.showAndWait();
                if (answer.get() == ButtonType.OK){
                    InventoryController.getInventory().deleteProduct(productToBeDeleted);
                    allProducts.remove(productToBeDeleted);
                    tableViewProduct.setItems(allProducts);  
                }
            }
        }
    }
    
    /**
     * When the exit button is clicked, close the program
     * @param event 
     */
    public void handleButtonExit(ActionEvent event){
        System.exit(0);
    }    
    
    /**
     * @return partToBeModified
     */
    public static Part getPartToBeModified(){
        return partToBeModified;
    }
    
    /**
     * @return productToBeModified
     */
    public static Product getProductToBeModified(){
        return productToBeModified;
    }
    
    /**
     * @return partIndex
     */
    public static int getPartIndex(){        
        return partIndex;
    }
    
    /*
     * @return productIndex
     */
    public static int getProductIndex(){
        return productIndex;
    }
}