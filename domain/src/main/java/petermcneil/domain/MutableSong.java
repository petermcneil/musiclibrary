package petermcneil.domain;

//TODO Make Set and Artists work
public class MutableSong {
    private String title;
    private int length;
    private String leadArtist;
    private String featuredArtists;
    private String genre;
    private String artwork;
    private String lyrics;

    public MutableSong(){
        super();
    }

    public String getTitle() {
        return title;
    }

    public int getLength() {
        return length;
    }

    public String getLeadArtist() {
        return leadArtist;
    }

    public String getFeaturedArtists() {
        return featuredArtists;
    }

    public String getGenre() {
        return genre;
    }

    public String getArtwork() {
        return artwork;
    }

    public String getLyrics() {
        return lyrics;
    }

    public int getPlaycount() {
        return playcount;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setLeadArtist(String leadArtist) {
        this.leadArtist = leadArtist;
    }

    public void setFeaturedArtists(String featuredArtists) {
        this.featuredArtists = featuredArtists;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setArtwork(String artwork) {
        this.artwork = artwork;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public void setPlaycount(int playcount) {
        this.playcount = playcount;
    }

    public int playcount;


}
