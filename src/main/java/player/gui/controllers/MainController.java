/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package player.gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author sainthxd@gmail.com
 */
public class MainController implements Initializable {
    
//    @FXML
//    private Label label;
//    
//    @FXML
//    private void handleButtonAction(ActionEvent event) {
//        System.out.println("You clicked me!");
//        label.setText("Hello World!");
//    }
	@FXML private AnchorPane topPane;
	@FXML private AnchorPane centerPane;
	@FXML private AnchorPane bottomPane;
	@FXML private MainTopController topPaneController;
	@FXML private MainCenterController centerPaneController;
	@FXML private MainBottomController bottomPaneController;
	
	private Stage mainStage;
	private Stage playListStage;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

		topPaneController.setFather(this);
        centerPaneController.setFather(this);
        bottomPaneController.setFather(this);
    }
	public void setMainStage(Stage stage) {
		this.mainStage=stage;
	}
	public Stage getMainStage(){
		return mainStage;
	}
	/**
	 * 关闭程序，先进行必要的信息保存，然后退出程序
	 */
	public void close() {
		// TODO 保存信息 或者进行提示
		System.exit(0);
		
	}
	public Stage getPlayListStage() {
		return playListStage;
	}
	public void setPlayListStage(Stage playListStage) {
		this.playListStage = playListStage;
	}
	
    
}
