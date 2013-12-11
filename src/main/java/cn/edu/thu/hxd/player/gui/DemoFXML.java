/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.edu.thu.hxd.player.gui;
/**
 * @author sainthxd@gmail.com
 */
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import cn.edu.thu.hxd.player.PlayerCore;
import player.gui.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author hxd
 */
public class DemoFXML extends Application {
   public static PlayerCore core;
    @Override
    public void start(Stage stage) throws Exception {
    	core=new PlayerCore();
        ResourceBundle i18nBundle = ResourceBundle.getBundle("main", new Locale("zh", "CN"));
       // Locale.CHINA;
        URL url=getClass().getResource("/main.fxml");
        FXMLLoader loader=new FXMLLoader(url, i18nBundle);
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.TRANSPARENT);       
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show(); 
        //调出最外层的controller 将stage注册给他。
        MainController controller=loader.getController();
        controller.setMainStage(stage);
        
        //打开playlist界面。目前按照百度音乐的做法，这个界面是没有关闭按钮的。也无法和主stage分离。
        Stage playListStage =new Stage(StageStyle.TRANSPARENT);
        loader=new FXMLLoader(getClass().getResource("/playList.fxml"),i18nBundle);
        root=(Parent) loader.load();
        Scene  playListScene=new Scene(root);
        playListStage.setScene(playListScene);
        playListStage.setX(stage.getX());
        playListStage.setY(stage.getY()+stage.getHeight());
        playListStage.show();
        //将playlistStage给最外层的controller
        controller.setPlayListStage(playListStage);
        
      }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
