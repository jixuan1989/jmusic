import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @web http://java-buddy.blogspot.com/
 */
public class JavaFX_ListView extends Application {

    class MyObject {

        String day;
        int number;
        Color color;

        MyObject(String d, int n, Color c) {
            day = d;
            number = n;
            color = c;
        }

        String getDay() {
            return day;
        }

        int getNumber() {
            return number;
        }
        
        Color getColor(){
            return color;
        }
    }
    List<MyObject> myList;

    //Create dummy list of MyObject
    private void prepareMyList() {
        myList = new ArrayList<>();
        myList.add(new MyObject("Sunday", 50, Color.RED));
        myList.add(new MyObject("Monday", 60, Color.GREEN));
        myList.add(new MyObject("Tuesday", 20, Color.BLUE));
        myList.add(new MyObject("Wednesday", 90, Color.VIOLET));
        myList.add(new MyObject("Thursday", 30, Color.BLUEVIOLET));
        myList.add(new MyObject("Friday", 62, Color.AZURE));
        myList.add(new MyObject("Saturday", 65, Color.GOLD));
        myList.add(new MyObject("Sunday", 50, Color.RED));
        myList.add(new MyObject("Monday", 60, Color.GREEN));
        myList.add(new MyObject("Tuesday", 20, Color.BLUE));
        myList.add(new MyObject("Wednesday", 90, Color.VIOLET));
        myList.add(new MyObject("Thursday", 30, Color.BLUEVIOLET));
        myList.add(new MyObject("Friday", 62, Color.AZURE));
        myList.add(new MyObject("Saturday", 65, Color.GOLD));

        myList.add(new MyObject("Sunday", 50, Color.RED));
        myList.add(new MyObject("Monday", 60, Color.GREEN));
        myList.add(new MyObject("Tuesday", 20, Color.BLUE));
        myList.add(new MyObject("Wednesday", 90, Color.VIOLET));
        myList.add(new MyObject("Thursday", 30, Color.BLUEVIOLET));
        myList.add(new MyObject("Friday", 62, Color.AZURE));
        myList.add(new MyObject("Saturday", 65, Color.GOLD));

    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("http://java-buddy.blogspot.com/");

        prepareMyList();
        ListView<MyObject> listView = new ListView<>();
        ObservableList<MyObject> myObservableList = FXCollections.observableList(myList);
        listView.setItems(myObservableList);

        listView.setCellFactory(new Callback<ListView<MyObject>, ListCell<MyObject>>() {
            @Override
            public ListCell<MyObject> call(ListView<MyObject> p) {

                ListCell<MyObject> cell = new ListCell<MyObject>() {
                    @Override
                    protected void updateItem(MyObject t, boolean bln) {
                        super.updateItem(t, bln);
                        
                       // Rectangle rect = new Rectangle(100, 20);
                        if (t != null) {
                            setText(t.getDay() + ":" + t.getNumber());
                            Color col = t.getColor();
                            setTextFill(col);
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