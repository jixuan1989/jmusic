/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package player.gui.controllers;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import player.gui.DemoFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * @author sainthxd@gmail.com
 */
public class MainCenterController implements Initializable{

    private MainController father;
   // @FXML private  ResourceBundle resources;
    
    @FXML private AnchorPane volumePane;
    @FXML private ImageView volumeIcon;
    @FXML private Slider volumeSlider;
    
    @FXML private ToggleButton qualityToggle;
    @FXML private ImageView loveIcon;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }
	public void setFather(MainController mainController) {
		this.father=mainController;
	}
    public void showVolumePane(MouseEvent me){
    	volumePane.setVisible(true);
    	volumeSlider.setVisible(true);
    }
    public void mayHideVolumePane(MouseEvent me){
    	
    	volumePane.setVisible(false);
		volumeSlider.setVisible(false);
    }
    public void changeQuality(ActionEvent e){
    	//DemoFXML.core.getConfig().setIsHighQuality(qualityToggle.isSelected());
    }
    //
    public void changeLoveStatus(){
    	if(loveIcon.getUserData()==null||!loveIcon.getUserData().equals("like")){
    		loveIcon.setImage(new Image(this.getClass().getResourceAsStream("/imgs/like.png")));
    		loveIcon.setUserData("like");
    		//DemoFXML.core.getCurrentSong().setLike(true);
    	}else{
    		loveIcon.setImage(new Image(this.getClass().getResourceAsStream("/imgs/dislike.png")));
    		loveIcon.setUserData("dislike");
    		//DemoFXML.core.getCurrentSong().setLike(false);
    	}
    	//TODO
    }
}
