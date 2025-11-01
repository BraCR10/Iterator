public class Song {
    private String Title;
    private String Artist;

    public Song(String title, String artist) {
        this.Title = title;
        this.Artist = artist;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getArtist() {
        return Artist;
    }

    public void setArtist(String artist) {
        this.Artist = artist;
    }

    @Override
    public String toString() {
        return Title + " - " + Artist;
    }
}
