import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeMap;

import javafx.application.Application; 
import javafx.event.ActionEvent; 
import javafx.event.EventHandler;
import javafx.scene.Scene; 
import javafx.scene.control.Button; 
import javafx.scene.control.Label;
import javafx.scene.control.TextField; 
import javafx.scene.layout.BorderPane; 
import javafx.stage.Stage; 


public class FileIOV3 extends Application 
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
                String s = null; 
                // Variable to display text read from file
                if(_clickMeMode)
                {
//                    FileInputStream in = null; 
//                    FileOutputStream out = null; 
                    BufferedReader br = null; 
                    BufferedWriter bw = null; 
                    String line = null; 

                    try
                    {
                        // Code to write to file
                        String text = textField.getText();  // textField is the user input shit !!!
//                        byte b[] = text.getBytes(); 
                        String outputFileName = "testingPlaylist.txt"; 
//                        out = new FileOutputStream(outputFileName);
//                        out.write(b);
//                        out.close(); 
                        bw = new BufferedWriter(new FileWriter (outputFileName, true));
                        bw.newLine(); 
                        bw.write(text);
                        bw.close();
                        
                        
                        
                        
                        // Clear textField
                        textField.setText("");
                        
                        // Code to read from file
                        String inputFileName = "testingPlaylist.txt"; 
//                        File inputFile = new File(inputFileName);  
//                        in = new FileInputStream(inputFile); 
//                        byte bt[] = new byte[(int) inputFile.length()]; // Create an array type byte, with the same size as the input length 
//                        in.read(bt);                                    // Read in this array
//                        s = new String(bt);                             // Convert this array into a string "s"
//                        in.close();
                        
                        TreeMap < String, Playlist> playlistMap = new TreeMap < String, Playlist>();

                        br = new BufferedReader(new FileReader (inputFileName));
                        while((line = br.readLine()) != null)
                        {
                            System.out.println(line);  
                            Playlist temp = new Playlist(); 

                            s = br.readLine(); 
//                            s =  br.toString();
                        }
                        br.close(); 
                        
                        
                    }
                    catch (IOException e)
                    {
                        System.out.println("Cannot access testingPlaylist.txt");     
                    }
                    finally
                    {
                        try
                        {
//                            in.close(); 
//                            out.close(); 
                            br.close();
                            bw.close();
                        }
                        catch(IOException e)
                        {
                            System.out.println("Cannot close");
                        }
                    }
                    // Clear text field
                    textField.setText("");  // TextField blank again         // IF "CLICK ME" IS SELECTED v
                    // Display text read from file
                    text.setText("Text retrieved from file: \n\n " + s );    // s is read from the file 
                    BPane.getChildren().remove(textField); 
                    button.setText("Click Again");
                    _clickMeMode = false;                                   // reset to unclicked 
                }
                else
                {
                    // Save text to file 
                    text.setText("Text to save to file: ");     // Label 
                    BPane.getChildren().add(textField);
                    textField.setText("");
                    button.setText("Click me");
                    _clickMeMode = true; 
                }
            }
        });
        
        // Set positions for each control in the BorderPane
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
    
    
    
    
    

