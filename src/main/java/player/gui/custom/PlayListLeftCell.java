package player.gui.custom;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
/**
 * @author sainthxd@gmail.com
 */
public class PlayListLeftCell extends AnchorPane{
	static ResourceBundle i18nBundle = ResourceBundle.getBundle("main", new Locale("zh", "CN"));
	@FXML public ImageView listIcon;
	@FXML public Label listLabel;
	@FXML public TextField listText;
	private int type;//0: local 1:local but can not delete.  10: online 11:online but can not delete.
	
	@FXML ContextMenu contextMenu;
	//以下是各种右键菜单。我们要根据不同的菜单类型加载不同的menuitem
	@FXML private MenuItem refreshListItem;
	@FXML private MenuItem newLocalListItem;
	@FXML private MenuItem newOnlineListItem;
	@FXML private MenuItem deleteListItem;
	@FXML private MenuItem clearListItem;
	@FXML private MenuItem renameListItem;
	
	static int k=0;
	private EditablePair<String, Integer>  pair;//与lists中的items是同一个对象，这样能实时修改items中的值
	public PlayListLeftCell(EditablePair<String, Integer> pair) {
		this();
		init(pair);
	}
	public PlayListLeftCell(){
		super();
		System.out.println(k++);
		FXMLLoader loader=new FXMLLoader(getClass().getResource("/player/gui/custom/playListLeftListCell.fxml"),i18nBundle);
		loader.setRoot(this);
		loader.setController(this);
		try{
			loader.load();
		}catch(IOException e){
			throw new RuntimeException(e);
		}
		listLabel.textProperty().bind(listText.textProperty());
		
	}
	public void init(EditablePair<String, Integer> pair){
		this.pair=pair;
		this.type=pair.getValue();
		listText.setText(pair.getKey());
		if(type<10){
			listIcon.setImage(new Image(getClass().getResourceAsStream("/imgs/icon/m_local.png")));
		}else if(type<20){
			listIcon.setImage(new Image(getClass().getResourceAsStream("/imgs/icon/m_online.png")));
		}
	}
	
	public void openContextMenu(){
		System.out.println(listLabel.getText());
		if(type==1){
			contextMenu.getItems().remove(refreshListItem);
		}
		
		
	}
	public void showPlayList(MouseEvent e){
		if(e.getClickCount()==2){
			renameList();
		}
		else if(e.getButton()==MouseButton.PRIMARY){
			//TODO
		}
	}
	
	public void refreshList(){
		
	}
	public void addLocalList(){
		
	}
	public void addOnlineList(){
		
	}
	public void deleteList(){
		
	}
	public void clearList(){
		
	}
	
	public void renameList(){
		System.out.println("call rename");
		listText.setVisible(true);
	}
	
	public void rename(ActionEvent event){
		listText.setVisible(false);
		pair.setKey(listText.getText());
		//TODO 修改playlists文件
	}
}
