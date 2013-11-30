package player.gui.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import player.gui.custom.EditablePair;
import player.gui.custom.PlayListLeftCell;
import player.gui.custom.PlayListRightCell;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.util.Callback;
import javafx.util.Pair;
/**
 * @author sainthxd@gmail.com
 */
public class PlaylistController implements Initializable{
	@FXML private ListView<EditablePair<String, Integer>> leftList;//pair <list_name, list_type>
	@FXML private ListView<EditablePair<String, Integer>> rightList;
	@FXML private Button addMusicButton;
	@FXML private Button sortMusicButton;
	@FXML private SplitPane splitPane;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//splitPane.setDisable(true);
		// TODO Auto-generated method stub
		leftList.setCellFactory(new Callback<ListView<EditablePair<String, Integer>>, ListCell<EditablePair<String, Integer>>>() {
			@Override
			public ListCell<EditablePair<String, Integer>> call(ListView<EditablePair<String, Integer>> param) {
				ListCell<EditablePair<String, Integer>> cell=new ListCell<EditablePair<String, Integer>>(){
					@Override
					protected void updateItem(EditablePair<String, Integer> arg0, boolean arg1) {
						// TODO Auto-generated method stub
						super.updateItem(arg0, arg1);

						PlayListLeftCell myCell=null;
						if(this.getGraphic() instanceof PlayListLeftCell){
							myCell= (PlayListLeftCell)getGraphic();
							if( (arg0!=null&&!arg0.getKey().equals(myCell.listText))){
								myCell.init(arg0);
							}
						}else{
							if(arg0!=null){
								myCell=new PlayListLeftCell(arg0);
								setGraphic(myCell);
							}else{
								myCell=new PlayListLeftCell();
								setGraphic(myCell);
							}
						}
						

					}
				};
				return cell;
			}
		});
		rightList.setCellFactory(new Callback<ListView<EditablePair<String, Integer>>, ListCell<EditablePair<String, Integer>>>() {
			@Override
			public ListCell<EditablePair<String, Integer>> call(ListView<EditablePair<String, Integer>> param) {
				ListCell<EditablePair<String, Integer>> cell=new ListCell<EditablePair<String, Integer>>(){
					@Override
					protected void updateItem(EditablePair<String, Integer> arg0, boolean arg1) {
						// TODO Auto-generated method stub
						super.updateItem(arg0, arg1);

						PlayListRightCell myCell=null;
						if(this.getGraphic() instanceof PlayListRightCell){
							myCell= (PlayListRightCell)getGraphic();
							if( (arg0!=null&&!arg0.getKey().equals(myCell.songAddress))){
								myCell.init(arg0);
							}
						}else{
							if(arg0!=null){
								myCell=new PlayListRightCell(arg0);
								setGraphic(myCell);
							}else{
								myCell=new PlayListRightCell();
								setGraphic(myCell);
							}
						}
						

					}
				};
				return cell;
			}
		});
		testInitLeft(leftList);
		testInitLeft(rightList);
	}

	private void testInitLeft(ListView<EditablePair<String, Integer>> list) {
		ObservableList<EditablePair<String, Integer>> items=list.getItems();
		items.add(new EditablePair<String, Integer>("hello",0));
		items.add(new EditablePair<String, Integer>("world1",10));
		items.add(new EditablePair<String, Integer>("world2",10));
		items.add(new EditablePair<String, Integer>("world3",10));
		items.add(new EditablePair<String, Integer>("world4",10));
		items.add(new EditablePair<String, Integer>("world5",10));
		items.add(new EditablePair<String, Integer>("world6",10));
		items.add(new EditablePair<String, Integer>("world7",10));
		items.add(new EditablePair<String, Integer>("world8",10));
		items.add(new EditablePair<String, Integer>("world9",10));
		items.add(new EditablePair<String, Integer>("world10",10));
		items.add(new EditablePair<String, Integer>("world11",10));
		//items.add(new Pair<String, Integer>("world12",10));
		items.add(new EditablePair<String, Integer>("world13",10));
		items.add(new EditablePair<String, Integer>("world14",10));
		items.add(new EditablePair<String, Integer>("world15",10));
		items.add(new EditablePair<String, Integer>("world16",10));
		items.add(new EditablePair<String, Integer>("world17",10));
		items.add(new EditablePair<String, Integer>("world18",10));
		items.add(new EditablePair<String, Integer>("world5",10));
		items.add(new EditablePair<String, Integer>("world6",10));
		items.add(new EditablePair<String, Integer>("world7",10));
		items.add(new EditablePair<String, Integer>("world8",10));
		items.add(new EditablePair<String, Integer>("world9",10));
		items.add(new EditablePair<String, Integer>("world10",10));
		items.add(new EditablePair<String, Integer>("world11",10));
		//items.add(new Pair<String, Integer>("world12",10));
		items.add(new EditablePair<String, Integer>("world13",10));
		items.add(new EditablePair<String, Integer>("world14",10));
		items.add(new EditablePair<String, Integer>("world15",10));
		items.add(new EditablePair<String, Integer>("world16",10));
		items.add(new EditablePair<String, Integer>("world17",10));
		items.add(new EditablePair<String, Integer>("world18",10));
		items.add(new EditablePair<String, Integer>("world5",10));
		items.add(new EditablePair<String, Integer>("world6",10));
		items.add(new EditablePair<String, Integer>("world7",10));
		items.add(new EditablePair<String, Integer>("world8",10));
		items.add(new EditablePair<String, Integer>("world9",10));
		items.add(new EditablePair<String, Integer>("world10",10));
		items.add(new EditablePair<String, Integer>("world11",10));
		//items.add(new Pair<String, Integer>("world12",10));
		items.add(new EditablePair<String, Integer>("world13",10));
		items.add(new EditablePair<String, Integer>("world14",10));
		items.add(new EditablePair<String, Integer>("world15",10));
		items.add(new EditablePair<String, Integer>("world16",10));
		items.add(new EditablePair<String, Integer>("world17",10));
		items.add(new EditablePair<String, Integer>("world18",10));
		//items.add(new Pair<String, Integer>("world19",10));
	}

	public void addMusic(){

	}
	public void sortMusic(){

	}
}
