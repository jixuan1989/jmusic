/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package player.gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author sainthxd@gmail.com
 */
public class MainTopController implements Initializable{


//	@FXML private  ResourceBundle resources;
	@FXML private AnchorPane mainTopPane;
	@FXML private ImageView closeIcon;
	@FXML private ImageView upIcon;
	@FXML private ImageView minIcon;
	@FXML private ImageView settingIcon;
	@FXML private ImageView skinIcon;
	@FXML private ImageView brandIcon;
	@FXML private Label appLabel;

	private MainController father;
	private Stage stage;
	 double initX,initY;
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		System.err.println("--");
	}
	//when screen is dragged, translate it accordingly
	
	public void moveWindow(MouseEvent me){
		//System.out.println("moved:"+me.getSceneX()+","+me.getSceneY());
		if(this.stage==null){
			this.stage=father.getMainStage();	
		}
		 this.stage.setX(me.getScreenX() - initX);
         father.getPlayListStage().setX(stage.getX());
		 this.stage.setY(me.getScreenY() - initY);
         father.getPlayListStage().setY(stage.getY()+stage.getHeight());
    }

     //when mouse button is pressed, save the initial position of screen

	public void prepareForMoveWindow(MouseEvent me) {
		//System.out.println("pressed:"+me.getSceneX()+","+me.getSceneY());
		initX = me.getScreenX() - father.getMainStage().getX();
        initY = me.getScreenY() - father.getMainStage().getY();
       
	}

	public void closeApp(){
		father.close();
	} 
	public void upWindow(){

	}
	public void minWindow(){

	}
	public void openSetting(){

	}
	public void openSkinSetting(){

	}

	public void setFather(MainController mainController) {
		this.father=mainController;
	}

}
