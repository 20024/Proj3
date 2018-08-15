/**
 * This program prompts user for a text file. 
 * 1. If the file exists, then it reads the content into the console. 
 * 2. If the file does not exist, then it prompts the user 
 *      to input a new file name. 
 * 3. It then hard code prints into the 
 *      text file via writePlaylist().
 *
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeMap;

public class SongFileReader
{
    
    static // User select file to read/write to 
    Scanner scanner = new Scanner(System.in); 
    // itemCode = key, description = value
    static FileWriter fw;
    static String fileName; 
    static BufferedWriter bw;
    
    static String song;
    static String itemCode;
    static String description;
    static String artist;
    static String album;
    static String price; 
    
    public static void main(String[] args) throws IOException 
    {
        getPlaylist(); 
    }

    static void getPlaylist() throws IOException 
    {
        System.out.println("Please input the file name, including .txt");
        String fileName = scanner.next(); 
        String line = null; 
       
        
        TreeMap < String, Playlist> playlistMap = new TreeMap < String, Playlist>();
    
        try(BufferedReader br = 
            new BufferedReader(new FileReader(fileName)))
        {
            while((line = br.readLine()) != null)
            {           
                System.out.println(line);  
//                playlistMap.put( , line); // writing to map 
                String[] column = line.split(";");
                String song = column[0].trim(); 
                String itemCode = column[1].trim(); 
                String description = column[2].trim();
                String artist = column[3].trim();
                String album = column[4].trim();
                String price = column[5].trim();
                
                Playlist playlist = new Playlist(song,itemCode, 
                        description, artist,  
                        album, Double.parseDouble(price));
                
                playlistMap.put(song, playlist);       
            }
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
                writePlaylist(fileName); 
            }
            else
            {
                System.out.println("This is when we exit()");
            } 
        } 
    }
        
    static void writePlaylist(String fileName) 
    {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true)))
        {   
            bw.write("Does; this; file; right: here; work? "); 
        }
        catch(IOException ioe) 
        {
            ioe.printStackTrace();
        } 
        System.out.println("File created and written. Success");  
    }
     


}

