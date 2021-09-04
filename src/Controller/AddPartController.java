/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller class for add part scene.
 * FUTURE ENHANCEMENT
 * Allow for multiple parts to be added at once
 * @author Caleb Lugo
 */
public class AddPartController implements Initializable{
    @FXML
    private Label labelMachineCompany;
    @FXML
    private RadioButton radioInHouse;
    @FXML
    private RadioButton radioOutsource;
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
    private TextField textFieldMachineCompany;
   
    /**
     * Empty
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {    
        //Empty
    }
    
    /**
     * Checks that the data meets specified requirements, if not display an alert
     * If so, save the part
     * @param event
     * @throws IOException 
     */
    public void handleButtonSave(ActionEvent event) throws IOException{
        try{
            /**
             * RUNTIME ERROR
             * The machine ID/ Company name parameter could not be placed with the rest of the variables as they take different inputs.
             * The logic for assigning the correct variable is at the end of the if else chain.
             */
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
                if (radioInHouse.isSelected()){
                    int machineID = Integer.parseInt(textFieldMachineCompany.getText());
                    InventoryController.getInventory().addPart(new InHouse(id, name, price, stock, min, max, machineID));
                } else if (radioOutsource.isSelected()){
                    String companyName = textFieldMachineCompany.getText();
                    InventoryController.getInventory().addPart(new Outsourced(id, name, price, stock, min, max, companyName));
                }
            Parent root = FXMLLoader.load(getClass().getResource("/View/MainScene.fxml"));
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            }
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Illegal entry");
            alert.setContentText("Illegal entry in text field, please look at the entries and try again.");
            alert.showAndWait();
        }
    }
    
    /**
     * Returns to the mains screen without saving anything
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
    
    /**
     * Sets the text for the machine ID if InHouse is selected
     * @param event 
     */
    public void handleRadioInHouseSelected(ActionEvent event){
        labelMachineCompany.setText("Machine ID");
    }
    
    /**
     * Sets the text for the company name if Outsourced is selected
     * @param event 
     */
    public void handleRadioOutsourcedSelected(ActionEvent event){
        labelMachineCompany.setText("Company Name");
    }
}
