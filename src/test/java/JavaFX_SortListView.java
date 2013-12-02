
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @web http://java-buddy.blogspot.com/
 */
public class JavaFX_SortListView extends Application {

    class MyObject {

        String day;
        int number;

        MyObject(String d, int n) {
            day = d;
            number = n;
        }

        String getDay() {
            return day;
        }

        int getNumber() {
            return number;
        }
    }
    List<MyObject> myList;

    //Create dummy list of MyObject
    private void prepareMyList() {
        myList = new ArrayList<MyObject>();
        myList.add(new MyObject("Sunday", 50));
        myList.add(new MyObject("Monday", 60));
        myList.add(new MyObject("Tuesday", 20));
        myList.add(new MyObject("Wednesday", 90));
        myList.add(new MyObject("Thursday", 30));
        myList.add(new MyObject("Friday", 62));
        myList.add(new MyObject("Saturday", 65));

        //sort myList
        Collections.sort(myList, comparatorMyObject_byDay);
    }
    
    //Comparator for String, by Day
    Comparator<? super MyObject> comparatorMyObject_byDay = new Comparator<MyObject>() {
        @Override
        public int compare(MyObject o1, MyObject o2) {
            return o1.getDay().compareToIgnoreCase(o2.getDay());
        }
    };
    
    //Comparator for int, by Number
    Comparator<? super MyObject> comparatorMyObject_byNumber = new Comparator<MyObject>() {
        @Override
        public int compare(MyObject o1, MyObject o2) {
            return o1.getNumber() - o2.getNumber();
        }
    };

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("http://java-buddy.blogspot.com/");

        prepareMyList();
        ListView<MyObject> listView = new ListView<MyObject>();
        ObservableList<MyObject> myObservableList = FXCollections.observableList(myList);
        listView.setItems(myObservableList);

        listView.setCellFactory(new Callback<ListView<MyObject>, ListCell<MyObject>>() {
            @Override
            public ListCell<MyObject> call(ListView<MyObject> p) {

                ListCell<MyObject> cell = new ListCell<MyObject>() {
                    @Override
                    protected void updateItem(MyObject t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getDay() + ":" + t.getNumber());
                        }
                    }
                };

                return cell;
            }
        });


        StackPane root = new StackPane();
        root.getChildren().add(listView);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


