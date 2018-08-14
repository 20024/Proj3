
public class Playlist
{
    private String song; 
    private String itemCode;
    private String description; 
    private String artist; 
    private String album;
    private String price; 

    // Default constructor
    public Playlist() {}

    // Constructor
    public Playlist(String song, String itemCode, 
            String description, String artist, 
            String album, String priceInfo)
    {
        super();
        this.song = song;
        this.itemCode = itemCode;
        this.description = description;
        this.artist = artist;
        this.album = album;
        this.price = priceInfo;
    }

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

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
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
