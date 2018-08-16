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
 * This program prompts the user for a playlist stores 
 * in a text file. If the playlist does not exist, it allows 
 * the user to create a new playlist. It incorporates javaFX
 * as the user interface. This program allows the user to 
 * add, edit or delete songs from the file using Button 
 * and ComboBox. For each song action, it updates the
 * treemap called playlistMap and it updates the file variable,
 * thisFile. 
 * 
 * date: August 16, 2018
 * assignment: Project 3
 * class: EN.605.201.82
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
    
    // Initialize Labels 
    private Label name; 
    private Label itemCode;
    private Label description; 
    private Label artist; 
    private Label album;
    private Label price; 
    private Label message; 
    
    // Initialize Combo box 
    ComboBox<String> cbName; 

    // Initialize Textfields for user insert
    private TextField itemCodeField; 
    private TextField descriptionField; 
    private TextField artistField; 
    private TextField albumField; 
    private TextField priceField; 
    
    String nameInfo; 
    String itemCodeInfo; 
    String descriptionInfo;
    String artistInfo;
    String albumInfo; 
    double priceInfo;
    
    private boolean addClicked; 
    private boolean deleteClicked; 
    private boolean editClicked; 
    
    Song newVal; // Used in cbName Listener
    Song song;
    
    Stage myStage; 
    String args[];
    static String fileName; 
    static String thisFile; 
    
    Scanner scanner = new Scanner(System.in); 
    
    TreeMap < String, Song> playlistMap = 
        new TreeMap < String, Song>();

    /**
     * The start() method sets the rootNodes for our data, 
     * calls in the getPlaylist(), create event listeners
     * and show the scene of our program. 
     */
    
    public void start(Stage myStage)
    {
        myStage.setTitle("Song Database");
        GridPane rootNode = new GridPane(); 
        
        // Set window's alignments
        rootNode.setHgap(5);
        rootNode.setVgap(5); // h and w between rows
        rootNode.setAlignment(Pos.CENTER); // Align center left 
        rootNode.setPadding(new Insets(30));
      
        Scene myScene = new Scene(rootNode, 550, 500);// w, h of window
        
        // Assigning node values
        name        = new Label ("Select Song: "); 
        itemCode    = new Label ("Item Code: ");
        description = new Label ("Description: ");
        artist      = new Label("Artist: "); 
        album       = new Label("Albumn: "); 
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
   
        // Create an ObservableList of entries for the combo box
        ObservableList<String> songField = 
            FXCollections.observableArrayList(
                "No Songs Selected" );
        
        // Create combo box.
        cbName = new ComboBox<String> (songField);
        
        /**
         * Opens up textfile inputed/created in command prompt.
         */
        getPlaylist(thisFile);//args[0]); 
     
        // Determine how the initial stage should be displayed
        if (playlistMap.size() != 0 ) // If file is not empty
        {
            nonemptyFileDisplay();
        }
        else // If file is empty
        {
            emptyFileDisplay();
        }
        
        /**
         * A set of action handlers for each button. 
         * 
         * Below triggers the correct handler for each time
         * a particular button is pressed. 
         */
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
                try 
                {   // Get new song selected from combo box
                    Song selectedSong = playlistMap.get(newVal);
    
                    // Split up the song via delimiter ";"
                    // Assign elements to an array of strings called column
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
        
        // Arrange nodes in grid
        rootNode.add(name, 0, 0);
        rootNode.add(cbName, 1, 0, 4, 1); // col0, row1, colspan, rowspan
        
        rootNode.add(itemCode, 0, 1);       // col1, row1 
        rootNode.add(itemCodeField, 1, 1, 2, 1); 
        
        rootNode.add(description,  0, 2);   // col0, row2
        rootNode.add(descriptionField, 1, 2, 4, 1);
        
        rootNode.add(artist, 0, 3);         // col0, row 3
        rootNode.add(artistField, 1 ,3, 4, 1);  
        
        rootNode.add(album, 0, 4);          // col0, row1
        rootNode.add(albumField, 1, 4, 4, 1);
        
        rootNode.add(price, 0, 5);          // col2, row5
        rootNode.add(priceField, 1, 5, 4, 1); 
        
        rootNode.add(add, 0, 14);       // col 0, row 14 
        rootNode.add(edit, 1 ,14);      // col 1, row 14
        rootNode.add(delete, 2, 14);    // col0, row1
        rootNode.add(accept, 3, 14);    // col1, row1
        rootNode.add(cancel, 4, 14);    // col2, row1
        rootNode.add(exit, 2, 15);
        
        rootNode.add(message, 0, 18, 4, 1); 
         
        // Display myScene
        myStage.setScene(myScene);
        myStage.show();
       
    }  
    
    class AddHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            // Set as true to register in the correct portion
            // of the AcceptHandler
            addClicked = true;
            if(addClicked)
            {
                // Allow editing in the combo boxs
                cbName.setEditable(true);
                
                // Clear field
                cbName.setValue("");
                itemCodeField.setText(""); 
                descriptionField.setText(""); 
                artistField.setText(""); 
                albumField.setText(""); 
                priceField.setText(""); 
                
                // Enable buttons and textfields
                accept.setDisable(false);
                cancel.setDisable(false);
                cbName.setDisable(false);
                itemCodeField.setDisable(false);
                descriptionField.setDisable(false);
                artistField.setDisable(false);
                albumField.setDisable(false); // if N/A, assign "NONE" 
                priceField.setDisable(false);
                
                // Disable button
                add.setDisable(true);
                edit.setDisable(true);
                delete.setDisable(true);
                exit.setDisable(true);        
            }
        }
    }
    
    /**
     * This EditHandler class is triggered by the button 
     * edit. It enables and disables required Textfields
     * for user edit
     * 
     * @author LynHNguyen
     *
     */
    class EditHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            // Set as true to register in the correct portion
            // of the AcceptHandler
            editClicked = true; 
            if(editClicked)
            {
                // Allow TextField to be editted
                cbName.setEditable(true);
                descriptionField.setEditable(true);
                artistField.setEditable(true);
                albumField.setEditable(true);
                priceField.setEditable(true);

                // Enable buttons and textfields
                cbName.setDisable(false);
                descriptionField.setDisable(false);
                artistField.setDisable(false);
                albumField.setDisable(false);
                priceField.setDisable(false);
                accept.setDisable(false);
                cancel.setDisable(false);
                
                // Disable buttons and textfields
                itemCodeField.setDisable(true);
                add.setDisable(true);
                edit.setDisable(true);
                delete.setDisable(true);
                exit.setDisable(true);
            }
        }
    }

    /**
     * This DeleteHandler has textfields disabled. 
     * The only enabled options are gheaccept and cancel buttons
     * The actual deletion of songs happens in the 
     * AcceptHandler.
     * 
     * @author LynHNguyen
     *
     */
    class DeleteHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        { 
            // Set as true to register in the correct portion
            // of the AcceptHandler
            deleteClicked = true; 
            if(deleteClicked)
            {
                // Enable buttons
                accept.setDisable(false);
                cancel.setDisable(false);
                
                // Disable buttons and textFields
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

    /**
     * This CancelHandler resets the addClicked, 
     * editClicked and deleteClickd to false depending on
     * which was set as true (previously clicked).
     * 
     * It calls defaultButtonDisplay() for each of the 
     * "clicked" options.
     * 
     * @author LynHNguyen
     *
     */
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
           
    /**
     * This AcceptHandler reacts when the accept button is 
     * pressed and based on whether  
     * addClicked, editClicked or deleteClicked is "true". 
     * 
     * addClicked : calls putToTreeMap() and writeToFile() 
     * so for the new song added. This condition proceeds to 
     * set the buttons and textfields accordingly.
     * 
     * deleteClicked: calls removeFromTreeMap() and removeFromFile()
     * on the desired song. This condition proceeds to 
     * set the buttons and textfields accordingly.
     * 
     * editClicked: first removes choosen song from playlistMap, 
     * then updates the playlistMap with new data. It also calls 
     * removeFromFile(), which is more like writing everything on
     * the playlistMap to the .txt file again. This condition proceeds 
     * to set the buttons and textfields accordingly.
     * 
     * @author LynHNguyen
     *
     */
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
                    
                    // Reset deleteClicked to false
                    deleteClicked = false; 
                }
            }
            
            if(checkForEmptyField())
            {
                if(editClicked)
                {
                    // Remove chosen song from playlistMap
                    playlistMap.remove(cbName.getValue()); 
                    try 
                    {
                        // Pull user input data
                        song = new Song(cbName.getValue(),  
                                itemCodeField.getText(), 
                                descriptionField.getText(), 
                                artistField.getText(), 
                                albumField.getText(),
                                Double.parseDouble(priceField.getText()) );
                        // Add new song to playlistMap
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
     
    /**
     * writeToFile() method appends new data to "thisFile"
     * using BufferedWriter and FileWriter. Its inputs are 
     * information in the song combobox and attributes text files. 
     * 
     * ComboBoxes use getValue() while textfields use getText().
     * Note that priceField.getText() is changed to Double.
     */
    public void writeToFile()
    {  
        try(BufferedWriter bw = new BufferedWriter(
                new FileWriter(thisFile, true))) // thisFile created in main
        {   
            // Just for playlistMap verification
            System.out.println("This is the map size: " + 
                playlistMap.size());
            // Pull user input data
            bw.write(cbName.getValue() + ";" + 
                itemCodeField.getText() + ";" +
                descriptionField.getText() + ";" + 
                artistField.getText() + ";" + 
                albumField.getText() + ";" + 
                Double.parseDouble(priceField.getText())); 
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
    
    /**
     * The defaultButtonDisplay() sets specified textfields
     * and buttons as disabled and enabled. It also clears 
     * the textfields. 
     * 
     * This method is used in the CancelHandler class
     * to "cancel" previously altered textfields.
     */
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
    
    /**
     * nonemptyFileDisplay() is called at the beginning of 
     * start up when the text file user works with is nonempty.
     * 
     * It sets the combo box's default value as the first 
     * song title in the text file along with enabling 
     * and disabling specific textfields and buttons. 
     */
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
    
    /**
     * emptyFileDisplay() is called at the beginning of 
     * start up when the text file user works with is empty.
     * 
     * It sets the combo box's default value as the first 
     * song title in the text file along with enabling 
     * and disabling specific textfields and buttons. 
     */
    public void emptyFileDisplay()
    {
        // Set the default value
        cbName.setValue("No Songs Selected");
        // Allow cb to be editted
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
    
    /**
     * checkForEmptyField() set conditions on how to handle
     * certain textfields in the gui if the user does not fill 
     * out correctly. It prevents the user from proceeding with
     * with the accept button and display the error message 
     * on the bottom left. 
     * 
     * If all the fields except for the albumField is empty, 
     * it will display an "Empty Field!". 
     *
     * If the albumField is empty, it will change the value 
     * textfield value to "None", representing the song is a single. 
     * 
     * If the priceField cannot be passed as a double, then 
     * a message, "Need NUMERIC Price!" is displayed. 
     * 
     * @return - a boolean true/false. False signals that the 
     * program cannot proceed pass accept. True signals that
     * the program can proceed pass the accept button.
     */
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
     * putToTreeMap() adds song entered by the user 
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
        // Adds song to map
        playlistMap.put(cbName.getValue(), song);
        cbName.getItems().remove("No Songs Selected");
      
        // For map verification
        System.out.println("playlistMap size: " + playlistMap.size()); 
    }
    
    /**
     * removeFromTreeMap() remove selected song title
     * from the playlistMap as well as from the combo box. 
     * 
     */
    public void removeFromTreeMap()
    {
        // Removes song from map
        playlistMap.remove(cbName.getValue());

        // Removes from drop down display
        cbName.getItems().remove("No Songs Selected"); 
        cbName.getItems().remove(cbName.getValue());
    }
    
    /**
     * removeFromFile() writes the updated playlistMap 
     * to the txt file currently being worked on. 
     */
    public void removeFromFile()
    { // After removing from the map, we will write the new map to the file. 
        // note, no "true" so we can overwrite the whole file!!!
        try(BufferedWriter bw = new BufferedWriter( 
                new FileWriter(thisFile))) 
        {   
            System.out.println("This is the map size: " 
                + playlistMap.size());

            // Writes each line in playlistMap to the text file
            for(Map.Entry<String, Song> p: playlistMap.entrySet())
            {
                bw.write("" +  p.getValue() ); // Our map's value has same content 
                bw.newLine();                  // as line in the text file
                bw.flush(); 
            }            
        }
        catch(IOException ioe) 
        {
            ioe.printStackTrace();
        } 
    }
    
    /**
     * This is our main method. It reassigns the first 
     * element in the String array args[] to to "thisFile", 
     * which gets passed through to other methods in the 
     * program to ensure the correct file is being written to 
     * and read from. 
     * 
     * It also holds launch(), which sets off the gui, starting 
     * with start(). 
     * @param args
     */
    public static void main(String args[])
    {      
        System.out.println(args.length);  // args of 1
        System.out.println("args[0] is " + args[0]);
        thisFile = args[0]; 
        
        System.out.println("thisFile is : " + thisFile);
        System.out.println("args is : " + args);

        launch(thisFile); 
    }


    /**
     * getPlaylist() is a method called in the beginning of start().
     * It reads in data from the file arg[0], or thisFile via 
     * BufferedReader and FileReader, and it
     * parse and assign the data to the playlistMap. Note that 
     * there is a section that mimics that of a handler for the combo
     * box. This prefills the combo box with title names in the 
     * existing file just opened. 
     * 
     * This method uses a try/catch to make sure that the file exists.
     * If it doesn't exist, the program jumps to the catch the section
     * where user is prompted to continue or exit then for a new 
     * file name, which gets assigned to "thisFile". 
     * 
     * @param args: passed on starting at the main method. 
     * 
     */
    
    public void getPlaylist(String args) // thisFile
    {
        String line = null; 
        //          Insert inside getPlaylist() and before the try if want to 
        //          input text file via console instead of command line.
        //  System.out.println("Please input the file name, including .txt");
        //  fileName = scanner.next();
        
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
                    
                    // Add to map
                    playlistMap.put(nameInfo, song);   
                    // Add to dropdown comboBox
                    cbName.getItems().add(nameInfo); 
                }
            }
            catch(NullPointerException npe)
            {
                System.out.println("Null exception between here");
            }
            cbName.getItems().remove("No Songs Selected");
            // For verification
            System.out.println("This is the map size: " 
                + playlistMap.size());
            
            // Reads in all that's in text file and add data to playlistMap
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
            // If text file name passed through via the main method
            // does not exist, then we prompt for a new file
            System.out.println("File does not exist");
            System.out.println("Would you like to create a new file? (Y/N)");
            String choice = scanner.next(); 
            if(choice.equalsIgnoreCase("y")) 
            {    
                System.out.println("What is the name "
                    + "of this new playlist (.txt)? ");
                String newFileName = scanner.next(); 
                fileName = newFileName; 
                thisFile = fileName; // Reassign to "thisFile" to access 
                                     // the rest of the code successfully
            }
            else
            {
                System.exit(0);
            } 
        } 
    }
}
    


