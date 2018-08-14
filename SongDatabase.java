import javafx.scene.control.*; 
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
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
    private Label song; 
    private Label itemCode;
    private Label description; 
    private Label artist; 
    private Label album;
    private Label price; 
    
    // Initialize Combo box 
    ComboBox<String> cbSong; 

    private TextField itemCodeField;  // look at middle name small field!!!
    private TextField descriptionField; 
    private TextField artistField; 
    private TextField albumField; 
    private TextField priceField; 
    
    String songInfo = null;
    String itemCodeInfo = null; 
    String descriptionInfo = null; 
    String artistInfo = null;
    String albumInfo = null; 
    String priceInfo = null; 
    
    Playlist newVal;
    Playlist playlist;
    
    private boolean addClicked = true; 
    private boolean deleteClicked; 
    private boolean editClicked; 
    
    
    TreeMap < String, Playlist> playlistMap = new TreeMap < String, Playlist>();

    public void start(Stage myStage)
    {
        myStage.setTitle("Song Database");
        GridPane rootNode = new GridPane(); 
//        rootNode.setPadding(new Insets(10,10,10,10));
        rootNode.setHgap(5);
        rootNode.setVgap(5); // height of vertical gaps bw rows
        rootNode.setAlignment(Pos.CENTER);// Pos., .TOP_LEFT, 
        rootNode.setPadding(new Insets(30));
      
        Scene myScene = new Scene(rootNode, 500,500); // w, h of window
        
        // Assigning node values
        song        = new Label ("Select Song: "); 
        itemCode    = new Label ("Item Code: ");
        description = new Label ("Description: ");
        artist      = new Label("Artist: "); 
        album       = new Label("Albumn: "); // if N/A, assign "NONE" 
        price       = new Label("Price: ");
        
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
        cbSong = new ComboBox<String> (songField);
        
        // Set the default value
        cbSong.setValue("No Songs Selected");
        
        cbSong.setEditable(true); 
  
        
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
        
        
        add.setOnAction(new AddHandler());
        accept.setOnAction(new AcceptHandler());
        delete.setOnAction(new DeleteHandler());
        edit.setOnAction(new EditHandler()); 
        

        /**
         * COMBO BOX SELECTION LISTENER
         * This Change Listener fills up the song's properties
         * when user select the song from the combo box. 
         * 
         * Note, <String>, not <Playlist>!!!
         */
        cbSong.valueProperty().addListener( new ChangeListener <String>() 
        {
              public void changed(ObservableValue <? extends String> 
              changed, String oldVal, String newVal) 
              {
                  Playlist selectedSong = playlistMap.get(newVal);

                  String[] column = (String[]) selectedSong.toString().split(";");
                 
                  cbSong.setValue(column[0]); 
                  itemCodeField.setText(column[1]);
                  descriptionField.setText(column[2]);
                  artistField.setText(column[3]); 
                  albumField.setText(column[4]);
                  priceField.setText(column[5]); 
              }
        });
        

  
        // Arrange node in grid
        rootNode.add(song, 0,0);
        rootNode.add(cbSong, 1, 0, 4, 1); // col0, row1, toColIndex, toRowIndex
        
        rootNode.add(itemCode, 0, 1); // col1, row1 
        rootNode.add(itemCodeField, 1, 1, 4, 1); // col2, row1 //<===== MAKE SMALLER!! 
        
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
                // Clear field
                // Disable
                cbSong.setValue("");
                itemCodeField.setText(""); 
                descriptionField.setText(""); 
                artistField.setText(""); 
                albumField.setText(""); 
                priceField.setText(""); 
                
                // Enable: 
                accept.setDisable(false);
                cancel.setDisable(false);
                cbSong.setDisable(false);
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
                cbSong.setEditable(true);
                descriptionField.setEditable(true);
                artistField.setEditable(true);
                albumField.setEditable(true);
                priceField.setEditable(true);

                // Enable 
                cbSong.setDisable(false);
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

    
    class AcceptHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {   
            if(addClicked)
            {
                // Write to file
                writeToFile();   // <======== do we put to map first?? 
                // Add to treemap
                putToTreeMap();
                
                // Disable editing in combo box
                cbSong.setEditable(true);
                
                // Enable
                cbSong.setDisable(false); 
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
            
            if(deleteClicked)
            {
                removeFromTreeMap(); 
                removeFromFile(); 
                
                // Disable editing in combo box
                cbSong.setEditable(true);
                
                // Enable
                cbSong.setDisable(false); 
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
            if(editClicked)
            {
                // Find cbSong.getValue();
                // Delete cbSong.getValue(); 
                // get new values? 
                playlistMap.remove(itemCodeField.getText()); // might move to EditHandler()
                
                // Pull user input data
                songInfo = cbSong.getValue();
                itemCodeInfo = itemCodeField.getText(); 
                descriptionInfo = descriptionField.getText();
                artistInfo = artistField.getText();
                albumInfo = albumField.getText();
                priceInfo = priceField.getText();
                
                playlist = new Playlist(songInfo,  itemCodeInfo, 
                        descriptionInfo, artistInfo, albumInfo, priceInfo);
                
                
                playlistMap.put(cbSong.getValue(), playlist);
//                playlistMap.put(itemCodeField.getText(), playlist);
                
                // more like write everything from map to file again
                removeFromFile(); 
                
                // Enable
                cbSong.setDisable(false); 
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

    public void writeToFile()
    {  
        String fileName = "tester.txt"; 
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true)))
        {   
            // Pull user input data
            songInfo = cbSong.getValue();
            itemCodeInfo = itemCodeField.getText(); 
            descriptionInfo = descriptionField.getText();
            artistInfo = artistField.getText();
            albumInfo = albumField.getText();
            priceInfo = priceField.getText();

            System.out.println("This is the map size: " + playlistMap.size() );
            
            // write
            bw.write(songInfo + ";" + itemCodeInfo + ";" +
                   descriptionInfo + ";" + artistInfo + ";" + 
                   albumInfo + ";" + priceInfo); 
            bw.newLine();    
        }
        catch(IOException ioe) 
        {
            ioe.printStackTrace();
        } 
        // Add new song title to ComboBox
        cbSong.getItems().addAll(songInfo); 
        System.out.println("File created and written. Success");   
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
        playlist = new Playlist(songInfo,  itemCodeInfo, 
                descriptionInfo, artistInfo, albumInfo, priceInfo);
        
        
        playlistMap.put(cbSong.getValue(), playlist);
//        playlistMap.put(itemCodeField.getText(), playlist);
        cbSong.getItems().remove("No Songs Selected");
      
        System.out.println("playlistMap size: " + playlistMap.size()); 
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
                cbSong.setDisable(true); 
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
    
    public void removeFromTreeMap()
    {
        playlistMap.remove(cbSong.getValue());
//        playlistMap.remove(itemCodeField.getText());
        cbSong.getItems().remove("No Songs Selected"); 
        cbSong.getItems().remove(cbSong.getValue());
    }
    
    
    public void removeFromFile()
    { // After removing from the map, we will write the new map to the file. 
        
        String fileName = "tester.txt"; 
        // note, no "true" so we can overwrite the whole file!!!
        try(BufferedWriter bw = new BufferedWriter(
                new FileWriter(fileName))) 
        {   
            System.out.println("This is the map size: " + playlistMap.size() );
             // Clear all items in combo box

            for(Map.Entry<String, Playlist> p: playlistMap.entrySet())
            {
                bw.write("" +  p.getValue() );// our map's value has same content as line in the text file
                bw.newLine();
                bw.flush(); 
//                cbSong.getItems(); // add each item to combo box 
            }            
        }
        catch(IOException ioe) 
        {
            ioe.printStackTrace();
        } 
//        // Add new song title to ComboBox

//        cbSong.getItems().addAll(songInfo); 
//        System.out.println("File created and written. Success");  
        
    }
    
    public static void main(String[] args)
    {
        launch(args); 
    }

}
