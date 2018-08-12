import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javafx.application.Application; 
import javafx.event.ActionEvent; 
import javafx.event.EventHandler;
import javafx.scene.Scene; 
import javafx.scene.control.Button; 
import javafx.scene.control.Label;
import javafx.scene.control.TextField; 
import javafx.scene.layout.BorderPane; 
import javafx.stage.Stage; 


public class FileIO extends Application 
{
    TextField textField; 
    Label text, clicked; 
    Button button, clickButton; 
    BorderPane BPane; 
    private boolean _clickMeMode = true; 
    
    public void start(Stage myStage)
    {
        // Create GridPane
        BPane = new BorderPane(); 
        BPane.setId("grid-pane" +  BPane.getStyleClass().add("pane-style")); 
        
        // Create Scene and add Grid
        Scene scene = new Scene(BPane, 300, 200); 
//            scene.getStylesheets().add(this.getClass().getResource(
//                    "EssentialsJPL.css").toExternalForm()); 
        // Create the stage and add the scene
        myStage.setTitle("FileIO Application");
        myStage.setScene(scene);
        
        text = new Label("Text to save to file: ");
        textField = new TextField(); 
        textField.setMaxWidth(200); 
        button= new Button("Click Me!");
        button.setOnAction(new EventHandler<ActionEvent>() 
        {
            public void handle(ActionEvent event)
            {
                Object source = event.getSource(); 
                String s = null; 
                // Variable to display text read from file
                if(_clickMeMode)
                {
                    FileInputStream in = null; 
                    FileOutputStream out = null; 
                    try
                    {
                        // Code to write to file
                        String text = textField.getText(); 
                        byte b[] = text.getBytes(); 
                        String outputFileName = System.getProperty("user.home", 
                            File.separatorChar + "home"
                            + File.separatorChar + "monica")
                            + File.separatorChar + "text.txt";
                        out = new FileOutputStream(outputFileName);
                        out.write(b);
                        out.close(); 
                        // Clear textField
                        textField.setText("");
                        // Code to read from file
//                        String inputFileName = System.getProperty("user.home",
//                            File.separatorChar + "home"
//                            + File.separatorChar + "monica")
//                            + File.separatorChar + "text.txt";
                        String inputFileName = "testingPlaylist.txt"; 
                        File inputFile = new File(inputFileName);
                        in = new FileInputStream(inputFile); 
                        byte bt[] = new byte[(int) inputFile.length()]; 
                        in.read(bt); 
                        s = new String(bt); 
                        in.close();
                    }
                    catch (IOException e)
                    {
                        System.out.println("Cannot access text.txt");     
                    }
                    finally
                    {
                        try
                        {
                            in.close(); 
                            out.close(); 
                        }
                        catch(IOException e)
                        {
                            System.out.println("Cannot close");
                        }
                    }
                    // Clear text field
                    textField.setText("");
                    // Display text read from file
                    text.setText("Text retrieved from file: \n\n " + s);
                    BPane.getChildren().remove(textField); 
                    button.setText("Click Again");
                    _clickMeMode = false; 
                }
                else
                {
                    // Save text to file 
                    text.setText("Text to save to file: ");
                    BPane.getChildren().add(textField);
                    textField.setText("");
                    button.setText("Click me");
                    _clickMeMode = true; 
                }
            }
        });
        
        // Set psitions for each control in the BorderPane
        BPane.setTop(text);
        BPane.setCenter(textField);
        BPane.setBottom(button);
        
        // Show the scene
        myStage.show(); 
    }
    
    // main method
    public static void main(String [] args)
    {
        launch(args);
    }
        
        
        
        
        
        
        
        
        
        
        
             
}
    
    
    
    
    

