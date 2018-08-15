public class Song
{
    private String name; 
    private String itemCode;
    private String description; 
    private String artist; 
    private String album;
    private String price; 

    public Song(String name, String itemCode, 
            String description, String artist, 
            String album, String price)
    {
        this.name = name;
        this.itemCode = itemCode;
        this.description = description;
        this.artist = artist;
        this.album = album;
        this.price = price;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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
        return name + ";" + itemCode + ";" + description + ";"
                + artist + ";" + album + ";" + price;
    }
}
