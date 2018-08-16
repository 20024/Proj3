/**
 * This is the Playlist class. It holds getters/
 * setters and constructor of the Playlist class as
 * well as getters and setters of the Playlist class
 * attributes. It can also return the object Playlist 
 * as a string.
 *
 * @author LynHNguyen
 *
 */
public class Playlist
{
    private String song; 
    private String itemCode;
    private String description; 
    private String artist; 
    private String album;
    private double price; 

    /**
     * 
     */
    public Playlist() {}

    // Constructor
    /**
     * 
     * @param song
     * @param itemCode
     * @param description
     * @param artist
     * @param album
     * @param price
     */
    public Playlist(String song, String itemCode, 
            String description, String artist, 
            String album, double price)
    {
        super();
        this.song = song;
        this.itemCode = itemCode;
        this.description = description;
        this.artist = artist;
        this.album = album;
        this.price = price;
    }

    /**
     * 
     * @return
     */
    public String getSong()
    {
        return song;
    }

    public void setSong(String song)
    {
        this.song = song;
    }

    public String getItemCode()
    {
        return itemCode;
    }

    public void setItemCode(String itemCode)
    {
        this.itemCode = itemCode;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getArtist()
    {
        return artist;
    }

    public void setArtist(String artist)
    {
        this.artist = artist;
    }

    public String getAlbum()
    {
        return album;
    }

    public void setAlbum(String album)
    {
        this.album = album;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }
   

    @Override
    public String toString()
    {
        return song + ";" + itemCode + ";" + description + ";"
                + artist + ";" + album + ";" + price;
    }

}
