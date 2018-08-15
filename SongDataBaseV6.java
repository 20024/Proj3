import javafx.scene.control.*; 
//import javafx.scene.*; 
//import javafx.geometry.*;
//import javafx.event.*; 
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;



public class SongDataBaseV6 extends Application
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

//    private TextField songField; 
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

    private boolean addClicked = true; 
    TreeMap < String, Song> songList = new TreeMap < String, Song>();

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
                "No Songs selected" );
        
        // Create combo box.
        cbSong = new ComboBox<String> (songField);
        
        // Set the default value
        cbSong.setValue("No Songs selected");
        
//        // Set the response label to indicate the default selection
//        response.setText("Selected Transport is " + 
//                cbSong.getValue());
        
//        /**
//         * allow user to edit selection
//         */
        cbSong.setEditable(true); // use for the "edit"? of prj3
//        
//        // Listen for action events on the combo box
//        cbSong.setOnAction(
//            new EventHandler<ActionEvent>()
//            {
//                public void handle(ActionEvent ae) 
//                {
//                    cbSong.getSelectionModel().getSelectedItem(); 
//                }
//            }
//        );
//        
        
        
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
        cbSong.setOnAction(new CBSongHandler()); // to display when toggle bw song titles
        
        

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
    
    
    class AcceptHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {            
            // Write to file
            writeToFile(); 
            
            // Add to treemap
            putToTreeMap();
            
            // Disable editing in combo box
            cbSong.setEditable(true);
            
            // Enable
            cbSong.setDisable(false); // CHANGED HERE!!!
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
    }

    public void writeToFile()
    {
        
        String fileName = "tester.txt"; 
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true)))
        {
            // Pull user input data
            Song songToSave = new Song(
                cbSong.getValue()
                , itemCodeField.getText()
                , descriptionField.getText()
                , artistField.getText()
                , albumField.getText()
                , priceField.getText()
                );

            System.out.println("This is the map size: " + songList.size() );

            // write
            bw.write( songToSave.toString() ); 
            bw.newLine();

            cbSong.getItems().remove("No Songs selected"); 
            cbSong.getItems().addAll( songToSave.getName() ); 
            System.out.println("File created and written. Success");   
        }
        catch(IOException ioe) 
        {
            ioe.printStackTrace();
        } 
        // Add new song title to ComboBox
    }
    
    public void putToTreeMap()
    {
        Song song = new Song(songInfo,  itemCodeInfo, 
                descriptionInfo, artistInfo,  
                albumInfo, priceInfo);
        
        songList.put(cbSong.getValue(), song);
       
        // Prints out what's in the map currently.
//        for (Map.Entry p: songList.entrySet())
//            System.out.println(p.getKey() + " : " +
//                    p.getValue());
        
        
        System.out.println("songList size: " + songList.size()); 
    }
    
    class CBSongHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        { 
//            songInfo = cbSong.getValue(); 
            getFromTreeMap();
        }
    }
    
    
    public void getFromTreeMap()
    {
        // Split up the key where user toggled to in ComboBox
        // Assign them to TextField Value and comboBox values 
        for (Map.Entry<String, Song> p: songList.entrySet())//contactMap.entrySet())
        //for(Contact c: contactMap.values())
        {
            System.out.println(p.getKey() + " : " +  // to print in console
                    p.getValue() );
            
            // split up the getValue() --- then assign to gui display ; 
        }      
    }
  
    public static void main(String[] args)
    {
        launch(args); 
    }

}