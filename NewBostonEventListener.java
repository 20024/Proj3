import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button; 
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Changing a selection in the drop-down menu is not an 
 * "event", so we have to use a "listener".
 * @author thenewboston
 *
 */
public class NewBostonEventListener extends Application
{
    Stage window; 
    Scene scene;
    Button button; 

    public static void main(String[] args)
    {
        launch(args);
    }
    
    public void start(Stage primaryStage) throws Exception
    {
        window = primaryStage; 
        window.setTitle("ChoiceBox Demo");
        button = new Button("Click me");
        
        ChoiceBox<String> choiceBox = new ChoiceBox<>(); 
        
        //get Items returns the ObservableList object 
        // which you can add items to
        choiceBox.getItems().add("Apples");
        choiceBox.getItems().add("BAnanas");
        choiceBox.getItems().addAll("Bacon", "Ham", "Meatballs");
        choiceBox.setValue("Apples"); // set as default
        /**
         * Add a listener.
         * Listen for selection changes
         * 
         * getSelectionModel()-- drop down selects 1 items only
         * selectedItemProperty()-- the item user selected
         * addListener()-- sit on item and wait for something to happen
         *  -- 3 params (on left) body on right 
         *  --v: observable (property) 
         */
        choiceBox.getSelectionModel(
            ).selectedItemProperty(
                ).addListener( 
                    (v, oldValue, newValue) -> 
                    System.out.println(newValue)); 
  
//        // here's an event== when user clicks the button
//        button.setOnAction(e -> getChoice(choiceBox));
        
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(choiceBox, button); 
        
        scene = new Scene (layout, 300, 250);
        window.setScene(scene);
        window.show(); 
    }

    // To get the value of the selected item
    // Happens when user clicks the button 
//    private void getChoice(ChoiceBox<String> choiceBox)
//    {
//        String food = choiceBox.getValue();
//        System.out.println(food);
//    }
    
    
}
