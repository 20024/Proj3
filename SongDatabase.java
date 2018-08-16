import javafx.scene.control.*;
import java.util.Scanner;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import javafx.application.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.beans.value.ChangeListener;


/**
 * 
 * 
 * 
 * @author LynHNguyen
 *
 */

public class SongDatabase extends Application
{
    // Initialize Buttons for user selection 
    private Button add; 
    private Button edit;
    private Button delete; 
    private Button accept;
    private Button cancel; 
    private Button exit; 
    
    // Initialize Labels & Textfields for user insert
    private Label name; 
    private Label itemCode;
    private Label description; 
    private Label artist; 
    private Label album;
    private Label price; 
    private Label message; 
    
    // Initialize Combo box 
    ComboBox<String> cbName; 

    private TextField itemCodeField; 
    private TextField descriptionField; 
    private TextField artistField; 
    private TextField albumField; 
    private TextField priceField; 
    
    String nameInfo; //= null;
    String itemCodeInfo; //= null; 
    String descriptionInfo;// = null; 
    String artistInfo;// = null;
    String albumInfo;// = null; 
    double priceInfo;// = 0.0; 
    
    Song newVal; // Used in cbName Listener
    Song song;
    
    private boolean addClicked; 
    private boolean deleteClicked; 
    private boolean editClicked; 
    Stage myStage; 
    static String fileName; 
    String args[];
    static String thisFile; 
    
    Scanner scanner = new Scanner(System.in); 
    
    TreeMap < String, Song> playlistMap = 
        new TreeMap < String, Song>();

    /**
     * 
     */
    
    public void start(Stage myStage)
    {
        myStage.setTitle("Song Database");
        GridPane rootNode = new GridPane(); 
        rootNode.setHgap(5);
        rootNode.setVgap(5); // height of vertical gaps between rows
        rootNode.setAlignment(Pos.CENTER); // Align center left 
        rootNode.setPadding(new Insets(30));
      
        Scene myScene = new Scene(rootNode, 550, 500); //500); // w, h of window
        
        // Assigning node values
        name        = new Label ("Select Song: "); 
        itemCode    = new Label ("Item Code: ");
        description = new Label ("Description: ");
        artist      = new Label("Artist: "); 
        album       = new Label("Albumn: "); // if N/A, assign "NONE" 
        price       = new Label("Price: ");
        message     = new Label(""); 
        
        itemCodeField     = new TextField();
        descriptionField  = new TextField(); 
        artistField       = new TextField(); 
        albumField        = new TextField(); // if N/A, assign "NONE" 
        priceField        = new TextField();
        
        add     = new Button("Add"); 
        edit    = new Button("Edit"); 
        delete  = new Button("Delete"); 
        accept  = new Button("Accept"); 
        cancel  = new Button("Cancel"); 
        exit    = new Button("Exit"); 
        
        // Set button width 
        add.setPrefWidth(80);
        edit.setPrefWidth(80);
        delete.setPrefWidth(80);
        accept.setPrefWidth(80);
        cancel.setPrefWidth(80);
        exit.setPrefWidth(60);
   
        // Create an ObservableList of entries for the combo box.
        ObservableList<String> songField = 
            FXCollections.observableArrayList(
                "No Songs Selected" );
        
        // Create combo box.
        cbName = new ComboBox<String> (songField);
        
        /**
         * Calling this opening of text file.
         */

        getPlaylist(thisFile);//args[0]); 
     
        // Determines how the initial stage should be displayed
        if (playlistMap.size() != 0 ) // If file is not empty
        {
            nonemptyFileDisplay();
        }
        else // If file is empty
        {
            emptyFileDisplay();
        }
        
        add.setOnAction(new AddHandler());
        accept.setOnAction(new AcceptHandler());
        delete.setOnAction(new DeleteHandler());
        edit.setOnAction(new EditHandler()); 
        cancel.setOnAction(new CancelHandler());
        exit.setOnAction(e -> Platform.exit());

        
        /**
         * COMBO BOX SELECTION LISTENER
         * This Change Listener fills up the song's properties
         * when user select the song from the combo box. 
         * 
         * Note, <String>, not <Song>!!!
         */
        cbName.valueProperty().addListener( new ChangeListener <String>() 
        {
            public void changed(ObservableValue <? extends String> 
            changed, String oldVal, String newVal) 
            {
                try {
                Song selectedSong = playlistMap.get(newVal);

                String[] column = 
                    selectedSong.toString().split(";");
             
                cbName.setValue(column[0]); 
                itemCodeField.setText(column[1]);
                descriptionField.setText(column[2]);
                artistField.setText(column[3]); 
                albumField.setText(column[4]);
                priceField.setText(column[5]); 
                }
                catch (Exception e)
                {
                    System.out.println(e);
                }
          }
        }) ;
      
      

        
        // Arrange node in grid
        rootNode.add(name, 0, 0);
        rootNode.add(cbName, 1, 0, 4, 1); // col0, row1, toColIndex, toRowIndex
        
        rootNode.add(itemCode, 0, 1); // col1, row1 
        rootNode.add(itemCodeField, 1, 1, 2, 1); // col2, row1 //<===== MAKE SMALLER!! 
        
        rootNode.add(description,  0, 2);
        rootNode.add(descriptionField, 1, 2, 4, 1);
        
        rootNode.add(artist, 0, 3);
        rootNode.add(artistField, 1 ,3, 4, 1);  
        
        rootNode.add(album, 0, 4); // col0, row1
        rootNode.add(albumField, 1, 4, 4, 1); // col1, row1
        
        rootNode.add(price, 0, 5); // col2, row1
        rootNode.add(priceField, 1, 5, 4, 1); //, 3, 0); 
        
        rootNode.add(add, 0, 14);//, 3, 0); 
        rootNode.add(edit, 1 ,14);
        rootNode.add(delete, 2, 14); // col0, row1
        rootNode.add(accept, 3, 14); // col1, row1
        rootNode.add(cancel, 4, 14); // col2, row1
        rootNode.add(exit, 2, 15);
        
        rootNode.add(message, 0, 18, 4, 1); //, 0, 3); // 0, 17
         
        myStage.setScene(myScene);
        myStage.show();
       
    }  
    
    class AddHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            addClicked = true;
            
            if(addClicked)
            {
                cbName.setEditable(true);
                // Clear field
                // Disable
                cbName.setValue("");
                itemCodeField.setText(""); 
                descriptionField.setText(""); 
                artistField.setText(""); 
                albumField.setText(""); 
                priceField.setText(""); 
                
                // Enable: 
                accept.setDisable(false);
                cancel.setDisable(false);
                cbName.setDisable(false);
                itemCodeField.setDisable(false);
                descriptionField.setDisable(false);
                artistField.setDisable(false);
                albumField.setDisable(false); // if N/A, assign "NONE" 
                priceField.setDisable(false);
                
                // Disable: 
                add.setDisable(true);
                edit.setDisable(true);
                delete.setDisable(true);
                exit.setDisable(true);        
            }
        }
    }
    
    class EditHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            editClicked = true;
            if(editClicked)
            {
                // Allow TextField to be editted
                cbName.setEditable(true);
                descriptionField.setEditable(true);
                artistField.setEditable(true);
                albumField.setEditable(true);
                priceField.setEditable(true);

                // Enable 
                cbName.setDisable(false);
                descriptionField.setDisable(false);
                artistField.setDisable(false);
                albumField.setDisable(false);
                priceField.setDisable(false);
                accept.setDisable(false);
                cancel.setDisable(false);
                
                // Disable
                itemCodeField.setDisable(true);
                add.setDisable(true);
                edit.setDisable(true);
                delete.setDisable(true);
                exit.setDisable(true);
            }
        }
    }
    // 1. delete from treemap
    // 2. delete from text file --write from treemap to same file
    class DeleteHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        { 
            deleteClicked = true; 
            if(deleteClicked)
            {
                // Enable
                accept.setDisable(false);
                cancel.setDisable(false);
                
                // Disable
                cbName.setDisable(true); 
                itemCodeField.setDisable(true);
                descriptionField.setDisable(true);
                artistField.setDisable(true);
                albumField.setDisable(true);
                priceField.setDisable(true); 
                add.setDisable(true);
                edit.setDisable(true);
                delete.setDisable(true);
                exit.setDisable(true);
            }
        }
    }

    class CancelHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {   
            if(addClicked)
            {
                addClicked = false; 
                defaultButtonDisplay(); 
            }
            if(editClicked)
            {
                editClicked = false;
                defaultButtonDisplay();
            }
            if(deleteClicked)
            {
                deleteClicked = false; 
                defaultButtonDisplay();
            }
        }
    }
           
    
    class AcceptHandler implements EventHandler<ActionEvent>
    {

        @Override
        public void handle(ActionEvent event)
        {   
            if(checkForEmptyField())
            {
                if(addClicked)
                {  
                    // Add to treemap
                    putToTreeMap();
                    // Write to file
                    writeToFile();   // <======== do we put to map first?? 
                    
                    // Disable editing in combo box
                    cbName.setEditable(true);
                    
                    // Enable
                    cbName.setDisable(false); 
                    add.setDisable(false);
                    edit.setDisable(false);
                    delete.setDisable(false);
                    exit.setDisable(false);
                    
                    // Disable
                    itemCodeField.setDisable(true);
                    descriptionField.setDisable(true);
                    artistField.setDisable(true);
                    albumField.setDisable(true);
                    priceField.setDisable(true);
                    accept.setDisable(true);
                    cancel.setDisable(true);  
                    
                    // Reset addClicked to false
                    addClicked = false; 
                
                }
            }
            
            if(checkForEmptyField())
            {
            
                if(deleteClicked)
                {
                    removeFromTreeMap(); 
                    removeFromFile(); 
                    
                    // Disable editing in combo box
                    cbName.setEditable(true);
                    
                    // Enable
                    cbName.setDisable(false); 
                    add.setDisable(false);
                    edit.setDisable(false);
                    delete.setDisable(false);
                    exit.setDisable(false);
                    
                    // Disable
                    itemCodeField.setDisable(true);
                    descriptionField.setDisable(true);
                    artistField.setDisable(true);
                    albumField.setDisable(true);
                    priceField.setDisable(true);
                    accept.setDisable(true);
                    cancel.setDisable(true);  
                    
                    // Reset addClicked to false
                    deleteClicked = false; 
                }
            }
            if(checkForEmptyField())
            {
                if(editClicked)
                {
                    // .remove(songInfo)
                    playlistMap.remove(cbName.getValue()); // (songgetText()); // might move to EditHandler()
                    try 
                    {
                        // Pull user input data
                 
                        song = new Song(cbName.getValue(),  
                                itemCodeField.getText(), 
                                descriptionField.getText(), 
                                artistField.getText(), 
                                albumField.getText(),
                                Double.parseDouble(priceField.getText()) );
                        
                        playlistMap.put(cbName.getValue(), song);
                        
                        // More like write everything from map to file again
                        removeFromFile(); 
                    }
                    catch(IllegalArgumentException iae)
                    {
                        System.out.println("Price needs to be a double! ");
                    }
                    
                    // Enable
                    cbName.setDisable(false); 
                    add.setDisable(false);
                    edit.setDisable(false);
                    delete.setDisable(false);
                    exit.setDisable(false);
                    
                    // Disable
                    itemCodeField.setDisable(true);
                    descriptionField.setDisable(true);
                    artistField.setDisable(true);
                    albumField.setDisable(true);
                    priceField.setDisable(true);
                    accept.setDisable(true);
                    cancel.setDisable(true);  
                    
                    editClicked = false; 
                }
            }
        }
    }
     
    public void writeToFile()
    {  
//        String fileName = "tester.txt"; 
        try(BufferedWriter bw = new BufferedWriter(
                new FileWriter(thisFile, true)))
        {   
            // Pull user input data
            System.out.println("This is the map size: " + playlistMap.size() );
  
            bw.write(cbName.getValue() + ";" + itemCodeField.getText() + ";" +
                    descriptionField.getText() + ";" + artistField.getText() + ";" + 
                    albumField.getText() + ";" + Double.parseDouble(priceField.getText())); 
            bw.newLine();    
        }
        catch(IllegalArgumentException iae)
        {
            System.out.println("Price needs to be a double! ");
        }
        catch(IOException ioe) 
        {
            ioe.printStackTrace();
        } 
        // Add new song title to ComboBox
        cbName.getItems().addAll(cbName.getValue()); 

        System.out.println("File created and written. Success");   
    }
    
    public void defaultButtonDisplay()
    {
        // Clear field
        cbName.setValue("");
        itemCodeField.setText(""); 
        descriptionField.setText(""); 
        artistField.setText(""); 
        albumField.setText(""); 
        priceField.setText(""); 
        
        // Enable
        cbName.setDisable(false); 
        add.setDisable(false);
        edit.setDisable(false);
        delete.setDisable(false);
        exit.setDisable(false);
        
        // Disable
        itemCodeField.setDisable(true);
        descriptionField.setDisable(true);
        artistField.setDisable(true);
        albumField.setDisable(true);
        priceField.setDisable(true);
        accept.setDisable(true);
        cancel.setDisable(true);  
    }
    
    
    public void nonemptyFileDisplay()
    {
        // Set the default value

        cbName.setValue("" + playlistMap.firstEntry().getKey());
        // Enable
        cbName.setDisable(false); 
        add.setDisable(false);
        edit.setDisable(false);
        delete.setDisable(false);
        exit.setDisable(false);
        
        // Disable
        itemCodeField.setDisable(true);
        descriptionField.setDisable(true);
        artistField.setDisable(true);
        albumField.setDisable(true);
        priceField.setDisable(true);
        accept.setDisable(true);
        cancel.setDisable(true);  
    }
    
    public void emptyFileDisplay()
    {
        // Set the default value
        cbName.setValue("No Songs Selected");

        cbName.setEditable(true); 
  
        // Initial State (empty)
        edit.setDisable(true);
        delete.setDisable(true);
        accept.setDisable(true);
        cancel.setDisable(true);
        
        itemCodeField.setDisable(true);
        descriptionField.setDisable(true);
        artistField.setDisable(true);
        albumField.setDisable(true); // if N/A, assign "NONE" 
        priceField.setDisable(true);
    }
    
    public boolean checkForEmptyField() 
    {
        if(cbName.getValue().isEmpty() |
            itemCodeField.getText().isEmpty() |
            descriptionField.getText().isEmpty() |
            artistField.getText().isEmpty() |
            priceField.getText().isEmpty())
        {
            message.setText("Empty Field!");
            return false;
        }
        else if(albumField.getText().isEmpty())
        {
            albumField.setText("None");
            message.setText("");
            return false; 
        }
        else if(!priceField.getText().isEmpty())
        {
            try 
            {
                Double.parseDouble(priceField.getText());
            }
            catch(NumberFormatException e)
            {
                message.setText("Need NUMERIC Price!");
                return false; 
            }
        }
        message.setText("");
        return true;   
    }
    
    /**
     * this method adds song entered by the user 
     * to the TreeMap playlistMap for "temp storage". 
     * 
     * We will use playlistMap when calling the information
     * by toggling between song titles. 
     */
    public void putToTreeMap()
    {
        song = new Song(cbName.getValue(),  
                itemCodeField.getText(), 
                descriptionField.getText() , 
                artistField.getText(), 
                albumField.getText(), 
                Double.parseDouble(priceField.getText()));

        playlistMap.put(cbName.getValue(), song);
        cbName.getItems().remove("No Songs Selected");
      
        System.out.println("playlistMap size: " + playlistMap.size()); 
    }
    
    public void removeFromTreeMap()
    {
        playlistMap.remove(cbName.getValue());

        // remove from drop down display
        cbName.getItems().remove("No Songs Selected"); 
        cbName.getItems().remove(cbName.getValue());
    }
    
    public void removeFromFile()
    { // After removing from the map, we will write the new map to the file. 
        // note, no "true" so we can overwrite the whole file!!!
        try(BufferedWriter bw = new BufferedWriter(
                new FileWriter(thisFile))) 
        {   
            System.out.println("This is the map size: " 
                + playlistMap.size());

             // Clear all items in combo box

            for(Map.Entry<String, Song> p: playlistMap.entrySet())
            {
                bw.write("" +  p.getValue() ); // our map's value has same content as line in the text file
                bw.newLine();
                bw.flush(); 
            }            
        }
        catch(IOException ioe) 
        {
            ioe.printStackTrace();
        } 
    }
    
    
    
    public static void main(String args[])
    {      
        System.out.println(args.length);  // args of 1
        System.out.println("args[0] is " + args[0]);
        thisFile = args[0]; 
        
        System.out.println("thisFile is : " + thisFile);
        System.out.println("args is : " + args);

        launch(thisFile); 
    }


    
    
    
    
    
//  System.out.println("Please input the file name, including .txt");
//  fileName = scanner.next();
    
    public void getPlaylist(String args)
    {
        String line = null; 
        
        try(BufferedReader br = 
            new BufferedReader(new FileReader(thisFile)))
        {
            try
            {
            while((line = br.readLine()) != null)
            {           
                System.out.println(line);  
                String[] column         = line.split(";");
                nameInfo         = column[0].trim(); 
                itemCodeInfo     = column[1].trim(); 
                descriptionInfo  = column[2].trim();
                artistInfo       = column[3].trim();
                albumInfo        = column[4].trim();
                priceInfo        = Double.parseDouble(column[5].trim());
                
                Song song = new Song(nameInfo,itemCodeInfo, 
                        descriptionInfo, artistInfo,  
                        albumInfo, priceInfo);
                
                playlistMap.put(nameInfo, song);   
                
                cbName.getItems().add(nameInfo); 
            }}
            catch(NullPointerException npe)
            {
                System.out.println("Null exception between here");
         
            }
            cbName.getItems().remove("No Songs Selected");
            
            System.out.println("This is the map size: " + playlistMap.size() );
            
            // Read in all that's in text file 
            // and add it to map 
            cbName.valueProperty().addListener( new ChangeListener <String>() 
            {
                  public void changed(ObservableValue <? extends String> 
                  changed, String oldVal, String newVal) 
                  {
                      Song song = playlistMap.get(newVal);

                      String[] column = (String[]) song.toString().split(";");
                     
                      cbName.setValue(column[0]); 
                      itemCodeField.setText(column[1]);
                      descriptionField.setText(column[2]);
                      artistField.setText(column[3]); 
                      albumField.setText(column[4]);
                      priceField.setText(column[5]); 
                  }
            });

        }
        catch(IOException e)
        {
            
            System.out.println("File does not exist");
            System.out.println("Would you like to create a new file? (Y/N)");
            String choice = scanner.next(); 
            if(choice.equalsIgnoreCase("y")) 
            {    
                System.out.println("What is the name of this new playlist (.txt)? ");
                String newFileName = scanner.next(); 
                fileName = newFileName; 
                thisFile = fileName; 
            }
            else
            {
                System.exit(0);
            } 
        } 
    }

    
}
    


