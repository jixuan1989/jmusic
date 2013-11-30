package player.gui.custom;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.util.Duration;
import player.util.MyTimeUtil;
/**
 * @author sainthxd@gmail.com
 */
public class PlayListRightCell extends AnchorPane{
	static ResourceBundle i18nBundle = ResourceBundle.getBundle("main", new Locale("zh", "CN"));
	@FXML public ImageView loveIcon;
	@FXML public ImageView downloadIcon;
	@FXML public ImageView closeIcon;
	@FXML public ImageView localIcon;
	
	@FXML public Label songLabel;
	@FXML public Label timeLabel;

	@FXML public AnchorPane pane;
	
	public String songAddress;
	public int type;
	public Media media;
	//由于 scene builder的问题 我们无法可视化添加菜单，因此手动添加。 
	@FXML ContextMenu contextMenu;
	//以下是各种右键菜单。我们要根据不同的菜单类型加载不同的menuitem
	
	@FXML private MenuItem playItem;
	@FXML private MenuItem showDetailItem;
	@FXML private MenuItem deleteFromListItem;
	
	
	public PlayListRightCell(EditablePair<String, Integer> pair) {
		this();
		init(pair);
		//this.setMedia();
	}
	public PlayListRightCell() {
		super();
		FXMLLoader loader=new FXMLLoader(getClass().getResource("/player/gui/custom/playListRightListCell.fxml"),i18nBundle);
		loader.setRoot(this);
		loader.setController(this);
		try{
			loader.load();
		}catch(IOException e){
			throw new RuntimeException(e);
		}
		
	}
	public void init(EditablePair<String, Integer> pair) {
		this.songAddress=pair.getKey();
		this.type=pair.getValue();
		this.songLabel.setText(songAddress);
		//this.setMedia();
	}
	public void openContextMenu(){
		
	}
	
	public void showPlayList(MouseEvent e){
		if(e.getClickCount()==2){
			play();
		}
	}
	
	public void play(){
		
	}
	public void deleteFromList(){
		
	}
	public void showDetail(){
		
	}
	public void openFolder(){
		
	}
	public void tagLikeOrDislike(){
		if(loveIcon.getUserData()==null||!loveIcon.getUserData().equals("like")){
    		loveIcon.setImage(new Image(this.getClass().getResourceAsStream("/imgs/like.png")));
    		loveIcon.setUserData("like");
    		//DemoFXML.core.getCurrentSong().setLike(true);
    	}else{
    		loveIcon.setImage(new Image(this.getClass().getResourceAsStream("/imgs/dislike.png")));
    		loveIcon.setUserData("dislike");
    		//DemoFXML.core.getCurrentSong().setLike(false);
    	}
	}
	public void download(){
		
	}
	public void removeFromList(){
		
	}
	public void showIcons(){
		timeLabel.setVisible(false);
		if(type<10){
			localIcon.setVisible(true);
			loveIcon.setVisible(false);
			downloadIcon.setVisible(false);
		}else{
			loveIcon.setVisible(true);
			downloadIcon.setVisible(true);
			localIcon.setVisible(false);
		}
		closeIcon.setVisible(true);
	}
	public void showTime(){
		timeLabel.setVisible(true);
		localIcon.setVisible(false);
		loveIcon.setVisible(false);
		downloadIcon.setVisible(false);
		closeIcon.setVisible(false);
	}
	public void setMedia(){
		if(this.songAddress!=null)
		this.media=new Media(this.songAddress);
		media.getMetadata().addListener(new MapChangeListener<String, Object>() {                 
            public void onChanged(Change<? extends String, ? extends Object> change) {
                if (change.wasAdded()) {
                    if (change.getKey().equals("duration")) {
                        timeLabel.setText(MyTimeUtil.formatTime((Duration) media.getMetadata().get("duration")));
                    }
                }
            }
        });
	}

}
