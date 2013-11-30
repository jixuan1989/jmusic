
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Test1 extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ObservableLists");
        Group root = new Group();
        Scene scene = new Scene(root, 400, 250, Color.WHITE);
        
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(10);
        gridpane.setVgap(10);
        
        Label candidatesLbl = new Label("Left");
        GridPane.setHalignment(candidatesLbl, HPos.CENTER);
        gridpane.add(candidatesLbl, 0, 0);
          
        Label heroesLbl = new Label("Right");
        gridpane.add(heroesLbl, 2, 0);
        GridPane.setHalignment(heroesLbl, HPos.CENTER);
        
        final ObservableList<String> lefts = FXCollections.observableArrayList("A","B","C");
        final ListView<String> leftListView = new ListView<String>(lefts);
        leftListView.setPrefWidth(150);
        leftListView.setPrefHeight(150);

        gridpane.add(leftListView, 0, 1);
        
        final ObservableList<String> rights = FXCollections.observableArrayList();
        final ListView<String> rightListView = new ListView<String>(rights);
        rightListView.setPrefWidth(150);
        rightListView.setPrefHeight(150);

        gridpane.add(rightListView, 2, 1);

        Button sendRightButton = new Button(">");
        sendRightButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String item = leftListView.getSelectionModel().getSelectedItem();
                if (item != null) {
                    leftListView.getSelectionModel().clearSelection();
                    lefts.remove(item);
                    rights.add(item);
                }
            }
        });
        
        Button sendLeftButton = new Button("<");
        sendLeftButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String item = rightListView.getSelectionModel().getSelectedItem();
                if (item != null) {
                    rightListView.getSelectionModel().clearSelection();
                    rights.remove(item);
                    lefts.add(item);
                }
            }
        });
        
        VBox vbox = new VBox(5);
        vbox.getChildren().addAll(sendRightButton,sendLeftButton);
        
        gridpane.add(vbox, 1, 1);
        GridPane.setConstraints(vbox, 1, 1, 1, 2,HPos.CENTER, VPos.CENTER);
       
        root.getChildren().add(gridpane);        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

   