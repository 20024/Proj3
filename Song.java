/**
 * This is the Song class. It holds getters/
 * setters and constructor of the Song class as
 * well as getters and setters of the Song class
 * attributes. It can also return the object Song 
 * as a string.
 * 
 * date: August 17, 2018
 * assignment: Project 3
 * class: EN.605.201.82
 * @author LynHNguyen
 *
 */

public class Song
{
    // Instance variables
    private String name; 
    private String itemCode;
    private String description; 
    private String artist; 
    private String album;
    private double price; 

    /**
     * Default constructor
     */
    public Song() 
    {
    }
    
    /**
     * Song constructor
     * 
     * @param name : song title attribute, String
     * @param itemCode : song code attribute, String
     * @param description : description of song attribute, String
     * @param artist : artist's name attribute, String
     * @param album : album's name attribute, 
     *  if "Non" then song is a single, String
     * @param price : song price's attribute, double
     */
    public Song(String name, String itemCode, 
            String description, String artist, 
            String album, double price)
    {
        this.name = name;
        this.itemCode = itemCode;
        this.description = description;
        this.artist = artist;
        this.album = album;
        this.price = price;
    }

    /**
     * Gets the Name of the song as a String
     * @return String name of the song
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the name of the song for writing/editing
     * @param name, type String
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Gets the item code of the song as a String
     * @return String item code of the song
     */
    public String getItemCode()
    {
        return itemCode;
    }

    /**
     * Sets the item code of the song for writing/editing
     * @param itemCode as a String
     */
    public void setItemCode(String itemCode)
    {
        this.itemCode = itemCode;
    }

    /**
     * Gets the descriptionof the song as a String
     * @return song description as a string
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Sets the description of the song for writing/editing
     * @param description as a String
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Gets the artist of the song as a String
     * @return artist as a String
     */
    public String getArtist()
    {
        return artist;
    }

    /**
     * Sets the artist of the song for writing/editing
     * @param artist as a String
     */
    public void setArtist(String artist)
    {
        this.artist = artist;
    }

    /**
     * Gets the album name of the song as a String
     * @return album as a String
     */
    public String getAlbum()
    {
        return album;
    }

    /**
     * Sets the album of the song for writing/editing
     * @param album String
     */
    public void setAlbum(String album)
    {
        this.album = album;
    }

    /**
     * Gets the price of the song as a double
     * @return price as a double
     */
    public double getPrice()
    {
        return price;
    }

    /**
     * Sets the price of the song for writing/editing
     * @param price double
     */
    public void setPrice(double price)
    {
        this.price = price;
    }

    @Override
    public String toString()
    {
        return name + ";" + itemCode + ";" + description + ";"
                + artist + ";" + album + ";" + price;
    }
}
