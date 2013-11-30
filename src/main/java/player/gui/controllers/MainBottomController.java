/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package player.gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
/**
 * @author sainthxd@gmail.com
 */
public class MainBottomController implements Initializable{

    private MainController father;
   // @FXML private  ResourceBundle resources;
    @FXML private TextField textField;
    @FXML private Button clearButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       System.out.println("");
       textField.textProperty().addListener(new ChangeListener<String>() {
           @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
               clearButton.setVisible(textField.getText().length() != 0);
           }
       });
    	
    }
    
    public void clearSearchText(ActionEvent actionEvent) {
    	textField.setText("");
    	textField.requestFocus();
    }

	public void setFather(MainController mainController) {
		this.father=mainController;
	}
}