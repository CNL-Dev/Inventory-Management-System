/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.InHouse;
import Model.Outsourced;
import Model.Part;
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
 * Controller class for the Modify Part scene.
 * @author Caleb Lugo
 */
public class ModifyPartController implements Initializable{
    
    @FXML
    private Label labelMachineCompany;
    @FXML
    private RadioButton radioInHouse;
    @FXML
    private RadioButton radioOutsourced;
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
    private TextField textFieldMachineCompany;
        
    private Part partToModify;
    private int partIndex;
    
    /**
     * Sets the require data to edit the selected part
     * Fills in the text fields with the previous data
     * @param location
     * @param resources 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {    
        /**
         * FUTURE ENHANCEMENT
         * partToModify should be passed in either as an instance of InHouse or Outsourced to reduce 
         * code that checks for one or the other in this file.
         */
        partToModify = MainSceneController.getPartToBeModified();
        partIndex = MainSceneController.getPartIndex();
        
        /**
         * RUNTIME ERROR
         * Could not access either getMachineID or getCompanyName since the partToModify object is a part
         * not either of the inherited classes. The workaround for this was to create an inhouse object and assign 
         * partToModify to it. Once that was accomplished, simply call the appropriate getter function.
         */
        
        if (partToModify instanceof InHouse){
            radioInHouse.setSelected(true);
            InHouse inhouse = (InHouse)partToModify;
            textFieldMachineCompany.setText(String.valueOf(inhouse.getMachineID()));
        } else if (partToModify instanceof Outsourced){
            radioOutsourced.setSelected(true);
            Outsourced outsourced = (Outsourced)partToModify;
            textFieldMachineCompany.setText(String.valueOf(outsourced.getCompanyName()));
        }
        
        textFieldId.setText(String.valueOf(partToModify.getId()));
        textFieldName.setText(partToModify.getName());
        textFieldStock.setText(String.valueOf(partToModify.getStock()));
        textFieldPrice.setText(String.valueOf(partToModify.getPrice()));
        textFieldMax.setText(String.valueOf(partToModify.getMax()));
        textFieldMin.setText(String.valueOf(partToModify.getMin()));
    }
    
    /**
     * Saves the modified part and returns to the main scene
     * @param event
     * @throws IOException 
     */
    public void handleButtonSave(ActionEvent event) throws IOException{
        try {
            int id = partToModify.getId();
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
                    int machineCompany = Integer.parseInt(textFieldMachineCompany.getText());
                    InventoryController.getInventory().updatePart(partIndex, new InHouse(id, name, price, stock, min, max, machineCompany));
                } else if (radioOutsourced.isSelected()){
                    String machineCompany = textFieldMachineCompany.getText();
                    InventoryController.getInventory().updatePart(partIndex, new Outsourced(id, name, price, stock, min, max, machineCompany));
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
    
    /**
     * Sets the label to machine ID when the radio button is selected
     * @param event 
     */
    public void handleRadioInHouse(ActionEvent event){
        labelMachineCompany.setText("Machine ID");
    }
    
    /**
     * Sets the label to company name when the radio button is selected
     * @param event 
     */
    public void handleRadioOutsourced(ActionEvent event){
        labelMachineCompany.setText("Company name");
    }
}
